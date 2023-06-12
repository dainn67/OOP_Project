package Scraping_data;

import HistoricalEvent.Event;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScraperUtilWiki {
    private static List<Event> eventList = new ArrayList<>();

    public static void showEvent(){
        for(Event e: eventList){
            System.out.println(e.toString());
        }
    }
    public static void EventScrapper(String url, String baseURL){
        try{
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements eventTitles = doc.select("p:has(a[href]):has(b)");

            int totalEvent = eventTitles.size();
            int invalid = 0;

            for(Element event : eventTitles){
                if(!event.text().contains("Văn hóa")){
                    if(event.select("b").html().contains("href")){
                        invalid +=1;
                        continue;
                    }else {
                        System.out.println(event.text());
                        Elements nextLinks = event.select("a[href]");
                        for(Element link: nextLinks){
                            handleDetail(baseURL + link.attr("href"));
                        }
                    }
                }else{
                    invalid += 1;
                }
            }
            System.out.println(totalEvent-invalid);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void handleDetail(String detailURL){
        try{
            Event newEvent = new Event();

            Document detailDoc = Jsoup.connect(detailURL).userAgent("Mozilla").get();
            Elements table = detailDoc.select("table.vevent");
            if(table == null){
                return;
            }
            Elements rows = table.select("tbody tr");
            newEvent.setTitle(detailDoc.select(".summary").text());

            for(Element row : rows){
                Elements cells = row.select("tr");
                if(cells.size() > 1){
                    for(Element innerRow: cells){
                        if(innerRow.text().contains("Thời gian")){
                            Elements content = innerRow.select("td");
                            for(Element cont: content){
                                newEvent.setTime(cont.text());
                            }
                        }else if(innerRow.text().contains("Địa điểm")){
                            Elements content = innerRow.select("td");
                            for(Element cont: content){
                                newEvent.setLocation(cont.text());
                            }
                        }else {
                            newEvent.addDescription(innerRow.text());
                        }
                    }
                }

                if(row.text().contains("lãnh đạo")){
                    Element relatedFigures = row.nextElementSibling();
                    if(relatedFigures != null){
//                        for(Element fig: relatedFigures){
//                            Elements figs = fig.select("a[href]");
//                            for(Element name: figs){
//                                newEvent.setRelatedFigures(name.text());
//                            }
//                        }
//                        System.out.println(detailDoc.select("h1").text());
//                        System.out.println("related fig: " + relatedFigures.text());

                    }
                }
            }
            eventList.add(newEvent);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
