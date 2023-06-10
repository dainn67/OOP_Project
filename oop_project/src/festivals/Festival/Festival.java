package Festival;

import java.util.ArrayList;

public class Festival {
	private String name;
	private String place;
	private String startTime;
	private String firstTimes;
	private String relatedCharacter;
	private ArrayList<String> decription = new ArrayList<String>();

	public Festival() {
		super();
	}

	public Festival(String name, String place) {
		super();
		this.name = name;
		this.place = place;
	}

	public Festival(String name, String place, String startTime) {
		super();
		this.name = name;
		this.place = place;
		this.startTime = startTime;
	}
	public Festival(String name, String place, String startTime, String firstTimes, String relatedCharacter,
			ArrayList<String> decription) {
		super();
		this.name = name;
		this.place = place;
		this.startTime = startTime;
		this.firstTimes = firstTimes;
		this.relatedCharacter = relatedCharacter;
		this.decription = decription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFirstTimes() {
		return firstTimes;
	}

	public void setFirstTimes(String firstTimes) {
		this.firstTimes = firstTimes;
	}

	public String getRelatedCharacter() {
		return relatedCharacter;
	}

	public void setRelatedCharacter(String relatedCharacter) {
		this.relatedCharacter = relatedCharacter;
	}

	public ArrayList<String> getDecription() {
		return decription;
	}
	
	public void addDescription(String a) {
		decription.add(a);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Tên : " + name + "\nNgày bắt đầu : " + startTime + "\nĐịa điểm : " + place + "\nLần đầu tổ chức năm : "
				+ firstTimes +"\nNhân vật liên quan : "+relatedCharacter+"\nChi tiết : "+decription +"\n\n";
	}

}
