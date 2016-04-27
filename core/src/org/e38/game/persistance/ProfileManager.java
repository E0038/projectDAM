package org.e38.game.persistance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.StringBuilder;
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
//        System.out.println(Gdx.files.internal("data/profiles.json"));
//       localPath= Gdx.files.getLocalStoragePath();
//        File file = new File("data/profiles.json");
        profilesFile = Gdx.files.internal("data/profiles.json");
        File file = profilesFile.file();
        if (!file.exists() || file.length() == 0) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            //noinspection TypeMayBeWeakened
            Writer writer = new FileWriter(file);
            profile = new Profile();
            writer.write(new Gson().toJson(profile));
            writer.close();
        } else {
            System.out.println("reading...");
            Reader reader = new FileReader(file);
//            StringBuilder builder = new StringBuilder();
//            char[] buffer = new char[1024];
//            int len;
//            while ((len = reader.read(buffer)) != -1) {
//                reader.read(buffer, 0, len);
//                builder.append(buffer);
//            }
//            System.out.println(builder);
            profile = new Gson().fromJson(reader, Profile.class);
            reader.close();
        }
//        System.out.println(new Gson().toJson(profile));
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
