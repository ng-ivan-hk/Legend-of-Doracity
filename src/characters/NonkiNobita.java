public class NonkiNobita extends Character {

	public NonkiNobita(Player player) {
		super(player, 8);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, ARCHER, true, 4, 3, 4, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using NonkiNobita's 1stJob passive skill 1!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using NonkiNobita's 1stJob active skill!");

						}

					}, 7);

		} else {

			setValues(true, CASTER, false, 4, 4, 3, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using NonkiNobita's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using NonkiNobita's 2ndJob active skill!");

						}

					}, 2);
		}
	}

}
