public class Feather extends Character {

	public Feather(Player player) {
		super(player, 13);
	}
	
	@Override
	public void jobChangeExtra() {
		if (isFirstJob()) {
			// Job 1 passive skill: Talent
			jobChangeMP = 10;
		} else {
			jobChangeMP = 15;
		}
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, CASTER, false, 3, 2, 2, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETED
				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Feather's 1stJob active skill!");

						}

					}, 3);

		} else {

			setValues(false, CASTER, false, 3, 2, 2, 2, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Feather's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[2];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Feather's 2ndJob active skill 1!");

						}

					}, 5);

			activeSkills[1] = new CharSkill(this, true, 1, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Feather's 2ndJob active skill 2!");

						}

					}, 5);
		}
	}

}
