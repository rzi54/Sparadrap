package fr.luzi.utilities.jtable;

/**
* @language JAVA
* @author Randy LUZI
* @mail randy.luzi@gmail.com
* @license MIT
*/

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import fr.luzi.application.App;
import fr.luzi.utilities.DateGestion;
import fr.luzi.utilities.FileTextUtilities;
import fr.luzi.utilities.math.ConvertBase;

public class TableGestion {

	/**
	 * Recherche d'une entrée (str) dans la table fournie dans les paramètres de la méthode.
	 * @param table
	 * @param str
	 */
	public static void searchInTableBdd(JTable table, String str) {
		
		str = str.toLowerCase();
		
		for (int i = 0; i < table.getRowCount(); i++) {

			for (int j = 0; j < table.getColumnCount(); j++) {

				Object value = table.getValueAt(i, j);
				if (value != null && value.toString().toLowerCase().contains(str)) {
					table.setRowSelectionInterval(i, i);
					table.scrollRectToVisible(table.getCellRect(i, j, true));
					return;
				}
			}
		}
	}
	
	/**
	
	/**
	 * Recherche d'une entrée (str) dans la table fournie dans les paramètres de la méthode.
	 * @param table
	 * @param str
	 */
	public static void searchInTableFrameBdd(JTable table, String str, int type) {
		
		str = str.toLowerCase();
		
		for (int i = 0; i < table.getRowCount(); i++) {

			for (int j = 0; j < table.getColumnCount(); j++) {

				Object value = table.getValueAt(i, j);
				if (value != null && value.toString().toLowerCase().contains(str)) {
					table.setRowSelectionInterval(i, i);
					table.scrollRectToVisible(table.getCellRect(i, j, true));
					
					switch(type) {
					case 0 :
						App.informationsClientOrderSelected = FileTextUtilities.searchClient(table.getValueAt(i, 0).toString());
						break;
					case 1:
						App.informationsClientOrderSelected.set(1, FileTextUtilities.searchMutual(table.getValueAt(i, 0).toString()));
						break;
						
					case 2:
						App.informationsClientOrderSelected.set(2, FileTextUtilities.searchDoctor(table.getValueAt(i, 0).toString()));
						break;
					}
					return;
				}
			}
		}
	}
	
	/**
	 * Affiche dans une nouvelle JFrame les informations de la ligne issue de la bdd pharmacologique.
	 * @param table : La table dans laquelle se trouve la ligne séléctionnée.
	 * @param selectedRow : Un entier correspondant à la ligne.
	 * @param iconPath : Une chaine de caractères représentant le chemin de l'image servant d'icone pour la JFrame.
	 */
	public static void drawTableBddPharma(JTable table, int selectedRow, String iconPath) {
		
		// --- Ini variables.
		String[] columsValue = VirtualTableBddPharma.columnNames;
		
		// --- Création du Jframe.
        JFrame frame = new JFrame(table.getValueAt(selectedRow, 0).toString());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        
        for(int i=0; i<columsValue.length; i++) {
        	
        	panel.add(new JLabel("<html><b>"+columsValue[i] + "</b> : " + (String) table.getValueAt(selectedRow, i)+"</html>"));
        	
        }
        
      	// --- Tentative de mise en place de l'icone de fenêtre.
        try {
			frame.setIconImage(ImageIO.read(new File(iconPath)));
		} catch (IOException e) {
			System.out.println(DateGestion.getTimeForConsole()+"L'icone '"+iconPath+"' est introuvable !");
			e.printStackTrace();
		}
        
        // --- Finalisation.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        panel.setBackground(ConvertBase.hexToColor("ffffff"));
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		
	}
	
