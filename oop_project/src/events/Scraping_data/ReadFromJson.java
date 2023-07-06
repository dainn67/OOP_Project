package Scraping_data;

import HistoricalEvent.EventInit;

import java.util.ArrayList;

public interface ReadFromJson {
    public ArrayList<EventInit> loadFromJson(String path);
}
