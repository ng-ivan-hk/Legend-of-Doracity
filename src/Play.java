import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**GitHub Started
 * @author Ivan Ng
 *
 */
public class Play extends JFrame {

	/* Constant Values */
	public final static int CHAR_MAX = 5; // No. of char per player
	public final static int DRAW_CARD_MAX = 3; // draw ? hand cards each turn
	public final static int[] EQUIPMENT_MAX = new int[] { 3, 3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1 };
	public final static int[] ITEM_MAX = new int[] { 10, 5, 2, 10, 5, 2, 5, 5 };
	public final static int[] SKILL_MAX = new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 3, 3, 5,
			5, 5, 2, 2, 3, 3 };
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

	/* Variables */
	private Player player1 = null; // Player who attacks first
	private Player player2 = null; // Player who attacks next
	private Stack<Card> cards = null; // card stack on the table
	private ArrayList<Character> charList = null;
	private Character currentChar = null;
	private int round = 0;

	/* GUI objects */
	private DisplayArea displayArea = null;
	private PlayerArea player1Area = null;
	private PlayerArea player2Area = null;

	public static void main(String[] args) throws InterruptedException {

		Play play = new Play();
		play.start();

	}

	/* Methods */

	public Play() {

		/* Create Players */
		player1 = new Player("Oolong Wong", true);
		player1.setCharacters(new Character(1, player1), new Character(2, player1), new Character(
				3, player1), new Character(4, player1), new Character(5, player1));
		player2 = new Player("Ivan Ng", false);
		player2.setCharacters(new Character(6, player2), new Character(7, player2), new Character(
				8, player2), new Character(9, player2), new Character(30, player2));

		/* Create Character list */
		charList = new ArrayList<Character>();
		charList.addAll(new ArrayList<Character>(Arrays.asList(player1.getCharacters())));
		charList.addAll(new ArrayList<Character>(Arrays.asList(player2.getCharacters())));

		/* Create card stack and shuffle */
		cards = new Stack<Card>();
		for (int i = 0; i < EQUIPMENT_MAX.length; i++) {
			for (int j = 0; j < EQUIPMENT_MAX[i]; j++) {
				if (i + 1 == 1 || i + 1 == 2 || i + 1 == 3 || i + 1 == 4 || i + 1 == 5
						|| i + 1 == 14 || i + 1 == 21 || i + 1 == 22) // DEBUG
					cards.push(new Equipment(i + 1));
			}
		}
//		 for (int i = 0; i < ITEM_MAX.length; i++) {
//		 for (int j = 0; j < ITEM_MAX[i]; j++) {
//		 cards.push(new Item(i + 1));
//		 }
//		 }
//		 for (int i = 0; i < SKILL_MAX.length; i++) {
//		 for (int j = 0; j < SKILL_MAX[i]; j++) {
//		 cards.push(new Skill(i + 1));
//		 }
//		 }
		Collections.shuffle(cards);

		// cards.push(new Skill(13));
		// cards.push(new Item(1));
		// cards.push(new Item(6));
		// cards.push(new Item(3));
		// cards.push(new Item(4));
		// cards.push(new Item(8));

		System.out.println("===== Ready to play! =====");

		/* Set up GUI */

		try {
			setIconImage(new ImageIcon(getClass().getResource("/resources/xander.png")).getImage());
		} catch (NullPointerException e) {
		}

		setTitle("League of Doracity");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		displayArea = new DisplayArea();
		player1Area = new PlayerArea(player1);
		player2Area = new PlayerArea(player2);

		getContentPane().add(BorderLayout.CENTER, displayArea);
		getContentPane().add(BorderLayout.WEST, player1Area);
		getContentPane().add(BorderLayout.EAST, player2Area);

		pack();
		setMinimumSize(getBounds().getSize());

		setSize(new Dimension(800, 600));

		setVisible(true);

	}

	private class DisplayArea extends JPanel {

		private TopField topField = null;
		private JLabel roundLabel = null;
		private JLabel stageLabel = null;

		protected BattleField battleField = null;

		private BottomField bottomField = null;
		private JLabel cardsLeft = null;

		public DisplayArea() {

			setLayout(new BorderLayout());

			/* Set Top Field */
			topField = new TopField();
			add(topField, BorderLayout.NORTH);

			/* Set Battle Field */
			battleField = new BattleField();
			add(battleField, BorderLayout.CENTER);

			/* Set Bottom Field */
			bottomField = new BottomField();
			add(bottomField, BorderLayout.SOUTH);

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
				setLayout(new GridLayout(0, 2));

				// Add Round Label
				roundLabel = new JLabel();
				roundLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				setRound(0);
				add(roundLabel);

				// Add Stage Label
				stageLabel = new JLabel(Lang.stage_readyToPlay);
				stageLabel.setHorizontalAlignment(SwingConstants.LEFT);
				add(stageLabel);
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
				add(new BLabel(Lang.player + ": " + player1.getName()));
				player1Actions[0] = new BLabel("player1");
				add(player1Actions[0]);
				player2Actions[0] = new BLabel("player2");
				add(player2Actions[0]);
				add(new BLabel(Lang.player + ": " + player2.getName()));

				// Row 1 - 6
				Character[] player1CharTemp = player1.getCharacters();
				Character[] player2CharTemp = player2.getCharacters();
				for (int i = 0; i < CHAR_MAX; i++) {

					player1Chars[i] = new CharLabel(player1CharTemp[i]);
					player1Chars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					player1Chars[i].setBackground(Color.WHITE);
					player1Chars[i].setOpaque(true); // paint the background
					add(player1Chars[i]);

					player1Actions[i] = new BLabel("Player1 " + i);
					add(player1Actions[i]);
					player2Actions[i] = new BLabel("Player2 " + i);
					add(player2Actions[i]);

					player2Chars[i] = new CharLabel(player2CharTemp[i]);
					player2Chars[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					player2Chars[i].setBackground(Color.WHITE);
					player2Chars[i].setOpaque(true); // paint the background
					add(player2Chars[i]);
				}

				updateAllLabels();

			}

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

				public void updateLabel() {
					setText("<html><u>" + character.toString()
							+ (character.getEquipment() == null ? "" : "+") + "</u><br>"
							+ character.getJobName() + " (" + Lang.property + ": "
							+ (character.isPhysical() ? Lang.physical : Lang.mana) + ")<br>"
							+ Lang.attack + ": " + character.getAttack() + " " + Lang.defP + ": "
							+ character.getDefP() + " " + Lang.defM + ": " + character.getDefM()
							+ " " + Lang.speed + ": " + character.getSpeed() + "<br> " + Lang.side
							+ ": " + (character.isDoracity() ? Lang.doracity : Lang.academy));

					setToolTipText("<html>" + Lang.equipment + ": <font color=blue>"
							+ character.getEquipmentName() + "</font><br><font color=green>"
							+ character.getEquipmentInfo() + "</font></html>");
				}
			}

		}

		private class BottomField extends JPanel {
			public BottomField() {
				setBackground(Color.WHITE);
				cardsLeft = new JLabel();
				add(cardsLeft);
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
			add(new JLabel("--- " + player.getName() + " ---"));

			/* Add HP & MP meter */
			HPmeter = new JLabel();
			MPmeter = new JLabel();
			add(HPmeter);
			add(MPmeter);
			// Horizontal line
			JSeparator separator = new JSeparator();
			Dimension d = separator.getPreferredSize();
			d.width = separator.getMaximumSize().width;
			separator.setMaximumSize(d);
			add(separator);

			/* Add General Buttons */
			attackButton = new AttackButton();
			add(attackButton);
			castSkillButton = new CastSkillButton();
			add(castSkillButton);
			jobChangeButton = new JobChangeButton();
			add(jobChangeButton);
			drawButton = new DrawButton();
			add(drawButton);
			passButton = new PassButton();
			add(passButton);

			JSeparator separator1 = new JSeparator(); // Horizontal line;
			separator1.setMaximumSize(d);
			add(separator1);

			/* Add Card Area */
			cardArea = new JPanel();
			cardArea.setLayout(new BoxLayout(cardArea, BoxLayout.PAGE_AXIS));
			// Create a scroll panel and put the card area into it
			JScrollPane scrollPanel = new JScrollPane(cardArea);
			scrollPanel.getVerticalScrollBar().setUnitIncrement(20);
			add(scrollPanel);

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
					setVisible(true);
				}

				private class SkillPanel extends JPanel {
					public SkillPanel() {
						setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
						// Add Passive Skills
						add(new JLabel(Lang.passive));

//						String[][] charSkills = currentChar.getPassiveSkills();
//						for (int i = 0; i < charSkills.length; i++) {
//							add(new JLabel(charSkills[i][0]));
//							add(new JLabel(charSkills[i][1]));
//						}
//						// Horizontal line
//						JSeparator separator = new JSeparator();
//						Dimension d = separator.getPreferredSize();
//						d.width = separator.getMaximumSize().width;
//						separator.setMaximumSize(d);
//						add(separator);
//						// Add Active Skills
//						charSkills = currentChar.getActiveSkills();
//						add(new JLabel(Lang.active));
//						for (int i = 0; i < charSkills.length; i++) {
//							add(new JLabel(charSkills[i][0]));
//							add(new JLabel(charSkills[i][1]));
//						}

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

	public void start() throws InterruptedException {

		while (true) { // Loop until HP <= 0

			/* Start a new round */
			round++;
			displayArea.setRound(round);
			System.out.println("========== Round " + round + " ==========");

			/* === Draw Cards === */

			displayArea.setStage(Lang.stage_drawCards);
			System.out.println(">>> Draw Cards <<<");

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

			/* === Prepare === */
			sortAllChars();
			displayArea.setStage(Lang.stage_prepare + " (" + Lang.stage_autoHealing + ")");
			System.out.println(">>> Prepare <<<");

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
				case Character.SABER: // And ARCHER
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
			// Player 2
			player1.changeMP(healMP);
			healMP = 0;
			playerCharTemp = player2.getCharacters();
			for (int i = 0; i < CHAR_MAX; i++) {
				switch (playerCharTemp[i].getJob()) {
				case Character.SABER: // or ARCHER
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

				System.out.println(currentChar);
				System.out.println(currentChar.getPlayer().indexOfChar(currentChar));

				// Highlight the character on the battle field
				displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar
						.getPlayer().indexOfChar(currentChar), true);

				// Handle Buttons
				PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
						: player2Area;
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
				displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar
						.getPlayer().indexOfChar(currentChar), false);

			}

			/* === Before Battle === */
			sortAllChars();
			System.out.println(">>> Before Battle <<<");
			displayArea.setStage(Lang.stage_beforeBattle);

			// Skills/Skill Card according to charList
			for (int i = 0; i < charList.size(); i++) {
				currentChar = charList.get(i);

				System.out.println(charList.get(i));

				// Highlight the character on the battle field
				displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar
						.getPlayer().indexOfChar(currentChar), true);

				// Handle Buttons
				PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
						: player2Area;
				areaTemp.castSkillButton.setEnabled(true);
				// TODO: enable skill buttons
				areaTemp.passButton.setEnabled(true);
				synchronized (this) {
					this.wait();
				}
				areaTemp.castSkillButton.setEnabled(false);
				// TODO: enable skill buttons
				areaTemp.passButton.setEnabled(false);

				// Unhighlight the character
				displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar
						.getPlayer().indexOfChar(currentChar), false);

			}

			/* === During Battle === */
			sortAllChars();
			System.out.println(">>> During Battle <<<");
			displayArea.setStage(Lang.stage_duringBattle);

			// Normal Attack/Skills/Skill Card according to charList

			for (int i = 0; i < charList.size(); i++) {

				currentChar = charList.get(i);

				// Highlight the character on the battle field
				displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar
						.getPlayer().indexOfChar(currentChar), true);

				// Handle Buttons
				PlayerArea areaTemp = currentChar.getPlayer().isPlayer1() ? player1Area
						: player2Area;
				areaTemp.attackButton.setEnabled(true);
				areaTemp.passButton.setEnabled(true);
				synchronized (this) {
					this.wait();
				}
				areaTemp.attackButton.setEnabled(false);
				areaTemp.passButton.setEnabled(false);

				// Unhighlight the character
				displayArea.battleField.highlightChar(currentChar.getPlayer(), currentChar
						.getPlayer().indexOfChar(currentChar), false);

			}

			/* === After Battle === */
			sortAllChars();
			System.out.println(">>> After Battle <<<");
			displayArea.setStage(Lang.stage_afterBattle);

			// Skills according to charList
			// for (int i = 0; i < charList.size(); i++) {
			// System.out.println(charList.get(i));
			// }

			// Defense off for all characters
			for (int i = 0; i < charList.size(); i++) {
				charList.get(i).setDefense(false);
			}

		}
	}

	public void sortAllChars() {
		/* Reorder characters according to their speeds */
		// Reorder the 10-chars list
		Collections.sort(charList, charComparator);
		// Reorder each players'
		player1.sortChars();
		player2.sortChars();
		displayArea.battleField.updateCharOrder();
	}

	public void draw(Player player) {

		// System.out.println("--- Player: " + player.getName()
		// + " is drawing a card...");
		if (cards.empty()) {
			System.out.println(Lang.noCardsLeft);
		} else {
			// Pop the top card from the stack into player's hand
			Card temp = cards.pop();
			// System.out.println("Get! " + temp);
			player.addCard(temp);

			displayArea.setCardsLeft(cards.size());

		}
	}

	public int useCard(Player player, Card card) {

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
			case 1: // Small HP Potion
				player.changeHP(2);
				break;
			case 2: // Middle HP Potion
				player.changeHP(5);
				break;
			case 3: // Large HP Potion
				player.changeHP(10);
				break;
			case 4: // Small MP Potion
				player.changeMP(2);
				break;
			case 5: // Middle MP Potion
				player.changeMP(5);
				break;
			case 6: // Large MP Potion
				player.changeMP(10);
				break;
			case 7: // Smoke Bomb
				// TODO: Ignore attack once
				break;
			case 8: // Throwing Knife
				if (player.isPlayer1()) {
					player2.changeHP(-1);
				} else {
					player1.changeHP(-1);
				}
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
			case 12: // Healing: HP+5 MP-3
				if (player.changeMP(-3) == 0)
					player.changeHP(5);
				break;
			case 13: // Great Healing: HP+10 MP-10
				if (player.changeMP(-10) == 0)
					player.changeHP(10);
				break;
			case 14: // Wind
				break;
			case 15: // Mute
				break;
			case 16: // Silence
				break;
			case 17: // Jail
				break;
			case 18: // Physical field
				break;
			case 19: // Mana field
				break;
			case 20: // Holy Blessing
				break;
			case 21: // Blood Sucking
				break;
			}
		}

		return 0;
	}

	public void removeEquipmentEffect() { // remove equipment for currentChar

		// TODO: remove equip

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

	

	private void gameOver(Player winner, Player loser) {
		JOptionPane.showMessageDialog(this,
				Lang.gameOver + "\n\n" + Lang.winner + ": " + winner.getName() + "\n" + Lang.loser
						+ ": " + loser.getName());

		setEnabled(false);

	}

}
