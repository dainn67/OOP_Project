package Scraping_data;

import ExternalObj.Dynasty;
import ExternalObj.Figure;
import ExternalObj.King;
import ExternalObj.Poinsettia;
import HistoricalEvent.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Decode {

    public static ArrayList<Event> decode(String path) {
        ArrayList<Event> resources = new ArrayList<Event>();
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(path)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JsonArray eventList = (JsonArray) obj;

            //Iterate over Event array
            eventList.forEach(e -> {
                JsonObject eventObj = (JsonObject) e;

                Event newEvent = new Event();
                newEvent.setId(eventObj.get("id").getAsString());
                newEvent.setName(eventObj.get("name").getAsString());
                newEvent.setTime(eventObj.get("time").getAsString());
                if (eventObj.get("location") != null) {
                    newEvent.setLocation(eventObj.get("location").getAsString());
                }
                if (eventObj.get("description") != null) {
                    newEvent.setDescription(eventObj.get("description").getAsString());
                }
                Dynasty d = new Dynasty();

                if(eventObj.get("dynastyDetails") != null){
                    JsonObject dObj = eventObj.get("dynastyDetails").getAsJsonObject();
                    d.setName(dObj.get("name").getAsString());
                    d.setStartYear(Integer.parseInt(dObj.get("startYear").getAsString()));
                    if(dObj.get("endYear") != null){
                        if(dObj.get("endYear").getAsString().contains("TCN")){
                            String temp = dObj.get("endYear").getAsString().split(" ")[0];
                            d.setEndYear(Integer.parseInt(temp) * -1);
                        }else{
                            d.setEndYear(Integer.parseInt(dObj.get("endYear").getAsString()));
                        }
                    }
                    d.setDesc(dObj.get("desc").getAsString());
                }
                newEvent.setDynastyDetails(d);
                ArrayList<Figure> relatedFigs = new ArrayList<>();

                if(eventObj.get("relatedFigures") != null){
                    if(!eventObj.get("relatedFigures").getAsJsonArray().isEmpty()){
                        JsonArray figs = (JsonArray) eventObj.get("relatedFigures").getAsJsonArray();
                        // iterate over figs of an event
                        Set<String> figuresStr = new HashSet<>();
                        figs.forEach(f -> {
                            String preProcess = f.toString();
                            if(preProcess.length() <= 3){
                                return;
                            }
                            figuresStr.add(f.getAsString());
//                            System.out.println(f.getAsString());
                        });
                        if(figuresStr.size() > 0){
                            newEvent.setRelatedFigure(figuresStr);
                        }
                    }
                }

                if(eventObj.get("relatedFigsDetails") != null){
                    if(!eventObj.get("relatedFigsDetails").getAsJsonArray().isEmpty()){
                        JsonArray figs = (JsonArray) eventObj.get("relatedFigsDetails").getAsJsonArray();
//                        ArrayList<Figure> relatedFigsObj = new ArrayList<>();
                        ArrayList<Figure> resList = new ArrayList<>();


                        figs.forEach(f -> {
                            JsonObject figObject = (JsonObject) f;
                            if (figObject.get("nienHieu") != null) {
                                King tmpKing = new King();
                                tmpKing.setId(figObject.get("id").getAsString());
                                tmpKing.setBornYear(figObject.get("bornYear").getAsInt());
                                tmpKing.setName(figObject.get("name").getAsString());
                                tmpKing.setKingYear(figObject.get("kingYear").getAsString());
                                tmpKing.setDesc(figObject.get("desc").getAsString());
//					tmpKing.setMieuHieu(figObject.get("mieuHieu").getAsString());
                                tmpKing.setThuyHieu(figObject.get("thuyHieu").getAsString());
                                tmpKing.setNienHieu(figObject.get("nienHieu").getAsString());
//					tmpKing.setTenHuy(figObject.get("tenHuy").getAsString());
                                tmpKing.setTheThu(figObject.get("theThu").getAsString());
                                JsonArray parentsArray = figObject.getAsJsonArray("parents");
                                ArrayList<String> parents = new ArrayList<>();
                                for (JsonElement parent : parentsArray) {
                                    parents.add(parent.getAsString());
                                }
                                tmpKing.setParents(parents);
                                JsonArray dynastiesArray = figObject.getAsJsonArray("dynasties");
                                ArrayList<String> dynasties = new ArrayList<>();
                                for (JsonElement dynasty : dynastiesArray) {
                                    dynasties.add(dynasty.getAsString());
                                }
                                tmpKing.setDynasties(dynasties);
                                resList.add(tmpKing);
                            } else if (figObject.get("graduatedYear") != null) {
                                Poinsettia poin = new Poinsettia();
                                poin.setId(figObject.get("id").getAsString());
                                poin.setBornYear(figObject.get("bornYear").getAsInt());
                                poin.setName(figObject.get("name").getAsString());
                                poin.setHome(figObject.get("home").getAsString());
                                poin.setKingYear(figObject.get("king").getAsString());
                                poin.setDesc(figObject.get("desc").getAsString());
                                poin.setGraduatedYear(figObject.get("graduatedYear").getAsString());
                                JsonArray parentsArray = figObject.getAsJsonArray("parents");
                                ArrayList<String> parents = new ArrayList<>();
                                for (JsonElement parent : parentsArray) {
                                    parents.add(parent.getAsString());
                                }
                                poin.setParents(parents);
                                JsonArray dynastiesArray = figObject.getAsJsonArray("dynasties");
                                ArrayList<String> dynasties = new ArrayList<>();
                                for (JsonElement dynasty : dynastiesArray) {
                                    dynasties.add(dynasty.getAsString());
                                }
                                poin.setDynasties(dynasties);
                                resList.add(poin);
                            } else {
                                Figure newFig = new Figure();
                                newFig.setId(figObject.get("id").getAsString());
                                newFig.setBornYear(figObject.get("bornYear").getAsInt());
                                newFig.setName(figObject.get("name").getAsString());
                                newFig.setOtherName(figObject.get("otherName").getAsString());
                                newFig.setDeathYear(figObject.get("deathYear").getAsInt());
                                newFig.setHome(figObject.get("home").getAsString());
                                newFig.setDesc(figObject.get("desc").getAsString());
                                JsonArray parentsArray = figObject.getAsJsonArray("parents");
                                ArrayList<String> parents = new ArrayList<>();
                                for (JsonElement parent : parentsArray) {
                                    parents.add(parent.getAsString());
                                }
                                newFig.setParents(parents);
                                JsonArray dynastiesArray = figObject.getAsJsonArray("dynasties");
                                ArrayList<String> dynasties = new ArrayList<>();
                                for (JsonElement dynasty : dynastiesArray) {
                                    dynasties.add(dynasty.getAsString());
                                }
                                newFig.setDynasties(dynasties);
                                resList.add(newFig);
                            }
                        });
                        newEvent.setRelatedFigsDetails(resList);
                    }
                }
                resources.add(newEvent);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
}
