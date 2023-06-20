package helper_package;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import objects.Dynasty;

public class MergeFunctions {
	static public List<Dynasty> removeDuplicateDynastyAndMerge(List<Dynasty> list1, List<Dynasty> list2) {
		List<Dynasty> resList = list1;

		for (Dynasty dynasty2 : list2) {
			if (dynasty2.getStartYear() != null) {
				addToList(resList, dynasty2);
			}
		}

		return resList;
	}

	static public void addToList(List<Dynasty> resList, Dynasty dynasty) {
		
		//check if exist or not
		for (Dynasty resDynasty : resList) {
			if (dynasty.getName().toLowerCase().equals(resDynasty.getName().toLowerCase()))
				return;
		}

		//if smallest
		int currentDynastyYear = parseYear(resList.get(0).getStartYear());
		int targetDynastyYear = parseYear(dynasty.getStartYear());
		int nextDynastyYear;
		
		if (currentDynastyYear > targetDynastyYear) {
			resList.add(0, dynasty);
			return;
		}
		
		//if biggest
		currentDynastyYear = parseYear(resList.get(resList.size()-1).getStartYear());
		targetDynastyYear = parseYear(dynasty.getStartYear());
		if (currentDynastyYear < targetDynastyYear) {
			resList.add(dynasty);
			return;
		}

		for (int i = 0; i < resList.size(); i++) {
			currentDynastyYear = parseYear(resList.get(i).getStartYear());
			targetDynastyYear = parseYear(dynasty.getStartYear());
			nextDynastyYear = parseYear(resList.get(i + 1).getStartYear());
			if (currentDynastyYear < targetDynastyYear && targetDynastyYear < nextDynastyYear) {
				resList.add(i + 1, dynasty);
				return;
			}
		}
	}
	
	public static int parseYear(String input) {
		input = input.trim();
		boolean isNegative = input.endsWith("TCN");
		if (isNegative) {
			input = input.substring(0, input.length() - 3).trim();
		}
		int year = Integer.parseInt(input);
		return isNegative ? -year : year;
	}

	//test main function
	public static void main(String[] args) {
		List<Dynasty> list = EncodeDecode.decodedDynastyList(false);
		List<Dynasty> list2 = EncodeDecode.decodedDynastyList(true);

		List<Dynasty> newList = removeDuplicateDynastyAndMerge(list, list2);
		List<Object> newList2 = new ArrayList<Object>();
		for(Dynasty dynasty: newList) {
			newList2.add(dynasty);
		}

		for (Dynasty dynasty : newList) {
				System.out.println(HelperFunctions.normalizeString(dynasty.getName()));
		}
		
		EncodeDecode.encodeToFile(newList2, "final_dynasties");
	}
}
