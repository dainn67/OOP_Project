package HistoricalEvent;

import java.util.HashSet;
import java.util.Set;

public class Event {
    private String id;
    private String title;
    private String time;
    private String location;
    private Set<String> relatedFigures;
    private String dynasty;
    private String description;
    public Event(){
        this.relatedFigures = new HashSet<>();
    }
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

    // not yet finished
    public int takeYear(){
        String time = this.getTime();
        int year = 0;
        int startIndex = time.length()-1;
        int flag = 0;
        int endIndex = time.length()-1;
        for(int i = time.length()-1; i >= 0; i --){
            if(!Character.isDigit(time.charAt(i))){
                if(flag == 0){
                    endIndex = i;
                    flag = 1;
                }else if(flag == 1){
                    startIndex = i+1;
                    break;
                }
            }else {
                continue;
            }
        }
        year = Integer.parseInt(time.substring(startIndex, endIndex));
        return 1000;
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
    public void setRelatedFigures(String fig){
        this.relatedFigures.add(fig);
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
