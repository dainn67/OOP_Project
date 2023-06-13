package helper_package;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import objects.Dynasty;
import objects.Figure;
import objects.Poinsettia;

public class HelperFunctions {

	public static String normalizeString(String s) {
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").replace("Đ", "D").replace("đ", "d")
				.replace(" – ", "-").replace("- ", "-").replace(" -", "-").replace("–", "-");
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

	public static void prtDynasty(Dynasty dynasty) {
		System.out.println(dynasty.getName());
		System.out.println("	" + dynasty.getStartYear());
		System.out.println("	" + dynasty.getEndYear());
		System.out.println("	" + dynasty.getDesc());
	}

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
					while (index >= 0 && normalizeString(nextFigure.getName()).compareTo(normalizeString(target)) == 0) {
						resSObjs.add(nextFigure);
						index--;
						nextFigure = (Figure) sortedList.get(index);
					}

					index = mid + 1;
					nextFigure = (Figure) sortedList.get(index);
					while (index < sortedList.size() && normalizeString(nextFigure.getName()).compareTo(normalizeString(target)) == 0) {
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
			//if list is long then add instance
		}

		// No match found
		return resSObjs;
	}
	public static List<Figure> searchFigureLinear(List<Figure> sortedList, String name) {
		List<Figure> resList = new ArrayList<>();
		List<Figure> resListNormalized = new ArrayList<>(); 
		for(Figure figure: sortedList)
			if(figure.getName().equals(name))
				resList.add(figure);
			else if(normalizeString(figure.getName()).contains(normalizeString(name)))
				resListNormalized.add(figure);
		if(!resList.isEmpty()) return resList;
		return resListNormalized;
	}
	
	public static List<Dynasty> searchDynastyLinear(List<Dynasty> sortedList, String name){
		List<Dynasty> resList = new ArrayList<>();
		for(Dynasty dynasty: sortedList) {
			String dName = normalizeString(dynasty.getName());
			if(dName.equals(normalizeString(name)) || dName.contains(normalizeString(name)))
				resList.add(dynasty);
		}
		return resList;
	}
	
	public static void main(String[] args) {
		List<Figure> figureList = EncodeDecode.decodedFigureList();
		List<Dynasty> dynastyList = EncodeDecode.decodedDynastyList(true);

		
		String searchName = "Nguyen Du";
		List<Figure> resFig = searchFigureLinear(figureList, searchName);
		if(resFig.isEmpty()) System.out.println("Figure not found");
		else {
			for(Figure figure: resFig) prtFigureAttributes(figure);
		}
	}
}
