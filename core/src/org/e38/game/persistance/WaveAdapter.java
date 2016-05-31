package org.e38.game.persistance;

import com.google.gson.*;
import org.e38.game.model.Wave;

import java.lang.reflect.Type;

/**
 * Created by sergi on 5/31/16.
 */
// TODO: 5/31/16  
public class WaveAdapter implements JsonDeserializer<Wave>, JsonSerializer<Wave> {
    @Override
    public Wave deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Wave src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
