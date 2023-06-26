package adapters;

//import java.io.IOException;
//
//import com.google.gson.*;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonWriter;
//
//import objects.Dynasty;
//
//public class DynastyGsonAdapter extends TypeAdapter<Dynasty> {
//	@Override
//	public void write(JsonWriter out, Dynasty dynasty) throws IOException {
//		out.beginObject();
//		out.name("name").value(dynasty.getName());
//		out.name("startYear").value(dynasty.getStartYear());
//		out.name("endYear").value(dynasty.getEndYear());
//		out.name("desc").value(dynasty.getDesc());
//		out.endObject();
//	}
//
//	@Override
//	public Dynasty read(JsonReader in) throws IOException {
//		Dynasty dynasty = new Dynasty();
//		in.beginObject();
//		while (in.hasNext()) {
//			String fieldName = in.nextName();
//			if (fieldName.equals("name")) {
//				dynasty.setName(in.nextString());
//			} else if (fieldName.equals("startYear")) {
//				dynasty.setStartYear(in.nextString());
//			} else if (fieldName.equals("endYear")) {
//				dynasty.setEndYear(in.nextString());
//			} else if (fieldName.equals("desc")) {
//				dynasty.setDesc(in.nextString());
//			}
//		}
//		in.endObject();
//		return dynasty;
//	}
//}

import com.google.gson.*;

import objects.Dynasty;
import objects.Figure;
import objects.King;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DynastyGsonAdapter implements JsonSerializer<Dynasty>, JsonDeserializer<Dynasty> {

    @Override
    public Dynasty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        int startYear = jsonObject.get("startYear").getAsInt();
        int endYear = jsonObject.get("endYear").getAsInt();
        String desc = jsonObject.get("desc").getAsString();

        Dynasty dynasty = new Dynasty(name, startYear, endYear, desc);

        JsonArray kingsArray = jsonObject.getAsJsonArray("kings");
        for (JsonElement kingElement : kingsArray) {
            String king = kingElement.getAsString();
            dynasty.addKing(king);
        }

        JsonArray figuresArray = jsonObject.getAsJsonArray("figures");
        for (JsonElement figureElement : figuresArray) {
            String figure = figureElement.getAsString();
            dynasty.addFigure(figure);
        }

        return dynasty;
    }

    @Override
    public JsonElement serialize(Dynasty dynasty, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", dynasty.getName());
        jsonObject.addProperty("startYear", dynasty.getStartYear());
        jsonObject.addProperty("endYear", dynasty.getEndYear());
        jsonObject.addProperty("desc", dynasty.getDesc());

        JsonArray kingsArray = new JsonArray();
        for (String king : dynasty.getListKings()) {
            kingsArray.add(new JsonPrimitive(king));
        }
        jsonObject.add("kings", kingsArray);

        JsonArray figuresArray = new JsonArray();
        for (String figure : dynasty.getListFigures()) {
            figuresArray.add(new JsonPrimitive(figure));
        }
        jsonObject.add("figures", figuresArray);

        return jsonObject;
    }
}