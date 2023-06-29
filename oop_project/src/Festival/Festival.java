package Festival;

import java.util.ArrayList;

import Figures.Figure;

public class Festival {
	private String id;
	private String name;
	private String place;
	private String startTime;
	private String firstTimes;
	private String[] relatedCharacter = new String[10];
	private ArrayList<Figure> relatedCharacterFigure = new ArrayList<Figure>();
	private ArrayList<String> decription = new ArrayList<String>();
	

	public Festival() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Festival(String name, String place) {
		super();
		this.name = name;
		this.place = place;
	}

	public Festival(String name, String place, String startTime) {
		super();
		this.name = name;
		this.place = place;
		this.startTime = startTime;
	}
	public Festival(String name, String place, String startTime, String firstTimes, ArrayList<Figure> relatedCharacterFigure, String[] relatedCharacter, 
			ArrayList<String> decription) {
		super();
		this.name = name;
		this.place = place;
		this.startTime = startTime;
		this.firstTimes = firstTimes;
		this.relatedCharacter = relatedCharacter;
		this.decription = decription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFirstTimes() {
		return firstTimes;
	}

	public void setFirstTimes(String firstTimes) {
		this.firstTimes = firstTimes;
	}

	public ArrayList<Figure> getRelatedCharacterFigure() {
		return relatedCharacterFigure;
	}

	public void setRelatedCharacter(String[] rc) {
		this.relatedCharacter = rc;
	}
	
	public String[] getRelatedCharacter() {
		return relatedCharacter;
	}

	public void setRelatedCharacterFigure(ArrayList<Figure> rc) {
		this.relatedCharacterFigure = rc;
	}

	public ArrayList<String> getDecription() {
		return decription;
	}
	
	public void addDescription(String a) {
		decription.add(a);
	}

}
