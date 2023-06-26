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

public class relic {
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
        Elements tables = document.select("div.mw-parser-output table.wikitable.sortable");
        Elements provinces = document.select("div.mw-parser-output section h3 span.mw-headline");
        Element pro = new Element("div.mw-parser-output section h3 span.mw-headline");
        pro.html("Thu do Ha Noi");
        provinces.add(24, pro);
        FileWriter writer = new FileWriter("src/mainPackage/doc.html");
        FileWriter writer1 = new FileWriter("src/mainPackage/provinces.txt");
        FileWriter writer2 = new FileWriter("src/mainPackage/data2.txt");
        FileWriter writer3 = new FileWriter("src/mainPackage/data3.json");
        writer.write(normalizeString(document.toString()));
        int j = 0;
        int k = 0;
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
                            local.setRelic(removeBrackets(info.text()));

                            Elements links = info.select("a[href]");
                            for (Element link : links) {
                                if (!link.attr("abs:href").isEmpty()) {
                                    String desString = "";
                                    Document doc = Jsoup.connect(link.attr("abs:href")).get();
                                    Elements description = doc.select("div.mw-parser-output section.mf-section-0");
                                    if (!description.isEmpty()) {
                                        for (Element des : description) {
                                            desString += normalizeString(des.text()) + " ";
                                        }
                                        local.setDescripton(desString);
                                    } else {
                                        local.setDescripton("Khong co");
                                    }
                                }
                            }

                            local.setID(normalizeString(info.text()).toLowerCase().replace(" ", ""));
                            break;
                        case 1:
                            writer2.write("Vi tri: ");
                            local.setLocation(removeBrackets(info.text()));
                            break;
                        case 2:
                            writer2.write("Loai di tich: ");
                            local.setType(removeBrackets(info.text()));
                            break;
                        case 3:
                            writer2.write("Thoi gian: ");
                            local.setTime(removeBrackets(info.text()));
                            break;
                    }
                    local.setProvince(provinces.get(k).text());
                    writer2.write(normalizeString(info.text().replaceAll("\\[.?\\]", "")));
                    writer2.write("\n");
                    i++;
                }
                writer2.write(normalizeString(provinces.get(k).text()));
                writer2.write("\n");
                locals.add(local);
                j++;
            }
            k++;

        }
        Gson gson = new Gson();
        String json = gson.toJson(locals);
        writer3.write(json);
        System.out.println(j);
        System.out.println(k);
        writer.close();
        writer1.close();
        writer2.close();
        writer3.close();

    }
}
