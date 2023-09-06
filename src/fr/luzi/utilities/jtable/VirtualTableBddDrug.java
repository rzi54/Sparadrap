package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class VirtualTableBddDrug extends AbstractTableModel {

	private static final long serialVersionUID = 2446814982695969794L;
	private ArrayList<ElementsDrug> data = new ArrayList<>();
    public static String[] columnNames = {
    	"Id. Médicament", "Nom", "Catégorie", "Prix", "Date de mise en service", "Quantité"
    };

	public VirtualTableBddDrug(ArrayList<ElementsDrug> elements) {

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
    	ElementsDrug elements = data.get(row);

        if (column == 0) {
            return elements.getId();
        } else if (column == 1) {
            return elements.getName();
        } else if (column == 2) {
            return elements.getCategorie();
        } else if (column == 3) {
            return elements.getPrice()+" €";
        } else if (column == 4) {
            return elements.getDate();
        } else {
            return elements.getQuantity();
        }
    }
}