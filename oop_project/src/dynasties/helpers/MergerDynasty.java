package dynasties.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import helper_package.HelperFunctions;
import objects.Dynasty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MergerDynasty {
	static ArrayList<Dynasty> finalDynasties = new ArrayList<>();

	public static void main(String[] args) {
		ArrayList<Dynasty> dynasties1 = new ArrayList<>();
		ArrayList<Dynasty> dynasties2 = new ArrayList<>();
		ArrayList<Dynasty> dynasties3 = new ArrayList<>();

		String[] fileNames = { "after_nks_dynasties.json", "after_nks_dynasties_extra.json", "after_vs_dynasies.json" };

		// Create Gson object
		Gson gson = new GsonBuilder().create();

		// Define the type of the ArrayList<Dynasty>
		Type type = new TypeToken<ArrayList<Dynasty>>() {
		}.getType();

		for (String fileName : fileNames) {
			try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
				StringBuilder json = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					json.append(line);
				}

				// Deserialize the JSON string into ArrayList<Dynasty>
				if (dynasties1.isEmpty())
					dynasties1 = gson.fromJson(json.toString(), type);
				else if (dynasties2.isEmpty())
					dynasties2 = gson.fromJson(json.toString(), type);
				else
					dynasties3 = gson.fromJson(json.toString(), type);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// after deserialization, merge
		mergeDynasty(dynasties1, dynasties2, dynasties3);
		HelperFunctions.encodeListToJson(finalDynasties, "after_dynasties.json");
	}

	static public void mergeDynasty(ArrayList<Dynasty> list1, ArrayList<Dynasty> list2, ArrayList<Dynasty> list3) {

		// get dynasties from 1st list
		finalDynasties = new ArrayList<>(list1);

		for (int i = 0; i < list2.size(); i++)
			addToList(list2.get(i));
		for (int i = 0; i < list3.size(); i++)
			addToList(list3.get(i));
	}

	static public void addToList(Dynasty targetDynasty) {

		// check if exist or not
		for (Dynasty dynasty : finalDynasties) {
			String targetName = targetDynasty.getName().toLowerCase().replaceAll("nhà", "").replaceAll("Nhà", "").trim();
			String currentName = dynasty.getName().toLowerCase().replaceAll("nhà", "").replaceAll("Nhà", "").trim();
			if (targetName.contains(currentName) || currentName.contains(targetName)
					|| (targetName.contains("phân tranh") && currentName.contains("phân tranh")))
				return;
		}

		// if smallest
		int currentDynastyYear = finalDynasties.get(0).getStartYear();
		int targetDynastyYear = targetDynasty.getStartYear();

		if (targetDynastyYear < currentDynastyYear) {
			finalDynasties.add(0, targetDynasty);
			return;
		}

		// if biggest
		currentDynastyYear = finalDynasties.get(finalDynasties.size() - 1).getStartYear();
		if (currentDynastyYear < targetDynastyYear) {
			finalDynasties.add(targetDynasty);
			return;
		}

		// loop from end to top till find the correct position
		for (int i = finalDynasties.size() - 2; i >= 0; i--) {
			currentDynastyYear = finalDynasties.get(i).getStartYear();
//			nextDynastyYear = parseYear(finalDynasties.get(i + 1).getStartYear());
			if (currentDynastyYear < targetDynastyYear
//					&& targetDynastyYear < nextDynastyYear
			) {
				finalDynasties.add(i + 1, targetDynasty);
				return;
			}
		}
	}
}