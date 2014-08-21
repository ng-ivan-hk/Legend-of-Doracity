import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultCaret;

/**
 * This is the main GUI for the game.
 * 
 * @author Ivan Ng
 * 
 */
@SuppressWarnings("serial")
public class Play extends JFrame {

	/* Constant Values */
	public final static int CHAR_MAX = 5; // No. of char per player
	public final static int DRAW_CARD_MAX = 4; // draw ? hand cards each turn
	public final static int[] EQUIPMENT_MAX = new int[] { 0, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1,
			1, 3, 1, 1, 1, 3, 1, 1, 3, 3, 3, 1, 1 };
	public final static int[] ITEM_MAX = new int[] { 0, 17, 17, 6 };
	public final static int[] SKILL_MAX = new int[] { 0, 3, 3, 3, 0, 0, 3, 3, 3, 3, 3, 3, 0, 3, 0,
			5, 0, 5 };
	public final static int MAX_HP = 50;
	public final static int MAX_MP = 150;
	public final static int INIT_HP = 20;
	public final static int INIT_MP = 100;

	/* Defines how to compare and order characters */
	public static Comparator<Character> charComparator = new Comparator<Character>() {
		@Override
		public int compare(Character c1, Character c2) {
			
			// Check characters' order
			if (c1.getOrder() == c2.getOrder()) {

				// Check characters' speed
				if (c1.getSpeed() == c2.getSpeed()) {
					
					if (c1.getPlayer() == c2.getPlayer()) {
						return c1.getNumber() - c2.getNumber();
					} else {
						return c1.getPlayer().isPlayer1() ? -1 : 1;
					}

				} else {
					return c2.getSpeed() - c1.getSpeed();
				}

			} else {
				return c2.getOrder() - c1.getOrder();
			}
			 
		}
	};

	/* Static Variables */
	private static JTextArea LOG_AREA = null;

	/* Non-Static Variables */
	private Player player1 = null; // Player who attacks first
	private Player player2 = null; // Player who attacks next
	private Player[] players = null;
	private static Stack<Card> cards = null; // card stack on the table
	private static ArrayList<Character> charList = null;
	private Character currentChar = null;
	private int round = 0;
	private int currentStatus = 0; // For checking occasion in battle

	/* GUI objects */
	private static Play play = null;
	private static DisplayArea displayArea = null;
	private static PlayerArea player1Area = null;
	private static PlayerArea player2Area = null;
	private PlayerArea[] playerAreas = null; // refers to the 2 player areas
	private LoadingScreen loadingScreen = null;
	private Preload preload = null;
	private Point mouseDownCompCoords = null;
	// Constant objects
	private final static int alpha = 200; // 0~255, 255 = no transparent
	private final static Color doracityColor = new Color(6, 77, 144);
	private final static Color academyColor = new Color(186, 53, 0);
	private final static Color doracityColorLight = new Color(220, 242, 255);
	private final static Color academyColorLight = new Color(255, 231, 210);
	
	/* Settings */
	private boolean drawCardOneOff = false;

	public static void main(String[] args) throws InterruptedException, InvocationTargetException {

		play = new Play();
		play.start();

	}

	/* Methods */

	public Play() throws InterruptedException {

		// Set Style of Progress Bar
		UIManager.put("ProgressBar.background", new Color(255, 255, 255, 150));
		UIManager.put("ProgressBar.foreground", new Color(0, 0, 0, 25));
		UIManager.put("ProgressBar.selectionBackground", Color.DARK_GRAY);
		UIManager.put("ProgressBar.selectionForeground", Color.DARK_GRAY);
		UIManager.put("ProgressBar.border", BorderFactory.createLineBorder(Color.GRAY));
		UIManager.put("ButtonUI", MyButtonUI.class.getName());
		
		// Loading Window
		loadingScreen = new LoadingScreen();
		loadingScreen.setVisible(true);

		/* Create card stack @formatter:off */
		cards = new Stack<Card>();
		// Push Equipment Cards		
		for (int i = 0; i < EQUIPMENT_MAX[1]; i++) cards.push(new KnightArmor());
		for (int i = 0; i < EQUIPMENT_MAX[2]; i++) cards.push(new HolyShield());
		for (int i = 0; i < EQUIPMENT_MAX[3]; i++) cards.push(new TwinDancer());
		for (int i = 0; i < EQUIPMENT_MAX[4]; i++) cards.push(new MobilityBoots());
		for (int i = 0; i < EQUIPMENT_MAX[5]; i++) cards.push(new FeatherBow());
		for (int i = 0; i < EQUIPMENT_MAX[6]; i++) cards.push(new Sniper());
		for (int i = 0; i < EQUIPMENT_MAX[7]; i++) cards.push(new WitchHat());
		for (int i = 0; i < EQUIPMENT_MAX[8]; i++) cards.push(new IceWand());
		for (int i = 0; i < EQUIPMENT_MAX[9]; i++) cards.push(new FireWand());
		for (int i = 0; i < EQUIPMENT_MAX[10]; i++) cards.push(new LightningWand());
		for (int i = 0; i < EQUIPMENT_MAX[11]; i++) cards.push(new RescueSuit());
		for (int i = 0; i < EQUIPMENT_MAX[12]; i++) cards.push(new PhysicalField());
		for (int i = 0; i < EQUIPMENT_MAX[13]; i++) cards.push(new ManaField());
		for (int i = 0; i < EQUIPMENT_MAX[14]; i++) cards.push(new ManaStone());
		for (int i = 0; i < EQUIPMENT_MAX[15]; i++) cards.push(new AdventurerSword());
		for (int i = 0; i < EQUIPMENT_MAX[16]; i++) cards.push(new VampireSickle());
		for (int i = 0; i < EQUIPMENT_MAX[17]; i++) cards.push(new TruthBlade());
		for (int i = 0; i < EQUIPMENT_MAX[18]; i++) cards.push(new PriorityClaw());
		for (int i = 0; i < EQUIPMENT_MAX[19]; i++) cards.push(new ManaStudentWand());
		for (int i = 0; i < EQUIPMENT_MAX[20]; i++) cards.push(new WizardRobe());
		for (int i = 0; i < EQUIPMENT_MAX[21]; i++) cards.push(new HealingWand());
		for (int i = 0; i < EQUIPMENT_MAX[22]; i++) cards.push(new FloatingShoes());
		for (int i = 0; i < EQUIPMENT_MAX[23]; i++) cards.push(new Gambeson());
		for (int i = 0; i < EQUIPMENT_MAX[24]; i++) cards.push(new AntiManaCloak());
		for (int i = 0; i < EQUIPMENT_MAX[25]; i++) cards.push(new FieldAcademy());
		for (int i = 0; i < EQUIPMENT_MAX[26]; i++) cards.push(new WallDoracity());
		// Push Item Cards
		for (int i = 0; i < ITEM_MAX[1]; i++) cards.push(new HPPotion());
		for (int i = 0; i < ITEM_MAX[2]; i++) cards.push(new MPPotion());
		for (int i = 0; i < ITEM_MAX[3]; i++) cards.push(new SmokeBomb());
		// Push Skill Cards
		for (int i = 0; i < SKILL_MAX[1]; i++) cards.push(new Assault());
		for (int i = 0; i < SKILL_MAX[2]; i++) cards.push(new Punch());
		for (int i = 0; i < SKILL_MAX[3]; i++) cards.push(new BattleSoul());
		for (int i = 0; i < SKILL_MAX[6]; i++) cards.push(new SupportShot());
		for (int i = 0; i < SKILL_MAX[7]; i++) cards.push(new Evasion());
		for (int i = 0; i < SKILL_MAX[8]; i++) cards.push(new EagleEye());
		for (int i = 0; i < SKILL_MAX[9]; i++) cards.push(new FireRain());
		for (int i = 0; i < SKILL_MAX[10]; i++) cards.push(new FreezingIce());
		for (int i = 0; i < SKILL_MAX[11]; i++) cards.push(new Thunder());
		for (int i = 0; i < SKILL_MAX[13]; i++) cards.push(new GreatHealing());
		for (int i = 0; i < SKILL_MAX[15]; i++) cards.push(new Mute());
		for (int i = 0; i < SKILL_MAX[17]; i++) cards.push(new Jail());
		// Shuffle randomly @formatter:on
		Collections.shuffle(cards);

		/* Create and Set GUI */
		setFrame();
		createPreload();

		synchronized (this) {
			this.wait();
		}
		
		createMainGame();

	}
	
