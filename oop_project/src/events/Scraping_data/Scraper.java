package Scraping_data;

import HistoricalEvent.Event;

import java.io.IOException;
import java.util.ArrayList;

public class Scraper {
    public static void main(String[] args) throws IOException {
//        String baseNguoiKeSu = "https://nguoikesu.com";
//        List<EventInit> eventNguoiKeSu = ScrapeUtilNguoiKeSu.standardizedEvents("https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=", baseNguoiKeSu);
//        String json = new Gson().toJson(eventNguoiKeSu);
//        String currentDir = System.getProperty("user.dir");
//        String path = currentDir + "\\" + "OOP_Project" + "\\" + "oop_project" + "\\" + "src" + "\\" + "events" + "\\" + "Resources" + "\\" + "eventsNKS.json";
//        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
//            out.write(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//                main branch
//        for (EventInit e : eventNguoiKeSu) {
////            if (e.getTime() == null)
//            System.out.println(e.toString());
//            System.out.println("take year method: " + e.takeYear());
//            System.out.println("___________________________________________");
//        }


//        String page = "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
//        String baseWiki2 = "https://vi.wikipedia.org/";
//        List<EventInit> eventWiki = ScraperUtilWiki.standardizeEvent(page, baseWiki2);
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
//        System.out.println("showing time");
//        Search s = new Search();
//        ArrayList<EventInit> res = s.eventSearch("bach dang");
//        for(EventInit e: res){
//            System.out.println(e.toString());
//        }
        String currentDir = System.getProperty("user.dir");

        String path = currentDir + "\\" + "OOP_Project" + "\\" + "oop_project" + "\\" + "src" + "\\" + "events" + "\\" + "Resources" + "\\";

        String path1 = path + "eventsFinal.json";
//        EventBuilder eb = new EventBuilder();
//
//        ArrayList<Event> ev = eb.decodeEvents(path1);
//        String json = new Gson().toJson(ev);
//        String pathEvent = path + "eventsFinal.json";
//        try (PrintWriter out = new PrintWriter(new FileWriter(pathEvent))) {
//            out.write(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("showing time");
//        Search s = new Search();
//        ArrayList<EventInit> res = s.eventSearch("bach dang");
//        for(EventInit e: res){
//            System.out.println(e.toString());
//        }
        ArrayList<Event> eList = Decode.decode(path1);
        System.out.println("***********************************");
        for(Event x: eList){
            System.out.println(x.toString());
        }
        System.out.println(eList.size());

    }
}