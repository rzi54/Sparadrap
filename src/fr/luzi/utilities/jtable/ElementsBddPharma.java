package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

public class ElementsBddPharma {
	
	private String codeCIS;
	private String codeCIP7;
	private String presentation;
	private String status;
	private String state;
	private String date;
	private String codeCIP13;
	private String approval;
	private String repaymentRate;
	private String price;
	private String repaymentIndocation;
	
	/**
	 * Cette classe 
	 * @param codeCIS_
	 * @param codeCIP7_
	 * @param presentation_
	 * @param status_
	 * @param state_
	 * @param date_
	 * @param codeCIP13_
	 * @param approval_
	 * @param repaymentRate_
	 * @param price_
	 * @param repaymentIndocation_
	 */
	public ElementsBddPharma(String codeCIS_, String codeCIP7_, String presentation_, String status_, String state_,
			String date_, String codeCIP13_, String approval_, String repaymentRate_, String price_, String repaymentIndocation_) {
		
		this.codeCIS = codeCIS_;
		this.codeCIP7 = codeCIP7_;
		this.presentation = presentation_;
		this.status = status_;
		this.state = state_;
		this.date = date_;
		this.codeCIP13 = codeCIP13_;
		this.approval = approval_;
		this.repaymentRate = repaymentRate_;
		if(!price_.equals("empty")) {
			this.price = price_+" €";
		}else {
			this.price = price_;
		}
		this.repaymentIndocation = repaymentIndocation_;
		
	}
	
	public String getCodeCIS() {
		return codeCIS;
	}

	public String getCodeCIP7() {
		return codeCIP7;
	}

	public String getPresentation() {
		return presentation;
	}

	public String getStatus() {
		return status;
	}

	public String getState() {
		return state;
	}

	public String getDate() {
		return date;
	}

	public String getCodeCIP13() {
		return codeCIP13;
	}

	public String getApproval() {
		return approval;
	}

	public String getRepaymentRate() {
		return repaymentRate;
	}

	public String getPrice() {
		return price;
	}

	public String getRepaymentIndocation() {
		return repaymentIndocation;
	}

}