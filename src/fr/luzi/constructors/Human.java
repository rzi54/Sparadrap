package fr.luzi.constructors;

/**
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import fr.luzi.defense.PersonnalException;
import fr.luzi.defense.Regex;

public abstract class Human {

	private String idNum;
	private String lastName;
	private String firstName;
	private String address;
	private String postalCode;
	private String city;
	private String phone;
	private String mail;

	/**
	 * @param idNum_ : Identifiant de la personne.
	 * @param lastName_ : Prénom de la personne.
	 * @param firstName_ : Nom de famille de la personne.
	 * @param address_ : Adresse de la personne (num, rue et complément)
	 * @param postalCode_ : Code postal de la personne.
	 * @param city_ : Ville de la personne.
	 * @param phone_ : Téléphone de la personne.
	 * @param mail_ : Email de la personne. 
	 * @throws PersonnalException
	 */
	
	public Human(String idNum_, String lastName_, String firstName_, String address_, String postalCode_, String city_,
			String phone_, String mail_) throws PersonnalException {

		this.setIdNum(idNum_);
		this.setLastName(lastName_);
		this.setFirstName(firstName_);
		this.setAddress(address_);
		this.setPostalCode(postalCode_);
		this.setCity(city_);
		this.setPhone(phone_);
		this.setMail(mail_);
		

	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) throws PersonnalException {
		if (!idNum.matches(Regex.REGEX_FalseId)) {
			this.idNum = idNum;
		} else {
			this.idNum = null;
			throw new PersonnalException("L'identifiant doit être un nombre composé de 7 chiffres.");
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws PersonnalException {
		if (!lastName.matches(Regex.REGEX_firstAndLastName)) {
			this.lastName = lastName;
		} else {
			this.lastName = null;
			throw new PersonnalException("Le nom saisi n'est pas valide.");
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws PersonnalException {
		if (!firstName.matches(Regex.REGEX_firstAndLastName)) {
			this.firstName = firstName;
		} else {
			this.firstName = null;
			throw new PersonnalException("Le prénom saisi n'est pas valide.");
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) throws PersonnalException {
		if (!address.matches(Regex.REGEX_addressNumAndStreet)) {
			this.address = address;
		} else {
			this.address = null;
			throw new PersonnalException("L'adresse saisie n'est pas valide.");
		}
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) throws PersonnalException {
		if (!postalCode.matches(Regex.REGEX_postalCodeFr)) {
			this.postalCode = postalCode;
		} else {
			this.postalCode = null;
			throw new PersonnalException("Le code postal saisi n'est pas valide.");
		}
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) throws PersonnalException {
		if (!city.matches(Regex.REGEX_cityFr)) {
			this.city = city;
		} else {
			this.city = null;
			throw new PersonnalException("La ville saisie n'est pas valide.");
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) throws PersonnalException {
		if (!phone.matches(Regex.REGEX_phoneFR)) {
			this.phone = phone;
		} else {
			this.phone = null;
			throw new PersonnalException("Le numéro de téléphone saisi n'est pas valide.");
		}
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) throws PersonnalException {
		if (!mail.matches(Regex.REGEX_email)) {
			this.mail = mail;
		} else {
			this.mail = null;
			throw new PersonnalException("L'email saisi n'est pas valide.");
		}
	}

}
