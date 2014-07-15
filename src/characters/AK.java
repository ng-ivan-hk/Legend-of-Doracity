public class AK extends Character {

	public AK(Player player) {
		super(player, 20);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 5, 3, 2, 3, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character  currentChar, Player opponent) {
					System.out.println("Using AK's 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character  currentChar, Player opponent) {
							System.out.println("Using AK's 1stJob active skill!");

						}

					});

		} else {

			setValues(true, SABER, true, 2, 4, 2, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character  currentChar, Player opponent) {
							System.out.println("Using AK's 2ndJob passive skill!");

						}

					});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character  currentChar, Player opponent) {
							System.out.println("Using AK's 2ndJob active skill!");

						}

					});
		}
	}

}
