import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import au.com.bytecode.opencsv.CSVWriter;


public class IMDBHarvesting {
	private String url;
	private String output;
	private static final String userAgent = "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6";
	private static final String referrer = "http://www.google.com";
	public IMDBHarvesting(String url, String output){
		this.url = url;
		this.output = output;	
	}
	
	public void startScrapping(){
		this.loadIMDBDescription();
	}
	
	private void loadIMDBDescription(){
			Document doc = null;
			try {
				//amazing referrer to clear 403 error
				doc = Jsoup.connect(url).userAgent(userAgent).referrer(referrer).get();
				//get class name item_description from 1 to last
				Elements eles = doc.getElementsByClass("item_description");
				//write into CSV file 
				 Iterator<Element> iter = eles.iterator();
				 CSVWriter writer = new CSVWriter(new FileWriter(output));
				 while (iter.hasNext()){
					 String[] des = {this.removeDescriptionEndings(iter.next().text().toString().trim())};
					 writer.writeNext(des);
				 }
				 writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Load HTML document error.");
			}
			
		}
		//Removing ( 1/22/134 mins.) from description ending
		private String removeDescriptionEndings(String input){
			int len = 0,
				end = 0;
			String result = "";
			if (input != null &&  input.trim().length() > 12){
				len = input.length();
				end = len - 12; //only for 3 digits
				result = input.substring(0,end);
				if (result.endsWith(".")){
					//OK
				}else {
					end = len - 11;	//for 2 digits	
					result = input.substring(0,end);
					if (result.endsWith(".")){
						//OK
					}else {
						end = len - 10; //for 1 digits
						result = input.substring(0,end);
						if (result.endsWith(".")){
							//OK
						}else { // no ends with mins etc
							result = input;
						}
					}
				}
			}
			
			return result;
		}
		

}
