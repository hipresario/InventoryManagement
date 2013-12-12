import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;


public class DVDInventory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	static SortedStockList inventory = new SortedStockList();
	
	public static void main(String [] arg){
		
		String userinput= "";
		
		displayInformation("Welcome to DVD Inventory Management System!");
		displayInformation("Enter H for Available Commands, Q to Quit...");
		displayInformation("==>");		
		do {
			Scanner sc = new Scanner(System.in);
			userinput = sc.nextLine();
			doTask(userinput);
			
		} while (!userinput.equalsIgnoreCase("q"));
		
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
			 displayByTitle();
			break;
		case "a":
			addByTitle();	
			break;
		case "m":
			modifyByTitle();	
			break;
		case "d":
			deliveryDVD();
			break;
		case "o":
			writePurchaseOrder();
			break;
		//case "r":
			//writeDVDReturn();
			//break;
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
		displayInformation("L => Display all DVDs by title");
		displayInformation("I => Display information for a specific title");
		displayInformation("A => Add a new DVD title to the inventory");
		displayInformation("D => Deliver DVD to people in waiting list");
		displayInformation("M => Modify want value for a specific title");
		displayInformation("O => Print out purchase order");
		//displayInformation("R => Make a return order after purchasing done");
		displayInformation("S => Sell a DVD");
		displayInformation("Q => Save and exit the system");
		
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
				displayInformation( i+": " +s.getTitle() + "\n");
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
			if (((StockItem) inventory.getStock(skip)).getTitle().equalsIgnoreCase(title)){
				return (StockItem) inventory.getStock(skip);
			} else {
				}
			}
		return null;
	}
	//add by title
	public static void addByTitle(){
		displayInformation("Please enter DVD title: ");
		Scanner sc = new Scanner(System.in);
		String title = sc.nextLine();
		StockItem s = new StockItem(title);
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
	//modify want number for DVD
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
					displayInformation("Inventory purchase order error.");
					return;
				}
				int order = s.getOrderNumber();
				if (order > 0){
					displayInformation("Order number for " + s.getTitle() + " : "+ order);
					s.setHave(s.getWant());
					noOrder = false;
				} else {
					
				}
				
			} catch (ListIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				displayInformation("Inventory write order error.");
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
									displayInformation("Delivery "+s.getTitle()+" to: "+s.getWaitingList().get(1).toString());
									s.removeFromWaitingList();
									
								}
								want = want - waitingSize;
								s.setWant(want);
								s.setHave(have-waitingSize);	
							} else {
								//waiting is more than want
								for (int i=1;i<=want;i++){
									displayInformation("Delivery "+s.getTitle()+" to: "+s.getWaitingList().get(1).toString());
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
									displayInformation("Delivery "+s.getTitle()+" to: "+s.getWaitingList().get(1).toString());
									s.removeFromWaitingList();
								}
								want = want - waitingSize;
								s.setWant(want);
								s.setHave(have-waitingSize);	
							} else {
								//waiting is more than have
								for (int i=1;i<=have;i++){
									displayInformation("Delivery "+s.getTitle()+" to: "+s.getWaitingList().get(1).toString());
									s.removeFromWaitingList();

								}
								have = 0;
								s.setHave(0);
								s.setWant(want-waitingSize);
							}
						}
					}
				}
			} catch (ListIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				displayInformation("Delivery error.");
			}finally{
				displayInformation("Delivery is done.");		
			}
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
