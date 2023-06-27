package Festival;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class WikiTable {
	public static void main(String[] args) {
		ArrayList<Festival> festivals = new ArrayList<Festival>();
		ArrayList<String> jsonFestivals = new ArrayList<String>();
		String fileName = "E:/Festival2.json";
		Gson gson = new Gson();

		try {
			String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#:~:text=M%E1%BB%99t%20s%E1%BB%91%20l%E1%BB%85%20h%E1%BB%99i%20l%E1%BB%9Bn,ph%E1%BB%91%20%C4%90%C3%A0%20N%E1%BA%B5ng)...";
			Document doc = Jsoup.connect(url).get();
			Elements data = doc.select("table.prettytable tr:not(:first-child)");
			boolean r = true;
			for (Element d : data) {
				Elements e = d.select("td");
				Festival f = new Festival();
				f.setStartTime(e.get(0).text());
				f.setPlace(e.get(1).text());
				f.setName(e.get(2).text());
				f.setFirstTimes(e.get(3).text());
				f.setRelatedCharacter(normalizeString(e.get(4).text()).toLowerCase().replace(" ", ""));
				f.setId(normalizeString(e.get(2).text()).toLowerCase().replace(" ", ""));

				Elements hrefElement = e.get(2).select("a");
				String hrefDetail = hrefElement.get(0).absUrl("href");

				getDetail(hrefDetail, f);
				festivals.add(f);

				String fString = gson.toJson(f);
				jsonFestivals.add(fString);
			}
			// r = FileManipulate.SaveFileFestival(festivals, fileName);
			r = FileManipulate.SaveFile(jsonFestivals, fileName);

			if (r)
				System.out.println("Save Successfully");
			else {
				System.out.println("Save fail");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getDetail(String url, Festival f) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements data = doc.select("p");
			// if(data == null) return"";
			for (Element element : data) {
				f.addDescription(element.text());
			}
			// return data.text();
		} catch (IOException e) {
			e.printStackTrace();
			// return "";
		}
	}

	static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace("—", "-");
	}
}
