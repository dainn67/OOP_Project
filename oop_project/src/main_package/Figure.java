package main_package;

public class Figure {
	protected String ten;
	protected String tenKhac;
	protected String sinh;
	protected String mat;
	protected String cha;
	protected String me;
	protected String trieuDai;
	protected String queQuan;
	protected String desc;
	
	public Figure() {}
	
	public Figure(String ten, String tenKhac, String sinh, String mat, String cha, String me, String trieuDai, String queQuan, String desc) {
		this.ten = ten;				//0
		this.tenKhac = tenKhac;		//1
		this.sinh = sinh;			//2
		this.mat = mat;				//3
		this.cha = cha;				//4
		this.me = me;				//5
		this.trieuDai = trieuDai;	//6
		this.queQuan = queQuan;		//7
		this.desc = desc;			//8
	}
}
