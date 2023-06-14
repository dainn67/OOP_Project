package objects;

public class Figure {
	protected String name;
	protected String otherName;
	protected String bornYear;
	protected String deathYear;
	protected String father;
	protected String mother;
	protected String dynasty;
	protected String home;
	protected String desc;

	public Figure() {
	}

	public Figure(String name, String otherName, String bornYear, String deathYear, String father, String mother,
			String dynasty, String home, String desc) {
		this.name = name; 				// 0 ok
		this.otherName = otherName; 	// 1 ok
		this.bornYear = bornYear; 		// 2 ok
		this.deathYear = deathYear; 	// 3 ok
		this.father = father; 			// 4 ok
		this.mother = mother; 			// 5 ok
		this.dynasty = dynasty; 		// 6 hmmm...
		this.home = home; 				// 7 ok
		this.desc = desc; 				// 8 ok
	}

	public String getName() {
		return name;
	}
	
	public String getOtherName() {
		return otherName;
	}

	public String getBornYear() {
		return bornYear;
	}

	public String getDeathYear() {
		return deathYear;
	}

	public String getDynasty() {
		return dynasty;
	}

	public String getFather() {
		return father;
	}

	public String getMother() {
		return mother;
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
	
	public void setBornYear(String bornYear) {
		this.bornYear = bornYear;
	}
	
	public void setDeathYear(String deathYear) {
		this.deathYear = deathYear;
	}
	
	public void setFather(String father) {
		this.father = father;
	}
	
	public void setMother(String mother) {
		this.mother = mother;
	}
	
	public void setDynasty(String dynasty) {
		this.dynasty = dynasty;
	}
	
	public void setHome(String home) {
		this.home = home;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
