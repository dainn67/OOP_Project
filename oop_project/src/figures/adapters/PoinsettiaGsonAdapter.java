package adapters;

import java.io.IOException;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import objects.Poinsettia;

public class PoinsettiaGsonAdapter extends TypeAdapter<Poinsettia> {
	@Override
	public void write(JsonWriter out, Poinsettia figure) throws IOException {
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
		out.name("king").value(figure.getKingYear());
		out.name("grad").value(figure.getGraduatedYear());
		out.endObject();
	}

	@Override
	public Poinsettia read(JsonReader in) throws IOException {
		Poinsettia poinsettia = new Poinsettia();
		in.beginObject();
		while (in.hasNext()) {
			String fieldName = in.nextName();
			if (fieldName.equals("name")) {
				poinsettia.setName(in.nextString());
			} else if (fieldName.equals("otherName")) {
				poinsettia.setOtherName(in.nextString());
			} else if (fieldName.equals("bornYear")) {
				poinsettia.setBornYear(in.nextString());
			} else if (fieldName.equals("deathYear")) {
				poinsettia.setDeathYear(in.nextString());
			} else if (fieldName.equals("father")) {
				poinsettia.setFather(in.nextString());
			} else if (fieldName.equals("mother")) {
				poinsettia.setMother(in.nextString());
			} else if (fieldName.equals("dynasty")) {
				poinsettia.setDynasty(in.nextString());
			} else if (fieldName.equals("home")) {
				poinsettia.setHome(in.nextString());
			} else if (fieldName.equals("desc")) {
				poinsettia.setDesc(in.nextString());
			} else if (fieldName.equals("king")) {
				poinsettia.setKingYear(in.nextString());
			} else if (fieldName.equals("grad")) {
				poinsettia.setGraduatedYear(in.nextString());
			}
		}
		in.endObject();
		return poinsettia;
	}
}
