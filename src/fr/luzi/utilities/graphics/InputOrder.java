package fr.luzi.utilities.graphics;

/**
 * Cette classe est dédiée à l'interface graphique des achats.
 * 
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import fr.luzi.application.App;
import fr.luzi.defense.Regex;
import fr.luzi.utilities.DateGestion;
import fr.luzi.utilities.FileTextUtilities;
import fr.luzi.utilities.StringToElementsConverter;
import fr.luzi.utilities.jtable.ElementsDrug;
import fr.luzi.utilities.jtable.ElementsPrescription;
import fr.luzi.utilities.jtable.ElementsPurchaseHistory;
import fr.luzi.utilities.jtable.TableGestion;
import fr.luzi.utilities.jtable.VirtualJTabbleBdd;
import fr.luzi.utilities.jtable.VirtualTableBddDrug;
import fr.luzi.utilities.math.ConvertBase;

public class InputOrder {

	public static JPanel panelParentOrder = new JPanel();

	// --- Relatifs au type d'achat.
	public static int PurchaseOnPrescription = 0;

	// --- Relatifs au couleurs de saisie.
	private static Color COLOR_RED = ConvertBase.hexToColor("E7BCBC");
	private static Color COLOR_GREEN = ConvertBase.hexToColor("BEE7BC");
	private static Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
			BorderFactory.createLineBorder(App.GREY_COLOR, 1)
	);

	// --- Relatifs à la saisie client.
	public static JPanel panelOrderClient = new JPanel();

	public static JLabel firstNameClientLbl = new JLabel("");
	public static JLabel lastNameClientLbl = new JLabel("");
	public static JLabel addressClientLbl = new JLabel("");
	public static JLabel postalCodeClientLbl = new JLabel("");
	public static JLabel cityClientLbl = new JLabel("");
	public static JLabel telephoneClientLbl = new JLabel("");
	public static JLabel emailClientLbl = new JLabel("");
	public static JLabel socialNumbClientLbl = new JLabel("");

	public static JTextField firstNameClient;
	public static JTextField lastNameClient;
	public static JTextField addressClient;
	public static JTextField postalCodeClient;
	public static JTextField cityClient;
	public static JTextField telephoneClient;
	public static JTextField emailClient;
	public static JTextField socialNumbClient;

	// --- Relatifs à la saisie mutuelle.
	public static JPanel panelOrderMutual = new JPanel();

	public static JLabel nameMutualLbl = new JLabel("");
	public static JLabel addressMutualLbl = new JLabel("");
	public static JLabel postalCodeMutualLbl = new JLabel("");
	public static JLabel cityMutualLbl = new JLabel("");
	public static JLabel telephoneMutualLbl = new JLabel("");
	public static JLabel emailMutualLbl = new JLabel("");
	public static JLabel rateMutualLbl = new JLabel("");

	public static JTextField nameMutual;
	public static JTextField addressMutual;
	public static JTextField postalCodeMutual;
	public static JTextField cityMutual;
	public static JTextField telephoneMutual;
	public static JTextField emailMutual;
	public static JTextField rateMutual;

	// --- Relatifs à la saisie Doctor.
	public static JPanel panelOrderDoctor = new JPanel();

	public static JLabel firstNameDoctorLbl = new JLabel("");
	public static JLabel lastNameDoctorLbl = new JLabel("");
	public static JLabel addressDoctorLbl = new JLabel("");
	public static JLabel postalCodeDoctorLbl = new JLabel("");
	public static JLabel cityDoctorLbl = new JLabel("");
	public static JLabel telephoneDoctorLbl = new JLabel("");
	public static JLabel emailDoctorLbl = new JLabel("");
	public static JLabel rrpsDoctorLbl = new JLabel("");

	public static JTextField firstNameDoctor;
	public static JTextField lastNameDoctor;
	public static JTextField addressDoctor;
	public static JTextField postalCodeDoctor;
	public static JTextField cityDoctor;
	public static JTextField telephoneDoctor;
	public static JTextField emailDoctor;
	public static JTextField rrpsDoctor;

	// --- Relatifs à la liste d'achat (médicaments).
	private static DefaultTableModel tablePrescriptionChoiceModel;
	private static JTable tablePrescriptionChoice;

	// --- Realtifs aux nombres.
	private static int totalDrugsOrder = 0;
	private static Double totalPrice = 0.00;
	private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
	private static JLabel totalDrugsLbl = new JLabel(
			"<html><span style=\"font-size:12px\"><b>Nb. produits : " + totalDrugsOrder + "</b></span></html>");
	private static JLabel totalPriceLbl = new JLabel("<html><span style=\"font-size:12px\"><b>Total : "
			+ decimalFormat.format(totalPrice) + " €</b></span></html>");
	private static Double totalPriceGlobal = 0.00;

	// --- Relatifs à la validation de l'achat.
	private static int paiementType = -1;
	private static String[] optionsComboBoxPaiementType = { "Select. type réglement", "Espèces", "Carte bancaire" };
	private static JComboBox<String> comboBoxPaiementType;
	private static JButton btnValidOrder;

	// --- Début des méthodes.

	/**
	 * Fenêtre globale de la saisie achat.
	 * 
	 * @return Un JSplitPane contenant le JPanel des saisies client, mutuelle et
	 *         médecin ainsi que celui contenant la liste des médicaments et le
	 *         panier d'achat.
	 * 
	 */
	public static JSplitPane inputOrderCreate() {

		panelParentOrder.setLayout(new FlowLayout());
		panelParentOrder.setPreferredSize(new Dimension(panelParentOrder.getWidth(), 1024));
		panelParentOrder.add(inputOrderClientCreate());
		panelParentOrder.add(inputOrderMutualCreate());
		panelParentOrder.add(inputOrderDoctorCreate());

		panelOrderMutual.setVisible(false);
		panelOrderDoctor.setVisible(false);

		JScrollPane scrollPaneinputOrder = new JScrollPane(panelParentOrder);
		scrollPaneinputOrder.setPreferredSize(new Dimension(280, 0));

		JPanel panelPrescriptionChoice = new JPanel();
		panelPrescriptionChoice.setLayout(new GridLayout(2, 1));

		panelPrescriptionChoice.add(prescriptionListCreate());
		panelPrescriptionChoice.add(prescriptionChoiceCreate());

		JSplitPane orderSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneinputOrder,
				panelPrescriptionChoice);
		orderSplitPane.setResizeWeight(0.4);

		return orderSplitPane;
	}

	/**
	 * Créer et retourne le JPanel contenant le formulaire client.
	 * 
	 * @return Un JPanel contenant le formulaire client.
	 */
	private static JPanel inputOrderClientCreate() {

		// --- Création des composants GridBagLayout Client.
		panelOrderClient.setBorder(border);
		panelOrderClient.setLayout(new GridBagLayout());

		// --- Création du titre GridBagLayout Client.
		JPanel panelTitle = new JPanel();
		panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.X_AXIS));
		panelTitle.setPreferredSize(new Dimension(512, 64));

		Border dashedBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, App.GREY_COLOR);
		Border emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panelTitle.setBorder(new CompoundBorder(emptyBorder, dashedBorder));

		JPanel titleLeft = new JPanel();
		JPanel titleRight = new JPanel();
		titleLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		titleRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		titleLeft.setPreferredSize(new Dimension(256, 44));
		titleRight.setPreferredSize(new Dimension(256, 44));

		JLabel titleClientLbl = new JLabel(
				"<html><span style=\"font-size:16px\"><b>Informations client :</b></span></html>");

		ImageIcon iconTrashClients = new ImageIcon(App.PATH_ICONS_LOW + "delete.png");
		JButton trashingClient = new JButton(iconTrashClients);
		trashingClient.addActionListener(e -> trashClient());
		trashingClient.setToolTipText("Effacer la sasie client");

		ImageIcon iconAddClients = new ImageIcon(App.PATH_ICONS_LOW + "user-male-add.png");
		JButton selectClient = new JButton(iconAddClients);
		selectClient.addActionListener(e -> TableGestion.drawTableBddClients());
		selectClient.setToolTipText("Sélectionner un client dans la base de données");

		titleLeft.add(titleClientLbl);
		titleRight.add(selectClient);
		titleRight.add(trashingClient);

		panelTitle.add(titleLeft);
		panelTitle.add(titleRight);

		// --- Création des JLabel et JTextField.
		firstNameClientLbl.setText("<html><b>Prénom :</b></html>");
		firstNameClientLbl.setPreferredSize(new Dimension(256, firstNameClientLbl.getPreferredSize().height));

		lastNameClientLbl.setText("<html><b>Nom :</b></html>");
		lastNameClientLbl.setPreferredSize(new Dimension(256, lastNameClientLbl.getPreferredSize().height));

		firstNameClient = new JTextField(20);
		firstNameClient.setPreferredSize(new Dimension(256, firstNameClient.getPreferredSize().height));
		AbstractDocument firstNameClientGet = (AbstractDocument) firstNameClient.getDocument();
		firstNameClientGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_firstAndLastName)) {
					firstNameClient.setBackground(COLOR_RED);
				} else {
					firstNameClient.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		firstNameClient.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (firstNameClient.getText().isEmpty()) {
					firstNameClient.setBackground(Color.WHITE);
				} else {
					firstNameClient.setText(firstNameClient.getText().toLowerCase());
					firstNameClient.setText(Character.toUpperCase(firstNameClient.getText().charAt(0))
							+ firstNameClient.getText().substring(1));
				}
				checkIfOrderIfValid();
			}
		});

		lastNameClient = new JTextField();
		lastNameClient.setPreferredSize(new Dimension(256, lastNameClient.getPreferredSize().height));
		AbstractDocument lastNameClientGet = (AbstractDocument) lastNameClient.getDocument();
		lastNameClientGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_firstAndLastName)) {
					lastNameClient.setBackground(COLOR_RED);
				} else {
					lastNameClient.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		lastNameClient.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (lastNameClient.getText().isEmpty()) {
					lastNameClient.setBackground(Color.WHITE);
				} else {
					lastNameClient.setText(lastNameClient.getText().toUpperCase());
				}
				checkIfOrderIfValid();
			}
		});

		addressClientLbl.setText("<html><b>Adresse :</b></html>");
		addressClientLbl.setPreferredSize(new Dimension(512, addressClientLbl.getPreferredSize().height));

		addressClient = new JTextField();
		addressClient.setPreferredSize(new Dimension(512, addressClient.getPreferredSize().height));
		AbstractDocument firstAddressClientGet = (AbstractDocument) addressClient.getDocument();
		firstAddressClientGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_addressNumAndStreet)) {
					addressClient.setBackground(COLOR_RED);
				} else {
					addressClient.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		addressClient.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (addressClient.getText().isEmpty()) {
					addressClient.setBackground(Color.WHITE);
				}
				checkIfOrderIfValid();
			}
		});

		postalCodeClientLbl.setText("<html><b>Code Postal :</b></html>");
		postalCodeClientLbl.setPreferredSize(new Dimension(128, postalCodeClientLbl.getPreferredSize().height));

		cityClientLbl.setText("<html><b>Ville :</b></html>");
		cityClientLbl.setPreferredSize(new Dimension(384, cityClientLbl.getPreferredSize().height));

		postalCodeClient = new JTextField();
		postalCodeClient.setPreferredSize(new Dimension(128, postalCodeClient.getPreferredSize().height));
		AbstractDocument postalCodeClientGet = (AbstractDocument) postalCodeClient.getDocument();
		postalCodeClientGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_postalCodeFr)) {
					postalCodeClient.setBackground(COLOR_RED);
				} else {
					postalCodeClient.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		postalCodeClient.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (postalCodeClient.getText().isEmpty()) {
					postalCodeClient.setBackground(Color.WHITE);
				}
				checkIfOrderIfValid();
			}
		});

		cityClient = new JTextField();
		cityClient.setPreferredSize(new Dimension(384, cityClient.getPreferredSize().height));
		AbstractDocument cityClientGet = (AbstractDocument) cityClient.getDocument();
		cityClientGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_cityFr)) {
					cityClient.setBackground(COLOR_RED);
				} else {
					cityClient.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		cityClient.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (cityClient.getText().isEmpty()) {
					cityClient.setBackground(Color.WHITE);
				} else {
					cityClient.setText(cityClient.getText().toUpperCase());
				}
				checkIfOrderIfValid();
			}
		});

		telephoneClientLbl.setText("<html><b>Téléphone :</b></html>");
		telephoneClientLbl.setPreferredSize(new Dimension(172, telephoneClientLbl.getPreferredSize().height));

		emailClientLbl.setText("<html><b>Email :</b></html>");
		emailClientLbl.setPreferredSize(new Dimension(171, emailClientLbl.getPreferredSize().height));

		socialNumbClientLbl.setText("<html><b>Num. sécu. social :</b></html>");
		socialNumbClientLbl.setPreferredSize(new Dimension(170, socialNumbClientLbl.getPreferredSize().height));

		telephoneClient = new JTextField();
		telephoneClient.setPreferredSize(new Dimension(172, telephoneClient.getPreferredSize().height));
		AbstractDocument telephoneClientGet = (AbstractDocument) telephoneClient.getDocument();
		telephoneClientGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_phoneFR)) {
					telephoneClient.setBackground(COLOR_RED);
				} else {
					telephoneClient.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		telephoneClient.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (telephoneClient.getText().isEmpty()) {
					telephoneClient.setBackground(Color.WHITE);
				} else {

					if (!telephoneClient.getText().matches(Regex.REGEX_phoneFR)) {
						telephoneClient.setBackground(COLOR_RED);
					} else {
						telephoneClient.setBackground(COLOR_GREEN);
					}

				}
				checkIfOrderIfValid();
			}
		});

		emailClient = new JTextField();
		emailClient.setPreferredSize(new Dimension(171, emailClient.getPreferredSize().height));
		AbstractDocument emailClientGet = (AbstractDocument) emailClient.getDocument();
		emailClientGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_email)) {
					emailClient.setBackground(COLOR_RED);
				} else {
					emailClient.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		emailClient.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (emailClient.getText().isEmpty()) {
					emailClient.setBackground(Color.WHITE);
				} else {

					if (!emailClient.getText().matches(Regex.REGEX_email)) {
						emailClient.setBackground(COLOR_RED);
					} else {
						emailClient.setBackground(COLOR_GREEN);
					}
				}
				checkIfOrderIfValid();
			}
		});

		socialNumbClient = new JTextField();
		socialNumbClient.setPreferredSize(new Dimension(170, socialNumbClient.getPreferredSize().height));
		AbstractDocument socialNumbClientGet = (AbstractDocument) socialNumbClient.getDocument();
		socialNumbClientGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_FalseSecuNumber)) {
					socialNumbClient.setBackground(COLOR_RED);
				} else {
					socialNumbClient.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		socialNumbClient.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (socialNumbClient.getText().isEmpty()) {
					socialNumbClient.setBackground(Color.WHITE);
				} else {

					if (!socialNumbClient.getText().matches(Regex.REGEX_FalseSecuNumber)) {
						socialNumbClient.setBackground(COLOR_RED);
					} else {
						socialNumbClient.setBackground(COLOR_GREEN);
					}
				}
				checkIfOrderIfValid();
			}
		});

		// --- Création des contraintes pour le Titre client.
		GridBagConstraints gbc_0_0 = new GridBagConstraints();
		gbc_0_0.gridx = 0;
		gbc_0_0.gridy = 0;
		gbc_0_0.gridwidth = 12;
		gbc_0_0.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_0.insets = new Insets(0, 0, 0, 0);

		// --- Création des contraintes pour le JLabel prénom client.
		GridBagConstraints gbc_0_1 = new GridBagConstraints();
		gbc_0_1.gridx = 0;
		gbc_0_1.gridy = 1;
		gbc_0_1.gridwidth = 6;
		gbc_0_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_1.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel nom client.
		GridBagConstraints gbc_1_1 = new GridBagConstraints();
		gbc_1_1.gridx = 7;
		gbc_1_1.gridy = 1;
		gbc_1_1.gridwidth = 6;
		gbc_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_1.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField prénom client.
		GridBagConstraints gbc_0_2 = new GridBagConstraints();
		gbc_0_2.gridx = 0;
		gbc_0_2.gridy = 2;
		gbc_0_2.gridwidth = 6;
		gbc_0_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_2.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField nom client.
		GridBagConstraints gbc_1_2 = new GridBagConstraints();
		gbc_1_2.gridx = 6;
		gbc_1_2.gridy = 2;
		gbc_1_2.gridwidth = 6;
		gbc_1_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_2.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JLabel adresse client.
		GridBagConstraints gbc_0_3 = new GridBagConstraints();
		gbc_0_3.gridx = 0;
		gbc_0_3.gridy = 3;
		gbc_0_3.gridwidth = 12;
		gbc_0_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_3.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField adresse client.
		GridBagConstraints gbc_0_4 = new GridBagConstraints();
		gbc_0_4.gridx = 0;
		gbc_0_4.gridy = 4;
		gbc_0_4.gridwidth = 12;
		gbc_0_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_4.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JLabel code postal client.
		GridBagConstraints gbc_0_5 = new GridBagConstraints();
		gbc_0_5.gridx = 0;
		gbc_0_5.gridy = 5;
		gbc_0_5.gridwidth = 4;
		gbc_0_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_5.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel ville client.
		GridBagConstraints gbc_1_5 = new GridBagConstraints();
		gbc_1_5.gridx = 5;
		gbc_1_5.gridy = 5;
		gbc_1_5.gridwidth = 8;
		gbc_1_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_5.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField code postal client.
		GridBagConstraints gbc_0_6 = new GridBagConstraints();
		gbc_0_6.gridx = 0;
		gbc_0_6.gridy = 6;
		gbc_0_6.gridwidth = 4;
		gbc_0_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_6.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField ville client.
		GridBagConstraints gbc_1_6 = new GridBagConstraints();
		gbc_1_6.gridx = 5;
		gbc_1_6.gridy = 6;
		gbc_1_6.gridwidth = 8;
		gbc_1_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_6.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JLabel téléphone client.
		GridBagConstraints gbc_0_7 = new GridBagConstraints();
		gbc_0_7.gridx = 0;
		gbc_0_7.gridy = 7;
		gbc_0_7.gridwidth = 4;
		gbc_0_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_7.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel email client.
		GridBagConstraints gbc_1_7 = new GridBagConstraints();
		gbc_1_7.gridx = 4;
		gbc_1_7.gridy = 7;
		gbc_1_7.gridwidth = 4;
		gbc_1_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_7.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel numéro de sécurité client.
		GridBagConstraints gbc_2_7 = new GridBagConstraints();
		gbc_2_7.gridx = 8;
		gbc_2_7.gridy = 7;
		gbc_2_7.gridwidth = 4;
		gbc_2_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_2_7.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField téléphone client.
		GridBagConstraints gbc_0_8 = new GridBagConstraints();
		gbc_0_8.gridx = 0;
		gbc_0_8.gridy = 8;
		gbc_0_8.gridwidth = 4;
		gbc_0_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_8.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField email client.
		GridBagConstraints gbc_1_8 = new GridBagConstraints();
		gbc_1_8.gridx = 4;
		gbc_1_8.gridy = 8;
		gbc_1_8.gridwidth = 4;
		gbc_1_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_8.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField numéro de sécurité client.
		GridBagConstraints gbc_2_8 = new GridBagConstraints();
		gbc_2_8.gridx = 8;
		gbc_2_8.gridy = 8;
		gbc_2_8.gridwidth = 4;
		gbc_2_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_2_8.insets = new Insets(0, 10, 10, 0);

		// --- Assemblage des composants graphiques.
		panelOrderClient.add(panelTitle, gbc_0_0);

		panelOrderClient.add(firstNameClientLbl, gbc_0_1);
		panelOrderClient.add(lastNameClientLbl, gbc_1_1);
		panelOrderClient.add(firstNameClient, gbc_0_2);
		panelOrderClient.add(lastNameClient, gbc_1_2);

		panelOrderClient.add(addressClientLbl, gbc_0_3);
		panelOrderClient.add(addressClient, gbc_0_4);

		panelOrderClient.add(postalCodeClientLbl, gbc_0_5);
		panelOrderClient.add(cityClientLbl, gbc_1_5);
		panelOrderClient.add(postalCodeClient, gbc_0_6);
		panelOrderClient.add(cityClient, gbc_1_6);

		panelOrderClient.add(telephoneClientLbl, gbc_0_7);
		panelOrderClient.add(emailClientLbl, gbc_1_7);
		panelOrderClient.add(socialNumbClientLbl, gbc_2_7);
		panelOrderClient.add(telephoneClient, gbc_0_8);
		panelOrderClient.add(emailClient, gbc_1_8);
		panelOrderClient.add(socialNumbClient, gbc_2_8);

		return panelOrderClient;
	}

	/**
	 * Créer et retourne le JPanel contenant le formulaire mutuelle.
	 * 
	 * @return Un JPanel contenant le formulaire mutuelle.
	 */
	private static JPanel inputOrderMutualCreate() {

		// --- Création des composants GridBagLayout Mutuelle.
		panelOrderMutual.setBorder(border);
		panelOrderMutual.setLayout(new GridBagLayout());

		// --- Création du titre GridBagLayout Mutuelle.
		JPanel panelTitle = new JPanel();
		panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.X_AXIS));
		panelTitle.setPreferredSize(new Dimension(640, 64));

		Border dashedBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, App.GREY_COLOR);
		Border emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panelTitle.setBorder(new CompoundBorder(emptyBorder, dashedBorder));

		JPanel titleLeft = new JPanel();
		JPanel titleRight = new JPanel();
		titleLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		titleRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		titleLeft.setPreferredSize(new Dimension(256, 44));
		titleRight.setPreferredSize(new Dimension(256, 44));

		JLabel titleMutualLbl = new JLabel(
				"<html><span style=\"font-size:16px\"><b>Mutuelle client :</b></span></html>");

		ImageIcon iconTrashMutual = new ImageIcon(App.PATH_ICONS_LOW + "delete.png");
		JButton trashingMutual = new JButton(iconTrashMutual);
		trashingMutual.addActionListener(e -> trashMutual());
		trashingMutual.setToolTipText("Effacer la sasie mutuelle");

		ImageIcon iconAddMutual = new ImageIcon(App.PATH_ICONS_LOW + "purchase-order-add.png");
		JButton selectMutual = new JButton(iconAddMutual);
		selectMutual.addActionListener(e -> TableGestion.drawTableBddMutuals());
		selectMutual.setToolTipText("Sélectionner une mutuelle dans la base de données");

		titleLeft.add(titleMutualLbl);
		titleRight.add(selectMutual);
		titleRight.add(trashingMutual);

		panelTitle.add(titleLeft);
		panelTitle.add(titleRight);

		// ---

		nameMutualLbl.setText("<html><b>Nom mutuelle :</b></html>");
		nameMutualLbl.setPreferredSize(new Dimension(512, firstNameClientLbl.getPreferredSize().height));

		nameMutual = new JTextField(20);
		nameMutual.setPreferredSize(new Dimension(512, nameMutual.getPreferredSize().height));
		AbstractDocument nameMutualGet = (AbstractDocument) nameMutual.getDocument();
		nameMutualGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_enterpriseName)) {
					nameMutual.setBackground(COLOR_RED);
				} else {
					nameMutual.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		nameMutual.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (nameMutual.getText().isEmpty()) {
					nameMutual.setBackground(Color.WHITE);
				} else {
					nameMutual.setText(nameMutual.getText().toLowerCase());
					nameMutual.setText(
							Character.toUpperCase(nameMutual.getText().charAt(0)) + nameMutual.getText().substring(1));
				}
				checkIfOrderIfValid();
			}
		});

		addressMutualLbl.setText("<html><b>Adresse :</b></html>");
		addressMutualLbl.setPreferredSize(new Dimension(512, addressClientLbl.getPreferredSize().height));

		addressMutual = new JTextField();
		addressMutual.setPreferredSize(new Dimension(512, addressMutual.getPreferredSize().height));
		AbstractDocument addressMutualGet = (AbstractDocument) addressMutual.getDocument();
		addressMutualGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_addressNumAndStreet)) {
					addressMutual.setBackground(COLOR_RED);
				} else {
					addressMutual.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		addressMutual.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (addressMutual.getText().isEmpty()) {
					addressMutual.setBackground(Color.WHITE);
				}
				checkIfOrderIfValid();
			}
		});

		postalCodeMutualLbl.setText("<html><b>Code Postal :</b></html>");
		postalCodeMutualLbl.setPreferredSize(new Dimension(128, postalCodeClientLbl.getPreferredSize().height));

		cityClientLbl.setText("<html><b>Ville :</b></html>");
		cityClientLbl.setPreferredSize(new Dimension(384, cityClientLbl.getPreferredSize().height));

		postalCodeMutual = new JTextField();
		postalCodeMutual.setPreferredSize(new Dimension(128, postalCodeMutual.getPreferredSize().height));
		AbstractDocument postalCodeMutualGet = (AbstractDocument) postalCodeMutual.getDocument();
		postalCodeMutualGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_postalCodeFr)) {
					postalCodeMutual.setBackground(COLOR_RED);
				} else {
					postalCodeMutual.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		postalCodeMutual.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (postalCodeMutual.getText().isEmpty()) {
					postalCodeMutual.setBackground(Color.WHITE);
				}
				checkIfOrderIfValid();
			}
		});

		cityMutual = new JTextField();
		cityMutual.setPreferredSize(new Dimension(384, cityMutual.getPreferredSize().height));
		AbstractDocument cityMutualGet = (AbstractDocument) cityMutual.getDocument();
		cityMutualGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_cityFr)) {
					cityMutual.setBackground(COLOR_RED);
				} else {
					cityMutual.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		cityMutual.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (cityMutual.getText().isEmpty()) {
					cityMutual.setBackground(Color.WHITE);
				} else {
					cityMutual.setText(cityMutual.getText().toUpperCase());
				}
				checkIfOrderIfValid();
			}
		});

		telephoneMutualLbl.setText("<html><b>Téléphone :</b></html>");
		telephoneMutualLbl.setPreferredSize(new Dimension(172, telephoneMutualLbl.getPreferredSize().height));

		emailMutualLbl.setText("<html><b>Email :</b></html>");
		emailMutualLbl.setPreferredSize(new Dimension(171, emailMutualLbl.getPreferredSize().height));

		rateMutualLbl.setText("<html><b>Taux Remb. :</b></html>");
		rateMutualLbl.setPreferredSize(new Dimension(170, rateMutualLbl.getPreferredSize().height));

		telephoneMutual = new JTextField();
		telephoneMutual.setPreferredSize(new Dimension(172, telephoneMutual.getPreferredSize().height));
		AbstractDocument telephoneMutualGet = (AbstractDocument) telephoneMutual.getDocument();
		telephoneMutualGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_phoneFR)) {
					telephoneMutual.setBackground(COLOR_RED);
				} else {
					telephoneMutual.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		telephoneMutual.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (telephoneMutual.getText().isEmpty()) {
					telephoneMutual.setBackground(Color.WHITE);
				} else {

					if (!telephoneMutual.getText().matches(Regex.REGEX_phoneFR)) {
						telephoneMutual.setBackground(COLOR_RED);
					} else {
						telephoneMutual.setBackground(COLOR_GREEN);
					}
				}
				checkIfOrderIfValid();
			}
		});

		emailMutual = new JTextField();
		emailMutual.setPreferredSize(new Dimension(171, emailMutual.getPreferredSize().height));
		AbstractDocument emailMutualGet = (AbstractDocument) emailMutual.getDocument();
		emailMutualGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_email)) {
					emailMutual.setBackground(COLOR_RED);
				} else {
					emailMutual.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		emailMutual.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (emailMutual.getText().isEmpty()) {
					emailMutual.setBackground(Color.WHITE);
				} else {

					if (!emailMutual.getText().matches(Regex.REGEX_email)) {
						emailMutual.setBackground(COLOR_RED);
					} else {
						emailMutual.setBackground(COLOR_GREEN);
					}
				}
				checkIfOrderIfValid();
			}
		});

		rateMutual = new JTextField();
		rateMutual.setPreferredSize(new Dimension(170, rateMutual.getPreferredSize().height));
		AbstractDocument rateMutualGet = (AbstractDocument) rateMutual.getDocument();
		rateMutualGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_numberRate)) {
					rateMutual.setBackground(COLOR_RED);
				} else {
					rateMutual.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		rateMutual.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (rateMutual.getText().isEmpty()) {
					rateMutual.setBackground(Color.WHITE);
				} else {
					if (!rateMutual.getText().matches(Regex.REGEX_numberRate)) {
						rateMutual.setBackground(COLOR_RED);
					} else {
						rateMutual.setBackground(COLOR_GREEN);
					}
				}
				checkIfOrderIfValid();
			}
		});
		rateMutual.setEditable(false);

		// --- Création des contraintes pour le Titre mutuelle.
		GridBagConstraints gbc_0_0 = new GridBagConstraints();
		gbc_0_0.gridx = 0;
		gbc_0_0.gridy = 0;
		gbc_0_0.gridwidth = 12;
		gbc_0_0.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_0.insets = new Insets(0, 0, 0, 0);

		// --- Création des contraintes pour le JLabel nom mutuelle.
		GridBagConstraints gbc_0_1 = new GridBagConstraints();
		gbc_0_1.gridx = 0;
		gbc_0_1.gridy = 1;
		gbc_0_1.gridwidth = 12;
		gbc_0_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_1.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField nom mutuelle.
		GridBagConstraints gbc_0_2 = new GridBagConstraints();
		gbc_0_2.gridx = 0;
		gbc_0_2.gridy = 2;
		gbc_0_2.gridwidth = 12;
		gbc_0_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_2.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JLabel adresse mutuelle.
		GridBagConstraints gbc_0_3 = new GridBagConstraints();
		gbc_0_3.gridx = 0;
		gbc_0_3.gridy = 3;
		gbc_0_3.gridwidth = 12;
		gbc_0_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_3.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField adresse mutuelle.
		GridBagConstraints gbc_0_4 = new GridBagConstraints();
		gbc_0_4.gridx = 0;
		gbc_0_4.gridy = 4;
		gbc_0_4.gridwidth = 12;
		gbc_0_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_4.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JLabel code postal mutuelle.
		GridBagConstraints gbc_0_5 = new GridBagConstraints();
		gbc_0_5.gridx = 0;
		gbc_0_5.gridy = 5;
		gbc_0_5.gridwidth = 4;
		gbc_0_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_5.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel ville mutuelle.
		GridBagConstraints gbc_1_5 = new GridBagConstraints();
		gbc_1_5.gridx = 5;
		gbc_1_5.gridy = 5;
		gbc_1_5.gridwidth = 8;
		gbc_1_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_5.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField code postal mutuelle.
		GridBagConstraints gbc_0_6 = new GridBagConstraints();
		gbc_0_6.gridx = 0;
		gbc_0_6.gridy = 6;
		gbc_0_6.gridwidth = 4;
		gbc_0_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_6.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField ville mutuelle.
		GridBagConstraints gbc_1_6 = new GridBagConstraints();
		gbc_1_6.gridx = 5;
		gbc_1_6.gridy = 6;
		gbc_1_6.gridwidth = 8;
		gbc_1_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_6.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JLabel téléphone mutuelle.
		GridBagConstraints gbc_0_7 = new GridBagConstraints();
		gbc_0_7.gridx = 0;
		gbc_0_7.gridy = 7;
		gbc_0_7.gridwidth = 4;
		gbc_0_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_7.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel email mutuelle.
		GridBagConstraints gbc_1_7 = new GridBagConstraints();
		gbc_1_7.gridx = 4;
		gbc_1_7.gridy = 7;
		gbc_1_7.gridwidth = 4;
		gbc_1_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_7.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel numéro de sécurité mutuelle.
		GridBagConstraints gbc_2_7 = new GridBagConstraints();
		gbc_2_7.gridx = 8;
		gbc_2_7.gridy = 7;
		gbc_2_7.gridwidth = 4;
		gbc_2_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_2_7.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField téléphone mutuelle.
		GridBagConstraints gbc_0_8 = new GridBagConstraints();
		gbc_0_8.gridx = 0;
		gbc_0_8.gridy = 8;
		gbc_0_8.gridwidth = 4;
		gbc_0_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_8.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField email mutuelle.
		GridBagConstraints gbc_1_8 = new GridBagConstraints();
		gbc_1_8.gridx = 4;
		gbc_1_8.gridy = 8;
		gbc_1_8.gridwidth = 4;
		gbc_1_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_8.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField taux remboursement mutuelle.
		GridBagConstraints gbc_2_8 = new GridBagConstraints();
		gbc_2_8.gridx = 8;
		gbc_2_8.gridy = 8;
		gbc_2_8.gridwidth = 4;
		gbc_2_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_2_8.insets = new Insets(0, 10, 10, 0);

		// --- Assemblage des composants graphiques.
		panelOrderMutual.add(panelTitle, gbc_0_0);

		panelOrderMutual.add(nameMutualLbl, gbc_0_1);
		panelOrderMutual.add(nameMutual, gbc_0_2);

		panelOrderMutual.add(addressMutualLbl, gbc_0_3);
		panelOrderMutual.add(addressMutual, gbc_0_4);

		panelOrderMutual.add(postalCodeMutualLbl, gbc_0_5);
		panelOrderMutual.add(cityMutualLbl, gbc_1_5);
		panelOrderMutual.add(postalCodeMutual, gbc_0_6);
		panelOrderMutual.add(cityMutual, gbc_1_6);

		panelOrderMutual.add(telephoneMutualLbl, gbc_0_7);
		panelOrderMutual.add(emailMutualLbl, gbc_1_7);
		panelOrderMutual.add(rateMutualLbl, gbc_2_7);
		panelOrderMutual.add(telephoneMutual, gbc_0_8);
		panelOrderMutual.add(emailMutual, gbc_1_8);
		panelOrderMutual.add(rateMutual, gbc_2_8);

		return panelOrderMutual;
	}

	/**
	 * Créer et retourne le JPanel contenant le formulaire médecin.
	 * 
	 * @return Un JPanel contenant le formulaire médecin.
	 */
	private static JPanel inputOrderDoctorCreate() {

		// --- Création des composants GridBagLayout Client.

		panelOrderDoctor.setBorder(border);
		panelOrderDoctor.setLayout(new GridBagLayout());

		// --- Création du titre GridBagLayout Client.
		JPanel panelTitle = new JPanel();
		panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.X_AXIS));
		panelTitle.setPreferredSize(new Dimension(512, 64));

		Border dashedBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, App.GREY_COLOR);
		Border emptyBorder = new EmptyBorder(10, 10, 10, 10);
		panelTitle.setBorder(new CompoundBorder(emptyBorder, dashedBorder));

		JPanel titleLeft = new JPanel();
		JPanel titleRight = new JPanel();
		titleLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		titleRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		titleLeft.setPreferredSize(new Dimension(256, 44));
		titleRight.setPreferredSize(new Dimension(256, 44));

		JLabel titleDoctorLbl = new JLabel(
				"<html><span style=\"font-size:16px\"><b>Informations médecin traitant :</b></span></html>");

		ImageIcon iconTrashDoctor = new ImageIcon(App.PATH_ICONS_LOW + "delete.png");
		JButton trashingDoctor = new JButton(iconTrashDoctor);
		trashingDoctor.addActionListener(e -> trashDoctor());
		trashingDoctor.setToolTipText("Effacer la sasie client");

		ImageIcon iconAddDoctor = new ImageIcon(App.PATH_ICONS_LOW + "doctor.png");
		JButton selectDoctor = new JButton(iconAddDoctor);
		selectDoctor.setToolTipText("Sélectionner un client dans la base de données");
		selectDoctor.addActionListener(e -> TableGestion.drawTableBddDoctors());

		titleLeft.add(titleDoctorLbl);
		titleRight.add(selectDoctor);
		titleRight.add(trashingDoctor);

		panelTitle.add(titleLeft);
		panelTitle.add(titleRight);

		// ---

		firstNameDoctorLbl.setText("<html><b>Prénom :</b></html>");
		firstNameDoctorLbl.setPreferredSize(new Dimension(256, firstNameDoctorLbl.getPreferredSize().height));

		lastNameDoctorLbl.setText("<html><b>Nom :</b></html>");
		lastNameDoctorLbl.setPreferredSize(new Dimension(256, lastNameDoctorLbl.getPreferredSize().height));

		firstNameDoctor = new JTextField(20);
		firstNameDoctor.setPreferredSize(new Dimension(256, firstNameDoctor.getPreferredSize().height));
		AbstractDocument firstNameDoctorGet = (AbstractDocument) firstNameDoctor.getDocument();
		firstNameDoctorGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_firstAndLastName)) {
					firstNameDoctor.setBackground(COLOR_RED);
				} else {
					firstNameDoctor.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		firstNameDoctor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (firstNameDoctor.getText().isEmpty()) {
					firstNameDoctor.setBackground(Color.WHITE);
				} else {
					firstNameDoctor.setText(firstNameDoctor.getText().toLowerCase());
					firstNameDoctor.setText(Character.toUpperCase(firstNameDoctor.getText().charAt(0))
							+ firstNameDoctor.getText().substring(1));
				}
			}
		});

		lastNameDoctor = new JTextField();
		lastNameDoctor.setPreferredSize(new Dimension(256, lastNameDoctor.getPreferredSize().height));
		AbstractDocument lastNameDoctorGet = (AbstractDocument) lastNameDoctor.getDocument();
		lastNameDoctorGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_firstAndLastName)) {
					lastNameDoctor.setBackground(COLOR_RED);
				} else {
					lastNameDoctor.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		lastNameDoctor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (lastNameDoctor.getText().isEmpty()) {
					lastNameDoctor.setBackground(Color.WHITE);
				} else {
					lastNameDoctor.setText(lastNameDoctor.getText().toUpperCase());
				}
				checkIfOrderIfValid();
			}
		});

		addressDoctorLbl.setText("<html><b>Adresse :</b></html>");
		addressDoctorLbl.setPreferredSize(new Dimension(512, addressDoctorLbl.getPreferredSize().height));

		addressDoctor = new JTextField();
		addressDoctor.setPreferredSize(new Dimension(512, addressDoctor.getPreferredSize().height));
		AbstractDocument firstAddressDoctorGet = (AbstractDocument) addressDoctor.getDocument();
		firstAddressDoctorGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_addressNumAndStreet)) {
					addressDoctor.setBackground(COLOR_RED);
				} else {
					addressDoctor.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		addressDoctor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (addressDoctor.getText().isEmpty()) {
					addressDoctor.setBackground(Color.WHITE);
				}
				checkIfOrderIfValid();
			}
		});

		postalCodeDoctorLbl.setText("<html><b>Code Postal :</b></html>");
		postalCodeDoctorLbl.setPreferredSize(new Dimension(128, postalCodeDoctorLbl.getPreferredSize().height));

		cityDoctorLbl.setText("<html><b>Ville :</b></html>");
		cityDoctorLbl.setPreferredSize(new Dimension(384, cityDoctorLbl.getPreferredSize().height));

		postalCodeDoctor = new JTextField();
		postalCodeDoctor.setPreferredSize(new Dimension(128, postalCodeDoctor.getPreferredSize().height));
		AbstractDocument postalCodeDoctorGet = (AbstractDocument) postalCodeDoctor.getDocument();
		postalCodeDoctorGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_postalCodeFr)) {
					postalCodeDoctor.setBackground(COLOR_RED);
				} else {
					postalCodeDoctor.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		postalCodeDoctor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (postalCodeDoctor.getText().isEmpty()) {
					postalCodeDoctor.setBackground(Color.WHITE);
				}
				checkIfOrderIfValid();
			}
		});

		cityDoctor = new JTextField();
		cityDoctor.setPreferredSize(new Dimension(384, cityDoctor.getPreferredSize().height));
		AbstractDocument cityDoctorGet = (AbstractDocument) cityDoctor.getDocument();
		cityDoctorGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_cityFr)) {
					cityDoctor.setBackground(COLOR_RED);
				} else {
					cityDoctor.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		cityDoctor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (cityDoctor.getText().isEmpty()) {
					cityDoctor.setBackground(Color.WHITE);
				} else {
					cityDoctor.setText(cityDoctor.getText().toUpperCase());
				}
				checkIfOrderIfValid();
			}
		});

		telephoneDoctorLbl.setText("<html><b>Téléphone :</b></html>");
		telephoneDoctorLbl.setPreferredSize(new Dimension(172, telephoneDoctorLbl.getPreferredSize().height));

		emailDoctorLbl.setText("<html><b>Email :</b></html>");
		emailDoctorLbl.setPreferredSize(new Dimension(171, emailDoctorLbl.getPreferredSize().height));

		rrpsDoctorLbl.setText("<html><b>Num. RRPS :</b></html>");
		rrpsDoctorLbl.setPreferredSize(new Dimension(170, rrpsDoctorLbl.getPreferredSize().height));

		telephoneDoctor = new JTextField();
		telephoneDoctor.setPreferredSize(new Dimension(172, telephoneDoctor.getPreferredSize().height));
		AbstractDocument telephoneDoctorGet = (AbstractDocument) telephoneDoctor.getDocument();
		telephoneDoctorGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_phoneFR)) {
					telephoneDoctor.setBackground(COLOR_RED);
				} else {
					telephoneDoctor.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		telephoneDoctor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (telephoneDoctor.getText().isEmpty()) {
					telephoneDoctor.setBackground(Color.WHITE);
				} else {

					if (!telephoneDoctor.getText().matches(Regex.REGEX_phoneFR)) {
						telephoneDoctor.setBackground(COLOR_RED);
					} else {
						telephoneDoctor.setBackground(COLOR_GREEN);
					}
				}
				checkIfOrderIfValid();
			}
		});

		emailDoctor = new JTextField();
		emailDoctor.setPreferredSize(new Dimension(171, emailDoctor.getPreferredSize().height));
		AbstractDocument emailDoctorGet = (AbstractDocument) emailDoctor.getDocument();
		emailDoctorGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_email)) {
					emailDoctor.setBackground(COLOR_RED);
				} else {
					emailDoctor.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		emailDoctor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (emailDoctor.getText().isEmpty()) {
					emailDoctor.setBackground(Color.WHITE);
				} else {

					if (!emailDoctor.getText().matches(Regex.REGEX_email)) {
						emailDoctor.setBackground(COLOR_RED);
					} else {
						emailDoctor.setBackground(COLOR_GREEN);
					}
				}
				checkIfOrderIfValid();
			}
		});

		rrpsDoctor = new JTextField();
		rrpsDoctor.setPreferredSize(new Dimension(170, rrpsDoctor.getPreferredSize().height));
		AbstractDocument rrpsDoctorGet = (AbstractDocument) rrpsDoctor.getDocument();
		rrpsDoctorGet.setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
				String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

				if (!newText.matches(Regex.REGEX_FalseSecuNumber)) {
					rrpsDoctor.setBackground(COLOR_RED);
				} else {
					rrpsDoctor.setBackground(COLOR_GREEN);
				}

				super.replace(fb, offset, length, text, attrs);
				checkIfOrderIfValid();
			}
		});
		rrpsDoctor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (rrpsDoctor.getText().isEmpty()) {
					rrpsDoctor.setBackground(Color.WHITE);
				} else {

					if (!rrpsDoctor.getText().matches(Regex.REGEX_FalseSecuNumber)) {
						rrpsDoctor.setBackground(COLOR_RED);
					} else {
						rrpsDoctor.setBackground(COLOR_GREEN);
					}
				}
				checkIfOrderIfValid();
			}
		});

		// --- Création des contraintes pour le Titre médecin.
		GridBagConstraints gbc_0_0 = new GridBagConstraints();
		gbc_0_0.gridx = 0;
		gbc_0_0.gridy = 0;
		gbc_0_0.gridwidth = 12;
		gbc_0_0.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_0.insets = new Insets(0, 0, 0, 0);

		// --- Création des contraintes pour le JLabel prénom médecin.
		GridBagConstraints gbc_0_1 = new GridBagConstraints();
		gbc_0_1.gridx = 0;
		gbc_0_1.gridy = 1;
		gbc_0_1.gridwidth = 6;
		gbc_0_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_1.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel nom médecin.
		GridBagConstraints gbc_1_1 = new GridBagConstraints();
		gbc_1_1.gridx = 7;
		gbc_1_1.gridy = 1;
		gbc_1_1.gridwidth = 6;
		gbc_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_1.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField prénom médecin.
		GridBagConstraints gbc_0_2 = new GridBagConstraints();
		gbc_0_2.gridx = 0;
		gbc_0_2.gridy = 2;
		gbc_0_2.gridwidth = 6;
		gbc_0_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_2.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField nom médecin.
		GridBagConstraints gbc_1_2 = new GridBagConstraints();
		gbc_1_2.gridx = 6;
		gbc_1_2.gridy = 2;
		gbc_1_2.gridwidth = 6;
		gbc_1_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_2.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JLabel adresse médecin.
		GridBagConstraints gbc_0_3 = new GridBagConstraints();
		gbc_0_3.gridx = 0;
		gbc_0_3.gridy = 3;
		gbc_0_3.gridwidth = 12;
		gbc_0_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_3.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField adresse médecin.
		GridBagConstraints gbc_0_4 = new GridBagConstraints();
		gbc_0_4.gridx = 0;
		gbc_0_4.gridy = 4;
		gbc_0_4.gridwidth = 12;
		gbc_0_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_4.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JLabel code postal médecin.
		GridBagConstraints gbc_0_5 = new GridBagConstraints();
		gbc_0_5.gridx = 0;
		gbc_0_5.gridy = 5;
		gbc_0_5.gridwidth = 4;
		gbc_0_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_5.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel ville médecin.
		GridBagConstraints gbc_1_5 = new GridBagConstraints();
		gbc_1_5.gridx = 5;
		gbc_1_5.gridy = 5;
		gbc_1_5.gridwidth = 8;
		gbc_1_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_5.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField code postal médecin.
		GridBagConstraints gbc_0_6 = new GridBagConstraints();
		gbc_0_6.gridx = 0;
		gbc_0_6.gridy = 6;
		gbc_0_6.gridwidth = 4;
		gbc_0_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_6.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField ville médecin.
		GridBagConstraints gbc_1_6 = new GridBagConstraints();
		gbc_1_6.gridx = 5;
		gbc_1_6.gridy = 6;
		gbc_1_6.gridwidth = 8;
		gbc_1_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_6.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JLabel téléphone médecin.
		GridBagConstraints gbc_0_7 = new GridBagConstraints();
		gbc_0_7.gridx = 0;
		gbc_0_7.gridy = 7;
		gbc_0_7.gridwidth = 4;
		gbc_0_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_7.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel email médecin.
		GridBagConstraints gbc_1_7 = new GridBagConstraints();
		gbc_1_7.gridx = 4;
		gbc_1_7.gridy = 7;
		gbc_1_7.gridwidth = 4;
		gbc_1_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_7.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JLabel numéro de sécurité médecin.
		GridBagConstraints gbc_2_7 = new GridBagConstraints();
		gbc_2_7.gridx = 8;
		gbc_2_7.gridy = 7;
		gbc_2_7.gridwidth = 4;
		gbc_2_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_2_7.insets = new Insets(4, 16, 4, 16);

		// --- Création des contraintes pour le JTextField téléphone médecin.
		GridBagConstraints gbc_0_8 = new GridBagConstraints();
		gbc_0_8.gridx = 0;
		gbc_0_8.gridy = 8;
		gbc_0_8.gridwidth = 4;
		gbc_0_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_0_8.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField email médecin.
		GridBagConstraints gbc_1_8 = new GridBagConstraints();
		gbc_1_8.gridx = 4;
		gbc_1_8.gridy = 8;
		gbc_1_8.gridwidth = 4;
		gbc_1_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_1_8.insets = new Insets(0, 10, 10, 0);

		// --- Création des contraintes pour le JTextField numéro de sécurité médecin.
		GridBagConstraints gbc_2_8 = new GridBagConstraints();
		gbc_2_8.gridx = 8;
		gbc_2_8.gridy = 8;
		gbc_2_8.gridwidth = 4;
		gbc_2_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_2_8.insets = new Insets(0, 10, 10, 0);

		// --- Assemblage des composants graphiques.
		panelOrderDoctor.add(panelTitle, gbc_0_0);

		panelOrderDoctor.add(firstNameDoctorLbl, gbc_0_1);
		panelOrderDoctor.add(lastNameDoctorLbl, gbc_1_1);
		panelOrderDoctor.add(firstNameDoctor, gbc_0_2);
		panelOrderDoctor.add(lastNameDoctor, gbc_1_2);

		panelOrderDoctor.add(addressDoctorLbl, gbc_0_3);
		panelOrderDoctor.add(addressDoctor, gbc_0_4);

		panelOrderDoctor.add(postalCodeDoctorLbl, gbc_0_5);
		panelOrderDoctor.add(cityDoctorLbl, gbc_1_5);
		panelOrderDoctor.add(postalCodeDoctor, gbc_0_6);
		panelOrderDoctor.add(cityDoctor, gbc_1_6);

		panelOrderDoctor.add(telephoneDoctorLbl, gbc_0_7);
		panelOrderDoctor.add(emailDoctorLbl, gbc_1_7);
		panelOrderDoctor.add(rrpsDoctorLbl, gbc_2_7);
		panelOrderDoctor.add(telephoneDoctor, gbc_0_8);
		panelOrderDoctor.add(emailDoctor, gbc_1_8);
		panelOrderDoctor.add(rrpsDoctor, gbc_2_8);

		return panelOrderDoctor;
	}

	/**
	 * Créer et retourne le JPanel contenant la JTable des médicaments dont dispose
	 * la pharmacie.
	 * 
	 * @return Un JPanel contenant la JTable des médicaments dont dispose la
	 *         pharmacie.
	 */
	private static JPanel prescriptionListCreate() {

		// --- Vérification de l'existence de la base de données (fichier).
		String filePathDataBase = App.PATH_DATABASE_DRUGS_FILE;
		App.verifRessources(filePathDataBase, true);

		// --- Paramètrage spécifique.
		AtomicReference<String> imageIconBdd = new AtomicReference<>(App.PATH_ICONS_LOW + "");
		imageIconBdd.set(App.PATH_ICONS_LOW + "order-history.png");

		// --- Création de la table virtualisée.
		ArrayList<String> data = FileTextUtilities.readFileText(filePathDataBase, "Drugs");

		ArrayList<ElementsDrug> elements = new ArrayList<>();
		for (String e : data) {
			ElementsDrug convertedElement = StringToElementsConverter.stringToElementsDrug(e);
			elements.add(convertedElement);
		}
		VirtualTableBddDrug dbbTable = new VirtualTableBddDrug(elements);
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

						}
					}
				}
				// --- Action via double click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {

							// --- Récupération de la ligne.
							int columnCount = table.getColumnCount();
							String[] newColumn = new String[columnCount];

							for (int i = 0; i < columnCount; i++) {

								if (i < columnCount - 1) {
									newColumn[i] = table.getValueAt(selectedRow, i).toString();
								} else {
									newColumn[i] = "1";
								}

							}

							// --- Recherche d'une ligne correspondante.
							int columnCountNew = tablePrescriptionChoice.getColumnCount();
							int rowCountNew = tablePrescriptionChoice.getRowCount();
							// String[] newColumn = new String[columnCount];

							int i = 0;
							boolean exist = false;

							while (i < rowCountNew) {

								if (tablePrescriptionChoice.getValueAt(i, 0).toString()
										.equals(table.getValueAt(selectedRow, 0).toString())) {

									tablePrescriptionChoiceModel
											.setValueAt(
													Integer.parseInt(tablePrescriptionChoice
															.getValueAt(i, columnCountNew - 2).toString()) + 1,
													i, columnCountNew - 2);

									Double result = Double
											.parseDouble(tablePrescriptionChoice.getValueAt(i, columnCountNew - 4)
													.toString().replace(" €", ""))
											* Double.parseDouble(tablePrescriptionChoice
													.getValueAt(i, columnCountNew - 2).toString());
									tablePrescriptionChoiceModel.setValueAt(
											decimalFormat.format(result).toString() + " €", i, columnCountNew - 1);
									exist = true;
									break;
								}
								i++;
							}

							if (!exist) {
								tablePrescriptionChoiceModel.addRow(newColumn);
								Double result = Double
										.parseDouble(tablePrescriptionChoice.getValueAt(i, columnCountNew - 4)
												.toString().replace(" €", ""))
										* Double.parseDouble(
												tablePrescriptionChoice.getValueAt(i, columnCountNew - 2).toString());
								tablePrescriptionChoiceModel.setValueAt(decimalFormat.format(result).toString() + " €",
										i, columnCountNew - 1);
							}

							drawInfoOrder();
							checkIfOrderIfValid();
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
		searchField.addActionListener(e -> TableGestion.searchInTableBdd(table, searchField.getText()));

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
		toolBar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		toolBar.setBorder(new EmptyBorder(0, 12, 0, 0));

		// --- Assemblage des composants graphique.
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(toolBar, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		return mainPanel;

	}

	/**
	 * Créer et retourne le JPanel contenant la JTable du panier achat.
	 * 
	 * @return Un JPanel contenant la JTable du panier achat.
	 */
	private static JPanel prescriptionChoiceCreate() {

		JPanel mainPanel = new JPanel(new BorderLayout());

		tablePrescriptionChoiceModel = new DefaultTableModel(0, 6) {
			private static final long serialVersionUID = -6267156103542900963L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		String[] columnNames = { "Id. Médicament", "Nom", "Catégorie", "Prix", "Date de mise en service", "Quantité",
				"Prix total" };

		tablePrescriptionChoiceModel.setColumnIdentifiers(columnNames);
		tablePrescriptionChoice = new JTable(tablePrescriptionChoiceModel);
		// tablePrescriptionChoice.setAutoCreateRowSorter(true);

		JScrollPane scrollPane = new JScrollPane(tablePrescriptionChoice);

		// --- Ajustement de la largeur des colonnes
		for (int column = 0; column < tablePrescriptionChoice.getColumnCount(); column++) {
			TableColumn tableColumn = tablePrescriptionChoice.getColumnModel().getColumn(column);
			int preferredWidth = 0;

			for (int row = 0; row < tablePrescriptionChoice.getRowCount(); row++) {
				TableCellRenderer cellRenderer = tablePrescriptionChoice.getCellRenderer(row, column);
				Component c = tablePrescriptionChoice.prepareRenderer(cellRenderer, row, column);
				preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
			}

			tableColumn.setPreferredWidth((int) Math.floor((double) preferredWidth * 0.6));
		}

		// --- Action sur la table via click de la souris.
		tablePrescriptionChoice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// --- Action via click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = tablePrescriptionChoice.getSelectedRow();
						if (selectedRow != -1) {

						}
					}
				}
				// --- Action via double click gauche de la souris.
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int selectedRow = tablePrescriptionChoice.getSelectedRow();
						if (selectedRow != -1) {

						}
					}
				}
				// --- Action via click droit de la souris.
				if (SwingUtilities.isRightMouseButton(e)) {
					if (e.getClickCount() == 1) {
						int selectedRow = tablePrescriptionChoice.getSelectedRow();
						if (selectedRow != -1) {
							System.out.println(DateGestion.getTimeForConsole() + " JTable (action via click droit)");
							int row = tablePrescriptionChoice.rowAtPoint(e.getPoint());

							int columnCount = tablePrescriptionChoice.getColumnCount();

							if (row >= 0 && row < tablePrescriptionChoice.getRowCount()) {

								if (Integer.parseInt(
										tablePrescriptionChoice.getValueAt(row, columnCount - 2).toString()) == 1) {
									tablePrescriptionChoiceModel.removeRow(row);
								} else {
									tablePrescriptionChoiceModel
											.setValueAt(
													Integer.parseInt(tablePrescriptionChoice
															.getValueAt(row, columnCount - 2).toString()) - 1,
													row, columnCount - 2);

									Double result = Double
											.parseDouble(tablePrescriptionChoice.getValueAt(row, columnCount - 4)
													.toString().replace(" €", ""))
											* Double.parseDouble(tablePrescriptionChoice
													.getValueAt(row, columnCount - 2).toString());
									tablePrescriptionChoiceModel.setValueAt(
											decimalFormat.format(result).toString() + " €", row, columnCount - 1);
								}
							}

							drawInfoOrder();
							checkIfOrderIfValid();
						}
					}
				}
			}
		});

		// --- Création de la toolBar.
		JLabel prescriptionZone = new JLabel(
				"<html><span style=\"font-size:11px\"><b>Liste des produits : </b></span></html>");
		JToolBar toolBar = new JToolBar();
		toolBar.add(prescriptionZone);
		toolBar.setFloatable(false);
		toolBar.setBorder(new EmptyBorder(0, 12, 0, 0));

		// --- Création et ajout du footer.

		// footerPanel.setLayout();

		// --- Création du footerRightPanel.
		JPanel footerLeftPanel = new JPanel();
		footerLeftPanel.setLayout(new BoxLayout(footerLeftPanel, BoxLayout.X_AXIS));
		footerLeftPanel.setBorder(new EmptyBorder(0, 12, 0, 0));

		footerLeftPanel.add(totalDrugsLbl);
		footerLeftPanel.add(Box.createRigidArea(new Dimension(64, 0)));
		footerLeftPanel.add(totalPriceLbl);

		// --- Création du footerRightPanel.
		JPanel footerRightPanel = new JPanel();
		footerRightPanel.setBorder(new EmptyBorder(0, 0, 0, 12));

		Font courierFont = new Font("Courier", Font.PLAIN, 12);
		totalDrugsLbl.setFont(courierFont);
		totalPriceLbl.setFont(courierFont);

		ImageIcon iconValid = new ImageIcon(App.PATH_ICONS_LOW + "check.png");
		btnValidOrder = new JButton(iconValid);
		btnValidOrder.setText("<html><b>Valider l'achat</b></html>");
		btnValidOrder.setToolTipText("<html><b>Valider l'achat</b></html>");
		btnValidOrder.addActionListener(e -> addPurchase());
		btnValidOrder.setBorderPainted(false);
		btnValidOrder.setEnabled(false);

		comboBoxPaiementType = new JComboBox<>(optionsComboBoxPaiementType);
		comboBoxPaiementType.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				switch (comboBoxPaiementType.getSelectedIndex()) {

				case 0:
					paiementType = -1;
					checkIfOrderIfValid();
					break;

				case 1:
					paiementType = 0;
					checkIfOrderIfValid();
					break;

				case 2:
					paiementType = 1;
					checkIfOrderIfValid();
					break;

				}
			}
		});
		footerRightPanel.add(comboBoxPaiementType);
		footerRightPanel.add(btnValidOrder);

		// --- Assemblage des divers composants.
		JPanel footerPanel = new JPanel(new BorderLayout());
		footerPanel.add(footerLeftPanel, BorderLayout.WEST);
		footerPanel.add(footerRightPanel, BorderLayout.EAST);

		mainPanel.add(toolBar, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(footerPanel, BorderLayout.SOUTH);

		return mainPanel;

	}

	/**
	 * Dessine à l'écran le nombre de produits présents dans le panier ainsi que le
	 * prix total.
	 */
	public static void drawInfoOrder() {

		Double totalPrice = 0.0;
		int totalDrugs = 0;

		int columnCount = tablePrescriptionChoice.getColumnCount();
		int rowCount = tablePrescriptionChoice.getRowCount();

		if (rowCount > 0) {
			for (int i = 0; i < rowCount; i++) {

				totalDrugs += Integer.parseInt(tablePrescriptionChoice.getValueAt(i, columnCount - 2).toString());
				totalPrice += Double.parseDouble(tablePrescriptionChoice.getValueAt(i, columnCount - 1).toString()
						.replace(" €", "").replace(",", "."));
			}

			totalDrugsLbl.setText(
					"<html><span style=\"font-size:12px\"><b>Nb. produits : " + totalDrugs + "</b></span></html>");

			// Si l'achat est sur ordonnance, sinon.
			if (PurchaseOnPrescription == 1) {

				totalPriceGlobal = totalPrice - (totalPrice * (Double.parseDouble(rateMutual.getText()) / 100.00));
				totalPriceLbl.setText("<html><span style=\"font-size:12px\"><b>Total : "
						+ decimalFormat
								.format(totalPrice - (totalPrice * (Double.parseDouble(rateMutual.getText()) / 100.00)))
						+ " € (-" + rateMutual.getText() + "%)</b></span></html>");
			} else {
				totalPriceGlobal = totalPrice;
				totalPriceLbl.setText("<html><span style=\"font-size:12px\"><b>Total : "
						+ decimalFormat.format(totalPrice) + " €</b></span></html>");
			}
		}
		checkIfOrderIfValid();
	}

	/**
	 * Efface l'ensemble des saisies (client, mutuelle, médecin).
	 */
	public static void trashAllWithQestion() {

		ImageIcon iconQuestion = new ImageIcon(App.ICON_QUESTION);
		int clickedQuestion = JOptionPane.showConfirmDialog(null,
				"Vous êtes sur le point d'effacer toutes les saisies achat.\n\n Souhaitez-vous continuer ?", "Question",
				JOptionPane.YES_NO_OPTION, 1, iconQuestion);

		if (clickedQuestion == JOptionPane.YES_OPTION) {
			trashClient();
			trashMutual();
			trashDoctor();

			DefaultComboBoxModel<String> modelComboBoxPaiementType = (DefaultComboBoxModel<String>) comboBoxPaiementType
					.getModel();
			comboBoxPaiementType.setSelectedItem(modelComboBoxPaiementType.getElementAt(0));

			DefaultComboBoxModel<String> modelcomboBoxOrderType = (DefaultComboBoxModel<String>) App.comboBoxOrderType
					.getModel();
			App.comboBoxOrderType.setSelectedItem(modelcomboBoxOrderType.getElementAt(0));

			totalDrugsLbl.setText("<html><span style=\"font-size:12px\"><b>Nb. produits : " + 0 + "</b></span></html>");

			totalPriceLbl.setText("<html><span style=\"font-size:12px\"><b>Total : " + decimalFormat.format(0.00)
					+ " €</b></span></html>");

			tablePrescriptionChoiceModel.setRowCount(0);
		}

	}

	/**
	 * Efface l'ensemble des saisies (client, mutuelle, médecin).
	 */
	public static void trashAllWithoutQestion() {

		trashClient();
		trashMutual();
		trashDoctor();

		DefaultComboBoxModel<String> modelComboBoxPaiementType = (DefaultComboBoxModel<String>) comboBoxPaiementType
				.getModel();
		comboBoxPaiementType.setSelectedItem(modelComboBoxPaiementType.getElementAt(0));

		DefaultComboBoxModel<String> modelcomboBoxOrderType = (DefaultComboBoxModel<String>) App.comboBoxOrderType
				.getModel();
		App.comboBoxOrderType.setSelectedItem(modelcomboBoxOrderType.getElementAt(0));

		totalDrugsLbl.setText("<html><span style=\"font-size:12px\"><b>Nb. produits : " + 0 + "</b></span></html>");

		totalPriceLbl.setText("<html><span style=\"font-size:12px\"><b>Total : " + decimalFormat.format(0.00)
				+ " €</b></span></html>");

		tablePrescriptionChoiceModel.setRowCount(0);

	}

	/**
	 * Efface les saisies du client.
	 */
	private static void trashClient() {

		firstNameClient.setText("");
		lastNameClient.setText("");
		addressClient.setText("");
		postalCodeClient.setText("");
		cityClient.setText("");
		telephoneClient.setText("");
		emailClient.setText("");
		socialNumbClient.setText("");

	}

	/**
	 * Efface les saisies de la mutuelle.
	 */
	private static void trashMutual() {

		nameMutual.setText("");
		addressMutual.setText("");
		postalCodeMutual.setText("");
		cityMutual.setText("");
		telephoneMutual.setText("");
		emailMutual.setText("");
		rateMutual.setText("");

	}

	/**
	 * Efface les saisies du médecin.
	 */
	private static void trashDoctor() {

		firstNameDoctor.setText("");
		lastNameDoctor.setText("");
		addressDoctor.setText("");
		postalCodeDoctor.setText("");
		cityDoctor.setText("");
		telephoneDoctor.setText("");
		emailDoctor.setText("");
		rrpsDoctor.setText("");

	}

	/**
	 * Vérification de l'intégralité des informations d'achat. Permet d'activer
	 * (enabled = true) le boutton "validation" afin de confirmer l'achat.
	 * 
	 * @return une valeur booléenne
	 */
	public static boolean checkIfOrderIfValid() {

		boolean OrderIsValid = true;

		// --- Relatifs au formulaire client.
		if (!firstNameClient.getText().matches(Regex.REGEX_firstAndLastName)) {
			OrderIsValid = false;
		}

		if (!lastNameClient.getText().matches(Regex.REGEX_firstAndLastName)) {
			OrderIsValid = false;
		}

		if (!addressClient.getText().matches(Regex.REGEX_addressNumAndStreet)) {
			OrderIsValid = false;
		}

		if (!postalCodeClient.getText().matches(Regex.REGEX_postalCodeFr)) {
			OrderIsValid = false;
		}

		if (!cityClient.getText().matches(Regex.REGEX_cityFr)) {
			OrderIsValid = false;
		}

		if (!telephoneClient.getText().matches(Regex.REGEX_phoneFR)) {
			OrderIsValid = false;
		}

		if (!emailClient.getText().matches(Regex.REGEX_email)) {
			OrderIsValid = false;
		}

		if (!socialNumbClient.getText().matches(Regex.REGEX_FalseSecuNumber)) {
			OrderIsValid = false;
		}

		// Si l'achat est sur ordonnance.
		if (PurchaseOnPrescription == 1) {

			// --- Relatifs à la saisie mutuelle.
			if (!nameMutual.getText().matches(Regex.REGEX_enterpriseName)) {
				OrderIsValid = false;
			}

			if (!addressMutual.getText().matches(Regex.REGEX_addressNumAndStreet)) {
				OrderIsValid = false;
			}

			if (!postalCodeMutual.getText().matches(Regex.REGEX_postalCodeFr)) {
				OrderIsValid = false;
			}

			if (!cityMutual.getText().matches(Regex.REGEX_cityFr)) {
				OrderIsValid = false;
			}

			if (!telephoneMutual.getText().matches(Regex.REGEX_phoneFR)) {
				OrderIsValid = false;
			}

			if (!emailMutual.getText().matches(Regex.REGEX_email)) {
				OrderIsValid = false;
			}

			if (!rateMutual.getText().matches(Regex.REGEX_numberRate)) {
				OrderIsValid = false;
			}

			// --- Realtifs à la saisie médecins.
			if (!firstNameDoctor.getText().matches(Regex.REGEX_firstAndLastName)) {
				OrderIsValid = false;
			}

			if (!lastNameDoctor.getText().matches(Regex.REGEX_firstAndLastName)) {
				OrderIsValid = false;
			}

			if (!addressDoctor.getText().matches(Regex.REGEX_addressNumAndStreet)) {
				OrderIsValid = false;
			}

			if (!postalCodeDoctor.getText().matches(Regex.REGEX_postalCodeFr)) {
				OrderIsValid = false;
			}

			if (!cityDoctor.getText().matches(Regex.REGEX_cityFr)) {
				OrderIsValid = false;
			}

			if (!telephoneDoctor.getText().matches(Regex.REGEX_phoneFR)) {
				OrderIsValid = false;
			}

			if (!emailDoctor.getText().matches(Regex.REGEX_email)) {
				OrderIsValid = false;
			}

			if (!rrpsDoctor.getText().matches(Regex.REGEX_FalseSecuNumber)) {
				OrderIsValid = false;
			}
		}

		// --- Vérification du panier d'achat.
		if (tablePrescriptionChoice.getRowCount() == 0) {
			OrderIsValid = false;
		}

		// --- Vérification du type de réglement.
		if (paiementType == -1) {
			OrderIsValid = false;
		}

		// --- Si l'ensemble des informations saisies sont correctes
		// --- et que le panier contient des médicament à acheter,
		// --- le boutton de validation d'achat est activé.
		if (OrderIsValid) {

			btnValidOrder.setEnabled(true);

		} else {

			btnValidOrder.setEnabled(false);

		}

		return OrderIsValid;

	}

	/*
	 * Finalisation : ajouter l'achat à la base de données.
	 */
	public static void addPurchase() {

		// ---------- Création de la ligne de la base données historique des achats.
		StringBuilder strPurchaseToBdd = new StringBuilder();

		// --- Id achat.
		strPurchaseToBdd
				.append((FileTextUtilities.dataBaseCountLine(App.PATH_DATABASE_PURCHASE_HISTORY_FILE) + 1) + ";");

		// --- Date achat.
		//String newDate = DateGestion.getTime() + " - " + DateGestion.getDate();
		
		String newDate = String.valueOf(System.currentTimeMillis());
		
		strPurchaseToBdd.append(newDate + ";");

		// --- Id Client.
		strPurchaseToBdd.append(App.informationsClientOrderSelected.get(0)[0] + ";");

		// --- Création de la liste des médicaments.
		StringBuilder drugsList = new StringBuilder();
		int nbRow = tablePrescriptionChoice.getRowCount();

		drugsList.append("{");
		for (int i = 0; i < nbRow; i++) {
			if (i < nbRow - 1) {
				drugsList.append(tablePrescriptionChoice.getValueAt(i, 0).toString() + ",");
			} else {
				drugsList.append(tablePrescriptionChoice.getValueAt(i, 0).toString());
			}
		}
		drugsList.append("}");
		strPurchaseToBdd.append(drugsList.toString() + ";");
		strPurchaseToBdd.append(decimalFormat.format(totalPriceGlobal).toString().replace(",", ".") + ";");
		strPurchaseToBdd.append(paiementType + ";");
		strPurchaseToBdd.append(PurchaseOnPrescription + ";");

		// --- Si l'achat est sur prescription.
		int idPrescription = -1;
		if (PurchaseOnPrescription == 1) {
			idPrescription = (FileTextUtilities.dataBaseCountLine(App.PATH_DATABASE_PRESCRIPTIONS_FILE) + 1);
			strPurchaseToBdd.append(idPrescription + ";");
		} else {
			strPurchaseToBdd.append("empty" + ";");
		}

		// --- Nom de l'utilisateur qui enregistre l'achat.
		strPurchaseToBdd.append(Character.toUpperCase(App.userName.charAt(0)) + App.userName.substring(1));

		// --- écriture la nouvelle ligne dans la base de données "Historiques des
		// achats".
		System.out.println(DateGestion.getTimeForConsole()+ "Enregistrement dans la bdd \"purchase-history\" : [" + strPurchaseToBdd.toString()+"]");
		FileTextUtilities.writeFile(App.PATH_DATABASE_PURCHASE_HISTORY_FILE, strPurchaseToBdd.toString());

		// ---------- Création de la ligne de la base données historique prescriptions.
		if (PurchaseOnPrescription == 1) {

			StringBuilder strPrescriptionToBdd = new StringBuilder();

			// --- Id prescription.
			strPrescriptionToBdd.append(idPrescription + ";");

			// --- Date de l'enregistrement de la prescription.
			strPrescriptionToBdd.append(newDate + ";");

			// --- Id Client.
			strPrescriptionToBdd.append(App.informationsClientOrderSelected.get(0)[0] + ";");

			// --- Id Doctor.
			strPrescriptionToBdd.append(App.informationsClientOrderSelected.get(2)[0] + ";");

			// --- Liste des médicaments de la prescription.
			strPrescriptionToBdd.append(drugsList + ";");

			// --- Dans le cas où l'ordonnance est préscrite par un médecin spécialiste.
			strPrescriptionToBdd.append("empty;");

			// --- Nom de l'utilisateur qui enregistre l'achat.
			strPrescriptionToBdd.append(Character.toUpperCase(App.userName.charAt(0)) + App.userName.substring(1));

			// --- Écriture la nouvelle ligne dans la base de données "Historiques des
			// ordonnances".
			System.out.println(DateGestion.getTimeForConsole()+ "Enregistrement dans la bdd \"prescritption\" : [" + strPrescriptionToBdd.toString()+"]");
			FileTextUtilities.writeFile(App.PATH_DATABASE_PRESCRIPTIONS_FILE, strPrescriptionToBdd.toString());
		}

		// --- Rafraîchissement de la table "Historique des achats".
		ArrayList<String> dataPurchaseHistory = FileTextUtilities.readFileText(App.PATH_DATABASE_PURCHASE_HISTORY_FILE,
				"PurshaseHistory");

		ArrayList<ElementsPurchaseHistory> elementsPurchaseHistory = new ArrayList<>();
		for (String e : dataPurchaseHistory) {
			ElementsPurchaseHistory convertedElement = StringToElementsConverter.stringToElementsPurchaseHistory(e);
			elementsPurchaseHistory.add(convertedElement);
		}
		VirtualJTabbleBdd.dbbTablePurchaseHistoryModel.updateData(elementsPurchaseHistory);

		// --- Rafraîchissement de la table "Historique des ordonnances".
		ArrayList<String> dataPrescription = FileTextUtilities.readFileText(App.PATH_DATABASE_PRESCRIPTIONS_FILE,
				"Prescritptions");

		ArrayList<ElementsPrescription> elementsPrescription = new ArrayList<>();
		for (String e : dataPrescription) {
			ElementsPrescription convertedElement = StringToElementsConverter.stringToElementsPrescription(e);
			elementsPrescription.add(convertedElement);
		}
		VirtualJTabbleBdd.dbbTablePrescriptionModel.updateData(elementsPrescription);

		// --- Finalisation.
		trashAllWithoutQestion();
		App.switchSectionNorth();
		JOptionPane.showMessageDialog(null, "L'achat a bien été enregistré dans la base de données !", "Message system",
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(App.ICON_VALID));

	}

}
