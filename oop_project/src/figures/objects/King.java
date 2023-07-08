package figures.objects;

public class King extends Figure {
	private String kingYear;
	private String thuyHieu;
	private String nienHieu;
	private String theThu;
	public King() {
	};
	public King(
			String id,
			String name,
			String otherName, 	// 0
			int bornYear,		// 1
			int deathYear,		// 2
			String home,		// 6
			String kingYear, 	// 7
			String desc,		// 8
//			String mieuHieu,	// 9
			String thuyHieu,	// 10
			String nienHieu,	// 11
//			String tenHuy,		// 12
			String theThu		// 13
	) {
		super(id, name, otherName, bornYear, deathYear, home, desc);
		this.kingYear = kingYear;
		this.thuyHieu = thuyHieu;
		this.nienHieu = nienHieu;
		this.theThu = theThu;
	}
	public String getKingYear() {
		return kingYear;
	}
	
	public String getThuyHieu() {
		return thuyHieu;
	}
	
	public String getNienHieu() {
		return nienHieu;
	}
	
	public String getTheThu() {
		return theThu;
	}
	
	
	//setter
	public void setKingYear(String kingYear) {
		this.kingYear = kingYear;
	}
	
	public void setThuyHieu(String thuyHieu) {
		this.thuyHieu = thuyHieu;
	}
	
	public void setNienHieu(String nienHieu) {
		this.nienHieu = nienHieu;
	}
	
	public void setTheThu(String theThu) {
		this.theThu = theThu;
	}
}



