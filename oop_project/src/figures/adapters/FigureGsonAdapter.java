package adapters;

import com.google.gson.*;

import objects.Dynasty;
import objects.Figure;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FigureGsonAdapter implements JsonSerializer<Figure>, JsonDeserializer<Figure> {
    @Override
    public JsonElement serialize(Figure figure, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", figure.getId());
        jsonObject.addProperty("name", figure.getName());
        jsonObject.addProperty("otherName", figure.getOtherName());
        jsonObject.addProperty("bornYear", figure.getBornYear());
        jsonObject.addProperty("deathYear", figure.getDeathYear());
        jsonObject.addProperty("home", figure.getHome());
        jsonObject.addProperty("desc", figure.getDesc());

        // Serialize parents
        JsonArray parentsArray = new JsonArray();
        for (String parent : figure.getParents()) {
            parentsArray.add(new JsonPrimitive(parent));
        }
        jsonObject.add("parents", parentsArray);

        // Serialize dynasties
        JsonArray dynastiesArray = new JsonArray();
        for (String dynasty : figure.getDynasties()) {
            dynastiesArray.add(new JsonPrimitive(dynasty));
        }
        jsonObject.add("dynasties", dynastiesArray);

        return jsonObject;
    }

    @Override
    public Figure deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        String name = jsonObject.get("name").getAsString();
        String otherName = jsonObject.get("otherName").getAsString();
        int bornYear = jsonObject.get("bornYear").getAsInt();
        int deathYear = jsonObject.get("deathYear").getAsInt();
        String home = jsonObject.get("home").getAsString();
        String desc = jsonObject.get("desc").getAsString();

        Figure figure = new Figure(id, name, otherName, bornYear, deathYear, home, desc);

        JsonArray parentsArray = jsonObject.getAsJsonArray("parents");
        ArrayList<String> parents = new ArrayList<>();
        for (JsonElement parent : parentsArray) {
            parents.add(parent.getAsString());
        }
        figure.setParents(parents);

        JsonArray dynastiesArray = jsonObject.getAsJsonArray("dynasties");
        ArrayList<String> dynasties = new ArrayList<>();
        for (JsonElement dynasty : dynastiesArray) {
            dynasties.add(dynasty.getAsString());
        }
        figure.setDynasties(dynasties);

        return figure;
    }
}