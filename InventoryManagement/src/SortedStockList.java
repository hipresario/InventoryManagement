import java.io.Serializable;

public class SortedStockList implements ListInterface, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	private Node head;
	private int numItems;
	
	public SortedStockList (){
		this.head = null;
		this.numItems = 0;
	}
	
	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

	public boolean isEmpty() {
		return numItems == 0;
	}

	public int size() {
		return numItems;
	}

	public void addStock(Object item)
			throws ListIndexOutOfBoundsException {
		//add need to find stock title position by alphabetic order
		int pos = this.findStockByAphabeticOrder((StockItem)item);
		if (pos == 1){
				Node newNode = new Node(item, head);
				head = newNode;
				numItems ++;
		} else if ( pos > 1) {
				Node prev = findStockByIndex(pos-1);
				Node newNode = new Node (item, prev.getNext());
				prev.setNext(newNode);
				numItems ++;
		} else {
			throw new ListIndexOutOfBoundsException("Add stock list out of bonds index.");
		}
		
	}
	//find stock by index number
	private Node findStockByIndex (int index){
		Node curr = head;
		for (int skip = 1; skip < index; skip++){
			curr = curr.getNext();
		}
		return curr;
	}
	//find position for stock to be added by title in alphabetic order (a-z)
	private int findStockByAphabeticOrder (StockItem s){
		Node curr = head;
		int index = 1;
		if (numItems == 0){
			return index;
		}
		for (int skip = 1; skip <= this.numItems; skip++){
			//if current stock title > s.title stop
			if (((StockItem)curr.getItem()).getDVD().compareTo(s.getDVD()) >= 0){
				index = skip;
				break;
			} else {
				//get next stock to compare
				if (curr.getNext()!= null){
					curr = curr.getNext();
				} else {
					//last stock
					index = skip+1;	
				}
			}
		}
		return index;
	}
	
	public void removeStock(Object item) throws ListIndexOutOfBoundsException {
		int pos = findStockByAphabeticOrder ((StockItem)item);
		if (pos == 1){
				head = head.getNext();
				numItems --;
		} else if (pos > 1){
				Node prev = findStockByIndex (pos);
				prev.setNext(prev.getNext().getNext());
				numItems --;
		} else {
			throw new ListIndexOutOfBoundsException("Remove list out of bonds index.");
		}
	}

	public Object getStock(int index) throws ListIndexOutOfBoundsException {
		if (index >=1 && index <= numItems ) {
			Node curr = findStockByIndex (index);
			return curr.getItem();
		} else {
			throw new ListIndexOutOfBoundsException("List Index out of bounds on get");
		}
	}

	public void removeAll() {
		this.head = null;
		this.numItems = 0;
	}

	@Override
	public void add(int index, Object item)
			throws ListIndexOutOfBoundsException {
		
	}

	@Override
	public void remove(int index) throws ListIndexOutOfBoundsException {
		
	}

	@Override
	public Object get(int index) throws ListIndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

}
