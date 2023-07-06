import dynasties.DynastyScraper;
import figures.FigureScraper;
import figures.helpers.ArrangeFunctions;

public class Runner {
	public static void main(String[] args) {
		
		DynastyScraper ds = new DynastyScraper();
		ds.start();
		ds.merge();
		
		FigureScraper fs = new FigureScraper();
		System.out.println("RUN");
		fs.start();
		fs.merge();
		
		ArrangeFunctions.arrangeFiguresToDynasties();
		
		
	}
}
