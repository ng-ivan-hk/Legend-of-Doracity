import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Stack;

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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultCaret;

/**
 * This is the main GUI for the game.
 * 
 * @author Ivan Ng
 * 
 */
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
			if (c1.getSpeed() == c2.getSpeed())
				return c1.getPlayer().isPlayer1() ? -1 : 1;
			else
				return c2.getSpeed() - c1.getSpeed();
		}
	};

	/* Static Variables */
	private static JTextArea LOG_AREA = null;

	/* Non-Static Variables */
	private Player player1 = null; // Player who attacks first
	private Player player2 = null; // Player who attacks next
	private Stack<Card> cards = null; // card stack on the table
	private ArrayList<Character> charList = null;
	private Character currentChar = null;
	private int round = 0;
	private int currentStatus = 0; // For checking occasion in battle

	/* GUI objects */
	private DisplayArea displayArea = null;
	private PlayerArea player1Area = null;
	private PlayerArea player2Area = null;

	public static void main(String[] args) throws InterruptedException {
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
			setIconImage(new ImageIcon(getClass().getResource("/resources/xander.png")).getImage());
		} catch (NullPointerException e) {
		}

		setTitle(Lang.frameTitle);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		/* Run Preload GUI */
		Preload preload = new Preload(this);
		add(preload);
		setJMenuBar(new MenuBar());
		setSize(new Dimension(600, 400));
		locateCenter();
		setResizable(false);
		setVisible(true);

		synchronized (this) {
			this.wait();
		}

		/* Run main game GUI */
		setVisible(false);
		remove(preload);

		add(BorderLayout.CENTER, displayArea = new DisplayArea());
		add(BorderLayout.WEST, player1Area = new PlayerArea(player1));
		add(BorderLayout.EAST, player2Area = new PlayerArea(player2));

		revalidate();
		pack();
		setMinimumSize(getBounds().getSize());
		setSize(new Dimension(1050, 600));
		locateCenter();
		setResizable(true);
		setVisible(true);
	}

	/**
	 * Locate the window at center.
	 */
	private void locateCenter() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2
				- this.getSize().height / 2);
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
			// Add Menu Items
			optionMenu.add(helpMenuItem = new JMenuItem(Lang.menu_help));
			optionMenu.add(aboutMenuItem = new JMenuItem(Lang.menu_about));
			optionMenu.add(new JSeparator());
			optionMenu.add(exitMenuItem = new JMenuItem(Lang.menu_exit));
			// Add Action Listener for Menu Items
			helpMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(Play.this, "<html><font size=3>"
							+ Lang.menu_helpInfo + "</html>", Lang.menu_help,
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
			aboutMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(Play.this, "<html>" + Lang.menu_aboutInfo
							+ "</html>", Lang.menu_about, JOptionPane.INFORMATION_MESSAGE);
				}
			});
			exitMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
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

				// Row 1 - 6
				Character[] player1CharTemp = player1.getCharacters();
				Character[] player2CharTemp = player2.getCharacters();
				for (int i = 0; i < CHAR_MAX; i++) {

					add(player1Chars[i] = new CharLabel(player1CharTemp[i]));
					player1Chars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					player1Chars[i].setBackground(Color.WHITE);
					player1Chars[i].setOpaque(true); // paint the background

					add(player1Actions[i] = new BLabel("Player1 " + i));
					add(player2Actions[i] = new BLabel("Player2 " + i));

					player2Chars[i] = new CharLabel(player2CharTemp[i]);
					player2Chars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					player2Chars[i].setBackground(Color.WHITE);
					player2Chars[i].setOpaque(true); // paint the background
					add(player2Chars[i]);
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
						player1Chars[index].setBackground(Color.CYAN);
					} else {
						player1Chars[index].setBackground(Color.WHITE);
					}
				} else {
					if (b) {
						player2Chars[index].setBackground(Color.CYAN);
					} else {
						player2Chars[index].setBackground(Color.WHITE);
					}
				}
			}

			public void updateAllLabels() {

				for (int i = 0; i < CHAR_MAX; i++) {
					player1Chars[i].updateLabel();
					player2Chars[i].updateLabel();
					// player2Chars[i].setToolTipText("<html><img src=" +
					// getClass().getResource("test.png") + "></html>");
				}
			}

			public void updateCharOrder() {
				// Row 1 - 6
				Character[] player1CharTemp = player1.getCharacters();
				Character[] player2CharTemp = player2.getCharacters();
				for (int i = 0; i < CHAR_MAX; i++) {

					player1Chars[i].changeChar(player1CharTemp[i]);
					player2Chars[i].changeChar(player2CharTemp[i]);
				}
			}

			private class BLabel extends JLabel {
				public BLabel(String text) {
					super(text);
					setHorizontalAlignment(SwingConstants.CENTER);
				}
			}

			private class CharLabel extends BLabel {
				private Character character = null;

				public CharLabel(Character character) {
					super("");
					this.character = character;
				}

				public void changeChar(Character character) {
					this.character = character;
					updateLabel();
				}

				/**
				 * Call this function to update the CharLabel whenever some data
				 * of the character has been changed.
				 */
				public void updateLabel() {
					setText(//@formatter:off
							// Line 1
							"<html><u>" + character.getTitle() + " " + character.toString()
							+ "</u><br>" + 
							// Line 2
							character.getJobName() + " (" + Lang.property + ": "
							+ (character.isPhysical() ? Lang.physical : Lang.mana) + ")<br>" + 
							// Line 3
							Lang.attack + ": " + character.getAttack() + " " + Lang.defP + ": "
							+ character.getDefP() + " " + Lang.defM + ": " + character.getDefM()
							+ " " + Lang.speed + ": " + character.getSpeed() + "<br> " + 
							// Line 4
							Lang.side
							+ ": " + (character.isDoracity() ? Lang.doracity : Lang.academy)
							+ "<br>" +
							// Line 5
							(character.getEquipment() == null ? "" :
							Lang.equipment + ": <font color=blue>" 
							+ character.getEquipmentName() + "</font>") + "</html>"
							// @formatter:on
					);

					setToolTipText("<html>" + Lang.equipment + ": <font color=blue>"
							+ character.getEquipmentName() + "</font><br><font color=green>"
							+ character.getEquipmentInfo() + "</font></html>");
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
				logArea.setEditable(false);

				// Update Log Area automatically whenever text is appended
				((DefaultCaret) logArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

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

		public void updateArea() {

			cardArea.removeAll();

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

		private class AttackButton extends JButton implements ActionListener {
			public AttackButton() {
				setText(Lang.normalAttack);
				setToolTipText(Lang.normalAttackInfo);
				setEnabled(false);
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				new CharSelectDialog();
			}

			private class CharSelectDialog extends JDialog {
				public CharSelectDialog() {
					super(Play.this, true);
					setLocationRelativeTo(AttackButton.this);
					setTitle(Lang.charSelection);
					add(new CharSelectPanel());
					pack();
					setResizable(false);
					setVisible(true);
				}

				private class CharSelectPanel extends JPanel {
					public CharSelectPanel() {
						setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
						Character[] charTemp = player.isPlayer1() ? player2.getCharacters()
								: player1.getCharacters();
						for (int i = 0; i < CHAR_MAX; i++) {
							add(new CharButton(charTemp[i]));
						}
					}

					private class CharButton extends JButton implements ActionListener {
						private Character character = null;

						public CharButton(Character character) {
							this.character = character;
							setText(character.toString());
							addActionListener(this);
						}

						@Override
						public void actionPerformed(ActionEvent e) {
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
			public void actionPerformed(ActionEvent e) {
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
							setText(charSkill.getName());

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
						public void actionPerformed(ActionEvent e) {
							charSkill.useSkill(currentChar, null); // TODO:testing

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
			public void actionPerformed(ActionEvent e) {
				if (player.changeMP(-15) == 1) { // MP not enough
					JOptionPane.showMessageDialog(this, Lang.noMP);
					return;
				}
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

		while (true) { // Loop until HP <= 0

			// Start a new round
			round++;
			displayArea.setRound(round);
			printlnLog("========== " + Lang.round + round + " ==========");

			// Let's begin!
			stageDrawCards();
			stagePrepare();
			stageBeforeBattle();
			stageDuringBattle();
			stageAfterBattle();

			// Defense off for all characters
			for (int i = 0; i < charList.size(); i++) {
				charList.get(i).setDefense(false);
			}

		}
	}

	private void stageDrawCards() throws InterruptedException {
		/* === Draw Cards === */

		displayArea.setStage(Lang.stage_drawCards);
		printlnLog(">>>" + Lang.stage_drawCards + "<<<");

		if (round == 1) { // give each player 5 cards //TODO debug

			for (int i = 0; i < 5; i++)
				draw(player1);
			for (int i = 0; i < 5; i++)
				draw(player2);
		} else if (cards.size() >= 8) { // give each player at most 2 cards

			// Player 1
			player1Area.drawButton.setDrawCard(DRAW_CARD_MAX);
			player1Area.passButton.setEnabled(true);
			synchronized (this) {
				// wait until the draw or pass button is pressed
				this.wait();
			}
			player1Area.drawButton.setEnabled(false);
			player1Area.passButton.setEnabled(false);
			// Player 2
			player2Area.drawButton.setDrawCard(DRAW_CARD_MAX);
			player2Area.passButton.setEnabled(true);
			synchronized (this) {
				this.wait();
			}
			player2Area.drawButton.setEnabled(false);
			player2Area.passButton.setEnabled(false);
		}
		player1.listCards();
		player2.listCards();
	}

	private void stagePrepare() throws InterruptedException {
		/* === Prepare === */
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
		// Player 1
		int healMP = 0;
		Character[] playerCharTemp = player1.getCharacters();
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
		}
		System.out.println("Final Heal MP" + healMP);
		player1.changeMP(healMP);
		// Player 2
		healMP = 0;
		playerCharTemp = player2.getCharacters();
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
		}
		player2.changeMP(healMP);

		player1Area.updateArea();
		player2Area.updateArea();

		player1.listStatus();
		player2.listStatus();

		/* Use Item */
		displayArea.setStage(Lang.stage_prepare + " (" + Lang.stage_useItem + ")");
		// Player 1
		player1Area.setEnableItem(true);
		player1Area.passButton.setEnabled(true);
		synchronized (this) {
			this.wait();
		}
		player1Area.setEnableItem(false);
		player1Area.passButton.setEnabled(false);
		// Player2
		player2Area.setEnableItem(true);
		player2Area.passButton.setEnabled(true);
		synchronized (this) {
			this.wait();
		}
		player2Area.setEnableItem(false);
		player2Area.passButton.setEnabled(false);

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
			PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area : player2Area;
			areaTemp.jobChangeButton.setEnabled(true);
			areaTemp.passButton.setEnabled(true);
			areaTemp.setEnableEquipment(true);
			synchronized (this) {
				this.wait();
			}
			areaTemp.jobChangeButton.setEnabled(false);
			areaTemp.passButton.setEnabled(false);
			areaTemp.setEnableEquipment(false);

			// Unhighlight the character
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), false);

		}
	}

	private void stageBeforeBattle() throws InterruptedException {
		/* === Before Battle === */
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
			PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area : player2Area;
			areaTemp.castSkillButton.setEnabled(true);
			areaTemp.passButton.setEnabled(true);
			areaTemp.setEnableSkill(true);
			synchronized (this) {
				this.wait();
			}
			areaTemp.castSkillButton.setEnabled(false);
			areaTemp.passButton.setEnabled(false);
			areaTemp.setEnableSkill(false);

			// Unhighlight the character
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), false);

		}
	}

	private void stageDuringBattle() throws InterruptedException {
		/* === During Battle === */
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
			PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area : player2Area;
			areaTemp.attackButton.setEnabled(true);
			areaTemp.castSkillButton.setEnabled(true);
			areaTemp.passButton.setEnabled(true);
			areaTemp.setEnableSkill(true);
			synchronized (this) {
				this.wait();
			}
			areaTemp.attackButton.setEnabled(false);
			areaTemp.castSkillButton.setEnabled(false);
			areaTemp.passButton.setEnabled(false);
			areaTemp.setEnableSkill(false);

			// Unhighlight the character
			displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar.getPlayer()
					.indexOfChar(currentChar), false);

		}
	}

	private void stageAfterBattle() throws InterruptedException {
		/* === After Battle === */
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
			PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area : player2Area;
			areaTemp.castSkillButton.setEnabled(true);
			areaTemp.passButton.setEnabled(true);
			synchronized (this) {
				this.wait();
			}
			areaTemp.castSkillButton.setEnabled(false);
			areaTemp.passButton.setEnabled(false);

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
