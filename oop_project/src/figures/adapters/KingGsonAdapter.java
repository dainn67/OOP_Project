package adapters;

import java.io.IOException;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import objects.King;

public class KingGsonAdapter extends TypeAdapter<King> {
	@Override
	public void write(JsonWriter out, King king) throws IOException {
		out.beginObject();
		out.name("name").value(king.getName());
		out.name("otherName").value(king.getOtherName());
		out.name("born").value(king.getBornYear());
		out.name("death").value(king.getDeathYear());
		out.name("father").value(king.getFather());
		out.name("mother").value(king.getMother());
		out.name("dynasty").value(king.getDynasty());
		out.name("home").value(king.getHome());
		out.name("kingYear").value(king.getKingYear());
		out.name("desc").value(king.getDesc());
		out.name("mieuHieu").value(king.getMieuHieu());
		out.name("thuyHieu").value(king.getThuyHieu());
		out.name("nienHieu").value(king.getNienHieu());
		out.name("tenHuy").value(king.getTenHuy());
		out.name("theThu").value(king.getTheThu());
		out.endObject();
	}

	@Override
	public King read(JsonReader in) throws IOException {
		King king = new King();
		in.beginObject();
		while (in.hasNext()) {
			String fieldName = in.nextName();
			if (fieldName.equals("name")) {
				king.setName(in.nextString());
			} else if (fieldName.equals("otherName")) {
				king.setOtherName(in.nextString());
			} else if (fieldName.equals("born")) {
				king.setBornYear(in.nextString());
			} else if (fieldName.equals("death")) {
				king.setDeathYear(in.nextString());
			} else if (fieldName.equals("father")) {
				king.setFather(in.nextString());
			} else if (fieldName.equals("mother")) {
				king.setMother(in.nextString());
			} else if (fieldName.equals("dynasty")) {
				king.setDynasty(in.nextString());
			} else if (fieldName.equals("home")) {
				king.setHome(in.nextString());
			} else if (fieldName.equals("kingYear")) {
				king.setKingYear(in.nextString());
			} else if (fieldName.equals("desc")) {
				king.setDesc(in.nextString());
			} else if (fieldName.equals("mieuHieu")) {
				king.setMieuHieu(in.nextString());
			} else if (fieldName.equals("thuyHieu")) {
				king.setThuyHieu(in.nextString());
			} else if (fieldName.equals("nienHieu")) {
				king.setNienHieu(in.nextString());
			} else if (fieldName.equals("tenHuy")) {
				king.setTenHuy(in.nextString());
			} else if (fieldName.equals("theThu")) {
				king.setTheThu(in.nextString());
			}
		}
		in.endObject();
		return king;
	}
}
