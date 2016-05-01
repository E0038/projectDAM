package org.e38.game.persistance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.e38.game.model.Level;

import java.io.*;
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
        gsonBuilder.registerTypeAdapter(LevelSerializer.class, new LevelSerializer());
//        gsonBuilder.registerTypeAdapter()
        FileHandle dir = Gdx.files.local("data");
        if (!dir.exists()) dir.file().mkdir();
        profilesFile = Gdx.files.local("data/profile.json");
        gson = gsonBuilder.create();
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

    public static ProfileManager getProfile() {
        return ourInstance;
    }

    public void save(Level level) {

    }

    public Integer getScrore(Level level) {
        return 0;
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
