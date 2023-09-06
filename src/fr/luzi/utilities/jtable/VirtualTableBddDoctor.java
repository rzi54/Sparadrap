package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class VirtualTableBddDoctor extends AbstractTableModel {

	private static final long serialVersionUID = 2446814982695969794L;
	private ArrayList<ElementsDoctor> data = new ArrayList<>();
    public static String[] columnNames = {
    	"Id. Médecin", "Nom", "Prénom", "Adresse", "Code postal", "Ville", "Téléphone", "Email", "Num. RRPS", "Nb. Patients", "Spécialité"
    };

	public VirtualTableBddDoctor(ArrayList<ElementsDoctor> elements) {

        this.data = elements;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
    	ElementsDoctor elements = data.get(row);

        if (column == 0) {
            return elements.getIdNum();
        } else if (column == 1) {
            return elements.getLastName();
        } else if (column == 2) {
            return elements.getFirstName();
        } else if (column == 3) {
            return elements.getAddress();
        } else if (column == 4) {
            return elements.getPostalCode();
        } else if (column == 5) {
            return elements.getCity();
        } else if (column == 6) {
            return elements.getPhone();
        } else if (column == 7) {
            return elements.getMail();
        } else if (column == 8) {
            return elements.getNumRRPS();
        } else if (column == 9) {
            return elements.getNumPatients();
        }  else {
            return elements.getSpeciality();
        }
    }
}