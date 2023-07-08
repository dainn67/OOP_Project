package events.Scraping_data;

import events.HistoricalEvent.EventInit;
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

    private ArrayList<EventInit> resultList;

    private static String standardize(String input) {
        String query = input.toLowerCase().replaceAll(" ", "");
        String temp = Normalizer.normalize(query, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d");
    }

    public ArrayList<EventInit> eventSearch(String input) {
        String query = standardize(input);
        String currentDir = System.getProperty("user.dir");

        String path = currentDir + "\\" + "OOP_Project" + "\\" + "oop_project" + "\\" + "src" + "\\" + "events" + "\\" + "Resources" + "\\";

        String path1 = path + "eventsNKS.json";
        String path2 = path + "eventsWiki.json";
        ArrayList<EventInit> resourcesWiki = loadFromJson(path2);
        ArrayList<EventInit> resourcesNKS = loadFromJson(path1);
        for (EventInit e : resourcesWiki) {
            if (e.getId().contains(query)) {
                resultList.add(e);
            }
        }

        for (EventInit e : resourcesNKS) {
            if (e.getId().contains(query)) {
                resultList.add(e);
            }
        }
        return resultList;
    }

    public ArrayList<EventInit> loadFromJson(String path) {
        ArrayList<EventInit> resources = new ArrayList<EventInit>();
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(path)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JsonArray eventList = (JsonArray) obj;
//            System.out.println(eventList);

            //Iterate over EventInit array
            eventList.forEach(e -> {
                JsonObject eventObj = (JsonObject) e;
//                System.out.println(eventObj.get("id"));
//                EventInit newEvent = new EventInit(eventObj.get("title").getAsString(),
//                        eventObj.get("time").getAsString(),
//                        eventObj.get("location").getAsString(),
//                        eventObj.get("dynasty").getAsString(),
//                        eventObj.get("description").getAsString());
                EventInit newEvent = new EventInit();
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
