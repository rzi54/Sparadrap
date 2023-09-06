package fr.luzi.constructors;

/**
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import fr.luzi.defense.PersonnalException;
import fr.luzi.defense.Regex;

public class Doctor extends Human {

	private String numRRPS;
	private int numPatients;
	private String speciality;

	/**
	 * @param idNum_ : Identifiant du médecin.
	 * @param lastName_ : Prénom du médecin.
	 * @param firstName_ : Nom de famille du médecin.
	 * @param address_ : Adresse du médecin (num, rue et complément)
	 * @param postalCode_ : Code postal de l'adresse médecin.
	 * @param city_ : Ville du médecin.
	 * @param phone_ : Téléphone du médecin.
	 * @param mail_ : Email du médecin. 
	 * @param numRRPS_ : Numéro d'agrément RRPS du médécin.
	 * @param numPatients_ : Nombre de patients du médecin.
	 * @param speciality_ : Spécialité médicale du médecin.
	 * @throws PersonnalException
	 */

	public Doctor(String idNum_, String lastName_, String firstName_, String address_, String postalCode_, String city_,
			String phone_, String mail_, String numRRPS_, int numPatients_, String speciality_)
			throws PersonnalException {

		super(idNum_, lastName_, firstName_, address_, postalCode_, city_, phone_, mail_);

		this.setNumRRPS(numRRPS_);
		this.setNumPatients(numPatients_);
		this.setSpeciality(speciality_);
		
	}

	public String getNumRRPS() {
		return numRRPS;
	}

	public void setNumRRPS(String numRRPS) throws PersonnalException {
		if (!numRRPS.matches(Regex.REGEX_FalseRRPSNumber)) {
			this.numRRPS = numRRPS;
		} else {
			this.numRRPS = null;
			throw new PersonnalException(
					"Le numéro d'agrément RRPS du médecin doit être un nombre composé de 15 chiffres.");
		}
	}

	public int getNumPatients() {
		return numPatients;
	}

	public void setNumPatients(int numPatients) throws PersonnalException {
		if (Integer.valueOf(numPatients) instanceof Integer) {
			this.numPatients = numPatients;
		} else {
			this.numPatients = -1;
			throw new PersonnalException("Le nombre de patients du médecin doit être un nombre entier.");
		}
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) throws PersonnalException {
		if (!speciality.matches(Regex.REGEX_ProffesionsMedicals)) {
			this.speciality = speciality;
		} else {
			this.speciality = null;
			throw new PersonnalException("La spécialité du médecin doit être spécifiée.");
		}
	}

}