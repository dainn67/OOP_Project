package mainPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;

public class test {
    static Document document;
    static ArrayList<Locate> locals = new ArrayList<Locate>();

    static String normalizeString(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
                .replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace("—", "-");
    }

    static String removeBrackets(String s) {
        return s.replaceAll("\\[.?\\]", "");
    }

    public static void getHTML(String url) {
        try {
            document = Jsoup.connect(url).get();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void main(String[] args) throws Exception {
        getHTML("https://vi.m.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam");
        Elements tables = document.select("div.mw-parser-output table.wikitable");
        FileWriter writer2 = new FileWriter("src/mainPackage/data2.txt");
        FileWriter writer3 = new FileWriter("src/mainPackage/data3.json");

        int j = 0;
        for (Element table : tables) {
            Elements historicals = table.select("tr");
            for (Element his : historicals) {
                writer2.write("\n");
                Elements infos = his.select("td");
                Locate local = new Locate();
                int i = 0;
                for (Element info : infos) {
                    switch (i) {
                        case 0:
                            writer2.write("Di tich: ");
                            local.setRelic(normalizeString(removeBrackets(info.text())));
                            break;
                        case 1:
                            writer2.write("Vi tri: ");
                            local.setLocation(normalizeString(removeBrackets(info.text())));
                            break;
                        case 2:
                            writer2.write("Loai di tich: ");
                            local.setType(normalizeString(removeBrackets(info.text())));
                            break;
                        case 3:
                            writer2.write("Thoi gian: ");
                            local.setTime(normalizeString(removeBrackets(info.text())));
                            break;
                        case 4:
                            writer2.write("Ghi chu: ");
                            local.setNote(normalizeString(removeBrackets(info.text())));
                            break;
                    }
                    writer2.write(normalizeString(info.text().replaceAll("\\[.?\\]", "")));
                    writer2.write("\n");
                    i++;
                }
                writer2.write("\n");
                locals.add(local);
                j++;
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(locals);
        writer3.write(json);
        System.out.println(j);
        writer2.close();
        writer3.close();

    }
}
