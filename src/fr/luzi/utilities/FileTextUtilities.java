package fr.luzi.utilities;

/**
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import fr.luzi.application.App;

public class FileTextUtilities {

	/**
	 * Vérification de l'existence d'un fichier quelconque.
	 * 
	 * @param Le chemin du fichier à tester.
	 * @return "true" si le fichier existe, "false" sinon.
	 */
	public static boolean checkFileExist(String filePath_) {

		boolean fileExist = false;

		File file = new File(filePath_);

		if (file.exists()) {
			fileExist = true;
		}

		return fileExist;
	}

	/**
	 * Ouvre un fichier .txt et enregistre chaque ligne dans un ArrayList<String>
	 * avant de le renvoyer.
	 * 
	 * @param fileName_, une chaîne de caractères représentant le chemin du fichier
	 *                   à charger.
	 * @return un ArrayList<String>
	 * 
	 */
	public static ArrayList<String> readFileText(String filePath_, String cse) {

		ArrayList<String> dataFile = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath_), "ISO-8859-1"))) {
			String data;
			while ((data = reader.readLine()) != null) {
				dataFile.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(DateGestion.getTimeForConsole() + "Nombre d'entrées de la base " + filePath_ + " = "
				+ dataFile.size() + " entrées");
		switch (cse) {
		case "bddPharma":
			App.bdd1NbOfElement = dataFile.size();
		case "PurshaseHistory":
		default:		
			break;
		}

		return dataFile;
	}

	/**
	 * Ouvre la base de données client, recherche un client, et renvoi toutes les
	 * informations relatives à ce client.
	 * 
	 * @param idClient, une chaîne de caractères représentant l'identifiant du
	 *                  client.
	 * @return un tableau String[]
	 * 
	 */
	public static ArrayList<String[]> searchClient(String idClient) {

		ArrayList<String[]> informationsClient = new ArrayList<String[]>();
		String[] client = new String[12];
		String[] mutual = new String[8];
		String[] doctor = new String[11];

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(App.PATH_DATABASE_CLIENTS_FILE), "ISO-8859-1"))) {
			String data;
			while ((data = reader.readLine()) != null) {

				if (data.split(";")[0].equals(idClient)) {

					client = data.split(";");

					if (!client[9].equals("")) {
						mutual = searchMutual(client[9]);
					}

					if (!client[10].equals("")) {
						doctor = searchDoctor(client[10]);
					}

					informationsClient.add(client);
					informationsClient.add(mutual);
					informationsClient.add(doctor);
					break;

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return informationsClient;
	}

	/**
	 * Ouvre la base de données mutuelle, recherche une mutuelle, et renvoi toutes
	 * les informations relatives à cette mutuelle.
	 * 
	 * @param idMutual, une chaîne de caractères représentant l'identifiant de la
	 *                  mutuelle.
	 * @return un tableau String[]
	 * 
	 */
	public static String[] searchMutual(String idMutual) {

		String[] mutual = new String[9];

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(App.PATH_DATABASE_MUTUALS_FILE), "ISO-8859-1"))) {
			String data;
			while ((data = reader.readLine()) != null) {

				if (data.split(";")[0].equals(idMutual)) {
					mutual = data.split(";");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(mutual.length);
		return mutual;
	}

	/**
	 * 
	 * Ouvre la base de données médecin, recherche un médecin, et renvoi toutes les
	 * informations relatives à ce médecin.
	 * 
	 * @param idDoctor, une chaîne de caractères représentant l'identifiant du
	 *                  médecin.
	 * @return un tableau String[]
	 * 
	 */
	public static String[] searchDoctor(String idDoctor) {

		String[] doctor = new String[11];

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(App.PATH_DATABASE_DOCTORS_FILE), "ISO-8859-1"))) {
			String data;
			while ((data = reader.readLine()) != null) {

				if (data.split(";")[0].equals(idDoctor)) {
					doctor = data.split(";");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return doctor;
	}

	/**
	 * Ouvre un fichier .txt et enregistre chaque ligne dans un ArrayList<String>
	 * avant de le renvoyer.
	 * 
	 * @param fileName_, une chaîne de caractères représentant le chemin du fichier
	 *                   à charger.
	 * @return un ArrayList<String>
	 * 
	 */
	public static String readDataBaseClients(String idClient) {

		String filePath_ = App.PATH_DATABASE_CLIENTS_FILE;
		String clientNames = "";

		idClient = idClient.replaceAll("\\s", "");

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath_), "ISO-8859-1"))) {

			String data;
			while ((data = reader.readLine()) != null) {

				ArrayList<String> list = new ArrayList<>(Arrays.asList(data.split(";")));

				if (list.get(0).equals(idClient)) {

					clientNames = list.get(1) + " " + list.get(2);

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return clientNames;
	}

	/**
	 * Ouvre un fichier .txt et enregistre chaque ligne dans un ArrayList<String>
	 * avant de le renvoyer.
	 * 
	 * @param fileName_, une chaîne de caractères représentant le chemin du fichier
	 *                   à charger.
	 * @return un ArrayList<String>
	 * 
	 */
	public static String readDataBaseDrugs(String idDrug) {

		String filePath_ = App.PATH_DATABASE_DRUGS_FILE;
		String drugName = "";

		idDrug = idDrug.replaceAll("\\s", "");

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath_), "ISO-8859-1"))) {

			String data;
			while ((data = reader.readLine()) != null) {

				ArrayList<String> list = new ArrayList<>(Arrays.asList(data.split(";")));

				if (list.get(0).equals(idDrug)) {

					drugName = list.get(1);

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return drugName;
	}

	/**
	 * Ouvre un fichier .txt et enregistre chaque ligne dans un ArrayList<String>
	 * avant de le renvoyer.
	 * 
	 * @param fileName_, une chaîne de caractères représentant le chemin du fichier
	 *                   à charger.
	 * @return un ArrayList<String>
	 * 
	 */
	public static String readDataBaseDoctors(String idDoctor) {

		String filePath_ = App.PATH_DATABASE_DOCTORS_FILE;
		String doctorName = "";

		idDoctor = idDoctor.replaceAll("\\s", "");

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath_), "ISO-8859-1"))) {

			String data;
			while ((data = reader.readLine()) != null) {

				ArrayList<String> list = new ArrayList<>(Arrays.asList(data.split(";")));

				if (list.get(0).equals(idDoctor)) {

					doctorName = list.get(1) + " " + list.get(2) + " (" + list.get(10) + ")";

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return doctorName;
	}

	/**
	 * Ouvre un fichier .txt et enregistre chaque ligne dans un ArrayList<String>
	 * avant de le renvoyer.
	 * 
	 * @param fileName_, une chaîne de caractères représentant le chemin du fichier
	 *                   à charger.
	 * @return un ArrayList<String>
	 * 
	 */
	public static String readDataBasePrescriptions(String idPrescription) {

		String filePath_ = App.PATH_DATABASE_PRESCRIPTIONS_FILE;
		String drugName = "";

		idPrescription = idPrescription.replaceAll("\\s", "");

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath_), "ISO-8859-1"))) {

			String data;
			while ((data = reader.readLine()) != null) {

				ArrayList<String> list = new ArrayList<>(Arrays.asList(data.split(";")));

				if (list.get(0).equals(idPrescription)) {

					drugName = list.get(1) + " " + list.get(2);

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return drugName;
	}

	/**
	 * Ouvre un fichier .txt et enregistre chaque ligne dans un ArrayList<String>
	 * avant de le renvoyer.
	 * 
	 * @param fileName_, une chaîne de caractères représentant le chemin du fichier
	 *                   à charger.
	 * @return un ArrayList<String>
	 * 
	 */
	public static String readDataBaseMutualsName(String idMutual) {

		String filePath_ = App.PATH_DATABASE_MUTUALS_FILE;
		String mutualName = "";

		idMutual = idMutual.replaceAll("\\s", "");

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath_), "ISO-8859-1"))) {

			String data;
			while ((data = reader.readLine()) != null) {

				ArrayList<String> list = new ArrayList<>(Arrays.asList(data.split(";")));

				if (list.get(0).equals(idMutual)) {

					mutualName = list.get(1);

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mutualName;
	}

	/**
	 * obsolète ! Il s'agissait là de la première méthode utilisée pour la connexion
	 * utilisateur. La nouvelle méthode utilisée, bien plus sûre, est :
	 * fr.luzi.defense.FileCipher.getUserFileCiphered();
	 * 
	 * Cette méthode ouvre le fichier contenant les informations de connexion des
	 * utilisateurs, elle vérifie si les paramètres fournis par l'utilisateur (id et
	 * mot de passe) existent bien et sont correctes. Si tel est le cas, la méthode
	 * retourne la valeur booléenne "true" et l'utilisateur entre alors dans
	 * l'application, sinon elle retourne "false" et alors l'utilisateur reste sur
	 * la fenêtre de connexion. Dans le cas où le fichier est manquant la méthode
	 * fait appel à la méthode Application.outSystemMessage(), ce qui provoque une
	 * erreur critique (ShowDialogMessage) et ferme l'application.
	 * 
	 * @param id : une chaîne de caractères représentant l'identifiant de
	 *           l'utilisateur.
	 * @param pw : un tableau de caractères représentant le mot-de-passe de
	 *           l'utilisateur.
	 * @return une valeur bolléenne : "false", l'une des deux informations de
	 *         connexion est incorrecte. "true", les informations de connexion sont
	 *         correctes.
	 */
	public static boolean readUserFile(String filePath_, String id, String pw) {

		boolean userValid = false;

		if (checkFileExist(filePath_)) {
			try {

				File fileUser = new File(filePath_);
				Scanner myReader = new Scanner(fileUser);

				while (myReader.hasNextLine()) {

					String data = myReader.nextLine();
					ArrayList<String> list = new ArrayList<>(Arrays.asList(data.split(",")));

					if (list.get(0).equals(id) && list.get(1).equals(pw)) {
						App.outSystemMessage();
					}

				}

				myReader.close();

			} catch (FileNotFoundException e) {

				System.out.println(DateGestion.getTimeForConsole() + "Le fichier des utilisateur est introuvable !");
				e.printStackTrace();
				App.outSystemMessage();
			}

			return userValid;

		} else {

			return false;
		}
	}

	/**
	 * Ouvre un fichier .txt et compte toutes les lignes qu'il contient.
	 * 
	 * @param fileName_, une chaîne de caractères représentant le chemin du fichier
	 *                   à compter.
	 * @return un entier représentant le nombre de lignes que contient le fichier.
	 * 
	 */
	public static int dataBaseCountLine(String filePath) {

		int cnt = 0;

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath), "ISO-8859-1"))) {

			while ((reader.readLine()) != null) {

				cnt++;

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return cnt;
	}

	/**
	 * Ouvre un fichier, ajoute un retour de ligne (uniquement si le fichier
	 * contient déjà des lignes) et écris une ligne dans ce fichier.
	 * 
	 * @param filePath : le chemin du fichier dans lequel écrire.
	 * @param newLine  : une chaîne de caractères à écrire dans le fichier.
	 */
	public static void writeFile(String filePath, String newLine) {

		try {

			FileOutputStream fos = new FileOutputStream(filePath, true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.ISO_8859_1);

			BufferedWriter bufferedWriter = new BufferedWriter(osw);

			if (dataBaseCountLine(filePath) > 0) {
				bufferedWriter.newLine();
			}

			bufferedWriter.write(newLine);

			bufferedWriter.close();

		} catch (IOException e) {

			System.err.println("Une erreur s'est produite : " + e.getMessage());

		}

	}
}
