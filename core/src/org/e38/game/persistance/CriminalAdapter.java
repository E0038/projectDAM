package org.e38.game.persistance;

import com.google.gson.*;
import org.e38.game.model.npcs.Criminal;

import java.lang.reflect.Type;

/**
 * Created by sergi on 5/31/16.
 */
// TODO: 5/31/16 implement
// and Register Criminal adapter to ProfileManager.gson
public class CriminalAdapter implements JsonDeserializer<Criminal>, JsonSerializer<Criminal> {
    @Override
    public Criminal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Criminal src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
