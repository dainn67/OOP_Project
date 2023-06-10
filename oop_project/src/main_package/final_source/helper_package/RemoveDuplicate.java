package helper_package;

import java.util.ArrayList;
import java.util.List;

import objects.Figure;

public class RemoveDuplicate {
	public List<Figure> removeDuplicate(List<Figure> l1, List<Figure> l2) {
		List<Figure> resList = new ArrayList<Figure>();

		for (Figure person1 : l1) {
			Boolean isDuplicated = false;
			for (Figure person2 : l2) {
				if (person1.getName().equals(person2.getName())) {
					isDuplicated = true;
					break;
				}
			}
			if (!isDuplicated)
				resList.add(person1);
		}

		for (Figure person : l2) {
			resList.add(person);
		}
		
		return resList;
	}
}