	private void setFrame(){
		
		/* Set Basic Info */
		getLoadingScreen().setProgress(1, "Setting Basic Info");
		setTitle(Lang.frameTitle);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/* Set Draggable */
		getLoadingScreen().setProgress(1, "Adding Mouse Listeners to Frame");
		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent evt) {
			}

			public void mousePressed(MouseEvent evt) {
				mouseDownCompCoords = evt.getPoint();
			}

			public void mouseExited(MouseEvent evt) {
			}

			public void mouseEntered(MouseEvent evt) {
			}

			public void mouseClicked(MouseEvent evt) {
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent evt) {
			}

			public void mouseDragged(MouseEvent evt) {
				Point currCoords = evt.getLocationOnScreen();
				try {
					setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y
							- mouseDownCompCoords.y);
				} catch (NullPointerException e) {
				}
			}
		});

		/* Set Font Size and Type */
		getLoadingScreen().setProgress(1, "Setting Font Size and Type");
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof FontUIResource) {
				UIManager.put(key, new FontUIResource(Lang.font, Font.PLAIN, 12));
			}
		}

		/* Set App Icon */
		getLoadingScreen().setProgress(1, "Setting App Icon");
		try {
			setIconImage(new ImageIcon(Play.class.getResource("/resources/app_icon.png"))
					.getImage().getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING));
		} catch (NullPointerException e) {
		}
		
		

		/* Set Tool Tip */
		getLoadingScreen().setProgress(1, "Setting Tool Tip");
		ToolTipManager.sharedInstance().setInitialDelay(500);
		
		
		
		/* Set Menu Bar */
		getLoadingScreen().setProgress(1, "Setting Menu Bar");
		setJMenuBar(new MenuBar());

		/* Set Style */
