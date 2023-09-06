package fr.luzi.utilities.graphics;

/**
 * Cette classe met en place les JPanel relatifs aux détails présent dans la
 * séction nord-est de l'application (détails achats, détails ordonnance,
 * détails clients, détails médecin)
 * 
 * @author Randy LUZI
 * 
 */

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.luzi.application.App;

public class DetailsPanel {

	private static int PADDING_VALUE = 128;
	private static String EMPTY_INPUT = "Empty";
	
	// --- Relatifs aux achats.
	public static JPanel PANEL_ORDER_DETAILS = new JPanel();
	public static JLabel typeDetails;
	public static String typeDetailsText = "<html>" + App.HTML_SPAN_STYLE_TITLE_BOLD + "Détails achat</span></html>";

	public static JLabel indexOrder;
	public static String indexOrderText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Id. achat : </span></html>";
	public static JLabel indexOrderValue;

	public static JLabel dateOrder;
	public static String dateOrderText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Date d'achat : </span></html>";
	public static JLabel dateOrderValue;

	public static JLabel clientOrder;
	public static String clientOrderText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Client : </span></html>";
	public static JLabel clientOrderValue;

	public static JLabel priceOrder;
	public static String priceOrderText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Montant total : </span></html>";
	public static JLabel priceOrderValue;

	public static JLabel paymentTypeOrder;
	public static String paymentTypeOrderText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Règlement : </span></html>";
	public static JLabel paymentTypeOrderValue;

	public static JLabel typeOrder;
	public static String typeOrderText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Type : </span></html>";
	public static JLabel typeOrderValue;

	public static JLabel drugsOrder;
	public static String drugsOrderText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Médicaments : </span></html>";
	public static JLabel drugsOrderValue;

	public static JPanel panelDrugsOrder;

	// --- Relatifs aux ordonnances.
	public static JPanel PANEL_PRESCRIPTION_DETAILS = new JPanel();
	public static JLabel typePrescriptionDetails;
	public static String typePrescriptionDetailsText = "<html>" + App.HTML_SPAN_STYLE_TITLE_BOLD
			+ "Détails ordonnances</span></html>";

	public static JLabel indexPrescription;
	public static String indexPrescriptionText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Id. ordonnances : </span></html>";
	public static JLabel indexPrescriptionValue;

	public static JLabel datePrescription;
	public static String datePrescriptionText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Date ordonnances : </span></html>";
	public static JLabel datePrescriptionValue;

	public static JLabel clientPrescription;
	public static String clientPrescriptionText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Client : </span></html>";
	public static JLabel clientPrescriptionValue;

	public static JLabel doctorPrescription;
	public static String doctorPrescriptionText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Médecin prescripteur : </span></html>";
	public static JLabel doctorPrescriptionValue;

	public static JLabel specialistPrescription;
	public static String specialistPrescriptionText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Spécialiste : </span></html>";
	public static JLabel specialistPrescriptionValue;

	public static JLabel drugsPrescription;
	public static String drugsPrescriptionText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Médicaments : </span></html>";
	public static JLabel drugsPrescriptionValue;

	public static JPanel paneldDrugsPrescription;

	// --- Relatifs aux clients.
	public static JPanel PANEL_CLIENT_DETAILS = new JPanel();
	public static JLabel typeClientDetails;
	public static String typeClientDetailsText = "<html>" + App.HTML_SPAN_STYLE_TITLE_BOLD
			+ "Détails clients</span></html>";

	public static JLabel indexClient;
	public static String indexClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Id. Client : </span></html>";
	public static JLabel indexClientValue;

	public static JLabel lastNameClient;
	public static String lastNameClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Nom : </span></html>";
	public static JLabel lastNameClientValue;

	public static JLabel firstNameClient;
	public static String firstNameClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Prénom : </span></html>";
	public static JLabel firstNameClientValue;

	public static JLabel addressClient;
	public static String addressClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Adresse : </span></html>";
	public static JLabel addressClientValue;

	public static JLabel phoneClient;
	public static String phoneClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Téléphone : </span></html>";
	public static JLabel phoneClientValue;

	public static JLabel mailClient;
	public static String mailClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Email : </span></html>";
	public static JLabel mailClientValue;

	public static JLabel socialSecuNumberClient;
	public static String socialSecuNumberClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Num. sécurité sociale : </span></html>";
	public static JLabel socialSecuNumberClientValue;

	public static JLabel mutualClient;
	public static String mutualClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Mutuelle : </span></html>";
	public static JLabel mutualClientValue;

	public static JLabel doctorClient;
	public static String doctorClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Médecin traitant : </span></html>";
	public static JLabel doctorClientValue;

