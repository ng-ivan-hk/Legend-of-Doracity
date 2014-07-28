public class WindSound extends Character {

	// Values for Human Form & Cat Form (Job 1 active skill)
	private final int[] humanFormValues = { 3, 3, 3, 3 };
	private final int[] catFormValues = { 4, 2, 2, 4 };

	private boolean catForm = false; // for job 1 active skill
	private boolean noDamage = false; // for job 2 active skill

	@Override
	public int getInitAttack() {
		if (isFirstJob()) {
			return (catForm ? catFormValues : humanFormValues)[0];
		} else {
			return super.getInitAttack();
		}
	}

	@Override
	public int getInitDefP() {
		if (isFirstJob()) {
			return (catForm ? catFormValues : humanFormValues)[1];
		} else {
			return super.getInitDefP();
		}
	}

	@Override
	public int getInitDefM() {
		if (isFirstJob()) {
			return (catForm ? catFormValues : humanFormValues)[2];
		} else {
			return super.getInitDefM();
		}
	}

	@Override
	public int getInitSpeed() {
		if (isFirstJob()) {
			return (catForm ? catFormValues : humanFormValues)[3];
		} else {
			return super.getInitSpeed();
		}
	}

	/* === Above are WindSound's unique fields and methods === */

	public WindSound(Player player) {
		super(player, 24);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, SABER, true, humanFormValues[0], humanFormValues[1],
					humanFormValues[2], humanFormValues[3], false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Wind_Sound's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {

							if (catForm) { // Turn back to human form
								setAttack(getAttack() + humanFormValues[0] - catFormValues[0]);
								setDefP(getDefP() + humanFormValues[1] - catFormValues[1]);
								setDefM(getDefM() + humanFormValues[2] - catFormValues[2]);
								setSpeed(getSpeed() + humanFormValues[3] - catFormValues[3]);
								Play.printlnLog(Lang.windsound_catForm_human);
							} else { // Change to cat form!!!
								setAttack(getAttack() + catFormValues[0] - humanFormValues[0]);
								setDefP(getDefP() + catFormValues[1] - humanFormValues[1]);
								setDefM(getDefM() + catFormValues[2] - humanFormValues[2]);
								setSpeed(getSpeed() + catFormValues[3] - humanFormValues[3]);
								Play.printlnLog(Lang.windsound_catForm_cat);
							}
							catForm = !catForm;

						}

					}, 0);

		} else {

			setValues(true, NA, true, 4, 2, 4, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Wind_Sound's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Wind_Sound's 2ndJob active skill!");

						}

					}, 5);
		}
	}

}
