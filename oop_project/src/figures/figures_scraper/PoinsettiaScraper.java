package figures_scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helper_package.EncodeDecode;
import helper_package.HelperFunctions;
import objects.Figure;
import objects.Poinsettia;

public class PoinsettiaScraper {
	static List<Figure> figureList = EncodeDecode.decodedFigureList();
	
	static List<Poinsettia> poinsettias = new ArrayList<Poinsettia>();

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
						String name = linkElement.text();
						if (name.contains("Nguyễn Công Bình")) {
							continue; // Skip elements containing "Bình"
						}
						
//						Figure myFigure = search(figureList, normalizeString(name));
						Figure myFigure = search(figureList, name);
						if(myFigure == null) {
							System.out.println(name + " NOT FOUND");
							continue;
						}

						String home = cells.get(3).text();
						String trangYear = cells.get(4).text();
						String king = cells.get(5).text();

//						String annotation = linkElement.selectFirst("a").attr("title");
						
						Poinsettia myPoinsettia = new Poinsettia(
								name,
								myFigure.getOtherName(),
								myFigure.getBornYear(), 
								myFigure.getDeathYear(),
								myFigure.getFather(),
								myFigure.getMother(),
								myFigure.getDynasty(),
								home,
								king,
								myFigure.getDesc(),
								trangYear);
						
						poinsettias.add(myPoinsettia);
					}
				}
			}
		}
		List<Object> newList = new ArrayList<Object>();
		for(Poinsettia p: poinsettias) {
			HelperFunctions.prtPointessiaAttributeas(p);
			newList.add(p);
		}
		EncodeDecode.encodeToFile(newList, "poinsettias");
	}

	static Figure search(List<Figure> sortedList, String target) {
		for (Figure figure: sortedList)
			if (figure.getName().equals(target))
				return figure;

	    return null;
	}
}