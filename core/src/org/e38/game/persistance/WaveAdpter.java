package org.e38.game.persistance;

import com.google.gson.*;
import org.e38.game.model.Wave;

import java.lang.reflect.Type;

/**
 * Created by sergi on 5/24/16.
 */
public class WaveAdpter implements JsonDeserializer<Wave>, JsonSerializer<Wave> {
    @Override
    public Wave deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return context.deserialize(json, typeOfT);
    }

    @Override
    public JsonElement serialize(Wave src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = context.serialize(src, typeOfSrc).getAsJsonObject();
        object.remove("");
        return null;
    }
}
