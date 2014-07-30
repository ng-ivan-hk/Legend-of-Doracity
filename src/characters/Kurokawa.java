public class Kurokawa extends Character {

	private boolean strong = false; // for job 1 active skill

	@Override
	public void roundEndExtra() {
		if (strong) {
			Play.printlnLog(Lang.kurokawa_strong_end);
			setAttack(getAttack() - 1);
			strong = false;
		}
	}

	/* === Above are Kurokawa's unique fields and methods === */

	public Kurokawa(Player player) {
		super(player, 14);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, ARCHER, true, 4, 2, 2, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Kurokawa's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							strong = true;
							setAttack(getAttack() + 1);
						}

					}, 3);

		} else {

			setValues(false, ARCHER, true, 5, 2, 3, 5, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Kurokawa's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Kurokawa's 2ndJob active skill!");

						}

					}, 10);
		}
	}

}
