package fr.luzi.defense;

/**
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import fr.luzi.application.App;
import fr.luzi.utilities.DateGestion;
import fr.luzi.utilities.FileTextUtilities;

public class PersonnalException extends Exception {

    private static final long serialVersionUID = 6506028788156742727L;

	public PersonnalException(String message) {
        super(message);
        FileTextUtilities.writeFile(App.PATH_DATA_LOG_ERRORS, DateGestion.getDateTimeForErrorLog() + message);
    }
}