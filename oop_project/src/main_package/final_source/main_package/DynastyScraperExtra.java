package main_package;

import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DynastyScraperExtra {
	static int urlCounter = 0;
	static int figureCounter = 0;
	static int parentCounter = 0;
	static int homeCounter = 0;

	public static void main(String[] args) {

		try {
			String url;
			Document doc;
//			/*
			while (true) {
				if (urlCounter < 935) {
					urlCounter += 5;
					continue;
				} else if (urlCounter > 965)
					break;
				if (urlCounter == 0)
					url = "https://nguoikesu.com/nhan-vat";
				else
					url = "https://nguoikesu.com/nhan-vat?start=" + urlCounter;
				doc = Jsoup.connect(url).get();
				getDynastyPage(doc);
			}
//			 */
			getDynastyPage(Jsoup.connect("https://nguoikesu.com/nhan-vat?start=955").get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void getDynastyPage(Document doc) {
		System.out.println("								" + urlCounter);

		Elements divs = doc.select("div.com-content-category-blog__item");

		// loop each dynasties in a page
		for (Element div : divs) {
			Element figureNameElement = div.selectFirst("h2");

			String figureName = normalizeString(figureNameElement.text());
			String detailLink = figureNameElement.select("a").attr("href");

			if (figureName.startsWith("nha") || figureName.startsWith("Nha")) {
				System.out.println("TRIEU DAI: " + figureName);
				dynastyDetail(detailLink);
			} else
				continue;
		}
		urlCounter += 5;
	}

	static void dynastyDetail(String link) {
		String url = "https://nguoikesu.com" + link;
		System.out.println("	" + url);
		try {
			Document doc = Jsoup.connect(url).get();

			getTime(doc);
			getCountry(doc);

		} catch (IOException e) {

			System.out.println("			CANNOT GET DOC");
		}
	}

	static void getTime(Document doc) {
		Boolean alreadyExtractYear = false;

		Element infobox = doc.selectFirst("div.infobox");
		if (infobox != null) {
			Elements trTags = infobox.select("tr");

			StringBuilder sb = new StringBuilder();
			for (Element trTag : trTags) {
				sb.append("\n		" + normalizeString(trTag.text()));
			}

			alreadyExtractYear = extractYearTable(sb.toString());
		}
		if (!alreadyExtractYear) {
			Elements pTags = doc.select("p");
			StringBuilder sb = new StringBuilder();
			for (Element pTag : pTags) {
				sb.append("\n	" + normalizeString(pTag.text()));
			}

			System.out.println("	Extract from paragraph");
			extractYearTable(sb.toString());
		}
//		System.out.println("	Description: " + sb.toString());
	}

	static void getCountry(Document doc) {
		Element containerDiv = doc.selectFirst("div.com-content-article__body");
		Elements paragraphs = containerDiv.select("p");
		String[] paragraphTexts = new String[3]; // Array to store the paragraph texts

		int count = 0;

		for (Element paragraph : paragraphs) {
			if (paragraph.parent() == containerDiv && count < 3) {
				String text = normalizeString(paragraph.text());
				paragraphTexts[count] = text;
				count++;
			}
		}

		boolean isChingChong = false;

		for (int i = 0; i < paragraphTexts.length; i++) {
			String text = paragraphTexts[i];

			if (text.toLowerCase().contains("trung quoc")) {
				System.out.println("\n      TRUNG QUOC");
				isChingChong = true;
				break;
			}

		}
		if (isChingChong == false)
			System.out.println("\n      VIET NAM");

	}

	static Boolean extractYearTable(String text) {
		Pattern pattern = Pattern.compile("(\\d+)( TCN)?-(\\d+)( TCN)?");
		Matcher matcher = pattern.matcher(text);

		String startYear = null;
		String endYear = null;

		while (matcher.find()) {
			startYear = matcher.group(1);
			endYear = matcher.group(3);

			if (matcher.group(2) != null) {
				startYear += " TCN";
			}
			if (matcher.group(4) != null) {
				endYear += " TCN";
			}

			break; // Assuming we only need the first occurrence
		}
		if (startYear != null && endYear != null) {
			System.out.println("		Start year: " + startYear);
			System.out.println("		End year: " + endYear);
			return true;
		}

		System.out.println("		Start Years (3): " + startYear);
		System.out.println("		End Year (3): " + endYear);
		return false;
	}

	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-");
	}
}