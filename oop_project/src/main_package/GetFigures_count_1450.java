package main_package;

import java.io.FileWriter;

import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileReader;
import java.io.BufferedReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

enum ContentType {
	TABLE, PARAGRAPH
}

public class NguoiKeSuScraper {
	public static int count = 0;
	public static int actualCount = 0;
	static int urlCounter = 0;

	public static void main(String[] args) {
		try {

			String url;
			Document doc;

			while (true) {
//				if (urlCounter > 1450) break;
				if (urlCounter < 175) {
					urlCounter += 5;
					continue;
				} else if (urlCounter > 200)
					break;

				if (urlCounter == 0)
					url = "https://nguoikesu.com/nhan-vat";
				else
					url = "https://nguoikesu.com/nhan-vat?start=" + urlCounter;
				doc = Jsoup.connect(url).get();
				getFigures(doc);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\nCount: " + count);
	}

	static void getFigures(Document doc) {
		Elements divs = doc.select("div.com-content-category-blog__item");

		for (Element div : divs) {
			Element figureNameElement = div.selectFirst("h2");
			String _figureDetail = div.select("a").attr("href");

			String figureName = figureNameElement.text();

			System.out.println(normalizeString(figureName));

			accessDetail(_figureDetail);

			count++;
			actualCount++;

		}
		urlCounter += 5;
	}

	private static void accessDetail(String _figureDetail) {
		ContentType type = ContentType.PARAGRAPH;

		String url = "https://nguoikesu.com" + _figureDetail;
		try {
			Document doc = Jsoup.connect(url).get();
			Elements rows = doc.select("tr");

			if (rows != null) {
				for (Element row : rows) {
					Element subDivTitle = row.selectFirst("th");
					Element subDivContent = row.selectFirst("td");

					if (subDivTitle != null && normalizeString(subDivTitle.text()).equals("Dia ly")) {
						System.out.println("	DIA DIEM");
						return;
					}

					String content = "";
					if (subDivContent != null)
						content = normalizeString(subDivContent.text());
					else
						continue;

					if (subDivTitle == null || subDivContent == null)
						continue;

					switch (normalizeString(subDivTitle.text())) {
					case "Ten day du": {
						type = ContentType.TABLE;
						System.out.println("	TEN KHAC: " + content);
						break;
					}
					case "Ten khac": {
						type = ContentType.TABLE;
						System.out.println("	TEN KHAC: " + content);
						break;
					}
					case "Biet danh": {
						type = ContentType.TABLE;
						System.out.println("	TEN KHAC: " + content);
						break;
					}
					case "Sinh": {
						type = ContentType.TABLE;
						System.out.println("	SINH: " + content);
						break;
					}
					case "Mat": {
						type = ContentType.TABLE;
						System.out.println("	MAT: " + content);
						break;
					}
					case "Than phu": {
						type = ContentType.TABLE;
						System.out.println("	THAN PHU: " + content);
						break;
					}
					case "Than mau": {
						type = ContentType.TABLE;
						System.out.println("	THAN MAU: " + content);
						break;
					}
					default:
						break;
					}
				}

				// if there are no content table
				if (type.equals(ContentType.PARAGRAPH)) {

					count--;
//					int i=0;
//					if(i == 0) return;

					Element div = doc.selectFirst("div.com-content-article__body p");
					
					if(div != null) getDataFromParagraph(div);
					else System.out.println("	NO PARAGRAPH, NO NOTHING ...");
					
				}
			} else {
				System.out.println("THERE ARE NO TR TAGS");
			}
		} catch (IOException e) {
			System.out.println("ACCESSING DETAILS ERROR");
			e.printStackTrace();
		}
	}
	
	static void getDataFromParagraph(Element div) {
		String descContent = normalizeString(div.text().toString());
		if(descContent.contains("co the la")) {
			System.out.println("	DIA DIEM");
			return;
		}
		
		Elements otherNameElements = div.select("b");
		String otherName = "Khong co";

		//1st bold text is real name -> 2nd bold text is otherName
		if (otherNameElements.size() >= 2 && otherNameElements.get(1) != null)
			otherName = normalizeString(otherNameElements.get(1).text());
		
		System.out.println("	TEN KHAC: " + otherName);
		
		//get other data
		getYears(descContent);
		getHome(descContent);
//		getParents(descContent);
	}
	
	static void getHome(String text) {
		Pattern pattern = Pattern.compile("nha\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String name = matcher.group(1);
            System.out.println("	Thoi: " + name);
            return;
        }
        
        pattern = Pattern.compile("trieu\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
        matcher = pattern.matcher(text);

        if (matcher.find()) {
            String name = matcher.group(1);
            System.out.println("	Thoi: " + name);
            return;
        }
        
        System.out.println("	Thoi: Khong ro");

	}

	static void getYears(String str) {
		int birthYear = 0, deathYear = 0;
		String birthYearString, deathYearString;
		
		// check the format: sinh nam 1990, mat nam 1990
		Pattern pattern = Pattern.compile("(sinh|mat) nam (\\d{4})");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			String yearString = matcher.group(2);
			int year = Integer.parseInt(yearString);
			System.out.println("	Sinh|Mat nam (3):" + year);
			return;
		}

		// check the format: 1990-1991
		pattern = Pattern.compile("(\\d{4})-(\\d{4})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			birthYearString = matcher.group(1);
			deathYearString = matcher.group(2);

			birthYear = Integer.parseInt(birthYearString);
			deathYear = Integer.parseInt(deathYearString);

			System.out.println("	SINH (1): " + birthYear);
			System.out.println("	MAT (1): " + deathYear);
			return;
		}

		pattern = Pattern.compile("(\\d{3})-(\\d{3})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			birthYearString = matcher.group(1);
			deathYearString = matcher.group(2);

			birthYear = Integer.parseInt(birthYearString);
			deathYear = Integer.parseInt(deathYearString);

			System.out.println("	SINH (2): " + birthYear);
			System.out.println("	MAT (2): " + deathYear);
			return;
		}

		// check the format: sinh nam 1990, mat nam 1990
		pattern = Pattern.compile("(sinh|mat) nam (\\d{4})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			String yearString = matcher.group(2);
			int year = Integer.parseInt(yearString);
			System.out.println("	Sinh|Mat nam (3):" + year);
			return;
		}

		// Check the format: "year-?" or "?-year"
		// First, check the 4 digit years
		pattern = Pattern.compile("(\\d{1,4}|\\?)-?(\\d{1,4}|\\?)?");
		matcher = pattern.matcher(str);
		while (matcher.find()) {
			String match = matcher.group();
			if (match.contains("?")) {
				match = match.replaceAll("\\?", "0");
			}
			if (match.contains("-")) {
				String[] years = match.split("-");
				if (years[0] != null)
					birthYear = Integer.parseInt(years[0]);
				if (years[1] != null)
					deathYear = Integer.parseInt(years[1]);
			} else {
				birthYear = Integer.parseInt(match);
			}
		}

		if (birthYear != 0 || deathYear != 0) {
			if (deathYear < 100)
				deathYear = 0;
			if (birthYear < 100)
				birthYear = 0;
			System.out.println("	SINH (4): " + birthYear);
			System.out.println("	MAT (4): " + deathYear);
			return;
		}

		// Then check the 3 digit years
		pattern = Pattern.compile("(\\d{1,3}|\\?)-?(\\d{1,3}|\\?)?");
		matcher = pattern.matcher(str);
		while (matcher.find()) {
			String match = matcher.group();
			if (match.contains("?")) {
				match = match.replaceAll("\\?", "0");
			}
			if (match.contains("-")) {
				String[] years = match.split("-");
				if (years[0] != null)
					birthYear = Integer.parseInt(years[0]);
				if (years[1] != null)
					deathYear = Integer.parseInt(years[1]);
			} else {
				birthYear = Integer.parseInt(match);
			}
		}

		if (birthYear != 0 || deathYear != 0) {
			System.out.println("	SINH (5): " + birthYear);
			System.out.println("	MAT(5): " + deathYear);
			return;
		}

		System.out.println("	SINH (6): " + birthYear);
		System.out.println("	MAT(6): " + deathYear);

	}

	static void encodeDynasty() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		String json = gson.toJson(toSaveToFile);

		try (FileWriter writer = new FileWriter("data.json")) {
//			writer.write(json);
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
//			resDynastys = gson.fromJson(json, String[].class);

			// Print the decoded array to the console
//			for (String item : resDynastys) {
//				if (item != null) {
//					System.out.println(item);
//				}
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-");
	}
}
