package figures_scraper;

import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PoinsettiaScraper {
	public static void main(String[] args) {
		String url = "https://nguoikesu.com/tu-lieu/danh-sach-trang-nguyen-viet-nam";
		try {
			Document doc = Jsoup.connect(url).get();
			getInfo(doc);
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
						Element detailLink = linkElement.selectFirst("a");
						String name = normalizeString(linkElement.text());
						String birthYear = normalizeString(cells.get(2).text());
						String home = normalizeString(cells.get(3).text());
						String trangYear = normalizeString(cells.get(4).text());
						String king = normalizeString(cells.get(5).text());
						String note = normalizeString(cells.get(6).text());
						String annotation = normalizeString(linkElement.attr("title"));
						System.out.println("Name: " + name);
						if (birthYear != null)
							getYears(birthYear);
						System.out.println("	Link: " + normalizeString(detailLink.toString()));
						System.out.println("	Home: " + home);
						System.out.println("	graduated year: " + trangYear);
						System.out.println("	King: " + king);
						System.out.println("	Note: " + note);
						System.out.println("	Annotation: " + annotation);
						System.out.println();
					}
				}
			}
		}
	}

	static void getYears(String str) {
		int birthYear = 0, deathYear = 0;
		String birthYearString, deathYearString;
		// check the format: 1990-1991
		Pattern pattern = Pattern.compile("(\\d{4})-(\\d{4})");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			birthYearString = matcher.group(1);
			deathYearString = matcher.group(2);
			birthYear = Integer.parseInt(birthYearString);
			deathYear = Integer.parseInt(deathYearString);
			System.out.println("	SINH (1): " + birthYear);
			System.out.println("	MAT (1): " + deathYear);
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

	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace("—", "-");
	}
}
