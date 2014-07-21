import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
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
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.Timer;
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
	public final static int DRAW_CARD_MAX = 3; // draw ? hand cards each turn
	public final static int[] EQUIPMENT_MAX = new int[] { 3, 3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1 };
	public final static int[] ITEM_MAX = new int[] { 15, 15, 5 };
	public final static int[] SKILL_MAX = new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 0, 5,
			0, 5, 2, 2 };
	public final static int MAX_HP = 50;
	public final static int MAX_MP = 30;
	public final static int INIT_HP = 10;
	public final static int INIT_MP = 20;

	/* Defines how to compare and order characters */
	public static Comparator<Character> charComparator = new Comparator<Character>() {
		@Override
		public int compare(Character c1, Character c2) {
			if (c1.getSpeed() == c2.getSpeed()) {
				if (c1.getPlayer() == c2.getPlayer()) {
					return c1.getNumber() - c2.getNumber();
				} else {
					return c1.getPlayer().isPlayer1() ? -1 : 1;
				}
			} else {
				return c2.getSpeed() - c1.getSpeed();
			}
		}
	};

	/* Static Variables */
	private static JTextArea LOG_AREA = null;

	/* Non-Static Variables */
	private Player player1 = null; // Player who attacks first
	private Player player2 = null; // Player who attacks next
	private Player[] players = null;
	private Stack<Card> cards = null; // card stack on the table
	protected static ArrayList<Character> charList = null;
	private Character currentChar = null;
	private int round = 0;
	private int currentStatus = 0; // For checking occasion in battle

	/* GUI objects */
	private DisplayArea displayArea = null;
	private PlayerArea player1Area = null;
	private PlayerArea player2Area = null;
	PlayerArea[] playerAreas = null; // refers to the 2 player areas
	// Constant objects
	private final static int alpha = 200; // 0~255, 255 = no transparent
	private final static Color doracityColor = new Color(6, 77, 144);
	private final static Color academyColor = new Color(186, 53, 0);
	private final static Color doracityColorLight = new Color(220, 242, 255);
	private final static Color academyColorLight = new Color(255, 231, 210);

	/* for threads */
	public static CountDownLatch latch = null;

	public static void main(String[] args) throws InterruptedException, InvocationTargetException {

		Play play = new Play();
		play.start();

	}

	/* Methods */

	public Play() throws InterruptedException {

		/* Create card stack and shuffle */
		cards = new Stack<Card>();
		for (int i = 0; i < EQUIPMENT_MAX.length; i++) {
			for (int j = 0; j < EQUIPMENT_MAX[i]; j++) {
				if (i + 1 == 1 || i + 1 == 2 || i + 1 == 3 || i + 1 == 4 || i + 1 == 5
						|| i + 1 == 14 || i + 1 == 21 || i + 1 == 22) // DEBUG
					cards.push(new Equipment(i + 1));
			}
		}
		for (int i = 0; i < ITEM_MAX.length; i++) {
			for (int j = 0; j < ITEM_MAX[i]; j++) {
				cards.push(new Item(i + 1));
			}
		}
		for (int i = 0; i < SKILL_MAX.length; i++) {
			for (int j = 0; j < SKILL_MAX[i]; j++) {
				cards.push(new Skill(i + 1));
			}
		}
		Collections.shuffle(cards);

		setGUI();

	}

	private void setGUI() throws InterruptedException {

		/* Set Font Size and Type */
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof FontUIResource) {
				UIManager.put(key, new FontUIResource(Lang.font, Font.PLAIN, 12));
			}
		}

		/* Set App Icon */
		try {
			setIconImage(new ImageIcon(Play.class.getResource("/resources/xander.png")).getImage());
		} catch (NullPointerException e) {
		}

		/* Set Tool Tip */
		ToolTipManager.sharedInstance().setInitialDelay(0);

		setTitle(Lang.frameTitle);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		/* Run Preload GUI */
		Preload preload = new Preload(this);
		add(preload);
		setJMenuBar(new MenuBar());
		setSize(new Dimension(935, 570));
		locateCenter(this);
		setResizable(false);
		setVisible(true);

		synchronized (this) {
			this.wait();
		}

		remove(preload);

		/* Run main game GUI */
		setVisible(false);
		remove(preload);

		add(BorderLayout.CENTER, displayArea = new DisplayArea());
		add(BorderLayout.WEST, player1Area = new PlayerArea(player1));
		add(BorderLayout.EAST, player2Area = new PlayerArea(player2));

		// If we want to do same thing for both area we can use playerAreas
		playerAreas = new PlayerArea[2];
		playerAreas[0] = player1Area;
		playerAreas[1] = player2Area;

		revalidate();
		pack();
		setMinimumSize(getBounds().getSize());
		setSize(new Dimension(1100, 680));
		locateCenter(this);
		setResizable(true);

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
	 * Locate the window (JFrame) at center.
	 */
	public static void locateCenter(Window frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2,
				dim.height / 2 - frame.getSize().height / 2);
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
		private JMenuItem exitMenuItem = null;

		public MenuBar() {

			JMenu optionMenu = new JMenu(Lang.menu_option);
			add(optionMenu);

			/* Add Menu Items */
			optionMenu.add(helpMenuItem = new JMenuItem(Lang.menu_help));
			optionMenu.add(aboutMenuItem = new JMenuItem(Lang.menu_about));
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

			exitMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					System.exit(0);
				}
			});
		}
	}

	/**
	 * The Central Area.
	 * 
	 * @author Ivan Ng
	 * 
	 */
	private class DisplayArea extends JPanel {

		private TopField topField = null;
		private JLabel roundLabel = null;
		private JLabel stageLabel = null;
		private JLabel cardsLeft = null;

		protected BattleField battleField = null;

		private BottomField bottomField = null;
		private JTextArea logArea = null;

		public DisplayArea() {

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
				setBackground(Color.WHITE);
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
			private BLabel[] player1Actions = new BLabel[CHAR_MAX + 1];
			private BLabel[] player2Actions = new BLabel[CHAR_MAX + 1];;

			public BattleField() {
				setBackground(Color.WHITE);
				setBorder(BorderFactory.createLineBorder(Color.GRAY));
				setLayout(new GridLayout(6, 4));

				// Row 0
				add(new BLabel(Lang.player + ": " + player1));
				add(player1Actions[0] = new BLabel("player1"));
				add(player2Actions[0] = new BLabel("player2"));
				add(new BLabel(Lang.player + ": " + player2));

				// Row 1 - 5
				Character[] player1CharTemp = player1.getCharacters();
				Character[] player2CharTemp = player2.getCharacters();
				for (int i = 0; i < CHAR_MAX; i++) {

					add(player1Chars[i] = new CharLabel(player1CharTemp[i]));

					add(player1Actions[i + 1] = new BLabel("Player1 " + i));
					add(player2Actions[i + 1] = new BLabel("Player2 " + i));

					add(player2Chars[i] = new CharLabel(player2CharTemp[i]));

				}

				updateAllLabels();

			}

			/**
			 * Highlight a CharLabel. It is used in Play.start() like this:
			 * displayArea.battleField.highlightChar(currentChar.getPlayer(),
			 * currentChar.getPlayer().indexOfChar(currentChar), true)
			 * 
			 * @param player
			 *            Player object
			 * @param index
			 *            index in CharLabel
			 * @param b
			 *            pass true to highlight, false to unhighlight
			 */
			public void highlightChar(Player player, int index, boolean b) {

				if (player.isPlayer1()) {
					if (b) {
						player1Chars[index].highLightLabel(true);
					} else {
						player1Chars[index].highLightLabel(false);
					}
				} else {
					if (b) {
						player2Chars[index].highLightLabel(true);
					} else {
						player2Chars[index].highLightLabel(false);
					}
				}
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

				try {
					SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							removeAll();
						}
					});
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}

				// Row 0
				add(new BLabel(Lang.player + ": " + player1));
				add(player1Actions[0]);
				add(player2Actions[0]);
				add(new BLabel(Lang.player + ": " + player2));

				// Row 1 - 5
				for (int i = 0; i < CHAR_MAX; i++) {

					add(player1Chars[i]);
					add(player1Actions[i + 1]);
					add(player2Actions[i + 1]);
					add(player2Chars[i]);

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

			private class BLabel extends JLabel {
				public BLabel(String text) {
					super(text);
					setHorizontalAlignment(SwingConstants.CENTER);
					setVerticalAlignment(SwingConstants.BOTTOM);
				}

				public BLabel() {
					setHorizontalAlignment(SwingConstants.CENTER);
					setVerticalAlignment(SwingConstants.BOTTOM);
				}
			}

			private class CharLabel extends BLabel {
				private Character character = null;
				// To be drawn in paintComponent()
				private BufferedImage charImage = null;
				private Image jobIcon = null;
				private Image propertyIcon = null;
				private String charTitle = null;
				private String charName = null;
				private String charValues = null;

				public CharLabel(Character character) {
					this.character = character;
					setCharImage();
					setJobIcon();
					setPropertyIcon();
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
						skillInfo += (charSkills[i] + ": " + charSkills[i].getInfo() + "<br>");
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
							+ ")<br><br><br>" +
							
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
					g.drawImage(charImage, 0, 0, null);

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

					// Write Character's title and name at top
					g.setFont(new Font(Lang.font, Font.PLAIN, 12));
					g.drawString(charTitle, valuesX, valuesY * 3);
					int titleWidth = g.getFontMetrics().stringWidth(charTitle);
					g.setFont(new Font(Lang.font, Font.PLAIN, 15));
					g.drawString(charName, valuesX + titleWidth + 2, valuesY * 3);

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
			private JScrollPane scrollPane = null;

			public BottomField() {
				setBackground(Color.WHITE);
				setLayout(new GridLayout(1, 1));

				/* Set Log Area */
				scrollPane = new JScrollPane(logArea = LOG_AREA = new JTextArea());
				scrollPane.getVerticalScrollBar().setUnitIncrement(20);
				scrollPane.setPreferredSize(new Dimension(getWidth(), 100));

				// Update Log Area automatically whenever text is appended
				((DefaultCaret) logArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

				logArea.setEditable(false);

				// Print current Date to Log Area
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd '('E')' HH:mm:ss");
				logArea.append(Lang.legendOfDoracity + Lang.log + " " + player1 + " vs " + player2
						+ " " + formatter.format(new Date()));

				add(scrollPane, BorderLayout.CENTER);
			}
		}

	}

	protected class PlayerArea extends JPanel {

		private Player player = null;
		private JLabel HPmeter = null;
		private JLabel MPmeter = null;
		private AttackButton attackButton = null;
		private CastSkillButton castSkillButton = null;
		private JobChangeButton jobChangeButton = null;
		protected DrawButton drawButton = null;
		protected PassButton passButton = null;
		protected JPanel cardArea = null;

		public PlayerArea(Player player) {
			this.player = player;

			setPreferredSize(new Dimension(200, 0));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			// setAlignmentX(Component.CENTER_ALIGNMENT);

			/* Add Player's name */
			add(new JLabel("--- " + player + " ---"));

			/* Add HP & MP meter */
			add(HPmeter = new JLabel());
			add(MPmeter = new JLabel());
			// Horizontal line
			JSeparator separator = new JSeparator();
			Dimension d = separator.getPreferredSize();
			d.width = separator.getMaximumSize().width;
			separator.setMaximumSize(d);
			add(separator);

			/* Add General Buttons */
			add(attackButton = new AttackButton());
			add(castSkillButton = new CastSkillButton());
			add(jobChangeButton = new JobChangeButton());
			add(drawButton = new DrawButton());
			add(passButton = new PassButton());

			JSeparator separator1 = new JSeparator(); // Horizontal line;
			separator1.setMaximumSize(d);
			add(separator1);

			/* Add Card Area */
			cardArea = new JPanel();
			cardArea.setLayout(new BoxLayout(cardArea, BoxLayout.PAGE_AXIS));
			// Create a scroll panel and put the card area into it
			JScrollPane scrollPane = new JScrollPane(cardArea);
			scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			add(scrollPane);

			updateArea();
		}

		/**
		 * Updates HP, MP meter and Card Area.
		 */
		public void updateArea() {

			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						cardArea.removeAll();
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}

			updateHPMP();

			/* Add Hand Card Buttons */
			cardArea.setBackground(Color.WHITE);
			ArrayList<Card> handCards = player.getHandCards();
			for (int i = handCards.size() - 1; i >= 0; i--) {
				cardArea.add(new CardButton(handCards.get(i)));
			}

			revalidate(); // Update JScrollPane size
			repaint(); // Prevent overlapping
		}

		public void updateHPMP() {

			/* Update HP & MP */
			HPmeter.setText("<html><font color=red>" + Lang.HP + " :  " + player.getHP() + " / "
					+ Play.MAX_HP + "</font></html>");
			MPmeter.setText("<html><font color=blue>" + Lang.MP + " :  " + player.getMP() + " / "
					+ Play.MAX_MP + "</font></html>");

		}

		public void setEnableEquipment(boolean b) {
			Component[] cardButtons = cardArea.getComponents();
			for (int i = 0; i < cardArea.getComponentCount(); i++) {
				if (cardButtons[i].getBackground() == Color.WHITE) {
					cardButtons[i].setEnabled(b);
				}
			}
		}

		public void setEnableItem(boolean b) {
			Component[] cardButtons = cardArea.getComponents();
			for (int i = 0; i < cardArea.getComponentCount(); i++) {
				if (cardButtons[i].getBackground() == Color.ORANGE) {
					cardButtons[i].setEnabled(b);
				}
			}

		}

		public void setEnableSkill(boolean b) {
			Component[] cardButtons = cardArea.getComponents();
			if (b) { // enable the correct Skill buttons
				for (int i = 0; i < cardArea.getComponentCount(); i++) {

					// If the button refers to a Skill Card
					if (cardButtons[i].getBackground() == Color.YELLOW) {
						CardButton cardButtonTemp = (CardButton) cardButtons[i];
						Skill cardTemp = (Skill) cardButtonTemp.card;

						// If the occasion of the Skill Card matches
						if (cardTemp.getOccasion() == currentStatus) {
							cardButtons[i].setEnabled(true);
						}
					}
				}
			} else { // disable all Skill buttons
				for (int i = 0; i < cardArea.getComponentCount(); i++) {
					if (cardButtons[i].getBackground() == Color.YELLOW) {
						cardButtons[i].setEnabled(false);
					}
				}

			}
		}

		public class AttackButton extends JButton implements ActionListener {
			public AttackButton() {
				setText(Lang.normalAttack);
				setToolTipText(Lang.normalAttackInfo);
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
						Character[] charTemp = player.isPlayer1() ? player2.getCharacters()
								: player1.getCharacters();
						for (int i = 0; i < CHAR_MAX; i++) {
							add(new CharButton(charTemp[i]));
						}

						// For Tea's active skill
						Tea.checkDoM(currentChar, player.isPlayer1() ? player2 : player1, this);
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
						}

						@Override
						public void actionPerformed(ActionEvent evt) {

							switch (currentChar.attack(character)) {
							case 1: // Attack failed
								JOptionPane.showMessageDialog(this, Lang.attackFailed);
								break;
							case 2: // character is defensing
								JOptionPane.showMessageDialog(this, Lang.charDefensing);
								return; // Player can choose another character
							case 3: // Game Over
								CharSelectDialog.this.dispose();
								gameOver(player, player.isPlayer1() ? player2 : player1);
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

		private class CastSkillButton extends JButton implements ActionListener {
			public CastSkillButton() {
				setText(Lang.castSkill);
				setToolTipText(Lang.castSkillInfo);
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

							/* Really use skill! */
							charSkill.useSkill(currentChar.getPlayer().isPlayer1() ? player2
									: player1);

							// Update Area
							updateArea();
							player1Area.updateHPMP();
							player2Area.updateHPMP();
							displayArea.battleField.updateAllLabels();

							// Pass
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

		private class JobChangeButton extends JButton implements ActionListener {
			public JobChangeButton() {
				setText(Lang.jobChange);
				setToolTipText(Lang.jobChangeInfo);
				setEnabled(false);
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent evt) {
				if (player.changeMP(-15) == 1) { // MP not enough
					JOptionPane.showMessageDialog(this, Lang.noMP);
					return;
				}
				// Print Log
				Play.printlnLog(currentChar + " " + Lang.log_jobChange
						+ (currentChar.isFirstJob() ? Lang.job2 : Lang.job1));
				// Remove Equipment effect
				removeEquipmentEffect();
				// Really Job Change
				currentChar.jobChange();
				// Equip again if extra requirement still matches
				switch (useCard(player, currentChar.getEquipment())) {
				case 1:
					JOptionPane.showMessageDialog(this, currentChar.getEquipmentName() + ": "
							+ Lang.wrongJob + "\n" + Lang.removeEquip);
					currentChar.setEquipment(null);
					break;
				case 10:
					JOptionPane.showMessageDialog(this, currentChar.getEquipmentName() + ": "
							+ Lang.notAcademy + "\n" + Lang.removeEquip);
					currentChar.setEquipment(null);
					break;
				case 11:
					JOptionPane.showMessageDialog(this, currentChar.getEquipmentName() + ": "
							+ Lang.notDoracity + "\n" + Lang.removeEquip);
					currentChar.setEquipment(null);
					break;

				}
				// Update Area
				player1Area.updateHPMP();
				player2Area.updateHPMP();
				displayArea.battleField.updateAllLabels();
				displayArea.battleField.updateCharImages();
				setEnabled(false);

			}
		}

		private class DrawButton extends JButton implements ActionListener {
			private int count = 0; // each time only draw ? cards

			public DrawButton() {
				setText(Lang.drawCard);
				setToolTipText(Lang.drawCardInfo);
				setEnabled(false);
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent ae) {
				draw(player);
				updateArea();
				// Update counter
				count--;
				if (count == 0) {
					setEnabled(false);
					synchronized (Play.this) {
						Play.this.notify();
					}
				}
			}

			public void setDrawCard(int count) {
				this.count = count;
				setEnabled(true);
			}

		}

		private class PassButton extends JButton implements ActionListener {
			public PassButton() {
				setText(Lang.pass);
				setToolTipText(Lang.passInfo);
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

		protected class CardButton extends JButton implements ActionListener {
			protected Card card = null;

			public CardButton(Card card) {
				this.card = card;
				setText(card.toString());
				setEnabled(false);

				if (card instanceof Equipment) {
					setBackground(Color.WHITE);
				} else if (card instanceof Item) {
					setBackground(Color.ORANGE);
				} else { // Skill
					setBackground(Color.YELLOW);
				}

				// Add description
				this.setToolTipText("<html>" + card.getInfo() + "</html>");

				addActionListener(this);
			}

			@Override
			public Point getToolTipLocation(MouseEvent e) {
				return moveWithCursor(e);
			}

			@Override
			public void actionPerformed(ActionEvent ae) {

				switch (useCard(player, card)) {
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

		if (round == 1) { // give each player 5 cards

			for (int i = 0; i < 5; i++) {
				draw(player1);
				draw(player2);
			}

		} else if (cards.size() >= 8) { // give each player at most 3 cards

			for (int p = 0; p < playerAreas.length; p++) {
				final PlayerArea playerAreaTemp = playerAreas[p];

				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						playerAreaTemp.drawButton.setDrawCard(DRAW_CARD_MAX);
						playerAreaTemp.passButton.setEnabled(true);
					}
				});

				synchronized (this) {
					this.wait(); // wait until a button is pressed
				}

				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						playerAreaTemp.drawButton.setEnabled(false);
						playerAreaTemp.passButton.setEnabled(false);
					}
				});

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
		// TODO: Handle Shirogane & Anthony's case
		for (int p = 0; p < players.length; p++) {
			int healMP = 0;
			Character[] playerCharTemp = players[p].getCharacters();
			for (int i = 0; i < CHAR_MAX; i++) {
				switch (playerCharTemp[i].getJob()) {
				case Character.SABER:
				case Character.ARCHER:
					healMP++;
					break;
				case Character.CASTER:
					healMP += 2;
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

			// Highlight the character on the battle field
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), true);

			// Handle Buttons
			final PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
					: player2Area;

			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					areaTemp.jobChangeButton.setEnabled(true);
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
					areaTemp.passButton.setEnabled(false);
					areaTemp.setEnableEquipment(false);
				}
			});

			// Unhighlight the character
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), false);

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
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), true);

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

			// Unhighlight the character
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), false);

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

			// Highlight the character on the battle field
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), true);

			// Handle Buttons
			final PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
					: player2Area;
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					areaTemp.attackButton.setEnabled(true);
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
					areaTemp.attackButton.setEnabled(false);
					areaTemp.castSkillButton.setEnabled(false);
					areaTemp.passButton.setEnabled(false);
					areaTemp.setEnableSkill(false);
				}
			});

			// Unhighlight the character
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), false);

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

			// Highlight the character on the battle field
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), true);

			// Handle Buttons
			final PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
					: player2Area;
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					areaTemp.castSkillButton.setEnabled(true);
					areaTemp.passButton.setEnabled(true);
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
				}
			});

			// Unhighlight the character
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), false);

		}
	}

	protected void sortAllChars() {
		/* Reorder characters according to their speeds */
		// Reorder the 10-chars list
		Collections.sort(charList, charComparator);
		// Reorder each players'
		player1.sortChars();
		player2.sortChars();
		displayArea.battleField.updateCharOrder();
	}

	/**
	 * Draw a card.
	 * 
	 * @param player
	 *            the Player object who draws a card
	 */
	protected void draw(Player player) {

		// System.out.println("--- Player: " + player.getName()
		// + " is drawing a card...");
		if (cards.empty()) {
			printlnLog(Lang.noCardsLeft);
		} else {
			// Pop the top card from the stack into player's hand
			Card temp = cards.pop();
			// System.out.println("Get! " + temp);
			player.addCard(temp);

			displayArea.setCardsLeft(cards.size());

		}
	}

	protected int useCard(Player player, Card card) {

		// Return 0 if success
		// Return 1 if wrong job
		// Return 10 if not Academy
		// Return 11 if not Doracity

		/* Card Handler: What to do? */

		// Check type and card number of the card for action

		if (card instanceof Equipment) { // Equipment Card
			int currentCharJob = currentChar.getJob();
			switch (card.getNumber()) {
			case 1: // Adventurer's Sword
				if (currentCharJob == Character.SABER || currentCharJob == Character.ARCHER
						|| currentCharJob == Character.NA) {
					if (currentChar.getEquipment() != null) {
						removeEquipmentEffect();
					}
					currentChar.setEquipment((Equipment) card);
					currentChar.setAttack(currentChar.getAttack() + 1);
				} else {
					return 1;
				}
				break;
			case 2: // Mana Student's Wand
				if (currentCharJob == Character.CASTER || currentCharJob == Character.SUPPORT
						|| currentCharJob == Character.NA) {
					if (currentChar.getEquipment() != null) {
						removeEquipmentEffect();
					}
					currentChar.setEquipment((Equipment) card);
					currentChar.setAttack(currentChar.getAttack() + 1);
				} else {
					return 1;
				}

				break;
			case 3: // Floating Shoes
				if (currentChar.getEquipment() != null) {
					removeEquipmentEffect();
				}
				currentChar.setEquipment((Equipment) card);
				currentChar.setSpeed(currentChar.getSpeed() + 1);

				break;
			case 4: // Gambeson (Cloth armor)
				if (currentChar.getEquipment() != null) {
					removeEquipmentEffect();
				}
				currentChar.setEquipment((Equipment) card);
				currentChar.setDefP(currentChar.getDefP() + 1);

				break;
			case 5: // Mana-resistant Cloak
				if (currentChar.getEquipment() != null) {
					removeEquipmentEffect();
				}
				currentChar.setEquipment((Equipment) card);
				currentChar.setDefM(currentChar.getDefM() + 1);

				break;
			case 6: // Armor of Knight

				break;
			case 7: // Feather Bow

				break;
			case 8: // Caster's Hat

				break;
			case 9: // Nurses' Uniform

				break;
			case 10: // Sickle of Vampire

				break;
			case 11: // Blade of Truth

				break;
			case 12: // Dual Shadows

				break;
			case 13: // Sniper Rifle (Chase attack guns)

				break;
			case 14: // Holy Shield
				if (currentChar.getJob() == Character.SABER) {
					if (currentChar.getEquipment() != null) {
						removeEquipmentEffect();
					}
					currentChar.setEquipment((Equipment) card);
					currentChar.setDefP(currentChar.getDefP() + 1);
					Character[] charTemp = currentChar.getPlayer().getCharacters();
					for (int i = 0; i < CHAR_MAX; i++) {
						charTemp[i].setDefP(charTemp[i].getDefP() + 1);
					}
				} else {
					return 1;
				}
				break;
			case 15: // Berserk Shoes

				break;
			case 16: // Ice Wand

				break;
			case 17: // Fire Wand

				break;
			case 18: // Thunder Wand

				break;
			case 19: // Sorcerer's Gown

				break;
			case 20: // Healing Wand

				break;
			case 21: // Field Academy
				if (!currentChar.isDoracity()) {
					if (currentChar.getEquipment() != null) {
						removeEquipmentEffect();
					}
					currentChar.setEquipment((Equipment) card);
					Character[] charTemp = currentChar.getPlayer().getCharacters();
					for (int i = 0; i < CHAR_MAX; i++) {
						if (!charTemp[i].isDoracity()) {
							charTemp[i].setDefP(charTemp[i].getDefP() + 1);
							charTemp[i].setDefM(charTemp[i].getDefM() + 1);
						}
					}
				} else {
					return 10;
				}

				break;
			case 22: // Wall Doracity
				if (currentChar.isDoracity()) {
					if (currentChar.getEquipment() != null) {
						removeEquipmentEffect();
					}
					currentChar.setEquipment((Equipment) card);
					Character[] charTemp = currentChar.getPlayer().getCharacters();
					for (int i = 0; i < CHAR_MAX; i++) {
						if (charTemp[i].isDoracity()) {
							charTemp[i].setDefP(charTemp[i].getDefP() + 1);
							charTemp[i].setDefM(charTemp[i].getDefM() + 1);
						}
					}
				} else {
					return 11;
				}

				break;
			case 23: // Preemptive Claw
				break;

			}

		} else if (card instanceof Item) { // Item Card
			switch (card.getNumber()) {
			case 1: // HP Potion
				player.changeHP(5);
				break;
			case 2: // MP Potion
				player.changeMP(5);
				break;
			case 3: // Smoke Bomb
				// TODO: Ignore attack once
				break;
			}

		} else if (card instanceof Skill) { // Skill Card
			switch (card.getNumber()) {
			case 1: // Assault (Sudden attack)
				break;
			case 2: // Punch
				break;
			case 3: // Battle Soul
				break;
			case 4: // Immortal (Never die)
				break;
			case 5: // Snipe (Chase attack)
				break;
			case 6: // Support Shot
				break;
			case 7: // Evasion
				break;
			case 8: // Eagle Eye
				break;
			case 9: // Fire Rain
				break;
			case 10: // Freezing Ice
				break;
			case 11: // Thunder
				break;
			case 13: // Great Healing: HP+10 MP-7
				if (player.changeMP(-7) == 0)
					player.changeHP(10);
				break;
			case 15: // Mute
				break;
			case 17: // Jail
				break;
			case 18: // Physical field
				break;
			case 19: // Mana field
				break;
			}
		}

		return 0;
	}

	/**
	 * Every time you remove an equipment from a character you must call this.
	 */
	protected void removeEquipmentEffect() { // remove equipment for currentChar

		if (currentChar.getEquipment() == null)
			return;

		switch (currentChar.getEquipment().getNumber()) {
		case 1:
		case 2: // Adventurer's Sword
			currentChar.setAttack(currentChar.getAttack() - 1);
			break;
		case 3: // Floating Shoes
			currentChar.setSpeed(currentChar.getSpeed() - 1);
			break;
		case 4: // Gambeson (Cloth armor)
			currentChar.setDefP(currentChar.getDefP() - 1);
			break;
		case 5: // Mana-resistant Cloak
			currentChar.setDefM(currentChar.getDefM() - 1);
			break;
		case 6: // Armor of Knight

			break;
		case 7: // Feather Bow

			break;
		case 8: // Caster's Hat

			break;
		case 9: // Nurses' Uniform

			break;
		case 10: // Sickle of Vampire

			break;
		case 11: // Blade of Truth

			break;
		case 12: // Dual Shadows

			break;
		case 13: // Sniper Rifle (Chase attack guns)

			break;
		case 14: // Holy Shield
			currentChar.setDefP(currentChar.getDefP() - 1);
			Character[] charTemp14 = currentChar.getPlayer().getCharacters();
			for (int i = 0; i < CHAR_MAX; i++) {
				charTemp14[i].setDefP(charTemp14[i].getDefP() - 1);
			}
			break;
		case 15: // Berserk Shoes

			break;
		case 16: // Ice Wand

			break;
		case 17: // Fire Wand

			break;
		case 18: // Thunder Wand

			break;
		case 19: // Sorcerer's Gown

			break;
		case 20: // Healing Wand

			break;
		case 21: // Field Academy
			Character[] charTemp21 = currentChar.getPlayer().getCharacters();
			for (int i = 0; i < CHAR_MAX; i++) {
				if (!charTemp21[i].isDoracity()) {
					charTemp21[i].setDefP(charTemp21[i].getDefP() - 1);
					charTemp21[i].setDefM(charTemp21[i].getDefM() - 1);
				}
			}
			break;
		case 22: // Wall Doracity
			Character[] charTemp22 = currentChar.getPlayer().getCharacters();
			for (int i = 0; i < CHAR_MAX; i++) {
				if (charTemp22[i].isDoracity()) {
					charTemp22[i].setDefP(charTemp22[i].getDefP() - 1);
					charTemp22[i].setDefM(charTemp22[i].getDefM() - 1);
				}
			}
			break;
		case 23: // Preemptive Claw
			break;

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
