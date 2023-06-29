package Relic.Object;

import figures.objects.Figure;

public class Locate extends Type {
    private String relic;
    private String location;
    private String time;
    private String note;
    private String description;
    private String id;
    private Figure figure;

    public void setRelic(String relic) {
        this.relic = relic;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDescripton(String description) {
        this.description = description;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public String getID() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }
}