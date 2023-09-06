package fr.luzi.utilities;

/**
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
*/

import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import fr.luzi.application.App;
import fr.luzi.utilities.jtable.TableGestion;

public class PopupMenuGestion {
	
	// --- Relatifs aux actions de click sur les arborescences.
	public static void jTreeSimpleRightClickAction(MouseEvent e, JTree tree, DefaultMutableTreeNode selectedNode) {

		JPopupMenu popupMenu = createPopupMenuJtree(selectedNode.getUserObject());
		popupMenu.show(tree, e.getX(), e.getY());
	}

	private static JPopupMenu createPopupMenuJtree(Object selectedObject) {

		JPopupMenu popupMenuJtree = new JPopupMenu();

		JMenuItem mnuOpen = new JMenuItem("Ouvrir" + App.JPOPUPMENU_SPACE);
		mnuOpen.addActionListener((e) -> jtreeMnuOpenListener());

		JMenuItem mnuModif = new JMenuItem("Editer");
		mnuModif.setIcon(new ImageIcon(App.PATH_ICONS_LOW + "edit-file.png"));
		mnuModif.addActionListener((e) -> jtreeMnuModifListener());

		JMenuItem mnuRename = new JMenuItem("Renommer" + App.JPOPUPMENU_SPACE);
		mnuOpen.addActionListener((e) -> jtreeMnuRenameListener());

		JMenuItem mnuDel = new JMenuItem("Supprimer");
		mnuDel.setIcon(new ImageIcon(App.PATH_ICONS_LOW + "delete.png"));
		mnuDel.addActionListener((e) -> jtreeMnuDelListener());

		JMenuItem mnuProperties = new JMenuItem("Propriété");
		mnuProperties.setIcon(new ImageIcon(App.PATH_ICONS_LOW + "show-property.png"));
		mnuProperties.addActionListener((e) -> jtreeMnuPropertyListener());

		popupMenuJtree.add(mnuOpen);
		popupMenuJtree.add(mnuModif);
		popupMenuJtree.addSeparator();
		popupMenuJtree.add(mnuRename);
		popupMenuJtree.add(mnuDel);
		popupMenuJtree.addSeparator();
		popupMenuJtree.add(mnuProperties);

		return popupMenuJtree;
	}

	private static void jtreeMnuOpenListener() {

		System.out.println(
				DateGestion.getTimeForConsole() + "Action d'ouverture de feuille JTree enclenchée (via menu pop-up).");

	}

	private static void jtreeMnuModifListener() {

		System.out.println(DateGestion.getTimeForConsole()
				+ "Action de modification de feuille JTree enclenchée (via menu pop-up).");

	}

	private static void jtreeMnuRenameListener() {

		System.out.println(
				DateGestion.getTimeForConsole() + "Action de renommage de feuille JTree enclenchée (via menu pop-up).");

	}

	private static void jtreeMnuDelListener() {

		System.out.println(DateGestion.getTimeForConsole()
				+ "Action de supression de feuille JTree enclenchée (via menu pop-up).");

	}

	private static void jtreeMnuPropertyListener() {

		System.out.println(DateGestion.getTimeForConsole()
				+ "Action de visualisation des propriétés de feuille JTree enclenchée (via menu pop-up).");

	}
	
	// --- Affichage du popup menu de la BDD pharmacologique.
	public static void bddPharmaRightClickAction(MouseEvent e, int row, JTable table, String iconDbb) {

		JPopupMenu popupMenu = createPopupBddPharma(table, row, iconDbb);
		popupMenu.show(table, e.getX(), e.getY());
	}
	
	private static JPopupMenu createPopupBddPharma(JTable table, int row, String iconDbb) {

		JPopupMenu BddPharma = new JPopupMenu();

		JMenuItem mnuOpen = new JMenuItem("Ouvrir" + App.JPOPUPMENU_SPACE);
		mnuOpen.addActionListener((e) -> bddMnuOpenListener(table, row, iconDbb));

		JMenuItem mnuModif = new JMenuItem("Editer");
		mnuModif.setIcon(new ImageIcon(App.PATH_ICONS_LOW + "edit-file.png"));
		mnuModif.addActionListener((e) -> bddMnuModifListener());

		JMenuItem mnuRename = new JMenuItem("Renommer" + App.JPOPUPMENU_SPACE);
		mnuOpen.addActionListener((e) -> bddMnuRenameListener());

		JMenuItem mnuDel = new JMenuItem("Supprimer");
		mnuDel.setIcon(new ImageIcon(App.PATH_ICONS_LOW + "delete.png"));
		mnuDel.addActionListener((e) -> bddMnuDelListener());

		JMenuItem mnuProperties = new JMenuItem("Propriété");
		mnuProperties.setIcon(new ImageIcon(App.PATH_ICONS_LOW + "show-property.png"));
		mnuProperties.addActionListener((e) -> bddMnuPropertyListener());

		BddPharma.add(mnuOpen);
		BddPharma.add(mnuModif);
		BddPharma.addSeparator();
		BddPharma.add(mnuRename);
		BddPharma.add(mnuDel);
		BddPharma.addSeparator();
		BddPharma.add(mnuProperties);

		return BddPharma;
	}
	
