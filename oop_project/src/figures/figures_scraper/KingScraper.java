package figures_scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helper_package.EncodeDecode;
import helper_package.HelperFunctions;
import objects.King;

public class KingScraper {
	static String[] kingAttributes = new String[14]; 
	static List<King> kings = new ArrayList<King>();

	public static void main(String[] args) throws Exception {

		String url = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam#Th%E1%BB%9Di_k%E1%BB%B3_chia_c%E1%BA%AFt";
		Document doc = Jsoup.connect(url).get();

		// there are 2 types of table
		Elements tables = doc.select("div.mw-parser-output table:not(.wikitable)[width!=\"1400\"]");
		Elements tables_wikitable = doc.select("div.mw-parser-output table.wikitable[width!=\"1400\"]");

		loopTables(tables);
		loopTables(tables_wikitable);

		System.out.println("Total kings: " + kings.size());
		
		EncodeDecode.encodeToFile(new ArrayList<>(kings), "kings");
	}

	static void loopTables(Elements tables) {
		boolean isTitileRow = true;
		List<String> titleList = new ArrayList<>();

		for (Element table : tables) {
			if (!table.attr("cellpadding").isEmpty() && Integer.parseInt(table.attr("cellpadding")) == 0) {

				// loop through kings inside each table
				Elements characters = table.select("tr");
				for (Element character : characters) {

					// first get the title of each column
					if (isTitileRow) {
						isTitileRow = false;
						Elements titles = character.select("th");
						for (Element title : titles) {
							titleList.add(title.text().replaceAll("\\[.*?\\]", ""));
						}
						continue;
					}

					// then get the attributes corresponding
					Elements characterAttributes = character.select("td");

					kings.add(getKing(characterAttributes));
				}
				isTitileRow = true;
			}
		}
	}

	static King getKing(Elements characterAttributes) {
		for (int i = 0; i < kingAttributes.length; i++)
			kingAttributes[i] = "Không rõ";

		String ten = characterAttributes.get(1).text().replaceAll("\\[.*?\\]", "");
		String detailLink = "";
		String namTriVi = "";

		if (characterAttributes.size() >= 2) {
			
			//link to access inside
			detailLink = characterAttributes.get(1).selectFirst("a").attr("href");
			
			//kingYear (năm trị vì)
			if (ten.equals("An Dương Vương")) {
				namTriVi = characterAttributes.get(7).text().replaceAll("\\[.*?\\]", "");
			} else
				for (int i = characterAttributes.size() - 3; i < characterAttributes.size(); i++)
					namTriVi += characterAttributes.get(i).text().replaceAll("\\[.*?\\]", "") + " ";
			
			kingAttributes[9] = characterAttributes.get(2).text().replaceAll("\\[.*?\\]", "");
			kingAttributes[10] = characterAttributes.get(3).text().replaceAll("\\[.*?\\]", "");
			kingAttributes[11] = characterAttributes.get(4).text().replaceAll("\\[.*?\\]", "");
			kingAttributes[12] = characterAttributes.get(5).text().replaceAll("\\[.*?\\]", "");
			if(!characterAttributes.get(6).text().isBlank())
				kingAttributes[13] = characterAttributes.get(6).text().replaceAll("\\[.*?\\]", "");
		}

		accessDetailKing(detailLink);

		King newKing =  new King(
				ten,											//ten
				kingAttributes[0],								//ten khac
				HelperFunctions.parseYear(kingAttributes[1]),	//sinh
				HelperFunctions.parseYear(kingAttributes[2]),	//mat
				kingAttributes[3],								//cha
				kingAttributes[4],								//me
				kingAttributes[5],								//trieu dai
				kingAttributes[6],								//que quan
				extractNumbers(namTriVi),						//tri vi
				kingAttributes[8],								//mo ta
				kingAttributes[9],								//mieu hieu
				kingAttributes[10],								//thuy hieu
				kingAttributes[11],								//nien hieu
				kingAttributes[12],								//ten huy
				kingAttributes[13]);							//the thu
		
		//prt
		HelperFunctions.prtKingAttributes(newKing);
		
		return newKing;
	}
	
	static String extractNumbers(String input) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+"); // Regular expression to match one or more digits
        Matcher matcher = pattern.matcher(input);
        
        int count = 0;
        while (matcher.find() && count < 2) {
            int number = Integer.parseInt(matcher.group());
            numbers.add(number);
            count++;
        }
        
        if(numbers.isEmpty()) return "Không rõ";
        if(numbers.size() >= 2) return numbers.get(0) + " - " + numbers.get(1);
        return numbers.get(0).toString();
    }

	static void accessDetailKing(String detailLink) {
		try {
			// for each king in the tables, access the link inside to get more data
			String url = "https://vi.wikipedia.org" + detailLink;
			Document detailDoc = Jsoup.connect(url).get();

			getDataFromTable(detailDoc);
			getDataFromParagraph(detailDoc);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void getDataFromTable(Document detailDoc) {
		Element boxDetail = detailDoc.selectFirst("table.infobox");
		if (boxDetail == null)
			return;

		Elements trTags = boxDetail.select("tr");

		Element title, content;
		for (Element trTag : trTags) {
			title = trTag.selectFirst("th");
			content = trTag.selectFirst("td");
			if (title == null || content == null)
				continue;

			String contentString = content.text().replaceAll("\\[.*?\\]", "");
			switch (title.text()) {
				case "Sinh": {
					kingAttributes[1] = contentString;
					break;
				}
				case "Mất": {
					kingAttributes[2] = contentString;
					break;
				}
				case "Thân phụ": {
					kingAttributes[3] = contentString;
					break;
				}
				case "Thân mẫu": {
					kingAttributes[4] = contentString;
					break;
				}
				case "Triều đại": {
					kingAttributes[5] = contentString;
					break;
				}
			}
		}

	}

	static void getDataFromParagraph(Document doc) {
		Elements pTags = doc.select("p");
		StringBuilder desc = new StringBuilder();
		
		int limit = 1;
		if (pTags.size() > 0) {
			for (Element pTag : pTags) {
				if(limit > 3) break;
				desc.append(pTag.text()).append(" ");
				limit++;
			}
		}
		
		//get description
		kingAttributes[8] = desc.toString();

		//get dynasty
		extractDynastyName(desc.toString());
	}

	static void extractDynastyName(String text) {
		if (text.contains("vua Lê chúa Trịnh")) {
			kingAttributes[5] = "Vua Lê chúa Trịnh";
			return;
		}

		String[] keywords = { "Nhà", "nhà", "triều", "triều đại" };

		String pattern = "(?i)(" + String.join("|", keywords) + ")\\s+([A-ZÀ-Ỹ][a-zà-ỹ]+)";

		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(text);

		if (matcher.find()) {
			String dynastyName = matcher.group(2);
			if (Character.isUpperCase(dynastyName.charAt(0))) {
				kingAttributes[5] = dynastyName;
				return;
			}
		}
	}
}
