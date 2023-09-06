package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class VirtualTableBddPurchaseHistory extends AbstractTableModel {

	private static final long serialVersionUID = 2446814982695969794L;
	private ArrayList<ElementsPurchaseHistory> data = new ArrayList<ElementsPurchaseHistory>();
    public static String[] columnNames = {
    	"Index achat", "Date", "Client", "Liste médicaments", "Prix total", "Type réglement", "Sur ordonnance", "Id. ordonnance", "Pharmacien"
    };

	public VirtualTableBddPurchaseHistory(ArrayList<ElementsPurchaseHistory> elements) {
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
    	ElementsPurchaseHistory elements = data.get(row);

        if (column == 0) {
            return elements.getBuyIndex();
        } else if (column == 1) {
            return elements.getDate();
        } else if (column == 2) {
            return elements.getIdClient();
        } else if (column == 3) {
            return elements.getDrusList();
        } else if (column == 4) {
            return elements.getPrice()+" €";
        } else if (column == 5) {
        	return elements.getPaymentType();
        } else if (column == 6) {	
        	return elements.getPrescription();
        } else if (column == 7) {	
        	return elements.getIdPrescription();
        }  else {
            return elements.getUserRecorder();
        }
    }
   
    public void updateData(ArrayList<ElementsPurchaseHistory> newData) {
        data = newData;
        fireTableDataChanged();
    }
}