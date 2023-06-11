package main_package23;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TrangScraper {

	public static void main(String[] args) {
		
        String url = "https://nguoikesu.com/tu-lieu/danh-sach-trang-nguyen-viet-nam";

        try {
            Document doc = Jsoup.connect(url).get();
            getInfo( doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	static void getInfo(Document doc) {
	    Element table = doc.selectFirst("table.table.table-bordered");

	    if (table != null) {
	        Elements rows = table.select("tr");
	        for (Element row : rows) {
	            Elements cells = row.select("td");
	            if (cells.size() >= 6) {
	                Element linkElement = cells.get(1).selectFirst("a.annotation");
	                if (linkElement != null) {
	                    String name = linkElement.text();
	                    if (name.contains("Nguyễn Công Bình")) {
                            continue; // Skip elements containing "Bình"
                        }
	                    
	                    String home = cells.get(3).text();
	                    String trangYear = cells.get(4).text();
	                    String king = cells.get(5).text();
	                    String note = cells.get(6).text();

	                    String annotation = linkElement.selectFirst("a").attr("title");

	                    System.out.println("Name: " + name);
	                    System.out.println("	Home: " + home);
	                    System.out.println("	Đỗ year: " + trangYear);
	                    System.out.println("	King: " + king);
	                    System.out.println("	Note: " + note);
	                    System.out.println("	Annotation: " + normalizeString(annotation));
	                    getYears(normalizeString(annotation));
	                    System.out.println();
	                }
	            }
	        }
	    }
	}
	
	static void getYears(String str) {
		int birthYear = 0, deathYear = 0;
		String birthYearString, deathYearString;
		
//		Special pattern
		
		Pattern pattern = Pattern.compile("11-2-1050-?");
	    Matcher matcher = pattern.matcher(str);

	    if (matcher.find()) {
	    	birthYear=1050;
	        System.out.println("	SINH (1): " + birthYear);
	        System.out.println("	MAT (1): " + deathYear);

	        return;
	    }
	    
		//      " - "     "-"     " -"   "- "
		
		// check the pattern: 1990-1991
	    
		 pattern = Pattern.compile("(\\d+)-(\\d+)");
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
		
		 birthYear = 0;
		 deathYear = 0;
		 pattern = Pattern.compile("(\\d+|\\?)-?(\\d+|\\?)");
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
			
			System.out.println("	SINH (3): " + birthYear);
			System.out.println("	MAT (3): " + deathYear);
			return;
		}
		

		
	}
	
	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace("&#160;", " ").replace(" ?", "?").replace(" -", "-").replace(" - ", "-").replace("- ", "-");
	}
	
	
}