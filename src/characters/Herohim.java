public class Herohim extends Character {

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
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using Herohim's 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Herohim's 1stJob active skill!");

						}

					});

		} else {

			setValues(false, SABER, false, 4, 3, 3, 3, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Herohim's 2ndJob passive skill!");

						}

					});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Herohim's 2ndJob active skill!");

						}

					});
		}
	}

}
