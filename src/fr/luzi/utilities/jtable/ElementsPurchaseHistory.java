package fr.luzi.utilities.jtable;

/**
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import java.util.ArrayList;

public class ElementsPurchaseHistory {

	private int buyIndex;
	private String date;
	private String idClient;
	private ArrayList<String> drusList;
	private Double price;
	private String paymentType;
	private String prescription;
	private String idPrescription;
	private String userRecorder;
	
	/**
	 * 
	 * @param buyIndex_
	 * @param date_
	 * @param idClient_
	 * @param drusList_
	 * @param price_
	 * @param paymentType_
	 * @param prescription_
	 * @param idPrescription_
	 * @param userRecorder_
	 */
	
	public ElementsPurchaseHistory(int buyIndex_, String date_, String idClient_, ArrayList<String> drusList_,
			Double price_, String paymentType_, String prescription_, String idPrescription_, String userRecorder_) {

		this.buyIndex = buyIndex_;
		this.date = date_;
		this.idClient = idClient_;
		this.drusList = drusList_;
		this.price = price_;
		this.paymentType = paymentType_;
		this.prescription = prescription_;
		this.idPrescription = idPrescription_;
		this.userRecorder = userRecorder_;
	}

	public int getBuyIndex() {
		return buyIndex;
	}

	public String getDate() {
		return date;
	}

	public String getIdClient() {
		return idClient;
	}

	public ArrayList<String> getDrusList() {
		return drusList;
	}

	public Double getPrice() {
		return price;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public String getPrescription() {
		return prescription;
	}

	public String getIdPrescription() {
		return idPrescription;
	}
	
	public String getUserRecorder() {
		return userRecorder;
	}
	
	
}