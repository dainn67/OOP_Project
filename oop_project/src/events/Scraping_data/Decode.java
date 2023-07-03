package Scraping_data;

import HistoricalEvent.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Decode {

    public static ArrayList<Event> decode(String path) {
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
                newEvent.setName(eventObj.get("title").getAsString());
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
