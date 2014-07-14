/**
 * This represents a Character. It is the superclass for all characters.
 * 
 * @author Ivan Ng
 * 
 */
abstract public class Character {

	/* Static Values for Job */
	public final static int SABER = 0;
	public final static int ARCHER = 1;
	public final static int CASTER = 2;
	public final static int SUPPORT = 3;
	public final static int NA = 4;

	/* Character properties */
	private Player player; // Player object (who owns the character?)
	private int number; // number of the character
	private boolean male; // if false, female
	private int job; // from 0 to 3 (see static field "jobs")
	private boolean physical; // if false, mana
	private int attack;
	private int defP; // physical defense
	private int defM; // magical defense
	private int speed; // AT (who moves first)
	private boolean doracity; // if false, academy

	protected CharSkill[] passiveSkills = null;
	protected CharSkill[] activeSkills = null;

	/* Character variables */
	private boolean firstJob = true; // if false, second job
	private Equipment equipment = null;
	private boolean defense = false; // character will defense if attacked

	/**
	 * @param player
	 *            Player object (who owns this character?)
	 * @param number
	 *            Character's number (E.g. Tea is C-001 therefore pass 1)
	 */
	public Character(Player player, int number) {
		this.player = player;
		this.number = number;
		setCharacter();
	}

	public void jobChange() {
		firstJob = !firstJob;
		setCharacter();
	}

	abstract protected void setCharacter();

	/**
	 * @param male
	 *            male->true, female->false
	 * @param job
	 *            SABER, ARCHER, CASTER, SUPPORT, NA
	 * @param physical
	 *            Physical->true, Magical->false
	 * @param attack
	 *            Attack
	 * @param defP
	 *            Physical Defense
	 * @param defM
	 *            Magical Defense
	 * @param speed
	 *            Speed
	 * @param doracity
	 *            Doracity->true, Academy->false
	 */
	protected void setValues(boolean male, int job, boolean physical, int attack, int defP,
			int defM, int speed, boolean doracity) {
		this.male = male;
		this.job = job;
		this.physical = physical;
		this.attack = attack;
		this.defP = defP;
		this.defM = defM;
		this.speed = speed;
		this.doracity = doracity;
	}

	public int getNumber() {
		return number;
	}

	public Player getPlayer() {
		return player;
	}

	public String toString() {
		return Lang.CharNames[number];
	}

	public String getTitle() {
		return (firstJob ? Lang.CharTitles1 : Lang.CharTitles2)[number];
	}

	public boolean isMale() {
		return male;
	}

	public int getJob() {
		return job;
	}

	public String getJobName() {
		return Lang.JobNames[job];
	}

	public boolean isPhysical() {
		return physical;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getAttack() {
		return attack;
	}

	public void setDefP(int defP) {
		this.defP = defP;
	}

	public int getDefP() {
		return defP;
	}

	public void setDefM(int defM) {
		this.defM = defM;
	}

	public int getDefM() {
		return defM;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public boolean isDoracity() {
		return doracity;
	}

	public boolean isFirstJob() {
		return firstJob;
	}

	public void setEquipment(Equipment e) {
		this.equipment = e;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public String getEquipmentName() {
		if (equipment == null) {
			return Lang.none;
		} else {
			return equipment.toString();
		}
	}

	public String getEquipmentInfo() {
		if (equipment == null) {
			return "";
		} else {
			return equipment.getInfo();
		}
	}

	public void setDefense(boolean b) {
		defense = b;
	}

	public boolean isDefense() {
		return defense;
	}

	public int attack(Character target) { // Normal Attack (c1->c2)

		// Return 0 if success
		// Return 1 if failed
		// Return 2 if c2's defense is on
		// Return 3 if opponent is dead (HP < 0)

		if (target.isDefense()) {
			return 2;
		}

		// Calculate damage
		int c2Def = isPhysical() ? target.getDefP() : target.getDefM();
		int damage = getAttack() - c2Def;
		
		Play.printlnLog(this + " " + Lang.attack + " " + target + " --- ");

		Play.printLog((isPhysical() ? Lang.physical : Lang.mana) + Lang.attack + getAttack()
				+ " -> " + (isPhysical() ? Lang.defP : Lang.defM) + c2Def + " --- ");

		if (damage < 0) { // Attack failed
			Play.printLog(Lang.log_attackFailed);
			target.setDefense(true);
			return 1;
		} else { // Attack success
			Play.printLog(Lang.log_attackSuccess);
			target.setDefense(true);
		}

		if (target.getPlayer().changeHP(-damage) == 1) // Opponent dead
			return 3;

		return 0;
	}

	// public String[][] getPassiveSkills() {
	// return (firstJob ? Lang.CharSkills1 : Lang.CharSkills2)[number][0];
	// }
	//
	// public String[][] getActiveSkills() {
	// return (firstJob ? Lang.CharSkills1 : Lang.CharSkills2)[number][1];
	// }

}
