package relic.Object;

import figures.objects.Figure;

import java.util.ArrayList;

public class Relic extends Type {
    private String name;
    private String location;
    private String time;
    private String desc;
    private String id;
    private ArrayList<Figure> figures = new ArrayList<Figure>();

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setTime(String time) {
        this.time = time;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getDesc() {
        return this.desc;
    }
    
    public Relic() {
		super();
	}
    @Override
    public String toString() {
    	String strRelic = new String();
    	strRelic += "Name: " + this.name +"\n";
    	strRelic += "ID: " + this.id +"\n";
    	strRelic += "Location: " + this.location +"\n";
    	strRelic += "Time: " + this.time +"\n";
    	strRelic += "Description: " + this.desc +"\n";
    	return strRelic;
    }
}