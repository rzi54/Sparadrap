package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

import java.util.ArrayList;

public class ElementsPrescription {

	private String number;
	private String date;
	private String client;
	private String doctor;
	private ArrayList<String> drugsList;
	private String specialist;
	private String userRecorder;

	/**
	 * 
	 * @param number_
	 * @param date_
	 * @param client_
	 * @param doctor_
	 * @param drugsList_
	 * @param specialist_
	 * @param userRecorder_
	 */
	
	public ElementsPrescription(String number_, String date_, String client_, String doctor_, ArrayList<String> drugsList_,
			String specialist_, String userRecorder_) {

		this.number = number_;
		this.date = date_;
		this.client = client_;
		this.doctor = doctor_;
		this.drugsList = drugsList_;
		this.specialist = specialist_;
		this.userRecorder = userRecorder_;
	}

	public String getNumber() {
		return number;
	}

	public String getDate() {
		return date;
	}

	public String getClient() {
		return client;
	}

	public String getDoctor() {
		return doctor;
	}

	public ArrayList<String> getDrugsList() {
		return drugsList;
	}

	public String getSpecialist() {
		return specialist;
	}
	
	public String getUserRecorder() {
		return userRecorder;
	}
	
}