	public static JLabel specialistClient;
	public static String specialistClientText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Spécialiste : </span></html>";
	public static JLabel specialistClientValue;

	// --- Relatifs aux médecins.
	public static JPanel PANEL_DOCTOR_DETAILS = new JPanel();
	public static JLabel typeDoctorDetails;
	public static String typeDoctorDetailsText = "<html>" + App.HTML_SPAN_STYLE_TITLE_BOLD
			+ "Détails Médecin</span></html>";

	public static JLabel indexDoctor;
	public static String indexDoctorText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Id. Doctor : </span></html>";
	public static JLabel indexDoctorValue;

	public static JLabel lastNameDoctor;
	public static String lastNameDoctorText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Nom : </span></html>";
	public static JLabel lastNameDoctorValue;

	public static JLabel firstNameDoctor;
	public static String firstNameDoctorText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Prénom : </span></html>";
	public static JLabel firstNameDoctorValue;

	public static JLabel addressDoctor;
	public static String addressDoctorText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Adresse : </span></html>";
	public static JLabel addressDoctorValue;

	public static JLabel phoneDoctor;
	public static String phoneDoctorText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Téléphone : </span></html>";
	public static JLabel phoneDoctorValue;

	public static JLabel mailDoctor;
	public static String mailDoctorText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Email : </span></html>";
	public static JLabel mailDoctorValue;

	public static JLabel numRRPSDoctor;
	public static String numRRPSDoctorText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD + "Num. RRPS : </span></html>";
	public static JLabel numRRPSDoctorValue;

	public static JLabel nbPatientDoctor;
	public static String nbPatientDoctorText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Nb. Patients : </span></html>";
	public static JLabel nbPatientDoctorValue;

	public static JLabel specialityDoctor;
	public static String specialityDoctorText = "<html>" + App.HTML_SPAN_STYLE_TEXT_BOLD
			+ "Spécialité : </span></html>";
	public static JLabel specialityDoctorValue;

