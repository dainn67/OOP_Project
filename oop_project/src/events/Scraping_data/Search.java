package events.Scraping_data;

import events.HistoricalEvent.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class Search {
    private ArrayList<Event> defaultEvent;

    private static String standardize(String input) {
        String query = input.toLowerCase().replaceAll(" ", "");
        String temp = Normalizer.normalize(query, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d");
    }

    public static ArrayList<Event> eventSearch(String input) {
        String query = standardize(input);
        String path1 = "./OOP_Project/OOP_Project/oop_project/src/events/Resources/events.json";
        String path2 = "./OOP_Project/OOP_Project/oop_project/src/events/Resources/eventsWiki.json";
        ArrayList<Event> resultList = new ArrayList<Event>();
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

    public static ArrayList<Event> loadFromJson(String path) {
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
                newEvent.setTitle(eventObj.get("title").getAsString());
                newEvent.setTime(eventObj.get("time").getAsString());
                if (eventObj.get("location") != null) {
                    newEvent.setLocation(eventObj.get("location").getAsString());
                }
                if (eventObj.get("description") != null) {
                    newEvent.setDescription(eventObj.get("description").getAsString());
                }

                newEvent.setId(eventObj.get("id").getAsString());
//                Set<>
                resources.add(newEvent);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
}
