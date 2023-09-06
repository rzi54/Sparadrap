package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

public class ElementsMutual {

	private int id;
	private String name;
	private String address;
	private String postalCode;
	private String city;
	private String phone;
	private String mail;
	private int	departement;
	private Double reimbursementRate;

	public ElementsMutual(int id_,String name_, String address_, String postalCode_,
			String city_, String phone_, String mail_, int departement_, Double reimbursementRate_) {
		
		this.id = id_;
		this.name = name_;
		this.address = address_;
		this.postalCode = postalCode_;
		this.city = city_;
		this.phone = phone_;
		this.mail = mail_;
		this.departement = departement_;
		this.reimbursementRate = reimbursementRate_;
		
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCity() {
		return city;
	}

	public String getPhone() {
		return phone;
	}

	public String getMail() {
		return mail;
	}

	public int getDepartement() {
		return departement;
	}

	public Double getReimbursementRate() {
		return reimbursementRate;
	}

}