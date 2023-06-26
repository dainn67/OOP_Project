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
		System.out.println("	id: " + myFigure.getId());
		System.out.println("	Ten khac: " + myFigure.getOtherName());
		System.out.println("	Sinh: " + myFigure.getBornYear());
		System.out.println("	Mat: " + myFigure.getDeathYear());
		if(myFigure.getDynasties() != null && !myFigure.getDynasties().isEmpty())
			for(String dynastyName: myFigure.getDynasties())
				System.out.println("	Trieu dai: " + dynastyName);
		System.out.println("	Que quan: " + myFigure.getHome());
		System.out.println("	Mo ta: " + myFigure.getDesc());
		if(myFigure.getParents() != null && !myFigure.getParents().isEmpty())
			for(String parent: myFigure.getParents())
				System.out.println("	" + parent);
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
		System.out.println(HelperFunctions.normalizeString(dynasty.getName()));
		System.out.println("	" + dynasty.getStartYear());
		System.out.println("	" + dynasty.getEndYear());
		System.out.println("	" + dynasty.getDesc());
	}
	
	public static void prtKingAttributes(King king) {
		System.out.println(king.getName());
		System.out.println("    Id: " + king.getId());
		System.out.println("    Sinh: " + king.getBornYear());
		System.out.println("    Mat: " + king.getDeathYear());
		System.out.println("    Que quan: " + king.getHome());
		System.out.println("    Mo ta: " + king.getDesc());
		System.out.println("    Mieu hieu: " + king.getMieuHieu());
		System.out.println("    Thuy hieu: " + king.getThuyHieu());
		System.out.println("    Nien hieu: " + king.getNienHieu());
		System.out.println("    Ten huy: " + king.getTenHuy());
		System.out.println("	The thu: " + king.getTheThu());
		System.out.println("	Nam tri vi: " + king.getKingYear());
	}
	
	public static int parseYear(String _input) {
		if(_input == null) return 0;
		
		String input = normalizeString(_input);
		// Regular expression to match one or more digits
        Pattern pattern = Pattern.compile("\\d+"); 
        Matcher matcher = pattern.matcher(input);
        
        int largestNumber = 0; // Default value if no numbers are found
        
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            if (number > largestNumber) {
                largestNumber = number;
            }
        }
        
        if(input.contains("TCN")) largestNumber *= -1;
        
        return largestNumber;
    }

	public static ArrayList<String> extractDynasty(String input, ArrayList<Dynasty> refDynastiesList) {
		ArrayList<String> resDynasties = new ArrayList<>();

		if (input.contains("vua Lê chúa Trịnh")) {
			for (Dynasty dynasty : refDynastiesList) {
				if (dynasty.getName().equals("Trịnh Nguyễn Phân Tranh"))
					resDynasties.add(dynasty.getName());
			}
		}

		String pattern = "(?:Nhà|nhà|triều đại|triều) ([A-ZÀ-Ỹ][a-zà-ỹ]+(?: [A-ZÀ-Ỹ][a-zà-ỹ]+)*)";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(input);

		if (matcher.find()) {
			if (!(matcher.group(1).contains("đình") || matcher.group(1).contains("nho")
					|| matcher.group(1).contains("Nho") || matcher.group(1).contains("đại"))) {
				String targetName = matcher.group(1).toLowerCase();
				boolean found = false;
				for (Dynasty dynasty : refDynastiesList)
					if (dynasty.getName().toLowerCase().contains(targetName)) {
						found = true;
						resDynasties.add(dynasty.getName());
					}
				if (!found)
					System.out.println("Unknown: " + HelperFunctions.normalizeString(targetName));
			}
		}

		return resDynasties;
	}
	
	public static ArrayList<String> extractParentsName(String input) {
	    ArrayList<String> names = new ArrayList<>();

	    String pattern = "(?:con của) ([A-ZÀ-Ỹ][a-zà-ỹ]+(?: [A-ZÀ-Ỹ][a-zà-ỹ]+)*) (?:và) ([A-ZÀ-Ỹ][a-zà-ỹ]+(?: [A-ZÀ-Ỹ][a-zà-ỹ]+)*)";
	    Pattern regex = Pattern.compile(pattern);
	    Matcher matcher = regex.matcher(input);

	    if (matcher.find()) {
	        String name1 = matcher.group(1).trim();
	        names.add(name1);
	        String name2 = matcher.group(2).trim();
	        names.add(name2);
	        return names;
	    }
	    
	    pattern = "(?:cha là) ([A-ZÀ-Ỹ][a-zà-ỹ]+(?: [A-ZÀ-Ỹ][a-zà-ỹ]+)*), (?:mẹ là) ([A-ZÀ-Ỹ][a-zà-ỹ]+(?: [A-ZÀ-Ỹ][a-zà-ỹ]+)*)";
	    regex = Pattern.compile(pattern);
	    matcher = regex.matcher(input);

	    if (matcher.find()) {
	        String name1 = matcher.group(1).trim();
	        names.add(name1);
	        String name2 = matcher.group(2).trim();
	        names.add(name2);
	        return names;
	    }
	    

	    pattern = "(?:là con trai của|là con gái của|con của|con thứ \\d+ của|cha là|mẹ là) ([A-ZÀ-Ỹ][a-zà-ỹ]+(?: [A-ZÀ-Ỹ][a-zà-ỹ]+)*)";
	    regex = Pattern.compile(pattern);
	    matcher = regex.matcher(input);

	    if (matcher.find()) {
	        String name = matcher.group(1).trim();
	        names.add(name);
	    }

	    return names;
	}
}
