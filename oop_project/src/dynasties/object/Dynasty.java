package objects;

import java.util.ArrayList;
import java.util.List;

public class Dynasty {
	String name;
	int startYear;
	int endYear;
	String desc;
//	ArrayList<King> kings = new ArrayList<King>();
	ArrayList<Figure> figures = new ArrayList<Figure>();
//	ArrayList<Poinsettia> poinsettias = new ArrayList<Poinsettia>();

	
	public Dynasty() {}
	
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
	
//	public void addKing(King king) {
//		kings.add(king);
//	}
//	
//	public void addPoinsettia(Poinsettia poin) {
//		poinsettias.add(poin);
//	}




}
