package main_package;

import java.util.ArrayList;
import java.util.List;

public class Dynasty {
	String name;
	int startYear;
	int endYear;
	String desc;
	List<King> kings = new ArrayList<King>();
	
	public Dynasty(String name, int startYear, int endYear, String desc) {
		this.name = name;
		this.startYear = startYear;
		this.endYear = endYear;
		this.desc = desc;
	}
}