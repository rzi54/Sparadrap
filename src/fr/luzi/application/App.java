package fr.luzi.application;

/** 
 * 
 * Prototype d'application développée dans le cadre du premier examen (ECF).
 * Type : Gestionnaire.
 * Déroulement de l'examen : du 29/08/2023 au 06/09/2023.
 * Titre visé : Concepteur Développeur d'Applications.
 * Formateur CDA : Jérôme BOEBION
 * Centre de formation : AFPA - Centre de Pompey, 56 Sq. Eugène Herzog, 54390 Frouard
 * 
 * @language JAVA
 * @author Randy LUZI
 * @mail randy.luzi@gmail.com
 * @license MIT
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import fr.luzi.defense.FileCipher;
import fr.luzi.defense.Regex;
import fr.luzi.utilities.DateGestion;
import fr.luzi.utilities.FileTextUtilities;
import fr.luzi.utilities.PopupMenuGestion;
import fr.luzi.utilities.dirfilestree.DirFilesTree;
import fr.luzi.utilities.graphics.CustomJtree;
import fr.luzi.utilities.graphics.DetailsPanel;
import fr.luzi.utilities.graphics.InputOrder;
import fr.luzi.utilities.jtable.TableGestion;
import fr.luzi.utilities.jtable.VirtualJTabbleBdd;
import fr.luzi.utilities.math.ConvertBase;

public class App extends JFrame {

	/*
	 * Initialisation de l'application, déclaration des constantes et des variables
	 * utilisées au cours de l'application.
	 */
	private static final long serialVersionUID = 1L;

	// ----------------- Relatifs à l'application.
	public static App THIS_WINDOW;
	static final String CURRENT_DIR = Paths.get("").toAbsolutePath().toString();
	private static final String APP_NAME = "Gestionnaire Sparadrap";
	private static final String TITLE_CONNEXION = APP_NAME + " !";
	private static final String TITLE_APP = "Bienvenue dans " + APP_NAME + " !";
	private static final String ICON_APP = "ressources/data/app/icon_app.png";

	public static final String PATH_ICONS_LOW = "ressources/data/app/icons/windows11/";
	public static final String PATH_ICONS_HIGH = "ressources/data/app/icons/48x48/";
	public static final String PATH_ICONS_LANG = "ressources/data/app/icons/lang/";
	public static final String PATH_ICONS_JTREE = "ressources/data/app/icons/jtree/";
	public static final String ICON_QUESTION = PATH_ICONS_HIGH + "question_squarre.png";
	public static final String ICON_ERROR = PATH_ICONS_HIGH + "error.png";
	public static final String ICON_VALID = PATH_ICONS_HIGH + "valid.png";
	private static final String MESSAGE_QUIT = "Êtes-vous sûr de vouloir quitter l'application ?";
	private static final String MESSAGE_QUIT_TITLE = "Quitter";

	// --- Chemin du fichier chiffré contenant les informations
	// utilisateurs (id & hash mot-de-passe).
	public static final String PATH_DATA_USER_CONNEXION_FILE = "ressources/user/data-user-crypted.txt";

	// --- Relatif au log d'erreurs.
	public static final String PATH_DATA_LOG_ERRORS = "ressources/data/dev/errors/errors-logs.txt";

	// --- Chemins des fichiers BDD.
	public static final String PATH_DATABASE_CIS_CIP_FILE = "ressources/bdd/CIS_CIP_bdpm.dbb";

	public static final String PATH_DATABASE_PURCHASE_HISTORY_FILE = "ressources/bdd/purchase-history.dbb";
	public static final String PATH_DATABASE_PRESCRIPTIONS_FILE = "ressources/bdd/prescriptions.dbb";
	public static final String PATH_DATABASE_DRUGS_FILE = "ressources/bdd/drugs.dbb";
	public static final String PATH_DATABASE_CLIENTS_FILE = "ressources/bdd/clients.dbb";
	public static final String PATH_DATABASE_DOCTORS_FILE = "ressources/bdd/doctors.dbb";
	public static final String PATH_DATABASE_MUTUALS_FILE = "ressources/bdd/mutuals.dbb";

	// --- Obtention de la taille de l'écran Windows (OS).
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int SCREEN_WIDTH = (int) screenSize.getWidth();
	private final int SCREEN_HEIGHT = (int) screenSize.getHeight();

	// --- Relatifs à l'apparence de la fenêtre.
	private static boolean changeGlobalTheme = false; // A changer pour tests d'apparence...

	// --- Relatif aux containers de fond et certains composants.
	private static final Color COLOR_DARK_BACKGROUND = ConvertBase.hexToColor("7A7A7A");
	private static final Color COLOR_DARK_BACKGROUND_TEXT = ConvertBase.hexToColor("FFFFFF");
	private static final List<Class<? extends JComponent>> TARGET_COMPONENTS = Arrays.asList(JPanel.class,
			JButton.class, JLabel.class, JToolBar.class, JCheckBox.class);

	// --- Relatif aux contours et certains composants.
	private static final Color COLOR_DARK_BACKGROUND_OUTLINE = ConvertBase.hexToColor("4D4D4D");
	private static final Color COLOR_DARK_BACKGROUND_OUTLINE_TEXT = ConvertBase.hexToColor("FFFFFF");
	private static final List<Class<? extends JComponent>> TARGET_COMPONENTS_OUTLINE = Arrays.asList(JSplitPane.class,
			JTabbedPane.class, JScrollPane.class);

	// --- Relatif aux composants de saisie.
	private static final Color COLOR_DARK_BACKGROUND_FIELD = ConvertBase.hexToColor("4D4D4D");
	private static final Color COLOR_DARK_BACKGROUND_FIELD_TEXT = ConvertBase.hexToColor("FFFFFF");
	private static final List<Class<? extends JComponent>> TARGET_COMPONENTS_FIELD = Arrays.asList(JTextField.class,
			JTextArea.class, JTree.class, JPasswordField.class);

	public static final Color GREY_COLOR = ConvertBase.hexToColor("A9A9A9");
	public static final Color RED_COLOR = ConvertBase.hexToColor("9E0000");

	// --- Relatifs à la fenêtre de connexion.
	private final int USER_CONNEXION_WIDTH = 288;
	private final int USER_CONNEXION_HEIGHT = 288;

	private static JLabel lblUserName;
	private static JTextField userInputId;
	private static JLabel lblUserPassword;
	private static JPasswordField userInputPassword;
	private static JLabel lblUserNameReturn;
	private static JPanel JPlblUserNameReturn;
	private static String userIdNotValid;

	public static String userName;
	public static int userSexe;
	public static String userGrade;
	private static String userTimeConnexion;
	private static String userDateConnexion;

	// --- Relatifs à la fenêtre de l'application.
	private static JPanel contentPaneApplication;
	private static boolean userEntered;

	private static JPanel contentNorthSectionAndOrderSection;
	private static JPanel orderSection;
	private static JSplitPane topSection;
	private static JTabbedPane leftSection;

	// --- Relatifs aux arborescences.
	private final static String JTREE1_DIR_FOCUSED = "entreprise";
	private final static String JTREE2_DIR_FOCUSED = "magasin";

	public final static ImageIcon ICON_JTREE1_ROOT = new ImageIcon(PATH_ICONS_JTREE + "enterprise.png");
	public final static ImageIcon ICON_JTREE1_NODE = new ImageIcon(PATH_ICONS_JTREE + "job.png");
	public final static ImageIcon ICON_JTREE1_LEAF = new ImageIcon(PATH_ICONS_JTREE + "person.png");

	public final static ImageIcon ICON_JTREE2_ROOT = new ImageIcon(PATH_ICONS_JTREE + "enterprise.png");
	public final static ImageIcon ICON_JTREE2_NODE = new ImageIcon(PATH_ICONS_JTREE + "warehouse.png");
	public final static ImageIcon ICON_JTREE2_LEAF = new ImageIcon(PATH_ICONS_JTREE + "box.png");

	public final static ImageIcon BACKGROUND_IMG_NEW_JFRAME = new ImageIcon("ressources/data/app/bg/bg.jpg");

	// --- Relatifs à la saisie dans la section achat.
	public static JComboBox<String> comboBoxOrderType;

	public static ArrayList<String[]> informationsClientOrderSelected = new ArrayList<String[]>(3);
	public static String[] infoArrayClientOrderSelected = new String[12];
	public static String[] infoArrayMutualOrderSelected = new String[9];
	public static String[] infoArrayDoctorOrderSelected = new String[11];

	private static void assembleInformationsClientOrder() {

		informationsClientOrderSelected.add(infoArrayClientOrderSelected);
		informationsClientOrderSelected.add(infoArrayMutualOrderSelected);
		informationsClientOrderSelected.add(infoArrayDoctorOrderSelected);

	}

	// --- Relatif aux détails des tables (rightSection).
	public static JPanel JTABBED_DETAILS = new JPanel();

	// --- Relatifs à la base de données sud.
	public static int bdd1NbOfElement;
	public static JLabel bddPathFile;

	// --- Relatifs à la barre de statuts.
	public static JLabel lblUserNameStatut;
	public static JLabel lblUserGradeStatut;
	public static JLabel lblUserConnectedAtStatut;
	public static Timestamp lblUserConnectedAtTS;
	public static JLabel lblUserElapsedTimeStatut;

	// --- Relatifs au espacement (largeur) des menus contextuels.
	public static String JPOPUPMENU_SPACE = "                          ";

	// --- Relatifs à la fermeture de la fenêtre et des méthodes asynchrones
	// (threads).
	public static boolean stopThread = false;

	// --- Relatifs à l'affichage de texte en html.
	public static String HTML_SPAN_STYLE_TEXT_RED = "<span style=\"color:'bc0000'\\\">";
	public static String HTML_SPAN_STYLE_TEXT_GREEN = "<span style=\"color:'119711'\\\">";
	public static String HTML_SPAN_STYLE_TEXT_BOLD = "<span style=\"font-weight:bold\\\">";
	public static String HTML_SPAN_STYLE_TEXT_RED_BOLD = "<span style=\"color:'bc0000'; font-weight:bold\\\">";
	public static String HTML_SPAN_STYLE_TEXT_GREEN_BOLD = "<span style=\"color:'119711'; font-weight:bold\\\">";
	public static String HTML_SPAN_STYLE_TITLE_BOLD = "<span style=\"text-decoration:underline;font-size:16px;font-weight:bold\\\">";

	// --- Relatifs à "l'entreprise" de développement.
	private static final String DEV_NAME = "LUZI-DEV";
	private static final String DEV_ADDRESS = "777 Boulevard du Cyber Espace";
	private static final String DEV_CITY_CODE = "54000";
	private static final String DEV_CITY = "NANCY";
	@SuppressWarnings("unused")
	private static final String DEV_PHONE = "+3383000102";
	@SuppressWarnings("unused")
	private static final String DEV_FAX = "+3383000103";
	private static final String DEV_MAIL = "randy.luzi@gmail.com";
	private static final String DEV_ICON = "ressources/data/dev/icon_dev.png";
	private static final String DEV_AUTHOR = "Randy LUZI";
	private static final String ABOUT_INFO_TITLE = "À propos de l'application";
	private static final String ABOUT_INFO = "<html>Powered by " + HTML_SPAN_STYLE_TEXT_RED_BOLD + DEV_NAME
			+ "</span><br><br>" + DEV_ADDRESS + "<br>" + DEV_CITY_CODE + " " + DEV_CITY + "<br><br>" + DEV_MAIL
			+ "<br><br>Auteur : <b>" + DEV_AUTHOR + "</b></html>";

	// --- Relatifs au message d'erreur critique.
	public static String CRITICAL_ERROR_MESSAGE = "<html>" + HTML_SPAN_STYLE_TEXT_RED
			+ "Une ressource vitale à l'execution du programme est manquante. Le programme doit fermer.</span></html>";

	// --- Son d'erreur de l'OS (Uniquement pour Windows).
	public static final Runnable ERROR_SOUND = (Runnable) Toolkit.getDefaultToolkit()
			.getDesktopProperty("win.sound.exclamation");

	/*
	 * Début de l'application.
	 */

	// ----------------- Main (point d'entreé de l'application).
	public static void main(String[] args) throws Exception {

		assembleInformationsClientOrder();
		System.out.println(DateGestion.getTimeForConsole() + "Répertoire de l'application : " + CURRENT_DIR);

		// --- Tentative de mise en place du look nimbus.
		try {

			UIManager.setLookAndFeel(new NimbusLookAndFeel());

		} catch (Exception e) {

			System.out.println(DateGestion.getTimeForConsole() + "Le thème 'Nimbus' ne fonctionne pas sur cet OS...");

		}

		// --- Lancement de l'application.
		SwingUtilities.invokeLater(() -> {
			THIS_WINDOW = new App();

			// --- Tentative de chargement de l'icone de l'application.
			try {

				THIS_WINDOW.setIconImage(ImageIO.read(new File(ICON_APP)));

			} catch (Exception e) {

				System.out.println(DateGestion.getTimeForConsole() + "L'icone de l'application est manquante...");

			}

			THIS_WINDOW.setVisible(true);
		});
	}

	// ----------------- Constructeur de l'application.
	public App() {

		verifRessources(PATH_DATA_USER_CONNEXION_FILE, true);

		// --- Ici on pose qu'une question sera posée avant fermeture de la fenêtre.
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// --- Récupération du contentPane.
		contentPaneApplication = (JPanel) getContentPane();

		// --- Création et affichage de la fenêtre de connexion.
		contentPaneApplication.add(createUserConnexionPanel());
		// createAppPane();
		// --- L'ActionListenner du boutton de fermeture de la fenêtre.
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				ImageIcon iconExit = new ImageIcon(ICON_QUESTION);
				int clickedClose = JOptionPane.showConfirmDialog(App.this, MESSAGE_QUIT, MESSAGE_QUIT_TITLE,
						JOptionPane.YES_NO_OPTION, 1, iconExit);

				if (clickedClose == JOptionPane.YES_OPTION) {
					closeAll();
					App.this.dispose();
				}
			};
		});

	}

	// ----------------- Fenêtre de connexion.
	private JPanel createUserConnexionPanel() {

		this.setTitle(TITLE_CONNEXION);
		setUserConnexionWindow();

		// --------------------------- Couche principale.
		JPanel JPHome = new JPanel(new GridBagLayout());
		JPHome.setBackground(ConvertBase.hexToColor("ffffff"));

		// --------------------------- Sous-couche.
		JPanel boxUserConnect = new JPanel();

		// --- Creation du titre et des bordures.
		Border borderLine = BorderFactory.createMatteBorder(3, 1, 1, 1, ConvertBase.hexToColor("8E8E8E"));
		TitledBorder title = BorderFactory.createTitledBorder(borderLine, "Connexion :");
		title.setTitleFont(new Font(Font.DIALOG, Font.BOLD, 12));
		title.setTitleColor(ConvertBase.hexToColor("000000"));

		boxUserConnect.setBorder(title);

		BoxLayout boxUserConnectLayout = new BoxLayout(boxUserConnect, BoxLayout.Y_AXIS);
		boxUserConnect.setLayout(boxUserConnectLayout);
		boxUserConnect.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

		// --- Label identifiant utilisateur.
		lblUserName = new JLabel("Nom d'utilisateur :", new ImageIcon(PATH_ICONS_LOW + "user.png"), JLabel.LEFT);
		JPanel JPlblUserId = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8));

		JPlblUserId.add(lblUserName);
		boxUserConnect.add(JPlblUserId);

		// --- Saisie identifiant utilisateur.
		userInputId = new JTextField();
		userInputId.setPreferredSize(new Dimension(196, 32));
		userInputId.addActionListener(this::connexionUser);

		JPanel JPuserInputId = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPuserInputId.add(userInputId);
		boxUserConnect.add(JPuserInputId);

		// --- Label mot-de-passe utilisateur.
		lblUserPassword = new JLabel("Mot de passe :", new ImageIcon(PATH_ICONS_LOW + "password.png"), JLabel.LEFT);
		JPanel JPlblUserPassword = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8));

		JPlblUserPassword.add(lblUserPassword);
		boxUserConnect.add(JPlblUserPassword);

		// --- Saisie mot-de-passe utilisateur.
		userInputPassword = new JPasswordField();
		userInputPassword.setPreferredSize(new Dimension(196, 32));
		userInputPassword.addActionListener(this::connexionUser);

		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				userInputId.requestFocus();
			}
		});

		JPanel JPuserInputPassword = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPuserInputPassword.add(userInputPassword);
		boxUserConnect.add(JPuserInputPassword);

		// --- Bouton d'entrée.
		ImageIcon btnEnterIcon = new ImageIcon(PATH_ICONS_LOW + "enter.png");
		JButton btnEnter = new JButton();
		btnEnter.setIcon(btnEnterIcon);
		btnEnter.setText("Entrer");
		btnEnter.setToolTipText("Entrer dans l'application");
		btnEnter.setVerticalTextPosition(AbstractButton.CENTER);
		btnEnter.setHorizontalTextPosition(AbstractButton.LEADING);
		btnEnter.addActionListener(this::connexionUser);
		JPanel JPbtnEnter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPbtnEnter.add(btnEnter);
		boxUserConnect.add(JPbtnEnter);

		// --- Label de mauvaise saisie.
		lblUserNameReturn = new JLabel(userIdNotValid, new ImageIcon(PATH_ICONS_LOW + "error.png"), JLabel.LEFT);
		lblUserNameReturn.setForeground(ConvertBase.hexToColor("A30505"));
		JPlblUserNameReturn = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
		JPlblUserNameReturn.add(lblUserNameReturn);
		JPlblUserNameReturn.setVisible(false);
		boxUserConnect.add(JPlblUserNameReturn);

		// --- Finalisation.
		JPHome.add(boxUserConnect);

		return JPHome;

	}

	// ----------------- Fenêtre de l'application.
	private JPanel createAppPane() {

		// ----------------- Initialisation de la fenêtre principale.
		this.setTitle(TITLE_APP);
		setFullScreenAppWindow();

		// ----------------- Création et ajout de la barre de menu.
		this.setJMenuBar(createmenuBar());

		// ----------------- Création et ajout de la barre d'outils.
		contentPaneApplication.add(createToolBar(), BorderLayout.NORTH);

		// ----------------- Création et ajout de l'arborescence.
		JSplitPane jtreesSplitPane = createJtrees();

		// ----------------- Création des panneaux centraux.
		JPanel rightSection = createDetailsSection();
		JTabbedPane bddSection = VirtualJTabbleBdd.createBddPharma_CIS_CIP();
		leftSection = createTableSection();
		topSection = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSection, rightSection);
		topSection.setResizeWeight(0.8);

		contentNorthSectionAndOrderSection = new JPanel();
		contentNorthSectionAndOrderSection
				.setLayout(new BoxLayout(contentNorthSectionAndOrderSection, BoxLayout.Y_AXIS));

		orderSection = createOrderSection();
		contentNorthSectionAndOrderSection.add(topSection, BorderLayout.CENTER);
		contentNorthSectionAndOrderSection.add(orderSection, BorderLayout.CENTER);
		orderSection.setVisible(false);

		JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, contentNorthSectionAndOrderSection,
				bddSection);
		rightSplitPane.setResizeWeight(0.6);

		JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jtreesSplitPane, rightSplitPane);
		contentPaneApplication.add(mainSplitPane);

		// ----------------- Création et ajout de la barre de statuts.
		contentPaneApplication.add(createStatusBar(), BorderLayout.SOUTH);

		return null;

	}

	// ----------------- Méthodes de creations des composants graphiques.
	private JMenuBar createmenuBar() {

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(Box.createRigidArea(new Dimension(4, 24)));
		JMenu menuFile = new JMenu("File    ");
		menuFile.setMnemonic('F');

		menuFile.add(actNew);
		menuFile.addSeparator();
		menuFile.add(actOpen);
		menuFile.add(actRecent);
		menuFile.add(actSave);
		menuFile.add(actSaveAs);
		menuFile.addSeparator();

		menuFile.add(actSwitchApp);
		menuFile.add(actExit);

		menuBar.add(menuFile);

		JMenu menuEdit = new JMenu("Edit    ");
		menuEdit.setMnemonic('E');

		menuEdit.add(actUndo);
		menuEdit.add(actRedo);
		menuEdit.addSeparator();
		menuEdit.add(actCut);
		menuEdit.add(actCopy);
		menuEdit.add(actPaste);

		menuBar.add(menuEdit);

		JMenu menuQuestion = new JMenu("About    ");
		menuFile.setMnemonic('A');

		menuQuestion.add(actAbout);

		menuBar.add(menuQuestion);

		return menuBar;

	}

	@SuppressWarnings("serial")
	private JToolBar createToolBar() {

		JToolBar toolBar = new JToolBar();

		JButton btnNew = toolBar.add(actNew);
		btnNew.setHideActionText(true);
		JButton btnOpen = toolBar.add(actOpen);
		btnOpen.setHideActionText(true);
		JButton btnSave = toolBar.add(actSave);
		btnSave.setHideActionText(true);
		JButton btnSaveAs = toolBar.add(actSaveAs);
		btnSaveAs.setHideActionText(true);

		toolBar.addSeparator();
		JButton btnUndo = toolBar.add(actUndo);
		btnUndo.setHideActionText(true);
		JButton btnRedo = toolBar.add(actRedo);
		btnRedo.setHideActionText(true);

		toolBar.addSeparator();
		JButton btnCopy = toolBar.add(actCopy);
		btnCopy.setHideActionText(true);
		JButton btnCut = toolBar.add(actCut);
		btnCut.setHideActionText(true);
		JButton btnPaste = toolBar.add(actPaste);
		btnPaste.setHideActionText(true);

		toolBar.addSeparator();

		JButton btnSwitch = toolBar.add(actSwitchApp);
		btnSwitch.setHideActionText(true);

		JButton btnExit = toolBar.add(actExit);
		btnExit.setHideActionText(true);

		toolBar.addSeparator();

		final JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(600, 400));
		final JPopupMenu popup = new JPopupMenu();

		JMenuItem langFrench = new JMenuItem(new AbstractAction("Français") {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Langue française selectionnée !");
			}
		});
		langFrench.setIcon(new ImageIcon(PATH_ICONS_LANG + "france.png"));

		JMenuItem langItalian = new JMenuItem(new AbstractAction("Italiano") {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Lingua italiana selezionata !");
			}
		});
		langItalian.setIcon(new ImageIcon(PATH_ICONS_LANG + "italy.png"));

		JMenuItem langEnglish = new JMenuItem(new AbstractAction("English") {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "English language selected !");
			}
		});
		langEnglish.setIcon(new ImageIcon(PATH_ICONS_LANG + "england.png"));

		popup.add(langFrench);
		popup.add(langItalian);
		popup.add(langEnglish);

		final JButton button = new JButton(new ImageIcon(PATH_ICONS_LOW + "languages.png"));
		button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		toolBar.add(button);

		JButton btnAbout = toolBar.add(actAbout);
		btnAbout.setHideActionText(true);

		toolBar.setFloatable(false);
		return toolBar;

	}

	private JPanel createStatusBar() {

		// --- Barre de statuts container.
		JPanel statusBar = new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));

		// --- Barre de statut de gauche.
		JPanel statusBarLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 32, 10));

		String iconUser;
		if (userSexe == 0) {
			iconUser = "user-female.png";
		} else {
			iconUser = "user-male.png";
		}
		lblUserNameStatut = new JLabel(
				"<html>Utilisateur : " + HTML_SPAN_STYLE_TEXT_GREEN_BOLD + userName + "</span></html>",
				new ImageIcon(PATH_ICONS_LOW + iconUser), JLabel.LEFT);
		statusBarLeft.add(lblUserNameStatut);

		lblUserGradeStatut = new JLabel("<html>" + HTML_SPAN_STYLE_TEXT_BOLD + userGrade + "</span></html>",
				new ImageIcon(PATH_ICONS_LOW + "grade.png"), JLabel.LEFT);
		statusBarLeft.add(lblUserGradeStatut);

		lblUserConnectedAtStatut = new JLabel("<html>Connecté à : " + HTML_SPAN_STYLE_TEXT_BOLD + userTimeConnexion
				+ " (" + userDateConnexion + ")</span></html>", new ImageIcon(PATH_ICONS_LOW + "schedule.png"),
				JLabel.LEFT);
		statusBarLeft.add(lblUserConnectedAtStatut);

		// --- Barre de statut de droite.
		JPanel statusBarRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 32, 10));

		lblUserElapsedTimeStatut = new JLabel("", new ImageIcon(PATH_ICONS_LOW + "timer.png"), JLabel.LEFT);
		statusBarRight.add(lblUserElapsedTimeStatut);

		// --- Ajout des deux sous-barres de statuts dans la barre de statuts.
		statusBar.add(statusBarLeft);
		statusBarLeft.add(Box.createHorizontalGlue());
		statusBar.add(statusBarRight);

		// --- Mis en place d'une bordure du haut.
		Border topBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, ConvertBase.hexToColor("939393"));
		statusBar.setBorder(topBorder);

		return statusBar;

	}

	private JSplitPane createJtrees() {

		// --- Factorisation des élements de toolbar relatifs aux Jtrees.
		ImageIcon searchButtonJtreeIcon = new ImageIcon(PATH_ICONS_LOW + "find.png");
		String searchToolTipText = "Rechercher...";

		ImageIcon toggleButtonJtreeIcon = new ImageIcon(PATH_ICONS_LOW + "tree-structure.png");
		String toggleToolTipText = "Déplier/Replier l'arborescence...";

		// ------------ Arborescence NORD.
		JTree jtree1Application = DirFilesTree.generateJtree(JTREE1_DIR_FOCUSED);
		JScrollPane jtrees1ScrollPane = new JScrollPane(jtree1Application);

		// --- Actions de click souris.
		MouseAdapter mouseListennerJtree1 = new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				JTree tree = jtree1Application;
				int selRow = jtree1Application.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = jtree1Application.getPathForLocation(e.getX(), e.getY());

				if (selRow != -1) {
					if (selPath != null && selPath.getLastPathComponent() instanceof DefaultMutableTreeNode) {

						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selPath.getLastPathComponent();

						// --- Action via click gauche de la souris.
						if (SwingUtilities.isLeftMouseButton(e)) {
							if (e.getClickCount() == 1) {

								// --- action (via click) sur un élément de l'arborescence.
								System.out.println(DateGestion.getTimeForConsole() + DirFilesTree.getJTreePath(selPath)
										+ " (action via click gauche)");
							} else if (e.getClickCount() == 2) {

								// --- action (via double click) sur un élément de l'arborescence.
								System.out.println(DateGestion.getTimeForConsole() + DirFilesTree.getJTreePath(selPath)
										+ " (action via click gauche)");
							}
						}

						// --- Action via click droit de la souris.
						if (SwingUtilities.isRightMouseButton(e)) {
							if (e.getClickCount() == 1) {

								// --- action (via click) sur un élément de l'arborescence.
								System.out.println(DateGestion.getTimeForConsole() + DirFilesTree.getJTreePath(selPath)
										+ " (action via click droit)");

								if (selectedNode.isLeaf()) {
									tree.setSelectionPath(selPath);
									PopupMenuGestion.jTreeSimpleRightClickAction(e, tree, selectedNode);
								}
							} else if (e.getClickCount() == 2) {

								// --- action (via double click) sur un élément de l'arborescence.
								System.out.println(DateGestion.getTimeForConsole() + DirFilesTree.getJTreePath(selPath)
										+ " (action via click droit)");
							}
						}
					}
				}
			}
		};

		jtree1Application.addMouseListener(mouseListennerJtree1);

		// --- Création des composants graphiques.
		JTextField searchFieldJtree1 = new JTextField(12);
		searchFieldJtree1.addActionListener(e -> searchTree(jtree1Application, searchFieldJtree1.getText()));

		JButton searchButtonJtree1 = new JButton(searchButtonJtreeIcon);
		searchButtonJtree1.setToolTipText(searchToolTipText);
		searchButtonJtree1.addActionListener(e -> searchTree(jtree1Application, searchFieldJtree1.getText()));

		JButton toggleButtonJtree1 = new JButton(toggleButtonJtreeIcon);
		toggleButtonJtree1.setToolTipText(toggleToolTipText);

		toggleButtonJtree1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				collapseOrExpandAll(jtree1Application, jtree1Application.getModel().getRoot(), false);
			}
		});

		JToolBar jtree1ToolBar = new JToolBar();
		jtree1ToolBar.add(searchFieldJtree1);
		jtree1ToolBar.add(searchButtonJtree1);
		jtree1ToolBar.add(toggleButtonJtree1);
		jtree1ToolBar.setFloatable(false);
		jtree1ToolBar.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel controlPanelJtree1 = new JPanel();
		controlPanelJtree1.add(jtree1ToolBar, BorderLayout.NORTH);
		controlPanelJtree1.setLayout(new BoxLayout(controlPanelJtree1, BoxLayout.X_AXIS));

		JSplitPane jtrees1GlobalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, controlPanelJtree1,
				jtrees1ScrollPane);
		jtrees1GlobalSplitPane.setResizeWeight(0.0);
		jtrees1GlobalSplitPane.setDividerSize(0);

		// ---------- Arborescence SUD.
		JTree jtree2Application = DirFilesTree.generateJtree(JTREE2_DIR_FOCUSED);
		JScrollPane jtrees2ScrollPane = new JScrollPane(jtree2Application);

		// --- Actions de click souris.
		MouseAdapter mouseListennerJtree2 = new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				JTree tree = jtree2Application;
				int selRow = jtree2Application.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = jtree2Application.getPathForLocation(e.getX(), e.getY());

				if (selRow != -1) {
					if (selPath != null && selPath.getLastPathComponent() instanceof DefaultMutableTreeNode) {

						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selPath.getLastPathComponent();
						// --- Action via click gauche de la souris.
						if (SwingUtilities.isLeftMouseButton(e)) {
							if (e.getClickCount() == 1) {
								// --- action (via click) sur un élément de l'arborescence.
								System.out.println(DateGestion.getTimeForConsole() + DirFilesTree.getJTreePath(selPath)
										+ " (action via click gauche)");
							} else if (e.getClickCount() == 2) {
								// --- action (via double click) sur un élément de l'arborescence.
								System.out.println(DateGestion.getTimeForConsole() + DirFilesTree.getJTreePath(selPath)
										+ " (action via click gauche)");
							}
						}

						// --- Action via click droit de la souris.
						if (SwingUtilities.isRightMouseButton(e)) {
							if (e.getClickCount() == 1) {
								// --- action (via click) sur un élément de l'arborescence.
								System.out.println(DateGestion.getTimeForConsole() + DirFilesTree.getJTreePath(selPath)
										+ " (action via click droit)");

								if (selectedNode.isLeaf()) {
									tree.setSelectionPath(selPath);
									PopupMenuGestion.jTreeSimpleRightClickAction(e, tree, selectedNode);
								}
							} else if (e.getClickCount() == 2) {
								// --- action (via double click) sur un élément de l'arborescence.
								System.out.println(DateGestion.getTimeForConsole() + DirFilesTree.getJTreePath(selPath)
										+ " (action via click droit)");
							}
						}
					}
				}
			}
		};

		jtree2Application.addMouseListener(mouseListennerJtree2);

		// --- Création des composants graphiques.
		JTextField searchFieldJtree2 = new JTextField(12);
		searchFieldJtree2.addActionListener(e -> searchTree(jtree2Application, searchFieldJtree2.getText()));

		JButton searchButtonJtree2 = new JButton(searchButtonJtreeIcon);
		searchButtonJtree2.setToolTipText(searchToolTipText);
		searchButtonJtree2.addActionListener(e -> searchTree(jtree2Application, searchFieldJtree2.getText()));

		JButton toggleButtonJtree2 = new JButton(toggleButtonJtreeIcon);
		toggleButtonJtree2.setToolTipText(toggleToolTipText);

		toggleButtonJtree2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				collapseOrExpandAll(jtree2Application, jtree2Application.getModel().getRoot(), false);
			}
		});

		JToolBar jtree2ToolBar = new JToolBar();
		jtree2ToolBar.add(searchFieldJtree2);
		jtree2ToolBar.add(searchButtonJtree2);
		jtree2ToolBar.add(toggleButtonJtree2);
		jtree2ToolBar.setFloatable(false);
		jtree2ToolBar.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel controlPanelJtree2 = new JPanel();
		controlPanelJtree2.add(jtree2ToolBar, BorderLayout.NORTH);
		controlPanelJtree2.setLayout(new BoxLayout(controlPanelJtree2, BoxLayout.X_AXIS));

		JSplitPane jtrees2GlobalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, controlPanelJtree2,
				jtrees2ScrollPane);
		jtrees2GlobalSplitPane.setResizeWeight(0.0);
		jtrees2GlobalSplitPane.setDividerSize(0);

		// --- Assemblage des arborescences.
		JSplitPane jtreesSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jtrees1GlobalSplitPane,
				jtrees2GlobalSplitPane);
		jtreesSplitPane.setResizeWeight(0.6);

		// --- Customisation des JTree.
		jtree1Application.setCellRenderer(new CustomJtree(ICON_JTREE1_ROOT, ICON_JTREE1_NODE, ICON_JTREE1_LEAF));
		jtree2Application.setCellRenderer(new CustomJtree(ICON_JTREE2_ROOT, ICON_JTREE2_NODE, ICON_JTREE2_LEAF));

		return jtreesSplitPane;

	}

	private JTabbedPane createTableSection() {

		JTabbedPane jTabbedPane = new JTabbedPane();

		JPanel tabbedPurchaseHistory = VirtualJTabbleBdd
				.jTabbedDatabasePurchaseHistory(PATH_DATABASE_PURCHASE_HISTORY_FILE);
		String nameTabbedPurchaseHistory = "<html><b>Historique des achats </b></html> ";
		ImageIcon iconBddPurchaseHistory = new ImageIcon(App.PATH_ICONS_LOW + "order-history.png");
		jTabbedPane.addTab(nameTabbedPurchaseHistory, iconBddPurchaseHistory, tabbedPurchaseHistory);

		JPanel tabbedPrescriptions = VirtualJTabbleBdd
				.jTabbedDatabasePrescriptionHistory(PATH_DATABASE_PRESCRIPTIONS_FILE);
		String nameTabbedPrescriptions = "<html><b>Historique des ordonnances </b></html> ";
		ImageIcon iconBddPrescriptions = new ImageIcon(App.PATH_ICONS_LOW + "order-history.png");
		jTabbedPane.addTab(nameTabbedPrescriptions, iconBddPrescriptions, tabbedPrescriptions);

		JPanel tabbedClients = VirtualJTabbleBdd.jTabbedDatabaseClients(PATH_DATABASE_CLIENTS_FILE, false);
		String nameTabbedClients = "<html><b>Clients </b></html> ";
		ImageIcon iconBddClients = new ImageIcon(App.PATH_ICONS_LOW + "user-male.png");
		jTabbedPane.addTab(nameTabbedClients, iconBddClients, tabbedClients);

		JPanel tabbedDoctors = VirtualJTabbleBdd.jTabbedDatabaseDoctors(PATH_DATABASE_DOCTORS_FILE, false);
		String nameTabbedDoctors = "<html><b>Médecins </b></html> ";
		ImageIcon iconBddDoctors = new ImageIcon(App.PATH_ICONS_LOW + "doctor.png");
		jTabbedPane.addTab(nameTabbedDoctors, iconBddDoctors, tabbedDoctors);

		// --- Actions pour changement d'onglet.
		jTabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				int selectedIndex = jTabbedPane.getSelectedIndex();

				JTABBED_DETAILS.removeAll();
				JTABBED_DETAILS.repaint();
				JTABBED_DETAILS.revalidate();

				switch (selectedIndex) {
				case 0:
					System.out
							.println(DateGestion.getTimeForConsole() + "Table \"historique des achats\" sélectionnée.");
					JTABBED_DETAILS.add(DetailsPanel.PANEL_ORDER_DETAILS, BorderLayout.CENTER);

					break;

				case 1:
					System.out.println(
							DateGestion.getTimeForConsole() + "Table \"historique des ordonnances\" sélectionnée.");
					JTABBED_DETAILS.add(DetailsPanel.PANEL_PRESCRIPTION_DETAILS, BorderLayout.CENTER);
					break;

				case 2:
					System.out.println(DateGestion.getTimeForConsole() + "Table \"liste des client\" sélectionnée.");
					JTABBED_DETAILS.add(DetailsPanel.PANEL_CLIENT_DETAILS, BorderLayout.CENTER);
					break;

				case 3:
					System.out.println(DateGestion.getTimeForConsole() + "Table \"liste des médecins\" sélectionnée.");
					JTABBED_DETAILS.add(DetailsPanel.PANEL_DOCTOR_DETAILS, BorderLayout.CENTER);
					break;
				}
			}
		});

		return jTabbedPane;
	}

	private JPanel createOrderSection() {

		JPanel jpanelParent = new JPanel();
		jpanelParent.setLayout(new BorderLayout());

		// --- Liste déroulante des options "achat direct" ou "achat sur ordonnance".
		JPanel panelComboBox = new JPanel();
		panelComboBox.setSize(new Dimension(512, 32));
		panelComboBox.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel llbComboBoxOrderType = new JLabel("<html>" + HTML_SPAN_STYLE_TEXT_BOLD + "Type d'achat </span></html>");
		String[] options = { "Achat direct", "Achat sur ordonnance" };
		comboBoxOrderType = new JComboBox<>(options);
		comboBoxOrderType.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				switch (comboBoxOrderType.getSelectedIndex()) {

				case 0:
					InputOrder.panelOrderMutual.setVisible(false);
					InputOrder.panelOrderDoctor.setVisible(false);
					InputOrder.PurchaseOnPrescription = 0;
					InputOrder.drawInfoOrder();
					break;

				case 1:
					InputOrder.panelOrderMutual.setVisible(true);
					InputOrder.panelOrderDoctor.setVisible(true);
					InputOrder.PurchaseOnPrescription = 1;
					InputOrder.drawInfoOrder();
					break;

				}
			}
		});

		panelComboBox.add(llbComboBoxOrderType);
		panelComboBox.add(comboBoxOrderType);

		// --- Création de la toolbar.
		ImageIcon iconReturn = new ImageIcon(PATH_ICONS_LOW + "left.png");
		JButton btnCancelOrder = new JButton(iconReturn);
		btnCancelOrder.setText("<html><b>Annuler l'achat</b></html>");
		btnCancelOrder.setToolTipText("<html><b>Annuler l'achat</b></html>");
		btnCancelOrder.addActionListener(e -> switchSectionNorth());
		btnCancelOrder.setBorderPainted(false);

		ImageIcon iconTrashAll = new ImageIcon(PATH_ICONS_LOW + "delete.png");
		JButton btnTrashAll = new JButton(iconTrashAll);
		btnTrashAll.setText("<html><b>Effacer les saisies achat</b></html>");
		btnTrashAll.setToolTipText("<html><b>Effacer les saisies achat</b></html>");
		btnTrashAll.addActionListener(e -> InputOrder.trashAllWithQestion());
		btnTrashAll.setBorderPainted(false);

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		leftPanel.add(btnCancelOrder);
		leftPanel.add(btnTrashAll);

		ImageIcon iconClose = new ImageIcon(PATH_ICONS_LOW + "close.png");
		JButton btnClose = new JButton(iconClose);
		btnClose.setBorderPainted(false);

		btnClose.addActionListener(e -> switchSectionNorth());

		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		leftPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		rightPanel.add(btnClose);

		JToolBar toolBar = new JToolBar();
		toolBar.add(leftPanel);
		toolBar.addSeparator();
		toolBar.add(panelComboBox);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(rightPanel);
		toolBar.setFloatable(false);

		JSplitPane splitPane = InputOrder.inputOrderCreate();
		splitPane.setResizeWeight(0.6);

		// --- Assemblage des composants graphiques.
		jpanelParent.add(toolBar, BorderLayout.NORTH);
		jpanelParent.add(splitPane, BorderLayout.CENTER);

		return jpanelParent;
	}

	private JPanel createDetailsSection() {

		JTABBED_DETAILS.setLayout(new BorderLayout());
		DetailsPanel.setPanelOrderDetails();
		DetailsPanel.setPanelPrescriptionDetails();
		DetailsPanel.setPanelClientDetails();
		DetailsPanel.setPanelDoctorDetails();
		JTABBED_DETAILS.add(DetailsPanel.PANEL_ORDER_DETAILS, BorderLayout.CENTER);
		JTABBED_DETAILS.setPreferredSize(new Dimension(256, 0));
		return JTABBED_DETAILS;
	}

	public static void switchSectionNorth() {

		if (topSection.isVisible()) {

			topSection.setVisible(false);
			orderSection.setVisible(true);

		} else {

			orderSection.setVisible(false);
			topSection.setVisible(true);

		}

	}

	/*
	 * Méthodes abstraites d'action du menu et de la barre d'outils.
	 */

	@SuppressWarnings("serial")
	private AbstractAction actNew = new AbstractAction() {
		{
			putValue(Action.NAME, "New File");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "new-file.png"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
			putValue(Action.SHORT_DESCRIPTION, "New file... (CTRL+N)");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(
					DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> " + "New");
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actOpen = new AbstractAction() {
		{
			putValue(Action.NAME, "Open...");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "open.png"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
			putValue(Action.SHORT_DESCRIPTION, "Open... (CTRL+O)");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(
					DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> " + "Open");
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actRecent = new AbstractAction() {
		{
			putValue(Action.NAME, "Recent File");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(
					DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> " + "Recent");
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actSave = new AbstractAction() {
		{
			putValue(Action.NAME, "Save");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "save.png"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
			putValue(Action.SHORT_DESCRIPTION, "Save (CTRL+S)");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(
					DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> " + "Save");
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actSaveAs = new AbstractAction() {
		{
			putValue(Action.NAME, "Save as...");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "save-as.png"));
			putValue(Action.SHORT_DESCRIPTION, "Save as... (CTRL+ALT+S)");
			putValue(Action.ACCELERATOR_KEY,
					KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> "
					+ "Save as...");
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actExit = new AbstractAction() {

		{
			putValue(Action.NAME, "Exit");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "close.png"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ImageIcon iconExit = new ImageIcon(ICON_QUESTION);
			int clickedClose = JOptionPane.showConfirmDialog(App.this, MESSAGE_QUIT, MESSAGE_QUIT_TITLE,
					JOptionPane.YES_NO_OPTION, 1, iconExit);

			if (clickedClose == JOptionPane.YES_OPTION) {
				closeAll();
				App.this.dispose();
			}
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actUndo = new AbstractAction() {
		{
			putValue(Action.NAME, "Undo");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "undo.png"));
			putValue(Action.SHORT_DESCRIPTION, "Undo (CTRL+Z)");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(
					DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> " + "Undo");
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actRedo = new AbstractAction() {
		{
			putValue(Action.NAME, "Redo");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "redo.png"));
			putValue(Action.SHORT_DESCRIPTION, "Redo (CTRL+ALT+Z)");
			putValue(Action.ACCELERATOR_KEY,
					KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(
					DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> " + "Redo");
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actCopy = new AbstractAction() {
		{
			putValue(Action.NAME, "Copy");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "copy.png"));
			putValue(Action.SHORT_DESCRIPTION, "Copy (CTRL+C)");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(
					DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> " + "Copy");
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actCut = new AbstractAction() {
		{
			putValue(Action.NAME, "Cut");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "cut.png"));
			putValue(Action.SHORT_DESCRIPTION, "Cut (CTRL+X)");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(
					DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> " + "Cut");
		}
	};

	@SuppressWarnings("serial")
	private AbstractAction actPaste = new AbstractAction() {
		{
			putValue(Action.NAME, "Paste");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "paste.png"));
			putValue(Action.SHORT_DESCRIPTION, "Cut (CTRL+V)");
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(
					DateGestion.getTimeForConsole() + "Action Barre de menu/Barre d'outils enclenchée -> " + "Paste");
		}
	};

	// --- Méthode d'action abstraite affichant les informations relatives à
	// l'entreprise de développement (À propos).
	@SuppressWarnings("serial")
	private AbstractAction actAbout = new AbstractAction() {
		{
			putValue(Action.NAME, "About");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "about.png"));
			putValue(Action.SHORT_DESCRIPTION, "À propos");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.aboutFileListener();

		}

		private void aboutFileListener() {
			ImageIcon iconAbout = new ImageIcon(DEV_ICON);
			JOptionPane.showMessageDialog(null, ABOUT_INFO, ABOUT_INFO_TITLE, JOptionPane.INFORMATION_MESSAGE,
					iconAbout);
		}
	};

	// --- Méthode d'action abstraite de retour à la fenêtre de connexion.
	@SuppressWarnings("serial")
	private AbstractAction actSwitchApp = new AbstractAction() {
		{
			putValue(Action.NAME, "Déconnexion");
			putValue(Action.SMALL_ICON, new ImageIcon(PATH_ICONS_LOW + "exit.png"));
			putValue(Action.SHORT_DESCRIPTION, "Déconnexion");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.aboutListener(e);

		}

		public void aboutListener(ActionEvent e) {

			connexionUser(e);

		}
	};

	/*
		 * ---------------------> Méthodes spécifiques
	 */

	/*
	 * Ferme l'ensemble des fenêtres ouvertes par l'application et stopper le thread
	 * "setTimeOut()".
	 */
	private static void closeAll() {
		TableGestion.frameBddClients.dispose();
		TableGestion.frameBddDoctors.dispose();
		TableGestion.frameBddMutuals.dispose();
		stopThread = true;

	}

	// --- Vérification des ressources vitales de l'application.
	public static void verifRessources(String filePath, boolean critical) {

		boolean fileExist = FileTextUtilities.checkFileExist(filePath);

		if (!fileExist) {

			if (critical) {
				outSystemMessage();
			} else {

				System.out.println(
						DateGestion.getTimeForConsole() + "Un fichier vital est manquant.");

				if (ERROR_SOUND != null) {
					ERROR_SOUND.run();
				}
				JOptionPane.showMessageDialog(null,
						"Le fichier " + filePath
								+ " est introuvable. L'application risque de rencontrer des problèmes.",
						"Erreur system (fichier introuvable)", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(ICON_ERROR));

			}
		}

	}

	// --- Message d'erreur critique et sortie du programme.
	public static void outSystemMessage() {

		System.out.println(DateGestion.getTimeForConsole() + "Erreur critique : " + CRITICAL_ERROR_MESSAGE);

		if (ERROR_SOUND != null) {
			ERROR_SOUND.run();
		}
		JOptionPane.showMessageDialog(null, CRITICAL_ERROR_MESSAGE, "Erreur critique !",
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ICON_ERROR));
		System.exit(0);
	}

	// --- Connexion (et déconnexion) d'un utilisateur.
	public void connexionUser(ActionEvent e) {

		boolean isValidId = Regex.isValidTypePseudonyme1(userInputId.getText());

		/*
		 * /!\ NOTE IMPORTANTE relative au codage défensif de l'application /!\
		 * 
		 * Dans ce cadre de développement, le mot-de-passe n'est pas passé au crible de
		 * l'expression régulière. Il est juste vérifié que sa longueur ne soit pas
		 * nulle.
		 * 
		 * La commande attendue lors du deploiement de l'application doit être :
		 * 
		 * boolean isValidPw =
		 * Regex.isValidPasswordTypeFort(Hashing.charArray2String(userInputId.
		 * userInputPassword.getPassword()));
		 * 
		 * Les mots-de-passes enregistrés pour le moment sont simples : 1234.
		 * 
		 * Il devront être remplacés en type fort (voir méthode :
		 * Regex.isValidPasswordTypeFort) ou l'attribut Regex.REGEX_PasswordTypeStrong
		 * 
		 */
		boolean isValidPw = false;
		if (userInputPassword.getPassword().length > 0) {
			isValidPw = true;
		}

		if (!userEntered && isValidId && isValidPw) {

			boolean userValid = false;
			try {
				userValid = FileCipher.getUserFileCiphered(userInputId.getText(), userInputPassword.getPassword());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			if (userValid) {

				// --- Tout est valide, l'utilisateur entre dans l'application.
				THIS_WINDOW.setVisible(false);
				setFullScreenAppWindow();

				userName = userInputId.getText();
				userTimeConnexion = DateGestion.getTime();
				userDateConnexion = DateGestion.getDate();
				lblUserConnectedAtTS = DateGestion.getTimeStamp();
				contentPaneApplication.removeAll();
				userEntered = true;

				// --- Si le label qui indique une mauvaise saisie est visible, alors on le rend
				// invisible.
				if (!JPlblUserNameReturn.isDisplayable()) {
					JPlblUserNameReturn.setVisible(false);
				}

				createAppPane();
				launchElapsedTime();
				contentPaneApplication.repaint();
				contentPaneApplication.revalidate();
				THIS_WINDOW.setLocation(0, 0);
				THIS_WINDOW.setVisible(true);
				System.out.println(DateGestion.getTimeForConsole() + "L'utilisateur '" + userInputId.getText()
						+ "' existe et le mot de passe saisi correspond.");

			} else if (!userValid) {

				/*
				 * Info codage défensif : ici l'identifiant ou le mot-de-passe n'est pas valide.
				 * On affiche alors un showMessageDialog indiquant seulement que l'un des deux
				 * est incorrect, sans préciser lequel par mesure de sécurité. On doit partir du
				 * principe que dans un tel cas, l'utilisateur est malveillant. On s'assure en
				 * procédant ainsi de ne réveler aucune informations quant à l'information qui
				 * est incorrecte dans la saisie.
				 * 
				 */
				System.out.println(
						DateGestion.getTimeForConsole() + "Une mauvaise saisie a été entrée par l'utilisateur.");

				if (ERROR_SOUND != null) {
					ERROR_SOUND.run();
				}
				JOptionPane.showMessageDialog(null, "L'identifiant ou le mot de passe est invalide.",
						"Erreur de saisie", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ICON_ERROR));

			}

		} else if (!userEntered && (!isValidId || isValidPw)) {

			/*
			 * Ici la saisie (regex) de l'utilisateur n'est pas valide.
			 * 
			 * On affiche un JLabel en rouge significatif. L'affichage du JLabel dure 5
			 * secondes avant d'être effacé. Pour cela on fait appel à un thread (voir la
			 * méthode setTimeout()).
			 *
			 */

			if (userInputId.getText().equals("")) {
				lblUserNameReturn.setText("Vous devez saisir un identifiant.");
			} else {
				lblUserNameReturn.setText("L'identifiant saisi n'est pas valide.");
			}

			JPlblUserNameReturn.setVisible(true);
			setTimeout(() -> JPlblUserNameReturn.setVisible(false), 5000);

		} else if (userEntered) {

			/*
			 * Ici l'utilisateur sort de l'application et retourne à la fenêtre de
			 * connexion.
			 * 
			 */

			DetailsPanel.PANEL_ORDER_DETAILS.removeAll();
			DetailsPanel.PANEL_PRESCRIPTION_DETAILS.removeAll();
			DetailsPanel.PANEL_CLIENT_DETAILS.removeAll();
			DetailsPanel.PANEL_DOCTOR_DETAILS.removeAll();

			InputOrder.panelParentOrder.removeAll();
			InputOrder.panelOrderClient.removeAll();
			InputOrder.panelOrderMutual.removeAll();
			InputOrder.panelOrderDoctor.removeAll();

			contentPaneApplication.removeAll();
			setUserConnexionWindow();
			userEntered = false;
			this.setJMenuBar(null);

			contentPaneApplication.add(createUserConnexionPanel());
			contentPaneApplication.repaint();
			contentPaneApplication.revalidate();
			setUserConnexionWindow();
			THIS_WINDOW.setVisible(true);

			System.out.println(DateGestion.getTimeForConsole() + "L'utilisateur '" + userInputId.getText()
					+ "' sort de l'application.");
		}

		// --- Changement de thème.
		if (changeGlobalTheme) {
			changeTheme(this);
		}
	}

	// --- Définition de la taille de la fenêtre de connexion.
	private void setUserConnexionWindow() {

		this.setMinimumSize(new Dimension(USER_CONNEXION_WIDTH, USER_CONNEXION_HEIGHT));
		this.setSize(USER_CONNEXION_WIDTH, USER_CONNEXION_HEIGHT);
		this.setMaximumSize(new Dimension(USER_CONNEXION_WIDTH, USER_CONNEXION_HEIGHT));
		this.setLocationRelativeTo(null);

	}

	// --- Définition de la taille de la fenêtre principale.
	private void setFullScreenAppWindow() {
		this.setMinimumSize(new Dimension(500, 300));
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	// --- Méthode d'asynchronisation d'autres méthodes.
	public void setTimeout(Runnable runnable, int delay) {

		new Thread(() -> {
			try {
				/*
				 * NOTE IMPORTANTE : lorsque l'utilisateur quitte l'application, l'attribut
				 * 'stopThread' initialisée au départ sur 'false', et passée en 'true' afin de
				 * tuer son processus en ne l'appelant plus. Si cela n'est pas fait,
				 * l'application ne termine jamais même après appel de la méthode
				 * 'App.this.dispose();'.
				 */
				if (!stopThread) {
					Thread.sleep(delay);
					runnable.run();
				}
				
			} catch (Exception e) {
				System.out.println(DateGestion.getTimeForConsole()
						+ "Erreur de thread : Un problème est survenu à la méthode 'setTimeOut()'");
				System.err.println(e);
			}
		}).start();

	}

	// --- Vérification et écriture du temps écoulé depuis la connexion de
	// l'utilisateur (méthode bouclée via thread setTimeOut()).
	public void launchElapsedTime() {

		lblUserElapsedTimeStatut.setText("<html>Temps écoulé : " + HTML_SPAN_STYLE_TEXT_BOLD
				+ DateGestion.getTimeElasped(lblUserConnectedAtTS, new Timestamp(System.currentTimeMillis()))
				+ "</span></html>");
		setTimeout(() -> launchElapsedTime(), 1000);

	}

	// --- Relatif à la recherche dans les arborescences (JTree).
	private static void searchTree(JTree tree, String searchText) {
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
		searchNode(rootNode, searchText, tree);
		if (ERROR_SOUND != null) {
			ERROR_SOUND.run();
		}
	}

	private static void searchNode(DefaultMutableTreeNode node, String searchText, JTree tree) {
		for (int i = node.getChildCount() - 1; i >= 0; i--) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);

			if (childNode.toString().toLowerCase().contains(searchText.toLowerCase())) {
				TreePath path = new TreePath(childNode.getPath());
				tree.scrollPathToVisible(path);
				tree.setSelectionPath(path);
				break;
			}
			searchNode(childNode, searchText, tree);
		}
	}

	// --- Relatif au repliage des arborescences (JTree).
	private static void collapseOrExpandAll(JTree tree, Object root, boolean expand) {
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
		if (!expand) {
			for (int i = tree.getRowCount() - 1; i >= 0; i--) {
				tree.collapseRow(i);
			}
		}
	}

	// --- Méthode recusrsive permetant le changement de couleur de l'application.
	public static void changeTheme(Component component) {

		if (component instanceof JComponent) {
			JComponent jComponent = (JComponent) component;
			if (TARGET_COMPONENTS.contains(jComponent.getClass())) {

				jComponent.setBackground(COLOR_DARK_BACKGROUND);
				jComponent.setForeground(COLOR_DARK_BACKGROUND_TEXT);

			} else if (TARGET_COMPONENTS_OUTLINE.contains(jComponent.getClass())) {

				jComponent.setBackground(COLOR_DARK_BACKGROUND_OUTLINE);
				jComponent.setForeground(COLOR_DARK_BACKGROUND_OUTLINE_TEXT);

			} else if (TARGET_COMPONENTS_FIELD.contains(jComponent.getClass())) {

				jComponent.setBackground(COLOR_DARK_BACKGROUND_FIELD);
				jComponent.setForeground(COLOR_DARK_BACKGROUND_FIELD_TEXT);

			} else {

				jComponent.setBackground(COLOR_DARK_BACKGROUND_OUTLINE);
				jComponent.setForeground(COLOR_DARK_BACKGROUND_OUTLINE_TEXT);

			}
		}

		if (component instanceof Container) {
			Container container = (Container) component;
			Component[] components = container.getComponents();
			for (Component childComponent : components) {
				changeTheme(childComponent);
			}
		}
	}

}
