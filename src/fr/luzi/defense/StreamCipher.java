package fr.luzi.defense;

/**
 * Classe défensive : Algorithme de chiffrement par flot.
 * 
 * Cette classe est dédiée au codage défensif et permet le chiffrement
 * reversible de données sensibles via deux algorithmes de chiffrement par flot
 * suivant chacun une loi de groupe lui étant propre :
 * 
 * 1) Loi XOR; 
 * 2) Loi Addition modulo n.
 * 
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
 * 
 */

public class StreamCipher {

	/**
	 * Convertisseur -> chaine de caractères vers tableau d'entier représentant la
	 * position dans la table ASCII d'un caractère donné.
	 * 
	 * @param : une chaîne de caractère.
	 * @return : tableau d'entier représentant la position dans la table ASCII de
	 *         tous les caractères de la chaîne.
	 */
	public static int[] string2Array(String str) {

		int[] buffer = new int[str.length()];

		for (int i = 0; i < str.length(); i++) {

			char c = str.charAt(i);
			buffer[i] = (int) c;
		}

		return buffer;
	}

	/**
	 * Algorithme de chiffrement par flot suivant une loi de groupe XOR.
	 * 
	 * Est totalement reversible, peut prendre en paramètre d'entrée aussi bien le
	 * message en clair que le chiffré. Retournera en conséquence le message en
	 * clair ou le message en chiffré.
	 * 
	 * @param M (ou C) : un tableau d'entier du message en clair (ou chiffré).
	 * @param K : un tableau d'entier (clé de chiffrement).
	 * @return C (ou M) : un tableau d'entier du message chiffré (ou en clair0).
	 */
	public static int[] encrypt(int[] M, int[] K) {

		int[] C = new int[M.length];

		for (int i = 0; i < M.length; i++) {

			C[i] = (int) M[i] ^ K[i % K.length];

		}

		return C;
	}

	/**
	 * Convertisseur -> tableau d'octets (byte) vers une chaîne de caractères.
	 * 
	 * @param array : un tableau d'octets.
	 * @return str_ : une chaîne de caractères.
	 */
	public static String byteArray2String(byte[] array) {

		String str_ = "";

		for (int i = 0; i < array.length; i++) {

			str_ += (char) array[i];// Character.toString(array[i]);

		}

		return str_;
	}

	/**
	 * Convertisseur -> tableau d'entiers (int) vers une chaîne de caractères.
	 * 
	 * @param array : un tableau d'entiers.
	 * @return str_ : une chaîne de caractères.
	 */
	public static String intArray2String(int[] array) {

		String str_ = "";

		for (int i = 0; i < array.length; i++) {

			str_ += (char) array[i];// Character.toString(array[i]);

		}

		return str_;
	}

	/**
	 * Transforme un tableau d'entiers en une chaîne de caractères tout en
	 * conservant sont aspect de tableau. La méthode "stringify simplement le
	 * tableau. Elle est utile pour afficher un tableau dans la console.
	 * 
	 * @param T : un tableau d'entiers.
	 * @return : le même tableau mais sous forme de chaîne de caractères.
	 */
	public static String stringifyArray(int[] T) {

		String buffer = "";
		buffer += "[ ";
		for (int j = 0; j < T.length; j++) {
			if (j < T.length - 1) {
				buffer += T[j] + ", ";
			} else {
				buffer += T[j] + " ";
			}
		}
		buffer += "]";

		return buffer;
	}

	/**
	 * Convertisseur -> tableau d'entiers (base 10) vers un tableau contenant ces
	 * même valeurs mais dans un format héxadécimal (base 16).
	 * 
	 * @param arr : un tableau d'entiers.
	 * @return : un tableau de valeur héxadécimales.
	 */
	public static String[] intArrToArrHex(int[] arr) {

		String[] hex = new String[arr.length];

		for (int i = 0; i < arr.length; i++) {

			String buffer = Integer.toHexString(arr[i]);

			if (buffer.length() < 2) {
				buffer = "0" + buffer;
			}
			hex[i] = buffer;
		}

		return hex;
	}

	/**
	 * Convertisseur -> tableau d'entiers (base 10) vers une chaîne de caractères
	 * héxadécimales (base 16).
	 * 
	 * @param arr : un tableau d'entiers.
	 * @return : une chaîne de caractères héxadécimales.
	 */
	public static String intArrToStringHex(int[] arr) {

		String hex = "";

		for (int i = 0; i < arr.length; i++) {

			String buffer = Integer.toHexString(arr[i]);

			if (buffer.length() < 2) {
				buffer = "0" + buffer;
			}
			hex += buffer;
		}

		return hex;
	}

	/**
	 * Convertisseur -> une chaîne de caractères héxadécimales (base 16). vers un
	 * tableau d'entiers (base 10).
	 * 
	 * @param hex : une chaîne de caractères héxadécimales.
	 * @return : un tableau d'entiers.
	 */
	public static int[] stringHexToIntArr(String hex) {

		int[] arr = new int[hex.length() / 2];
		int j = 0;

		for (int i = 0; i < hex.length(); i += 2) {

			String buffer = "#" + hex.charAt(i) + hex.charAt(i + 1);
			arr[j] = Integer.decode(buffer);
			j++;
		}

		return arr;

	}

}