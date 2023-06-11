package helper_package;
import java.util.List;

import objects.Dynasty;

public class RemoveDuplicate {
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
		if (parseYear(resList.get(0).getStartYear()) > parseYear(dynasty.getStartYear())) {
			resList.add(0, dynasty);
			return;
		}

		for (int i = 0; i < resList.size(); i++) {
			
			//if biggest
			if (i == resList.size() - 1) {
				if(parseYear(resList.get(i).getStartYear()) < parseYear(dynasty.getStartYear())) {					
					resList.add(dynasty);
					return;
				}
			}
			
			//else, find correct position
			if (parseYear(resList.get(i).getStartYear()) < parseYear(dynasty.getStartYear())
					&& parseYear(dynasty.getStartYear()) < parseYear(resList.get(i + 1).getStartYear())) {
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

		for (Dynasty dynasty : newList) {
			if (dynasty.getStartYear() != null)
				System.out.println(NormalizeString.normalizeString(dynasty.getName()));
		}
	}
}
