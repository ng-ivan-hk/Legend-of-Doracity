/**
 * Represents a character's skill (whether it's active or passive)
 * 
 * @author Ivan Ng
 * 
 */
public class CharSkill {

	/* CharSkill */
	public final static int SELECT_NO_CHAR = 0;
	public final static int SELECT_ONE_CHAR = 1;

	private Character character; // Which character owns this skill?
	private boolean active; // if false, passive
	private int number; // Which skill is this? (refer to Lang)
	private int occasion; // When can be this used? (see class Command)
	private CharSkillMethod skillMethod; // Stores the method

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
	 */
	public CharSkill(Character character, boolean active, int number, int occasion,
			CharSkillMethod skillMethod) {
		this.character = character;
		this.active = active;
		this.number = number;
		this.occasion = occasion;
		this.skillMethod = skillMethod;
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

	public String toString() {
		return (character.isFirstJob() ? Lang.CharSkills1 : Lang.CharSkills2)[character.getNumber()][active ? 1
				: 0][number][0];
	}

	public String getInfo() {
		return (character.isFirstJob() ? Lang.CharSkills1 : Lang.CharSkills2)[character.getNumber()][active ? 1
				: 0][number][1];
	}

}
