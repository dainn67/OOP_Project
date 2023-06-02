package Wiki;

import com.google.gson.Gson;

public class DemoGson {
	public static void main(String[] args) {
		Gson gson = new Gson();
		MobilePhone mb = new MobilePhone("Samsung","Samsung Galaxy Node 20",12,256);
		String json = gson.toJson(mb);
		System.out.println(json);
	}
}
