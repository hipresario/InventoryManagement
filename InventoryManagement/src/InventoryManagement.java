import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
/**
 * LinkedLit based simple DVD Inventory Management System
 * Read and Store data into binary format file
 * @author user
 *
 */
public class InventoryManagement implements Serializable {
	private static final long serialVersionUID = 10L;
	
	private static final String fileName = "Movies.csv";
	private static final String binaryName = "inventory.dat";
	static SortedStockList inventory = new SortedStockList();
	
	public static void main(String [] arg){
		
		 
		loadIMDBUrl();
	
		displayInformation("Welcome to DVD Inventory Management System!");
		
		String userinput= "";
		displayInformation("Enter H for Available Commands, Q to Quit...");
		displayInformation("==>");		
		do {
			Scanner sc = new Scanner(System.in);
			userinput = sc.nextLine();
			doTask(userinput);
			
		} while (!userinput.equalsIgnoreCase("q"));
		
	}
	
	//TODO: to get item_description from IMDB website and write to CSV file
	
	private static void doTask(String... input){
		String choice = "";
		if (input.length >= 1){
			choice = input[0];
		}
		switch(choice.toLowerCase()){
		//help menu
		case "h":
			 printHelpMenu();
			break;
		case "b":
			loadBinaryData(binaryName);
			break;
		case "t":
			loadCSVData(fileName);
			break;
		//display all DVDs in alphabetic ascending order
		case "l":
			 displayAll();
			break;
		//promt to display specific DVD
		case "i":
			 displayByTitle();
			break;
		//add DVD record 
		case "a":
			addByTitle();	
			break;
		//modify DVD record
		case "m":
			modifyByTitle();	
			break;
		//deliver DVD to customer in waiting list
		case "d":
			deliveryDVD();
			break;
		//print out purchase order 
		case "o":
			writePurchaseOrder();
			break;
		//remove DVD record
		case "r":
			removeByTitle();
			break;
		case "s":
			sellByTitle();
			break;
		case "q":
			    //save data
				saveInventory();
				displayInformation("Bye, system is shutting down.");
			break;
		default:
			displayInformation("Please enter the correct command.");
			displayInformation("==>");
		}
	}
	//help menu
	public static void printHelpMenu(){
		displayInformation("===============Help Menu==============");
		displayInformation("B => Load from binary file");
		displayInformation("T => Load IMDB top 1000 movies");
		displayInformation("L => Display all DVD records");
		displayInformation("I => Display a DVD record");
		displayInformation("A => Add a new DVD record");
		displayInformation("S => Sell a DVD");
		displayInformation("R => Remove a DVD record");
		displayInformation("M => Modify DVD record");
		displayInformation("D => Print DVD deliver list");
		displayInformation("O => Print DVD purchase order");
		displayInformation("Q => Save to file and exit system");
		displayInformation("==>");
	}	
	//IMDB pages control
	private static void loadIMDBUrl(){
		int [] loop = {0,101,201,301,401,501,601,701,801,901};
		int len = loop.length;
		for (int l = 0; l<len; l++){
			loadIMDBDescription(l);
		}
	}
	//Load description from imdb html page
	private static void loadIMDBDescription(int page){
		String url = "http://www.imdb.com/list/tNwWwtkvwDQ/";
		String post = "&view=detail&sort=listorian:asc";
		
		if (page == 0 ){
		}else {
			url = url + "?start="+page+post;
		}
		
		Document doc;
		try {
			//amazing referrer to clear 403 error
			doc = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				    .referrer("http://www.google.com").get();
			
			//String title = doc.title();
			//get class name item_description from 1 to last
			Elements eles = doc.getElementsByClass("item_description");
			//write into CSV file 
			//TODO: find a good CSV parser
			//temp wite to another file and copy to Movies.csv
			 Iterator<Element> iter = eles.iterator();
			 String csv = "description-"+page+".csv";
			 CSVWriter writer = new CSVWriter(new FileWriter(csv));
			 while (iter.hasNext()){
				  String[] des = {iter.next().text().toString()};
				 writer.writeNext(des);
				// System.out.println(iter.next().text().toString().s);
			 }
			 writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayInformation("Load HTML document error.");
			
		}
		
		
	}
	
