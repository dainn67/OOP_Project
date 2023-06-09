package figures;

import java.util.ArrayList;

import dynasties.object.Dynasty;
import figures.helpers.HelperFunctions;
import figures.helpers.MergeFigures;
import figures.helpers.RemoveDuplicate;

public class FigureScraper implements Scraper{
	
	static public ArrayList<String> refDynasties = HelperFunctions.decodeDynastyNamesFromJson("final_dynasties.json");
	static public ArrayList<Dynasty> refDynastiesObj = HelperFunctions.decodeDynastiesFromJson("final_dynasties.json");

	
	@Override
	public void merge() {
		RemoveDuplicate.remove();
	}

	
	public void start() {
		NKSFigureScraper.crawl();
		VSFigureScraper.crawl();
		MergeFigures.mergeDynasty();
		
		NKSPoinsettiaScraper.crawl();
		WikiKingScraper.crawl();
	}

}
