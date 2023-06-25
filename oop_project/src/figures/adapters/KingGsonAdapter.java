package adapters;

import java.lang.reflect.Type;

import com.google.gson.*;
import objects.King;

public class KingGsonAdapter implements JsonDeserializer<King>, JsonSerializer<King> {

    @Override
    public King deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        String name = jsonObject.get("name").getAsString();
        String otherName = jsonObject.get("otherName").getAsString();
        int bornYear = jsonObject.get("bornYear").getAsInt();
        int deathYear = jsonObject.get("deathYear").getAsInt();
        String home = jsonObject.get("home").getAsString();
        String kingYear = jsonObject.get("kingYear").getAsString();
        String desc = jsonObject.get("desc").getAsString();
        String mieuHieu = jsonObject.get("mieuHieu").getAsString();
        String thuyHieu = jsonObject.get("thuyHieu").getAsString();
        String nienHieu = jsonObject.get("nienHieu").getAsString();
        String tenHuy = jsonObject.get("tenHuy").getAsString();
        String theThu = jsonObject.get("theThu").getAsString();

        return new King(id, name, otherName, bornYear, deathYear, home, kingYear, desc, mieuHieu, thuyHieu, nienHieu, tenHuy, theThu);
    }

    @Override
    public JsonElement serialize(King king, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", king.getId());
        jsonObject.addProperty("name", king.getName());
        jsonObject.addProperty("otherName", king.getOtherName());
        jsonObject.addProperty("bornYear", king.getBornYear());
        jsonObject.addProperty("deathYear", king.getDeathYear());
        jsonObject.addProperty("home", king.getHome());
        jsonObject.addProperty("kingYear", king.getKingYear());
        jsonObject.addProperty("desc", king.getDesc());
        jsonObject.addProperty("mieuHieu", king.getMieuHieu());
        jsonObject.addProperty("thuyHieu", king.getThuyHieu());
        jsonObject.addProperty("nienHieu", king.getNienHieu());
        jsonObject.addProperty("tenHuy", king.getTenHuy());
        jsonObject.addProperty("theThu", king.getTheThu());

        return jsonObject;
    }
}