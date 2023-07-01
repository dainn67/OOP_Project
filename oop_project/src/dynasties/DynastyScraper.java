package dynasties;

import dynasties.helpers.MergerDynasty;
import figures.Scraper;
import figures.helpers.ArrangeFunctions;

public class DynastyScraper implements Scraper{

	@Override
	public void merge() {
		MergerDynasty.mergeDynasty();
		
	}

	@Override
	public void start() {
		NKSDynastyScraper.crawl();
		NKSDynastyScraperExtra.crawl();
		VSDynastyScraper.crawl();
	}

}