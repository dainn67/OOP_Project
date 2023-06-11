package objects;

import java.util.ArrayList;
import java.util.List;

public class Dynasty {
	String name;
	String startYear;
	String endYear;
	String desc;
	List<King> kings = new ArrayList<King>();
	
	public Dynasty() {}
	
	public Dynasty(String name, String startYear, String endYear, String desc) {
		this.name = name;
		this.startYear = startYear;
		this.endYear = endYear;
		this.desc = desc;
	}
	
	public String getName() {
		return name;
	}
	
	public String getStartYear() {
		return startYear;
	}
	
	public String getEndYear() {
		return endYear;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}