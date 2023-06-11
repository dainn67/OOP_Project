package figures_scraper;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import objects.Figure;
import helper_package.EncodeDecode;

public class NguoiKeSuScraper {
	static int figureCount = 0;
	static int homeCount = 0;
	static int dynastyCount = 0;
	static int parentCount = 0;
	static int bitrhDeathCount = 0;

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

	public static List<Object> figures = new ArrayList<Object>();
	static String[] figureAttributes = new String[9];

	public static void main(String[] args) {

		String url;
		Document doc;
		while (true) {
			if (urlCounter > 1450)
				break;
			if (urlCounter == 0)
				url = "https://nguoikesu.com/nhan-vat";
			else
				url = "https://nguoikesu.com/nhan-vat?start=" + urlCounter;

			try {
				doc = Jsoup.connect(url).get();
				getFiguresPage(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// encode the list to Json, write to a file and decode back to check
		EncodeDecode.encodeToFile(figures);
		
//		List<Figure> newList = EncodeDecode.decodedFigureList();
//		prtList(newList);
	}

	static void getFiguresPage(Document doc) {
		Elements figures = doc.select("div.com-content-category-blog__item");

		System.out.println("								PAGE: " + urlCounter);

		// loop each figures in a page
		for (Element figure : figures) {
			Element figureNameElement = figure.selectFirst("h2");
			String _figureDetail = figure.select("a").attr("href");

			String figureName = normalizeString(figureNameElement.text());

			if (figureName.startsWith("nha") || figureName.startsWith("Nha"))
				continue;
			else {
				figureAttributes[0] = figureName;
				figureCount++;
			}

			accessDetail(_figureDetail);

			Figure myFigure = new Figure(figureAttributes[0], figureAttributes[1], figureAttributes[2],
					figureAttributes[3], figureAttributes[4], figureAttributes[5], figureAttributes[6],
					figureAttributes[7], figureAttributes[8]);

			NguoiKeSuScraper.figures.add(myFigure);

//			System.out.println("			Nam sinh/mat: " + bitrhDeathCount + "/" + figureCount);
//			System.out.println("			Que quan: " + homeCount + "/" + figureCount);
//			System.out.println("			Trieu dai: " + dynastyCount + "/" + figureCount);
//			System.out.println("			Cha me: " + parentCount + "/" + figureCount);

		}
		urlCounter += 5;
	}

	private static void accessDetail(String _figureDetail) {

		// set initial attributes to unknown
		for (int i = 1; i < figureAttributes.length; i++)
			figureAttributes[i] = null;

		String url = "https://nguoikesu.com" + _figureDetail;
		try {
			Document doc = Jsoup.connect(url).get();

			// get description inside detail page for data scrapping
			StringBuilder desc = new StringBuilder();
			Elements pTags = doc.select("p");
			for (Element pTag : pTags)
				desc.append(normalizeString(pTag.text()) + " ");

			// assign the name first
			figureAttributes[8] = desc.toString();

			// find tr tags to search whether there's a table or not
			Elements rows = doc.select("tr");

			// get data from table
			if (rows != null)
				getDataFromTable(rows);

			// get the remaining unknown data from description
			if (pTags.size() != 0) {
				getDataFromParagraph(pTags.get(0), desc.toString());
			} else {
				figureCount--;
			}

		} catch (IOException e) {
			return;
		}
	}

	static void getDataFromTable(Elements rows) {

		// debug
		Boolean hasMother = false, hasFather = false;
		Boolean hasBirthYear = false, hasDeathYear = false;

		for (Element row : rows) {
			// each row has 2 columns, title and content
			Element title = row.selectFirst("th");
			Element subDivContent = row.selectFirst("td");

			if (title == null || subDivContent == null)
				continue;

			if (title != null && normalizeString(title.text()).equals("Dia ly"))
				return;

			String content = subDivContent != null ? normalizeString(subDivContent.text()) : null;

			switch (normalizeString(title.text())) {
			case "Ten day du": {
				figureAttributes[1] = content;
				break;
			}
			case "Ten khac": {
				figureAttributes[1] = content;
				break;
			}
			case "Biet danh": {
				figureAttributes[1] = content;
				break;
			}
			case "Sinh": {
				figureAttributes[2] = content;
				hasBirthYear = true;
				getHome(content);
				break;
			}
			case "Mat": {
				figureAttributes[3] = content;
				hasDeathYear = true;
				break;
			}
			case "Than phu": {
				figureAttributes[4] = content;
				hasFather = true;
				break;
			}
			case "Than mau": {
				figureAttributes[5] = content;
				hasMother = true;
				break;
			}
			case "Tai vi": {

				break;
			}
			default:
				break;
			}

			// debug
			if (hasBirthYear || hasDeathYear)
				bitrhDeathCount++;
			if (hasFather || hasMother)
				parentCount++;

			hasBirthYear = false;
			hasDeathYear = false;
			hasFather = false;
			hasMother = false;
		}
	}

	static void getDataFromParagraph(Element div, String desc) {

		// 1st bold text is real name -> 2nd bold text is otherName
		Elements otherNameElements = div.select("b");
		String otherName = null;

		if (otherNameElements.size() >= 2 && otherNameElements.get(1) != null)
			otherName = normalizeString(otherNameElements.get(1).text());

		figureAttributes[1] = otherName;

		// get dynasty and remaining data if not found in table
		getDynasty(desc);
		if (figureAttributes[2] == null && figureAttributes[3] == null)
			getYears(desc);
		if (figureAttributes[4] == null && figureAttributes[5] == null)
			getParent(desc);
		if (figureAttributes[7] == null)
			getHome(desc);
	}

	static boolean getHome(String text) {

		String[] words = text.split("\\s+");
		StringBuilder cityNameBuilder = new StringBuilder();
		for (String word : words) {
			cityNameBuilder.append(word).append(" ");

			for (String city : cities) {
				if (cityNameBuilder.toString().contains(city)) {
					figureAttributes[7] = city;
					homeCount++;
					return true;
				}
			}
		}

		if (homePattern(text, "lang\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)"))
			return true;
		if (homePattern(text, "tai\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)"))
			return true;

		return false;
	}

	static Boolean homePattern(String text, String patternString) {
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			figureAttributes[7] = matcher.group(1);
			homeCount++;
			return true;
		}

		return false;
	}

	static void getDynasty(String text) {
		if (text.contains("vua Le chua Trinh")) {
			dynastyCount++;
//			System.out.println("    THOI: Vua Le chua Trinh");
			figureAttributes[6] = "Vua Le chua Trinh";
			return;
		}

		Pattern pattern = Pattern.compile("nha\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		Matcher matcher = pattern.matcher(text);

		String dynasty, dynastyGroup;
		if (matcher.find()) {
			dynastyGroup = matcher.group(0);
			dynasty = matcher.group(1);

			// check if "Le so"
			int index = text.indexOf(dynastyGroup);
			String afterSubstring = text.substring(index + dynastyGroup.length());
			if (afterSubstring.trim().startsWith("so")) {
				dynasty += " so";
			}

			dynastyCount++;
			figureAttributes[6] = dynasty;
			return;
		}

		if (dynastyPattern(text, "trieu\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)"))
			return;
		if (dynastyPattern(text, "trieu dai\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)"))
			return;
		if (dynastyPattern(text, "Nha\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)"))
			return;
	}

	static Boolean dynastyPattern(String text, String patternString) {
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			dynastyCount++;
			figureAttributes[6] = matcher.group(1);
			return true;
		} else {
			return false;
		}
	}

	static void getParent(String text) {
		Pattern pattern = Pattern.compile("la con trai cua\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		Matcher matcher = pattern.matcher(text);

		String father;
		if (matcher.find()) {
			father = matcher.group(1);

			parentCount++;
			figureAttributes[4] = father;
			return;
		}
	}

	static void getYears(String str) {
		int birthYear = 0, deathYear = 0;
		String birthYearString, deathYearString;

		Pattern pattern;
		Matcher matcher;

		// check the format: 1990-1991
		pattern = Pattern.compile("(\\d{4})-(\\d{4})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			birthYearString = matcher.group(1);
			deathYearString = matcher.group(2);

			birthYear = Integer.parseInt(birthYearString);
			deathYear = Integer.parseInt(deathYearString);

			figureAttributes[2] = "" + birthYear;
			figureAttributes[3] = "" + deathYear;

			bitrhDeathCount++;
			return;
		}

		pattern = Pattern.compile("\\((.*?)\\)");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			String matchedString = matcher.group(1);
			String[] parts = matchedString.split("-");

			if (parts.length == 2) {
				figureAttributes[2] = parts[0];
				figureAttributes[3] = parts[1];

				bitrhDeathCount++;
				return;
			}
		}

		// check the format: sinh nam 1990, mat nam 1990
		pattern = Pattern.compile("(Sinh|sinh) nam (\\d{4})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			String yearString = matcher.group(2);
			int year = Integer.parseInt(yearString);

			figureAttributes[2] = "" + year;
			bitrhDeathCount++;

			return;
		}

		if (str.contains("sinh ngay")) {
			pattern = Pattern.compile("nam (\\d{4})");
			matcher = pattern.matcher(str);
			if (matcher.find()) {
				String year = matcher.group(1);

				figureAttributes[2] = "" + year;
				bitrhDeathCount++;

				return;
			}
		}

		pattern = Pattern.compile("(Mat|mat) nam (\\d{4})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			String yearString = matcher.group(2);
			int year = Integer.parseInt(yearString);

			figureAttributes[3] = "" + year;
			bitrhDeathCount++;

			return;
		}

		// check the format: 769 - 860
		birthYear = 0;
		deathYear = 0;
		pattern = Pattern.compile("(\\d{3})-(\\d{3})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			birthYearString = matcher.group(1);
			deathYearString = matcher.group(2);

			birthYear = Integer.parseInt(birthYearString);
			deathYear = Integer.parseInt(deathYearString);

			figureAttributes[2] = "" + birthYear;
			figureAttributes[3] = "" + deathYear;

			bitrhDeathCount++;
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

			figureAttributes[2] = "" + birthYear;
			figureAttributes[3] = "" + deathYear;

			bitrhDeathCount++;
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

			figureAttributes[2] = "" + birthYear;
			figureAttributes[3] = "" + deathYear;

			bitrhDeathCount++;
			return;
		}
	}

	static void prtList(List<Figure> list) {
		for (Figure figure : list) {
			prtAttributes(figure);
		}
	}

	static void prtAttributes(Figure myFigure) {
		System.out.println(myFigure.getName());
		System.out.println("	Ten khac: " + myFigure.getOtherName());
		System.out.println("	Sinh: " + myFigure.getBornYear());
		System.out.println("	Mat: " + myFigure.getDeathYear());
		System.out.println("	Cha: " + myFigure.getFather());
		System.out.println("	Me: " + myFigure.getMother());
		System.out.println("	Trieu dai: " + myFigure.getDynasty());
		System.out.println("	Que quan: " + myFigure.getHome());
		System.out.println("	Mo ta: " + myFigure.getDesc());
	}

	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-");
	}
}