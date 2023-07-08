package festival;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import figures.objects.Figure;


public class Utils {
	public static boolean SaveStringFile(String c, String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bf = new BufferedWriter(osw);

			bf.write(c);

			bf.close();
			osw.close();
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Figure> figureDecode() {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(FinalParameter.FIGURE_FILE_ADDRESS)) {
			Type figuresType = new TypeToken<ArrayList<Figure>>() {
			}.getType();
			ArrayList<Figure> figures = gson.fromJson(reader, figuresType);
			return figures;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static ArrayList<Festival> festivalDecode() {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(FinalParameter.FESTIVAL_FILE_ADDRESS)) {
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

		String idInput = Utils.normalizeString(input);
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

}
