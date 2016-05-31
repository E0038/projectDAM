package org.e38.game.persistance;

import com.google.gson.*;
import org.e38.game.model.npcs.Criminal;
import org.e38.game.model.npcs.criminals.Bane;
import org.e38.game.utils.Recurses;

import java.lang.reflect.Type;

/**
 * Created by sergi on 5/31/16.
 */
// TODO: 5/31/16 implement
// and Register Criminal adapter to ProfileManager.gson
public class CriminalAdapter implements JsonDeserializer<Criminal>, JsonSerializer<Criminal> {
    @Override
    public Criminal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Recurses.AnimatedCriminals enumCrimi = context.deserialize(object.get("name"), Recurses.AnimatedCriminals.class);
        Criminal criminal;
        switch (enumCrimi) {
            default:
                criminal = new Bane();
        }
        //etc
        return null;
    }

    // TODO: 5/31/16 JSON FORMAT {"name":"Recurses.AnimatedCriminals Enum",etc}
    @Override
    public JsonElement serialize(Criminal src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("name", context.serialize(Recurses.AnimatedCriminals.valueOf(src.getName())));
        //etc
        return object;

    }
}
