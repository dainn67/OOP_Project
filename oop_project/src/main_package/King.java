package main_package;

public class King extends Figure{
	private String trieu_dai;
	private String thoiGianTriVi;
	
	public King() {
		super();
	};

	public King(String ten, String sinh, String mat, String trieu_dai, String cha, String me,
			String tri_vi, String desc) {
		super(ten, sinh, mat, cha, me, desc);
		this.trieu_dai = trieu_dai;
		this.thoiGianTriVi = tri_vi;
	}
	
	public String getTen() {return ten;}
	public String getNamSinh() {return sinh;}
	public String getNamMat() {return mat;}
	public String getTrieu() {return trieu_dai;}
	public String getCha() {return cha;}
	public String getMe() {return me;}
	public String getNamTriVi() {return thoiGianTriVi;}
}
