import Scraping_data.ScrapeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Crawler {
    public static void main(String[] args) throws IOException {
        String base = "https://nguoikesu.com";
        ScrapeUtil.EventScrapper("https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=", base);
//        ScrapeUtil.handleDetail("https://nguoikesu.com/tu-?lieu/quan-su/chien-tranh-nguyen-mong-dai-viet-lan-2");
    }
}