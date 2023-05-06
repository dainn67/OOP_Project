package main_package;
import java.text.Normalizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CharacterScraper {

	// A helper function to normalize the string and replace Đ with D
    public static String normalizeString(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D");
    }
    
    public static void main(String[] args) {
        try {
          Document doc = Jsoup.connect("https://vi.wikipedia.org/wiki/Anh_h%C3%B9ng_d%C3%A2n_t%E1%BB%99c_Vi%E1%BB%87t_Nam").get();
          Elements rows = doc.select("table.wikitable.sortable tr");

          for (int i = 1; i < rows.size(); i++) {
              Elements cols = rows.get(i).select("td");
              String name = cols.get(1).text();
              String dynasty = cols.get(2).text();

              // Normalize the name and dynasty strings and replace "Đ" with "D"
              String normalized_name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D");
              String normalized_dynasty = Normalizer.normalize(dynasty, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D");

              // Print the normalized name and dynasty
              System.out.println(normalized_name + " - " + normalized_dynasty);
          }
        }catch (Exception e) {
			 e.printStackTrace();
		}
      }
}

