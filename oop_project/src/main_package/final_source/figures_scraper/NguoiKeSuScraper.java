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
import helper_package.HelperFunctions;

public class NguoiKeSuScraper {

	static int urlCounter = 0;

	static List<String> cities = Arrays.asList("An Giang", "Bạc Liêu", "Bắc Giang", "Bắc Kạn",
			"Bắc Ninh", "Bến Tre", "Bình Dương", "Bình Định", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng",
			"Cần Thơ", "Đà Nẵng", "Dak Lak", "Dak Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang",
			"Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hoà Bình", "Huế", "Hưng Yên",
			"Hồ Chí Minh", "Khánh Hoà", "Kiên Giang", "Kon Tum", "Lai Châu", "Lạng Sơn", "Lào Cai", "Lâm Đồng",
			"Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình",
			"Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình",
			"Thái Nguyên", "Thanh Hoá", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long",
			"Vĩnh Phúc", "Yên Bái", "Thừa Thiên", "Thăng Long");

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
		EncodeDecode.encodeToFile(figures, "figures");
	}

	static void getFiguresPage(Document doc) {
		Elements figures = doc.select("div.com-content-category-blog__item");

		System.out.println("								PAGE: " + urlCounter);

		// loop each figures in a page
		for (Element figure : figures) {
			Element figureNameElement = figure.selectFirst("h2");
			String _figureDetail = figure.select("a").attr("href");

			String figureName = figureNameElement.text();

			if (figureName.startsWith("nhà") || figureName.startsWith("Nhà"))
				continue;
			else
				figureAttributes[0] = figureName;

			accessDetail(_figureDetail);

			Figure myFigure = new Figure(
					figureAttributes[0],
					figureAttributes[1],
					"" + HelperFunctions.parseYear(figureAttributes[2]),
					"" + HelperFunctions.parseYear(figureAttributes[3]),
					figureAttributes[4],
					figureAttributes[5],
					figureAttributes[6],
					figureAttributes[7],
					figureAttributes[8]);

			NguoiKeSuScraper.figures.add(myFigure);
			
			HelperFunctions.prtFigureAttributes(myFigure);
		}
		urlCounter += 5;
	}

	private static void accessDetail(String _figureDetail) {

		// set initial attributes to unknown
		for (int i = 1; i < figureAttributes.length; i++)
			figureAttributes[i] = "Không rõ";

		String url = "https://nguoikesu.com" + _figureDetail;
		try {
			Document doc = Jsoup.connect(url).get();

			// get description inside detail page for data scrapping
			StringBuilder desc = new StringBuilder();
			Elements pTags = doc.select("p");
			for (Element pTag : pTags)
				desc.append(pTag.text() + " ");

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
			}

		} catch (IOException e) {
			return;
		}
	}

	static void getDataFromTable(Elements rows) {

		for (Element row : rows) {
			// each row has 2 columns, title and content
			Element title = row.selectFirst("th");
			Element subDivContent = row.selectFirst("td");

			if (title == null || subDivContent == null)
				continue;

			if (title != null && title.text().equals("Địa lý"))
				return;

			String content = subDivContent != null ? subDivContent.text() : null;

			if(content != null) {				
				switch (title.text()) {
				case "Tên đầy đủ": {
					figureAttributes[1] = content;
					break;
				}
				case "Tên khác": {
					figureAttributes[1] = content;
					break;
				}
				case "Biệt danh": {
					figureAttributes[1] = content;
					break;
				}
				case "Sinh": {
					figureAttributes[2] = content;
					getHome(content);
					break;
				}
				case "Mất": {
					figureAttributes[3] = content;
					break;
				}
				case "Thân phụ": {
					figureAttributes[4] = content;
					break;
				}
				case "Thân mẫu": {
					figureAttributes[5] = content;
					break;
				}
				default:
					break;
				}
			}
		}
	}

	static void getDataFromParagraph(Element div, String desc) {

		// 1st bold text is real name -> 2nd bold text is otherName
		Elements otherNameElements = div.select("b");
		String otherName = "Không rõ";

		if (otherNameElements.size() >= 2 && otherNameElements.get(1) != null)
			otherName = otherNameElements.get(1).text();

		figureAttributes[1] = otherName;

		// get dynasty and remaining data if not found in table
//		getDynasty(desc);
		extractDynastyName(desc);
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
					return true;
				}
			}
		}

		if (homePattern(text, "làng\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)"))
			return true;
		if (homePattern(text, "tại\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)"))
			return true;

		return false;
	}

	static Boolean homePattern(String text, String patternString) {
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			figureAttributes[7] = matcher.group(1);
			return true;
		}

		return false;
	}
	
	static void extractDynastyName(String text) {
		if (text.contains("vua Lê chúa Trịnh")) {
			figureAttributes[6] = "Vua Lê chúa Trịnh";
			return;
		}
		
        String[] keywords = {"Nhà", "nhà", "triều", "triều đại"};

        String pattern = "(?i)(" + String.join("|", keywords) + ")\\s+([A-ZÀ-Ỹ][a-zà-ỹ]+)";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(text);

        if (matcher.find()) {
        	String dynastyName = matcher.group(2);
        	if(Character.isUpperCase(dynastyName.charAt(0))) {
        		figureAttributes[6] = dynastyName; 
        		return;
        	}
        }
    }

	static void getParent(String text) {
		Pattern pattern = Pattern.compile("là con trai của\\s+([A-Z][a-z]*(\\s+[A-Z][a-z]*)*)");
		Matcher matcher = pattern.matcher(text);

		String father;
		if (matcher.find()) {
			father = matcher.group(1);

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

				return;
			}
		}

		// check the format: sinh nam 1990, mat nam 1990
		pattern = Pattern.compile("(Sinh|sinh) năm (\\d{4})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			String yearString = matcher.group(2);
			int year = Integer.parseInt(yearString);

			figureAttributes[2] = "" + year;

			return;
		}

		if (str.contains("sinh ngày")) {
			pattern = Pattern.compile("năm (\\d{4})");
			matcher = pattern.matcher(str);
			if (matcher.find()) {
				String year = matcher.group(1);

				figureAttributes[2] = "" + year;

				return;
			}
		}

		pattern = Pattern.compile("(Mất|mất) năm (\\d{4})");
		matcher = pattern.matcher(str);
		if (matcher.find()) {
			String yearString = matcher.group(2);
			int year = Integer.parseInt(yearString);

			figureAttributes[3] = "" + year;

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
}