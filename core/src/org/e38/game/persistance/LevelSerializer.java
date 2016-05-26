package org.e38.game.persistance;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.e38.game.model.Level;
import org.e38.game.model.Wave;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sergi on 5/1/16.
 */
public class LevelSerializer implements JsonDeserializer<Level>, JsonSerializer<Level> {
    public static final String dificultat = "dificultat",
            coins = "coins", INIT_LIFES = "INIT_LIFES",
            waves = "waves", uid = "uid";
    public static final String mapPath = "mapPath";
    public static final String WAVE_GAP = "waveGap";


    @Override
    public Level deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int coins = object.get(LevelSerializer.coins).getAsInt();
        String path = object.get(mapPath).getAsString();
        int lif = object.get(INIT_LIFES).getAsInt();
        float waveGap = object.get(WAVE_GAP).getAsFloat();

        Level level = new Level(coins, path);
        level.waves = context.deserialize(object.get(waves), new TypeToken<List<Wave>>() {
        }.getType());
        level.setInitLifes(lif).setLifes(lif);
        level.waveGap = waveGap;
        return level;
    }

    @Override
    public JsonElement serialize(Level src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject element = new JsonObject();
//        element.addProperty(dificultat, src.dificultat.name());
        element.addProperty(coins, src.getCoins());
        element.addProperty(INIT_LIFES, src.getLifes());
//        element.add(waves, context.serialize(src.getPath()));
        element.add(waves, context.serialize(src.waves));
        element.addProperty(mapPath, src.mapPath);
        element.addProperty(WAVE_GAP, src.waveGap);
        return element;
    }
}
