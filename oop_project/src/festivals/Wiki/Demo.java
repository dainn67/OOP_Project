package Wiki;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Demo {
	public static void main(String[] args) {
		try {
			String url = "https://nguoikesu.com/anh-hung-dan-toc?types[0]=1&start=10";
			Document doc = Jsoup.connect(url).get();
			Elements data = doc.select("span.tag-body p");
			for (Element d : data) {
                String info = d.text();
                //String linkUrl = link.absUrl("href");
                System.out.println(info);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
