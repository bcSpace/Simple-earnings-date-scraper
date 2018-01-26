package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LoadDate {
	
	String database = "C:\\EarningsDates\\"; //local database 
	
	String webpage = "https://www.zacks.com/stock/research/";
	Document doc; 
	
	Data[] data = new Data[500];
	int dataCounter = 0;
	
	int totalResults = 0; 
	int pages = 0;
	
	public boolean failed = false;
	
	
	LoadDate() {
		reset();
	}
	
	void reset() {
		for(int i = 0; i < 500; i++) data[i] = new Data();
		dataCounter = 0;
	}
	
	
	void loadDoc(String underlying) {
		reset();
		String loader = webpage+underlying+"/earnings-announcements"; 
		
		boolean trying  = true; 
		while(trying) {
			try {
				doc = Jsoup.connect(loader).userAgent("Chrome").get();
				//System.out.println(doc);
				trying = false;
			} catch (IOException e) {
				System.out.println("Trying to load again: " + loader);
			}
		}
		
		getEarnings();
		if(failed == false) writeData(underlying);
		
	}
	
	String datas[];
	

	void getEarnings() {
		
		String s = doc.toString();
		
		//checking to see if there was an error in loading the page
		if(s.contains("404 Page Not Found") || s.contains("The page you are looking for does not exist") || s.contains("ETF Quote Details") || s.contains("www.zacks.com/search.php?q=") || s.contains("www.zacks.com/funds/etf/")) failed = true;
		else failed = false;
		
		//System.out.println(s);
		
		if(failed == false) {
			try {
				s = s.split("document.obj_data")[1];
				s = s.split("document.tab")[0];
				s = s.split("earnings_announcements_webcasts_table")[0];
				
				int ayy = s.split("]").length;
				
				datas = new String[ayy];
				
				
				for(int i = 0; i < ayy; i++) {
					datas[i] = s.split("]")[i].replaceAll(" ", "");
					datas[i] = datas[i].split("\\[")[datas[i].split("\\[").length-1];
					//datas[i] = datas[i].split("[")[0];
				}
				for(int i = 0; i < ayy; i++) {
					if(datas[i].contains("divclass") || datas[i].contains(".")){
					data[dataCounter].time = datas[i].split(",")[6];
					data[dataCounter].date = datas[i].split(",")[0];
					System.out.println(data[dataCounter].date+", "+data[dataCounter].time);
					dataCounter++;
					}
				}
				
			} catch (Exception e) {failed = true;}
		}
	}
	
	
	
	PrintWriter pw;
	
	void writeData(String underlying) {
		
		String output = "";
		
		for(int i = 0; i < dataCounter; i++) {
			output += data[i].date + "," + data[i].time;  
			output+=System.lineSeparator();
		}
		
		try {
			File file = new File(database + underlying+ "\\earnings.csv");
			file.getParentFile().mkdirs();
			pw = new PrintWriter(file);
			pw.write(output);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
