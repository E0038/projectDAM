package org.e38.game.persistance;

import com.google.gson.*;
import org.e38.game.model.npcs.Criminal;
import org.e38.game.model.npcs.criminals.Bane;
import org.e38.game.model.npcs.criminals.Bicicleta;
import org.e38.game.model.npcs.criminals.Espadon;
import org.e38.game.model.npcs.criminals.LadronEscopeta;
import org.e38.game.utils.Recurses;

import java.lang.reflect.Type;

/**
 * Created by sergi on 5/31/16.
 */
public class CriminalAdapter implements JsonDeserializer<Criminal>, JsonSerializer<Criminal> {
    @Override
    public Criminal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Recurses.AnimatedCriminals enumCrimi = context.deserialize(object.get("name"), Recurses.AnimatedCriminals.class);
        Criminal criminal;
        switch (enumCrimi) {
            case bane:
                criminal = new Bane();
                break;
            case bicicletaFinal:
                criminal = new Bicicleta();
                break;
            case enemigoEspadon:
                criminal = new Espadon();
                break;
            case ladronEscopetaBueno:
                criminal = new LadronEscopeta();
                break;
            default:
                criminal = new Bane();
                break;
        }
/*
#####################
extras modifications optionals != defaults
#####################
*/
        if (object.has("totalHpPoins")) {
            criminal.setTotalHpPoins(object.get("totalHpPoins").getAsFloat());
        }
        if (object.has("protecion")) {
            criminal.setProtecion(object.get("protecion").getAsFloat());
        }
        if (object.has("speed")) {
            criminal.setSpeed(object.get("speed").getAsLong());
        }
        if (object.has("dodgeRate")) {
            criminal.setDodgeRate(object.get("dodgeRate").getAsFloat());
        }
        return criminal;
    }

    @Override
    public JsonElement serialize(Criminal src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("name", context.serialize(Recurses.AnimatedCriminals.valueOf(src.getName())));
        object.addProperty("totalHpPoins", src.getTotalHpPoins());
        object.addProperty("protecion", src.getProtecion());
        object.addProperty("speed", src.getSpeed());
        object.addProperty("dodgeRate", src.getDodgeRate());
        return object;

    }
}
