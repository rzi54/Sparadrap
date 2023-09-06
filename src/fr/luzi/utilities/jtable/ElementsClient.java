package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

public class ElementsClient {

	private String idNum;
	private String lastName;
	private String firstName;
	private String address;
	private String postalCode;
	private String city;
	private String phone;
	private String mail;
	private String scialSecuNumber;
	private String mutual;
	private String doctor;
	private String specialist;
	
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
	 * @param scialSecuNumber_
	 * @param mutual_
	 * @param doctor_
	 * @param specialist_
	 */
	
	public ElementsClient(String idNum_, String lastName_, String firstName_, String address_, String postalCode_,
			String city_, String phone_, String mail_, String scialSecuNumber_, String mutual_, String doctor_,
			String specialist_) {

		this.idNum = idNum_;
		this.lastName = lastName_;
		this.firstName = firstName_;
		this.address = address_;
		this.postalCode = postalCode_;
		this.city = city_;
		this.phone = phone_;
		this.mail = mail_;
		this.scialSecuNumber = scialSecuNumber_;
		this.mutual = mutual_;
		this.doctor = doctor_;
		this.specialist = specialist_;

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

	public String getScialSecuNumber() {
		return scialSecuNumber;
	}

	public String getMutual() {
		return mutual;
	}

	public String getDoctor() {
		return doctor;
	}

	public String getSpecialist() {
		return specialist;
	}


}