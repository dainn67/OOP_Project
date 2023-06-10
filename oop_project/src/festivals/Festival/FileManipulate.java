package Festival;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;

public class FileManipulate {
	public static boolean SaveFile(Collection<String> c,String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bf = new BufferedWriter(osw);
			for(String s : c) {
				bf.write(s);
				bf.newLine();
			}
			bf.close();
			osw.close();
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean SaveFileFestival(Collection<Festival> festivals,String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bf = new BufferedWriter(osw);
			for(Festival s : festivals) {
				bf.write(s.toString());
				bf.newLine();
			}
			bf.close();
			osw.close();
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<String> ReadFile(String path){
		ArrayList<String> c = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis ,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = br.readLine();
			while(line != null) {
				if(line.length() > 0)
					c.add(line);
				line = br.readLine();
			}	
			br.close();
			isr.close();
			fis.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
}
