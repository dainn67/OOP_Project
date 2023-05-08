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

public class CharacterScraper {
	public static int count = 0;
	public static int realCount = 0;

	static int urlCounter = 0;

	static String[] toSaveToFile = new String[155];
	static String[] resDynastys = new String[155];

	public static void main(String[] args) {
			// DEBUGGING
			debug();

			//RUN REAL MAIN FUNCTION
			runMain();
			
//			encodeDynasty();
//			decodeDynasty();

			System.out.println("\nCount: " + count);
	}

	static void debug() {
		try {
			while (true) {
				if (urlCounter > 20)
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

			String str = "chu Han: ??; 22 thang 10 nam 1913 - 31 thang 7 nam 1997\n????\n1945\n1949-1955";

			Pattern datePattern = Pattern.compile("\\d{4}");
			Matcher dateMatcher = datePattern.matcher(str);
			String dob = null;
			String dod = null;
			if (dateMatcher.find()) {
				dob = dateMatcher.group();
				if (dateMatcher.find()) {
					dod = dateMatcher.group();
				}
			}

			System.out.println("Date of birth: " + dob);
			System.out.println("Date of death: " + dod);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void runMain() {
		try {
			Document document = Jsoup.connect("https://nguoikesu.com/nhan-vat").get();
			getFigures(document);
			System.out.println(normalizeString(document.toString()));

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
			Elements _figureDesc = div.select("p");

			String nameContentString = "";
			for (Element name : _figureName) {
				nameContentString += name.text();
			}

			String descContentString = "";
			for (Element desc : _figureDesc) {
				descContentString += desc.text() + "";
			}
			descContentString += "\n";

			System.out.println(normalizeString(nameContentString));
//			System.out.println(normalizeString(descContentString));

			Pattern pattern = Pattern.compile("\\((.*?)\\)");

			String findDOBString = "";
			Matcher matcher = pattern.matcher(descContentString);
			while (matcher.find()) {
				System.out.println("	" + normalizeString(matcher.group(1)));
				findDOBString += (matcher.group(1));
			}

			//TODO: unfix, check out the function in debug
			if (findDOBString.matches(".*\\d+.*")) {
				Pattern patternDOB = Pattern.compile("\\b\\d{4}z\\d{4}\\b");
				Matcher matcherDOB = patternDOB.matcher(findDOBString);

				while (matcherDOB.find()) {
					String yearRange = matcherDOB.group();
					int startYear = Integer.parseInt(yearRange.substring(0, 4));
					int endYear = Integer.parseInt(yearRange.substring(5));
					System.out.println("Year range: " + startYear + "-" + endYear);
				}
			} else
				System.out.println("NO\n");

			count++;

		}
		urlCounter += 5;
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

	// A helper function to normalize the string
	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "z");
	}
}
