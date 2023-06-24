package objects;

import java.util.ArrayList;

public class Poinsettia extends Figure {
	String kingYear;
	String graduatedYear;
	
	public Poinsettia() {
	}

	public Poinsettia(
			String name,
			String otherName, // 0
			String bornYear, // 1
			String deathYear, // 2
			ArrayList<Figure> parents,
			Dynasty dynasty, // 5
			String home, // 6
			String kingYear, // 7
			String desc, // 8
			String graduatedYear
	) {
		super(name, otherName, bornYear, deathYear, parents, dynasty, home, desc);
		this.kingYear = kingYear;
		this.graduatedYear = graduatedYear;
	}
	
	public String getKingYear() {
		return kingYear;
	}
	
	public String getGraduatedYear() {
		return graduatedYear;
	}

	public void setKingYear(String kingYear) {
		this.kingYear = kingYear;
	}
	
	public void setGraduatedYear(String gradYear) {
		this.graduatedYear = gradYear;
	}
}
