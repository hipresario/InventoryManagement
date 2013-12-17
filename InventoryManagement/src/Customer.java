/**
 * Customer Information
 * Add/Remove Customer from DVD waiting list
 */
import java.io.Serializable;

public class Customer implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private String first;
	private String last;
	private String phone;
	private String address;
	public Customer (){
		first = "";
		last = "";
		phone = "";
		address = "";
	}
	public Customer (String first, String last, String phone, String address){
		this.first = first;
		this.last = last;
		this.phone = phone;
		this.address = address;
	}
	@Override
	public String toString(){
		return "Customer: " + this.first + " " + this.last + "\n"
				+ "Phone: " + this.phone + "\n"
				+"Address:" + this.address ;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
