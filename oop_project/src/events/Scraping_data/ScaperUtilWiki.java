package Scraping_data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ScaperUtilWiki {
    public static void EventScrapper(String url){
        try{
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements eventTitles = doc.select("p:has(a[href]):has(b)");

            int totalEvent = eventTitles.size();

            for(Element event : eventTitles){
                System.out.println(event.text());
            }
            System.out.println(totalEvent);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void handleDetail(String detailURL){
        try{
            Document detailDoc = Jsoup.connect(detailURL).userAgent("Mozilla").get();
            Elements table = detailDoc.select("table[cellpadding=0][cellspacing=0][width=100%]");
            Elements header = detailDoc.select("h1");

            System.out.println(header.text());
            Elements rows = table.select("tbody tr");

            for(Element row : rows){
                Elements cells = row.select("td");
                if (cells.size() > 1) {
                    String label = cells.get(0).text();
                    String value1 = cells.get(1).text();
                    System.out.println(label + ": " + value1);
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
