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

	/* Character's initial properties */
	// Modify only when create character or job change
	private Player player; // Player object (who owns the character?)
	private int number; // number of the character
	private boolean male; // if false, female
	private int job; // from 0 to 3 (see static field "jobs")
	private boolean physical; // if false, mana (Phoebell can modify this)
	private int attack_init;
	private int defP_init; // physical defense
	private int defM_init; // mana defense
	private int speed_init; // AT (who moves first)
	private boolean doracity; // if false, academy
	protected int jobChangeMP = 15; // How many MP is required for job change

	/* Character's current properties */
	private int attack;
	private int defP;
	private int defM;
	private int speed;

	protected CharSkill[] passiveSkills = null;
	protected CharSkill[] activeSkills = null;

	/* Character variables */
	private boolean firstJob = false; // if false, second job
	private Equipment equipment = null;
	protected boolean defense = false; // character will defense if attacked
	protected boolean assassin = false; // Map's Active Skill
	private boolean giveUp = false; // if true, this character will be passed

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

	final public void jobChange() {
		firstJob = !firstJob;
		setCharacter();
		jobChangeExtra();
	}

	/**
	 * Override this if the character needs to do something else other than
	 * jobChange() during jog change.
	 */
	public void jobChangeExtra() {
	}

	/**
	 * Set character properties and skills when it is created or job change.
	 */
	abstract protected void setCharacter();

	/**
	 * Override this if the character needs to do something when a game starts.
	 */
	public void gameStart() {
	}

	/**
	 * Do something when a round ends.
	 */
	final public void roundEnd() {
		// Defense off
		defense = false;
		// Give Up off
		giveUp = false;
		// Map's Assassin off
		assassin = false;
		// Any other things?
		roundEndExtra();
	}

	/**
	 * Override this if the character needs to do something else other than
	 * roundEnd() when a round ends.
	 */
	public void roundEndExtra() {
	}

	/**
	 * Call this in setCharacter() to set character's properties.
	 * 
	 * @param male
	 *            male->true, female->false
	 * @param job
	 *            SABER, ARCHER, CASTER, SUPPORT, NA
	 * @param physical
	 *            Physical->true, Mana->false
	 * @param attack
	 *            Attack
	 * @param defP
	 *            Physical Defense
	 * @param defM
	 *            Mana Defense
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
		this.attack = this.attack_init = attack;
		this.defP = this.defP_init = defP;
		this.defM = this.defM_init = defM;
		this.speed = this.speed_init = speed;
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

	/**
	 * Used by Phoebell only.
	 * 
	 * @param b
	 */
	public void setPhysical(boolean b) {
		if (this instanceof Phoebell)
			physical = b;
	}

	public boolean isPhysical() {
		return physical;
	}

	public void setAttack(int attack) {
		int diff = attack - this.attack;
		Play.printlnLog(this + " " + Lang.attack + (diff >= 0 ? "+" : "") + diff);
		this.attack = attack;
	}

	public int getAttack() {
		return attack;
	}

	public void setDefP(int defP) {
		int diff = defP - this.defP;
		Play.printlnLog(this + " " + Lang.defP + (diff >= 0 ? "+" : "") + diff);
		this.defP = defP;
	}

	public int getDefP() {
		return defP;
	}

	public void setDefM(int defM) {
		int diff = defM - this.defM;
		Play.printlnLog(this + " " + Lang.defM + (diff >= 0 ? "+" : "") + diff);
		this.defM = defM;
	}

	public int getDefM() {
		return defM;
	}

	public void setSpeed(int speed) {
		int diff = speed - this.speed;
		Play.printlnLog(this + " " + Lang.speed + (diff >= 0 ? "+" : "") + diff);
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public boolean isDoracity() {
		return doracity;
	}
	
	public int getJobChangeMP(){
		return jobChangeMP;
	}

	public boolean isFirstJob() {
		return firstJob;
	}

	public int getInitAttack() {
		return attack_init;
	}

	public int getInitDefP() {
		return defP_init;
	}

	public int getInitDefM() {
		return defM_init;
	}

	public int getInitSpeed() {
		return speed_init;
	}

	public void setEquipment(Equipment e) {
		if (e == null) {
			Play.printlnLog(this + " " + Lang.log_removeEquipment);
		} else {
			Play.printlnLog(this + " " + Lang.log_equip + " " + e);
		}
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

	public void setAssassin(boolean b) {
		assassin = b;
	}

	public boolean isAssassin() {
		return assassin;
	}
	
	protected void setGiveUp(boolean b) {
		Play.printlnLog(this + Lang.giveUp);
		giveUp = b;
	}

	public boolean isGiveUp() {
		return giveUp;
	}

	final public int attack(Character target) { // Normal Attack (c1->c2)

		// Return 0 if success
		// Return 1 if failed
		// Return 2 if c2's defense is on
		// Return 3 if opponent is dead (HP < 0)

		if (target.isDefense()) {
			return 2;
		}
		
		/* Check skills */

		// Iron's job 2 passive skill heroic
		if (this instanceof Iron && !isFirstJob() && target.getJob() == SABER) {
			Play.printlnLog(Lang.iron_heroic);
			attack++;
		}

		/* Calculate damage */
		int c2Def = isPhysical() ? target.getDefP() : target.getDefM();
		int damage = getAttack() - c2Def;

		/* Print log and set defense */
		Play.printlnLog(this + " " + Lang.attack + " " + target + " --- ");

		Play.printLog((isPhysical() ? Lang.physical : Lang.mana) + Lang.attack + getAttack()
				+ " -> " + (isPhysical() ? Lang.defP : Lang.defM) + c2Def + " --- ");
		target.setDefense(true);

		if (damage < 0) { // Attack failed
			Play.printLog(Lang.log_attackFailed);
		} else {
			Play.printLog(Lang.log_attackSuccess);
			if (damage == 0) { // Attack success but no damage

			} else { // Attack success with damage!

				if (target instanceof Tea) {
					// If Tea's doM is on, damage - 1
					Tea tea = (Tea) target;
					if (tea.isDoM() && !isMale()) {
						damage--;
						Play.printlnLog(Lang.tea_DoM_lessDamage);
					}
				} else if (target instanceof Iron && target.isFirstJob()) {
					// Iron's job1 passive skill: damage - 1
					damage--;
					Play.printlnLog(Lang.iron_fortitude_lessDamage);
				}

				// If marked by Map's assassin, damage + 1
				if (target.isAssassin()) {
					damage++;
					Play.printlnLog(Lang.map_assassin_moreDamage);
				}

				/* True Damage */
				if (target.getPlayer().changeHP(-damage) == 1) // Opponent dead?
					return 3;
			}

		}

		/* === Commond Route for 3 cases === */

		// Check for Tea's MSoul
		if (target instanceof Tea) {
			Tea tea = (Tea) target;
			if (tea.isFirstJob() && !isMale()) {
				Play.printlnLog(Lang.tea_MSoul);
				tea.getPlayer().changeHP(1);
			}
		}
		// Set Phoebell's property back to physical
		if (this instanceof Phoebell && isFirstJob()) {
			setPhysical(true);
		}

		// Iron's job 2 passive skill heroic: back to normal
		if (this instanceof Iron && !isFirstJob() && target.getJob() == SABER) {
			attack--;
		}

		/* === Commond Route END === */

		if (damage < 0) { // Attack failed
			return 1;
		}

		return 0;
	}
}
