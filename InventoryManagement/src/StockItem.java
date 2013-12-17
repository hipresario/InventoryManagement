import java.io.Serializable;
/**
 * @author user
 *
 */
public class StockItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DVD dvd;
	private int have;
	private int want;
	private ListReferenceBased waitingList;
	
	public StockItem(){
		this.dvd = new DVD();
		this.have = 0;
		this.want = 0;
		this.waitingList = new ListReferenceBased();
	}
	
	public StockItem(DVD dvd){
		this.dvd = dvd;
		this.have = 0;
		this.want = 0;
		this.waitingList = new ListReferenceBased();
	}
	
	
	public StockItem(DVD dvd, int have, int want, ListReferenceBased waitingList){
		this.dvd = dvd;
		this.have = have;
		this.want = want;
		this.waitingList = waitingList;
	}
	
	
	public DVD getDVD() {
		return dvd;
	}

	public void setDVD(DVD dvd) {
		this.dvd = dvd;
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
	//purchase order number
	public int getOrderNumber (){
		int order = (this.want - this.have);
		return (order > 0) ? order : 0;
	}
	
	@Override
	public String toString() {
		
		int size = this.waitingList.size();
		
		String wl = this.dvd.toString();
			   wl += "Want: " + this.want + "\n";
			   wl += "Have: " + this.have + "\n";
			   wl += "Waiting List: \n";
		if (size == 0){
			wl += "empty.";
		}else {
			for (int i= 1 ;i<= size;i++){
					wl += i+ " ";
				try {
					wl += ((Customer)this.waitingList.get(i)).toString();
					wl += "\n";
				} catch (ListIndexOutOfBoundsException e) {
					 //e.printStackTrace();
					wl +="waiting list error.\n";
				}
			}
		}
		
		return wl;
	}	
}
