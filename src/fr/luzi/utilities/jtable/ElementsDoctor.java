package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

public class ElementsDoctor {

	private String idNum;
	private String lastName;
	private String firstName;
	private String address;
	private String postalCode;
	private String city;
	private String phone;
	private String mail;
	private String numRRPS;
	private int numPatients;
	private String speciality;
	
	/**
	 * 
	 * @param idNum_
	 * @param lastName_
	 * @param firstName_
	 * @param address_
	 * @param postalCode_
	 * @param city_
	 * @param phone_
	 * @param mail_
	 * @param numRRPS_
	 * @param numPatients_
	 * @param speciality_
	 */
	
	public ElementsDoctor(String idNum_, String lastName_, String firstName_, String address_, String postalCode_,
			String city_, String phone_, String mail_, String numRRPS_, int numPatients_, String speciality_) {

		this.idNum = idNum_;
		this.lastName = lastName_;
		this.firstName = firstName_;
		this.address = address_;
		this.postalCode = postalCode_;
		this.city = city_;
		this.phone = phone_;
		this.mail = mail_;
		this.numRRPS = numRRPS_;
		this.numPatients = numPatients_;
		this.speciality = speciality_;

	}

	public String getIdNum() {
		return idNum;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
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

	public String getNumRRPS() {
		return numRRPS;
	}

	public int getNumPatients() {
		return numPatients;
	}

	public String getSpeciality() {
		return speciality;
	}

}