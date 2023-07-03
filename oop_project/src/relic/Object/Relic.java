package relic.Object;

import java.util.ArrayList;

import Figures.Figure;

public class Relic extends Type {
    private String namerelic;
    private String location;
    private String time;
    private String note;
    private String description;
    private String id;
    private ArrayList<Figure> figures = new ArrayList<Figure>();

    public void setNameRelic(String namerelic) {
        this.namerelic = namerelic;
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
    public Relic() {
		super();
	}
    @Override
    public String toString() {
    	String strRelic = new String();
    	strRelic += "Name: " + this.namerelic +"\n";
    	strRelic += "ID: " + this.id +"\n";
    	strRelic += "Location: " + this.location +"\n";
    	strRelic += "Time: " + this.time +"\n";
    	strRelic += "Description: " + this.description +"\n";
    	return strRelic;
    }
}