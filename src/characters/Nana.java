public class Nana extends Character {

	public Nana(Player player) {
		super(player, 9);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, CASTER, false, 3, 2, 2, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character  currentChar, Player opponent) {
					System.out.println("Using Nana's 1stJob passive skill 1!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character  currentChar, Player opponent) {
							System.out.println("Using Nana's 1stJob active skill!");

						}

					});

		} else {

			setValues(false, SUPPORT, false, 3, 2, 2, 2, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character  currentChar, Player opponent) {
					System.out.println("Using Nana's 2ndJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character  currentChar, Player opponent) {
							System.out.println("Using Nana's 2ndJob active skill!");

						}

					});
		}
	}

}
