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

enum ContentType{
	TABLE, PARAGRAPH
}

public class CharacterScraper {
	public static int count = 0;
	public static int realCount = 0;

	static int urlCounter = 0;

	static String[] toSaveToFile = new String[155];
	static String[] resDynastys = new String[155];

	public static void main(String[] args) {
		// DEBUGGING
		debug();

		// RUN REAL MAIN FUNCTION
//		runMain();

//			encodeDynasty();
//			decodeDynasty();

		System.out.println("\nCount: " + count);
	}

	static void debug() {
		try {
			while (true) {
				if (urlCounter > 30)
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
//			accessDetail("/nhan-vat/duong-tam-kha");
//			accessDetail("/nhan-vat/duong-quy-phi");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void runMain() {
		try {
//			Document document = Jsoup.connect("https://nguoikesu.com/nhan-vat").get();
//			getFigures(document);
//			System.out.println(normalizeString(document.toString()));

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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void getFigures(Document doc) {
		Elements divs = doc.select("div.com-content-category-blog__item");

		for (Element div : divs) {
			Elements _figureName = div.select("h2");
//			Elements _figureDesc = div.select("p");
			String _figureDetail = div.select("a").attr("href");

			//get name
			String nameContentString = "";
			for (Element name : _figureName) {
				nameContentString += name.text();
			}

			//get outside description
//			String descContentString = "";
//			for (Element desc : _figureDesc) {
//				descContentString += desc.text() + "";
//			}
//			descContentString += "\n";

			System.out.println(normalizeString(nameContentString));
//			System.out.println(normalizeString(descContentString));

			accessDetail(_figureDetail);

			count++;

		}
		urlCounter += 5;
	}

	private static void accessDetail(String _figureDetail) {
		ContentType type = ContentType.PARAGRAPH;
		
		String url = "https://nguoikesu.com" + _figureDetail;
		try {
//			System.out.println(url);
			Document doc = Jsoup.connect(url).get();
			Elements divs = doc.select("tr");
			
			if(divs != null) {
				for(Element div : divs) {
					Element subDivTitle = div.selectFirst("th");
					Element subDivContent = div.selectFirst("td");
					if(subDivTitle == null || subDivContent == null) continue;
					
					switch (normalizeString(subDivTitle.text())) {
						case "Ten day du": {
							type = ContentType.TABLE;
							System.out.println("TEN KHAC: " + normalizeString(subDivContent.text().toString()) + "\n");
							break;
						}
						case "Ten khac": {
							type = ContentType.TABLE;
							System.out.println("TEN KHAC: " + normalizeString(subDivContent.text().toString()) + "\n");
							break;
						}
						case "Biet danh": {
							type = ContentType.TABLE;
							System.out.println("TEN KHAC: " + normalizeString(subDivContent.text().toString()) + "\n");
							break;
						}
						case "Sinh": {
							type = ContentType.TABLE;
							System.out.println("SINH: " + normalizeString(subDivContent.text().toString()) + "\n");
							break;
						}
						case "Mat": {
							type = ContentType.TABLE;
							System.out.println("MAT: " + normalizeString(subDivContent.text().toString()) + "\n");
						}
						default: break;
					}
				}
				if(type.equals(ContentType.PARAGRAPH)) {
					Element div = doc.selectFirst("div.com-content-article__body p");
					Elements otherNameElements = div.select("b");
					String otherName = "No other name";
					
					int i=0;
					for(Element name: otherNameElements) {
						if(i == 0) {
							i++;
							continue;
						}
						otherName = normalizeString(name.text());
						break;
					}
					System.out.println("TEN KHAC: " + normalizeString(otherName));
					String descContent = normalizeString(div.text().toString());
					getYears(descContent);
				}
			}else {
				Element div = doc.selectFirst("div.com-content-article__body p");
				System.out.println(div);
			}
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}

	static void getYears(String str) {

		// check the format: 1990-1991
		Pattern pattern = Pattern.compile("(\\d{4})-(\\d{4})");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			String birthYearString = matcher.group(1);
			String deathYearString = matcher.group(2);
			int birthYear1 = Integer.parseInt(birthYearString);
			int deathYear1 = Integer.parseInt(deathYearString);
			System.out.println("SINH (1): " + birthYear1);
			System.out.println("MAT (1): " + deathYear1 + "}\n\n\n");
			return;
		}

		pattern = Pattern.compile("(\\d{3})-(\\d{3})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			String birthYearString = matcher.group(1);
			String deathYearString = matcher.group(2);
			int birthYear1 = Integer.parseInt(birthYearString);
			int deathYear1 = Integer.parseInt(deathYearString);
			System.out.println("SINH (2): " + birthYear1);
			System.out.println("MAT (2): " + deathYear1 + "}\n\n\n");
			return;
		}
//		System.out.println("No format year-year");

		// check the format: sinh nam 1990, mat nam 1990
		pattern = Pattern.compile("(sinh|mat) nam (\\d{4})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			String yearString = matcher.group(2);
			int year = Integer.parseInt(yearString);
			System.out.println("Sinh|Mat nam (3):" + year + "}\n\n\n");
			return;
		}
//		System.out.println("No format sinh|mat nam");

		// Check the format: "year-?" or "?-year"
		// Initialize variables to hold the birth and death year
		int birthYear = 0;
		int deathYear = 0;

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
				birthYear = Integer.parseInt(years[0]);
				deathYear = Integer.parseInt(years[1]);
			} else {
				birthYear = Integer.parseInt(match);
			}
		}

		// Print the birth and death year
		if (birthYear != 0 || deathYear != 0) {
			if(deathYear < 100) deathYear = 0;
			if(birthYear < 100) birthYear = 0;
			System.out.println("SINH (4): " + birthYear);
			System.out.println("MAT (4): " + deathYear + "}\n\n\n");
			return;
		}
//		System.out.println("No format ?-year, year-? 4 digits");

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
				birthYear = Integer.parseInt(years[0]);
				deathYear = Integer.parseInt(years[1]);
			} else {
				birthYear = Integer.parseInt(match);
			}
		}

		// Print the birth and death year
		if (birthYear != 0 || deathYear != 0) {
			System.out.println("SINH (5): " + birthYear);
			System.out.println("MAT(5): " + deathYear);
			return;
		}

//		System.out.println("No format ?-year, year-? 3 digits");
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

	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-");
	}
}
