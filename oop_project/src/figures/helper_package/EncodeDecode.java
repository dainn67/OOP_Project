package helper_package;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import adapters.DynastyGsonAdapter;
import adapters.FigureGsonAdapter;
import adapters.KingGsonAdapter;
import adapters.PoinsettiaGsonAdapter;
import objects.Dynasty;
import objects.Figure;
import objects.King;
import objects.Poinsettia;

public class EncodeDecode {

	public static void encodeToFile(List<Object> list, String fileName) {

		Object tmpObject = list.get(0);
		String filePath = fileName + ".json";
		Gson gson = null;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

			if (tmpObject instanceof Poinsettia) {
				System.out.println("IS INSTANCE OF POINSETTIA");
				gson = new GsonBuilder().registerTypeAdapter(Poinsettia.class, new PoinsettiaGsonAdapter())
						.setPrettyPrinting().create();
			}
			else if (tmpObject instanceof Dynasty) {
				System.out.println("IS INSTANCE OF DYNASTY");
				gson = new GsonBuilder().registerTypeAdapter(Dynasty.class, new DynastyGsonAdapter())
						.setPrettyPrinting().create();
			}
			else if (tmpObject instanceof King) {
				System.out.println("IS INSTANCE OF KING");
				gson = new GsonBuilder().registerTypeAdapter(King.class, new KingGsonAdapter()).setPrettyPrinting()
						.create();
			}
			else if (tmpObject instanceof Figure) {
				System.out.println("IS INSTANCE OF FIGURE");
				gson = new GsonBuilder().registerTypeAdapter(Figure.class, new FigureGsonAdapter()).setPrettyPrinting()
						.create();
			}
			String jsonString = gson.toJson(list);
			writer.write(jsonString);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Dynasty> decodedDynastyList(int type) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Dynasty.class, new DynastyGsonAdapter()).setPrettyPrinting()
				.create();

		ArrayList<Dynasty> newDynastyList = null;
		String filePath;
		switch(type) {
			case 1:{
				filePath = "dynasties.json";
				break;
			}
			case 2: {
				filePath = "extra_dynasties.json";
				break;
			}
			default: filePath = "final_dynasties.json";
		}

		try (Reader reader = new FileReader(filePath)) {
			Type listType = new TypeToken<List<Dynasty>>() {
			}.getType();
			newDynastyList = gson.fromJson(reader, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newDynastyList;
	}

	public static ArrayList<Figure> decodedFigureList(int type) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Figure.class, new FigureGsonAdapter()).setPrettyPrinting()
				.create();

		ArrayList<Figure> newFigureList = null;
		String filePath;
		switch (type) {
		case 1: {
			filePath = "figures.json";
			break;
		}
		case 2: {
			filePath = "vansu_figures.json";
			break;
		}
		default:
			filePath = "final_figures.json";
		}
		
		try (Reader reader = new FileReader(filePath)) {
			Type listType = new TypeToken<List<Figure>>() {
			}.getType();
			newFigureList = gson.fromJson(reader, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newFigureList;
	}

	public static ArrayList<Poinsettia> decodedPoinsettiaList() {
		Gson gson = new GsonBuilder().registerTypeAdapter(Poinsettia.class, new PoinsettiaGsonAdapter())
				.setPrettyPrinting().create();

		ArrayList<Poinsettia> newPoinsettiaList = null;
		String filePath = "poinsettias.json";

		try (Reader reader = new FileReader(filePath)) {
			Type listType = new TypeToken<List<Poinsettia>>() {
			}.getType();
			newPoinsettiaList = gson.fromJson(reader, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newPoinsettiaList;
	}
}
