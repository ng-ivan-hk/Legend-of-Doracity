public class WindSound extends Character {

	public WindSound(Player player) {
		super(player, 24);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, SABER, true, 3, 3, 3, 3, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					System.out.println("Using Wind_Sound's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							System.out.println("Using Wind_Sound's 1stJob active skill!");

						}

					}, 0);

		} else {

			setValues(true, NA, true, 4, 2, 4, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					System.out.println("Using Wind_Sound's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							System.out.println("Using Wind_Sound's 2ndJob active skill!");

						}

					}, 5);
		}
	}

}
