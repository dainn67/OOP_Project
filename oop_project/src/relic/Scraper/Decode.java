package relic.Scraper;

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

import figures.objects.Figure;
import relic.Object.Relic;


public class Decode {
    static String normalizeString(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
                .replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace("—", "-");
    }
    public static ArrayList<Relic> decode(String filePath) {
    	try {
	    	ArrayList<Relic> locals = new ArrayList<Relic>();
	    	Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
	    	Reader reader1 = new FileReader(filePath);
	    	Type listType1 = new TypeToken<List<Relic>>() {
	        }.getType();
	        locals = gson1.fromJson(reader1, listType1);
	        
	        return locals;
    	} catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    public static void main(String[] args) {
        ArrayList<Figure> figures = new ArrayList<Figure>();
        ArrayList<Relic> locals = new ArrayList<Relic>();
        ArrayList<Relic> fommatedLocal = new ArrayList<Relic>();
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
        try {
            String filePath = "E:/data3.json";
            File file1 = new File("E:/finalRelic.json");
            FileWriter writer1 = new FileWriter(file1);
            locals = decode(filePath);
            Reader reader2 = new FileReader("E:/figures.json");
            Type listType2 = new TypeToken<List<Figure>>() {
            }.getType();
            figures = gson2.fromJson(reader2, listType2);
            /*for(int i = 0; i<locals.size();i++) {
            	if(locals.get(i).getProvince().contains("Ninh Bình")) locals.remove(i);
            	if(locals.get(i).getProvince().contains("Ninh Bình")) locals.remove(i);
            	if(locals.get(i).getProvince().contains("Ninh Bình")) locals.remove(i);

            }*/
            for (Relic loc : locals) {
            	
	                for (Figure figure : figures) {
	                	if (loc.getDesc() == null)
	                        break;
	                    if (normalizeString(loc.getDesc()).contains(normalizeString(figure.getName()))) {
	                        loc.addFigure(figure);                    }
	                }
	                fommatedLocal.add(loc);
            	}
            
            String json = gson3.toJson(fommatedLocal);
            writer1.write(json);
            writer1.close();
            reader2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}