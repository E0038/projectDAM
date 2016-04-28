package org.e38.game.persistance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
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

    private ProfileManager() throws IOException {
        FileHandle dir = Gdx.files.local("data");
        if (!dir.exists()) dir.file().mkdir();
        profilesFile = Gdx.files.local("data/profile.json");
        System.out.println(profilesFile);
        File file = profilesFile.file();
        if (!file.exists() || file.length() == 0) {
            file.createNewFile();
            Writer writer = new FileWriter(file);
            profile = new Profile();
            writer.write(new Gson().toJson(profile));
            writer.close();
        } else {
            System.out.println("reading...");
            Reader reader = new FileReader(file);
            profile = new Gson().fromJson(reader, Profile.class);
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
