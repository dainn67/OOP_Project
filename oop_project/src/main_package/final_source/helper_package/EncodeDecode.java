package helper_package;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import objects.Dynasty;
import objects.Figure;

public class EncodeDecode {

	public static void encodeToFile(List<Object> list, String fileName) {

		Object tmpObject = list.get(0);
		String filePath = fileName +  ".json";
		
		if (tmpObject instanceof Figure) {
			Gson gson = new GsonBuilder().registerTypeAdapter(Figure.class, new FigureGsonAdapter()).setPrettyPrinting()
					.create();

			try (Writer writer = new FileWriter(filePath)) {
				gson.toJson(list, writer);
				System.out.println("Encode & Write figure list complete");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (tmpObject instanceof Dynasty) {
			Gson gson = new GsonBuilder().registerTypeAdapter(Dynasty.class, new DynastyGsonAdapter()).setPrettyPrinting()
					.create();

			try (Writer writer = new FileWriter(filePath)) {
				gson.toJson(list, writer);
				System.out.println("Encode & Write dynasty list complete");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<Dynasty> decodedDynastyList(Boolean isExtra) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Dynasty.class, new DynastyGsonAdapter()).setPrettyPrinting()
				.create();

		List<Dynasty> newDynastyList = null;
		String filePath = isExtra ? "extra_dynasties.json" : "dynasty.json";

		try (Reader reader = new FileReader(filePath)) {
			Type listType = new TypeToken<List<Dynasty>>() {
			}.getType();
			newDynastyList = gson.fromJson(reader, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newDynastyList;
	}

	public static List<Figure> decodedFigureList() {
		Gson gson = new GsonBuilder().registerTypeAdapter(Figure.class, new FigureGsonAdapter()).setPrettyPrinting()
				.create();

		List<Figure> newFigureList = null;
		String filePath = "figures.json";

		try (Reader reader = new FileReader(filePath)) {
			Type listType = new TypeToken<List<Figure>>() {
			}.getType();
			newFigureList = gson.fromJson(reader, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newFigureList;
	}
}
