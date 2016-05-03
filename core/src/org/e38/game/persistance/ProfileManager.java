package org.e38.game.persistance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.e38.game.model.Level;

import java.io.*;
import java.util.List;
import java.util.Map;


public class ProfileManager {
    private static ProfileManager ourInstance;

    static {
        try {
            ourInstance = new ProfileManager();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public FileHandle localBackup;
    private String localPath;

    private FileHandle profilesFile;
    private Profile profile;
    private GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;

    private ProfileManager() throws IOException {
        load();
        dataInit();
    }

    private void load() {
        configureJson();
        loadStructure();
    }

    private void dataInit() throws IOException {
        File file = profilesFile.file();
        if (!file.exists() || file.length() == 0) {
            file.createNewFile();
            Writer writer = new FileWriter(file);
            profile = new Profile();
            writer.write(gson.toJson(profile));
            writer.close();
        } else {
            Gdx.app.log(ProfileManager.class.getName(), "reading saved profile...");
            Reader reader = new FileReader(file);
            profile = gson.fromJson(reader, Profile.class);
            reader.close();
        }
    }

    private void configureJson() {
        gsonBuilder.registerTypeAdapter(LevelSerializer.class, new LevelSerializer());
        gson = gsonBuilder.create();
    }

    private void loadStructure() {
        FileHandle dir = Gdx.files.local("data");
        if (!dir.exists()) dir.file().mkdir();
        profilesFile = Gdx.files.local("data/profile.json");
        localBackup = Gdx.files.local("data/profile.json.bak");
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
     * save to persistent file  <b>NOT USE</b> on Main Game Thread
     *
     * @throws IOException
     */
    public void persistSave() throws IOException {
        profilesFile.copyTo(localBackup);
        try {
            profilesFile.delete();//truncate
            if (!profilesFile.file().createNewFile()) throw new IOException("file not truncated");
            Writer writer = new FileWriter(profilesFile.file());
            gson.toJson(profile, writer);
            writer.close();
        } catch (IOException e) {
            localBackup.copyTo(profilesFile);//restore bak if fail
            throw e;
        }
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
