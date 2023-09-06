package fr.luzi.utilities;

/**
 * Classe dédiée à la gestion du temps (horodate).
 *
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateGestion {

	/**
	 * @info Obtenir l'heure courante.
	 * @return L'heure courante.
	 */
	public static String getTime() {

		DateFormat monFormatTime = new SimpleDateFormat("HH:mm:ss");
		Date aTime = new Date();
		return monFormatTime.format(aTime);

	}

	/**
	 * @info Obtenir la date actuelle.
	 * @return La date courrante.
	 */
	public static String getDate() {

		DateFormat monFormatDate = new SimpleDateFormat("dd/MM/yyyy");
		Date aDate = new Date();
		return monFormatDate.format(aDate);

	}

	/**
	 * @info Timestamp actuel.
	 * @return Le timestamp actuel.
	 */
	public static Timestamp getTimeStamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}

	/**
	 * Heure et temps pour les bases de données.
	 * 
	 * @return une chaîne de caractère contenant l'heure et la date actuelle dans un
	 *         format spécifique.
	 */
	public static String tsToDateBdd(long ts) {

		DateFormat monFormatTime = new SimpleDateFormat("HH:mm:ss");
		Date aTime = new Date(ts);

		DateFormat monFormatDate = new SimpleDateFormat("dd/MM/yyyy");
		Date aDate = new Date(ts);

		return monFormatTime.format(aTime) + " le " + monFormatDate.format(aDate);
	}

	/**
	 * Heure et temps pour les log fichiers.
	 * 
	 * @return une chaîne de caractère contenant l'heure et la date actuelle dans un
	 *         format spécifique.
	 */
	public static String getDateTimeForErrorLog() {

		DateFormat monFormatTime = new SimpleDateFormat("HH:mm:ss");
		Date aTime = new Date();

		DateFormat monFormatDate = new SimpleDateFormat("dd/MM/yyyy");
		Date aDate = new Date();

		return "[" + monFormatTime.format(aTime) + "|" + monFormatDate.format(aDate) + "] > ";
	}

	/**
	 * Retourne le temps écoulé entre deux timestamp.
	 * 
	 * @param Un timestamp précédent (dans le temps) le second fourni en paramètres.
	 * @param Le timestamp courrant.
	 * @return Le temps écoulé entre les deux timestamps fournis en paramètres
	 *         d'entrée de la méthode.
	 */
	public static String getTimeElasped(Timestamp tsLast, Timestamp tsCurrent) {

		long last = tsLast.getTime();
		long current = tsCurrent.getTime();

		long totalSeconds = (current - last) / 1000;
		long hours = totalSeconds / 3600;
		long minutes = (totalSeconds % 3600) / 60;
		long seconds = totalSeconds % 60;

		String timeElapsed = String.format("%02d:%02d:%02d", hours, minutes, seconds);

		return timeElapsed;
	}

	/**
	 * @info Affichage de l'heure dans la console.
	 * @return Une chaine de caractère type : '[HH:mm:ss] > '
	 */
	public static String getTimeForConsole() {
		return "[" + getTime() + "] > ";
	}
}
