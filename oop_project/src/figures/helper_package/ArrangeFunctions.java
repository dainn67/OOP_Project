package helper_package;

import java.util.ArrayList;

import objects.Dynasty;
import objects.Figure;

public class ArrangeFunctions {
	static ArrayList<Dynasty> dynasties = EncodeDecode.decodedDynastyList(3);
	static ArrayList<Figure> figures = EncodeDecode.decodedFigureList(1);
	static ArrayList<Figure> vansuFigures = EncodeDecode.decodedFigureList(2);

	public static void arrangeFigures() {

		for (int i = 0; i < figures.size(); i++) {
			Figure fig = figures.get(i);
			
			//if figure has dynasty found
			if (!fig.getDynasties().isEmpty()) {
				for (String dynastyName : fig.getDynasties())
					for (int j = dynasties.size() - 1; j >= 0; j--)
						if (dynastyName.toLowerCase().equals(dynasties.get(j).getName().toLowerCase())) {
							dynasties.get(j).addFigure(fig.getId());
							break;
						}
			}else {
				//check if found birth, death year
				if(fig.getBornYear() != 0 && fig.getDeathYear() != 0) {
					System.out.println(HelperFunctions.normalizeString(fig.getName() + ": " + fig.getBornYear() + " -> " + fig.getDeathYear()));
					int targetYear = (fig.getBornYear() + fig.getDeathYear()) / 2;
					for (int j = dynasties.size() - 1; j >= 0; j--)
						if(targetYear > dynasties.get(j).getStartYear()) {
							dynasties.get(j).addFigure(fig.getId());
							break;
						}
				}
			}
		}
		
		//TODO: remove after merge
		for (int i = 0; i < vansuFigures.size(); i++) {
			Figure fig = vansuFigures.get(i);
			
			//if figure has dynasty found
			if (!fig.getDynasties().isEmpty()) {
				for (String dynastyName : fig.getDynasties())
					for (int j = dynasties.size() - 1; j >= 0; j--)
						if (dynastyName.toLowerCase().equals(dynasties.get(j).getName().toLowerCase())) {
							dynasties.get(j).addFigure(fig.getId());
							break;
						}
			}else {
				//check if found birth, death year
				if(fig.getBornYear() != 0 && fig.getDeathYear() != 0) {
					System.out.println(HelperFunctions.normalizeString(fig.getName() + ": " + fig.getBornYear() + " -> " + fig.getDeathYear()));
					int targetYear = (fig.getBornYear() + fig.getDeathYear()) / 2;
					for (int j = dynasties.size() - 1; j >= 0; j--)
						if(targetYear > dynasties.get(j).getStartYear()) {
							dynasties.get(j).addFigure(fig.getId());
							break;
						}
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(figures.size());
		arrangeFigures();

		// check print
		int count = 0;

		for (Dynasty dynasty : dynasties) {
			System.out.println(HelperFunctions.normalizeString(dynasty.getName()));
			if (!dynasty.getListFigures().isEmpty()) {
				count += dynasty.getListFigures().size();
//				for (String fig : dynasty.getListFigures())
//					System.out.println("	" + HelperFunctions.normalizeString(fig));
			} else
				System.out.println("	Empty");
		}
		System.out.println(count);
	}
}