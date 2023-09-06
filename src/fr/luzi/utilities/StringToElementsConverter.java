package fr.luzi.utilities;

/**
 * Cette classe permet de convertir des chaînes de caractères issues de lignes de
 * texte (provenant des fichiers .bdd présents dans le dossier
 * "ressources/bdd/") en objets spécifiques à la base de données à laquelles
 * elles appartiennent.
 * 
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
 * 
 */

import java.util.ArrayList;

import fr.luzi.utilities.jtable.ElementsBddPharma;
import fr.luzi.utilities.jtable.ElementsClient;
import fr.luzi.utilities.jtable.ElementsDoctor;
import fr.luzi.utilities.jtable.ElementsDrug;
import fr.luzi.utilities.jtable.ElementsMutual;
import fr.luzi.utilities.jtable.ElementsPrescription;
import fr.luzi.utilities.jtable.ElementsPurchaseHistory;

public class StringToElementsConverter {

	/**
	 * Cette méthode convertie les données issues du fichier CIS_CIP_bdpm.dbb de
	 * type String en type Objet ElementsBddPharma
	 * 
	 * @param Une chaîne de caractères issue d'une ligne de texte.
	 * @return UUne objet de type ElementsBddPharma
	 */
	public static ElementsBddPharma stringToElementsBddPharma(String str) {

		// --- Découpage de la chaîne de caractères avant mise en objet
		// ElementsBddPharma.
		String[] values = str.split(";");

		// --- Création et retour de l'Element.
		return new ElementsBddPharma(values[0], values[1], values[2], values[3], values[4], values[5], values[6],
				values[7], values[8], values[9], values[10]);
	}

	/**
	 * Cette méthode convertie les données issues du fichier purchase-history.dbb de
	 * type String en type Objet ElementsPurchaseHistory
	 * 
	 * @param Une chaîne de caractères issue d'une ligne de texte.
	 * @return Une objet de type ElementsBddPharma
	 */
	public static ElementsPurchaseHistory stringToElementsPurchaseHistory(String str) {

		// --- Découpage de la chaîne de caractères avant mise en objet
		// ElementsPurchaseHistory.
		String[] values = str.split(";");

		// --- Conversion du timeStamp en date
		long ts = Long.valueOf(values[1]);
		String dateConverted = DateGestion.tsToDateBdd(ts);

		// --- ArrayList(String)en vrai ArrayList (drugsList).
		String[] drugsList_ = values[3].replaceAll("[{}\\s]", "").split(",");

		ArrayList<String> drugsList = new ArrayList<>();
		for (String element : drugsList_) {
			drugsList.add(FileTextUtilities.readDataBaseDrugs(element));
		}

		// --- Mode de paiement.
		String paymentType;

		if (values[5].contains("0")) {

			paymentType = "Espèce";

		} else {

			paymentType = "Carte bancaire";

		}

		// --- Relatif au type presciption (avec ou sans ordonnance).
		String isPrescription;

		if (Integer.parseInt(values[6]) == 0) {
			isPrescription = "Non";
		} else {
			isPrescription = "Oui";
		}

		String idPrescription;

		if (values[7].contains("empty")) {
			idPrescription = "-";
		} else {
			idPrescription = values[7];
		}

		// --- Création et retour de l'Element.
		return new ElementsPurchaseHistory(Integer.parseInt(values[0]), dateConverted,
				FileTextUtilities.readDataBaseClients(values[2]), drugsList, Double.parseDouble(values[4]), paymentType,
				isPrescription, idPrescription, values[8]);
	}

	/**
	 * Cette méthode convertie les données issues du fichier drugs.dbb de type
	 * String en type Objet ElementsDrug
	 * 
	 * @param Une chaîne de caractères issue d'une ligne de texte.
	 * @return Une objet de type ElementsDrug
	 */
	public static ElementsDrug stringToElementsDrug(String str) {

		// --- Découpage de la chaîne de caractères avant mise en objet
		// ElementsDrug.
		String[] values = str.split(";");

		// --- Création de l'Element.
		return new ElementsDrug(Integer.parseInt(values[0]), values[1], values[2], Double.parseDouble(values[3]),
				values[4], values[5]);
	}

