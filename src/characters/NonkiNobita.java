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
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using NonkiNobita's 1stJob passive skill 1!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using NonkiNobita's 1stJob active skill!");

						}

					});

		} else {

			setValues(true, CASTER, false, 4, 4, 3, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using NonkiNobita's 2ndJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using NonkiNobita's 2ndJob active skill!");

						}

					});
		}
	}

}
