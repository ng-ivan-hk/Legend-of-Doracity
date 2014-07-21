import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * Represents a character's skill (whether it's active or passive)
 * 
 * @author Ivan Ng
 * 
 */
public class CharSkill {

	/* CharSelectPanel for CharSkill */
	@SuppressWarnings("serial")
	public static class CharSelectDialog extends JDialog {
		private Character currentChar = null;
		private Player opponent = null; // be null if list my character's only
		private TargetMethod method = null;

		/**
		 * Select a character from the opponent player.
		 * 
		 * @param currentChar
		 * @param opponent
		 * @param method
		 */
		public CharSelectDialog(Character currentChar, Player opponent, TargetMethod method) {
			super((java.awt.Frame) null, true);
			this.currentChar = currentChar;
			this.opponent = opponent;
			this.method = method;

			setTitle(Lang.charSelection);
			add(new CharSelectPanel(true));
			pack();
			Play.locateCenter(this);
			setResizable(false);
			setVisible(true);
		}

		/**
		 * Select a character from myself.
		 * 
		 * @param currentChar
		 * @param method
		 */
		public CharSelectDialog(Character currentChar, TargetMethod method) {
			this.currentChar = currentChar;
			this.method = method;

			setTitle(Lang.charSelection);
			add(new CharSelectPanel(false));
			pack();
			setResizable(false);
			setVisible(true);
		}

		public class CharSelectPanel extends SuperCharSelectPanel {
			/**
			 * @param listOpponent
			 *            Pass true if list opponent's characters, false if list
			 *            my characters
			 */
			public CharSelectPanel(boolean listOpponent) {
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

				if (listOpponent) {
					Character[] charTemp = opponent.getCharacters();
					for (int i = 0; i < Play.CHAR_MAX; i++) {
						add(charTemp[i]);
					}
					// For Tea's skill
					Tea.checkDoM(currentChar, opponent, this);
				} else { // List my character
					Character[] charTemp = currentChar.getPlayer().getCharacters();
					for (int i = 0; i < Play.CHAR_MAX; i++) {
						add(charTemp[i]);
					}
				}
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
					CharSelectDialog.this.dispose();
					Play.printlnLog(currentChar + Lang.choose + character);
					method.targetMethod(currentChar, character);
				}
			}
		}

	}

	/* CardSelectDialog for CharSkill */
	@SuppressWarnings("serial")
	public static class CardSelectDialog extends JDialog {
		private Player player = null;
		private TargetMethod method = null;

		public CardSelectDialog(Player player, TargetMethod method) {
			super((java.awt.Frame) null, true);
			this.player = player;
			this.method = method;

			setTitle(Lang.cardSelection);
			add(new CardSelectPanel()); // TODO: JScrollPane?
			pack();
			Play.locateCenter(this);
			setResizable(false);
			setVisible(true);
		}

		public class CardSelectPanel extends JPanel {

			public CardSelectPanel() {
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

				ArrayList<Card> cards = player.getHandCards();

				for (int i = cards.size() - 1; i >= 0; i--) {
					add(new CardButton(cards.get(i)));
				}
			}

			public class CardButton extends JButton implements ActionListener {
				private Card card = null;

				public CardButton(Card card) {
					this.card = card;
					setText(card.toString());
					addActionListener(this);
				}

				@Override
				public void actionPerformed(ActionEvent evt) {
					CardSelectDialog.this.dispose();
					player.removeCard(card);
					method.targetMethod(null, null);
				}
			}
		}

	}

	/* CharSkill */
	public final static int SELECT_NO_CHAR = 0;
	public final static int SELECT_ONE_CHAR = 1;

	private Character character; // Which character owns this skill?
	private boolean active; // if false, passive
	private int number; // Which skill is this? (refer to Lang)
	private int occasion; // When can be this used? (see class Command)
	private CharSkillMethod skillMethod; // Stores the method
	private int requiredMP; // How many MP is required for this skill?

	/**
	 * Create a character skill in setCharacter() from Character's subclass.
	 * 
	 * @param character
	 *            Which character does this skill belongs to?
	 * @param active
	 *            active skill? If false, passive
	 * @param number
	 *            If this character only has one skill, pass 0. Otherwise,
	 *            increment for every skills.(E.g. A has 3 skills, then pass 0,
	 *            1, 2 for 3 skills respectively.)
	 * @param occasion
	 *            See class Command: NA, BEFORE_BATTLE, DURING_BATTLE,
	 *            AFTER_BATTLE
	 * @param skillMethod
	 *            Pass the function that really does the skill
	 * @param requiredMP
	 *            How many MP is required for this skill?
	 */
	public CharSkill(Character character, boolean active, int number, int occasion,
			CharSkillMethod skillMethod, int requiredMP) {
		this.character = character;
		this.active = active;
		this.number = number;
		this.occasion = occasion;
		this.skillMethod = skillMethod;
		this.requiredMP = requiredMP;
	}

	/**
	 * Call this to use the skill.
	 * 
	 * @param opponent
	 *            Opponent Player
	 */
	public void useSkill(Player opponent) {
		skillMethod.skillMethod(character, opponent);
	}

	public boolean isActive() {
		return active;
	}

	public Character getCharacter() {
		return character;
	}

	public int getOccasion() {
		return occasion;
	}

	public int getRequiredMP() {
		return requiredMP;
	}

	public String toString() {
		return (character.isFirstJob() ? Lang.CharSkills1 : Lang.CharSkills2)[character.getNumber()][active ? 1
				: 0][number][0];
	}

	public String getInfo() {
		return (character.isFirstJob() ? Lang.CharSkills1 : Lang.CharSkills2)[character.getNumber()][active ? 1
				: 0][number][1];
	}

}
