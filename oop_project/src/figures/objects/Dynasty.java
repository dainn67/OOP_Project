package objects;

import java.util.ArrayList;
import java.util.List;

public class Dynasty {
	private String name;
	private int startYear;
	private int endYear;
	private String desc;
	private ArrayList<King> kings = new ArrayList<King>();
	private ArrayList<Figure> figures = new ArrayList<Figure>();
	
	public Dynasty() {}
	
	public Dynasty(String name, int startYear, int endYear, String desc) {
		this.name = name;
		this.startYear = startYear;
		this.endYear = endYear;
		this.desc = desc;
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
	
	public ArrayList<King> getListKings() {
		return kings;
	}
	
	public ArrayList<Figure> getListFigures() {
		return figures;
	}
	
	public void addFigure(Figure fig) {
		figures.add(fig);
	}
	
	public void addKing(King king) {
		kings.add(king);
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
}