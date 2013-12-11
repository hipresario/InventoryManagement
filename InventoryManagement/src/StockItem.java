import java.lang.Comparable;
import java.io.Serializable;
/**
 * @author user
 *
 */
public class StockItem implements Comparable<Object>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private int have, want;
	private ListReferenceBased waitingList;
	
	public StockItem(String title){
		this.title = title;
		this.have = 0;
		this.want = 0;
		this.waitingList = new ListReferenceBased();
	}
	
	public StockItem(String title, int have, int want){
		this.title = title;
		this.have = have;
		this.want = want;
		this.waitingList = new ListReferenceBased();
	}
	
	public StockItem(String title, int have, int want, ListReferenceBased waitingList){
		this.title = title;
		this.have = have;
		this.want = want;
		this.waitingList = waitingList;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHave() {
		return have;
	}

	public void setHave(int have) {
		this.have = have;
	}

	public int getWant() {
		return want;
	}

	public void setWant(int want) {
		this.want = want;
	}

	public ListReferenceBased getWaitingList() {
		return this.waitingList;
	}

	public void setWaitingList(ListReferenceBased waitingList) {
		this.waitingList = waitingList;
	}

	public void addToWaitingList (Customer c) throws ListIndexOutOfBoundsException{
		int size = this.waitingList.size();
		//add to the last of waiting list
		this.waitingList.add(size+1, c );
	}
	
	public void removeFromWaitingList () throws ListIndexOutOfBoundsException{
		//remove the first customer on waiting list
		this.waitingList.remove(1);
	}
	//purchase order
	public int getOrderNumber (){
		int order = (this.want - this.have);
		return (order > 0) ? order : 0;
	}
	public String toString() {
		int size = this.waitingList.size();
		
		String wl = "Title : " + this.title + "\n";
			   wl += "Want: " + this.want + "\n";
			   wl += "Have: " + this.have + "\n";
			   wl += "Waiting List: \n";
		if (size == 0){
			wl += "0";
		}
		for (int i= 1 ;i<= size;i++){
			try {
				wl += ((Customer)this.waitingList.get(i)).toString();
				wl += "\n";
			} catch (ListIndexOutOfBoundsException e) {
				 e.printStackTrace();
			}
		}
		
		return wl;
		
	}
	
	public int compareTo(Object o) {
 		
		return title.compareTo(((StockItem) o).title);
	}
	
}
