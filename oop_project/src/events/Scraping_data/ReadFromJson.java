package Scraping_data;

import HistoricalEvent.Event;

import java.util.ArrayList;

public interface ReadFromJson {
    public ArrayList<Event> loadFromJson(String path);
}
