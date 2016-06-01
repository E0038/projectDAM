package org.e38.game.persistance;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.e38.game.model.Wave;
import org.e38.game.model.npcs.Criminal;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sergi on 6/1/16.
 */
public class WaveAdapter implements JsonDeserializer<Wave>, JsonSerializer<Wave> {
    @Override
    public Wave deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject object = json.getAsJsonObject();
        long gap = object.get("gap").getAsLong();
        List<Criminal> criminals = context.deserialize(object.get("criminals"), new TypeToken<List<Criminal>>() {
        }.getType());
        Wave wave = new Wave(criminals);
        wave.setGap(gap);
        return wave;
    }

    @Override
    public JsonElement serialize(Wave src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("gap", src.getGap());
        object.add("criminals", context.serialize(src.getCriminals()));
        return object;
    }
}
