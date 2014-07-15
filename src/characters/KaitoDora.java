public class KaitoDora extends Character {

	public KaitoDora(Player player) {
		super(player, 22);
	}

	@Override
	protected void setCharacter() {
		// TODO Auto-generated method stub
		if (isFirstJob()) {

			setValues(true, SUPPORT, true, 3, 2, 2, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character  currentChar, Player opponent) {
					System.out.println("Using KaitoDora's 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character  currentChar, Player opponent) {
							System.out.println("Using KaitoDora's 1stJob active skill!");

						}

					});

		} else {

			setValues(true, ARCHER, true, 5, 2, 4, 4, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character  currentChar, Player opponent) {
					System.out.println("Using KaitoDora's 2ndJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character  currentChar, Player opponent) {
					System.out.println("Using KaitoDora's 2ndJob active skill!");

				}

			});
		}
	}

}
