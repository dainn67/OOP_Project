package main_package;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class WikiScraper {
	public static void main(String[] args) throws Exception {

//		int i = 1;
//		debug();
//		if(i != 0) return;

		List<King> kings = new ArrayList<King>();

		String url = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam#Th%E1%BB%9Di_k%E1%BB%B3_chia_c%E1%BA%AFt";
		Document doc = Jsoup.connect(url).get();

		Elements tables = doc.select("div.mw-parser-output table:not(.wikitable)[width!=\"1400\"]");
		Elements tables_wikitable = doc.select("div.mw-parser-output table.wikitable[width!=\"1400\"]");

		loopTables(tables, kings);
		loopTables(tables_wikitable, kings);

		prtKingList(kings);
//		encodeData(kings);
//		decodeData();
		System.out.println("Total kings: " + kings.size());
	}

	static void debug() {
		String url = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam#Th%E1%BB%9Di_k%E1%BB%B3_chia_c%E1%BA%AFt";
		try {
			Document doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void loopTables(Elements tables, List<King> kings) {
		boolean isTitileRow = true;
		List<String> titleList = new ArrayList<>();

		for (Element table : tables) {
			if (!table.attr("cellpadding").isEmpty() && Integer.parseInt(table.attr("cellpadding")) == 0) {

				// loop through kings inside each table
				Elements characters = table.select("tr");
				for (Element character : characters) {

					// first get the title of each column
					if (isTitileRow) {
						isTitileRow = false;
						Elements titles = character.select("th");
						for (Element title : titles) {
							titleList.add(normalizeString(title.text().replaceAll("\\[.*?\\]", "")));
						}
						continue;
					}

					// then get the attributes corresponding
					Elements characterAttributes = character.select("td");

					kings.add(getKing(characterAttributes));
				}
				isTitileRow = true;
			}
		}
	}

	static King getKing(Elements characterAttributes) {
		String[] kingAttributes = new String[5];
		for (int i = 0; i < kingAttributes.length; i++)
			kingAttributes[i] = "Khong ro";

		String ten = normalizeString(characterAttributes.get(1).text()).replaceAll("\\[.*?\\]", "");
		String detailLink = "";
		String namTriVi = "";

		if (characterAttributes.size() >= 2) {
			detailLink = characterAttributes.get(1).selectFirst("a").attr("href");
			if (ten.equals("An Duong Vuong")) {
				namTriVi = normalizeString(characterAttributes.get(7).text()).replaceAll("\\[.*?\\]", "");
			} else
				for (int i = characterAttributes.size() - 3; i < characterAttributes.size(); i++)
					namTriVi += normalizeString(characterAttributes.get(i).text()).replaceAll("\\[.*?\\]", "") + " ";
		}

		accessDetailKing(detailLink, kingAttributes);

		return new King(ten, kingAttributes[0], kingAttributes[1], kingAttributes[2], kingAttributes[3],
				kingAttributes[4], namTriVi);
	}

	static void accessDetailKing(String detailLink, String[] kingAttributes) {
		// for each king in the tables, access the link inside to get more data
		String url = "https://vi.wikipedia.org" + detailLink;
		try {
			Document detailDoc = Jsoup.connect(url).get();
			Element boxDetail = detailDoc.selectFirst("table.infobox");
			if (boxDetail == null) {
				return;
			}
			Elements trTags = boxDetail.select("tr");
			Element title, content;
			for (Element trTag : trTags) {
				title = trTag.selectFirst("th");
				content = trTag.selectFirst("td");
				if (title == null || content == null)
					continue;
				String contentString = normalizeString(content.text()).replaceAll("\\[.*?\\]", "");
				switch (normalizeString(title.text())) {
				case "Sinh": {
					kingAttributes[0] = contentString;
					break;
				}
				case "Mat": {
					kingAttributes[1] = contentString;
					break;
				}
				case "Trieu dai": {
					kingAttributes[2] = contentString;
					break;
				}
				case "Than phu": {
					kingAttributes[3] = contentString;
					break;
				}
				case "Than mau": {
					kingAttributes[4] = contentString;
					break;
				}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void encodeData(List<King> kings) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(kings);

		try (FileWriter writer = new FileWriter("kings.json")) {
			writer.write(json);
			System.out.println("Data saved to kings.json");
		} catch (IOException e) {
			System.err.println("Failed to save data: " + e.getMessage());
		}
	}

	static List<King> decodeData() {
		String filePath = "kings.json";

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
			List<King> kings = gson.fromJson(json, new TypeToken<List<King>>() {
			}.getType());

			// Print the decoded array to the console
			for (King king : kings) {
				if (king != null) {
					kings.add(king);
//					System.out.println(king.getTen());
				}
			}
			return kings;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	static void prtKingList(List<King> kings) {
		if (kings.isEmpty()) {
			System.out.println("No kings");
			return;
		}
		for (King king : kings) {
			System.out.println(king.getTen());
			System.out.println("    " + king.getNamSinh());
			System.out.println("    " + king.getNamMat());
			System.out.println("    " + king.getTrieu());
			System.out.println("    " + king.getCha());
			System.out.println("    " + king.getMe());
			System.out.println("    " + king.getNamTriVi() + "\n");
		}
	}

	// put the string in this function if console cannot print Vietnamese font
	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace("—", "-");
	}
}
