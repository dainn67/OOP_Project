package Festival;

import java.util.HashMap;

public class FestivalDescription {
	private String header;
	private HashMap<String, String> detail = new HashMap<String, String>() ;
	public FestivalDescription() {
	}
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public HashMap<String, String> getDetail() {
		return detail;
	}
	public void setDetail(HashMap<String, String> detail) {
		this.detail = detail;
	}
	
	
	
}
