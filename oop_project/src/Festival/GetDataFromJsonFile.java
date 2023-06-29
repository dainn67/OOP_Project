package Festival;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.ArrayList;

import Figures.Figure;

public class GetDataFromJsonFile {
	public static final String FILE_ADDRESS = "E:/Festival.json";

	public static void main(String[] args) {
		ArrayList<Festival> festivals = new ArrayList<Festival>();
		festivals = festivalDecode();
//		ArrayList<Festival> fes = new ArrayList<Festival>();
//		fes = festivalSearch("le hoi", festivals);
//		for (Festival festival : fes) {
//			System.out.println(festival.getName());
//		}

		ArrayList<Figure> figures = new ArrayList<Figure>();
		figures = figureDecode();

	}

	public static ArrayList<Festival> festivalDecode() {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(FILE_ADDRESS)) {
			Type festivalType = new TypeToken<ArrayList<Festival>>() {
			}.getType();
			ArrayList<Festival> festivals = gson.fromJson(reader, festivalType);
			return festivals;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Festival> festivalSearch(String input, ArrayList<Festival> festivals) {
		ArrayList<Festival> festivalArrayList = new ArrayList<>();

		String idInput = normalizeString(input);
		for (Festival festival : festivals) {
			if (festival.getId().contains(idInput)) {
				festivalArrayList.add(festival);
			}
		}

		return festivalArrayList;
	}

	public static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace("—", "-").toLowerCase().replace(" ", "");
	}

	public static ArrayList<Figure> figureDecode() {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader("E:/figures.json")) {
			Type figuresType = new TypeToken<ArrayList<Figure>>() {
			}.getType();
			ArrayList<Figure> figures = gson.fromJson(reader, figuresType);
			return figures;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
