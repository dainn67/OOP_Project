package objects;

import java.util.ArrayList;

public class Figure {
	protected String name;
	protected String otherName;
	protected int bornYear;
	protected int deathYear;
	ArrayList<Figure> parents = new ArrayList<>();
	Dynasty dynasty = null;
	protected String home;
	protected String desc;

	public Figure() {
	}

	public Figure(String name, String otherName, int bornYear, int deathYear,
			String home, String desc) {
		this.name = name; 				// 0 ok
		this.otherName = otherName; 	// 1 ok
		this.bornYear = bornYear; 		// 2 ok
		this.deathYear = deathYear; 	// 3 ok
		this.home = home; 				// 4 ok
		this.desc = desc; 				// 5 ok
	}

	public String getName() {
		return name;
	}
	
	public String getOtherName() {
		return otherName;
	}

	public int getBornYear() {
		return bornYear;
	}

	public int getDeathYear() {
		return deathYear;
	}

	public Dynasty getDynasty() {
		return dynasty;
	}

	public ArrayList<Figure> getParents() {
		return parents;
	}
	
	public String getHome() {
		return home;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	
	public void setBornYear(int bornYear) {
		this.bornYear = bornYear;
	}
	
	public void setDeathYear(int deathYear) {
		this.deathYear = deathYear;
	}
	
	public void setParents(ArrayList<Figure> parents) {
		this.parents = parents;
	}
	
	public void setDynasty(Dynasty dynasty) {
		this.dynasty = dynasty;
	}
	
	public void setHome(String home) {
		this.home = home;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
