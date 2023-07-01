import dynasties.DynastyScraper;
import figures.FigureScraper;
import figures.helpers.ArrangeFunctions;

public class Runner {
	public static void main(String[] args) {
		
//		DynastyScraper ds = new DynastyScraper();
//		System.out.println("Start scraping dynasties");
//		ds.start();
//		ds.merge();
		
		FigureScraper fs = new FigureScraper();
//		System.out.println("Start scraping figures");
//		fs.start();
		fs.merge();
		
		System.out.println("HERE");
		ArrangeFunctions.arrangeFiguresToDynasties();
	}
}
