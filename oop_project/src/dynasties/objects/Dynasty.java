package dynasties.objects;

import java.util.ArrayList;

import figures.objects.Figure;

public class Dynasty {
	String name;
	int startYear;
	int endYear;
	String desc;
	ArrayList<Figure> figures = new ArrayList<Figure>();

	public Dynasty() {
	}

	public Dynasty(String name, int startYear, int endYear, String desc) {
		this.name = name;
		this.startYear = startYear;
		this.endYear = endYear;
		this.desc = desc;
	}

	public Dynasty(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getStartYear() {
		return startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public String getDesc() {
		return desc;
	}
	
	public ArrayList<Figure> getFigures() {
		return figures;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void addFigure(Figure fig) {
		figures.add(fig);
	}
}