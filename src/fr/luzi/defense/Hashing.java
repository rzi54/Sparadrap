package fr.luzi.defense;

/**
 * Classe défensive : Hachage de mots-de-passe et chaînes de caractères.
 * 
 * Cette classe est dédiée au codage défensif et permet le chiffrement
 * irreversible de données sensibles. La classe comprend les algorithmes de
 * hachage suivant :
 * 
 * 1) sha-256; 
 * 2) sha-512.
 * 
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
 * 
 */

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

	/**
	 * Fonction de hashage SHA256 (char[]).
	 * 
	 * @param un tableau de caractères [ex :
	 *           sha256Password(myJPasswordField.getPassword())]).
	 * @return une chaîne de caractères (longueur 64) au format hexadécimal.
	 * @throws NoSuchAlgorithmException
	 */
	public static String sha256Password(char[] pw) throws NoSuchAlgorithmException {

		MessageDigest msg = MessageDigest.getInstance("SHA-256");
		byte[] hash = msg.digest(charArray2String(pw).getBytes(StandardCharsets.UTF_8));

		StringBuilder s = new StringBuilder();

		for (byte b : hash) {
			s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}

		return s.toString();
	}

	/**
	 * Fonction de hashage SHA512 (char[]).
	 * 
	 * @param un tableau de caractères [ex :
	 *           sha512Password(myJPasswordField.getPassword())]).
	 * @return une chaîne de caractères (longueur 128) au format hexadécimal.
	 * @throws NoSuchAlgorithmException
	 */
	public static String sha512Password(char[] pw) throws NoSuchAlgorithmException {

		MessageDigest msg = MessageDigest.getInstance("SHA-512");
		byte[] hash = msg.digest(charArray2String(pw).getBytes(StandardCharsets.UTF_8));

		StringBuilder s = new StringBuilder();

		for (byte b : hash) {
			s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}

		return s.toString();
	}

	/**
	 * Fonction de hashage SHA256 (String).
	 * 
	 * @param une chaîne de caractères.
	 * @return une chaîne de caractères (longueur 64) au format hexadécimal.
	 * @throws NoSuchAlgorithmException
	 */
	public static String sha256(String str) throws NoSuchAlgorithmException {

		MessageDigest msg = MessageDigest.getInstance("SHA-256");
		byte[] hash = msg.digest(str.getBytes(StandardCharsets.UTF_8));

		StringBuilder s = new StringBuilder();

		for (byte b : hash) {
			s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}

		return s.toString();
	}

	/**
	 * Fonction de hashage SHA512 (String).
	 * 
	 * @param une chaîne de caractères.
	 * @return une chaîne de caractères (longueur 128) au format hexadécimal.
	 * @throws NoSuchAlgorithmException
	 */
	public static String sha512(String str) throws NoSuchAlgorithmException {

		MessageDigest msg = MessageDigest.getInstance("SHA-512");
		byte[] hash = msg.digest(str.getBytes(StandardCharsets.UTF_8));

		StringBuilder s = new StringBuilder();

		for (byte b : hash) {
			s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}

		return s.toString();
	}

	/**
	 * Convertisseur char[] -> String. Utilisé par les méthodes
	 * sha256Password(char[] pw) et sha512Password(char[] pw)
	 * 
	 * @param un tableau de caractères.
	 * @return une chaîne de caractères.
	 */
	public static String charArray2String(char[] array) {

		StringBuilder str_ = new StringBuilder();

		for (char chr : array) {
			str_.append(chr);
		}

		return str_.toString();
	}

}