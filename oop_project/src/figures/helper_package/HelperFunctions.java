package helper_package;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import objects.Dynasty;
import objects.Figure;
import objects.King;
import objects.Poinsettia;

public class HelperFunctions {

	public static List<String> cities = Arrays.asList("An Giang", "Bạc Liêu", "Bắc Giang", "Bắc Kạn",
			"Bắc Ninh", "Bến Tre", "Bình Dương", "Bình Định", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng",
			"Cần Thơ", "Đà Nẵng", "Dak Lak", "Dak Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang",
			"Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hoà Bình", "Huế", "Hưng Yên",
			"Hồ Chí Minh", "Khánh Hoà", "Kiên Giang", "Kon Tum", "Lai Châu", "Lạng Sơn", "Lào Cai", "Lâm Đồng",
			"Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình",
			"Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình",
			"Thái Nguyên", "Thanh Hoá", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long",
			"Vĩnh Phúc", "Yên Bái", "Thừa Thiên", "Thăng Long");


	public static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-").replace(" � ", "-");
	}
	public static void prtFigureAttributes(Figure myFigure) {
		System.out.println(myFigure.getName());
		System.out.println("	Ten khac: " + myFigure.getOtherName());
		System.out.println("	Sinh: " + myFigure.getBornYear());
		System.out.println("	Mat: " + myFigure.getDeathYear());
		System.out.println("	Cha: " + myFigure.getFather());
		System.out.println("	Me: " + myFigure.getMother());
		System.out.println("	Trieu dai: " + myFigure.getDynasty());
		System.out.println("	Que quan: " + myFigure.getHome());
		System.out.println("	Mo ta: " + myFigure.getDesc());
	}

	public static void prtPointessiaAttributeas(Poinsettia myFigure) {
		System.out.println(myFigure.getName());
		System.out.println("	Ten khac: " + myFigure.getOtherName());
		System.out.println("	Sinh: " + myFigure.getBornYear());
		System.out.println("	Mat: " + myFigure.getDeathYear());
		System.out.println("	Cha: " + myFigure.getFather());
		System.out.println("	Me: " + myFigure.getMother());
		System.out.println("	Trieu dai: " + myFigure.getDynasty());
		System.out.println("	Que quan: " + myFigure.getHome());
		System.out.println("	Trieu vua: " + myFigure.getKingYear());
		System.out.println("	Nam do trang nguyen: " + myFigure.getGraduatedYear());
		System.out.println("	Mo ta: " + myFigure.getDesc());
	}

	public static void prtDynastyAttributes(Dynasty dynasty) {
		System.out.println(dynasty.getName());
		System.out.println("	" + dynasty.getStartYear());
		System.out.println("	" + dynasty.getEndYear());
		System.out.println("	" + dynasty.getDesc());
	}
	
	public static void prtKingAttributes(King king) {
		System.out.println(king.getName());
		System.out.println("    Sinh: " + king.getBornYear());
		System.out.println("    Mat: " + king.getDeathYear());
		System.out.println("    Trieu dai: " + king.getDynasty());
		System.out.println("    Que quan: " + king.getHome());
		System.out.println("    Cha: " + king.getFather());
		System.out.println("    Me: " + king.getMother());
		System.out.println("    Mo ta: " + king.getDesc());
		System.out.println("    Mieu hieu: " + king.getMieuHieu());
		System.out.println("    Thuy hieu: " + king.getThuyHieu());
		System.out.println("    Nien hieu: " + king.getNienHieu());
		System.out.println("    Ten huy: " + king.getTenHuy());
		System.out.println("	The thu: " + king.getTheThu());
		System.out.println("	Nam tri vi: " + king.getKingYear());
	}

	// not use
	public static List<Object> searchSortedDuplicates(List<Object> sortedList, String target) {
		List<Object> resSObjs = new ArrayList<>();
		Object sample = sortedList.get(0);

		int left = 0;
		int right = sortedList.size() - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2;

			if (sample instanceof Figure) {
				Figure tmpFigure = (Figure) sortedList.get(mid);
				if (normalizeString(tmpFigure.getName()).compareTo(normalizeString(target)) == 0) {
					resSObjs.add(tmpFigure);

					int index = mid - 1;
					Figure nextFigure = (Figure) sortedList.get(index);
					while (index >= 0
							&& normalizeString(nextFigure.getName()).compareTo(normalizeString(target)) == 0) {
						resSObjs.add(nextFigure);
						index--;
						nextFigure = (Figure) sortedList.get(index);
					}

					index = mid + 1;
					nextFigure = (Figure) sortedList.get(index);
					while (index < sortedList.size()
							&& normalizeString(nextFigure.getName()).compareTo(normalizeString(target)) == 0) {
						resSObjs.add(nextFigure);
						index++;
						nextFigure = (Figure) sortedList.get(index);
					}

					return resSObjs;
				} else if (normalizeString(((Figure) sortedList.get(mid)).getName())
						.compareTo(normalizeString(target)) < 0) {
					left = mid + 1;
				} else {
					right = mid - 1;
				}
			}
			// if list is long then add instance
		}

		// No match found
		return resSObjs;
	}

	public static List<Figure> searchFigureLinear(List<Figure> sortedList, String name) {
		List<Figure> resList = new ArrayList<>();
		List<Figure> resListNormalized = new ArrayList<>();
		for (Figure figure : sortedList)
			if (figure.getName().equals(name))
				resList.add(figure);
			else if (normalizeString(figure.getName()).contains(normalizeString(name)))
				resListNormalized.add(figure);
		if (!resList.isEmpty())
			return resList;
		return resListNormalized;
	}

	public static List<Dynasty> searchDynastyLinear(List<Dynasty> sortedList, String name) {
		List<Dynasty> resList = new ArrayList<>();
		for (Dynasty dynasty : sortedList) {
			String dName = normalizeString(dynasty.getName());
			if (dName.equals(normalizeString(name)) || dName.contains(normalizeString(name)))
				resList.add(dynasty);
		}
		return resList;
	}
	
	public static String parseYear(String _input) {
		String input = normalizeString(_input);
        Pattern pattern = Pattern.compile("\\d+"); // Regular expression to match one or more digits
        Matcher matcher = pattern.matcher(input);
        
        int largestNumber = 0; // Default value if no numbers are found
        
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            if (number > largestNumber) {
                largestNumber = number;
            }
        }
        
        if(input.contains("TCN")) largestNumber = -largestNumber;
        
        return largestNumber == 0 ? "Không rõ" : largestNumber + "";
    }

//	public static void main(String[] args) {
//		List<Figure> figureList = EncodeDecode.decodedFigureList();
//		List<Dynasty> dynastyList = EncodeDecode.decodedDynastyList(true);
//
//		String searchName = "Nguyen Du";
//		List<Figure> resFig = searchFigureLinear(figureList, searchName);
//		if (resFig.isEmpty())
//			System.out.println("Figure not found");
//		else {
//			for (Figure figure : resFig)
//				prtFigureAttributes(figure);
//		}
//
//		String a = "ngày 19 tháng 5 năm 1890";
//		String b = "tháng 4 năm 6000 TCN";
//		String c = "Không rõ";
//
//		System.out.println(parseYear(a));
//		System.out.println(parseYear(b));
//		System.out.println(parseYear(c));
//	}
}
