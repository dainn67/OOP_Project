package main_package;

import java.io.FileWriter;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.ExemptionMechanism;

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
	public static int figureCount = 0;
	public static int homeCount = 0;
	public static int dynastyCount = 0;
	public static int parentCount = 0;
	public static int bitrhDeathCount = 0;
	
	static int urlCounter = 0;
	static List<String> cities = Arrays.asList("An Giang", "Ba Ria-Vung Tau", "Bac Lieu", "Bac Giang", "Bac Kan",
			"Bac Ninh", "Ben Tre", "Binh Duong", "Binh Dinh", "Binh Phuoc", "Binh Thuan", "Ca Mau", "Cao Bang",
			"Can Tho", "Da Nang", "Dak Lak", "Dak Nong", "Dien Bien", "Dong Nai", "Dong Thap", "Gia Lai", "Ha Giang",
			"Ha Nam", "Ha Noi", "Ha Tinh", "Hai Duong", "Hai Phong", "Hau Giang", "Hoa Binh", "Hue", "Hung Yen",
			"Ho Chi Minh", "Khanh Hoa", "Kien Giang", "Kon Tum", "Lai Chau", "Lang Son", "Lao Cai", "Lam Dong",
			"Long An", "Nam Dinh", "Nghe An", "Ninh Binh", "Ninh Thuan", "Phu Tho", "Phu Yen", "Quang Binh",
			"Quang Nam", "Quang Ngai", "Quang Ninh", "Quang Tri", "Soc Trang", "Son La", "Tay Ninh", "Thai Binh",
			"Thai Nguyen", "Thanh Hoa", "Thua Thien Hue", "Tien Giang", "Tra Vinh", "Tuyen Quang", "Vinh Long",
			"Vinh Phuc", "Yen Bai", "Thua Thien", "Thang Long");

	public static void main(String[] args) {

		try {
			String url;
			Document doc;
			while (true) {
				if (urlCounter > 1450)
					break;
///*
				if (urlCounter < 935) {
					urlCounter += 5;
					continue;
				}else if(urlCounter > 960) break;
//*/
				if (urlCounter == 0)
					url = "https://nguoikesu.com/nhan-vat";
				else
					url = "https://nguoikesu.com/nhan-vat?start=" + urlCounter;
				doc = Jsoup.connect(url).get();
				getFiguresPage(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void getFiguresPage(Document doc) {
		Elements divs = doc.select("div.com-content-category-blog__item");
		
		System.out.println("								" + urlCounter);

		//loop each figures in a page
		for (Element div : divs) {
			Element figureNameElement = div.selectFirst("h2");
			String _figureDetail = div.select("a").attr("href");

			String figureName = normalizeString(figureNameElement.text());

			if(figureName.startsWith("nha") || figureName.startsWith("Nha")) {				
				System.out.println("TRIEU DAI: " + figureName);
				continue;
			}else if(figureName.equals("Nha Trang")) {
				System.out.println("DIA DIEM: " + figureName);
				continue;
			}
			else {				
				//print figure's name
				System.out.println(figureName);
			}

			accessDetail(_figureDetail);
			System.out.println("			Nam sinh/mat: " + bitrhDeathCount + "/" + figureCount);
			System.out.println("			Que quan: " + homeCount + "/" + figureCount);
			System.out.println("			Trieu dai: " + dynastyCount + "/" + figureCount);
			System.out.println("			Cha me: " + parentCount + "/" + figureCount);

		}
		urlCounter += 5;
	}

	private static void accessDetail(String _figureDetail) {
		ContentType type = ContentType.PARAGRAPH;
		Boolean alreadyGetHome = false;
		Boolean hasParents = false;
		Boolean hasBirthOrDeathYear = false;

		String url = "https://nguoikesu.com" + _figureDetail;
		try {
			Document doc = Jsoup.connect(url).get();
			
			//get description inside detail page
			StringBuilder desc = new StringBuilder();
			Elements pTags = doc.select("p");
			for (Element pTag : pTags) 
				desc.append(" " + normalizeString(pTag.text()));

			if(desc.toString().contains("la thuy hieu")) {
				System.out.println("	KHONG PHAI NHAN VAT");
				return;
			}
			
			//find tr tags to search whether there's a table or not
			Elements rows = doc.select("tr");

			if (rows != null) {
				
				//each row has 2 columns, title and content
				for (Element row : rows) {
					Element title = row.selectFirst("th");
					Element subDivContent = row.selectFirst("td");

					if (title != null && normalizeString(title.text()).equals("Dia ly")) {
						System.out.println("	DIA DIEM");
						return;
					}

					String content = "";
					if (subDivContent != null)
						content = normalizeString(subDivContent.text());
					else continue;

					if (title == null || subDivContent == null)
						continue;

					switch (normalizeString(title.text())) {
					case "Ten day du": {
						type = ContentType.TABLE;
						System.out.println("	(T)TEN KHAC: " + content);
						break;
					}
					case "Ten khac": {
						type = ContentType.TABLE;
						System.out.println("	(T)TEN KHAC: " + content);
						break;
					}
					case "Biet danh": {
						type = ContentType.TABLE;
						System.out.println("	(T)TEN KHAC: " + content);
						break;
					}
					case "Sinh": {
						type = ContentType.TABLE;
						System.out.println("	(T)SINH: " + content);
						hasBirthOrDeathYear = true;
						if (getHome(content))
							alreadyGetHome = true;
						break;
					}
					case "Mat": {
						type = ContentType.TABLE;
						System.out.println("	(T)MAT: " + content);
						hasBirthOrDeathYear = true;
						break;
					}
					case "Than phu": {
						type = ContentType.TABLE;
						System.out.println("	(T)THAN PHU: " + content);
						hasParents = true;
						break;
					}
					case "Than mau": {
						type = ContentType.TABLE;
						System.out.println("	(T)THAN MAU: " + content);
						hasParents = true;
						break;
					}
					case "Tai vi": {
						type = ContentType.TABLE;
						System.out.println("	(T)TAI VI: " + content);
						break;
					}
					default:
						break;
					}
				}

				// if data is not a place then increment
				figureCount++;
				if(hasParents) parentCount++;
				if(hasBirthOrDeathYear) bitrhDeathCount++;

				//get belonged dynasty
				getDynasty(desc.toString());
				
				//get parents
				getParent(desc.toString());

				//get home town if not found in "SINH"
				if (!alreadyGetHome) {
					getHome(desc.toString());
				}

				// if there are no content table
				if (type.equals(ContentType.PARAGRAPH)) {

					Element div = doc.selectFirst("div.com-content-article__body p");

					if (div != null)
						getDataFromParagraph(div, alreadyGetHome, desc.toString());
					else
						System.out.println("	NO PARAGRAPH, NO NOTHING ...");

				}
			} else {
				System.out.println("THERE ARE NO TR TAGS");
			}
		} catch (IOException e) {
			System.out.println("NOT A FIGURE");
			return;
		}
	}

	static void getDataFromParagraph(Element div, boolean alreadyGetHome, String desc) {
		System.out.println("\n	(SEARCH IN PARAGRAPH)");
		
		//check if it's a place or not
		String descContent = normalizeString(div.text().toString());
		if (descContent.contains("co the la")) {
			System.out.println("	DIA DIEM");
			return;
		}

		// 1st bold text is real name -> 2nd bold text is otherName
		Elements otherNameElements = div.select("b");
		String otherName = "Khong co";

		if (otherNameElements.size() >= 2 && otherNameElements.get(1) != null)
			otherName = normalizeString(otherNameElements.get(1).text());

		System.out.println("	TEN KHAC: " + otherName);

		getYears(descContent);
		getDynasty(descContent);
	}

	static boolean getHome(String text) {
		
		String[] words = text.split("\\s+");
		StringBuilder cityNameBuilder = new StringBuilder();
		for(String word: words) {
			cityNameBuilder.append(word 	).append(" ");
			
			for(String city: cities) {
				if(cityNameBuilder.toString().contains(city)) {					
					System.out.println("	QUE QUAN: " + city);
					homeCount++;
					return true;
				}
			}
		}
		
		
		Pattern pattern = Pattern.compile("lang\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		Matcher matcher = pattern.matcher(text);

		String name;
		if (matcher.find()) {
			name = matcher.group(1);

			System.out.println("	QUE QUAN (lang)" + name);
			homeCount++;
			return true;
		}

		pattern = Pattern.compile("tai\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		matcher = pattern.matcher(text);

		if (matcher.find()) {
			name = matcher.group(1);
			System.out.println("	QUE QUAN (tai)" + name);
			homeCount++;
			return true;
		}

		System.out.println("	QUE QUAN: Khong ro");
		return false;
	}

	static void getDynasty(String text) {
		if (text.contains("vua Le chua Trinh")) {
			dynastyCount++;
			System.out.println("    THOI: Vua Le chua Trinh");
			return;
		}

		Pattern pattern = Pattern.compile("nha\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		Matcher matcher = pattern.matcher(text);

		String name, nameGroup;
		if (matcher.find()) {
			nameGroup = matcher.group(0);
			name = matcher.group(1);

			// check if "Le so"
			int index = text.indexOf(nameGroup);
			String afterSubstring = text.substring(index + nameGroup.length());
			if (afterSubstring.trim().startsWith("so")) {
				name += " so";
			}

			dynastyCount++;
			System.out.println("	THOI (nha): " + name);
			return;
		}

		pattern = Pattern.compile("trieu\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		matcher = pattern.matcher(text);

		if (matcher.find()) {
			name = matcher.group(1);
			System.out.println("	THOI (trieu): " + name);
			dynastyCount++;
			return;
		}
		
		pattern = Pattern.compile("trieu dai\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		matcher = pattern.matcher(text);

		if (matcher.find()) {
			name = matcher.group(1);
			System.out.println("	THOI (trieu dai): " + name);
			dynastyCount++;
			return;
		}

		pattern = Pattern.compile("Nha\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		matcher = pattern.matcher(text);

		if (matcher.find()) {
			name = matcher.group(1);
			if(!(name.equals("Y") || name.equals("nho") || name.equals("y"))) {
				System.out.println("	THOI (Nha): " + name);
				dynastyCount++;
				return;
			}
		}

		System.out.println("	THOI: Khong ro");

	}

	
	static void getParent(String text) {
		Pattern pattern = Pattern.compile("la con trai cua\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		Matcher matcher = pattern.matcher(text);

		String name;
		if (matcher.find()) {
			name = matcher.group(1);


			dynastyCount++;
			System.out.println("	CHA ME: " + name);
			return;
		}
		System.out.println("	CHA ME: Khong ro");
	}
	
	static void getYears(String str) {
		int birthYear = 0, deathYear = 0;
		String birthYearString, deathYearString;

		// check the format: sinh nam 1990, mat nam 1990
		Pattern pattern = Pattern.compile("(Sinh|sinh) nam (\\d{4})");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			String yearString = matcher.group(2);
			int year = Integer.parseInt(yearString);
			System.out.println("	SINH (Sinh nam): " + year);
			return;
		}
		
		if(str.contains("sinh ngay")) {			
			pattern = Pattern.compile("nam (\\d{4})");
			matcher = pattern.matcher(str);
			if (matcher.find()) {
	            String year = matcher.group(1);
	            System.out.println("	SINH (Sinh ngay): " + year);
	            return;
	        }
		}
		
		
		pattern = Pattern.compile("(Mat|mat) nam (\\d{4})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			String yearString = matcher.group(2);
			int year = Integer.parseInt(yearString);
			System.out.println("	MAT (Mat nam): " + year);
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

		//check the format: 769 - 860
		birthYear = 0;
		deathYear = 0;
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

		// Check the format: "year-?" or "?-year"
		// First, check the 4 digit years
		birthYear = 0;
		deathYear = 0;
		pattern = Pattern.compile("(\\d{1,4}|\\?)-?(\\d{1,4}|\\?)?");
		matcher = pattern.matcher(str);
		while (matcher.find()) {
			String match = matcher.group();
			if (match.contains("?")) {
				match = match.replaceAll("\\?", "0");
			}
			if (match.contains("-")) {
				String[] years = match.split("-");
				if (years.length >= 2) {
					if (years[0] != null)
						birthYear = Integer.parseInt(years[0]);
					if (years[1] != null)
						deathYear = Integer.parseInt(years[1]);
				}
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
		birthYear = 0;
		deathYear = 0;
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