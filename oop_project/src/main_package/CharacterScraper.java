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
	
	static int dongLichSuCounter = 0;

	static String[] toSaveToFile = new String[155];
	static String[] resDynastys = new String[155];

	public static void main(String[] args) {
		try {
//			Document document = Jsoup.connect("https://nguoikesu.com/dong-lich-su/hong-bang-va-van-lang/ky-hong-bang-thi").get();
//			Document document = Jsoup.connect("https://nguoikesu.com/dong-lich-su/bac-thuoc-lan-thu-nhat/nha-trieu/trieu-vu-vuong-trieu-da").get();
//			Document document = Jsoup.connect("https://nguoikesu.com/dong-lich-su/bac-thuoc-lan-thu-nhat/nha-trieu/trieu-vu-vuong-trieu-da").get();
//			Document document = Jsoup.connect("https://nguoikesu.com/dong-lich-su/bac-thuoc-lan-thu-nhat/nha-trieu/trieu-van-vuong-trieu-ho").get();
//			Document document = Jsoup.connect("").get();
			
			
//			System.out.println(normalizeString(document.toString()));
			
//			getFigures(document);

			while (true) {
				if (dongLichSuCounter > 150)
					break;
				String url;
				Document dynastys;
				if (dongLichSuCounter == 0) {
					url = "https://nguoikesu.com/dong-lich-su";
					dynastys = Jsoup.connect(url).get();
					getDynastys(dynastys);
				} else {
					url = "https://nguoikesu.com/dong-lich-su?start=" + dongLichSuCounter;
					dynastys = Jsoup.connect(url).get();
					getDynastys(dynastys);
				}

			}

//			encodeDynasty();
//
//			decodeDynasty();

			System.out.println("\nCount: " + count);
//			System.out.println("Real count: " + realCount);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static void getDynastys(Document doc) {
		Elements divs = doc.select("div.item-content");
		
		for (Element div : divs) {
			Elements headings = div.select("h2[itemprop=name]");
			Elements paragraphs = div.select("p");
			String readmore = div.select("p.readmore a").attr("href");
			
			System.out.println(readmore);
			
			String content = "";
			for (Element paragraph : paragraphs) {
				content += paragraph.text() + "\n";
			}

//			toSaveToFile[count] = normalizeString(headings.text()) + "\n" + normalizeString(content.trim()) + "\n" + readmore + "\n\n";

			count++;
			
			try {
				getFigures(Jsoup.connect("https://nguoikesu.com/" + readmore).get());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		dongLichSuCounter += 6;
	}
	
	static void getFigures(Document doc) {
		Elements divs = doc.select("div.com-content-article__body");
		for(Element div: divs) {
			Elements _tocElements = div.select("h3");
			Elements _desc = divs.select("p");
			
			String nameContent = "";;
			if(!_tocElements.isEmpty()) {
				for (Element paragraph : _tocElements) {
					count++;
					nameContent += paragraph.text() + "\n";
				}
			}else {
				count++;
			}
			
			String descContent = "";
			for (Element paragraph : _desc) {
				descContent += paragraph.text() + "\n";
			}
			
//			if(!_tocElements.isEmpty()) System.out.println(normalizeString(nameContent));
//			System.out.println(normalizeString(descContent));
		}
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
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d");
	}
}
