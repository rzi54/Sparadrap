package fr.luzi.constructors;

/**
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import java.sql.Date;
import java.util.ArrayList;

import fr.luzi.defense.PersonnalException;
import fr.luzi.defense.Regex;

public class Order {

	private int id;
	private Date date;
	private String idClient;
	private ArrayList<String> drugs;
	private Double price;
	private boolean prescription;
	private String idPrescription;
	
	/**
	 * @param id_ : Identifiant de l'achat.
	 * @param date_ : Date de l'achat.
	 * @param idClient_ : Identifiant du client acheteur.
	 * @param drugs_ : Liste de la prescription (médicaments).
	 * @param price_ : Prix total de la prescription.
	 * @param prescription_ : Achat sur ordonnance ou non.
	 * @param idPrescription_ : Idenfifiant de l'ordonnance si il y a lieu d'être.
	 * @throws PersonnalException
	 */
	
	public Order(int id_, Date date_, String idClient_, ArrayList<String> drugs_, Double price_, boolean prescription_,
			String idPrescription_) throws PersonnalException {

		this.setId(id_);
		this.setDate(date_);
		this.setIdClient(idClient_);
		this.setDrugs(drugs_);
		this.setPrice(price_);
		this.setPrescription(prescription_);
		this.setIdPrescription(idPrescription_);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws PersonnalException {
		if (Integer.valueOf(id) instanceof Integer) {
			this.id = id;
		} else {
			this.id = -1;
			throw new PersonnalException("L'identifiant d'achat doit être un nombre entier.");
		}
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) throws PersonnalException {
		if (date instanceof Date) {
			this.date = date;
		} else {
			this.date = null;
			throw new PersonnalException("La date d'achat n'est pas valide.");
		}
	}

	public String getIdClient() {
		return idClient;
	}

	public void setIdClient(String idClient) throws PersonnalException {
		if (!idClient.matches(Regex.REGEX_FalseId)) {
			this.idClient = idClient;
		} else {
			this.idClient = null;
			throw new PersonnalException("L'identifiant client doit être un nombre composé de 7 chiffres.");
		}
	}

	public ArrayList<String> getDrugs() {
		return drugs;
	}

	public void setDrugs(ArrayList<String> drugs) throws PersonnalException {
		if (drugs instanceof ArrayList) {
			this.drugs = drugs;
		} else {
			this.drugs = null;
			throw new PersonnalException("La liste de médicament doit être un ArrayList.");
		}
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) throws PersonnalException {
		if (Double.valueOf(price) instanceof Double) {
			this.price = price;
		} else {
			this.price = -1.0;
			throw new PersonnalException("Le prix doit être un nombre de type Double.");
		}
	}

	public boolean isPrescription() {
		return prescription;
	}

	public void setPrescription(boolean prescription) throws PersonnalException {
		if (Boolean.valueOf(prescription) instanceof Boolean) {
			this.prescription = prescription;
		} else {
			this.prescription = false;
			throw new PersonnalException("La colonne de prescritption.");
		}
	}

	public String getIdPrescription() {
		return idPrescription;
	}

	public void setIdPrescription(String idPrescription) throws PersonnalException {
		if (isPrescription()) {
			if (Integer.valueOf(Integer.parseInt(idPrescription)) instanceof Integer) {
				this.idPrescription = idPrescription;
			} else {
				this.idPrescription = null;
				throw new PersonnalException("L'identifiant de la prescription doit être un nombre entier.");
			}
		} else {
			if (idPrescription.equals("empty")) {
				this.idPrescription = idPrescription;
			} else {
				this.idPrescription = null;
				throw new PersonnalException(
						"L'identifiant de la prescription doit être ici une chaîne de caractère (String) comportant la valeur \"empty\".");
			}
		}
	}

}
