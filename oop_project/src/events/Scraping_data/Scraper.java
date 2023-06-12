package Scraping_data;
import HistoricalEvent.Event;
import HistoricalEvent.EventWrapper;

import java.io.IOException;
import java.util.List;

public class Scraper {
    public static void main(String[] args) throws IOException {
        String baseNguoiKeSu = "https://nguoikesu.com";
//        List<Event> eventNguoiKeSu =  ScrapeUtilNguoiKeSu.standardizedEvents("https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=", baseNguoiKeSu);
        //main branch
//        for (Event e : eventNguoiKeSu){
//            System.out.println(e.toString());
//        }


        //        ScrapeUtil.handleDetail("https://nguoikesu.com/tu-?lieu/quan-su/chien-tranh-nguyen-mong-dai-viet-lan-2");
        String page = "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
        String baseWiki2 = "https://vi.wikipedia.org/";
        ScraperUtilWiki.EventScrapper(page, baseWiki2);
        System.out.println("showing time");
        System.out.println();
        ScraperUtilWiki.showEvent();
    }
}