	/**
	 * Affiche dans une nouvelle JFrame les informations de la ligne issue de l'historique des achats.
	 * @param table : La table dans laquelle se trouve la ligne séléctionnée.
	 * @param selectedRow : Un entier correspondant à la ligne.
	 * @param iconPath : Une chaine de caractères représentant le chemin de l'image servant d'icone pour la JFrame.
	 */
	public static void drawTableBddPurchaseHistory(JTable table, int selectedRow, String iconPath) {
		
		// --- Ini variables.
		String[] columsValue = VirtualTableBddPurchaseHistory.columnNames;
		
		// --- Création du Jframe.
        JFrame frame = new JFrame(table.getValueAt(selectedRow, 0).toString());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        
        for(int i=0; i<columsValue.length; i++) {
        	
        	panel.add(new JLabel("<html><b>"+columsValue[i] + "</b> : " + (String) table.getValueAt(selectedRow, i)+"</html>"));
        	
        }
        
      	// --- Tentative de mise en place de l'icone de fenêtre.
        try {
			frame.setIconImage(ImageIO.read(new File(iconPath)));
		} catch (IOException e) {
			System.out.println(DateGestion.getTimeForConsole()+"L'icone '"+iconPath+"' est introuvable !");
			e.printStackTrace();
		}
        
        // --- Finalisation.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        panel.setBackground(ConvertBase.hexToColor("ffffff"));
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		
	}

	/**
	 * Affiche dans une nouvelle JFrame contenant la base de données client.
	 */
	public static JFrame frameBddClients = new JFrame("Base de données clients");
	public static void drawTableBddClients() {
		
		// --- Ini variables.
		String iconPath = App.PATH_ICONS_LOW+"user-male.png";

		// --- Création du Jframe.
		frameBddClients.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        
      	// --- Tentative de mise en place de l'icone de fenêtre.
        try {
        	frameBddClients.setIconImage(ImageIO.read(new File(iconPath)));
		} catch (IOException e) {
			System.out.println(DateGestion.getTimeForConsole()+"L'icone '"+iconPath+"' est introuvable !");
			e.printStackTrace();
		}
        
        // --- Finalisation.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        panel.setBackground(ConvertBase.hexToColor("ffffff"));
        frameBddClients.add(VirtualJTabbleBdd.jTabbedDatabaseClients(App.PATH_DATABASE_CLIENTS_FILE, true));
        frameBddClients.setSize(1280, 480);
        frameBddClients.setLocationRelativeTo(null);
        frameBddClients.setVisible(true);
		
	}

	/**
	 * Affiche dans une nouvelle JFrame contenant la base de données médecins.
	 */
	public static JFrame frameBddDoctors = new JFrame("Base de données médecins");
	public static void drawTableBddDoctors() {
		
		// --- Ini variables.
		String iconPath = App.PATH_ICONS_LOW+"doctor.png";

		// --- Création du Jframe.
		frameBddDoctors.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        
      	// --- Tentative de mise en place de l'icone de fenêtre.
        try {
        	frameBddDoctors.setIconImage(ImageIO.read(new File(iconPath)));
		} catch (IOException e) {
			System.out.println(DateGestion.getTimeForConsole()+"L'icone '"+iconPath+"' est introuvable !");
			e.printStackTrace();
		}
        
        // --- Finalisation.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        panel.setBackground(ConvertBase.hexToColor("ffffff"));
        frameBddDoctors.add(VirtualJTabbleBdd.jTabbedDatabaseDoctors(App.PATH_DATABASE_DOCTORS_FILE, true));
        frameBddDoctors.setSize(1280, 480);
        frameBddDoctors.setLocationRelativeTo(null);
        frameBddDoctors.setVisible(true);
		
	}

	/**
	 * Affiche dans une nouvelle JFrame contenant la base de données médecins.
	 */
	public static JFrame frameBddMutuals = new JFrame("Base de données mutuelles");
	public static void drawTableBddMutuals() {
		
		// --- Ini variables.
		String iconPath = App.PATH_ICONS_LOW+"purchase-order.png";

		// --- Création du Jframe.
		frameBddMutuals.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        
      	// --- Tentative de mise en place de l'icone de fenêtre.
        try {
        	frameBddMutuals.setIconImage(ImageIO.read(new File(iconPath)));
		} catch (IOException e) {
			System.out.println(DateGestion.getTimeForConsole()+"L'icone '"+iconPath+"' est introuvable !");
			e.printStackTrace();
		}
        
        // --- Finalisation.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        panel.setBackground(ConvertBase.hexToColor("ffffff"));
        frameBddMutuals.add(VirtualJTabbleBdd.jTabbedDatabaseMutuals(App.PATH_DATABASE_MUTUALS_FILE, true));
        frameBddMutuals.setSize(1280, 480);
        frameBddMutuals.setLocationRelativeTo(null);
        frameBddMutuals.setVisible(true);
		
	}

}
