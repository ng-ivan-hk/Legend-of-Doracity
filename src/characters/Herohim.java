public class Herohim extends Character {
	
	private boolean doll = false; // for job 1 active skill
	
	@Override
	public void roundEndExtra() {
		if (doll) {
			doll = false;
			Play.printlnLog(this + Lang.herohim_doll_end);
		}
	}
	
	// For job 1 active skill

	@Override
	public String toString() {
		if (doll) {
			return super.toString() + Lang.herohim_doll_toString;
		} else {
			return super.toString();
		}
	}
	
	@Override
	public String getTitle() {
		if (doll) {
			return "";
		} else {
			return super.getTitle();
		}
	}
	
	@Override
	public boolean isPhysical() {
		if (doll) {
			return false;
		} else {
			return super.isPhysical();
		}
	}

	@Override
	public int getAttack() {
		if (doll) {
			return 5;
		} else {
			return super.getAttack();
		}
	}

	@Override
	public int getDefP() {
		if (doll) {
			return 2;
		} else {
			return super.getDefP();
		}
	}

	@Override
	public int getDefM() {
		if (doll) {
			return 3;
		} else {
			return super.getDefM();
		}
	}
	
	/* === Above are Herohim's unique fields and methods === */

	public Herohim(Player player) {
		super(player, 15);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, SABER, true, 3, 2, 3, 3, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Herohim's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							doll = true;
						}

					}, 3);

		} else {

			setValues(false, SABER, false, 4, 3, 3, 3, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Herohim's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Herohim's 2ndJob active skill!");

						}

					}, 20);
		}
	}

}
