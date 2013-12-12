/**
 * Linked List reference based implementation 
 */
import java.io.Serializable;

public class ListReferenceBased implements ListInterface,Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;
	private Node head;
	private int numItems = 0;
	
	public ListReferenceBased (){
		head = null;
		numItems = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return numItems == 0;
	}

	@Override
	public int size() {
		return numItems;
	}

	private Node find (int index) throws ListIndexOutOfBoundsException{
		Node curr = head;
		if (index >= 1 && index <= numItems){
			for (int skip = 1; skip < index; skip++){
				curr = curr.getNext();
			}	
		} else {
			throw new ListIndexOutOfBoundsException("Find list out of bonds index: "+ index+".");
		} 
		
		return curr;
	}
	
	@Override
	public void add(int index, Object item)
			throws ListIndexOutOfBoundsException {
		
		if (index == 1){
			Node newNode = new Node(item, head);
			head = newNode; //assign head to the new node
			numItems ++;
			
		} else if (index > 1) {
			Node prev = find(index -1);
			Node newNode = new Node (item, prev.getNext());
			prev.setNext(newNode);
			numItems ++;
		} else {
			throw new ListIndexOutOfBoundsException("Add to list out of bonds index: "+index +".");
		}
		
	}
	@Override
	public void remove(int index) throws ListIndexOutOfBoundsException {
		if (index == 1){
			head = head.getNext();
			numItems --;
		} else if (index > 1){
			Node prev = find (index -1);
			prev.setNext(prev.getNext().getNext());
			numItems --;
		} else {
			throw new ListIndexOutOfBoundsException("Remove from list out of bonds index: "+ index+".");
		}
		
	}
	@Override
	public Object get(int index) throws ListIndexOutOfBoundsException {
		if (index >=1 && index <= numItems ) {
			Node curr = find (index);
			return curr.getItem();
		} else {
			throw new ListIndexOutOfBoundsException("Get list out of bounds index: "+index+".");
		}
	}
	@Override
	public void removeAll() {
		head = null;
		numItems = 0;
	}

}