	//Simple load binary file 
	private static void loadBinaryData(String binaryName){
		//loading for inventory.dat file first
				try{
					FileInputStream fis = new FileInputStream(binaryName);
					ObjectInputStream ois = new ObjectInputStream(fis);
					Object o = ois.readObject();
					inventory = (SortedStockList)o;
					
				}catch (Exception e){
					displayInformation("Reading inventory file error.");
				}finally {
				}
				
	}
	//Simple load csv file
	private static void loadCSVData(String fileName){
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(fileName));
			 int i=0;
			 String [] nextLine;
			 while ( (nextLine = reader.readNext()) != null) {
			        // nextLine[] is an array of values from the line
			    	//skip first row
				 	if (i == 0 ){
				 	}else {
				 		//4:des 5:title 7:directors 9:rate 10:runtime 11: year 12: genre
				 		//System.out.println(nextLine[0] + nextLine[1] + "etc...");
				 		addIMDBRecords(nextLine[5], nextLine[11],nextLine[7], nextLine[12],nextLine[10],nextLine[9], nextLine[4]);
				 	}
				 	i++;
			    }
			    
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			displayInformation(fileName+ " not found.");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayInformation(fileName+ " loading I/O error.");
			
		}

		displayInformation("Done!");
	}
	
	//display all DVD in ascending order
	public static void displayAll(){
		try {
			int size = inventory.size();
			if (size == 0){
				displayInformation( "DVD Inventory is empty.");
				return;
			}
			for (int i=1;i<=size;i++){
				StockItem s = (StockItem)inventory.getStock(i);
				displayInformation( i+": " +s.getDVD().getTitle() + "\n");
			}
				
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayInformation("Display inventory information error.");
		} finally {
			displayInformation("==>");
		}
		
		
	}
	//display DVD information by title in details
	public static void displayByTitle(){
		
		System.out.println("Please enter DVD title: ");
		Scanner sc = new Scanner(System.in);
		String title = sc.nextLine();
		
		StockItem s = null;
		try {
			s = findStockItemByTitle(title);
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayInformation("Display "+ title+" error.");
		}
		if (s == null){
			displayInformation("No result found.");
		} else {
			displayInformation(s.toString());
		}
		displayInformation("==>");
	}
	//find stock item by title in list
	private static StockItem findStockItemByTitle(String title) throws ListIndexOutOfBoundsException{
		int size = inventory.size();
		if (size ==0){
			return null;
		}
		for (int skip = 1; skip <= size; skip++){
			if (((StockItem) inventory.getStock(skip)).getDVD().getTitle().equalsIgnoreCase(title)){
				return (StockItem) inventory.getStock(skip);
			} else {
				}
			}
		return null;
	}
	
	
	
	public static void addIMDBRecords(String title, String year, String directors, String genres, String runtime, String imdbRating, String description){
		
		DVD dvd = new DVD(title, year,directors,description, genres,imdbRating,runtime);
		
		StockItem s = new StockItem(dvd);
		
		try {
			inventory.addStockByRating(s);
			//update have value
			s.setHave(s.getHave()+1);
			//displayInformation(title + " is successfully added to inventory.");
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayInformation("Adding " + title + " to inventory has error.");
		}
		finally {
			//displayInformation("==>");
		}
	}
	
	
	//add by title
	//TODO:check already added title
	public static void addByTitle(){
		displayInformation("Please enter DVD title: ");
		Scanner sc = new Scanner(System.in);
		String title = sc.nextLine();
		displayInformation("Please enter DVD year: ");
		String year = sc.nextLine();
		
		displayInformation("Please enter DVD directors: ");
		String directors = sc.nextLine();
		
		displayInformation("Please enter DVD Genres: ");
		String genres = sc.nextLine();		
		
		displayInformation("Please enter DVD runtime: ");
		String runtime = sc.nextLine();
			
		displayInformation("Please enter DVD IMDB Rating: ");
		String imdbRating = sc.nextLine();
		
		displayInformation("Please enter DVD description: ");
		String description = sc.nextLine();
		
		 
		DVD dvd = new DVD(title, year,directors,description, genres,imdbRating,runtime);
		
		StockItem s = new StockItem(dvd);
		
		try {
			inventory.addStock(s);
			//update have value
			s.setHave(s.getHave()+1);
			displayInformation(title + " is successfully added to inventory.");
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayInformation("Adding " + title + " to inventory has error.");
		}
		finally {
			displayInformation("==>");
		}
	}
	//modify want/have number for DVD
	public static void modifyByTitle(){
		
		displayInformation("Please enter DVD title: ");
		Scanner sc = new Scanner(System.in);
		String title = sc.nextLine();
		displayInformation("Want number for "+ title +": ");
		Scanner sc2 = new Scanner(System.in);
		int num = sc2.nextInt();
		StockItem s = null;
		try {
			s = findStockItemByTitle(title);
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayInformation("Modification error.");
		}
		if (s == null){
			displayInformation("No result found.");
		} else {
			s.setWant(num);
			for (int a=1;a<=num;a++){
				displayInformation("Setting up waiting list for " + title);
				displayInformation("Enter customer last name:");
				String last = sc.nextLine();
				displayInformation("Enter customer first name: ");
				String first = sc.nextLine();
				displayInformation("Enter customer phone number:");
				String phone = sc.nextLine();
				displayInformation("Enter customer address: ");
				String address = sc.nextLine();
				Customer c = new Customer(first, last, phone, address);
				try {
					s.addToWaitingList(c);
				} catch (ListIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			displayInformation("Modification successfully done.");
		}
		displayInformation("==>");
		
	}
	
	//compare want and have values to create a list of DVDs need to order
	public static void writePurchaseOrder ()  {
		int size = inventory.size();
		boolean noOrder = true;
		if (size ==0){
			displayInformation("Inventory is empty.");
			displayInformation("==>");
			return;
		}
		for (int skip = 1; skip <= size; skip++){
			StockItem s = null;
			try {
				s = ((StockItem)inventory.getStock(skip));
				if (s == null ){
					displayInformation("Print inventory purchase order error.");
					return;
				}
				int order = s.getOrderNumber();
				if (order > 0){
					displayInformation("Order number for " + s.getDVD().getTitle() + " : "+ order);
					s.setHave(s.getWant());
					noOrder = false;
				} else {
				}
				
			} catch (ListIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				displayInformation("Print inventory purchase order error.");
			}
		}
		if (noOrder){
			displayInformation("No order needed.");
		}
		displayInformation("==>");
	}
	
	public static void sellByTitle(){
		displayInformation("Sell for DVD title:");
		String title = "";
		Scanner sc = new Scanner(System.in);
		title  = sc.nextLine();
		StockItem s = null;
		try {
			s = findStockItemByTitle(title);
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayInformation("Sell DVD error.");
		}
		if (s == null){
			displayInformation("No result found.");
		} else {
			int have = s.getHave();
			//put on waiting list
			if (have == 0 ){
				displayInformation("DVD is out of stock.");
				displayInformation("Setting up waiting list for " + title);
				displayInformation("Enter customer last name:");
				String last = sc.nextLine();
				displayInformation("Enter customer first name: ");
				String first = sc.nextLine();
				displayInformation("Enter customer phone number:");
				String phone = sc.nextLine();
				displayInformation("Enter customer address: ");
				String address = sc.nextLine();
				Customer c = new Customer(first, last, phone, address);
				try {
					s.addToWaitingList(c);
					//update want number
					s.setWant(s.getWant()+1);
				} catch (ListIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					displayInformation("Add to waiting list error.");
				}finally {
					displayInformation("Successfully updated waiting list for " + title);
				}
			} else {
				s.setHave(have -1 );
				displayInformation("Sold successfully.");
			}
		 
		}
		displayInformation("==>");
	}
	public static void writeDVDReturn(){
		
	}
	//deliver DVD to customer in the 1st of waiting list 1 by 1, only for those available ones
	//if have - want >= 0 then have = have - want, want = 0;
	//if have - want < 0; then have = 0; want = want - have
	public static void deliveryDVD(){
		int size = inventory.size();
		if (size ==0){
			displayInformation("Inventory is empty.");
			displayInformation("==>");
			return;
		}
		for (int skip = 1; skip <= size; skip++){
			StockItem s = null;
			try {
				s = ((StockItem)inventory.getStock(skip));
				int have = s.getHave();
				int want = s.getWant();
				int waitingSize =  s.getWaitingList().size();
				if (want == 0){
					// no delivery
				}else { 
					//want > 0
					if (waitingSize == 0 ){
						//no people in list recode
					}else { 
						//have >= want
						if (have >= want){
							if (want >= waitingSize){ //waiting is less than want
								for (int i=1;i<=waitingSize;i++){
									//display first
									displayInformation("Delivery "+s.getDVD().getTitle()+" to: "+s.getWaitingList().get(1).toString());
									s.removeFromWaitingList();
								}
								want = want - waitingSize;
								s.setWant(want);
								s.setHave(have-waitingSize);	
							} else {
								//waiting is more than want
								for (int i=1;i<=want;i++){
									displayInformation("Delivery "+s.getDVD().getTitle()+" to: "+s.getWaitingList().get(1).toString());
									s.removeFromWaitingList();
								}
								want = waitingSize - want;
								s.setWant(want);
								s.setHave(have-waitingSize);
							}
						}else {
							//have < want
							if (have >= waitingSize){ //waiting is less than have
								for (int i=1;i<=waitingSize;i++){
									displayInformation("Delivery "+s.getDVD().getTitle()+" to: "+s.getWaitingList().get(1).toString());
									s.removeFromWaitingList();
								}
								want = want - waitingSize;
								s.setWant(want);
								s.setHave(have-waitingSize);	
							} else {
								//waiting is more than have
								for (int i=1;i<=have;i++){
									displayInformation("Delivery "+s.getDVD().getTitle()+" to: "+s.getWaitingList().get(1).toString());
									s.removeFromWaitingList();

								}
								have = 0;
								s.setHave(0);
								s.setWant(want-have);
							}
						}
					}
				}
			} catch (ListIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				displayInformation("Delivery error.");
			}finally{
				//displayInformation("Delivery is done.");		
			}
		}
		displayInformation("Delivery is done.");
		displayInformation("==>");
	}
	//Remove DVD record
	//TODO: handle not delivered information
	public static void removeByTitle(){
		
		displayInformation("Please enter DVD title: ");
		Scanner sc = new Scanner(System.in);
		String title = sc.nextLine();
		
		StockItem s = null;
		try {
			s = findStockItemByTitle(title);
		} catch (ListIndexOutOfBoundsException e) {
			//e.printStackTrace();
			displayInformation("Remove DVD record error.");
		}
		if (s == null){
			displayInformation("No result found.");
		} else {
			try {
				inventory.removeStock(s);
			} catch (ListIndexOutOfBoundsException e) {
				//e.printStackTrace();
				displayInformation("Remove DVD record error.");
			}
			displayInformation("Remove successfully done.");
		}
		displayInformation("==>");
	}
	//Save inventory 
	public static void saveInventory(){
		try{
			FileOutputStream fos = new FileOutputStream ("inventory.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(inventory);
			fos.close();
		} 
		catch (Exception e) {
			displayInformation("Store data error.\n"+e.getMessage());
		}
	}
	//display information
	public static void displayInformation(String msg){
		System.out.println(msg);
	}
}
