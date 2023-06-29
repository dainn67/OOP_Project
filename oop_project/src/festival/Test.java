package festival;

import java.util.ArrayList;

public class Test {
	public static final String FILE_ADDRESS = "E:/Festival.json";

	public static void main(String[] args) {
		ArrayList<Festival> festivals = new ArrayList<Festival>();
		festivals = Utils.festivalDecode();
		ArrayList<Festival> fes = new ArrayList<Festival>();
		fes = Utils.festivalSearch("le hoi", festivals);
		for (Festival festival : fes) {
			System.out.println(festival.getName());
		}

//		ArrayList<Figure> figures = new ArrayList<Figure>();
//		figures = Utils.figureDecode();

	}

}