//		setUndecorated(true);
//		setBackground(new Color(0, 0, 0, 0));
//		setContentPane(new ShadowPane());

	}

	/**
	 * Create and shows Preload GUI.
	 */
	private void createPreload() {

		add(preload = new Preload(this));
		setSize(new Dimension(935, 570));
		setLocationRelativeTo(null);
		setResizable(false);
		loadingScreen.setVisible(false);
		setVisible(true);

	}

	/**
	 * Create abd shows the GUI for main game.
	 */
	private void createMainGame() {

		setVisible(false);
		remove(preload);

		// Loading Window again
		loadingScreen.setVisible(true);		

		getLoadingScreen().setProgress(1, "Creating Display Area");
		add(BorderLayout.CENTER, displayArea = new DisplayArea());
		getLoadingScreen().setProgress(1, "Creating Player Area 1");
		add(BorderLayout.WEST, player1Area = new PlayerArea(player1));
		getLoadingScreen().setProgress(1, "Creating Player Area 2");
		add(BorderLayout.EAST, player2Area = new PlayerArea(player2));

		// If we want to do same thing for both area we can use playerAreas
		playerAreas = new PlayerArea[2];
		playerAreas[0] = player1Area;
		playerAreas[1] = player2Area;

		getLoadingScreen().setProgress(1, "Setting Frame for Main Game");
		revalidate();
		pack();
		setMinimumSize(getBounds().getSize());
		setSize(new Dimension(1000, getHeight() + 10));
		setLocationRelativeTo(null);
		setResizable(true);

	}
	
	public LoadingScreen getLoadingScreen(){
		return loadingScreen;
	}

	/**
	 * Override getToolTipLocation() of a Component and return this function.
	 * This can make the ToolTip follows the mouse cursor.
	 * 
	 * @param e
	 *            MouseEvent
	 * @return Point
	 */
	private Point moveWithCursor(MouseEvent e) {
		Point p = e.getPoint();
		p.x += 15;
		p.y += 15;
		return p;
	}
	
	/**
	 * Shake this JFrame.
	 */
	public static void shake(final int distance, final int duration) {
		WindowHandler.shake(play, distance, duration);
	}

	/**
	 * Set Player and create character list. It is called by Preload.
	 * 
	 * @param p1
	 *            Player 1's name
	 * @param p2
	 *            Player 2's name
	 * @param p2Chars
	 *            Player 1's characters in int
	 * @param p1Chars
	 *            Player 2's characters in int
	 */
	public void setPlayer(String p1, String p2, int[] p1Chars, int[] p2Chars) {

		/* Create Players */

		player1 = new Player(p1, true);
		player1.setCharacters(p1Chars);
		// player1.setCharacters(new Tea(player1), new Livia(player1), new
		// Phoebell(player1), new Map(
		// player1), new Iron(player1));

		player2 = new Player(p2, false);
		player2.setCharacters(p2Chars);
		// player2.setCharacters(new FishBall(player2), new Shirogane(player2),
		// new NonkiNobita(
		// player2), new Nana(player2), new GameNobita(player2));
		
		player1.setOpponent(player2);
		player2.setOpponent(player1);

		players = new Player[2];
		players[0] = player1;
		players[1] = player2;

		/* Create Character list */
		charList = new ArrayList<Character>();
		charList.addAll(new ArrayList<Character>(Arrays.asList(player1.getCharacters())));
		charList.addAll(new ArrayList<Character>(Arrays.asList(player2.getCharacters())));

	}

	/**
	 * Menu Bar for JFrame.
	 * 
	 * @author Ivan Ng
	 */
	private class MenuBar extends JMenuBar {

		private JMenuItem helpMenuItem = null;
		private JMenuItem aboutMenuItem = null;
		
		private JCheckBoxMenuItem drawCardMenuItem = null;
		
		private JMenuItem exitMenuItem = null;

		public MenuBar() {

			setBackground(new Color(245, 245, 245));
			setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ActionButton.borderColor));

			JMenu optionMenu = new JMenu(Lang.menu_option);
			add(optionMenu);
			
			/* Add Menu Items */
			optionMenu.add(helpMenuItem = new JMenuItem(Lang.menu_help));
			optionMenu.add(aboutMenuItem = new JMenuItem(Lang.menu_about));
			optionMenu.add(new JSeparator());
			optionMenu.add(drawCardMenuItem = new JCheckBoxMenuItem(Lang.menu_drawCardOneOff));
			optionMenu.add(new JSeparator());
			optionMenu.add(exitMenuItem = new JMenuItem(Lang.menu_exit));

			/* Add Action Listener for Menu Items */
			helpMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					new Help();
				}
			});

			aboutMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					JOptionPane.showMessageDialog(Play.this, "<html>" + Lang.menu_aboutInfo
							+ "</html>", Lang.menu_about, JOptionPane.INFORMATION_MESSAGE);
				}
			});
			
			drawCardMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					drawCardOneOff = drawCardMenuItem.getState();
				}
			});

			exitMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					System.exit(0);
				}
			});
			
		}
	}

	/**
	 * The Central Area which shows Character's labels.
	 * 
	 * @author Ivan Ng
	 * 
	 */
	private class DisplayArea extends JPanel {

		private TopField topField = null;
		private JLabel roundLabel = null;
		private JLabel stageLabel = null;
		private JLabel cardsLeft = null;

		private BattleField battleField = null;

		private BottomField bottomField = null;
		private JScrollPane logAreaScrollPane = null;

		public DisplayArea() {
			
			/* Set Log Area */
			logAreaScrollPane = new JScrollPane(LOG_AREA = new JTextArea());
			logAreaScrollPane.getVerticalScrollBar().setUnitIncrement(20);

			// Update Log Area automatically whenever text is appended
			((DefaultCaret) LOG_AREA.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

			LOG_AREA.setEditable(false);

			// Print current Date to Log Area
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd '('E')' HH:mm:ss");
			LOG_AREA.append(Lang.legendOfDoracity + Lang.log + " " + player1 + " vs " + player2
					+ " " + formatter.format(new Date()));


			setLayout(new BorderLayout());
			// Set Top Field
			add(topField = new TopField(), BorderLayout.NORTH);
			// Set Battle Field
			add(battleField = new BattleField(), BorderLayout.CENTER);
			// Set Bottom Field
			add(bottomField = new BottomField(), BorderLayout.SOUTH);

		}

		public void setStage(String stage) {
			stageLabel.setText(stage);
		}

		public void setRound(int round) {
			roundLabel.setText(Lang.round + " " + round + ": ");
		}

		public void setCardsLeft(int number) {
			cardsLeft.setText(Lang.cardsLeft + ": " + number);
		}

		private class TopField extends JPanel {
			public TopField() {
				setBackground(Style.panelColor);
				setLayout(new GridLayout(0, 3));

				// Add Round Label
				add(roundLabel = new JLabel());
				roundLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				setRound(0);

				// Add Stage Label
				add(stageLabel = new JLabel(Lang.stage_readyToPlay));
				stageLabel.setHorizontalAlignment(SwingConstants.LEFT);

				// Add Cards Left Label
				add(cardsLeft = new JLabel());
				cardsLeft.setHorizontalAlignment(SwingConstants.LEFT);

			}

		}

		private class BattleField extends JPanel {

			private CharLabel[] player1Chars = new CharLabel[CHAR_MAX];
			private CharLabel[] player2Chars = new CharLabel[CHAR_MAX];

			public BattleField() {
				setBackground(Color.WHITE);
				setBorder(BorderFactory.createLineBorder(Color.GRAY));

				// Create GUI Objects
				Character[] player1CharTemp = player1.getCharacters();
				Character[] player2CharTemp = player2.getCharacters();
				for (int i = 0; i < CHAR_MAX; i++) {
					player1Chars[i] = new CharLabel(player1CharTemp[i]);
					player2Chars[i] = new CharLabel(player2CharTemp[i]);
				}
				
				// Set Layout
				GroupLayout layout = new GroupLayout(this);
				setLayout(layout);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);

				updateCharOrder();
				updateAllLabels();

			}

			/**
			 * Highlight a CharLabel representing the Character.
			 * 
			 * @param c
			 *            Character to be highlighted
			 * @param b
			 *            pass true to highlight, false to unhighlight
			 */
			public void highlightChar(Character c, boolean b) {

				int index = c.getPlayer().indexOfChar(c);
				(c.getPlayer().isPlayer1() ? player1Chars : player2Chars)[index].highLightLabel(b);

			}

			/**
			 * Paint a CharLabel to grayscale.
			 * 
			 * @param c
			 *            Character to be painted gray
			 * @param b
			 *            pass true to grayscale, false to color
			 */
			public void paintCharGray(Character c, boolean b) {
				int index = c.getPlayer().indexOfChar(c);
				(c.getPlayer().isPlayer1() ? player1Chars : player2Chars)[index].paintLabelGray(b);
			}

			public void updateAllLabels() {

				for (int i = 0; i < CHAR_MAX; i++) {
					player1Chars[i].updateLabel();
					player2Chars[i].updateLabel();
				}
			}

			public void updateCharOrder() { // TODO: update Char

				Comparator<CharLabel> labelComparator = new Comparator<CharLabel>() {
					@Override
					public int compare(CharLabel l1, CharLabel l2) {
						Character c1 = l1.character;
						Character c2 = l2.character;
						return charComparator.compare(c1, c2);
					}
				};

				Arrays.sort(player1Chars, labelComparator);
				Arrays.sort(player2Chars, labelComparator);

				Runnable runner = new Runnable() {
					@Override
					public void run() {

						/* Define how to draw layout here */
						removeAll();

						GroupLayout layout = (GroupLayout) getLayout();

						GroupLayout.ParallelGroup h1 = layout
								.createParallelGroup(GroupLayout.Alignment.LEADING);
						for (int i = 0; i < player1Chars.length; i++) {
							h1.addComponent(player1Chars[i],GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
							          GroupLayout.PREFERRED_SIZE);
						}

						GroupLayout.ParallelGroup h2 = layout
								.createParallelGroup(GroupLayout.Alignment.LEADING);
						h2.addComponent(logAreaScrollPane, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

						GroupLayout.ParallelGroup h3 = layout
								.createParallelGroup(GroupLayout.Alignment.LEADING);
						for (int i = 0; i < player2Chars.length; i++) {
							h3.addComponent(player2Chars[i],GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
							          GroupLayout.PREFERRED_SIZE);
						}

						layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(h1)
								.addGroup(h2).addGroup(h3));

						GroupLayout.SequentialGroup v1a = layout.createSequentialGroup();
						for (int i = 0; i < player1Chars.length; i++) {
							v1a.addComponent(player1Chars[i],GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
							          GroupLayout.PREFERRED_SIZE);
						}

						GroupLayout.ParallelGroup v1b = layout
								.createParallelGroup(GroupLayout.Alignment.CENTER);
						v1b.addComponent(logAreaScrollPane, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

						GroupLayout.SequentialGroup v1c = layout.createSequentialGroup();
						for (int i = 0; i < player2Chars.length; i++) {
							v1c.addComponent(player2Chars[i],GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
							          GroupLayout.PREFERRED_SIZE);
						}

						GroupLayout.ParallelGroup v1 = layout
								.createParallelGroup(GroupLayout.Alignment.LEADING);
						v1.addGroup(v1a).addGroup(v1b).addGroup(v1c);

						layout.setVerticalGroup(layout.createSequentialGroup().addGroup(v1));

					}
				};

				if (EventQueue.isDispatchThread()) {
					runner.run();
				} else {
					try {
						SwingUtilities.invokeAndWait(runner);
					} catch (InvocationTargetException | InterruptedException e) {
						e.printStackTrace();
					}
				}
				

				updateAllLabels();

			}

			public void updateCharImages() {
				for (int i = 0; i < CHAR_MAX; i++) {
					player1Chars[i].setCharImage();
					player1Chars[i].setJobIcon();
					player1Chars[i].setPropertyIcon();
					player1Chars[i].highLightLabel(false);
					player2Chars[i].setCharImage();
					player2Chars[i].setJobIcon();
					player2Chars[i].setPropertyIcon();
					player2Chars[i].highLightLabel(false);
				}

			}

			private class CharLabel extends JLabel {
				private Character character = null;
				
				private boolean gray = false; // if true, this label will be painted gray
				
				// To be drawn in paintComponent()
				private BufferedImage charImage = null;
				private BufferedImage charImageGray = null;
				
				private Image jobIcon = null;
				private Image propertyIcon = null;
				private Image defenseIcon = null;
				private String charTitle = null;
				private String charName = null;
				private String charValues = null;

				public CharLabel(Character character) {

					// Set Text Position (Equipment)
					setHorizontalAlignment(SwingConstants.CENTER);
					setVerticalAlignment(SwingConstants.BOTTOM);
					
					// Set Size
					setPreferredSize(new Dimension(170,84));

					// Set GUI Images for paintComponent()
					this.character = character;
					setCharImage();
					setJobIcon();
					setPropertyIcon();
					setDefenseIcon();
					setOpaque(false);
					highLightLabel(false);
					
				}

				/**
				 * Called by highLightChar().
				 * 
				 * @param b
				 */
				public void highLightLabel(boolean b) {

					int borderWidth = b ? 3 : 2;
					if (b) {
						setBorder(BorderFactory.createMatteBorder(borderWidth, borderWidth,
								borderWidth, borderWidth, Color.CYAN));
					} else {
						setBorder(BorderFactory.createMatteBorder(borderWidth, borderWidth,
								borderWidth, borderWidth, character.isDoracity() ? doracityColor
										: academyColor));
					}

				}

				/**
				 * Called by paintCharGray(). This will set a boolean variable
				 * to be true, and allow paintComponent() to paint color or gray
				 * according to the boolean.
				 * 
				 * @param b
				 */
				public void paintLabelGray(boolean b) {
					gray = b;
					repaint();
				}

				/**
				 * Call this function to update the CharLabel whenever some data
				 * of the character has been changed.
				 */
				public void updateLabel() {

					// For paintComponent()
					charTitle = character.getTitle();
					charName = character.toString();

					charValues = "SP " + character.getSpeed() + "\nPM " + character.getDefM()
							+ "\nPD " + character.getDefP() + "\nAT " + character.getAttack();

					setText(// @formatter:off
							// Line 1
							"<html>" + 
							// Line 3
							(character.getEquipment() == null ? "" :
							Lang.equipment + ": <font color=blue>" 
							+ character.getEquipmentName() + "</font>") + "</html>"
							// @formatter:on
					);

					// For displaying all skill infos of the Character
					String skillInfo = "";
					skillInfo += (Lang.passive + Lang.skill + "<br>");
					CharSkill[] charSkills = character.passiveSkills;
					for (int i = 0; i < charSkills.length; i++) {
						skillInfo += (charSkills[i] + ": " + charSkills[i].getInfo() + "<br>");
					}
					skillInfo += ("<br>" + Lang.active + Lang.skill + "<br>");
					charSkills = character.activeSkills;
					for (int i = 0; i < charSkills.length; i++) {
						skillInfo += (charSkills[i] + " "
								+ Lang.occasion[charSkills[i].getOccasion()] + ": "
								+ charSkills[i].getInfo() + "<br>");
					}

					setToolTipText(//@formatter:off
							"<html><table border=0><tr><td>" + 
							
							/* === Left-Hand-Side BEGIN === */
							"<img height=316 width=220 src=" + getCharImageURL() + ">"
							
							/* === Left-Hand-Side END === */
							+ "</td><td>" +
							
							/* === Right-Hand-Side BEGIN === */
							"<font size=5><b>" + character.getTitle() + " " + character.toString()
							+ "</b></font> (" + (character.isFirstJob()? Lang.job1 :Lang.job2) 
							+ ")<br>" + (character.isDefense()?Lang.charDefensing:"") + "<br><br>" +
							
							Lang.equipment + ": <font color=blue>"
							+ character.getEquipmentName() + "</font><br><font color=green>"
							+ character.getEquipmentInfo() + "</font><br><br>" +
							
							Lang.job + ": " + character.getJobName() + "<br>" + Lang.property + ": "
							+ (character.isPhysical() ? Lang.physical : Lang.mana) + "<br><br>" + 
							
							Lang.attack + ": <b>" + character.getAttack()
							+ "</b> (" + character.getInitAttack() + ")<br>" +
							Lang.defP + ": <b>" + character.getDefP()
							+ "</b> (" + character.getInitDefP() + ")<br>" +
							Lang.defM + ": <b>" + character.getDefM()
							+ "</b> (" + character.getInitDefM() + ")<br>" +
							Lang.speed + ": <b>" + character.getSpeed()
							+ "</b> (" + character.getInitSpeed() + ")<br><br>" + 
							
							Lang.side
							+ ": " + (character.isDoracity() ? Lang.doracity : Lang.academy)
							+ "<br><br>" +
							skillInfo +
							
							/* === Right-Hand-Side END === */
							"</td></tr></table></html>"
							//@formatter:on
					);

					repaint();

				}

				@Override
				public JToolTip createToolTip() {
					JToolTip toolTip = super.createToolTip();
					Color color = character.isDoracity() ? doracityColor : academyColor;
					Color bg = character.isDoracity() ? doracityColorLight : academyColorLight;
					toolTip.setBackground(bg);
					toolTip.setForeground(color);
					toolTip.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, color));
					return toolTip;

				}

				/**
				 * Set Character's image. Only call this in job change (or game
				 * start).
				 */
				public void setCharImage() {
					BufferedImage src = null;
					try {
						src = ImageIO.read(getCharImageURL());
					} catch (IOException e) {
						e.printStackTrace();
					}
					// Crop Image
					charImage = src.getSubimage(120, 185, 300, 100);
					
					// Set Filter					
					RescaleOp rescaleOp = new RescaleOp(0.3f, 182, null);
					rescaleOp.filter(charImage, charImage);
					
					// Set Gray Scale Image
					charImageGray = new BufferedImage(300, 100, BufferedImage.TYPE_BYTE_GRAY);
					Graphics gray = charImageGray.getGraphics();
					gray.drawImage(charImage, 0, 0, null);
					gray.dispose();
				}

				/**
				 * Set Character's Job Icon. Only call this in job change (or
				 * game start).
				 */
				public void setJobIcon() {
					BufferedImage src = null;
					try {
						src = ImageIO.read(getJobIconURL());
					} catch (IOException e) {
						e.printStackTrace();
					}
					// Resize Image smoothly
					jobIcon = src.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
				}

				/**
				 * Set Character's Property(Physical/Mana) Icon. Only call this
				 * in job change (or game start).
				 */
				public void setPropertyIcon() {
					BufferedImage src = null;
					try {
						src = ImageIO.read(getPropertyIconURL());
					} catch (IOException e) {
						e.printStackTrace();
					}
					// Resize Image smoothly
					propertyIcon = src.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
				}

				/**
				 * Set Defense Icon, which will be displayed if Character's
				 * defense is on.
				 */
				public void setDefenseIcon() {
					BufferedImage src = null;
					try {
						src = ImageIO
								.read(Play.class.getResource("/resources/icons/defenseOn.png"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					// Resize Image smoothly
					defenseIcon = src.getScaledInstance(15, 15, Image.SCALE_AREA_AVERAGING);
				}

				/**
				 * Checks which Character does this CharLabel represents and
				 * returns the respective URL.
				 * 
				 * @return URL object which represents the Character image.
				 */
				private URL getCharImageURL() {
					URL imageURL = Play.class.getResource("/resources/CharPics/mC"
							+ String.format("%03d", character.getNumber()) + "-"
							+ (character.isFirstJob() ? "1" : "2") + ".png");
					if (imageURL == null) {
						imageURL = Play.class.getResource("/resources/CharPics/null.png");
					}
					return imageURL;
				}

				/**
				 * Checks Character's job and returns the respective URL.
				 * 
				 * @return URL object which represents the Character's job icon.
				 */
				private URL getJobIconURL() {
					URL iconURL = Play.class.getResource("/resources/icons/job"
							+ character.getJob() + ".png");
					return iconURL;
				}

				/**
				 * Checks Character's property (physical/mana) and returns the
				 * respective URL.
				 * 
				 * @return URL object which represents the Character's property.
				 */
				private URL getPropertyIconURL() {
					String property = character.isPhysical() ? "physical" : "mana";
					URL iconURL = Play.class.getResource("/resources/icons/property-" + property
							+ ".png");
					return iconURL;
				}

				@Override
				public Point getToolTipLocation(MouseEvent e) {
					return moveWithCursor(e);
				}

				@Override
				public void paintComponent(Graphics g) {// TODO::PaintComponent

					// Horizontal space between image and edge
					int imageX = 2;
					int imageY = 2;
					// Horizontal space between text and edge
					int valuesX = 5;
					int valuesY = 5;

					// Draw Char Image at back
					g.drawImage(gray ? charImageGray : charImage, 0, 0, null);

					// Draw Job Icon at bottom
					int iconSize = 25;
					g.drawImage(jobIcon, imageX, getHeight() - iconSize - imageY, iconSize,
							iconSize, null);

					// Draw Property Icon above Job Icon
					g.drawImage(propertyIcon, imageX, getHeight() - iconSize * 2 - imageY,
							iconSize, iconSize, null);

					// Set Color representing doracity or academy
					Color lineColor = character.isDoracity() ? doracityColor : academyColor;
					g.setColor(lineColor);

					// Write Character's title at top-left
					g.setFont(new Font(Lang.font, Font.PLAIN, 12));
					g.drawString(charTitle, valuesX, valuesY * 3);
					int titleWidth = g.getFontMetrics().stringWidth(charTitle);
					g.setFont(new Font(Lang.font, Font.PLAIN, 15));
					// Followed by Character's name
					int charNameX = valuesX + titleWidth + 2;
					g.drawString(charName, charNameX, valuesY * 3);
					// Followed by Defense Icon if Character's defense is on
					if (character.isDefense()) {
						int nameWidth = g.getFontMetrics().stringWidth(charName);
						g.drawImage(defenseIcon, charNameX + nameWidth, valuesY - imageY, null);
					}

					// Write Character's values at right
					valuesY += getHeight();
					g.setFont(new Font("Arial", Font.PLAIN, 12));
					for (String line : charValues.split("\n")) { // bottom-to-top
						int lineWidth = g.getFontMetrics().stringWidth(line); // right-align
						g.drawString(line, getWidth() - lineWidth - valuesX, valuesY -= g
								.getFontMetrics().getHeight());

					}

					super.paintComponent(g);
				}
			}

		}

		private class BottomField extends JPanel {
			

			public BottomField() {
				setBackground(Color.WHITE);
				setLayout(new GridLayout(1, 1));

				
			}
		}

	}

	private class PlayerArea extends JPanel {

		private Player player = null;
		private JLabel HPmeter = null;
		private JLabel MPmeter = null;
		private AttackButton attackButton = null;
		private CastSkillButton castSkillButton = null;
		private JobChangeButton jobChangeButton = null;
		private DrawButton drawButton = null;
		private UnequipButton unequipButton = null;
		private PassButton passButton = null;
		private JPanel cardArea = null;

		public PlayerArea(Player player) {
			this.player = player;

			setPreferredSize(new Dimension(115, 0));
			setLayout(new BorderLayout());
			setBackground(Style.panelColor);//TODO:1
			

			/* Add Player's name &  HP, MP meter */
			JPanel playerPanel = new JPanel();
			add(playerPanel, BorderLayout.NORTH);
			playerPanel.setBackground(Style.panelColor);
			playerPanel.setLayout(new GridLayout(0,1));
			playerPanel.add(new JLabel("--- " + player + " ---"));
			playerPanel.add(HPmeter = new JLabel());
			playerPanel.add(MPmeter = new JLabel());

			/* Add Action Buttons */
			JPanel actionPanel = new JPanel();
			add(actionPanel, BorderLayout.CENTER);
			actionPanel.setBackground(Style.panelColor);
			actionPanel.setLayout(new FlowLayout());
			actionPanel.add(attackButton = new AttackButton());
			actionPanel.add(castSkillButton = new CastSkillButton());
			actionPanel.add(jobChangeButton = new JobChangeButton());
			actionPanel.add(drawButton = new DrawButton());
			actionPanel.add(unequipButton = new UnequipButton());
			actionPanel.add(passButton = new PassButton());

			/* Add Card Area */
			cardArea = new JPanel();
			cardArea.setLayout(new BoxLayout(cardArea, BoxLayout.PAGE_AXIS));
			// Create a scroll panel and put the card area into it
			JScrollPane scrollPane = new JScrollPane(cardArea);
			scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
					ActionButton.borderColor));
			scrollPane.setPreferredSize(new Dimension(getWidth(), 250));
			add(scrollPane, BorderLayout.SOUTH);

			updateArea();
		}

		/**
		 * Updates HP, MP meter and Card Area.
		 */
		public void updateArea() {

			Runnable r = new Runnable() {
				@Override
				public void run() {
					cardArea.removeAll();
				}
			};
			
			if (SwingUtilities.isEventDispatchThread())
				r.run();
			else {
				try {
					SwingUtilities.invokeAndWait(r);
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}
			}

			updateHPMP();

			/* Add Hand Card Buttons */
			cardArea.setBackground(new Color(250, 250, 250));
			ArrayList<Card> handCards = player.getHandCards();
			for (int i = handCards.size() - 1; i >= 0; i--) {
				cardArea.add(new CardButton(handCards.get(i)));
			}

			revalidate(); // Update JScrollPane size
			repaint(); // Prevent overlapping
		}

		public void updateHPMP() {

			/* Update HP & MP */
			HPmeter.setText("<html><font color=red>   " + Lang.HP + " :  " + player.getHP() + " / "
					+ Play.MAX_HP + "</font></html>");
			MPmeter.setText("<html><font color=blue>" + Lang.MP + " :  " + player.getMP() + " / "
					+ Play.MAX_MP + "</font></html>");
			repaint();

		}

		public void setEnableEquipment(boolean b) {
			Component[] cardButtons = cardArea.getComponents();
			for (int i = 0; i < cardArea.getComponentCount(); i++) {
				CardButton cardButton = (CardButton) cardButtons[i];
				if (cardButton.card instanceof Equipment) {
					cardButtons[i].setEnabled(b);
				}
			}
		}

		public void setEnableItem(boolean b) {
			Component[] cardButtons = cardArea.getComponents();
			for (int i = 0; i < cardArea.getComponentCount(); i++) {
				CardButton cardButton = (CardButton) cardButtons[i];
				if (cardButton.card instanceof Item) {
					cardButtons[i].setEnabled(b);
				}
			}

		}

		public void setEnableSkill(boolean b) {
			Component[] cardButtons = cardArea.getComponents();
			if (b) { // enable the correct Skill buttons
				for (int i = 0; i < cardArea.getComponentCount(); i++) {
					CardButton cardButton = (CardButton) cardButtons[i];

					// If the button refers to a Skill Card
					if (cardButton.card instanceof Skill) {
						Skill skillCard = (Skill) cardButton.card;

						// If the occasion of the Skill Card matches
						if (skillCard.getOccasion() == currentStatus) {
							cardButton.setEnabled(true);
						}
					}
				}
			} else { // disable all Skill buttons
				for (int i = 0; i < cardArea.getComponentCount(); i++) {
					CardButton cardButton = (CardButton) cardButtons[i];

					if (cardButton.card instanceof Skill) {
						cardButton.setEnabled(false);
					}
				}

			}
		}

		public void setJobChangeButtonText() {
			jobChangeButton.setDescription(Lang.jobChange,
					Lang.jobChangeInfo[0] + currentChar.getJobChangeMP() + Lang.jobChangeInfo[1]);
		}
		
		public Player getPlayer() {
			return player;
		}

		public class AttackButton extends ActionButton implements ActionListener {
			public AttackButton() {
				setImageName("attack");
				setDescription(Lang.normalAttack, Lang.normalAttackInfo);
				setEnabled(false);
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent evt) {
				new CharSelectDialog();
			}

			public class CharSelectDialog extends JDialog {
				public CharSelectDialog() {
					super(Play.this, true);
					setLocationRelativeTo(AttackButton.this);
					setTitle(Lang.charSelection);
					add(new CharSelectPanel());
					pack();
					setResizable(false);

					setVisible(true);
				}

				public class CharSelectPanel extends SuperCharSelectPanel {
					public CharSelectPanel() {
						setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
						Character[] charTemp = player.getOpponent().getCharacters();
						for (int i = 0; i < CHAR_MAX; i++) {
							add(new CharButton(charTemp[i]));
						}

						// For Tea's active skill
						Tea.checkDoM(currentChar, player.getOpponent(), this);
						// For Phoebell's passive skill
						Phoebell.checkPhoebell(currentChar, this);
					}

					public void add(Character character) {
						add(new CharButton(character));
					}

					public class CharButton extends JButton implements ActionListener {
						private Character character = null;

						public CharButton(Character character) {
							this.character = character;
							setText(character.toString());
							addActionListener(this);
							if (character.isDefense()) {
								setEnabled(false);
							}
						}

						@Override
						public void actionPerformed(ActionEvent evt) {

							switch (currentChar.attack(character)) {
							case 1: // Attack failed
								JOptionPane.showMessageDialog(this, Lang.attackFailed);
								break;
							case 2: // character is defensing
								JOptionPane.showMessageDialog(this, Lang.charDefensing + "\n"
										+ Lang.cannotAttack);
								return; // Player can choose another character
							case 3: // Game Over
								CharSelectDialog.this.dispose();
								gameOver(player, player.getOpponent());
								return;
							}

							player1Area.updateHPMP();
							player2Area.updateHPMP();
							displayArea.battleField.updateAllLabels();

							CharSelectDialog.this.dispose();
							synchronized (Play.this) {
								Play.this.notify();
							}

						}
					}
				}

			}

		}

		private class CastSkillButton extends ActionButton implements ActionListener {
			public CastSkillButton() {
				setImageName("castSkill");
				setDescription(Lang.castSkill, Lang.castSkillInfo);
				setEnabled(false);
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent evt) {
				new SkillDialog();
			}

			private class SkillDialog extends JDialog {
				public SkillDialog() {
					super(Play.this, true);
					setLocationRelativeTo(CastSkillButton.this);
					setTitle(Lang.skillSelection);
					add(new SkillPanel());
					pack();
					setResizable(false);
					setVisible(true);
				}

				private class SkillPanel extends JPanel {
					public SkillPanel() {
						setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
						// Add Passive Skills
						add(new JLabel(Lang.passive));

						CharSkill[] charSkills = currentChar.passiveSkills;
						for (int i = 0; i < charSkills.length; i++) {
							add(new CharSkillButton(charSkills[i]));
						}
						// Horizontal line
						JSeparator separator = new JSeparator();
						Dimension d = separator.getPreferredSize();
						d.width = separator.getMaximumSize().width;
						separator.setMaximumSize(d);
						add(separator);

						// Add Active Skills
						add(new JLabel(Lang.active));

						charSkills = currentChar.activeSkills;
						for (int i = 0; i < charSkills.length; i++) {
							add(new CharSkillButton(charSkills[i]));
						}

					}

					private class CharSkillButton extends JButton implements ActionListener {
						private CharSkill charSkill = null;

						public CharSkillButton(CharSkill charSkill) {
							this.charSkill = charSkill;
							setText(charSkill.toString());

							setToolTipText("<html><font color=blue>"
									+ (charSkill.getOccasion() != Command.NA ? Lang.occasion[charSkill
											.getOccasion()] + "<br>"
											: "") + "</font><font color=black>"
									+ charSkill.getInfo() + "</font></html>");

							if (!charSkill.isActive() || charSkill.getOccasion() != currentStatus) {
								setEnabled(false);
							}
							addActionListener(this);
						}

						@Override
						public void actionPerformed(ActionEvent evt) {
							// Check MP enough?
							if (charSkill.getCharacter().getPlayer()
									.changeMP(-charSkill.getRequiredMP()) == 1) {
								JOptionPane.showMessageDialog(this, Lang.noMP);
								return;
							}

							Play.printlnLog(currentChar + Lang.log_castSkill + charSkill + " ("
									+ Lang.log_skillEffect + charSkill.getInfo() + ")");

							// Really use skill!
							charSkill.useSkill();
							
							// Update Area
							repaint();
							player1Area.updateArea();
							player2Area.updateArea();
							displayArea.battleField.updateAllLabels();

							// Pass or not?
							if (charSkill.isDoNotPass()) {
								return;
							}
							SkillDialog.this.dispose();
							CastSkillButton.this.setEnabled(false);
							synchronized (Play.this) {
								Play.this.notify();
							}

						}
					}

				}
			}

		}

		private class JobChangeButton extends ActionButton implements ActionListener {
			
			public JobChangeButton() {
				setImageName("jobChange");
				setDescription(Lang.jobChange, Lang.jobChangeInfo[0] + "15" + Lang.jobChangeInfo[1]);
				setEnabled(false);
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent evt) {

				if (player.changeMP(-currentChar.getJobChangeMP()) == 1) {
					// MP not enough
					JOptionPane.showMessageDialog(this, Lang.noMP);
					return;
				}
				// Print Log
				Play.printlnLog(currentChar + " " + Lang.log_jobChange
						+ (currentChar.isFirstJob() ? Lang.job2 : Lang.job1));

				// Really Job Change
				currentChar.jobChange();
				
				// Check if Equipment's extra requirement still matches
				Equipment tempEquip = currentChar.getEquipment();
				if (tempEquip != null) {
					switch (tempEquip.check(currentChar)) {
					case 1:
						JOptionPane.showMessageDialog(this, tempEquip + ": " + Lang.wrongJob + "\n"
								+ Lang.removeEquip);
						currentChar.setEquipment(null);
						unequipButton.setEnabled(false);
						break;
					case 10:
						JOptionPane.showMessageDialog(this, tempEquip + ": " + Lang.notAcademy
								+ "\n" + Lang.removeEquip);
						currentChar.setEquipment(null);
						unequipButton.setEnabled(false);
						break;
					case 11:
						JOptionPane.showMessageDialog(this, tempEquip + ": " + Lang.notDoracity
								+ "\n" + Lang.removeEquip);
						currentChar.setEquipment(null);
						unequipButton.setEnabled(false);
						break;

					}
				}
				
				setEnabled(false);
				
				// Update Area
				player1Area.updateHPMP();
				player2Area.updateHPMP();
				displayArea.battleField.updateAllLabels();
				displayArea.battleField.updateCharImages();
				
				// Rehighlight character
				displayArea.battleField.highlightChar(currentChar, true);

			}
		}

		private class DrawButton extends ActionButton implements ActionListener {
			private int count = 0; // each time only draw ? cards

			public DrawButton() {
				setImageName("draw");
				setDescription(Lang.drawCard, Lang.drawCardInfo);
				setEnabled(false);
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent ae) {

				if (drawCardOneOff) {

					while (count != 0) {
						draw(player);
						count--;
					}

					setEnabled(false);
					synchronized (Play.this) {
						Play.this.notify();
					}

				} else {

					draw(player);
					// Update counter
					count--;
					if (count == 0) {
						setEnabled(false);
						synchronized (Play.this) {
							Play.this.notify();
						}
					}

				}

			}

			public void setDrawCard(int count) {
				this.count = count;
				setEnabled(true);
			}

		}
		
		private class UnequipButton extends ActionButton implements ActionListener {
			public UnequipButton() {
				setImageName("unequip");
				setDescription(Lang.unequip, Lang.unequipInfo);
				setEnabled(false);
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent ae) {
				setEnabled(false);
				currentChar.setEquipment(null);
				displayArea.battleField.updateAllLabels();
			}

		}

		private class PassButton extends ActionButton implements ActionListener {
			public PassButton() {
				setImageName("pass");
				setDescription(Lang.pass, Lang.passInfo);
				setEnabled(false);
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent ae) {
				setEnabled(false);
				synchronized (Play.this) {
					Play.this.notify();
				}
			}

		}

		private class CardButton extends JButton implements ActionListener {

			private Card card = null;
			private Style.CardColorHolder colorHolder = null;

			public CardButton(Card card) {
				
				this.card = card;
				addActionListener(this);
				
				// Set Style
				colorHolder = new Style.CardColorHolder(card);

				setUI(new MyButtonUI() {
					@Override
					public Color getHoverColor() {
						return colorHolder.getHoverColor();
					}

					@Override
					public Color getPressedColor() {
						return colorHolder.getPressedColor();
					}
				});
				setBackground(colorHolder.getNormalColor());
				
				setText(card.toString());
				setToolTipText("<html>" + card.getInfo() + "</html>");
				setEnabled(false);
			}

			@Override
			public Point getToolTipLocation(MouseEvent e) {
				return moveWithCursor(e);
			}

			@Override
			public void setEnabled(boolean b) {
				super.setEnabled(b);
				if (b) {
					setForeground(colorHolder.getDarkColor());
					setBorder(BorderFactory.createLineBorder(colorHolder.getDarkColor()));
				} else {
					setBorder(BorderFactory.createEmptyBorder());
				}

			}

			@Override
			public void actionPerformed(ActionEvent ae) {

				if (card instanceof Equipment) {

					Equipment equipment = (Equipment) card;
					switch (currentChar.setEquipment(equipment)) { // Equip!
					case 1:
						JOptionPane.showMessageDialog(this, Lang.wrongJob);
						return;
					case 10:
						JOptionPane.showMessageDialog(this, Lang.notAcademy);
						return;
					case 11:
						JOptionPane.showMessageDialog(this, Lang.notDoracity);
						return;
					}
					
					unequipButton.setEnabled(true);

				} else if (card instanceof Item) {
					
					Item item = (Item) card;
					item.useItem(player);

				} else if (card instanceof Skill) {

					Skill skill = (Skill) card;
					switch (skill.check(currentChar)) { // Check first!
					case 1:
						JOptionPane.showMessageDialog(this, Lang.wrongJob);
						return;
					}

					// Check MP enough?
					if (currentChar.getPlayer().changeMP(-skill.getRequiedMP()) == 1) {
						JOptionPane.showMessageDialog(this, Lang.noMP);
						return;
					}

					Play.printlnLog(currentChar + Lang.log_useSkillCard + skill + " ("
							+ Lang.log_skillEffect + skill.getEffectInfo() + ")");

					skill.useSkill(currentChar); // Use it!

				}

				setVisible(false);
				player.removeCard(card);
				player1Area.updateHPMP();
				player2Area.updateHPMP();
				displayArea.battleField.updateAllLabels();

			}

		}

	}

	/**
	 * Start the game.
	 * 
	 * @throws InterruptedException
	 *             for handling wait()
	 */
	public void start() throws InterruptedException {

		// Call characters to do something when game start
		for (int i = 0; i < charList.size(); i++) {
			Character temp = charList.get(i);
			// First Game Start, then Job Change! Because some jobChangeExtra()
			// depends on gameStart()
			temp.gameStart();
			temp.jobChange();
		}

		displayArea.battleField.updateCharImages();
		loadingScreen.dispose();		
		setVisible(true);

		while (true) { // Loop until HP <= 0

			// Start a new round
			round++;
			displayArea.setRound(round);
			printlnLog("========== " + Lang.round + round + " ==========");

			// Let's begin!
			try {
				stageDrawCards();
				stagePrepare();
				stageBeforeBattle();
				stageDuringBattle();
				stageAfterBattle();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			// Call characters to disable something (e.g. defense, skills)
			for (int i = 0; i < charList.size(); i++) {
				Character temp = charList.get(i);
				temp.roundEnd();
			}

			// Update GUI
			player1Area.updateArea();
			player2Area.updateArea();
			displayArea.battleField.updateAllLabels();

		}
	}

	private void stageDrawCards() throws InterruptedException, InvocationTargetException {

		sortAllChars();
		displayArea.setStage(Lang.stage_drawCards);
		printlnLog(">>>" + Lang.stage_drawCards + "<<<");
		currentStatus = Command.DRAW_CARD; // for Kuzmon's job 1 active skill

		if (cards.size() >= 8) { // give each player exactly 4 cards

			for (int p = 0; p < playerAreas.length; p++) {
				final PlayerArea playerAreaTemp = playerAreas[p];

				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {

						// Check for Butterfly's job 2 passive skill
						Butterfly maybeButterfly = (Butterfly) playerAreaTemp.getPlayer().contains(
								Butterfly.class);
						if (maybeButterfly != null && !maybeButterfly.isFirstJob()) {
							Play.printlnLog(maybeButterfly + Lang.butterfly_wealth);
							playerAreaTemp.drawButton.setDrawCard(DRAW_CARD_MAX + 1);
						} else {
							playerAreaTemp.drawButton.setDrawCard(DRAW_CARD_MAX);
						}

					}
				});

				synchronized (this) {
					this.wait(); // wait until a button is pressed
				}

				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						playerAreaTemp.drawButton.setEnabled(false);
					}
				});

			}

		}
		
		for (int p = 0; p < playerAreas.length; p++) {
			
			final PlayerArea playerAreaTemp = playerAreas[p];

			// Check for Kuzmon's job 1 active skill
			Kuzmon maybeKuzmon = (Kuzmon) playerAreas[p].getPlayer().contains(Kuzmon.class);
			if (maybeKuzmon != null && maybeKuzmon.isFirstJob()) {

				currentChar = maybeKuzmon;
				displayArea.battleField.highlightChar(currentChar, true);

				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						playerAreaTemp.castSkillButton.setEnabled(true);
						playerAreaTemp.passButton.setEnabled(true);
					}
				});

				synchronized (this) {
					this.wait();
				}
				
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						playerAreaTemp.castSkillButton.setEnabled(false);
						playerAreaTemp.passButton.setEnabled(false);
					}
				});
				
				displayArea.battleField.highlightChar(currentChar, false);
				
			}
			
		}
		player1.listCards();
		player2.listCards();
	}

	private void stagePrepare() throws InterruptedException, InvocationTargetException {

		sortAllChars();
		displayArea.setStage(Lang.stage_prepare + " (" + Lang.stage_autoHealing + ")");
		printlnLog(">>>" + Lang.stage_prepare + "<<<");

		/* Heal HP */
		if (round > 1) { // No healing in round 1
			player1.changeHP(2);
			player2.changeHP(2);

		}

		/* Heal MP */
		for (int p = 0; p < players.length; p++) {
			int healMP = 0;
			Character[] playerCharTemp = players[p].getCharacters();

			// Check for Shirogane
			int casterMP = 2;
			Character maybeShirogane = players[p].contains(Shirogane.class);
			if (maybeShirogane != null && maybeShirogane.isFirstJob()) {
				casterMP++;
				Play.printlnLog(Lang.shirogane_resonance);
			}
			
			// Check for Kuru's job 1 passive skill: Bad Intention
			Kuru maybeKuru = (Kuru) players[p].contains(Kuru.class);
			if (maybeKuru != null && maybeKuru.isFirstJob() && maybeKuru.getEquipment() != null) {
				healMP++;
				Play.printlnLog(maybeKuru + Lang.kuru_badIntention);
			}

			for (int i = 0; i < CHAR_MAX; i++) {
				switch (playerCharTemp[i].getJob()) {
				case Character.SABER:
				case Character.ARCHER:
					healMP++;
					break;
				case Character.CASTER:
					healMP += casterMP;
					break;
				case Character.SUPPORT:
					healMP += 3;
					break;

				}
				// Check if there is a job2-Phoebell with Livia
				if (playerCharTemp[i] instanceof Phoebell && !playerCharTemp[i].isFirstJob()
						&& playerCharTemp[i].getPlayer().contains(Livia.class) != null) {
					Play.printlnLog(Lang.phoebell_together_job2);
					healMP++;
				}
			}

			players[p].changeMP(healMP);
			playerAreas[p].updateArea();
		}

		player1.listStatus();
		player2.listStatus();

		/* Use Item */
		displayArea.setStage(Lang.stage_prepare + " (" + Lang.stage_useItem + ")");
		for (int p = 0; p < playerAreas.length; p++) {
			final PlayerArea playerAreaTemp = playerAreas[p];

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					playerAreaTemp.setEnableItem(true);
					playerAreaTemp.passButton.setEnabled(true);
				}
			});

			synchronized (this) {
				this.wait();
			}

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					playerAreaTemp.setEnableItem(false);
					playerAreaTemp.passButton.setEnabled(false);
				}
			});
		}

		/* According to char order: equip or/and job change */
		displayArea.setStage(Lang.stage_prepare + " (" + Lang.stage_equipOrJobChange + ")");
		for (int i = 0; i < charList.size(); i++) {

			currentChar = charList.get(i);
			printCurrentChar();
			// printLog(currentChar.getPlayer().indexOfChar(currentChar));

			// Set Job Change Button ToolTip Text
			(currentChar.getPlayer().isPlayer1() ? player1Area : player2Area)
					.setJobChangeButtonText();

			// Highlight the character on the battle field
			displayArea.battleField.highlightChar(currentChar, true);

			// Handle Buttons
			final PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
					: player2Area;

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					areaTemp.jobChangeButton.setEnabled(true);
					if (currentChar.getEquipment() != null) {
						areaTemp.unequipButton.setEnabled(true);
					}
					areaTemp.passButton.setEnabled(true);
					areaTemp.setEnableEquipment(true);
				}
			});

			synchronized (this) {
				this.wait();
			}

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					areaTemp.jobChangeButton.setEnabled(false);
					areaTemp.unequipButton.setEnabled(false);
					areaTemp.passButton.setEnabled(false);
					areaTemp.setEnableEquipment(false);
				}
			});

			// Unhighlight the character & Paint it in grayscale
			displayArea.battleField.highlightChar(currentChar, false);
			displayArea.battleField.paintCharGray(currentChar, true);

		}
		
		// Paint CharLabel back to color
		for (int i = 0; i < charList.size(); i++) {
			displayArea.battleField.paintCharGray(charList.get(i), false);
		}

	}

	private void stageBeforeBattle() throws InterruptedException, InvocationTargetException {

		sortAllChars();
		printlnLog(">>>" + Lang.stage_beforeBattle + "<<<");
		displayArea.setStage(Lang.stage_beforeBattle);
		currentStatus = Command.BEFORE_BATTLE;

		// Skills/Skill Card according to charList
		for (int i = 0; i < charList.size(); i++) {

			currentChar = charList.get(i);
			printCurrentChar();

			// Highlight the character on the battle field
			displayArea.battleField.highlightChar(currentChar, true);

			// Handle Buttons
			final PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
					: player2Area;
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					areaTemp.castSkillButton.setEnabled(true);
					areaTemp.passButton.setEnabled(true);
					areaTemp.setEnableSkill(true);
				}
			});

			synchronized (this) {
				this.wait();
			}

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					areaTemp.castSkillButton.setEnabled(false);
					areaTemp.passButton.setEnabled(false);
					areaTemp.setEnableSkill(false);
				}
			});

			// Unhighlight the character & Paint it in grayscale
			displayArea.battleField.highlightChar(currentChar, false);
			displayArea.battleField.paintCharGray(currentChar, true);

		}

		// Paint CharLabel back to color
		for (int i = 0; i < charList.size(); i++) {
			displayArea.battleField.paintCharGray(charList.get(i), false);
		}

	}

	private void stageDuringBattle() throws InterruptedException, InvocationTargetException {

		sortAllChars();
		printlnLog(">>>" + Lang.stage_duringBattle + "<<<");
		displayArea.setStage(Lang.stage_duringBattle);
		currentStatus = Command.DURING_BATTLE;

		// Normal Attack/Skills/Skill Card according to charList
		for (int i = 0; i < charList.size(); i++) {

			currentChar = charList.get(i);
			printCurrentChar();
			
			// Check if the character has given up his/her action in this round
			if (currentChar.isGiveUp()) {
				Play.printlnLog(currentChar + Lang.givenUp);
				continue;
			}

			// Highlight the character on the battle field
			displayArea.battleField.highlightChar(currentChar, true);

			// Handle Buttons
			final PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
					: player2Area;
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					if (!currentChar.isGiveUpNormalAttack()) {
						areaTemp.attackButton.setEnabled(true);
					}
					if (!currentChar.isGiveUpSkills()) {
						areaTemp.setEnableSkill(true);
						areaTemp.castSkillButton.setEnabled(true);
					}
					areaTemp.passButton.setEnabled(true);

				}
			});

			synchronized (this) {
				this.wait();
			}
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					areaTemp.attackButton.setEnabled(false);
					areaTemp.castSkillButton.setEnabled(false);
					areaTemp.passButton.setEnabled(false);
					areaTemp.setEnableSkill(false);
				}
			});

			// Unhighlight the character & Paint it in grayscale
			displayArea.battleField.highlightChar(currentChar, false);
			displayArea.battleField.paintCharGray(currentChar, true);

		}
		
		// Paint CharLabel back to color
		for (int i = 0; i < charList.size(); i++) {
			displayArea.battleField.paintCharGray(charList.get(i), false);
		}

	}

	private void stageAfterBattle() throws InterruptedException, InvocationTargetException {
		sortAllChars();
		printlnLog(">>>" + Lang.stage_afterBattle + "<<<");
		displayArea.setStage(Lang.stage_afterBattle);
		currentStatus = Command.AFTER_BATTLE;

		// Skills according to charList
		for (int i = 0; i < charList.size(); i++) {

			currentChar = charList.get(i);
			printCurrentChar();
			
			// Check if the character has given up his/her action in this round
			if (currentChar.isGiveUp()) {
				Play.printlnLog(currentChar + Lang.givenUp);
				continue;
			}

			// Highlight the character on the battle field
			displayArea.battleField.highlightChar(currentChar, true);

			// Handle Buttons
			final PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
					: player2Area;
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					if (!currentChar.isGiveUpSkills()) {
						areaTemp.castSkillButton.setEnabled(true);
					}
					areaTemp.passButton.setEnabled(true);
				}
			});
			
			// Check for FishBall's job 1 passive skill
			if (currentChar instanceof FishBall) {
				FishBall fishBall = (FishBall) currentChar;
				if (fishBall.isLimit()) {
					Play.printlnLog(Lang.fishball_limit2);
					fishBall.getPlayer().changeMP(2);
					player1Area.updateHPMP();
					player2Area.updateHPMP();
				}
			}

			synchronized (this) {
				this.wait();
			}
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					areaTemp.castSkillButton.setEnabled(false);
					areaTemp.passButton.setEnabled(false);
				}
			});

			// Unhighlight the character & Paint it in grayscale
			displayArea.battleField.highlightChar(currentChar, false);
			displayArea.battleField.paintCharGray(currentChar, true);

		}
		
		// Paint CharLabel back to color
		for (int i = 0; i < charList.size(); i++) {
			displayArea.battleField.paintCharGray(charList.get(i), false);
		}

	}

	private void sortAllChars() {
		/* Reorder characters according to their speeds */
		// Reorder the 10-chars list
		Collections.sort(charList, charComparator);
		// Reorder each players'
		player1.sortChars();
		player2.sortChars();
		displayArea.battleField.updateCharOrder();
	}

	/**
	 * Draw a card. This method print "no cards left" in log if the card stack
	 * is empty.
	 * 
	 * @param player
	 *            the Player object who draws a card
	 */
	public static void draw(Player player) {

		if (cards.empty()) {
			printlnLog(Lang.noCardsLeft);
		} else {
			// Pop the top card from the stack into player's hand
			Card temp = cards.pop();
			// System.out.println("Get! " + temp);
			player.addCard(temp);
			
			player1Area.updateArea();
			player2Area.updateArea();
			displayArea.setCardsLeft(cards.size());

		}
	}

	/**
	 * Prints a string to the Log Area.
	 * 
	 * @param text
	 */
	public static void printLog(Object object) {
		LOG_AREA.append(object.toString());
	}

	/**
	 * Prints a string with a line break in prior to the Log Area.
	 * 
	 * @param text
	 */
	public static void printlnLog(Object object) {
		printLog("\n" + object);
	}
	
	/**
	 * Usually called by CharSkill.
	 * 
	 * @return Character List (Sorted)
	 */
	public static ArrayList<Character> getCharList() {
		return charList;
	}

	/**
	 * Prints names of current Character and Player.
	 */
	private void printCurrentChar() {
		printlnLog(currentChar + " (" + currentChar.getPlayer().toString() + ") " + Lang.log_round);
	}

	/**
	 * Call this function when one player's HP <= 0.
	 * 
	 * @param winner
	 *            Player object which represents the winner.
	 * @param loser
	 *            Player object which represents the loser.
	 */
	private void gameOver(Player winner, Player loser) {

		player1Area.updateHPMP();
		player2Area.updateHPMP();

		JOptionPane.showMessageDialog(this, Lang.gameOver + "\n\n" + Lang.winner + ": " + winner
				+ "\n" + Lang.loser + ": " + loser);

		System.exit(0); // End the application

	}

}
