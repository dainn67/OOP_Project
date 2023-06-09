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
	static String[] dynastyAttributes = new String[4];

	public static void main(String[] args) {
		try {
			String url = "https://nguoikesu.com";
			Document doc = Jsoup.connect(url).get();

			getDynasties(doc);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void getDynasties(Document doc) {
		for(int i=1; i<dynastyAttributes.length; i++) dynastyAttributes[i] = "Khong ro";
		
		Elements test = doc.select("ul.issues");
		Elements dynasties = test.select("li");

		for (Element dynasty : dynasties) {
			Element title = dynasty.selectFirst("h3");
//			System.out.println(normalizeString(title.text()));
			dynastyAttributes[0] = normalizeString(title.text());
			
//			System.out.println("	Link: " + title.select("a").attr("href"));

			StringBuilder content = new StringBuilder();
			Elements contentPTags = dynasty.select("p");
			for (Element contentPTag : contentPTags) {
				content.append(normalizeString(contentPTag.text() + " "));
			}
//			System.out.println("	Desc: " + content.toString() + "\n");
			dynastyAttributes[3] = content.toString();

			getTime(content.toString());

			prtDynasty();
		}
	}

	static void getTime(String text) {
		Boolean foundStartYear = false;
		Boolean foundEndYear = false;
		
		int startYear = 0;
		int endYear = 0;

		Pattern yearPattern = Pattern.compile("tu (\\d+) cho den (\\d+)");
		Matcher yearMatcher = yearPattern.matcher(text);

		if (yearMatcher.find()) {
			startYear = Integer.parseInt(yearMatcher.group(1));
			endYear = Integer.parseInt(yearMatcher.group(2));
		}

		if (startYear != 0 || endYear != 0) {
			
			dynastyAttributes[1] = startYear + " (tu ... den ...)";
			dynastyAttributes[2] = endYear + " (tu ... den ...)";
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
			
			dynastyAttributes[1] = startYearTCN + " (tu nam)";
			foundStartYear = true;
		}
		
		if(!foundStartYear) {			
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
			dynastyAttributes[1] = startYearTCN + " (vao nam)";
			foundStartYear = true;
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
			
			dynastyAttributes[2] = endYearTCN + " (den nam)";
			foundEndYear = true;
		}

		if(foundStartYear && foundEndYear) return;
		
		// Check form: Ngay 2/7/1976
		yearPattern = Pattern.compile("\\d{1,2}/\\d{1,2}/(\\d{4})");
		yearMatcher = yearPattern.matcher(text);

		if (yearMatcher.find()) {
			String year = yearMatcher.group(1);
			dynastyAttributes[1] = year + " (1/1/1111)";
			foundStartYear = true;
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
			
			dynastyAttributes[1] = startYear + " (2003/2003)";
			dynastyAttributes[2] = endYear + " (2003/2003)";
			
			return;
		}
		
		//Check ì years are in brackets (905), (938)
		startYear = 0;
		endYear = 0;
		Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            if(startYear == 0) startYear = Integer.parseInt(matcher.group(1));
            else endYear = Integer.parseInt(matcher.group(1));
        }
        
        if(startYear != 0 && endYear != 0) {
        	dynastyAttributes[1] = startYear + " (905), (938)";
        	dynastyAttributes[2] = endYear + " (905), (938)";
        }
        
//        pattern = Pattern.compile("\\((\\d+)\\)");
//        matcher = pattern.matcher(text);
//
//        startYear = 0;
//        endYear = 0;
//
//        while (matcher.find()) {
//            if (startYear != 0) {
//                startYear = Integer.parseInt( matcher.group(1) );
//                dynastyAttributes[1] = startYear + "";
//            } else {
//                endYear = Integer.parseInt( matcher.group(1) );
//                dynastyAttributes[2] = endYear + "";
//                return;
//            }
//        }
        
	}
	
	static void prtDynasty() {
		System.out.println(dynastyAttributes[0]);
		System.out.println("	Bat dau: " + dynastyAttributes[1]);
		System.out.println("	Ket thuc: " + dynastyAttributes[2]);
		System.out.println("	Mo ta: " + dynastyAttributes[3]);
	}

	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-");
	}
}
