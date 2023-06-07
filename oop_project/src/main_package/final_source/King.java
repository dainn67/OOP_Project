package main_package;

public class King extends Figure{
	private String thoiGianTriVi;
	
	public King() {};

	public King(String name, String otherName, String bornYear, String deathYear, Figure father, Figure mother, String dynasty, String home,
			String tri_vi, String desc) {
		super(name, otherName, bornYear, deathYear, father, mother, dynasty, home, desc);
		this.thoiGianTriVi = tri_vi;
	}
	
	public String getName() {return name;}
	public String getBornYear() {return bornYear;}
	public String getDeathYear() {return deathYear;}
	public String getDynasty() {return dynasty;}
	public Figure getFather() {return father;}
	public Figure getMother() {return mother;}
	public String getNamTriVi() {return thoiGianTriVi;}
}