	/**
	 * Cette méthode met en place le JPanel relatifs aux détails achats.
	 */
	public static void setPanelOrderDetails() {

		PANEL_ORDER_DETAILS.setLayout(new BoxLayout(PANEL_ORDER_DETAILS, BoxLayout.Y_AXIS));
		// PANEL_ORDER_DETAILS.setBackground(ConvertBase.hexToColor("F0F0F0"));
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");

		typeDetails = new JLabel(typeDetailsText);
		typeDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
		typeDetails.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));

		indexOrder = new JLabel(indexOrderText);
		indexOrder.setAlignmentX(Component.LEFT_ALIGNMENT);
		indexOrder.setBorder(BorderFactory.createEmptyBorder(16, 8, 0, 8));
		indexOrderValue = new JLabel(EMPTY_INPUT);
		indexOrderValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		indexOrderValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		dateOrder = new JLabel(dateOrderText);
		dateOrder.setAlignmentX(Component.LEFT_ALIGNMENT);
		dateOrder.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		dateOrderValue = new JLabel(EMPTY_INPUT);
		dateOrderValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		dateOrderValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		clientOrder = new JLabel(clientOrderText + clientOrderValue);
		clientOrder.setAlignmentX(Component.LEFT_ALIGNMENT);
		clientOrder.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		clientOrderValue = new JLabel(EMPTY_INPUT);
		clientOrderValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		clientOrderValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));
		
		priceOrder = new JLabel(priceOrderText);
		priceOrder.setAlignmentX(Component.LEFT_ALIGNMENT);
		priceOrder.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		priceOrderValue = new JLabel(EMPTY_INPUT);
		priceOrderValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		priceOrderValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		paymentTypeOrder = new JLabel(paymentTypeOrderText);
		paymentTypeOrder.setAlignmentX(Component.LEFT_ALIGNMENT);
		paymentTypeOrder.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		paymentTypeOrderValue = new JLabel(EMPTY_INPUT);
		paymentTypeOrderValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		paymentTypeOrderValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		typeOrder = new JLabel(typeOrderText);
		typeOrder.setAlignmentX(Component.LEFT_ALIGNMENT);
		typeOrder.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		typeOrderValue = new JLabel(EMPTY_INPUT);
		typeOrderValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		typeOrderValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		drugsOrder = new JLabel(drugsOrderText);
		drugsOrder.setAlignmentX(Component.LEFT_ALIGNMENT);
		drugsOrder.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));

		panelDrugsOrder = new JPanel();
		panelDrugsOrder.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelDrugsOrder.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		panelDrugsOrder.setLayout(new BorderLayout());

		// --- Assemblage des composant graphiques du JPanel.
		PANEL_ORDER_DETAILS.add(typeDetails);

		PANEL_ORDER_DETAILS.add(indexOrder);
		PANEL_ORDER_DETAILS.add(indexOrderValue);
		PANEL_ORDER_DETAILS.add(dateOrder);
		PANEL_ORDER_DETAILS.add(dateOrderValue);
		PANEL_ORDER_DETAILS.add(clientOrder);
		PANEL_ORDER_DETAILS.add(clientOrderValue);
		PANEL_ORDER_DETAILS.add(typeOrder);
		PANEL_ORDER_DETAILS.add(typeOrderValue);
		PANEL_ORDER_DETAILS.add(priceOrder);
		PANEL_ORDER_DETAILS.add(priceOrderValue);

		PANEL_ORDER_DETAILS.add(drugsOrder);
		PANEL_ORDER_DETAILS.add(panelDrugsOrder, BorderLayout.CENTER);

		PANEL_ORDER_DETAILS.repaint();
		PANEL_ORDER_DETAILS.revalidate();

	}
	
	/**
	 * Cette méthode met en place le JPanel relatifs aux détails ordonnance.
	 */
	public static void setPanelPrescriptionDetails() {

		PANEL_PRESCRIPTION_DETAILS.setLayout(new BoxLayout(PANEL_PRESCRIPTION_DETAILS, BoxLayout.Y_AXIS));
		// panelDetails.setBackground(ConvertBase.hexToColor("d6d6d6"));
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");

		typePrescriptionDetails = new JLabel(typePrescriptionDetailsText);
		typePrescriptionDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
		typePrescriptionDetails.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));

		indexPrescription = new JLabel(indexPrescriptionText);
		indexPrescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		indexPrescription.setBorder(BorderFactory.createEmptyBorder(16, 8, 0, 8));
		indexPrescriptionValue = new JLabel(EMPTY_INPUT);
		indexPrescriptionValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		indexPrescriptionValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		datePrescription = new JLabel(datePrescriptionText);
		datePrescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		datePrescription.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		datePrescriptionValue = new JLabel(EMPTY_INPUT);
		datePrescriptionValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		datePrescriptionValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		clientPrescription = new JLabel(clientPrescriptionText);
		clientPrescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		clientPrescription.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		clientPrescriptionValue = new JLabel(EMPTY_INPUT);
		clientPrescriptionValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		clientPrescriptionValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		doctorPrescription = new JLabel(doctorPrescriptionText);
		doctorPrescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		doctorPrescription.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		doctorPrescriptionValue = new JLabel(EMPTY_INPUT);
		doctorPrescriptionValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		doctorPrescriptionValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		specialistPrescription = new JLabel(specialistPrescriptionText);
		specialistPrescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		specialistPrescription.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		specialistPrescriptionValue = new JLabel(EMPTY_INPUT);
		specialistPrescriptionValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		specialistPrescriptionValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		drugsPrescription = new JLabel(drugsPrescriptionText);
		drugsPrescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		drugsPrescription.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));

		paneldDrugsPrescription = new JPanel();
		paneldDrugsPrescription.setAlignmentX(Component.LEFT_ALIGNMENT);
		paneldDrugsPrescription.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		paneldDrugsPrescription.setLayout(new BorderLayout());

		// --- Assemblage des composant graphiques du JPanel.
		PANEL_PRESCRIPTION_DETAILS.add(typePrescriptionDetails);

		PANEL_PRESCRIPTION_DETAILS.add(indexPrescription);
		PANEL_PRESCRIPTION_DETAILS.add(indexPrescriptionValue);
		PANEL_PRESCRIPTION_DETAILS.add(datePrescription);
		PANEL_PRESCRIPTION_DETAILS.add(datePrescriptionValue);
		PANEL_PRESCRIPTION_DETAILS.add(clientPrescription);
		PANEL_PRESCRIPTION_DETAILS.add(clientPrescriptionValue);
		PANEL_PRESCRIPTION_DETAILS.add(doctorPrescription);
		PANEL_PRESCRIPTION_DETAILS.add(doctorPrescriptionValue);
		PANEL_PRESCRIPTION_DETAILS.add(specialistPrescription);
		PANEL_PRESCRIPTION_DETAILS.add(specialistPrescriptionValue);

		PANEL_PRESCRIPTION_DETAILS.add(drugsPrescription);
		PANEL_PRESCRIPTION_DETAILS.add(paneldDrugsPrescription, BorderLayout.CENTER);

		PANEL_PRESCRIPTION_DETAILS.repaint();
		PANEL_PRESCRIPTION_DETAILS.revalidate();

	}
	
	/**
	 * Cette méthode met en place le JPanel relatifs aux détails clients.
	 */
	public static void setPanelClientDetails() {

		PANEL_CLIENT_DETAILS.setLayout(new BoxLayout(PANEL_CLIENT_DETAILS, BoxLayout.Y_AXIS));
		// panelDetails.setBackground(ConvertBase.hexToColor("d6d6d6"));
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");

		typeClientDetails = new JLabel(typeClientDetailsText);
		typeClientDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
		typeClientDetails.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));

		indexClient = new JLabel(indexClientText);
		indexClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		indexClient.setBorder(BorderFactory.createEmptyBorder(16, 8, 0, 8));
		indexClientValue = new JLabel(EMPTY_INPUT);
		indexClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		indexClientValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		lastNameClient = new JLabel(lastNameClientText);
		lastNameClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		lastNameClient.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		lastNameClientValue = new JLabel(EMPTY_INPUT);
		lastNameClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		lastNameClientValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		firstNameClient = new JLabel(firstNameClientText);
		firstNameClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		firstNameClient.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		firstNameClientValue = new JLabel(EMPTY_INPUT);
		firstNameClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		firstNameClientValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		addressClient = new JLabel(addressClientText);
		addressClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		addressClient.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		addressClientValue = new JLabel(EMPTY_INPUT);
		addressClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		addressClientValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		phoneClient = new JLabel(phoneClientText);
		phoneClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		phoneClient.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		phoneClientValue = new JLabel(EMPTY_INPUT);
		phoneClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		phoneClientValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		mailClient = new JLabel(mailClientText);
		mailClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		mailClient.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		mailClientValue = new JLabel(EMPTY_INPUT);
		mailClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		mailClientValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		socialSecuNumberClient = new JLabel(socialSecuNumberClientText);
		socialSecuNumberClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		socialSecuNumberClient.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		socialSecuNumberClientValue = new JLabel(EMPTY_INPUT);
		socialSecuNumberClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		socialSecuNumberClientValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		mutualClient = new JLabel(mutualClientText);
		mutualClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		mutualClient.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		mutualClientValue = new JLabel(EMPTY_INPUT);
		mutualClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		mutualClientValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		doctorClient = new JLabel(doctorClientText);
		doctorClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		doctorClient.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		doctorClientValue = new JLabel(EMPTY_INPUT);
		doctorClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		doctorClientValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		specialistClient = new JLabel(specialistClientText);
		specialistClient.setAlignmentX(Component.LEFT_ALIGNMENT);
		specialistClient.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		specialistClientValue = new JLabel(EMPTY_INPUT);
		specialistClientValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		specialistClientValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		// --- Assemblage des composant graphiques du JPanel.
		PANEL_CLIENT_DETAILS.add(typeClientDetails);

		PANEL_CLIENT_DETAILS.add(indexClient);
		PANEL_CLIENT_DETAILS.add(indexClientValue);
		PANEL_CLIENT_DETAILS.add(lastNameClient);
		PANEL_CLIENT_DETAILS.add(lastNameClientValue);
		PANEL_CLIENT_DETAILS.add(firstNameClient);
		PANEL_CLIENT_DETAILS.add(firstNameClientValue);
		PANEL_CLIENT_DETAILS.add(addressClient);
		PANEL_CLIENT_DETAILS.add(addressClientValue);
		PANEL_CLIENT_DETAILS.add(phoneClient);
		PANEL_CLIENT_DETAILS.add(phoneClientValue);
		PANEL_CLIENT_DETAILS.add(mailClient);
		PANEL_CLIENT_DETAILS.add(mailClientValue);
		PANEL_CLIENT_DETAILS.add(socialSecuNumberClient);
		PANEL_CLIENT_DETAILS.add(socialSecuNumberClientValue);
		PANEL_CLIENT_DETAILS.add(mutualClient);
		PANEL_CLIENT_DETAILS.add(mutualClientValue);
		PANEL_CLIENT_DETAILS.add(doctorClient);
		PANEL_CLIENT_DETAILS.add(doctorClientValue);
		PANEL_CLIENT_DETAILS.add(specialistClient);
		PANEL_CLIENT_DETAILS.add(specialistClientValue);

		PANEL_CLIENT_DETAILS.repaint();
		PANEL_CLIENT_DETAILS.revalidate();

	}
	
	/**
	 * Cette méthode met en place le JPanel relatifs aux détails médecin.
	 */
	public static void setPanelDoctorDetails() {

		PANEL_DOCTOR_DETAILS.setLayout(new BoxLayout(PANEL_DOCTOR_DETAILS, BoxLayout.Y_AXIS));
		// panelDetails.setBackground(ConvertBase.hexToColor("d6d6d6"));
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");

		typeDoctorDetails = new JLabel(typeDoctorDetailsText);
		typeDoctorDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
		typeDoctorDetails.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));

		indexDoctor = new JLabel(indexDoctorText);
		indexDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);
		indexDoctor.setBorder(BorderFactory.createEmptyBorder(16, 8, 0, 8));
		indexDoctorValue = new JLabel(EMPTY_INPUT);
		indexDoctorValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		indexDoctorValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		lastNameDoctor = new JLabel(lastNameDoctorText);
		lastNameDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);
		lastNameDoctor.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		lastNameDoctorValue = new JLabel(EMPTY_INPUT);
		lastNameDoctorValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		lastNameDoctorValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		firstNameDoctor = new JLabel(firstNameDoctorText);
		firstNameDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);
		firstNameDoctor.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		firstNameDoctorValue = new JLabel(EMPTY_INPUT);
		firstNameDoctorValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		firstNameDoctorValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		addressDoctor = new JLabel(addressDoctorText);
		addressDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);
		addressDoctor.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		addressDoctorValue = new JLabel(EMPTY_INPUT);
		addressDoctorValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		addressDoctorValue.setBorder(BorderFactory.createEmptyBorder(0, PADDING_VALUE, 0, 8));

		phoneDoctor = new JLabel(phoneDoctorText);
		phoneDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);
		phoneDoctor.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		phoneDoctorValue = new JLabel(EMPTY_INPUT);
		phoneDoctorValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		phoneDoctorValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		mailDoctor = new JLabel(mailDoctorText);
		mailDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);
		mailDoctor.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		mailDoctorValue = new JLabel(EMPTY_INPUT);
		mailDoctorValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		mailDoctorValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		numRRPSDoctor = new JLabel(numRRPSDoctorText);
		numRRPSDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);
		numRRPSDoctor.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		numRRPSDoctorValue = new JLabel(EMPTY_INPUT);
		numRRPSDoctorValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		numRRPSDoctorValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		nbPatientDoctor = new JLabel(nbPatientDoctorText);
		nbPatientDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);
		nbPatientDoctor.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		nbPatientDoctorValue = new JLabel(EMPTY_INPUT);
		nbPatientDoctorValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		nbPatientDoctorValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		specialityDoctor = new JLabel(specialityDoctorText);
		specialityDoctor.setAlignmentX(Component.LEFT_ALIGNMENT);
		specialityDoctor.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		specialityDoctorValue = new JLabel(EMPTY_INPUT);
		specialityDoctorValue.setAlignmentX(Component.LEFT_ALIGNMENT);
		specialityDoctorValue.setBorder(BorderFactory.createEmptyBorder(4, PADDING_VALUE, 0, 8));

		// --- Assemblage des composant graphiques du JPanel.
		PANEL_DOCTOR_DETAILS.add(typeDoctorDetails);

		PANEL_DOCTOR_DETAILS.add(indexDoctor);
		PANEL_DOCTOR_DETAILS.add(indexDoctorValue);
		PANEL_DOCTOR_DETAILS.add(lastNameDoctor);
		PANEL_DOCTOR_DETAILS.add(lastNameDoctorValue);
		PANEL_DOCTOR_DETAILS.add(firstNameDoctor);
		PANEL_DOCTOR_DETAILS.add(firstNameDoctorValue);
		PANEL_DOCTOR_DETAILS.add(addressDoctor);
		PANEL_DOCTOR_DETAILS.add(addressDoctorValue);
		PANEL_DOCTOR_DETAILS.add(phoneDoctor);
		PANEL_DOCTOR_DETAILS.add(phoneDoctorValue);
		PANEL_DOCTOR_DETAILS.add(mailDoctor);
		PANEL_DOCTOR_DETAILS.add(mailDoctorValue);
		PANEL_DOCTOR_DETAILS.add(numRRPSDoctor);
		PANEL_DOCTOR_DETAILS.add(numRRPSDoctorValue);
		PANEL_DOCTOR_DETAILS.add(nbPatientDoctor);
		PANEL_DOCTOR_DETAILS.add(nbPatientDoctorValue);
		PANEL_DOCTOR_DETAILS.add(specialityDoctor);
		PANEL_DOCTOR_DETAILS.add(specialityDoctorValue);

		PANEL_DOCTOR_DETAILS.repaint();
		PANEL_DOCTOR_DETAILS.revalidate();

	}

}
