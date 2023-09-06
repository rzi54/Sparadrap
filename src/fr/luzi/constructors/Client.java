package fr.luzi.constructors;

/**
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import fr.luzi.defense.PersonnalException;
import fr.luzi.defense.Regex;

public class Client extends Human {

	private String socialSecuNumber;
	private int mutual;
	private String doctor;
	private String specialist;

	/**
	 * 
	 * @param idNum_ : Identifiant du client.
	 * @param lastName_ : Prénom du client.
	 * @param firstName_ : Nom de famille du client.
	 * @param address_ : Adresse du client (num, rue et complément)
	 * @param postalCode_ : Code postal de l'adresse client.
	 * @param city_ : Ville du client.
	 * @param phone_ : Téléphone du client.
	 * @param mail_ : Email du client. 
	 * @param scialSecuNumber_ : Numério de sécurité sociale du client.
	 * @param mutual_ : Murtuelle du client.
	 * @param doctor_ : Médecin traitant du Client.
	 * @param specialist_ : Médecin spécialiste du client.
	 * @throws PersonnalException
	 */

	public Client(String idNum_, String lastName_, String firstName_, String address_, String postalCode_, String city_,
			String phone_, String mail_, String scialSecuNumber_, int mutual_, String doctor_, String specialist_)
			throws PersonnalException {

		super(idNum_, lastName_, firstName_, address_, postalCode_, city_, phone_, mail_);

		this.setSocialSecuNumber(scialSecuNumber_);
		this.setMutual(mutual_);
		this.setDoctor(doctor_);
		this.setSpecialist(specialist_);
		
	}

	public String getSocialSecuNumber() {
		return socialSecuNumber;
	}

	public void setSocialSecuNumber(String socialSecuNumber) throws PersonnalException {
		if (!socialSecuNumber.matches(Regex.REGEX_FalseSecuNumber)) {
			this.socialSecuNumber = socialSecuNumber;
		} else {
			this.socialSecuNumber = null;
			throw new PersonnalException("Le numéro de sécurité sociale doit être un nombre composé de 15 chiffres.");
		}
	}

	public int getMutual() {

		return mutual;
	}

	public void setMutual(int mutual) throws PersonnalException {
		if (Integer.valueOf(mutual) instanceof Integer) {
			this.mutual = mutual;
		} else {
			this.mutual = -1;
			throw new PersonnalException("L'identifiant de la mutuelle doit être un nombre entier.");
		}
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) throws PersonnalException {
		if (!doctor.matches(Regex.REGEX_FalseId)) {
			this.doctor = doctor;
		} else {
			this.doctor = null;
			throw new PersonnalException("L'identifiant du médecin doit être un nombre composé de 7 chiffres.");
		}
	}

	public String getSpecialist() {
		return specialist;
	}

	public void setSpecialist(String specialist) throws PersonnalException {
		if (!specialist.matches(Regex.REGEX_FalseId)) {
			this.specialist = specialist;
		} else {
			this.doctor = null;
			throw new PersonnalException("L'identifiant du spécialiste doit être un nombre composé de 7 chiffres.");
		}

	}

}