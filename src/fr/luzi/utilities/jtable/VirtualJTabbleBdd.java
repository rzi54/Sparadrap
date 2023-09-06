package fr.luzi.utilities.jtable;

/**
 * Cette classe est dédiée à l'affichage de tables virtualisées, chargées par de
 * trés grandes quantités de données.
 * 
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import fr.luzi.application.App;
import fr.luzi.utilities.DateGestion;
import fr.luzi.utilities.FileTextUtilities;
import fr.luzi.utilities.PopupMenuGestion;
import fr.luzi.utilities.StringToElementsConverter;
import fr.luzi.utilities.graphics.DetailsPanel;
import fr.luzi.utilities.graphics.InputOrder;
import fr.luzi.utilities.math.ConvertBase;

public class VirtualJTabbleBdd {
	
	// --- Relatifs à l'historique des achats.
		public static VirtualTableBddPurchaseHistory dbbTablePurchaseHistoryModel;
		public static JTable dbbTablePurchaseHistory;
		
	// --- Relatifs à l'historique des ordonnances.
	public static VirtualTableBddPrescription dbbTablePrescriptionModel;
	public static JTable dbbTablePrescription;
		
		
	/**
	 * Cette méthode créer une table virutalisée chargée par une trés grande
	 * quantité de données. La méthode est ici propre à la base de donnée CIS_CIP
	 * téléchargeable à partir de cette adresse web :
	 * https://base-donnees-publique.medicaments.gouv.fr/telechargement.php
	 * 
	 * Cette table n'intervient en rien dans le fonctionnement de l'application, si
	 * ce n'est qu'elle "comble les trous" et apporte un peu plus de
	 * profesionnalisme à l'apparence de l'interface utilisateur.
	 * 
	 * 
	 * @return un JTabbedPane
	 */
	public static JTabbedPane createBddPharma_CIS_CIP() {

		// --- Vérification de l'existence de la base de données pharmacologique
		// (fichier).
		App.verifRessources(App.PATH_DATABASE_CIS_CIP_FILE, true);

		// --- Factorisation des élements de toolbar relatifs à la table virtuel.
		ImageIcon searchButtonToolBarIcon = new ImageIcon(App.PATH_ICONS_LOW + "find.png");
		String searchToolTipText = "Rechercher...";

		ImageIcon iconBddPharma = new ImageIcon(App.PATH_ICONS_LOW + "database.png");
		String imageIconBddPharma = App.PATH_ICONS_LOW + "database.png";

		// --- Création de la table virtualisée.
		JPanel contentBddPharma = new JPanel(new GridLayout(1, 1));

		ArrayList<String> data = FileTextUtilities.readFileText(App.PATH_DATABASE_CIS_CIP_FILE, "bddPharma");

		ArrayList<ElementsBddPharma> elements = new ArrayList<>();
		for (String e : data) {
			ElementsBddPharma convertedElement = StringToElementsConverter.stringToElementsBddPharma(e);
			elements.add(convertedElement);
		}

		VirtualTableBddPharma dbbTable = new VirtualTableBddPharma(elements);
		JTable table = new JTable(dbbTable);
		table.setAutoCreateRowSorter(true);

		// --- Ajustement de la largeur des colonnes
		/*
		 * for (int column = 0; column < table.getColumnCount(); column++) { TableColumn
		 * tableColumn = table.getColumnModel().getColumn(column); int preferredWidth =
		 * 0;
		 * 
		 * for (int row = 0; row < table.getRowCount(); row++) { TableCellRenderer
		 * cellRenderer = table.getCellRenderer(row, column); Component c =
		 * table.prepareRenderer(cellRenderer, row, column); preferredWidth =
		 * Math.max(preferredWidth, c.getPreferredSize().width); }
		 * 
		 * tableColumn.setPreferredWidth((int)Math.floor((double)preferredWidth*0.6)); }
		 */

		// --- Action sur la table via click de la souris.
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// --- Action via click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							System.out.println(DateGestion.getTimeForConsole() + " JTable (action via click gauche)");
						}
					}
				}
				// --- Action via double click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							TableGestion.drawTableBddPharma(table, selectedRow, imageIconBddPharma);
						}
					}
				}
				// --- Action via click droit de la souris.
				if (SwingUtilities.isRightMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							System.out.println(DateGestion.getTimeForConsole() + " JTable (action via click droit)");
							int row = table.rowAtPoint(e.getPoint()); // Récupérer la ligne du clic
							if (row >= 0 && row < table.getRowCount()) {
								table.setRowSelectionInterval(row, row); // Sélectionner la ligne du clic
								PopupMenuGestion.bddPharmaRightClickAction(e, selectedRow, table, imageIconBddPharma);
							}
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		contentBddPharma.add(scrollPane);

		// --- Création et ajout de la barre d'outils.
		JTextField searchField = new JTextField(32);
		searchField.addActionListener(e -> TableGestion.searchInTableBdd(table, searchField.getText()));

		JButton searchButton = new JButton(searchButtonToolBarIcon);
		searchButton.setToolTipText(searchToolTipText);
		searchButton.addActionListener(e -> TableGestion.searchInTableBdd(table, searchField.getText()));

		Component horizontalSpaceToolBar = Box.createHorizontalStrut(8);
		JToolBar toolBar = new JToolBar();
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchField);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchButton);
		toolBar.setFloatable(false);
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.setBorder(new EmptyBorder(0, 12, 0, 0));

		// --- Création de la barre de status.

		// --- Barre de statuts container.
		JPanel statusBar = new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		statusBar.setMinimumSize(new Dimension(0, 32));
		statusBar.setMaximumSize(new Dimension(0, 32));
		statusBar.setPreferredSize(new Dimension(0, 32));

		// --- Barre de statut de gauche.
		JPanel statusBarLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 32, 10));

		String iconNbElement = "apps-tab.png";

		App.lblUserNameStatut = new JLabel(
				"<html>Nb. élements : " + App.HTML_SPAN_STYLE_TEXT_BOLD + App.bdd1NbOfElement + "</span></html>",
				new ImageIcon(App.PATH_ICONS_LOW + iconNbElement), JLabel.LEFT);
		statusBarLeft.add(App.lblUserNameStatut);

		// --- Barre de statut de droite.
		JPanel statusBarRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 32, 10));

		App.bddPathFile = new JLabel("<html> Ficher BDD : " + App.PATH_DATABASE_CIS_CIP_FILE
				+ App.HTML_SPAN_STYLE_TEXT_BOLD + "</span></html>", new ImageIcon(App.PATH_ICONS_LOW + "database.png"),
				JLabel.LEFT);
		statusBarRight.add(App.bddPathFile);

		// --- Ajout des deux sous-barre de statuts dans la barre de statuts.
		statusBar.add(statusBarLeft);
		statusBarLeft.add(Box.createHorizontalGlue());
		statusBar.add(statusBarRight);
		// statusBar.add(btnDecryptMessage);

		// --- Mis en place d'une bordure du haut.
		Border topBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, ConvertBase.hexToColor("939393"));
		statusBar.setBorder(topBorder);

		// --- Assemblage de la barre d'outils et de la table.
		JPanel contentGlobal = new JPanel();
		contentGlobal.add(toolBar, BorderLayout.NORTH);
		contentGlobal.setLayout(new BoxLayout(contentGlobal, BoxLayout.X_AXIS));

		JSplitPane bddJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, contentGlobal, contentBddPharma);
		bddJSplitPane.setResizeWeight(0.0);
		bddJSplitPane.setDividerSize(0);

		JSplitPane bddJSplitPaneStatuBarAndBddPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, bddJSplitPane,
				statusBar);
		bddJSplitPaneStatuBarAndBddPanel.setResizeWeight(0.0);
		bddJSplitPaneStatuBarAndBddPanel.setDividerSize(0);

		JTabbedPane tabbed = new JTabbedPane();
		tabbed.addTab("<html><b>Base de données pharmacologiques </b></html>", iconBddPharma, bddJSplitPane);

		return tabbed;

	}

	/**
	 * Cette méthode créée une table virtualisée contenant l'ensemble des achats
	 * passés ainsi que les divers composants graphiques qui l'accompagnent.
	 * 
	 * @param filePathDataBase fichier de la bdd des achats passés.
	 * @return un JTabbedPane contenant une table virtualisée de la base de données.
	 */
	public static JPanel jTabbedDatabasePurchaseHistory(String filePathDataBase) {

		// --- Vérification de l'existence de la base de données (fichier).
		App.verifRessources(filePathDataBase, true);

		// --- Paramètrage spécifique.
		AtomicReference<String> imageIconBdd = new AtomicReference<>(App.PATH_ICONS_LOW + "");
		imageIconBdd.set(App.PATH_ICONS_LOW + "order-history.png");

		// --- Création de la table virtualisée.
		ArrayList<String> data = FileTextUtilities.readFileText(filePathDataBase, "PurshaseHistory");

		ArrayList<ElementsPurchaseHistory> elements = new ArrayList<>();
		for (String e : data) {
			ElementsPurchaseHistory convertedElement = StringToElementsConverter.stringToElementsPurchaseHistory(e);
			elements.add(convertedElement);
		}
		
		dbbTablePurchaseHistoryModel = new VirtualTableBddPurchaseHistory(elements);
		dbbTablePurchaseHistory = new JTable(dbbTablePurchaseHistoryModel);
		dbbTablePurchaseHistory.setAutoCreateRowSorter(true);

		// --- Ajustement de la largeur des colonnes
		for (int column = 0; column < dbbTablePurchaseHistory.getColumnCount(); column++) {
			TableColumn tableColumn = dbbTablePurchaseHistory.getColumnModel().getColumn(column);
			int preferredWidth = 0;

			for (int row = 0; row < dbbTablePurchaseHistory.getRowCount(); row++) {
				TableCellRenderer cellRenderer = dbbTablePurchaseHistory.getCellRenderer(row, column);
				Component c = dbbTablePurchaseHistory.prepareRenderer(cellRenderer, row, column);
				preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
			}

			tableColumn.setPreferredWidth((int) Math.floor((double) preferredWidth * 0.6));
		}

		// --- Action sur la dbbTablePurchaseHistory via click de la souris.
		dbbTablePurchaseHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// --- Action via click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = dbbTablePurchaseHistory.getSelectedRow();
						if (selectedRow != -1) {

							System.out.println(DateGestion.getTimeForConsole() + "Achat selectionné.");

							DetailsPanel.indexOrderValue.setText(dbbTablePurchaseHistory.getValueAt(selectedRow, 0).toString());
							DetailsPanel.dateOrderValue.setText(dbbTablePurchaseHistory.getValueAt(selectedRow, 1).toString());
							DetailsPanel.clientOrderValue.setText(dbbTablePurchaseHistory.getValueAt(selectedRow, 2).toString());
							DetailsPanel.priceOrderValue.setText(dbbTablePurchaseHistory.getValueAt(selectedRow, 4).toString());

							if (dbbTablePurchaseHistory.getValueAt(selectedRow, 6).toString().equals("true")) {
								DetailsPanel.typeOrderValue.setText("Sur ordonnance");
							} else {
								DetailsPanel.typeOrderValue.setText("Achat direct");
							}

							DetailsPanel.indexOrderValue.revalidate();
							DetailsPanel.dateOrderValue.revalidate();
							DetailsPanel.clientOrderValue.revalidate();
							DetailsPanel.priceOrderValue.revalidate();
							DetailsPanel.typeOrderValue.revalidate();

							String[] drugs_ = dbbTablePurchaseHistory.getValueAt(selectedRow, 3).toString().replace("[", "{\"")
									.replace("]", "\"}").replace(",", "\",\"").replace(" ", "").replace("{", "")
									.replace("}", "").split(",");

							DefaultTableModel tableMode = new DefaultTableModel();
							tableMode.addColumn("Dénomination");

							for (String value : drugs_) {
								Object[] row = { value };

								tableMode.addRow(row);
							}

							JTable dbbTablePurchaseHistory = new JTable(tableMode);
							JScrollPane scrollPane = new JScrollPane(dbbTablePurchaseHistory);

							DetailsPanel.panelDrugsOrder.removeAll();
							DetailsPanel.panelDrugsOrder.add(scrollPane, BorderLayout.CENTER);

						}
					}
				}
				// --- Action via double click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int selectedRow = dbbTablePurchaseHistory.getSelectedRow();
						if (selectedRow != -1) {

						}
					}
				}
				// --- Action via click droit de la souris.
				if (SwingUtilities.isRightMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = dbbTablePurchaseHistory.getSelectedRow();
						if (selectedRow != -1) {
							System.out.println(DateGestion.getTimeForConsole() + " JTable (action via click droit)");
							int row = dbbTablePurchaseHistory.rowAtPoint(e.getPoint());
							if (row >= 0 && row < dbbTablePurchaseHistory.getRowCount()) {
								dbbTablePurchaseHistory.setRowSelectionInterval(row, row);
								
							}
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(dbbTablePurchaseHistory, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// --- Création de la barre d'outils.
		JLabel searchText = new JLabel("<html><b> Rechercher </b></html>");

		JTextField searchField = new JTextField(32);
		searchField.addActionListener(e -> TableGestion.searchInTableBdd(dbbTablePurchaseHistory, searchField.getText()));

		JButton searchButton = new JButton(new ImageIcon(App.PATH_ICONS_LOW + "find.png"));
		searchButton.setToolTipText("Rechercher...");
		searchButton.addActionListener(e -> TableGestion.searchInTableBdd(dbbTablePurchaseHistory, searchField.getText()));

		Component horizontalSpaceToolBar = Box.createHorizontalStrut(8);
		JToolBar toolBar = new JToolBar();
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchText);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchField);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchButton);
		toolBar.setFloatable(false);
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.setBorder(new EmptyBorder(0, 12, 0, 0));

		// --- Création de la barre de status.
		JPanel statusBar = statusBarOrderButtonCreate();

		// --- Assemblage des composants graphiques.
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(toolBar, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(statusBar, BorderLayout.SOUTH);

		return mainPanel;

	}

	/**
	 * Cette méthode créée une table virtualisée contenant l'ensemble des
	 * préscriptions passés ainsi que les divers composants graphiques qui
	 * l'accompagnent.
	 * 
	 * @param filePathDataBase fichier de la bdd des achats passés.
	 * @return un JTabbedPane contenant une table virtualisée de la base de données.
	 */
	public static JPanel jTabbedDatabasePrescriptionHistory(String filePathDataBase) {

		// --- Vérification de l'existence de la base de données (fichier).
		App.verifRessources(filePathDataBase, true);

		// --- Paramètrage spécifique.
		AtomicReference<String> imageIconBdd = new AtomicReference<>(App.PATH_ICONS_LOW + "");
		imageIconBdd.set(App.PATH_ICONS_LOW + "order-history.png");

		// --- Création de la table virtualisée.
		ArrayList<String> data = FileTextUtilities.readFileText(filePathDataBase, "Prescritptions");

		ArrayList<ElementsPrescription> elements = new ArrayList<>();
		for (String e : data) {
			ElementsPrescription convertedElement = StringToElementsConverter.stringToElementsPrescription(e);
			elements.add(convertedElement);
		}
		dbbTablePrescriptionModel = new VirtualTableBddPrescription(elements);
		dbbTablePrescription = new JTable(dbbTablePrescriptionModel);
		dbbTablePrescription.setAutoCreateRowSorter(true);

		// --- Ajustement de la largeur des colonnes
		for (int column = 0; column < dbbTablePrescription.getColumnCount(); column++) {
			TableColumn tableColumn = dbbTablePrescription.getColumnModel().getColumn(column);
			int preferredWidth = 0;

			for (int row = 0; row < dbbTablePrescription.getRowCount(); row++) {
				TableCellRenderer cellRenderer = dbbTablePrescription.getCellRenderer(row, column);
				Component c = dbbTablePrescription.prepareRenderer(cellRenderer, row, column);
				preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
			}

			tableColumn.setPreferredWidth((int) Math.floor((double) preferredWidth * 0.6));
		}

		// --- Action sur la dbbTablePrescription via click de la souris.
		dbbTablePrescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// --- Action via click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = dbbTablePrescription.getSelectedRow();
						if (selectedRow != -1) {

							System.out.println(DateGestion.getTimeForConsole() + "Ordonnances selectionné.");

							String specialist_;

							if (!dbbTablePrescription.getValueAt(selectedRow, 5).toString().equals("")) {

								specialist_ = dbbTablePrescription.getValueAt(selectedRow, 5).toString();
							} else {
								specialist_ = "Aucun spécialiste déclaré";
							}
							DetailsPanel.indexPrescriptionValue.setText(dbbTablePrescription.getValueAt(selectedRow, 0).toString());
							DetailsPanel.datePrescriptionValue.setText(dbbTablePrescription.getValueAt(selectedRow, 1).toString());
							DetailsPanel.clientPrescriptionValue.setText(dbbTablePrescription.getValueAt(selectedRow, 2).toString());
							DetailsPanel.doctorPrescriptionValue.setText(dbbTablePrescription.getValueAt(selectedRow, 3).toString());
							DetailsPanel.specialistPrescriptionValue.setText(specialist_);

							DetailsPanel.indexPrescriptionValue.revalidate();
							DetailsPanel.datePrescriptionValue.revalidate();
							DetailsPanel.clientPrescriptionValue.revalidate();
							DetailsPanel.doctorPrescriptionValue.revalidate();
							DetailsPanel.specialistPrescriptionValue.revalidate();

							String[] drugs_ = dbbTablePrescription.getValueAt(selectedRow, 4).toString().replace("[", "{\"")
									.replace("]", "\"}").replace(",", "\",\"").replace(" ", "").replace("{", "")
									.replace("}", "").split(",");

							DefaultTableModel tableMode = new DefaultTableModel();
							tableMode.addColumn("Dénomination");

							for (String value : drugs_) {
								Object[] row = { value };

								tableMode.addRow(row);
							}

							JTable dbbTablePrescription = new JTable(tableMode);
							JScrollPane scrollPane = new JScrollPane(dbbTablePrescription);

							DetailsPanel.paneldDrugsPrescription.removeAll();
							DetailsPanel.paneldDrugsPrescription.add(scrollPane, BorderLayout.CENTER);

						}
					}
				}
				// --- Action via double click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int selectedRow = dbbTablePrescription.getSelectedRow();
						if (selectedRow != -1) {
							TableGestion.drawTableBddPharma(dbbTablePrescription, selectedRow, imageIconBdd.get());
						}
					}
				}
				// --- Action via click droit de la souris.
				if (SwingUtilities.isRightMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = dbbTablePrescription.getSelectedRow();
						if (selectedRow != -1) {
							System.out.println(DateGestion.getTimeForConsole() + " JTable (action via click droit)");
							int row = dbbTablePrescription.rowAtPoint(e.getPoint());
							if (row >= 0 && row < dbbTablePrescription.getRowCount()) {
								dbbTablePrescription.setRowSelectionInterval(row, row);
								PopupMenuGestion.bddPharmaRightClickAction(e, selectedRow, dbbTablePrescription, imageIconBdd.get());
							}
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(dbbTablePrescription, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// --- Création de la barre d'outils.
		JLabel searchText = new JLabel("<html><b> Rechercher </b></html>");

		JTextField searchField = new JTextField(32);
		searchField.addActionListener(e -> TableGestion.searchInTableBdd(dbbTablePrescription, searchField.getText()));

		JButton searchButton = new JButton(new ImageIcon(App.PATH_ICONS_LOW + "find.png"));
		searchButton.setToolTipText("Rechercher...");
		searchButton.addActionListener(e -> TableGestion.searchInTableBdd(dbbTablePrescription, searchField.getText()));

		Component horizontalSpaceToolBar = Box.createHorizontalStrut(8);
		JToolBar toolBar = new JToolBar();
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchText);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchField);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchButton);
		toolBar.setFloatable(false);
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.setBorder(new EmptyBorder(0, 12, 0, 0));

		// --- Création de la barre de status.
		JPanel statusBar = statusBarOrderButtonCreate();

		// --- Assemblage des composants graphique.
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(toolBar, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(statusBar, BorderLayout.SOUTH);

		return mainPanel;

	}

	/**
	 * Cette méthode créée une table virtualisée contenant l'ensemble des clients.
	 * 
	 * @param filePathDataBase fichier de la bdd des clients.
	 * @return un JTabbedPane contenant une table virtualisée de la base de données.
	 */
	public static JPanel jTabbedDatabaseClients(String filePathDataBase, boolean isFrame) {

		// --- Vérification de l'existence de la base de données (fichier).
		App.verifRessources(filePathDataBase, true);

		// --- Paramètrage spécifique.
		AtomicReference<String> imageIconBdd = new AtomicReference<>(App.PATH_ICONS_LOW + "");
		imageIconBdd.set(App.PATH_ICONS_LOW + "user-male.png");

		// --- Création de la table virtualisée.
		ArrayList<String> data = FileTextUtilities.readFileText(filePathDataBase, "Prescritptions");

		ArrayList<ElementsClient> elements = new ArrayList<>();
		for (String e : data) {
			ElementsClient convertedElement = StringToElementsConverter.stringToElementsClient(e);
			elements.add(convertedElement);
		}
		VirtualTableBddClient dbbTable = new VirtualTableBddClient(elements);
		JTable table = new JTable(dbbTable);
		table.setAutoCreateRowSorter(true);

		// --- Ajustement de la largeur des colonnes
		for (int column = 0; column < table.getColumnCount(); column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = 0;

			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
				Component c = table.prepareRenderer(cellRenderer, row, column);
				preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
			}

			tableColumn.setPreferredWidth((int) Math.floor((double) preferredWidth * 0.6));
		}

		// --- Action sur la table via click de la souris.
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// --- Action via click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {

							System.out.println(DateGestion.getTimeForConsole() + "Clients selectionné.");

							if (isFrame) {

								String idClient = table.getValueAt(selectedRow, 0).toString();
								App.informationsClientOrderSelected = FileTextUtilities.searchClient(idClient);

							} else {

								String address_ = table.getValueAt(selectedRow, 3).toString() + ", "
										+ table.getValueAt(selectedRow, 4).toString() + " - "
										+ table.getValueAt(selectedRow, 5).toString();

								String specialist_;

								if (!table.getValueAt(selectedRow, 11).toString().equals("")) {
									specialist_ = table.getValueAt(selectedRow, 11).toString();
								} else {
									specialist_ = "Aucun spécialiste déclaré";
								}

								DetailsPanel.indexClientValue.setText(table.getValueAt(selectedRow, 0).toString());
								DetailsPanel.lastNameClientValue.setText(table.getValueAt(selectedRow, 1).toString());
								DetailsPanel.firstNameClientValue.setText(table.getValueAt(selectedRow, 2).toString());
								DetailsPanel.addressClientValue.setText(address_);
								DetailsPanel.phoneClientValue.setText(table.getValueAt(selectedRow, 6).toString());
								DetailsPanel.mailClientValue.setText(table.getValueAt(selectedRow, 7).toString());
								DetailsPanel.socialSecuNumberClientValue
										.setText(table.getValueAt(selectedRow, 8).toString());
								DetailsPanel.mutualClientValue.setText(table.getValueAt(selectedRow, 9).toString());
								DetailsPanel.doctorClientValue.setText(table.getValueAt(selectedRow, 10).toString());
								DetailsPanel.specialistClientValue.setText(specialist_);

								DetailsPanel.indexClientValue.revalidate();
								DetailsPanel.lastNameClientValue.revalidate();
								DetailsPanel.firstNameClientValue.revalidate();
								DetailsPanel.addressClientValue.revalidate();
								DetailsPanel.phoneClientValue.revalidate();
								DetailsPanel.mailClientValue.revalidate();
								DetailsPanel.socialSecuNumberClientValue.revalidate();
								DetailsPanel.mutualClientValue.revalidate();
								DetailsPanel.doctorClientValue.revalidate();
								DetailsPanel.specialistClientValue.revalidate();
							}
						}
					}
				}
				// --- Action via double click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							
							if (isFrame) {
								addClientToInputOrder();
							}
						}
					}
				}
				// --- Action via click droit de la souris.
				if (SwingUtilities.isRightMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							System.out.println(DateGestion.getTimeForConsole() + " JTable (action via click droit)");
							int row = table.rowAtPoint(e.getPoint());
							if (row >= 0 && row < table.getRowCount()) {
								table.setRowSelectionInterval(row, row);
								
							}
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// --- Création de la barre d'outils.
		JLabel searchText = new JLabel("<html><b> Rechercher </b></html>");

		JTextField searchField = new JTextField(32);
		if (isFrame) {
			searchField.addActionListener(e -> TableGestion.searchInTableFrameBdd(table, searchField.getText(), 0));
		} else {
			searchField.addActionListener(e -> TableGestion.searchInTableBdd(table, searchField.getText()));
		}
		JButton searchButton = new JButton(new ImageIcon(App.PATH_ICONS_LOW + "find.png"));
		searchButton.setToolTipText("Rechercher...");
		searchButton.addActionListener(e -> TableGestion.searchInTableBdd(table, searchField.getText()));

		Component horizontalSpaceToolBar = Box.createHorizontalStrut(8);
		JToolBar toolBar = new JToolBar();
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchText);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchField);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchButton);
		toolBar.setFloatable(false);
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.setBorder(new EmptyBorder(0, 12, 0, 0));

		// --- Création de la barre de status.
		JPanel statusBar = new JPanel();
		if (isFrame) {
			statusBar = statusBarAddClientButtonCreate();
		} else {
			statusBar = statusBarOrderButtonCreate();
		}

		// --- Assemblage des composants graphiques.
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(toolBar, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(statusBar, BorderLayout.SOUTH);

		return mainPanel;

	}

	/**
	 * Cette méthode créée une table virtualisée contenant l'ensemble des médecins.
	 * 
	 * @param filePathDataBase fichier de la bdd des médecins.
	 * @return un JTabbedPane contenant une table virtualisée de la base de données.
	 */
	public static JPanel jTabbedDatabaseDoctors(String filePathDataBase, boolean isFrame) {

		// --- Vérification de l'existence de la base de données (fichier).
		App.verifRessources(filePathDataBase, true);

		// --- Paramètrage spécifique.
		AtomicReference<String> imageIconBdd = new AtomicReference<>(App.PATH_ICONS_LOW + "");
		imageIconBdd.set(App.PATH_ICONS_LOW + "doctor.png");

		// --- Création de la table virtualisée.
		ArrayList<String> data = FileTextUtilities.readFileText(filePathDataBase, "");

		ArrayList<ElementsDoctor> elements = new ArrayList<>();
		for (String e : data) {
			ElementsDoctor convertedElement = StringToElementsConverter.stringToElementsDoctor(e);
			elements.add(convertedElement);
		}
		VirtualTableBddDoctor dbbTable = new VirtualTableBddDoctor(elements);
		JTable table = new JTable(dbbTable);
		table.setAutoCreateRowSorter(true);

		// --- Ajustement de la largeur des colonnes
		for (int column = 0; column < table.getColumnCount(); column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = 0;

			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
				Component c = table.prepareRenderer(cellRenderer, row, column);
				preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
			}

			tableColumn.setPreferredWidth((int) Math.floor((double) preferredWidth * 0.6));
		}

		// --- Action sur la table via click de la souris.
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// --- Action via click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {

							if (isFrame) {

								String idDoctor = table.getValueAt(selectedRow, 0).toString();

								App.infoArrayDoctorOrderSelected = FileTextUtilities.searchDoctor(idDoctor);
								App.informationsClientOrderSelected.set(2, App.infoArrayDoctorOrderSelected);

							} else {
								System.out.println(DateGestion.getTimeForConsole() + "Médecins selectionné.");

								String address_ = table.getValueAt(selectedRow, 3).toString() + ", "
										+ table.getValueAt(selectedRow, 4).toString() + " - "
										+ table.getValueAt(selectedRow, 5).toString();

								DetailsPanel.indexDoctorValue.setText(table.getValueAt(selectedRow, 0).toString());
								DetailsPanel.lastNameDoctorValue.setText(table.getValueAt(selectedRow, 1).toString());
								DetailsPanel.firstNameDoctorValue.setText(table.getValueAt(selectedRow, 2).toString());
								DetailsPanel.addressDoctorValue.setText(address_);
								DetailsPanel.phoneDoctorValue.setText(table.getValueAt(selectedRow, 6).toString());
								DetailsPanel.mailDoctorValue.setText(table.getValueAt(selectedRow, 7).toString());
								DetailsPanel.numRRPSDoctorValue.setText(table.getValueAt(selectedRow, 8).toString());
								DetailsPanel.nbPatientDoctorValue.setText(table.getValueAt(selectedRow, 9).toString());
								DetailsPanel.specialityDoctorValue
										.setText(table.getValueAt(selectedRow, 10).toString());

								DetailsPanel.indexDoctorValue.revalidate();
								DetailsPanel.lastNameDoctorValue.revalidate();
								DetailsPanel.firstNameDoctorValue.revalidate();
								DetailsPanel.addressDoctorValue.revalidate();
								DetailsPanel.phoneDoctorValue.revalidate();
								DetailsPanel.mailDoctorValue.revalidate();
								DetailsPanel.numRRPSDoctorValue.revalidate();
								DetailsPanel.nbPatientDoctorValue.revalidate();
								DetailsPanel.specialityDoctorValue.revalidate();
							}
						}
					}
				}
				// --- Action via double click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							if (isFrame) {
								addDoctorToInputOrder();
							}
						}
					}
				}
				// --- Action via click droit de la souris.
				if (SwingUtilities.isRightMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							System.out.println(DateGestion.getTimeForConsole() + " JTable (action via click droit)");
							int row = table.rowAtPoint(e.getPoint()); // Récupérer la ligne du clic
							if (row >= 0 && row < table.getRowCount()) {
								table.setRowSelectionInterval(row, row); // Sélectionner la ligne du clic

							}
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// --- Création de la barre d'outils.
		JLabel searchText = new JLabel("<html><b> Rechercher </b></html>");

		JTextField searchField = new JTextField(32);
		if (isFrame) {
			searchField.addActionListener(e -> TableGestion.searchInTableFrameBdd(table, searchField.getText(), 2));
		} else {
			searchField.addActionListener(e -> TableGestion.searchInTableBdd(table, searchField.getText()));
		}

		JButton searchButton = new JButton(new ImageIcon(App.PATH_ICONS_LOW + "find.png"));
		searchButton.setToolTipText("Rechercher...");
		searchButton.addActionListener(e -> TableGestion.searchInTableBdd(table, searchField.getText()));

		Component horizontalSpaceToolBar = Box.createHorizontalStrut(8);
		JToolBar toolBar = new JToolBar();
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchText);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchField);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchButton);
		toolBar.setFloatable(false);
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.setBorder(new EmptyBorder(0, 12, 0, 0));

		// --- Création de la barre de status.
		JPanel statusBar = new JPanel();
		if (isFrame) {
			statusBar = statusBarAddDoctorButtonCreate();
		} else {
			statusBar = statusBarOrderButtonCreate();
		}
		// --- Assemblage des composants graphique.
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(toolBar, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(statusBar, BorderLayout.SOUTH);

		return mainPanel;

	}

	/**
	 * 
	 * /** Cette méthode créée une table virtualisée contenant l'ensemble des
	 * mutuelles.
	 * 
	 * @param filePathDataBase fichier de la bdd des mutuelles.
	 * @return un JTabbedPane contenant une table virtualisée de la base de données.
	 */
	public static JPanel jTabbedDatabaseMutuals(String filePathDataBase, boolean isFrame) {

		// --- Vérification de l'existence de la base de données (fichier).
		App.verifRessources(filePathDataBase, true);

		// --- Paramètrage spécifique.
		AtomicReference<String> imageIconBdd = new AtomicReference<>(App.PATH_ICONS_LOW + "");
		imageIconBdd.set(App.PATH_ICONS_LOW + "purchase-order.png");

		// --- Création de la table virtualisée.
		ArrayList<String> data = FileTextUtilities.readFileText(filePathDataBase, "");

		ArrayList<ElementsMutual> elements = new ArrayList<>();
		for (String e : data) {
			ElementsMutual convertedElement = StringToElementsConverter.stringToElementsMutual(e);
			elements.add(convertedElement);
		}
		VirtualTableBddMutual dbbTable = new VirtualTableBddMutual(elements);
		JTable table = new JTable(dbbTable);
		table.setAutoCreateRowSorter(true);

		// --- Ajustement de la largeur des colonnes
		for (int column = 0; column < table.getColumnCount(); column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = 0;

			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
				Component c = table.prepareRenderer(cellRenderer, row, column);
				preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
			}

			tableColumn.setPreferredWidth((int) Math.floor((double) preferredWidth * 0.6));
		}

		// --- Action sur la table via click de la souris.
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// --- Action via click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {

							if (isFrame) {

								String idMutual = table.getValueAt(selectedRow, 0).toString();

								App.infoArrayMutualOrderSelected = FileTextUtilities.searchMutual(idMutual);
								System.out.println(App.informationsClientOrderSelected.size());
								App.informationsClientOrderSelected.set(1, App.infoArrayMutualOrderSelected);

								for (int i = 0; i < App.informationsClientOrderSelected.size(); i++) {
									for (int j = 0; j < App.informationsClientOrderSelected.get(i).length; j++) {
										System.out.print(App.informationsClientOrderSelected.get(i)[j] + " | ");
									}
									System.out.println("");
								}

							}
						}
					}
				}
				// --- Action via double click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							if (isFrame) {
								addMutualToInputOrder();
							}
						}
					}
				}
				// --- Action via click droit de la souris.
				if (SwingUtilities.isRightMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							System.out.println(DateGestion.getTimeForConsole() + " JTable (action via click droit)");
							int row = table.rowAtPoint(e.getPoint());
							if (row >= 0 && row < table.getRowCount()) {
								table.setRowSelectionInterval(row, row);

							}
						}
					}
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// --- Création de la barre d'outils.
		JLabel searchText = new JLabel("<html><b> Rechercher </b></html>");

		JTextField searchField = new JTextField(32);
		if (isFrame) {
			searchField.addActionListener(e -> TableGestion.searchInTableFrameBdd(table, searchField.getText(), 2));
		} else {
			searchField.addActionListener(e -> TableGestion.searchInTableBdd(table, searchField.getText()));
		}

		JButton searchButton = new JButton(new ImageIcon(App.PATH_ICONS_LOW + "find.png"));
		searchButton.setToolTipText("Rechercher...");
		searchButton.addActionListener(e -> TableGestion.searchInTableBdd(table, searchField.getText()));

		Component horizontalSpaceToolBar = Box.createHorizontalStrut(8);
		JToolBar toolBar = new JToolBar();
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchText);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchField);
		toolBar.add(horizontalSpaceToolBar);
		toolBar.add(searchButton);
		toolBar.setFloatable(false);
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.setBorder(new EmptyBorder(0, 12, 0, 0));

		// --- Création de la barre de status.
		JPanel statusBar = new JPanel();
		if (isFrame) {
			statusBar = statusBarAddMutualButtonCreate();
		} else {
			statusBar = statusBarOrderButtonCreate();
		}
		// --- Assemblage des composants graphique.
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(toolBar, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(statusBar, BorderLayout.SOUTH);

		return mainPanel;

	}

	/**
	 * Retourne un JButton permettant d'ouvrir la fenêtre "nouvel achat".
	 * 
	 * @return Un JButton
	 */
	private static JPanel statusBarOrderButtonCreate() {

		ImageIcon newOrderIcon = new ImageIcon(App.PATH_ICONS_LOW + "receipt.png");
		JButton newOrderBtn = new JButton(newOrderIcon);
		newOrderBtn.setText("Saisir un achat");
		newOrderBtn.setToolTipText("Saisir un achat");
		newOrderBtn.setPreferredSize(new Dimension(160, 32));
		newOrderBtn.addActionListener(e -> App.switchSectionNorth());

		JPanel statusBar = new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		statusBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
		statusBar.add(Box.createHorizontalGlue());
		statusBar.setMinimumSize(new Dimension(0, 32));
		statusBar.setPreferredSize(new Dimension(0, 40));

		statusBar.add(newOrderBtn);

		return statusBar;

	}

	/**
	 * Retourne un JButton permettant d'ajouter un client choisi dans la liste.
	 * 
	 * @return Un JButton
	 */
	private static JPanel statusBarAddClientButtonCreate() {

		ImageIcon newOrderIcon = new ImageIcon(App.PATH_ICONS_LOW + "user-male-add.png");
		JButton newAddBtn = new JButton(newOrderIcon);
		newAddBtn.setText("Ajouter client");
		newAddBtn.setToolTipText("Ajouter client");
		newAddBtn.setPreferredSize(new Dimension(160, 32));
		newAddBtn.addActionListener(e -> addClientToInputOrder());

		JPanel statusBar = new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		statusBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
		statusBar.add(Box.createHorizontalGlue());
		statusBar.setMinimumSize(new Dimension(0, 32));
		statusBar.setPreferredSize(new Dimension(0, 40));

		statusBar.add(newAddBtn);

		return statusBar;

	}

	/**
	 * Retourne un JButton permettant d'ajouter un médecin choisi dans la liste.
	 * 
	 * @return Un JButton
	 */
	private static JPanel statusBarAddDoctorButtonCreate() {

		ImageIcon newOrderIcon = new ImageIcon(App.PATH_ICONS_LOW + "Doctor-add.png");
		JButton newAddBtn = new JButton(newOrderIcon);
		newAddBtn.setText("Ajouter médecin traitant");
		newAddBtn.setToolTipText("Ajouter médecin traitant");
		newAddBtn.setPreferredSize(new Dimension(256, 32));
		newAddBtn.addActionListener(e -> addDoctorToInputOrder());

		JPanel statusBar = new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		statusBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
		statusBar.add(Box.createHorizontalGlue());
		statusBar.setMinimumSize(new Dimension(0, 32));
		statusBar.setPreferredSize(new Dimension(0, 40));

		statusBar.add(newAddBtn);

		return statusBar;

	}

	/**
	 * 
	 * Retourne un JButton permettant d'ajouter une mutuelle choisie dans la liste.
	 * 
	 * @return Un JButton
	 */
	private static JPanel statusBarAddMutualButtonCreate() {

		ImageIcon newOrderIcon = new ImageIcon(App.PATH_ICONS_LOW + "purchase-order-add.png");
		JButton newAddBtn = new JButton(newOrderIcon);
		newAddBtn.setText("Ajouter mutuelle");
		newAddBtn.setToolTipText("Ajouter médecin traitant");
		newAddBtn.setPreferredSize(new Dimension(256, 32));
		newAddBtn.addActionListener(e -> addMutualToInputOrder());

		JPanel statusBar = new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		statusBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
		statusBar.add(Box.createHorizontalGlue());
		statusBar.setMinimumSize(new Dimension(0, 32));
		statusBar.setPreferredSize(new Dimension(0, 40));

		statusBar.add(newAddBtn);

		return statusBar;

	}

	/**
	 * Ajoute les informations du client sélectionné dans la Frame Bbdd clients dans
	 * les champs de saisie client.
	 */
	private static void addClientToInputOrder() {

		if (!App.informationsClientOrderSelected.isEmpty()) {
			/*
			 * Note : même si le type d'achat sélectionné est un achat simple sans
			 * ordonnance, les informations de la mutuelle et celles du médecins traitant
			 * sont ajouter au formulaire. Il ne seront pas traité lors de l'achat.
			 */

			// --- Fermeture de la fenêtre "ajouter client (bdd)".
			TableGestion.frameBddClients.dispose();

			// --- Ajout des informations clients sélectionnées dans le formulaire.
			InputOrder.firstNameClient.setText(App.informationsClientOrderSelected.get(0)[2]);
			InputOrder.lastNameClient.setText(App.informationsClientOrderSelected.get(0)[1]);
			InputOrder.addressClient.setText(App.informationsClientOrderSelected.get(0)[3]);
			InputOrder.postalCodeClient.setText(App.informationsClientOrderSelected.get(0)[4]);
			InputOrder.cityClient.setText(App.informationsClientOrderSelected.get(0)[5]);
			InputOrder.telephoneClient.setText(App.informationsClientOrderSelected.get(0)[6]);
			InputOrder.emailClient.setText(App.informationsClientOrderSelected.get(0)[7]);
			InputOrder.socialNumbClient.setText(App.informationsClientOrderSelected.get(0)[8]);

			// --- Ajout des informations mutuelle client sélectionnées dans le formulaire.
			InputOrder.nameMutual.setText(App.informationsClientOrderSelected.get(1)[1]);
			InputOrder.addressMutual.setText(App.informationsClientOrderSelected.get(1)[2]);
			InputOrder.postalCodeMutual.setText(App.informationsClientOrderSelected.get(1)[3]);
			InputOrder.cityMutual.setText(App.informationsClientOrderSelected.get(1)[4]);
			InputOrder.telephoneMutual.setText(App.informationsClientOrderSelected.get(1)[5]);
			InputOrder.emailMutual.setText(App.informationsClientOrderSelected.get(1)[6]);
			InputOrder.rateMutual.setText(App.informationsClientOrderSelected.get(1)[8]);

			// --- Ajout des informations médecin traitant sélectionnées dans le formulaire.
			InputOrder.firstNameDoctor.setText(App.informationsClientOrderSelected.get(2)[2]);
			InputOrder.lastNameDoctor.setText(App.informationsClientOrderSelected.get(2)[1]);
			InputOrder.addressDoctor.setText(App.informationsClientOrderSelected.get(2)[3]);
			InputOrder.postalCodeDoctor.setText(App.informationsClientOrderSelected.get(2)[4]);
			InputOrder.cityDoctor.setText(App.informationsClientOrderSelected.get(2)[5]);
			InputOrder.telephoneDoctor.setText(App.informationsClientOrderSelected.get(2)[6]);
			InputOrder.emailDoctor.setText(App.informationsClientOrderSelected.get(2)[7]);
			InputOrder.rrpsDoctor.setText(App.informationsClientOrderSelected.get(2)[8]);

		} else {
			if (App.ERROR_SOUND != null) {
				App.ERROR_SOUND.run();
			}
			JOptionPane.showMessageDialog(null, "Aucun client n'a été sélectionné.", "Message system",
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon(App.ICON_ERROR));

		}
		InputOrder.drawInfoOrder();
	}

	/**
	 * Ajoute les informations du médecin sélectionné dans la Frame Bbdd médecins
	 * dans les champs de saisie médecin.
	 */
	private static void addDoctorToInputOrder() {

		if (!isStringArrayEmpty(App.infoArrayDoctorOrderSelected)) {

			// --- Fermeture de la fenêtre "ajouter médecin (bdd)".
			TableGestion.frameBddDoctors.dispose();

			// --- Ajout des informations médecin traitant sélectionnées dans le formulaire.
			InputOrder.firstNameDoctor.setText(App.informationsClientOrderSelected.get(2)[2]);
			InputOrder.lastNameDoctor.setText(App.informationsClientOrderSelected.get(2)[1]);
			InputOrder.addressDoctor.setText(App.informationsClientOrderSelected.get(2)[3]);
			InputOrder.postalCodeDoctor.setText(App.informationsClientOrderSelected.get(2)[4]);
			InputOrder.cityDoctor.setText(App.informationsClientOrderSelected.get(2)[5]);
			InputOrder.telephoneDoctor.setText(App.informationsClientOrderSelected.get(2)[6]);
			InputOrder.emailDoctor.setText(App.informationsClientOrderSelected.get(2)[7]);
			InputOrder.rrpsDoctor.setText(App.informationsClientOrderSelected.get(2)[8]);

		} else {

			if (App.ERROR_SOUND != null) {
				App.ERROR_SOUND.run();
			}
			JOptionPane.showMessageDialog(null, "Aucun médecin n'a été sélectionné.", "Message system",
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon(App.ICON_ERROR));

		}
		InputOrder.drawInfoOrder();
	}

	/**
	 * 
	 * Ajoute les informations de la mutuelle sélectionnée dans la Frame Bbdd
	 * mutuelles dans les champs de saisie mutuelle.
	 */
	private static void addMutualToInputOrder() {

		if (!isStringArrayEmpty(App.infoArrayMutualOrderSelected)) {

			// --- Fermeture de la fenêtre "ajouter médecin (bdd)".
			TableGestion.frameBddMutuals.dispose();

			// --- Ajout des informations médecin traitant sélectionnées dans le formulaire.
			InputOrder.nameMutual.setText(App.informationsClientOrderSelected.get(1)[1]);
			InputOrder.addressMutual.setText(App.informationsClientOrderSelected.get(1)[2]);
			InputOrder.postalCodeMutual.setText(App.informationsClientOrderSelected.get(1)[3]);
			InputOrder.cityMutual.setText(App.informationsClientOrderSelected.get(1)[4]);
			InputOrder.telephoneMutual.setText(App.informationsClientOrderSelected.get(1)[5]);
			InputOrder.emailMutual.setText(App.informationsClientOrderSelected.get(1)[6]);
			InputOrder.rateMutual.setText(App.informationsClientOrderSelected.get(1)[8]);

		} else {

			if (App.ERROR_SOUND != null) {
				App.ERROR_SOUND.run();
			}
			JOptionPane.showMessageDialog(null, "Aucune mutuelle n'a été sélectionnée.", "Message system",
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon(App.ICON_ERROR));

		}
		InputOrder.drawInfoOrder();
	}

	/**
	 * Permet de vérifier si un tableau (String[]) est vide.
	 * 
	 * @param un tableau de chaines de caractères.
	 * @return une valeur booléenne.
	 */
	public static boolean isStringArrayEmpty(String[] array) {

		boolean isEmpty = false;

		for (int i = 0; i < array.length; i++) {

			if (array[i] == "" || array[i] == null) {
				isEmpty = true;
			}

		}

		return isEmpty;
	}
}