package Scraping_data;

import HistoricalEvent.Event;
import HistoricalEvent.EventWrapper;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Scraper {
    public static void main(String[] args) throws IOException {
//        String baseNguoiKeSu = "https://nguoikesu.com";
//        List<Event> eventNguoiKeSu = ScrapeUtilNguoiKeSu.standardizedEvents("https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=", baseNguoiKeSu);
//        String json = new Gson().toJson(eventNguoiKeSu);
//        String path = "./OOP_Project/OOP_Project/oop_project/src/events/Resources/events.json";
//        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
//            out.write(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //        main branch
//        for (Event e : eventNguoiKeSu) {
////            if (e.getTime() == null)
//            System.out.println(e.toString());
//            System.out.println("take year method: " + e.takeYear());
//            System.out.println("___________________________________________");
//        }


        String page = "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
        String baseWiki2 = "https://vi.wikipedia.org/";
        List<Event> eventWiki = ScraperUtilWiki.standardizeEvent(page, baseWiki2);
        String json = new Gson().toJson(eventWiki);
        String path = "./OOP_Project/OOP_Project/oop_project/src/events/Resources/eventsWiki.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        System.out.println("showing time");
//        System.out.println("___________________________________________");
//        ScraperUtilWiki.showEvent();

    }
}