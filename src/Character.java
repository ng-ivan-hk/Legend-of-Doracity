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
	
	/* Static Values for change???() */
	public final static int FOR_EVER = 0;
	public final static int FOR_JOB_CHANGE = 1;
	public final static int FOR_ROUND_END = 2;
	public final static int FOR_EQUIPMENT = 3;
	

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

	/* Extra effects on character's properties */
	// forever
	private int attack_ever = 0;
	private int defP_ever = 0;
	private int defM_ever = 0;
	private int speed_ever = 0;
	// for job change
	private int attack_jobChange = 0;
	private int defP_jobChange = 0;
	private int defM_jobChange = 0;
	private int speed_jobChange = 0;
	// for round end
	private int attack_roundEnd = 0;
	private int defP_roundEnd = 0;
	private int defM_roundEnd = 0;
	private int speed_roundEnd = 0;
	// for equipment
	private int attack_equipment = 0;
	private int defP_equipment = 0;
	private int defM_equipment = 0;
	private int speed_equipment = 0;
	

	/* Character variables */
	protected CharSkill[] passiveSkills = null;
	protected CharSkill[] activeSkills = null;
	
	private boolean firstJob = false; // if false, second job
	private Equipment equipment = null;
	protected boolean defense = false; // character will defense if attacked
	protected boolean assassin = false; // Map's Active Skill
	private boolean giveUp = false; // if true, this character will be passed
	private boolean giveUpNormalAttack = false; // if true, cannot use normal attack
	private boolean giveUpSkills = false; // if true, cannot use skills
	private boolean destroyDefP = false; // if true, getDefP() returns 0

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
		if (attack_jobChange != 0 || defP_jobChange != 0 || defM_jobChange != 0
				|| speed_jobChange != 0) {
			Play.printlnLog(this + Lang.log_passiveSkillEnd);
			attack_jobChange = 0;
			defP_jobChange = 0;
			defM_jobChange = 0;
			speed_jobChange = 0;
		}
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
		
		boolean for_round_end = false; // if true, print message
		
		/* Remove round end effects */
		if (attack_roundEnd != 0 || defP_roundEnd != 0 || defM_roundEnd != 0 || speed_roundEnd != 0) {
			attack_roundEnd = 0;
			defP_roundEnd = 0;
			defM_roundEnd = 0;
			speed_roundEnd = 0;
			for_round_end = true;
		}

		/* Boolean variables off */
		defense = false; // Defense off
		giveUp = false; // Give Up off
		giveUpSkills = false;
		giveUpNormalAttack = false;
		if (destroyDefP) { // Destroy DefP off
			destroyDefP = false;
			for_round_end = true;
		}
		assassin = false; // Map's Assassin off

		/* Print message */
		if (for_round_end) {
			Play.printlnLog(Lang.log_for_round_end[0] + " " + this + " "
					+ Lang.log_for_round_end[1]);
		}

		/* Any other things? */
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
		this.attack_init = attack;
		this.defP_init = defP;
		this.defM_init = defM;
		this.speed_init = speed;
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

	/**
	 * @param attack How much attack is changed? (e.g. pass 2 if attack + 2,
	 * pass -1 if attack - 1)
	 * 
	 * @param type
	 *            <code>FOR_EVER</code> this effect will still take place even
	 *            if round end or job change is performed (usually applied by
	 *            equipment or skills from other character)<br>
	 *            <code>FOR_JOB_CHANGE</code> this effect will disappear if
	 *            character performs a job change (usually for passive skills)<br>
	 *            <code>FOR_ROUND_END</code> this effect will disappear when a
	 *            round ends (usually for active skills)<br>
	 *            <code>FOR_EQUIPMENT</code> this effect will disappear if
	 *            equipment is removed / changed
	 */
	public void changeAttack(int attack, int type) {
		switch (type) {
		case FOR_EVER:
			attack_ever += attack;
			break;
		case FOR_JOB_CHANGE:
			attack_jobChange += attack;
			break;
		case FOR_ROUND_END:
			attack_roundEnd += attack;
			break;
		case FOR_EQUIPMENT:
			attack_equipment += attack;
			break;
		default:
			return;
		}
		if (attack != 0) {
			Play.printlnLog(this + " " + Lang.attack + (attack >= 0 ? "+" : "") + attack);
		} 
	}

	public int getAttack() {
		return attack_init + attack_ever + attack_jobChange + attack_roundEnd + attack_equipment;
	}

	/**
	 * @param defP
	 *            How much Physical Defense is changed? (e.g. pass 2 if defP +
	 *            2, pass -1 if defP - 1)
	 * @param type
	 *            <code>FOR_EVER</code> this effect will still take place even
	 *            if round end or job change is performed (usually applied by
	 *            equipment or skills from other character)<br>
	 *            <code>FOR_JOB_CHANGE</code> this effect will disappear if
	 *            character performs a job change (usually for passive skills)<br>
	 *            <code>FOR_ROUND_END</code> this effect will disappear when a
	 *            round ends (usually for active skills)<br>
	 *            <code>FOR_EQUIPMENT</code> this effect will disappear if
	 *            equipment is removed / changed
	 */
	public void changeDefP(int defP, int type) {
		switch (type) {
		case FOR_EVER:
			defP_ever += defP;
			break;
		case FOR_JOB_CHANGE:
			defP_jobChange += defP;
			break;
		case FOR_ROUND_END:
			defP_roundEnd += defP;
			break;
		case FOR_EQUIPMENT:
			defP_equipment += defP;
			break;
		default:
			return;
		}
		if (defP != 0) {
			Play.printlnLog(this + " " + Lang.defP + (defP >= 0 ? "+" : "") + defP);
		}
	}

	public int getDefP() {
		if (destroyDefP) {
			return 0;
		}
		return defP_init + defP_ever + defP_jobChange + defP_roundEnd + defP_equipment;
	}

	/**
	 * @param defM
	 *            How much Mana Defense is changed? (e.g. pass 2 if defM + 2,
	 *            pass -1 if defM - 1)
	 * @param type
	 *            <code>FOR_EVER</code> this effect will still take place even
	 *            if round end or job change is performed (usually applied by
	 *            equipment or skills from other character)<br>
	 *            <code>FOR_JOB_CHANGE</code> this effect will disappear if
	 *            character performs a job change (usually for passive skills)<br>
	 *            <code>FOR_ROUND_END</code> this effect will disappear when a
	 *            round ends (usually for active skills)<br>
	 *            <code>FOR_EQUIPMENT</code> this effect will disappear if
	 *            equipment is removed / changed
	 */
	public void changeDefM(int defM, int type) {
		switch (type) {
		case FOR_EVER:
			defM_ever += defM;
			break;
		case FOR_JOB_CHANGE:
			defM_jobChange += defM;
			break;
		case FOR_ROUND_END:
			defM_roundEnd += defM;
			break;
		case FOR_EQUIPMENT:
			defM_equipment += defM;
			break;
		default:
			return;
		}
		if (defM != 0) {
			Play.printlnLog(this + " " + Lang.defM + (defM >= 0 ? "+" : "") + defM);
		}
	}

	public int getDefM() {
		return defM_init + defM_ever + defM_jobChange + defM_roundEnd + defM_equipment;
	}

	/**
	 * @param speed
	 *            How much speed is changed? (e.g. pass 2 if speed + 2, pass -1
	 *            if speed - 1)
	 * @param type
	 *            <code>FOR_EVER</code> this effect will still take place even
	 *            if round end or job change is performed (usually applied by
	 *            equipment or skills from other character)<br>
	 *            <code>FOR_JOB_CHANGE</code> this effect will disappear if
	 *            character performs a job change (usually for passive skills)<br>
	 *            <code>FOR_ROUND_END</code> this effect will disappear when a
	 *            round ends (usually for active skills)<br>
	 *            <code>FOR_EQUIPMENT</code> this effect will disappear if
	 *            equipment is removed / changed
	 */
	public void changeSpeed(int speed, int type) {
		switch (type) {
		case FOR_EVER:
			speed_ever += speed;
			break;
		case FOR_JOB_CHANGE:
			speed_jobChange += speed;
			break;
		case FOR_ROUND_END:
			speed_roundEnd += speed;
			break;
		case FOR_EQUIPMENT:
			speed_equipment += speed;
			break;
		default:
			return;
		}
		if (speed != 0) {
			Play.printlnLog(this + " " + Lang.speed + (speed >= 0 ? "+" : "") + speed);
		}
	}

	public int getSpeed() {
		return speed_init + speed_ever + speed_jobChange + speed_roundEnd  + speed_equipment;
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

	/**
	 * Call this to equip an {@link Equipment}.
	 * 
	 * @param e
	 *            Equipment to be equipped on this Character. If simply remove
	 *            Equipment, pass null.
	 * @return 0 if success. Otherwise returns error code returned by
	 *         {@link Equipment#check(Character)}.
	 */
	public int setEquipment(Equipment e) {

		if (equipment == null) {

			if (e != null) { // Equip Equipment

				int code = e.check(this);

				if (code != 0) {
					return code;
				}

				this.equipment = e;
				Play.printlnLog(this + " " + Lang.log_equip + " " + e);
				e.useEquipment(this);

				// Check for T8's job 1 passive skill
				T8 maybeT8 = (T8) getPlayer().contains(T8.class);
				if (maybeT8 != null && maybeT8.isFirstJob()) {
					T8.addEquipmentExtraEffect(this);
				}

			}

		} else {
			
			if (e != null) {
				
				int code = e.check(this);

				if (code != 0) {
					return code;
				}
			}

			// Remove current Equipment
			attack_equipment = 0;
			defP_equipment = 0;
			defM_equipment = 0;
			speed_equipment = 0;

			Play.printlnLog(this + " " + Lang.log_removeEquipment + " " + equipment);
			equipment.removeEquipmentEffect(this);

			// Check for T8's job 1 passive skill
			boolean isJob1T8here = false;
			T8 maybeT8 = (T8) getPlayer().contains(T8.class);
			if (maybeT8 != null && maybeT8.isFirstJob()) {
				isJob1T8here = true;
			}

			if (isJob1T8here) {
				T8.removeEquipmentExtraEffect(this);
			}

			// Equip new Equipment or null
			this.equipment = e;
			if (e != null) {
				Play.printlnLog(this + " " + Lang.log_equip + " " + e);
				e.useEquipment(this);
				
				// Check for T8's job 1 passive skill again
				if (isJob1T8here) {
					T8.addEquipmentExtraEffect(this);
				}
			}

		}
		
		return 0;

	}

	/**
	 * @return Equipment equipped by this Character. Returns null if no
	 *         Equipment is equipped.
	 */
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

	protected void setGiveUpSkills(boolean b) {
		Play.printlnLog(this + Lang.giveUpSkills);
		giveUpSkills = b;
	}

	public boolean isGiveUpSkills() {
		return giveUpSkills;
	}
	
	protected void setGiveUpNormalAttack(boolean b) {
		Play.printlnLog(this + Lang.giveUpSkills);
		giveUpNormalAttack = b;
	}

	public boolean isGiveUpNormalAttack() {
		return giveUpNormalAttack;
	}
	
	/**
	 * Destroy this Character's Physical Defense for a round.
	 * 
	 * @param b
	 */
	protected void setDestroyDefP(boolean b) {
		Play.printlnLog(this + Lang.destroyDefP);
		destroyDefP = true;
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
			attack_ever++;
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
			attack_ever--;
		}

		/* === Commond Route END === */

		if (damage < 0) { // Attack failed
			return 1;
		}

		return 0;
	}
}
