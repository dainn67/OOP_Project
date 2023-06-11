package Scraping_data;

import HistoricalEvent.Event;
import HistoricalEvent.EventWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class ScrapeUtilNguoiKeSu {
    private static final List<Event> eventList = new ArrayList<Event>();

    private static final List<String> keyWords = new ArrayList<>();

    private static Map<HashMap<Integer, Integer>, String> dynasties = new HashMap<>();
    static {
        keyWords.add("Thời gian");
        keyWords.add("Địa điểm");
        keyWords.add("Kết quả");

        dynasties.put(new HashMap<>(-207, 40), "Bắc thuộc lần I");
//        dynasties.put(new HashMap<>())

    }

    public static List<Event> standardizedEvents(String url, String baseURL){
        List<Event> eventNguoiKeSu = EventScrapper(url, baseURL);

        for(Event e: eventNguoiKeSu){
            if(e.getTime() == null){
                int existed = e.getTitle().indexOf("năm");
                if(existed != -1){
                    e.setTime(e.getTitle().substring(existed));
                }else{
                    for(int i = 0; i < e.getTitle().length(); i++){
                        boolean isYear = Character.isDigit(e.getTitle().charAt(i));
                        if(isYear){
                            e.setTime(e.getTitle().substring(i));
                            break;
                        }
                    }
                }
            }
        }
        return eventNguoiKeSu;
    }
    public static List<Event> EventScrapper(String url, String baseURL){
        try{
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements links = doc.select("p.readmore a[href]");

            Element isTheEnd = doc.select(".active.page-item").first().nextElementSibling();

            String nextLink = baseURL + isTheEnd.select(".page-link").attr("href");

            for(Element link : links){
                handleDetail(baseURL + link.attr("href"));
            }
            if(nextLink.equals(baseURL)){
                return eventList;
            }
            EventScrapper(nextLink, baseURL);

        }catch(IOException e){
            e.printStackTrace();
        }
        return eventList;
    }
    public static void handleDetail(String detailURL){
        try{
            Event newEvent = new Event();
            Document detailDoc = Jsoup.connect(detailURL).userAgent("Mozilla").get();
            Elements table = detailDoc.select("table[cellpadding=0][cellspacing=0][width=100%]");
            Elements header = detailDoc.select("h1");
            Elements relatedFigures = detailDoc.select(".table-striped a[href]");

            Set<String> figures = new HashSet<String>();
            for(Element e: relatedFigures){
                if(e.attr("href").contains("nhan-vat")){
                    figures.add(e.text());
                }
            }

            newEvent.setTitle(header.text());
            Elements rows = table.select("tbody tr");
            for(Element row : rows){
                Elements cells = row.select("td");
                if (cells.size() > 1) {
                    String label = cells.get(0).text();
                    String value1 = cells.get(1).text();
                    if(label.compareTo(keyWords.get(0)) == 0){
                        newEvent.setTime(value1);
                    }else if(label.compareTo(keyWords.get(1)) == 0){
                        newEvent.setLocation(value1);
                    }else if(label.compareTo(keyWords.get(2)) == 0){
                        newEvent.setDescription(label + ": " + value1);
                    }else {
                        newEvent.addDescription(label + ": " + value1);
                    }
                }
            }
            newEvent.setRelatedFigure(figures);
            eventList.add(newEvent);

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
