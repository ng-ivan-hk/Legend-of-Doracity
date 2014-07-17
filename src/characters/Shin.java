public class Shin extends Character {

	public Shin(Player player) {
		super(player, 25);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, ARCHER, true, 4, 2, 3, 4, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					System.out.println("Using Shin's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							System.out.println("Using Shin's 1stJob active skill!");

						}

					}, 5);

		} else {

			setValues(true, ARCHER, true, 4, 3, 3, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					System.out.println("Using Shin's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							System.out.println("Using Shin's 2ndJob active skill!");

						}

					}, 5);
		}
	}

}
