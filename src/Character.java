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
//		if (firstJob) { // First Job
//			switch (number) {
//			case 1: // 1. Tea
//				setValues(true, SABER, true, 3, 2, 2, 2, true);
//				passiveSkills = new CharSkill[1];
//				passiveSkills[0] = new CharSkill(this, false, 1, Command.NA);
//				activeSkills = new CharSkill[1];
//				activeSkills[0] = new CharSkill(this, true, 1, Command.BEFORE_BATTLE);
//				break;
//			case 2: // 2. Livia
//				setValues(false, CASTER, false, 3, 2, 2, 3, true);
//				break;
//			case 3: // 3. Phoebell
//				setValues(true, SABER, true, 4, 3, 3, 4, true);
//				break;
//			case 4: // 4. Map
//				setValues(true, SABER, true, 5, 2, 4, 7, true);
//				break;
//			case 5: // 5. Iron
//				setValues(true, SABER, true, 2, 2, 2, 3, true);
//				break;
//			case 6: // 6. FishBall
//				setValues(true, SABER, true, 3, 4, 2, 3, false);
//				break;
//			case 7: // 7. Shirogane
//				setValues(true, SABER, false, 4, 2, 2, 2, false);
//				break;
//			case 8: // 8. Nonki-Nobita
//				setValues(true, ARCHER, true, 4, 3, 4, 4, false);
//				break;
//			case 9: // 9. Nana
//				setValues(true, SABER, false, 4, 2, 2, 2, false);
//				break;
//			case 10: // 10. Game-Nobita
//				setValues(true, SABER, true, 4, 4, 2, 3, true);
//				break;
//			case 11: // 11. Xander
//				setValues(true, CASTER, false, 4, 3, 3, 3, true);
//				break;
//			case 12: // 12. Butterfly
//				setValues(false, CASTER, false, 4, 2, 2, 2, false);
//				break;
//			case 13: // 13. Feather
//				setValues(true, CASTER, false, 3, 2, 2, 2, true);
//				break;
//			case 14: // 14. Kurokawa
//				setValues(false, ARCHER, true, 4, 2, 2, 4, false);
//				break;
//			case 15: // 15. Herohim
//				setValues(false, SABER, true, 3, 2, 3, 3, false);
//				break;
//			case 16: // 16. Knight
//				setValues(true, SABER, true, 3, 3, 3, 3, false);
//				break;
//			case 17: // 17. Cloud
//				setValues(true, ARCHER, true, 4, 2, 3, 4, false);
//				break;
//			case 18: // 18. Mandy Lee
//				setValues(false, SABER, true, 3, 3, 3, 2, true);
//				break;
//			case 19: // 19. Kuru
//				setValues(true, SABER, true, 3, 2, 2, 2, true);
//				break;
//			case 20: // 20. AK
//				setValues(true, SABER, true, 5, 3, 2, 3, false);
//				break;
//			case 21: // 21. Kuzmon
//				setValues(true, SABER, true, 3, 2, 3, 3, true);
//				break;
//			case 22: // 22. KaitoDora
//				setValues(true, SUPPORT, true, 3, 2, 2, 2, true);
//				break;
//			case 23: // 23. LittleCity
//				setValues(true, SABER, true, 1, 2, 1, 1, false);
//				break;
//			case 24: // 24. Wind Sound
//				setValues(false, SABER, true, 3, 3, 3, 3, false);
//				break;
//			case 25: // 25. Shin
//				setValues(true, ARCHER, true, 4, 2, 3, 4, true);
//				break;
//			case 26: // 26. Mini
//				setValues(true, SUPPORT, false, 3, 3, 3, 3, true);
//				break;
//			case 27: // 27. T8
//				setValues(true, SUPPORT, false, 2, 2, 2, 2, true);
//				break;
//			case 28: // 28. Anthony
//				setValues(true, SUPPORT, true, 3, 2, 2, 2, true);
//				break;
//			case 29: // 29. Sasa
//				setValues(true, SUPPORT, false, 3, 2, 2, 2, true);
//				break;
//			case 30: // 30. SunnyShum
//				setValues(true, CASTER, false, 2, 2, 4, 3, true);
//				break;
//			}
//		} else { // Second Job
//			switch (number) {
//			case 1: // 1. Tea
//				setValues(true, ARCHER, true, 3, 4, 3, 4, false);
//				break;
//			case 2: // 2. Livia
//				setValues(false, SUPPORT, false, 3, 2, 3, 2, false);
//				break;
//			case 3: // 3. Phoebell
//				setValues(true, CASTER, false, 4, 3, 4, 3, false);
//				break;
//			case 4: // 4. Map
//				setValues(true, SUPPORT, true, 4, 2, 3, 6, false);
//				break;
//			case 5: // 5. Iron
//				setValues(true, SABER, true, 6, 6, 5, 4, false);
//				break;
//			case 6: // 6. FishBall
//				setValues(true, ARCHER, false, 5, 3, 2, 4, false);
//				break;
//			case 7: // 7. Shirogane
//				setValues(true, CASTER, false, 4, 3, 4, 2, true);
//				break;
//			case 8: // 8. Nonki-Nobita
//				setValues(true, CASTER, false, 4, 4, 3, 3, true);
//				break;
//			case 9: // 9. Nana
//				setValues(true, SUPPORT, false, 3, 2, 2, 2, false);
//				break;
//			case 10: // 10. Game-Nobita
//				setValues(true, SABER, true, 4, 4, 3, 3, true);
//				break;
//			case 11: // 11. Xander
//				setValues(true, SUPPORT, false, 4, 3, 3, 2, false);
//				break;
//			case 12: // 12. Butterfly
//				setValues(false, CASTER, false, 4, 2, 3, 2, false);
//				break;
//			case 13: // 13. Feather
//				setValues(false, CASTER, false, 3, 2, 2, 2, false);
//				break;
//			case 14: // 14. Kurokawa
//				setValues(false, ARCHER, true, 5, 2, 3, 5, true);
//				break;
//			case 15: // 15. Herohim
//				setValues(false, SABER, false, 4, 3, 3, 3, false);
//				break;
//			case 16: // 16. Knight
//				setValues(true, SABER, true, 5, 4, 3, 3, false);
//				break;
//			case 17: // 17. Cloud
//				setValues(true, ARCHER, true, 5, 2, 4, 5, true);
//				break;
//			case 18: // 18. Mandy Lee
//				setValues(false, SABER, true, 3, 3, 3, 2, false);
//				break;
//			case 19: // 19. Kuru
//				setValues(true, CASTER, false, 3, 3, 3, 2, true);
//				break;
//			case 20: // 20. AK
//				setValues(true, SABER, true, 2, 4, 2, 3, true);
//				break;
//			case 21: // 21. Kuzmon
//				setValues(true, CASTER, false, 3, 3, 4, 3, true);
//				break;
//			case 22: // 22. KaitoDora
//				setValues(true, ARCHER, true, 5, 2, 4, 4, true);
//				break;
//			case 23: // 23. LittleCity
//				setValues(true, CASTER, false, 5, 2, 5, 4, false);
//				break;
//			case 24: // 24. Wind Sound
//				setValues(false, NA, true, 4, 2, 4, 4, false);
//				break;
//			case 25: // 25. Shin
//				setValues(true, ARCHER, true, 4, 3, 3, 4, true);
//				break;
//			case 26: // 26. Mini
//				setValues(true, ARCHER, false, 3, 3, 3, 3, true);
//				break;
//			case 27: // 27. T8
//				setValues(true, SUPPORT, false, 3, 2, 2, 2, false);
//				break;
//			case 28: // 28. Anthony
//				setValues(true, CASTER, false, 3, 2, 2, 2, true);
//				break;
//			case 29: // 29. Sasa
//				setValues(true, ARCHER, true, 3, 2, 3, 4, false);
//				break;
//			case 30: // 30. SunnyShum
//				setValues(true, ARCHER, true, 4, 3, 3, 4, true);
//				break;
//			}
//		}
//
//	}

	protected void setValues(boolean male, int job, boolean physical, int attack, int defP, int defM,
			int speed, boolean doracity) {
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
		return Lang.CharNames[number] + " (" + player.getName() + ")";
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
			System.out.println("Character is defensing!");
			return 2;
		}

		int c2Def = isPhysical() ? target.getDefP() : target.getDefM();
		int damage = getAttack() - c2Def;

		String property = isPhysical() ? "Physical" : "Mana";
		System.out.println(property + " Attack " + getAttack() + " -> " + property + " Defense "
				+ c2Def);

		if (damage < 0) { // Attack failed
			target.setDefense(true);
			return 1;
		} else { // Attack success
			System.out.print("Attack succeessfully! ");
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
