package main_package;

import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.io.FileReader;
import java.io.BufferedReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CharacterScraper {
	public static int count = 0;
	public static int realCount = 0;

	static int urlCounter = 0;

	static String[] toSaveToFile = new String[155];
	static String[] resDynastys = new String[155];

	public static void main(String[] args) {
		try {
//			Document document = Jsoup.connect("https://nguoikesu.com/dong-lich-su/hong-bang-va-van-lang/ky-hong-bang-thi").get();
//			Document document = Jsoup.connect("https://nguoikesu.com/dong-lich-su/bac-thuoc-lan-thu-nhat/nha-trieu/trieu-vu-vuong-trieu-da").get();
//			Document document = Jsoup.connect("https://nguoikesu.com/dong-lich-su/bac-thuoc-lan-thu-nhat/nha-trieu/trieu-vu-vuong-trieu-da").get();
//			Document document = Jsoup.connect("https://nguoikesu.com/dong-lich-su/bac-thuoc-lan-thu-nhat/nha-trieu/trieu-van-vuong-trieu-ho").get();
			Document document = Jsoup.connect("https://nguoikesu.com/nhan-vat").get();

			getFigures(document);
//			System.out.println(normalizeString(document.toString()));

//			getFigures(document);

			while (true) {
				if (urlCounter > 1450)
					break;
				String url;
				Document doc;
				if (urlCounter == 0) {
					url = "https://nguoikesu.com/nhan-vat";
					doc = Jsoup.connect(url).get();
					getFigures(doc);
				} else {
					url = "https://nguoikesu.com/nhan-vat?start=" + urlCounter;
					doc = Jsoup.connect(url).get();
					getFigures(doc);
				}

			}

//			encodeDynasty();
//			decodeDynasty();

			System.out.println("\nCount: " + count);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	static void getFigures(Document doc) {
		Elements divs = doc.select("div.com-content-category-blog__item");

		for (Element div : divs) {
			Elements _figureName = div.select("h2");
			Elements _figureDesc = div.select("p");

			String nameContentString = "";
			for (Element name : _figureName){
				nameContentString += name.text();
			}
			
			String descContentString = "";
			for (Element desc : _figureDesc){
				descContentString += desc.text() + "";
			}
			descContentString += "\n";

			System.out.println(normalizeString(nameContentString));
			System.out.println(normalizeString(descContentString));
			
			count++;
			
		}
		urlCounter += 5;
	}

	static void encodeDynasty() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(toSaveToFile);

		try (FileWriter writer = new FileWriter("data.json")) {
			writer.write(json);
			System.out.println("Data saved to data.json");
		} catch (IOException e) {
			System.err.println("Failed to save data: " + e.getMessage());
		}
	}

	static void decodeDynasty() {
		String filePath = "data.json";

		try {
			// Read the JSON file into a string
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			br.close();
			String json = sb.toString();

			// Decode the JSON string into a String array
			Gson gson = new Gson();
			resDynastys = gson.fromJson(json, String[].class);

			// Print the decoded array to the console
			for (String item : resDynastys) {
				if (item != null) {
					realCount++;
					System.out.println(item);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// A helper function to normalize the string
	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ",
				"d");
	}
}
