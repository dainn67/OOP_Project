package dynasty_scraper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import helper_package.DynastyGsonAdapter;
import objects.Dynasty;

public class NguoikesuDynastyScraper {
	public static int foundYearCount = 0;
	public static int actualCount = 0;

	static List<Dynasty> dynasties = new ArrayList<Dynasty>();

	public static void main(String[] args) {
		try {
			String url = "https://nguoikesu.com";
			Document doc = Jsoup.connect(url).get();

			getDynasties(doc);

			System.out.println("	Found: " + foundYearCount + "/" + actualCount);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void getDynasties(Document doc) {
		String[] years = new String[2];

		Elements test = doc.select("ul.issues");
		Elements dynasties = test.select("li");

		for (Element dynasty : dynasties) {
			// reset years
			years[0] = null;
			years[1] = null;

			// title
			Element title = dynasty.selectFirst("h3");
			System.out.println(title.text());

			// link to detail page
			System.out.println("	Link: " + title.select("a").attr("href"));

			// description
			StringBuilder content = new StringBuilder();
			Elements contentPTags = dynasty.select("p");
			for (Element contentPTag : contentPTags) {
				content.append(contentPTag.text() + " ");
			}
			System.out.println("	Desc: " + content.toString() + "\n");

			getTime(content.toString(), years);

//			prtDynasty(title.text(), years, content.toString());
			Dynasty myDynasty = new Dynasty(title.text(), years[0], years[1], content.toString());

			NguoikesuDynastyScraper.dynasties.add(myDynasty);

			actualCount++;
		}

		encodeDynastyList();

		List<Dynasty> myNewList = decodedDynastyList();
		for (int i = 0; i < myNewList.size(); i++) {
			System.out.println("HI");
			prtDynasty(myNewList.get(i).getName(), myNewList.get(i).getStartYear(), myNewList.get(i).getEndYear(),
					myNewList.get(i).getDesc());
		}
	}

	static void getTime(String text, String[] years) {
		int startYear = 0;
		int endYear = 0;
		String startYearTCN = null;
		String endYearTCN = null;

		// Pattern 1: tu (year) den (year)
		Pattern yearPattern = Pattern.compile("từ (\\d+)");
		Matcher yearMatcher = yearPattern.matcher(text);
		if (yearMatcher.find()) {
			if (yearMatcher.group(1) != null) {
//				startYear = Integer.parseInt(yearMatcher.group(1));
				years[0] = yearMatcher.group(1);
			}

			yearPattern = Pattern.compile("đến (\\d+)");
			yearMatcher = yearPattern.matcher(text);

			if (yearMatcher.find()) {
				if (yearMatcher.group(1) != null) {
//					endYear = Integer.parseInt(yearMatcher.group(1));
					years[1] = yearMatcher.group(1);
				}
			}

//			System.out.println("	Start Year (2): " + startYear);
//			System.out.println("	End Year (2): " + endYear);
			return;
		}

		// specail01
		yearPattern = Pattern.compile("1627");
		yearMatcher = yearPattern.matcher(text);
		if (yearMatcher.find()) {
			years[0] = "1627";
			years[1] = "Cuối thế kỉ 18";
//			System.out.println("	Start Year (6): 1627");
//			System.out.println("	End Year (6): cuoi the ki 18");
			return;
		}

		// special02
		yearPattern = Pattern.compile("3/2/1930");
		yearMatcher = yearPattern.matcher(text);
		if (yearMatcher.find()) {
			years[0] = "1945";
			years[1] = "1975";
//			System.out.println("	Start Year : 1945");
//			System.out.println("	End Year : 1976");
			return;
		}
		// special03
		yearPattern = Pattern.compile("3/1/1428");
		yearMatcher = yearPattern.matcher(text);
		if (yearMatcher.find()) {
			years[0] = "1407";
			years[1] = "1427";
//			System.out.println("	Start Year : 1407");
//			System.out.println("	End Year : 1427");
			return;
		}

		// Pattern 4: (year)-(year)
		// Check form: year-year
		yearPattern = Pattern.compile("(\\d+) - (\\d+)");
		yearMatcher = yearPattern.matcher(text);
		startYear = 0;
		endYear = 0;
		if (yearMatcher.find()) {
			years[0] = yearMatcher.group(1);
			years[1] = yearMatcher.group(2);
//			startYear = Integer.parseInt(yearMatcher.group(1));
//			endYear = Integer.parseInt(yearMatcher.group(2));
		}
		if (years[0] != null || years[1] != null) {
//			System.out.println("	Start Year (4): " + startYear);
//			System.out.println("	End Year (4): " + endYear);
			return;
		}

		yearPattern = Pattern.compile("(\\d+)-(\\d+)");
		yearMatcher = yearPattern.matcher(text);
		startYear = 0;
		endYear = 0;
		if (yearMatcher.find()) {
			years[0] = yearMatcher.group(1);
			years[1] = yearMatcher.group(2);
//			startYear = Integer.parseInt(yearMatcher.group(1));
//			endYear = Integer.parseInt(yearMatcher.group(2));
		}
		if (years[0] != null || years[1] != null) {
//			System.out.println("	Start Year (4): " + startYear);
//			System.out.println("	End Year (4): " + endYear);
			return;
		}

		// Pattern 2: tu nam (year) or vao nam (year)
		yearPattern = Pattern.compile("từ năm (\\d+)|vào năm (\\d+)");
		yearMatcher = yearPattern.matcher(text);
		if (yearMatcher.find()) {
			if (yearMatcher.group(1) != null) {
				years[0] = yearMatcher.group(1);
				startYear = Integer.parseInt(yearMatcher.group(1));
			}

			if (yearMatcher.group(2) != null) {
				years[0] = yearMatcher.group(2);
				startYear = Integer.parseInt(yearMatcher.group(2));
			}

			startYearTCN = String.valueOf(startYear);

			int yearIndex = text.indexOf(startYearTCN);
			String remainingText = text.substring(yearIndex + startYearTCN.length());

			if (remainingText.contains("TCN"))
				startYearTCN += " TCN";

			// Pattern 3: den nam (year)
			yearPattern = Pattern.compile("đến năm (\\d+)");
			yearMatcher = yearPattern.matcher(text);
			if (yearMatcher.find()) {
				endYear = Integer.parseInt(yearMatcher.group(1));

				endYearTCN = String.valueOf(endYear);
				yearIndex = text.indexOf(endYearTCN);
				remainingText = text.substring(yearIndex + endYearTCN.length());
				if (remainingText.contains("TCN"))
					endYearTCN += " TCN";
			}
			
			years[0] = startYearTCN;
			years[1] = endYearTCN;
//			System.out.println("	Start Year (2): " + startYearTCN);
//			System.out.println("	End Year (2): " + endYearTCN);
			return;
		}

		// Pattern 3: Ngay (date)/(month)/(year)
		yearPattern = Pattern.compile("\\d{1,2}/\\d{1,2}/(\\d{4})");
		yearMatcher = yearPattern.matcher(text);
		if (yearMatcher.find()) {

			years[0] = yearMatcher.group(1);
//			System.out.println(" Start Year (3): " + year);
			return;
		}

		// Pattern 5: (year)
		yearPattern = Pattern.compile("\\((\\d+)\\)");
		yearMatcher = yearPattern.matcher(text);
		while (yearMatcher.find()) {
			if (years[0] == null) {
//				startYear = Integer.parseInt(yearMatcher.group(1));
				years[0] = yearMatcher.group(1);
			} else {
				endYear = Integer.parseInt(yearMatcher.group(1));
				years[1] = yearMatcher.group(1);
			}
		}
//		if (startYear != 0 && endYear != 0) {
//			System.out.println("	Start Year (5): " + startYear);
//			System.out.println("	End Year (5): " + endYear);
//			foundYearCount++;
//			return;
//		}

	}

	static void prtDynasty(String title, String startYear, String endYear, String desc) {
		System.out.println(title);
		System.out.println("	Bắt đầu: " + startYear);
		System.out.println("	Kết thúc: " + endYear);
		System.out.println("	Mô tả: " + desc);
	}

	static void encodeDynastyList() {
		Gson gson = new GsonBuilder().registerTypeAdapter(Dynasty.class, new DynastyGsonAdapter()).setPrettyPrinting()
				.create();
		String filePath = "dynasties.json";

		try (Writer writer = new FileWriter(filePath)) {
			gson.toJson(dynasties, writer);
			System.out.println("Encode & Write complete");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static List<Dynasty> decodedDynastyList() {
		Gson gson = new GsonBuilder().registerTypeAdapter(Dynasty.class, new DynastyGsonAdapter()).setPrettyPrinting()
				.create();

		List<Dynasty> newDynastyList = null;
		String filePath = "dynasties.json";

		try (Reader reader = new FileReader(filePath)) {
			Type listType = new TypeToken<List<Dynasty>>() {
			}.getType();
			newDynastyList = gson.fromJson(reader, listType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newDynastyList;
	}

}
