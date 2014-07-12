public class Cloud extends Character {

	public Cloud(Player player) {
		super(player, 17);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, ARCHER, true, 4, 2, 3, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using Cloud's 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Cloud's 1stJob active skill!");

						}

					});

		} else {

			setValues(true, ARCHER, true, 5, 2, 4, 5, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Cloud's 2ndJob passive skill!");

						}

					});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Cloud's 2ndJob active skill!");

						}

					});
		}
	}

}
