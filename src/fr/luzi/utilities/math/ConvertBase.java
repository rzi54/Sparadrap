package fr.luzi.utilities.math;

/**
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ConvertBase {
	
	// --- Alphabets communs.
	private static String BINARY = "01";
	private static String HEXADECIMAL_LOW = "0123456789abcdef";
	private static String HEXADECIMAL_UPP = "0123456789ABCDEF";

	/**
	 * Cette méthode convertie un entier de type long en une chaîne de caractères.
	 * De la base 10 courante, la méthode retourne un nombre d'une base égale à la
	 * longueur de l'alphabet.
	 * 
	 * @param n      : Le nombre à convertir.
	 * @param alphab : Un alphabet qui contient les symboles de la base.
	 * @return Une chaîne de caractère.
	 */
	@SuppressWarnings("unused")
	private static String intToStr(long n, String alphab) {

		if (alphab.equals("bin")) {

			alphab = BINARY;

		} else if (alphab.equals("hex")) {

			alphab = HEXADECIMAL_LOW;

		} else if (alphab.equals("HEX")) {

			alphab = HEXADECIMAL_UPP;

		}

		ArrayList<Long> buffer = new ArrayList<Long>();
		String str = "";

		long b = alphab.length(); // doit être un BigInt (à voir)
		long b_ = alphab.length();

		while (n > 0) {

			if (n % b > 0) {

				buffer.add(n % b);

			} else {

				buffer.add((long) 0.00);

			}

			n = (long) Math.floor(n / b);
		}

		for (int i_ = buffer.size() - 1; i_ >= 0; i_--) {

			str += alphab.charAt((int) (Math.round((buffer.get(i_)))));
		}

		return str;
	}

	/**
	 * Cette méthode convertie une chaîne de caractères en un entier de type long.
	 * D'une base égale à la longueur de l'alphabet soumis en paramètre, la méthode
	 * retourne un nombre en base 10 courante.
	 * 
	 * @param str    : Le "nombre" à convertir.
	 * @param alphab : Un alphabet qui contient les symboles de la base.
	 * @return Un nombre de type long.
	 */
	private static long strToInt(String str, String alphab) {

		if (alphab.equals("bin")) {

			alphab = BINARY;

		} else if (alphab.equals("hex") || alphab.equals("HEX")) {

			if (Pattern.compile("^[0-9a-f]+$").matcher(str).matches()) {
				alphab = HEXADECIMAL_LOW;
			} else if (Pattern.compile("^[0-9A-F]+$").matcher(str).matches()) {
				alphab = HEXADECIMAL_UPP;
			} else {

			}

		}

		double out = 0;
		double base = alphab.length();
		double len = str.length();

		int j_ = 0;

		for (double i_ = len - 1; i_ > -1; i_--) {

			int a = posCharInAlphab(str, j_, alphab);
			int b = (int) Math.pow(base, i_);
			out += a * b;

			j_++;
		}

		return (long) out;
	}

	/**
	 * 
	 * @param str    : une chaine de caractère.
	 * @param pos    : la position du caractères de la chaine str.
	 * @param alphab : un alphabet.
	 * @return la position (un entier) du caractère dans l'alphabet.
	 */
	private static int posCharInAlphab(String str, int pos, String alphab) {

		return alphab.indexOf((char) str.charAt(pos));

	}

	/**
	 * Cette méthode convertie une chaîne de caractères de type hexadécimale en une
	 * couleur de type Color. Elle permet d'utiliser de convertir les couleur web
	 * type "fe6a03" comme il est largement utilisée dans les technologies relatives
	 * aux web.
	 * 
	 * @param hex : une chaîne de caractères longue de 6 caractères hexadécimaux.
	 * @return une couleur de type Color.
	 */
	public static Color hexToColor(String hex) {

		boolean hexIsValid = false;

		if (Pattern.compile("^[0-9a-f]+$").matcher(hex).matches()) {
			hexIsValid = true;
		} else if (Pattern.compile("^[0-9A-F]+$").matcher(hex).matches()) {
			hexIsValid = true;
		}

		if (hexIsValid && hex.length() == 6) {
			int[] c = new int[3];
			int j = 0;

			for (int i = 0; i < 6; i += 2) {

				c[j] = (int) strToInt(hex.charAt(i) + "" + hex.charAt(i + 1), "hex");
				j++;

			}

			Color color = new Color(c[0], c[1], c[2]);
			return color;
		} else {
			Color color = new Color(0, 0, 0);
			return color;
		}
	}

}
