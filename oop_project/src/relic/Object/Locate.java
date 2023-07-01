package relic.Object;

import java.util.ArrayList;

import objects.Figure;

public class Locate extends Type {
    private String relic;
    private String location;
    private String time;
    private String note;
    private String description;
    private String id;
    private ArrayList<Figure> figures = new ArrayList<Figure>();

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

    public void addFigure(Figure figure) {
        this.figures.add(figure);
    }

    public String getID() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }
}