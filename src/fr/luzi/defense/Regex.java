package fr.luzi.defense;

/**
 * Classe défensive : Expressions régulières (Regex).
 * 		
 * Cette classe est dédiée au codage défensif et permet de vérifier la
 * régularité expressive des chaînes de caractères soumises à l'application. 
 * 
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
 */

import java.util.regex.Pattern;

public class Regex {

	// --- Attributions Expressions régulières.
	public static final String REGEX_PasswordTypeStrong = "^(?=.*\\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
	public static final String REGEX_PseudonymeType1 = "^([a-zA-Z0-9-_]+)$";
	public static final String REGEX_PseudonymeType2 = "^([a-zA-Z0-9_]+)([a-zA-Z0-9-_]+)?$";
	public static final String REGEX_firstAndLastName = "^[a-zA-ZÀ-ÖØ-öø-ÿ][a-zA-ZÀ-ÖØ-öø-ÿ-]{1,25}$";
	public static final String REGEX_enterpriseName = "^[A-Za-z0-9&'\"\\-()@#€$%^*+=!_,.;:<>?/\\\\ À-ÖØ-öø-ÿ ]+$";
	public static final String REGEX_email = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	public static final String REGEX_phoneFR = "^(0|\\+33|0033)[1-9][0-9]{8}$";
	public static final String REGEX_phoneInternational = "^\\+(?:[0-9] ?){6,14}[0-9]$";
	public static final String REGEX_addressNumAndStreet = "\\d{1,3} [\\p{L} .'-]+";
	public static final String REGEX_postalCodeFr = "\\d{5}";
	public static final String REGEX_cityFr = "^[A-Za-zÀ-ÖØ-öø-ÿ -]+$";;
	public static final String REGEX_addressEU_FR = ".*\\d{1,3}.*[\\n ,]\\d{4,5}([- ]\\d{4,5})? [^\\n]+([\\n ,](France|Suisse|Allemagne|Italie|Germany|Deutschland|Italia))?";
	public static final String REGEX_numberRate = "^\\d{1,2}(\\.\\d{1,2})?$";

	// --- "Faux Regex" seulement employés pour les besoins de l'application
	public static final String REGEX_FalseId = "\\d{7}";
	public static final String REGEX_FalseSecuNumber = "\\d{15}";
	public static final String REGEX_FalseRRPSNumber = "\\d{15}";
	public static final String REGEX_ProffesionsMedicals = "\\b(pédiatre|généraliste|cardiologue|ophtalmologiste|dentiste|chirurgien|anesthésiste|dermatologue|endocrinologue|gynécologue|neurologue|oncologue|orthopédiste|radiologue|urologue|psychiatre|psychologue|pharmacien|infirmier|sage-femme|radiologiste|chirurgien plastique|chirurgien cardiovasculaire|chirurgien thoracique|chirurgien orthopédique|gastroentérologue|proctologue|pneumologue|rhumatologue|allergologue|néphrologue|gériatre|obstétricien|toxicologue|infectiologue|généticien)\\b";

