package figures.helpers;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import figures.objects.Figure;
import figures.objects.King;
import figures.objects.Poinsettia;

public class Compliation {
	public static void append() {
		ArrayList<Figure> objectList = new ArrayList<>();
		String filePath1 = "kings.json";
		String filePath2 = "nonduplicate_figures.json";
		String filePath3 = "poinsettias.json";

		Gson gson = new Gson();
		String jsonData = HelperFunctions.readFile(filePath2);
		Type listType = new TypeToken<ArrayList<Figure>>() {
		}.getType();
		ArrayList<Figure> fileObjects = gson.fromJson(jsonData, listType);

		jsonData = HelperFunctions.readFile(filePath1);
		listType = new TypeToken<ArrayList<King>>() {
		}.getType();
		ArrayList<King> kingList = gson.fromJson(jsonData, listType);

		jsonData = HelperFunctions.readFile(filePath3);
		listType = new TypeToken<ArrayList<Poinsettia>>() {
		}.getType();
		ArrayList<Poinsettia> poinsettiaList = gson.fromJson(jsonData, listType);

		objectList.addAll(fileObjects);
		objectList.addAll(kingList);
		objectList.addAll(poinsettiaList);
		
		HelperFunctions.encodeListToJson(objectList, "appended_figures.json");
	}
}
