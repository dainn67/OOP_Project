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

		boolean isTitileRow = true;
		List<String> titleList = new ArrayList<>();

		// loop through tables with kings
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
					King tmpKing;
					if ((tmpKing = getKing(characterAttributes)) != null) {
						kings.add(tmpKing);
						// add to the king list
					}
				}
			}
		}

//		prtKingList(kings);
//		encodeData(kings);
//		decodeData();
		System.out.println("Total kings: " + kings.size());
	}

	static King getKing(Elements characterAttributes) {
		List<String> kingAttributes = new ArrayList<String>();
		String detailLink = "";
		String namTriVi = "";

		if (characterAttributes.size() >= 2) {
			detailLink = characterAttributes.get(1).selectFirst("a").attr("href");
			for (int i = 7; i < characterAttributes.size(); i++) {
				namTriVi += normalizeString(characterAttributes.get(i).text()) + " ";
			}
		}

		for (Element attribute : characterAttributes) {
			if (attribute.text().isEmpty()) {
				kingAttributes.add("Khong co");
			} else {
				kingAttributes.add(normalizeString(attribute.text().replaceAll("\\[.*?\\]", "")));
			}
		}

		if (kingAttributes.size() != 0) {
			King tmpKing = new King(kingAttributes.get(1), kingAttributes.get(2), kingAttributes.get(3),
					kingAttributes.get(4), kingAttributes.get(5), kingAttributes.get(6), namTriVi);
			System.out.println(tmpKing.getTen());
			accessDetailKing(detailLink);
			System.out.println("    Tri vi: " + tmpKing.getNamTriVi().replaceAll("\\[.*?\\]", ""));
			return tmpKing;
		}
		return null;
	}
	static void accessDetailKing(String detailLink) {
		//for each king in the tables, access the link inside to get more data
		String url = "https://vi.wikipedia.org" + detailLink;
		try {
			Document detailDoc = Jsoup.connect(url).get();
			Element boxDetail = detailDoc.selectFirst("table.infobox");
			if (boxDetail == null) {
				System.out.println("    (King) NO TABLE");
				return;
			}
			Elements trTags = boxDetail.select("tr");
			Element title, content;
			for (Element trTag : trTags) {
				title = trTag.selectFirst("th");
				content = trTag.selectFirst("td");
				if (title == null || content == null)
					continue;
				if (checkIsRequiredData(normalizeString(title.text())))
					System.out.println("    " + normalizeString(title.text() + ": " + content.text()));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//a fuction to get only needed data
	static boolean checkIsRequiredData(String s) {
		List<String> requiredData = new ArrayList<>();
		requiredData.add("Sinh");
		requiredData.add("Mat");
		requiredData.add("Than phu");
		requiredData.add("Than mau");
		requiredData.add("Trieu dai");
		for (String data : requiredData) {
			if (s.equals(data))
				return true;
		}
		return false;
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
			List<King> kings = gson.fromJson(json, new TypeToken<List<King>>(){}.getType());

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
			System.out.println("    " + king.getMieuHieu());
			System.out.println("    " + king.getThuyHieu());
			System.out.println("    " + king.getNienHieu());
			System.out.println("    " + king.getTenHuy());
			System.out.println("    " + king.getTheThu());
			System.out.println("    " + king.getNamTriVi() + "\n");
		}
	}

	// put the string in this function if console cannot print Vietnamese font
	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace("—", "-");
	}
}