	/**
	 * Cette méthode convertie les données issues du fichier prescriptions.dbb de
	 * type String en type Objet ElementsPrescription
	 * 
	 * @param Une chaîne de caractères issue d'une ligne de texte.
	 * @return Une objet de type ElementsBddPharma
	 */
	public static ElementsPrescription stringToElementsPrescription(String str) {

		// --- Découpage de la chaîne de caractères avant mise en objet
		// ElementsPrescription.
		String[] values = str.split(";");

		// --- Conversion du timeStamp en date
		long ts = Long.valueOf(values[1]);
		String dateConverted = DateGestion.tsToDateBdd(ts);

		// --- ArrayList(String)en vrai ArrayList (drugsList)
		String[] drugsList_ = values[4].replaceAll("[{}\\s]", "").split(",");

		ArrayList<String> drugsList = new ArrayList<>();
		for (String element : drugsList_) {
			drugsList.add(FileTextUtilities.readDataBaseDrugs(element));
		}

		// --- Création de l'Element.
		return new ElementsPrescription(values[0], dateConverted, FileTextUtilities.readDataBaseClients(values[2]),
				FileTextUtilities.readDataBaseDoctors(values[3]), drugsList,
				FileTextUtilities.readDataBaseDoctors(values[5]), values[6]);
	}

	/**
	 * Cette méthode convertie les données issues du fichier client.dbb de type
	 * String en type Objet ElementsClient
	 * 
	 * @param Une chaîne de caractères issue d'une ligne de texte.
	 * @return Une objet de type ElementsClient
	 */
	public static ElementsClient stringToElementsClient(String str) {

		// --- Découpage de la chaîne de caractèresavant mise en objet ElementsClient.
		String[] values = str.split(";");

		// --- Création de l'Element.
		return new ElementsClient(values[0], values[1], values[2], values[3], values[4], values[5], values[6],
				values[7], values[8], FileTextUtilities.readDataBaseMutualsName(values[9]),
				FileTextUtilities.readDataBaseDoctors(values[10]), FileTextUtilities.readDataBaseDoctors(values[11]));
	}

	/**
	 * Cette méthode convertie les données issues du fichier doctors.dbb de type
	 * String en type Objet ElementsDoctor
	 * 
	 * @param Une chaîne de caractères issue d'une ligne de texte.
	 * @return Une objet de type ElementsDoctor
	 */
	public static ElementsDoctor stringToElementsDoctor(String str) {

		// --- Découpage de la chaîne de caractères avant mise en objet ElementsDoctor.
		String[] values = str.split(";");
		// --- Création de l'Element.
		return new ElementsDoctor(values[0], values[1], values[2], values[3], values[4], values[5], values[6],
				values[7], values[8], Integer.parseInt(values[9]), values[10]);
	}

	/**
	 * Cette méthode convertie les données issues du fichier mutual.dbb de type
	 * String en type Objet ElementsBddPharma
	 * 
	 * @param Une chaîne de caractères issue d'une ligne de texte.
	 * @return Une objet de type ElementsMutual
	 */
	public static ElementsMutual stringToElementsMutual(String str) {

		// --- Découpage de la chaîne de caractères avant mise en objet ElementsMutual.
		String[] values = str.split(";");
		// --- Création de l'Element.
		return new ElementsMutual(Integer.parseInt(values[0]), values[1], values[2], values[3], values[4], values[5],
				values[6], Integer.parseInt(values[7]), Double.parseDouble(values[8]));
	}

	/**
	 * @info seulement pour tester d'eventuelles erreurs. À supprimer avant
	 *       deploiement final.
	 * @param text
	 * @param targetChar
	 * @return un entier
	 */
	public static int countOccurrences(String text, char targetChar) {
		int count = 0;

		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == targetChar) {
				count++;
			}
		}

		return count;
	}

}
