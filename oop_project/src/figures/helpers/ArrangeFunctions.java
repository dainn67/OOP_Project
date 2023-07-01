package figures.helpers;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.io.BufferedReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import figures.objects.*;
import dynasties.objects.Dynasty;

public class ArrangeFunctions {
	static String filePath1 = "final_dynasties.json";
	static String filePath2 = "appended_figures.json";
	static ArrayList<Dynasty> dynasties = decodeJsonFileToList(filePath1, Dynasty.class);
	static ArrayList<Figure> figures = HelperFunctions.decodeFromJson(filePath2);
	
	public static void arrangeFiguresToDynasties() {
		int[] counter = new int[40];
		for (int i = 0; i < figures.size(); i++) {
			Figure fig = figures.get(i);
			
			System.out.println();
			
			// if figure has dynasty
			if (!fig.getDynasties().isEmpty()) {
				// loop through each dynasty figure has
				for (String dynastyName : fig.getDynasties())
					// looop through list of refDynasties
					for (int j = dynasties.size() - 1; j >= 0; j--)
						// if equal and count < 20
						if (dynastyName.toLowerCase().equals(dynasties.get(j).getName().toLowerCase())
								&& counter[j] < 20) {
							dynasties.get(j).addFigure(fig);
							counter[j]++;
							break;
						}
			} else {
				// check if found birth, death year
				if (fig.getBornYear() != 0 && fig.getDeathYear() != 0) {
//					System.out.println(HelperFunctions
//							.normalizeString(fig.getName() + ": " + fig.getBornYear() + " -> " + fig.getDeathYear()));

					// avg yesr
					int targetYear = (fig.getBornYear() + fig.getDeathYear()) / 2;

					// loop from end to start and check if counter < 20
					for (int j = dynasties.size() - 1; j >= 0; j--)
						if (targetYear > dynasties.get(j).getStartYear() && counter[j] < 20) {
							dynasties.get(j).addFigure(fig);
							break;
						}
				}
			}
		}
		
		HelperFunctions.encodeListToJson(dynasties, "arranged_figures_dynasties.json");
	}

	public static <T> ArrayList<T> decodeJsonFileToList(String filePath, Class<T> elementType) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
//                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Type listType = TypeToken.getParameterized(ArrayList.class, elementType).getType();
        return gson.fromJson(content.toString(), listType);
    }
}


    

