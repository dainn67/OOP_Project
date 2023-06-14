package adapters;

import java.io.IOException;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import objects.Figure;

public class FigureGsonAdapter extends TypeAdapter<Figure> {
	@Override
	public void write(JsonWriter out, Figure figure) throws IOException {
		out.beginObject();
		out.name("name").value(figure.getName());
		out.name("otherName").value(figure.getOtherName());
		out.name("bornYear").value(figure.getBornYear());
		out.name("deathYear").value(figure.getDeathYear());
		out.name("father").value(figure.getFather());
		out.name("mother").value(figure.getMother());
		out.name("dynasty").value(figure.getDynasty());
		out.name("home").value(figure.getHome());
		out.name("desc").value(figure.getDesc());
		out.endObject();
	}

	@Override
	public Figure read(JsonReader in) throws IOException {
		Figure figure = new Figure();
		in.beginObject();
		while (in.hasNext()) {
			String fieldName = in.nextName();
			if (fieldName.equals("name")) {
				figure.setName(in.nextString());
			} else if (fieldName.equals("otherName")) {
				figure.setOtherName(in.nextString());
			} else if (fieldName.equals("bornYear")) {
				figure.setBornYear(in.nextString());
			} else if (fieldName.equals("deathYear")) {
				figure.setDeathYear(in.nextString());
			} else if (fieldName.equals("father")) {
				figure.setFather(in.nextString());
			} else if (fieldName.equals("mother")) {
				figure.setMother(in.nextString());
			} else if (fieldName.equals("dynasty")) {
				figure.setDynasty(in.nextString());
			} else if (fieldName.equals("home")) {
				figure.setHome(in.nextString());
			} else if (fieldName.equals("desc")) {
				figure.setDesc(in.nextString());
			}
		}
		in.endObject();
		return figure;
	}
}
