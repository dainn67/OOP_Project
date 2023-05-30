package main_package;

import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DynastyScraper {
	public static int foundYearCount = 0;
	public static int actualCount = 0;

	public static void main(String[] args) {
		// mod-articlescategories categories-module mod-list

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
//		Elements test = doc.select("div.historical-timeline");
		Elements test = doc.select("ul.issues");
		Elements dynasties = test.select("li");
//		System.out.println(normalizeString(test.toString()));

		for (Element dynasty : dynasties) {
			Element title = dynasty.selectFirst("h3");
			System.out.println(normalizeString(title.text()));
			System.out.println("	Link: " + title.select("a").attr("href"));

			StringBuilder content = new StringBuilder();
			Elements contentPTags = dynasty.select("p");
			for (Element contentPTag : contentPTags) {
				content.append(normalizeString(contentPTag.text() + " "));
			}
			System.out.println("	Desc: " + content.toString() + "\n");

			getTime(content.toString());

			actualCount++;
		}
	}

	static void getTime(String text) {
		int startYear = 0;
		int endYear = 0;

		Pattern yearPattern = Pattern.compile("tu (\\d+) cho den (\\d+)");
		Matcher yearMatcher = yearPattern.matcher(text);

		startYear = 0;
		endYear = 0;

		if (yearMatcher.find()) {
			startYear = Integer.parseInt(yearMatcher.group(1));
			endYear = Integer.parseInt(yearMatcher.group(2));
		}

		if (startYear != 0 || endYear != 0) {
			foundYearCount++;
			System.out.println("	Start Year (1): " + startYear);
			System.out.println("	End Year (1): " + endYear);
			return;
		}

		// Check form: tu nam 1890 hoac 1891 den nam 2000
		startYear = 0;
		endYear = 0;
		
		String startYearTCN = null;
		String endYearTCN = null;
		
		
		Pattern startYearPattern = Pattern.compile("tu nam (\\d+)");
		Matcher startYearMatcher = startYearPattern.matcher(text);
		if (startYearMatcher.find()) {
			startYear = Integer.parseInt(startYearMatcher.group(1));
			
			startYearTCN = "" + startYear;
			int yearIndex = text.indexOf(startYear);
			String remainingText = text.substring(yearIndex + startYearTCN.length());
			if (remainingText.contains("TCN")) {
				startYearTCN += " TCN";
			}
		}
		
		startYearPattern = Pattern.compile("vao nam (\\d+)");
		startYearMatcher = startYearPattern.matcher(text);
		if (startYearMatcher.find()) {
			startYear = Integer.parseInt(startYearMatcher.group(1));
			
			startYearTCN = "" + startYear;
			int yearIndex = text.indexOf(startYear);
			String remainingText = text.substring(yearIndex + startYearTCN.length());
			if (remainingText.contains("TCN")) {
				startYearTCN += " TCN";
			}
		}
		

		Pattern endYearPattern = Pattern.compile("den nam (\\d+)");
		Matcher endYearMatcher = endYearPattern.matcher(text);
		if (endYearMatcher.find()) {
			endYear = Integer.parseInt(endYearMatcher.group(1));
			
			endYearTCN = "" + endYear;
			int yearIndex = text.indexOf(endYear);
			String remainingText = text.substring(yearIndex + endYearTCN.length());
			if (remainingText.contains("TCN")) {
				endYearTCN += " TCN";
			}
		}

		if (startYear != 0) {
			System.out.println("	Start Years (2): " + startYearTCN);
			System.out.println("	End Year (2): " + endYearTCN);
			foundYearCount++;
			return;
		}

		// Check form: Ngay 2/7/1976
		yearPattern = Pattern.compile("\\d{1,2}/\\d{1,2}/(\\d{4})");
		yearMatcher = yearPattern.matcher(text);

		if (yearMatcher.find()) {
			String year = yearMatcher.group(1);
			System.out.println("	Start Year (3): " + year);
			foundYearCount++;
			return;
		}

		// Check form: (year-year)
		yearPattern = Pattern.compile("(\\d+)-(\\d+)");
		yearMatcher = yearPattern.matcher(text);

		startYear = 0;
		endYear = 0;

		if (yearMatcher.find()) {
			startYear = Integer.parseInt(yearMatcher.group(1));
			endYear = Integer.parseInt(yearMatcher.group(2));
		}

		if (startYear != 0 || endYear != 0) {
			foundYearCount++;
			System.out.println("	Start Year (4): " + startYear);
			System.out.println("	End Year (4): " + endYear);
			return;
		}
		
		startYear = -1;
		endYear = -1;
		Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            if(startYear == -1) startYear = Integer.parseInt(matcher.group(1));
            else endYear = Integer.parseInt(matcher.group(1));
        }
        
        if(startYear != -1 && endYear != -1) {
        	System.out.println("	Start year (5): " + startYear);
            System.out.println("	End year (5): " + endYear);
            foundYearCount++;
            return;
        }
        
        System.out.println("	Start year (6): 1627");
        System.out.println("	End year (6): cuoi the ki 18");
	}

	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-");
	}
}
