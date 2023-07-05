package Scraping_data;

import HistoricalEvent.EventBuilder;
import HistoricalEvent.EventWrapper;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Scraper {
    public static void main(String[] args) throws IOException {
//        String baseNguoiKeSu = "https://nguoikesu.com";
//        List<Event> eventNguoiKeSu = ScrapeUtilNguoiKeSu.standardizedEvents("https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=", baseNguoiKeSu);
//        String json = new Gson().toJson(eventNguoiKeSu);
//        String currentDir = System.getProperty("user.dir");
//        String path = currentDir + "\\" + "OOP_Project" + "\\" + "oop_project" + "\\" + "src" + "\\" + "events" + "\\" + "Resources" + "\\" + "eventsNKS.json";
//        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
//            out.write(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//                main branch
//        for (Event e : eventNguoiKeSu) {
////            if (e.getTime() == null)
//            System.out.println(e.toString());
//            System.out.println("take year method: " + e.takeYear());
//            System.out.println("___________________________________________");
//        }


//        String page = "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
//        String baseWiki2 = "https://vi.wikipedia.org/";
//        List<Event> eventWiki = ScraperUtilWiki.standardizeEvent(page, baseWiki2);
//        String json = new Gson().toJson(eventWiki);
//
//        String currentDir = System.getProperty("user.dir");
//
//        String path = currentDir + "\\" + "OOP_Project" + "\\" + "oop_project" + "\\" + "src" + "\\" + "events" + "\\" + "Resources" + "\\" + "eventsWiki.json";
//
//        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
//            out.write(json);
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//
        System.out.println("showing time");
//        Search s = new Search();
//        ArrayList<Event> res = s.eventSearch("bach dang");
//        for(Event e: res){
//            System.out.println(e.toString());
//        }
        String currentDir = System.getProperty("user.dir");

        String path = currentDir + "\\" + "OOP_Project" + "\\" + "oop_project" + "\\" + "src" + "\\" + "events" + "\\" + "Resources" + "\\";

        String path1 = path + "eventsWiki.json";
        EventBuilder eb = new EventBuilder();

        ArrayList<EventWrapper> ev = eb.decodeEvents(path1);
        String json = new Gson().toJson(ev);
        String pathEvent = path + "eventsFinal.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(pathEvent))) {
            out.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("***********************************");
//        for(EventWrapper x: ev){
//            System.out.println(x.toString());
//        }
//        System.out.println(ev.size());

    }
}