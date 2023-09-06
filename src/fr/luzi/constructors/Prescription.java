package fr.luzi.constructors;

import java.sql.Date;
import java.util.ArrayList;

import fr.luzi.defense.PersonnalException;
import fr.luzi.defense.Regex;

public class Prescription {
	
	private int id;
	private Date date;
	private String idClient;
	private String idDoctor;
	private ArrayList<String> drugs;
	private String idSpecialist;
	
	/**
	 * @param id_ : Identifiant de l'ordonnance.
	 * @param date_ : Date de l'enregistrement de l'ordonnance.
	 * @param idClient_ : Identifiant du client lié à cette ordonnance.
	 * @param idDoctor_ : Identitifiant du médecin émetteur de l'ordonnance.
	 * @param drugs_ :  Liste de la prescription (médicaments).
	 * @param idSpecialist_ : Identifiant du spécialiste si il y a lieu d'être.
	 * @throws PersonnalException
	 */
	
	public Prescription(int id_, Date date_, String idClient_, String idDoctor_, ArrayList<String> drugs_, String idSpecialist_) throws PersonnalException{
		
		this.setId(id_);
		this.setDate(date_);
		this.setIdClient(idClient_);
		this.setIdDoctor(idDoctor_);
		this.setDrugs(drugs_);
		this.setIdSpecialist(idSpecialist_);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws PersonnalException {
		if (Integer.valueOf(id) instanceof Integer) {
			this.id = id;
		} else {
			this.id = -1;
			throw new PersonnalException("L'identifiant de la prescription doit être un nombre entier.");
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
			throw new PersonnalException("L'identifiant du client doit être un nombre composé de 7 chiffres.");
		}
	}

	public String getIdDoctor() {
		return idDoctor;
	}

	public void setIdDoctor(String idDoctor) throws PersonnalException {
		if (!idDoctor.matches(Regex.REGEX_FalseId)) {
			this.idDoctor = idDoctor;
		} else {
			this.idDoctor = null;
			throw new PersonnalException("L'identifiant du médecin doit être un nombre composé de 7 chiffres.");
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

	public String getIdSpecialist() {
		return idSpecialist;
	}

	public void setIdSpecialist(String idSpecialist) throws PersonnalException {
		if (!idSpecialist.matches(Regex.REGEX_FalseId)) {
			this.idSpecialist = idSpecialist;
		} else {
			this.idSpecialist = null;
			throw new PersonnalException("L'identifiant du médecin spécialiste doit être un nombre composé de 7 chiffres.");
		}
	}
	
}
