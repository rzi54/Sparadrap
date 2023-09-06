package fr.luzi.defense;

/**
 * Classe défensive : Algorithme de chiffrement par flot pour fichier.
 * 
 * Cette classe est dédiée au codage défensif et permet le chiffrement
 * reversible de données sensibles via un algorithme de chiffrement par flot
 * suivant une loi de groupe XOR. Elle permet le chiffrement du fichier
 * "data-user-crypted" contenant les informations de connexion des utilisateurs
 * enregistrés sur l'application. La clé servant au chiffrement et au
 * déchiffrement se situe dans la classe MasterKey. J'ai écris la méthode de
 * chiffrement pour chiffrer en première instance le fichier "data-user", mais
 * l'application n'en fait ici jamais usage. Elle déchiffre seulement le fichier
 * afin de "checker" si les informations de connexion sont correctes quand un
 * utilisateur tente de se connecter à l'application. Les données en clair
 * n'apparaissent donc que dans la mémoire de la machine. Après usage, le
 * tableau qui contient les données est mis à nul.
 * 
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
 * 
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import fr.luzi.application.App;
import fr.luzi.utilities.DateGestion;
import fr.luzi.utilities.FileTextUtilities;

public class FileCipher {

	/**
	 * Cette méthode déchiffre et ouvre le fichier contenant les informations de
	 * connexion des utilisateurs, elle vérifie ensuite si les paramètres fournis
	 * par l'utilisateur (id et mot de passe) existent bien et sont correctes. Si
	 * tel est le cas, la méthode retourne la valeur booléenne "true" et
	 * l'utilisateur entre alors dans l'application, sinon elle retourne "false", et
	 * alors l'utilisateur reste sur la fenêtre de connexion. Dans le cas où le
	 * fichier est manquant, la méthode fait appel à la méthode
	 * App.outSystemMessage(), ce qui provoque une erreur critique
	 * (ShowDialogMessage) et ferme l'application.
	 * 
	 * En plus de retourner une valeur booléenne, la méthode met à jour certains
	 * attributs de la classe fr.luzi.application.App. Les attributs modifiés sont
	 * relatifs à l'utilisateur (App.userName, App.userGrade, App.userSexe).
	 * 
	 * @param id : une chaîne de caractères représentant l'identifiant de
	 *           l'utilisateur.
	 * @param pw : un tableau de caractères représentant le mot-de-passe de
	 *           l'utilisateur.
	 * @return une valeur booléenne : "false", si l'une des deux informations de
	 *         connexion est incorrecte; "true", si les informations de connexion
	 *         sont correctes.
	 */
	public static boolean getUserFileCiphered(String id, char[] pw_) throws IOException {

		String pw = "";

		// --- Hachage du mot de passe.
		try {

			pw = Hashing.sha256Password(pw_);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();

		}

		boolean userValid = false;

		FileInputStream fileIn = new FileInputStream(App.PATH_DATA_USER_CONNEXION_FILE);

		if (FileTextUtilities.checkFileExist(App.PATH_DATA_USER_CONNEXION_FILE)) {

			try {

				// --- Déchiffrement du fichier "data-user-crypted.txt'.
				String[] K = MasterKey.getMasterKey();
				int C;
				int K_;
				StringBuilder M = new StringBuilder();
				int i = 0;

				while ((C = fileIn.read()) != -1) {

					K_ = Integer.parseInt(K[i % K.length], 16);
					M.append((char) (C ^ K_));
					i++;

				}

				// --- Effacement de la clé.
				K = new String[0];

				// --- Conversion des lignes du fichier en ArrayList<String>'.
				ArrayList<String> users = new ArrayList<>(Arrays.asList(M.toString().split("\n")));

				// --- Verification des informations de connexion.
				String[] user = {};
				int j = 0;

				while (j < users.size()) {

					user = users.get(j).split(";");
					user[1] = user[1].replaceAll("\n", "");

					if (user[0].equals(id) && user[1].equals(pw)) {

						userValid = true;
						App.userName = user[0];

						if (Integer.valueOf(user[2]) == 0) {
							App.userGrade = "Utilisateur";
						} else if (Integer.valueOf(user[2]) == 1) {
							App.userGrade = "Administrateur";
						} else if (Integer.valueOf(user[2]) == 2) {
							App.userGrade = "Super-Administrateur";
						}

						App.userSexe = Integer.valueOf(user[3]);
						break;
					}

					j++;
				}

			} catch (IOException e) {
				// --- Le fichier est introuvable, affichage d'un message d'erreur critique et
				// fermeture de l'application.

				System.out.println(DateGestion.getTimeForConsole() + "Le fichier des utilisateur est introuvable !");
				e.printStackTrace();
				App.outSystemMessage();
			}
		} else {
			// --- Le fichier est introuvable, affichage d'un message d'erreur critique et
			// fermeture de l'application.
			System.out.println(DateGestion.getTimeForConsole() + "Le fichier des utilisateur est introuvable !");
			App.outSystemMessage();
		}

		return userValid;
	}

	/**
	 * Méthode de test. À supprimer avant déploiement final.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "unused" })
	public static void encryptNewFile() throws IOException {

		try {

			// --- Récupération de la clé.
			String[] K = MasterKey.getMasterKey();

			// --- Déclarations diverses.
			FileInputStream fileIn = new FileInputStream("ressources/user/data-user.txt");
			FileOutputStream fileOut = new FileOutputStream("ressources/user/data-user-crypted.txt");

			int fileOutSize = fileIn.available();

			int M;
			int K_;
			byte[] C = new byte[fileOutSize];

			int i = 0;

			// --- Phase de chiffrement.
			while ((M = fileIn.read()) != -1) {

				K_ = Integer.parseInt(K[i % K.length], 16);
				C[i] = (byte) (M ^ K_);
				i++;

			}

			// --- Effacement de la clé.
			K = new String[0];

			// --- Écriture du fichier chiffré.
			fileOut.write(C, 0, fileOutSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Méthode de test. À supprimer avant déploiement final.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "unused" })
	public static void decryptNewFile() throws IOException {

		try {

			// --- Récupération de la clé.
			String[] K = MasterKey.getMasterKey();

			// --- Déclarations diverses.
			FileInputStream fileIn = new FileInputStream("ressources/user/data-user_crypted.txt");
			// FileOutputStream fileOut = new
			// FileOutputStream("ressources/user/data-user_decrypted.txt");

			int fileOutSize = fileIn.available();
			int C;
			int K_;
			byte[] M = new byte[fileOutSize];
			StringBuilder dataFile = new StringBuilder();
			int i = 0;

			// --- Phase de déchiffrement.
			while ((C = fileIn.read()) != -1) {

				K_ = Integer.parseInt(K[i % K.length], 16);
				M[i] = (byte) (C ^ K_);
				dataFile.append((char) (C ^ K_));

				i++;
			}

			// --- Effacement de la clé.
			K = new String[0];

			ArrayList<String> users = new ArrayList<>(Arrays.asList(dataFile.toString().split("\n")));

			// System.out.println("");
			// System.out.println(users.get(0));
			// fileOut.write(M, 0, fileOutSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Le main est ici conservé sous forme de commentaire. Le dé-commenté pour
	 * chiffrer/déchiffrer/tester.
	 */

	/*
	 * public static void main(String[] args) throws IOException {
	 * 
	 * encryptNewFile(); //decryptNewFile();
	 * 
	 * }
	 */
}
