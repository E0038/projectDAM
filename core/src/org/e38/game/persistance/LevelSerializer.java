package org.e38.game.persistance;

import com.google.gson.*;
import org.e38.game.model.Level;

import java.lang.reflect.Type;

/**
 * Created by sergi on 5/1/16.
 */
public class LevelSerializer implements JsonDeserializer<Level>, JsonSerializer<Level> {
    public static final String dificultat = "dificultat",
            coins = "coins", lifes = "lifes",
            path = "path", uid = "uid";


    @Override
    public Level deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Level src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject element = new JsonObject();
        element.addProperty(dificultat, src.dificultat.name());
        element.addProperty(coins, src.getCoins());
        element.addProperty(lifes, src.getLifes());
        element.add(path, context.serialize(src.getPath()));
        element.addProperty(uid, src.getLevelUID());
        return element;
    }
}
