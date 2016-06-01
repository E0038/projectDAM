package org.e38.game.persistance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.e38.game.model.Level;
import org.e38.game.model.Wave;
import org.e38.game.model.npcs.Criminal;
import org.e38.game.utils.World;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


@SuppressWarnings({"TryFinallyCanBeTryWithResources", "ResultOfMethodCallIgnored"})
public class ProfileManager {
    public static final String ALGORIM = "AES";
    private static final String b64Key = "Whiy3TtJhr484rDop7vsfg==";
    private static ProfileManager ourInstance;

    static {
        try {
            ourInstance = new ProfileManager();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public final AtomicReference<Gson> gson = new AtomicReference<>();
    private final Object persistLocker = new Object();
    private final AtomicReference<FileHandle> localBackup = new AtomicReference<>();
    private final AtomicReference<FileHandle> profilesFile = new AtomicReference<>();
    private final AtomicReference<Profile> profile = new AtomicReference<>();
    private FileHandle configFile = Gdx.files.local("data/app.conf");
    private GsonBuilder gsonBuilder = new GsonBuilder();
    private Cipher decrypter;
    private volatile Cipher encryper;
    private ProfileSycronizer sycronizer = new ProfileSycronizer();
    private Thread autosaveThread;

    private ProfileManager() throws IOException {
        conf();
        loadLevels();
        dataInit();
        autoSaveInit();
    }

    private void conf() throws IOException {
        configureChiper();
        configureJson();
        loadStructure();
        Configuration configuration = readConfig();
        applyConf(configuration);
    }

    private void loadLevels() {
        try {
            String json = Gdx.files.internal("raw/rawLevels.json").readString("UTF-8");
            World.levels = gson.get().fromJson(json, new TypeToken<List<Level>>() {
            }.getType());
        } catch (GdxRuntimeException e) {
            Gdx.app.error(getClass().getName(), e.getMessage(), e);
        }
    }

    private void dataInit() throws IOException {
        File file = profilesFile.get().file();
        if (!file.exists() || file.length() == 0) {
            createFiles(file);
        } else {
            loadProfile(file);
        }
    }

    private void autoSaveInit() {
        autosaveThread = new Thread(sycronizer);
        autosaveThread.setDaemon(true);
        autosaveThread.setName("ProfileSycronizerThread");
        autosaveThread.start();
    }

    private void configureChiper() throws IOException {
        byte[] decodedKey = Base64Coder.decode(b64Key);
        Key secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        try {
            decrypter = Cipher.getInstance(ALGORIM);
            encryper = Cipher.getInstance(ALGORIM);
            encryper.init(Cipher.ENCRYPT_MODE, secretKey);
            decrypter.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            Gdx.app.log(getClass().getName(), "KEY error", e);
            throw new IOException(e);
        }

    }

    private void configureJson() {
        gson.set(gsonBuilder
                .registerTypeAdapter(Level.class, new LevelSerializer())
                .registerTypeAdapter(Criminal.class, new CriminalAdapter())
                .registerTypeAdapter(Wave.class, new WaveAdapter())
                .enableComplexMapKeySerialization()
                .create());
    }

    private void loadStructure() {
        FileHandle dir = Gdx.files.local("data");
        if (!dir.exists()) dir.file().mkdir();
        profilesFile.set(Gdx.files.local("data/profile"));
        localBackup.set(Gdx.files.local("data/profile.bak"));
    }

    public Configuration readConfig() {
        Configuration configuration = new Configuration();
        if (configFile.exists()) {
            try {
                String json = readChars(configFile.file());
                configuration = gson.get().fromJson(json, Configuration.class);
                return configuration;
            } catch (IOException e) {
                Gdx.app.log(getClass().getName(), "readConfError", e);
            }
        } else {
            try {
                configFile.file().createNewFile();
                writeConfig(configuration);
            } catch (IOException e) {
                Gdx.app.debug(getClass().getName(), e.getMessage(), e);
            }
        }
        return configuration;//with defaults
    }

    public void applyConf(Configuration configuration) {
        World.volumeChange(configuration.volume);
        //etc
    }

    private void createFiles(File profile) throws IOException {
        if (localBackup.get().length() == 0) localBackup.get().delete();//backup no valida
        if (!localBackup.get().exists()) {
            profile.createNewFile();
            Writer writer = new FileWriter(profile);
            this.profile.set(new Profile());
            try {
                byte[] crypt = encryper.doFinal(gson.get().toJson(this.profile.get()).getBytes());
                writer.write(Base64Coder.encode(crypt));
                writer.close();
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                throw new IOException(e);
            }
        } else {
            localBackup.get().copyTo(profilesFile.get());
        }
    }

    private void loadProfile(File profile) throws IOException {
        Gdx.app.log(getClass().getName(), "reading saved profile...");
        String readed = readChars(profile);
        try {
            byte[] bytes = decrypter.doFinal(Base64Coder.decode(readed));
            String p = new String(bytes);
            this.profile.set(gson.get().fromJson(p, Profile.class));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IOException(e);
        }
    }

    public static String readChars(File file) throws IOException {
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
            gson.get().toJson(configuration, writer);
            writer.close();
        } catch (IOException e) {
            Gdx.app.log(getClass().getName(), "writeConfError", e);
        }
    }

    public static ProfileManager getInstance() {
        return ourInstance;
    }

    private Profile readProfile() throws IOException {
        synchronized (persistLocker) {
            Profile profile;
            String readed = readChars(profilesFile.get().file());
            try {
                byte[] bytes = decrypter.doFinal(Base64Coder.decode(readed));
                String p = new String(bytes);
                profile = gson.get().fromJson(p, Profile.class);
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                throw new IOException(e);
            }
            return profile;
        }
    }

    public void stopAutoSave() {
        sycronizer.stop.set(true);
    }

    public void restartAutoSave() {
        if (!sycronizer.stop.get()) {//if autosave is stoped
            try {
                autosaveThread.join();//wait until finsh
                autoSaveInit();
            } catch (InterruptedException e) {
                Gdx.app.debug(getClass().getName(), e.getMessage(), e);
            }
        }
    }

    public Profile getProfile() {
        return profile.get();
    }

    public void save(Level level) {
        List<Level> list = new ArrayList<>();
        for (Integer idx : profile.get().getCompleteLevels().keySet()) {
            list.add(World.levels.get(idx));
        }
        if (list.contains(level)) {
            int idx = list.indexOf(level);
            if (level.getScore() > list.get(idx).getScore()) {
                list.remove(level);
                profile.get().getCompleteLevels().put(World.levels.indexOf(level), level.getScore());
                sycronizer.doUpdate.set(true);
            }
        } else {
            profile.get().getCompleteLevels().put(World.levels.indexOf(level), level.getScore());
            sycronizer.doUpdate.set(true);
        }
    }

    /**
     * save to persistent files  <b>NOT USE</b> on Main Game Thread
     *
     * @throws IOException
     */
    public void persistentSave() throws IOException {
        synchronized (persistLocker) {
            localBackup.get().delete();
            profilesFile.get().copyTo(localBackup.get());
            try {
                profilesFile.get().delete();//truncate
                if (!profilesFile.get().file().createNewFile()) throw new IOException("file not truncated");
                Writer writer = new FileWriter(profilesFile.get().file());
                try {
//                writer.write(base64Encoder.encodeToString(encryper.doFinal(gson.toJson(profile).getBytes())));
                    writer.write(Base64Coder.encode(encryper.doFinal(gson.get().toJson(profile.get()).getBytes())));
                } catch (IllegalBlockSizeException | BadPaddingException e) {
                    throw new IOException(e);
                } finally {
                    writer.close();
                }
            } catch (IOException e) {
                localBackup.get().copyTo(profilesFile.get());//restore bak if fail
                throw e;
            } finally {
                writeConfig(extractRunTimeConfig());
            }
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
     * get the game progress in format {LevelNumber: LevelPoins}
     *
     * @return a map with user pogres
     */
    public Map<Integer, Integer> getGameProgres() {
//        Map<Integer, Integer> ranking = new HashMap<>();
//        for (Integer idx : profile.getCompleteLevels()) {
//            ranking.put(idx + 1, World.levels.get(idx).getScore());
//        }
//        return ranking;
        return profile.get().getCompleteLevels();
    }

    public boolean newGame() {
        localBackup.get().delete();
        profilesFile.get().delete();
        profile.set(new Profile());
        try {
            createFiles(profilesFile.get().file());
            loadProfile(profilesFile.get().file());
            return true;
        } catch (IOException e) {
            Gdx.app.log(getClass().getName(), e.getMessage(), e);
            return false;
        }

    }

    private class ProfileSycronizer implements Runnable {
        public static final int WAIT_TIME = 600000;
        /**
         * set to True to force update without checking changes, will be reset to false when save is done
         */
        private volatile AtomicBoolean doUpdate = new AtomicBoolean(false);
        /**
         * set to True to stop AutoSave loop on next check,when to run again create new Thread with this object
         */
        private volatile AtomicBoolean stop = new AtomicBoolean(false);

        ProfileSycronizer() {
        }

        @Override
        public void run() {
            while (!stop.get()) {
                if (doUpdate.get() || checkChanges()) {
                    try {
                        ProfileManager.this.persistentSave();
                        doUpdate.set(false);
                    } catch (IOException e) {
                        Gdx.app.debug(getClass().getName(), e.getMessage(), e);
                    }
                }
                for (int i = 0; i < WAIT_TIME / 1000; i++) {
                    if (!doUpdate.get()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Gdx.app.debug(getClass().getName(), e.getMessage(), e);
                        }
                    } else {
                        System.out.println("force");
                        break;
                    }
                }

            }
        }

        private boolean checkChanges() {
            synchronized (ProfileManager.this.persistLocker) {
                HashMap<Integer, Integer> fileLevels;
                try {
                    fileLevels = readProfile().getCompleteLevels();
                } catch (IOException | NullPointerException e) {
                    Gdx.app.debug(getClass().getName(), e.getMessage(), e);
                    return false;
                }
                if (profile.get().getCompleteLevels().size() == fileLevels.size()) {
                    boolean ck = false;
                    for (Integer idx : profile.get().getCompleteLevels().keySet()) {
                        if (!fileLevels.containsKey(idx)) return true;
                        if (World.levels.get(idx).getScore() > fileLevels.get(idx)) {
                            ck = true;
                            break;
                        }
                    }
                    return ck;
                } else return true;
            }
        }
    }
}
