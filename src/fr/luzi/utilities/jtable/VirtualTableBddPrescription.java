package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class VirtualTableBddPrescription extends AbstractTableModel {

    private static final long serialVersionUID = 5129015592522217313L;
	private ArrayList<ElementsPrescription> data = new ArrayList<>();
    public static String[] columnNames = {
    	"Id.", "Date", "Client", "Médecin", "Médicaments", "Spécialiste", "Pharmacien"
    };

	public VirtualTableBddPrescription(ArrayList<ElementsPrescription> elements) {
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
    	ElementsPrescription elements = data.get(row);

        if (column == 0) {
            return elements.getNumber();
        } else if (column == 1) {
            return elements.getDate();
        } else if (column == 2) {
            return elements.getClient();
        } else if (column == 3) {
            return elements.getDoctor();
        } else if (column == 4) {
            return elements.getDrugsList();
        } else if (column == 5) {
            return elements.getSpecialist();
        } else {
            return elements.getUserRecorder();
        }
    }
    
    public void updateData(ArrayList<ElementsPrescription> newData) {
        data = newData;
        fireTableDataChanged();
    }
}


