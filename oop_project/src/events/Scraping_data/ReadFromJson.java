package events.Scraping_data;

import events.HistoricalEvent.EventInit;

import java.util.ArrayList;

public interface ReadFromJson {
    public ArrayList<EventInit> loadFromJson(String path);
}
