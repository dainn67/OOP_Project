package main_package;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiScraper {
	public static void main(String[] args) throws Exception {
		String url = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam#Th%E1%BB%9Di_k%E1%BB%B3_chia_c%E1%BA%AFt";
		Document doc = Jsoup.connect(url).get();

		Elements tables = doc.select("div.mw-parser-output table:not(.wikitable)");

		boolean isTitileRow = true;
		List<String> titleList = new ArrayList<>();
		
		int titleIterator = 0;
		for (Element table : tables) {
			if (!table.attr("cellpadding").isEmpty() && Integer.parseInt(table.attr("cellpadding")) == 0) {

				Elements characters = table.select("tr");
				for (Element character : characters) {
					if(isTitileRow) {
//						System.out.println("FIRST PRINT TITLE");
						isTitileRow = false;
						
						Elements titles = character.select("th");
						for(Element title: titles) {
//							System.out.println(normalizeString(title.text().replaceAll("\\[.*?\\]", "")));
							titleList.add(normalizeString(title.text().replaceAll("\\[.*?\\]", "")));
						}
//						titleList.add("");
//						titleList.add("");
						
						continue;
					}
					Elements characterAttributes = character.select("td");
					for (Element attribite : characterAttributes) {
						
						if(attribite.text().isEmpty()) System.out.println(titleList.get(titleIterator) + ": khong co");
						else System.out.println(
//								titleList.get(titleIterator) + ": " +
								normalizeString(attribite.text().replaceAll("\\[.*?\\]", "")));
						titleIterator++;
						if(titleIterator >= titleList.size()) titleIterator = 0;
					}
					System.out.println("\n\n\n");
				}
				break;
//				isTitileRow = true;
//				titleList.clear();
//				System.out.println("-----------------------------------------------");
			}
		}
	}

	//put the string in this function if console cannot print Vietnamese font
	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-");
	}
	
	static String removeBrackets(String s) {
		return s.replaceAll("\\[.*?\\]", "");
	}
}
