import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;


public class DVDInventory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	static SortedList inventory = new SortedList();
	
	public static void main(String [] arg){
		
		String userinput= "";
		
		System.out.println("Welcome to DVD Inventory Management System!");
		System.out.println("Enter H for Available Commands, Q for Quit.");
		nextCommandLine();
		
		do {
			Scanner sc = new Scanner(System.in);
			userinput = sc.nextLine();
			doTask(userinput);
			
		} while (!userinput.equalsIgnoreCase("q"));
		
		
//		ListReferenceBased waitingList = new ListReferenceBased();
//		Customer c = new Customer ("Jianmin", "Yu", "84080013");
//		StockItem s = new StockItem("Dragon Me", 1,2);
//		s.setWaitingList(waitingList);
//		
//		
//		ListReferenceBased waitingList2 = new ListReferenceBased();
//		Customer c2 = new Customer ("Liting", "Liang", "90929112");
//		StockItem s2 = new StockItem("G Man 2", 3,4);
//		s2.setWaitingList(waitingList2);
//		
//		StockItem s3 = new StockItem("A City", 2,3);
//		s3.setWaitingList(waitingList2);
//		
//		StockItem s4 = new StockItem("C City", 2,3);
//		s4.setWaitingList(waitingList2);
//		
		
	//	try {
//			s.addToWaitingList(c);
//			s2.addToWaitingList(c);
//			s2.addToWaitingList(c2);
//			
//			inventory.add(s);
//			inventory.add(s2);
//			inventory.add(s3);
//			inventory.add(s4);
//		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
	//	}
		
//		try {
//			int size = inventory.size();
//			for (int i=1;i<=size;i++){
//				System.out.println( i+":\n" + inventory.get(i));
//			}
//				
//		} catch (ListIndexOutOfBoundsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	}
	private static void doTask(String... input){
		String choice = "";
		if (input.length >= 1){
			choice = input[0];
		}
		switch(choice.toLowerCase()){
		case "h":
			 printHelpMenu();
			break;
		case "l":
			 displayAll();
			break;
		case "i":
			System.out.println("Please enter DVD title: ");
			Scanner sc0 = new Scanner(System.in);
			String title = sc0.nextLine();
			 displayByTitle(title);
			break;
		case "a":
			System.out.println("Please enter DVD title: ");
			Scanner sc1 = new Scanner(System.in);
			String titleAdd = sc1.nextLine();
			addByTitle(titleAdd);	
			break;
		case "m":
			System.out.println("Please enter DVD title: ");
			Scanner sc2 = new Scanner(System.in);
			String titleM = sc2.nextLine();
			modifyByTitle(titleM);	
			break;
		case "d":
			
			break;
		case "o":
			writeDVDOrder();
			break;
		case "r":
			writeDVDReturn();
			break;
		case "s":
			sellByTitle();
			break;
		case "q":
			    //save data
				saveInventory();
				System.out.println("Bye, system is shutting down.");
			break;
		default:
			System.out.println("Please enter the correct command.");
			nextCommandLine();
		}
		//System.out.println(input);
	}
	public static void printHelpMenu(){
		System.out.println("===============Help Menu==============");
		System.out.println("L => Display All DVD Titles");
		System.out.println("I Title => Display the Information for a specific title");
		System.out.println("A Title => Add a new title to the inventory");

	}	
	
	public static void displayAll(){
		System.out.println("Display All DVD Information");
		try {
			int size = inventory.size();
			if (size == 0){
				System.out.println( "DVD Inventory is empty...\n");
				return;
			}
			for (int i=1;i<=size;i++){
				StockItem s = (StockItem)inventory.get(i);
				System.out.println( i+": " +s.getTitle() + "\n");
			}
				
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Display All Inventory Information Error.");
		} finally {
			nextCommandLine();
		}
		
		
	}
	
	public static void displayByTitle(String title){
		
		StockItem s = null;
		try {
			s = findStockItemByTitle(title);
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (s == null){
			System.out.println("No result found. Inventory is empty.");
		} else {
			System.out.println(s);
		}
		nextCommandLine();
	}
	
	
	private static StockItem findStockItemByTitle(String title) throws ListIndexOutOfBoundsException{
		int size = inventory.size();
		if (size ==0){
			return null;
		}
		for (int skip = 1; skip <= size; skip++){
			if (((StockItem) inventory.get(skip)).getTitle().equalsIgnoreCase(title)){
				return (StockItem) inventory.get(skip);
			} else {
				}
			}
		return null;
	}
	
	public static void addByTitle(String... t){
		String title = "";
		if (t.length >= 1){
			title = t[0];
		}
		
		StockItem s = new StockItem(title);
		
		try {
			inventory.add(s);
			s.setHave(s.getHave()+1);
			System.out.println(title + " is successfully added to inventory.");
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Adding " + title + " to inventory has error.");
		}
		finally {
			nextCommandLine();

		}
		
	}
	
	public static void modifyByTitle(String... t){
		String title = "";
		if (t.length >=1){
			title = t[0];
		}
		System.out.println("Want number for "+ title +": ");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		StockItem s = null;
		try {
			s = findStockItemByTitle(title);
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayError("Modification error.");
		}
		if (s == null){
			System.out.println("No result found.");
		} else {
			s.setWant(num);
			System.out.println("Modification successfully done.");
		}
		nextCommandLine();
		
	}
	
	//compare want and have values to create a list of DVDs need to order
	public static void writeDVDOrder ()  {
		int size = inventory.size();
		boolean noOrder = true;
		if (size ==0){
			System.out.println("Inventory is empty.");
			nextCommandLine();
			return;
		}
		for (int skip = 1; skip <= size; skip++){
			StockItem s = null;
			try {
				s = ((StockItem)inventory.get(skip));
				if (s == null ){
					displayError("Inventory write order error.");
					return;
				}
				int order = s.getOrderNumber();
				if (order > 0){
					System.out.println("Order for " + s.getTitle() + " is: "+ order + "\n");
					s.setHave(s.getWant());
					noOrder = false;
				} else {
					
				}
				
			} catch (ListIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				displayError("Inventory write order error.");
			}
		}
		if (noOrder){
			displayError("No order needed.");
		}
	}
	
	public static void sellByTitle(){
		System.out.println("Sell for :");
		String title = "";
		Scanner sc = new Scanner(System.in);
		title  = sc.nextLine();
		StockItem s = null;
		try {
			s = findStockItemByTitle(title);
		} catch (ListIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			displayError("Sell DVD error.");
		}
		if (s == null){
			System.out.println("No result found.");
		} else {
			int have = s.getHave();
			//put on waiting list
			if (have == 0 ){
				System.out.println("DVD is out of stock.");
				System.out.println("Setting up waiting list for " + title);
				
				System.out.println("Enter customer last name:");
				String last = sc.nextLine();
				System.out.println("Enter customer first name: ");
				String first = sc.nextLine();
				
				System.out.println("Enter customer phone number:");
				String phone = sc.nextLine();
				System.out.println("Enter customer address: ");
				String address = sc.nextLine();
				
				Customer c = new Customer(first, last, phone, address);
				
				try {
					s.addToWaitingList(c);
				} catch (ListIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					displayError("Successfully updated waiting list for " + title);
				}
			} else {
				s.setHave(have -1 );
				displayError("Sold successfully.");
			}
		 
		}
		nextCommandLine();
	}
	
	public static void deliveryDVD(){
		
	}
	//return order to reduce the have value to a want value
	//if have - want >= 0 then have = have -want, want = 0;
	//if have - want < 0; then have = 0; want = want - have
	public static void writeDVDReturn(){
		
		int size = inventory.size();
		if (size ==0){
			System.out.println("Inventory is empty.");
			nextCommandLine();
			return;
		}
		for (int skip = 1; skip <= size; skip++){
			StockItem s = null;
			try {
				s = ((StockItem)inventory.get(skip));
				int order = s.getOrderNumber();
				if (order >= 0){// have = 0; want = want -have
					s.setWant(s.getWant() - s.getHave());
					s.setHave(0);
				} else {
					s.setHave(s.getHave() - s.getWant());
					s.setWant(0);
				}
				
			} catch (ListIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				displayError("Return error.");
			}
		}
		System.out.println("Return is done.");
	}
	public static void saveInventory(){
		try{
			FileOutputStream fos = new FileOutputStream ("inventory.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(inventory);
			fos.close();
		} 
		catch (Exception e) {
			System.out.println("Store data error.\n"+e.getMessage());
		}
	}
	public static void nextCommandLine(){
		System.out.println("==>");

	}
		
	public static void displayError(String msg){
		System.out.println(msg);
	}
}
