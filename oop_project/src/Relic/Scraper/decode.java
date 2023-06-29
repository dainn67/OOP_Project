package Relic.Scraper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Relic.Object.Locate;
import objects.Figure;

public class decode {
    static String normalizeString(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
                .replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace("—", "-");
    }

    public static void main(String[] args) {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        ArrayList<Locate> locals = new ArrayList<Locate>();
        ArrayList<Locate> fommatedLocal = new ArrayList<Locate>();
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
        try {
            Reader reader1 = new FileReader("src/Relic/Data/data3.json");
            File file1 = new File("src/Relic/Data/finalRelic.json");
            FileWriter writer1 = new FileWriter(file1);
            Type listType1 = new TypeToken<List<Locate>>() {
            }.getType();
            locals = gson1.fromJson(reader1, listType1);

            Reader reader2 = new FileReader("src/figures/data/vansu_figures.json");
            Type listType2 = new TypeToken<List<Figure>>() {
            }.getType();
            figures = gson2.fromJson(reader2, listType2);
            for (Locate loc : locals) {
                for (Figure figure : figures) {
                    if (loc.getDescription() == null)
                        break;
                    if (normalizeString(loc.getDescription()).contains(normalizeString(figure.getName()))) {
                        loc.setFigure(figure);
                    }
                }
                fommatedLocal.add(loc);
            }
            String json = gson3.toJson(fommatedLocal);
            writer1.write(json);
            writer1.close();
            reader1.close();
            reader2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}