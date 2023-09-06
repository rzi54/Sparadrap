package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class VirtualTableBddMutual extends AbstractTableModel {

	private static final long serialVersionUID = 2446814982695969794L;
	private ArrayList<ElementsMutual> data = new ArrayList<>();
    public static String[] columnNames = {
    	"Id", "Nom", "Adresse", "Code postal", "Ville", "Téléphone", "Email", "Département", "Taux remboursement",
    };

	public VirtualTableBddMutual(ArrayList<ElementsMutual> elements) {
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
    	ElementsMutual elements = data.get(row);

        if (column == 0) {
        	 return elements.getId();
        } else if (column == 1) {
            return elements.getName();
        } else if (column == 2) {
            return elements.getAddress();
        } else if (column == 3) {
            return elements.getPostalCode();
        } else if (column == 4) {
            return elements.getCity();
        } else if (column == 5) {
            return elements.getPhone();
        } else if (column == 6) {
            return elements.getMail();
        } else if (column == 7) {
            return elements.getDepartement();
        }  else {
            return elements.getReimbursementRate();
        }
    }
}