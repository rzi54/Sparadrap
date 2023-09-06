package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class VirtualTableBddPharma extends AbstractTableModel {

	private static final long serialVersionUID = -51510655741918973L;
	
   	private ArrayList<ElementsBddPharma> data = new ArrayList<>();
    public static String[] columnNames = {
            "Code CIS", "Code CIP7", "Présentation", "Statut", "Etat",
            "Date de commercialisation", "Code CIP13", "Agrément",
            "Taux de remboursement", "Prix (€)", "Indication de remboursement"
    };

    public VirtualTableBddPharma(ArrayList<ElementsBddPharma> elements) {
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
        ElementsBddPharma elements = data.get(row);

        if (column == 0) {
            return elements.getCodeCIS();
        } else if (column == 1) {
            return elements.getCodeCIP7();
        } else if (column == 2) {
            return elements.getPresentation();
        } else if (column == 3) {
            return elements.getStatus();
        } else if (column == 4) {
            return elements.getState();
        } else if (column == 5) {
            return elements.getDate();
        } else if (column == 6) {
            return elements.getCodeCIP13();
        } else if (column == 7) {
            return elements.getApproval();
        } else if (column == 8) {
            return elements.getRepaymentRate();
        } else if (column == 9) {
            return elements.getPrice();
        } else {
            return elements.getRepaymentIndocation();
        }
    }
}