	/**
	 * Expression régulière pour Mot-de-passe fort.
	 * 
	 * Valide des mots-de-passe contenant :
	 * 
	 * Au moins un chiffre. Au moins un caractère spécial parmi !@#$%^&*. Au moins
	 * une lettre minuscule. Au moins une lettre majuscule.
	 * 
	 * Longueur totale d''un minimum de 8 caractères.
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidPasswordTypeStrong(String password) {
		Pattern pattern = Pattern.compile(REGEX_PasswordTypeStrong);
		return pattern.matcher(password).matches();
	}

	/**
	 * Expression régulière pour Pseudonyme type 1.
	 * 
	 * Valide les chaines de caractères contenant uniquement des lettres (majuscules
	 * et minuscules), des chiffres, des tirets (-) et des tirets bas (_)
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidTypePseudonyme1(String pseudo) {
		Pattern pattern = Pattern.compile(REGEX_PseudonymeType1);
		return pattern.matcher(pseudo).matches();
	}

	/**
	 * Expression régulière pour Pseudonyme type 2
	 * 
	 * Valide une chaîne de caractères satisfaisant l'une des deux conditions
	 * suivantes :
	 *
	 * 1) La chaîne doit contenir un ou plusieurs caractères qui sont des lettres
	 * (majuscules et minuscules), des chiffres ou des tirets bas (_). Cette partie
	 * est capturée par le premier groupe de capture ([a-zA-Z0-9_]+).
	 * 
	 * 2) La chaîne peut également contenir un autre groupe de caractères qui est
	 * une combinaison de lettres, chiffres, tirets (-) et tirets bas (_). Cette
	 * partie est capturée par le deuxième groupe de capture ([a-zA-Z0-9-_]+) et est
	 * facultative (?).
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidTypePseudonyme2(String pseudo) {
		Pattern pattern = Pattern.compile(REGEX_PseudonymeType2);
		return pattern.matcher(pseudo).matches();
	}

	/**
	 * Expression régulière pour Nom et Nom de famille.
	 * 
	 * Valide une chaîne de caractères satisfaisant les trois conditions suivantes :
	 *
	 * 1) La chaîne doit commencer par une lettre (majuscule ou minuscule).
	 * 
	 * 2) La chaîne peut ensuite contenir des lettres (majuscules ou minuscules) ou
	 * des tirets (-) après la première lettre.
	 * 
	 * 3) La longueur totale de la chaîne doit être comprise entre 2 et 26
	 * caractères.
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidTypeName(String name) {
		Pattern pattern = Pattern.compile(REGEX_firstAndLastName);
		return pattern.matcher(name).matches();
	}

	/**
	 * 
	 * /** Expression régulière pour Nom d'entreprise.
	 * 
	 * Ce regex permet de valider une chaîne de caractères comportant :
	 * 
	 * Lettres majuscules et minuscules (A-Z, a-z) avec des accents français.
	 * Chiffres (0-9). & (et), ' (apostrophe), " (guillemets doubles), - (tiret), ()
	 * (parenthèses), @, #, €, $, %, ^, *, +, =, !, _, comma (, point-virgule ;,
	 * deux-points :, inférieur < et supérieur >, point d'interrogation ?, barre
	 * oblique /, barre oblique inversée \ et espace.
	 *
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidTypeNameEnterprise(String name) {
		Pattern pattern = Pattern.compile(REGEX_enterpriseName);
		return pattern.matcher(name).matches();
	}

	/**
	 * Expression régulière pour Email.
	 * 
	 * Valide une chaîne de caractères satisfaisant les conditions suivantes :
	 *
	 * 1) La chaîne doit commencer par un groupe de caractères composé de lettres
	 * (majuscules et minuscules), de chiffres, de tirets bas (_), et des caractères
	 * spéciaux +, &, *, -.
	 * 
	 * 2) Après le groupe initial, il doit y avoir un signe @.
	 * 
	 * 3) Ensuite, la chaîne doit inclure un nom de domaine (domaine de niveau
	 * supérieur, domaine de niveau inférieur, etc.) qui est composé de lettres
	 * (majuscules et minuscules), de chiffres et de tirets.
	 * 
	 * 4) Le nom de domaine doit être suivi par un point.
	 * 
	 * 5) Le processus de 3 et 4 peut se répéter pour des sous-domaines.
	 * 
	 * 6) En dernier lieu, le nom de domaine doit se terminer par une extension de
	 * domaine de 2 à 7 caractères (lettres majuscules et minuscules).
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(REGEX_email);
		return pattern.matcher(email).matches();
	}

	/**
	 * Expression régulière pour Numéros de téléphone français.
	 * 
	 * Valide une chaîne de caractères satisfaisant les trois conditions suivantes :
	 *
	 * 1) Le numéro de téléphone doit commencer par l'indicatif du pays France, qui
	 * peut être soit 0, +33 ou 0033.
	 * 
	 * 2) Après l'indicatif, il doit y avoir un chiffre de 1 à 9, indiquant le
	 * premier chiffre du numéro de téléphone.
	 * 
	 * 3) Les neuf chiffres suivants doivent être des chiffres de 0 à 9.
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidPhoneNumberFr(String phoneNumber) {
		Pattern pattern = Pattern.compile(REGEX_phoneFR);
		return pattern.matcher(phoneNumber).matches();
	}

	/**
	 * Expression régulière pour Numéros de téléphone internationaux.
	 * 
	 * Valide une chaîne de caractères satisfaisant les trois conditions suivantes :
	 *
	 * 1) La chaîne doit commencer par le caractère +.
	 * 
	 * 2) Ensuite, il peut y avoir de 6 à 14 occurrences de chiffres (0-9) suivis
	 * éventuellement d'un espace facultatif.
	 * 
	 * 3) La chaîne doit se terminer par un chiffre (0-9).
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidPhoneNumber(String phoneNumber) {
		Pattern pattern = Pattern.compile(REGEX_phoneInternational);
		return pattern.matcher(phoneNumber).matches();
	}

	/**
	 * Expression régulière pour Adresse française (numéro et rue).
	 * 
	 * Explication détaillée des éléments du regex :
	 *
	 * \\d{1,3} : Correspond à un numéro composé de 1 à 3 chiffres. [\\p{L} .'-]+ :
	 * Correspond à la rue, qui peut contenir des lettres (y compris les lettres
	 * accentuées), des espaces, des points, des apostrophes et des tirets. Le +
	 * signifie qu'il doit y avoir au moins un caractère de rue après le numéro.
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidAddressNumAndStreet(String address) {
		Pattern pattern = Pattern.compile(REGEX_addressEU_FR);
		return pattern.matcher(address).matches();
	}

	/**
	 * Expression régulière pour codes postaux français.
	 * 
	 * Explication détaillée des éléments du regex :
	 *
	 * Ce regex correspondra à exactement cinq chiffres consécutifs dans une chaîne
	 * de texte, sans prendre en compte les limites de mot ou les espaces avant ou
	 * après les chiffres. Cela capturera uniquement les chiffres du code postal
	 * français.
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidPostalCode(String address) {
		Pattern pattern = Pattern.compile(REGEX_postalCodeFr);
		return pattern.matcher(address).matches();
	}

	/**
	 * 
	 * /** Expression régulière pour villes françaises.
	 * 
	 * Explication détaillée des éléments du regex :
	 *
	 * Ce regex correspondra aux noms de villes françaises qui peuvent contenir des
	 * lettres majuscules et minuscules, des espaces, des traits d'union et des
	 * caractères accentués couramment utilisés en français. Il s'assure que la
	 * ville commence et se termine par une lettre et autorise les caractères
	 * spécifiés entre.
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidCityFr(String address) {
		Pattern pattern = Pattern.compile(REGEX_cityFr);
		return pattern.matcher(address).matches();
	}

	/**
	 * Expression régulière pour Adresse d'Europe de l'ouest.
	 * 
	 * Explication détaillée des éléments du regex :
	 *
	 * 1) .* : Matche n'importe quel caractère (.) zéro ou plus de fois (*), ce qui
	 * signifie que la chaîne peut commencer par n'importe quoi.
	 * 
	 * 2) \\d{1,3} : Matche un nombre de 1 à 3 chiffres. Cela pourrait correspondre
	 * à un code d'indicatif régional ou à quelque chose de similaire.
	 * 
	 * 3) .* : Matche n'importe quel caractère zéro ou plus de fois.
	 * 
	 * 4) [\\n ,] : Matche un caractère de nouvelle ligne (\\n) ou un espace ou une
	 * virgule (,).
	 * 
	 * 5) \\d{4,5} : Matche un nombre de 4 à 5 chiffres.
	 * 
	 * 6) ([- ]\\d{4,5})? : Matche éventuellement un espace ou un tiret ([- ]) suivi
	 * de 4 à 5 chiffres.
	 * 
	 * 7) [\\n ,] : Matche un caractère de nouvelle ligne (\\n) ou un espace ou une
	 * virgule (,).
	 * 
	 * 8) [^\\n]+ : Matche un ou plusieurs caractères qui ne sont pas des caractères
	 * de nouvelle ligne (\\n).
	 * 
	 * 9) ([\\n ,](France|Suisse|Allemagne|Italie|Germany|Deutschland|Italia))? :
	 * Matche éventuellement un caractère de nouvelle ligne (\\n) ou un espace ou
	 * une virgule (,), suivi de l'un des mots spécifiés (France, Suisse, Allemagne,
	 * Italie, Germany, Deutschland, Italia).
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidAddressEuFR(String address) {
		Pattern pattern = Pattern.compile(REGEX_addressEU_FR);
		return pattern.matcher(address).matches();
	}

	/**
	 * Expression régulière pour taux avec deux chiffres après la virgule.
	 * 
	 * Explication détaillée des éléments du regex :
	 *
	 * Il peut y avoir un ou deux chiffres avant la virgule (ou le point). S'il y a
	 * une virgule (ou un point), il doit y avoir un ou deux chiffres après. Le
	 * point décimal est facultatif, ce qui signifie que les nombres entiers et
	 * décimaux sont valides. Exemples de taux valides :
	 * 
	 * 5 - 10.5 - 0.25 
	 * 
	 * Exemples de taux invalides :
	 * 
	 * .5 (doit avoir au moins un chiffre avant la virgule) 12.345 (seuls deux
	 * chiffres après la virgule sont autorisés)
	 * 
	 * @param Une chaîne de caractères.
	 * @return Une valeur booléene.
	 */
	public static boolean isValidnumberRate2Digits(String address) {
		Pattern pattern = Pattern.compile(REGEX_numberRate);
		return pattern.matcher(address).matches();
	}

}
