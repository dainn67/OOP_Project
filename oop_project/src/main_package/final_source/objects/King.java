package objects;

public class King extends Figure {
	private String kingYear;

	public King() {
	};

	public King(String name, String otherName, // 0
			String bornYear, // 1
			String deathYear, // 2
			String father, // 3
			String mother, // 4
			String dynasty, // 5
			String home, // 6
			String kingYear, // 7
			String desc // 8
	) {
		super(name, otherName, bornYear, deathYear, father, mother, dynasty, home, desc);
		this.kingYear = kingYear;
	}

	public String getKingYear() {
		return kingYear;
	}
}
