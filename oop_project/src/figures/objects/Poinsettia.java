package figures.objects;

import java.util.ArrayList;

public class Poinsettia extends Figure {
	String king;
	String graduatedYear;
	
	public Poinsettia() {
	}

	public Poinsettia(
			String id,
			String name,
			String otherName, 	// 0
			int bornYear, 	// 1
			int deathYear, 	// 2
			ArrayList<String> parents,
			ArrayList<String> dynasties, 	// 5
			String home, 		// 6
			String king, 	// 7
			String desc, 		// 8
			String graduatedYear
	) {
		super(id,name, otherName,bornYear,deathYear,parents,dynasties, home, desc);
		this.king = king;
		
		this.graduatedYear = graduatedYear;
	}
	
	public Poinsettia(
			String id,
			String name,
			String otherName, 	// 0
			int bornYear, 	// 1
			int deathYear, 	// 2// 5
			String home, 		// 6
			String kingYear, 	// 7
			String desc, 		// 8
			String graduatedYear
	) {
		super(id,name, otherName,bornYear,deathYear, home, desc);
		this.king = kingYear;
		this.graduatedYear = graduatedYear;
	}
	
	public String getKingYear() {
		return king;
	}
	
	public String getGraduatedYear() {
		return graduatedYear;
	}

	public void setKingYear(String kingYear) {
		this.king = kingYear;
	}
	
	public void setGraduatedYear(String gradYear) {
		this.graduatedYear = gradYear;
	}
}
