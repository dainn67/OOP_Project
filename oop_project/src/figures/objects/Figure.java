package objects;

import java.util.ArrayList;

public class Figure {
	protected String id;
	protected String name;
	protected String otherName;
	protected int bornYear;
	protected int deathYear;
	protected ArrayList<String> parents = new ArrayList<>();
	protected ArrayList<String> dynasties = new ArrayList<>();
	protected String home;
	protected String desc;

	public Figure() {
	}

	public Figure(String id, String name, String otherName, int bornYear, int deathYear,
			String home, String desc) {
		this.id = id;
		this.name = name; 				// 0 ok
		this.otherName = otherName; 	// 1 ok
		this.bornYear = bornYear; 		// 2 ok
		this.deathYear = deathYear; 	// 3 ok
		this.home = home; 				// 4 ok
		this.desc = desc; 				// 5 ok
	}
	
	public String getId() {
		return id;
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

	public ArrayList<String> getDynasties() {
		return dynasties;
	}

	public ArrayList<String> getParents() {
		return parents;
	}
	
	public String getHome() {
		return home;
	}
	
	public String getDesc() {
		return desc;
	}
	
	
	//setters
	public void setId(String id) {
		this.id = id;
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
	
	public void setParents(ArrayList<String> parents) {
		this.parents = parents;
	}
	
	public void setDynasties(ArrayList<String> dynasties) {
		this.dynasties = dynasties;
	}
	
	public void setHome(String home) {
		this.home = home;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
