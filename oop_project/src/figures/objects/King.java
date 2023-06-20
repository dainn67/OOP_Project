package objects;

public class King extends Figure {
	private String kingYear;
	private String mieuHieu;
	private String thuyHieu;
	private String nienHieu;
	private String tenHuy;
	private String theThu;

	public King() {
	};

	public King(
			String name,
			String otherName, 	// 0
			String bornYear,	// 1
			String deathYear,	// 2
			String father, 		// 3
			String mother,		// 4
			String dynasty, 	// 5
			String home,		// 6
			String kingYear, 	// 7
			String desc,		// 8
			String mieuHieu,	// 9
			String thuyHieu,	// 10
			String nienHieu,	// 11
			String tenHuy,		// 12
			String theThu		// 13
	) {
		super(name, otherName, bornYear, deathYear, father, mother, dynasty, home, desc);
		this.kingYear = kingYear;
		this.mieuHieu = mieuHieu;
		this.thuyHieu = thuyHieu;
		this.nienHieu = nienHieu;
		this.tenHuy = tenHuy;
		this.theThu = theThu;
	}

	public String getKingYear() {
		return kingYear;
	}
	
	public String getMieuHieu() {
		return mieuHieu;
	}
	
	public String getThuyHieu() {
		return thuyHieu;
	}
	
	public String getNienHieu() {
		return nienHieu;
	}
	
	public String getTenHuy() {
		return tenHuy;
	}
	
	public String getTheThu() {
		return theThu;
	}
	
	public void setKingYear(String kingYear) {
		this.kingYear = kingYear;
	}
	
	public void setMieuHieu(String mieuHieu) {
		this.mieuHieu = mieuHieu;
	}
	
	public void setThuyHieu(String thuyHieu) {
		this.thuyHieu = thuyHieu;
	}
	
	public void setNienHieu(String nienHieu) {
		this.nienHieu = nienHieu;
	}
	
	public void setTenHuy(String tenHuy) {
		this.tenHuy = tenHuy;
	}
	
	public void setTheThu(String theThu) {
		this.theThu = theThu;
	}
}