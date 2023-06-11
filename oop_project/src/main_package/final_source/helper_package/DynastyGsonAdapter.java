package helper_package;

import java.io.IOException;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import objects.Dynasty;

public class DynastyGsonAdapter extends TypeAdapter<Dynasty> {
	@Override
	public void write(JsonWriter out, Dynasty dynasty) throws IOException {
		out.beginObject();
		out.name("name").value(dynasty.getName());
		out.name("startYear").value(dynasty.getStartYear());
		out.name("endYear").value(dynasty.getEndYear());
		out.name("desc").value(dynasty.getDesc());
		out.endObject();
	}

	@Override
	public Dynasty read(JsonReader in) throws IOException {
		Dynasty dynasty = new Dynasty();
		in.beginObject();
		while (in.hasNext()) {
			String fieldName = in.nextName();
			if (fieldName.equals("name")) {
				dynasty.setName(in.nextString());
			} else if (fieldName.equals("startYear")) {
				dynasty.setStartYear(in.nextString());
			} else if (fieldName.equals("endYear")) {
				dynasty.setEndYear(in.nextString());
			} else if (fieldName.equals("desc")) {
				dynasty.setDesc(in.nextString());
			}
		}
		in.endObject();
		return dynasty;
	}
}
