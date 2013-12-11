import java.io.Serializable;


public class SortedList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6L;
	private Node head;
	private int numItems;
	
	public SortedList (){
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
		// TODO Auto-generated method stub
		return numItems;
	}

	public void add(Object item)
			throws ListIndexOutOfBoundsException {
		//add need to find stock title position
		int pos = findStockItem ((StockItem)item);
		//Node curr = find (pos);
		if (pos == 1){
				Node newNode = new Node(item, head);
				head = newNode;
				numItems ++;
		} else if ( pos > 1) {
				Node prev = find(pos-1);
				Node newNode = new Node (item, prev.getNext());
				prev.setNext(newNode);
				numItems ++;
		} else {
			throw new ListIndexOutOfBoundsException("Add list out of bonds index.");
		}
		
	}
	private Node find (int index){
		Node curr = head;
		for (int skip = 1; skip < index; skip++){
			curr = curr.getNext();
		}
		return curr;
	}
	//find by Alphabetic order title
	private int findStockItem (StockItem s){
		Node curr = head;
		int index = 1;
		if (numItems == 0){
			return index;
		}
		
		for (int skip = 1; skip <= this.numItems; skip++){
			if (((StockItem)curr.getItem()).compareTo(s) >= 0){
				index = skip;
				break;
			} else {
				if (curr.getNext()!= null){
					curr = curr.getNext();
				} else {
					index = skip+1;	
				}
			}
		}
		return index;
	}
	
	public void remove(Object item) throws ListIndexOutOfBoundsException {
		int pos = findStockItem ((StockItem)item);
		
		if (pos == 1){
				head = head.getNext();
				numItems --;
		} else if (pos > 1){
				Node prev = find (pos);
				prev.setNext(prev.getNext().getNext());
				numItems --;
		} else {
			throw new ListIndexOutOfBoundsException("Remove list out of bonds index.");
		}
	}

	public Object get(int index) throws ListIndexOutOfBoundsException {
		if (index >=1 && index <= numItems ) {
			
			Node curr = find (index);
			return curr.getItem();
		} else {
			throw new ListIndexOutOfBoundsException("List Index out of bounds on get");
		}
	}

	public void removeAll() {
		// TODO Auto-generated method stub
		this.head = null;
		this.numItems = 0;
				
	}

}
