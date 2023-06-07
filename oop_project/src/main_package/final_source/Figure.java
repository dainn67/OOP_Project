package main_package;

import java.util.List;

public class Figure {
	protected String name;
	protected String otherName;
	protected String bornYear;
	protected String deathYear;
	protected Figure father;
	protected Figure mother;
	protected String dynasty;
	protected String home;
	protected String desc;
	
	public Figure() {}
	
	public Figure(String name, String otherName, String bornYear, String deathYear, Figure father, Figure mother, String dynasty, String home, String desc) {
		this.name = name;					//0		ok
		this.otherName = otherName;			//1		ok
		this.bornYear = bornYear;			//2		ok
		this.deathYear = deathYear;			//3		ok
		this.father = father;				//4
		this.mother = mother;				//5
		this.dynasty = dynasty;				//6		hmmm...
		this.home = home;					//7		ok
		this.desc = desc;					//8		ok
	}
	
	public String getName() {return name;}
	public String getBornYear() {return bornYear;}
	public String getDeathYear() {return deathYear;}
	public String getDynasty() {return dynasty;}
	public Figure getFather() {return father;}
	public Figure getMother() {return mother;}
}
