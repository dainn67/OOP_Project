package HistoricalEvent;

import java.util.Set;

public class Event {
    private String id;
    private String title;
    private String time;
    private String location;
    private Set<String> relatedFigures;
    private String dynasty;
    private String description;
    public Event(){}
    public Event(String title, String time, String location) {
        this.title = title;
        this.time = time;
        this.location = location;
    }
    public Event(String title, String time, String location, String description){
        this.title = title;
        this.time = time;
        this.location = location;
        this.description = description;
    }
    @Override
    public String toString(){
        return this.title + "\n" + this.time + "\n" + this.location + "\n" + this.description + "\n"
                + this.dynasty + "\n" + this.relatedFigures;
    }
    public void addDescription(String description){
        this.setDescription(getDescription() + "\n" + description);
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Set<String> getRelatedFigure() {
        return relatedFigures;
    }
    public void setRelatedFigure(Set<String> relatedFigure) {
        this.relatedFigures = relatedFigure;
    }
    public String getDynasty() {
        return dynasty;
    }
    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
