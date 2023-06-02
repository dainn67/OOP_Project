package Scraping_data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrapeUtil {
    public static void EventScrapper(String url, String baseURL){
//        List<String> detailsURL = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements links = doc.select("p.readmore a[href]");

            Element isTheEnd = doc.select(".active.page-item").first().nextElementSibling();
            System.out.println();

//            Element nextPage = doc.getElementsByClass("page-item").last().previousElementSibling();
//            if(isTheEnd.select(".page-link").attr("href") == null)
//                return;
            String nextLink = baseURL + isTheEnd.select(".page-link").attr("href");
            System.out.println(nextLink);

            for(Element link : links){
//                    detailsURL.add(baseURL + link.attr("href"));
//                System.out.println(baseURL + link.attr("href"));
                handleDetail(baseURL + link.attr("href"));
            }
            if(nextLink.equals(baseURL)){
                return;
            }
            EventScrapper(nextLink, baseURL);

//            System.out.println();
//            System.out.println(isTheEnd.text());


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
//            return;

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
