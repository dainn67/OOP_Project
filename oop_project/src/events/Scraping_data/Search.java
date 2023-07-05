package Scraping_data;

import HistoricalEvent.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Search implements ReadFromJson {
    public Search() {
        this.resultList = new ArrayList<>();
    }

    private ArrayList<Event> resultList;

    private static String standardize(String input) {
        String query = input.toLowerCase().replaceAll(" ", "");
        String temp = Normalizer.normalize(query, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d");
    }

    public ArrayList<Event> eventSearch(String input) {
        String query = standardize(input);
        String currentDir = System.getProperty("user.dir");

        String path = currentDir + "\\" + "OOP_Project" + "\\" + "oop_project" + "\\" + "src" + "\\" + "events" + "\\" + "Resources" + "\\";

        String path1 = path + "eventsNKS.json";
        String path2 = path + "eventsWiki.json";
        ArrayList<Event> resourcesWiki = loadFromJson(path2);
        ArrayList<Event> resourcesNKS = loadFromJson(path1);
        for (Event e : resourcesWiki) {
            if (e.getId().contains(query)) {
                resultList.add(e);
            }
        }

        for (Event e : resourcesNKS) {
            if (e.getId().contains(query)) {
                resultList.add(e);
            }
        }
        return resultList;
    }

    public ArrayList<Event> loadFromJson(String path) {
        ArrayList<Event> resources = new ArrayList<Event>();
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(path)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JsonArray eventList = (JsonArray) obj;
//            System.out.println(eventList);

            //Iterate over event array
            eventList.forEach(e -> {
                JsonObject eventObj = (JsonObject) e;
//                System.out.println(eventObj.get("id"));
//                Event newEvent = new Event(eventObj.get("title").getAsString(),
//                        eventObj.get("time").getAsString(),
//                        eventObj.get("location").getAsString(),
//                        eventObj.get("dynasty").getAsString(),
//                        eventObj.get("description").getAsString());
                Event newEvent = new Event();
                newEvent.setName(eventObj.get("name").getAsString());
                newEvent.setTime(eventObj.get("time").getAsString());
                if (eventObj.get("location") != null) {
                    newEvent.setLocation(eventObj.get("location").getAsString());
                }
                if (eventObj.get("description") != null) {
                    newEvent.setDescription(eventObj.get("description").getAsString());
                }

                newEvent.setId(eventObj.get("id").getAsString());
//                Set<>
//                System.out.println(newEvent.getName());
                resources.add(newEvent);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
}