	private static void bddMnuOpenListener(JTable table, int row, String iconDbb) {

		System.out.println(
				DateGestion.getTimeForConsole() + "Action d'ouverture de ligne de table enclenchée (via menu pop-up).");
				TableGestion.drawTableBddPharma(table, row, iconDbb);
	}

	private static void bddMnuModifListener() {

		System.out.println(DateGestion.getTimeForConsole()
				+ "Action de modification de ligne de table enclenchée (via menu pop-up).");

	}

	private static void bddMnuRenameListener() {

		System.out.println(
				DateGestion.getTimeForConsole() + "Action de renommage de ligne de table enclenchée (via menu pop-up).");

	}

	private static void bddMnuDelListener() {

		System.out.println(DateGestion.getTimeForConsole()
				+ "Action de supression de ligne de table enclenchée (via menu pop-up).");

	}

	private static void bddMnuPropertyListener() {

		System.out.println(DateGestion.getTimeForConsole()
				+ "Action de visualisation des propriétés de ligne de table enclenchée (via menu pop-up).");

	}
	
	// --- Affichage du popup menu de la BDD historique des achats.
	public static void bddPurchaseHistoryRightClickAction(MouseEvent e, int row, JTable table, String iconDbb) {

		JPopupMenu popupMenu = createPopupBddPurchaseHistory(table, row, iconDbb);
		popupMenu.show(table, e.getX(), e.getY());
	}
	
	private static JPopupMenu createPopupBddPurchaseHistory(JTable table, int row, String iconDbb) {

		JPopupMenu BddPharma = new JPopupMenu();

		JMenuItem mnuOpen = new JMenuItem("Ouvrir" + App.JPOPUPMENU_SPACE);
		mnuOpen.addActionListener((e) -> purchaseHistoryMnuOpenListener(table, row, iconDbb));

		JMenuItem mnuModif = new JMenuItem("Editer");
		mnuModif.setIcon(new ImageIcon(App.PATH_ICONS_LOW + "edit-file.png"));
		mnuModif.addActionListener((e) -> purchaseHistoryMnuModifListener());

		JMenuItem mnuRename = new JMenuItem("Renommer" + App.JPOPUPMENU_SPACE);
		mnuOpen.addActionListener((e) -> purchaseHistoryMnuRenameListener());

		JMenuItem mnuDel = new JMenuItem("Supprimer");
		mnuDel.setIcon(new ImageIcon(App.PATH_ICONS_LOW + "delete.png"));
		mnuDel.addActionListener((e) -> purchaseHistoryMnuDelListener());

		JMenuItem mnuProperties = new JMenuItem("Propriété");
		mnuProperties.setIcon(new ImageIcon(App.PATH_ICONS_LOW + "show-property.png"));
		mnuProperties.addActionListener((e) -> purchaseHistoryMnuPropertyListener());

		BddPharma.add(mnuOpen);
		BddPharma.add(mnuModif);
		BddPharma.addSeparator();
		BddPharma.add(mnuRename);
		BddPharma.add(mnuDel);
		BddPharma.addSeparator();
		BddPharma.add(mnuProperties);

		return BddPharma;
	}
	
	private static void purchaseHistoryMnuOpenListener(JTable table, int row, String iconDbb) {

		System.out.println(
				DateGestion.getTimeForConsole() + "Action d'ouverture de ligne de table enclenchée (via menu pop-up).");
				TableGestion.drawTableBddPharma(table, row, iconDbb);
	}

	private static void purchaseHistoryMnuModifListener() {

		System.out.println(DateGestion.getTimeForConsole()
				+ "Action de modification de ligne de table enclenchée (via menu pop-up).");

	}

	private static void purchaseHistoryMnuRenameListener() {

		System.out.println(
				DateGestion.getTimeForConsole() + "Action de renommage de ligne de table enclenchée (via menu pop-up).");

	}

	private static void purchaseHistoryMnuDelListener() {

		System.out.println(DateGestion.getTimeForConsole()
				+ "Action de supression de ligne de table enclenchée (via menu pop-up).");

	}

	private static void purchaseHistoryMnuPropertyListener() {

		System.out.println(DateGestion.getTimeForConsole()
				+ "Action de visualisation des propriétés de ligne de table enclenchée (via menu pop-up).");

	}
	
		
}
