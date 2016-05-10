package org.e38.game.persistance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.e38.game.World;
import org.e38.game.model.Level;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

//import java.util.Base64;


public class ProfileManager {
    public static final String ALGORIM = "AES";
    private static ProfileManager ourInstance;

    static {
        try {
            ourInstance = new ProfileManager();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    //    private final Base64.Encoder base64Encoder = Base64.getEncoder();
//    private final Base64.Decoder base64Decoder = Base64.getDecoder();
    public FileHandle localBackup;
    private FileHandle configFile = Gdx.files.local("data/app.conf");
    private String localPath;
    private FileHandle profilesFile;
    private Profile profile;
    private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;
    private FileHandle key = Gdx.files.internal("key");
    private SecretKey secretKey;
    private Cipher decrypter;
    private Cipher encryper;


    private ProfileManager() throws IOException {
        conf();
        dataInit();
    }

    private void conf() throws IOException {
        configureChiper();
        configureJson();
        loadStructure();
        Configuration configuration = readConfig();
        applyConf(configuration);
    }

    private void dataInit() throws IOException {
        File file = profilesFile.file();
        if (!file.exists() || file.length() == 0) {
            createFiles(file);
        } else {
            loadFiles(file);
        }
    }

    private void configureChiper() throws IOException {
        byte[] decodedKey = Base64.decodeBase64(readChars(key.file()));
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        try {
            decrypter = Cipher.getInstance(ALGORIM);
            encryper = Cipher.getInstance(ALGORIM);
            encryper.init(Cipher.ENCRYPT_MODE, secretKey);
            decrypter.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            Gdx.app.log(getClass().getName(), "KEY error", e);
        }

    }

    private void configureJson() {
        gsonBuilder.registerTypeAdapter(Level.class, new LevelSerializer());
        gson = gsonBuilder.create();
    }

    private void loadStructure() {
        FileHandle dir = Gdx.files.local("data");
        if (!dir.exists()) dir.file().mkdir();
        profilesFile = Gdx.files.local("data/profile.json");
        localBackup = Gdx.files.local("data/profile.json.bak");
    }

    public Configuration readConfig() {
        Configuration configuration = new Configuration();
        if (configFile.exists()) {
            try {
                String json = readChars(configFile.file());
                configuration = gson.fromJson(json, Configuration.class);
                return configuration;
            } catch (IOException e) {
                Gdx.app.log(getClass().getName(), "readConfError", e);
            }
        } else {
            try {
                configFile.file().createNewFile();
                writeConfig(configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configuration;//with defaults
    }

    public void applyConf(Configuration configuration) {
        World.volumeChange(configuration.volume);
        //etc
    }

    private void createFiles(File file) throws IOException {
        if (localBackup.length() == 0) localBackup.delete();//backup no valida
        if (!localBackup.exists()) {
            file.createNewFile();
            Writer writer = new FileWriter(file);
            profile = new Profile();
            try {
                byte[] crypt = encryper.doFinal(gson.toJson(profile).getBytes());
//                writer.write(base64Encoder.encodeToString(crypt));
                writer.write(Base64.encodeBase64String(crypt));
                writer.close();
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                throw new IOException(e);
            }

        } else {
            localBackup.copyTo(profilesFile);
        }
    }

    private void loadFiles(File file) throws IOException {
        Gdx.app.log(ProfileManager.class.getName(), "reading saved profile...");
        String readed = readChars(file);
        try {
//            byte[] bytes = decrypter.doFinal(base64Decoder.decode(readed));
            byte[] bytes = decrypter.doFinal(Base64.decodeBase64(readed));
            String p = new String(bytes);
            profile = gson.fromJson(p, Profile.class);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IOException(e);
        }
    }

    private String readChars(File file) throws IOException {
        Reader reader = new FileReader(file);
        char[] chars = new char[1024];
        StringBuilder builder = new StringBuilder();
        int n;
        while ((n = reader.read(chars)) != -1) {
            builder.append(chars, 0, n);
        }
        reader.close();
        return builder.toString();
    }

    public void writeConfig(Configuration configuration) {
        configFile.delete();
        try {
            configFile.file().createNewFile();
            Writer writer = new FileWriter(configFile.file());
            gson.toJson(configuration, writer);
            writer.close();
        } catch (IOException e) {
            Gdx.app.log(getClass().getName(), "writeConfError", e);
        }
    }

    public static ProfileManager getProfile() {
        return ourInstance;
    }

    public void save(Level level) {
        List<Level> list = profile.getCompleteLevels();
        if (list.contains(level)) {
            int idx = list.indexOf(level);
            if (level.getScore() > list.get(idx).getScore()) {
                list.remove(level);
                profile.getCompleteLevels().add(level);
            }
        } else {
            profile.getCompleteLevels().add(level);
        }
    }

    /**
     * save to persistent files  <b>NOT USE</b> on Main Game Thread
     *
     * @throws IOException
     */
    public void persistSave() throws IOException {
        profilesFile.copyTo(localBackup);
        try {
            profilesFile.delete();//truncate
            if (!profilesFile.file().createNewFile()) throw new IOException("file not truncated");
            Writer writer = new FileWriter(profilesFile.file());
            try {
//                writer.write(base64Encoder.encodeToString(encryper.doFinal(gson.toJson(profile).getBytes())));
                writer.write(Base64.encodeBase64String(encryper.doFinal(gson.toJson(profile).getBytes())));
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                throw new IOException(e);
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            localBackup.copyTo(profilesFile);//restore bak if fail
            throw e;
        } finally {
            writeConfig(extractRunTimeConfig());
        }
    }

    private Configuration extractRunTimeConfig() {
        Configuration configuration = new Configuration();
        configuration.volume = World.getVolume();
        configuration.selecteDificultat = World.selecteDificultat;
        configuration.speed = World.speed;
        return configuration;
    }

    /**
     * gets de scrore of a saved level on the current profile
     *
     * @param level
     * @return -1 if not found , the saved Scorre if found
     */
    public Integer getScrore(Level level) {
        List<Level> levels = profile.getCompleteLevels();
        int idx = levels.indexOf(level);
        if (idx < 0) {
            return -1;
        }
        return levels.get(idx).getScore();
    }

    /**
     * get the game progress in format {LevelNumber: LevelPoins}
     *
     * @return a map with user pogres
     */
    public Map<Integer, Integer> getGameProgres() {
        return null;
    }

}
