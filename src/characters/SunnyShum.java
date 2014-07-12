public class SunnyShum extends Character {

	public SunnyShum(Player player) {
		super(player, 30);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, CASTER, false, 2, 2, 4, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using SunnyShum''s 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using SunnyShum''s 1stJob active skill!");

						}

					});

		} else {

			setValues(true, ARCHER, true, 4, 3, 3, 4, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using SunnyShum''s 2ndJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using SunnyShum's 2ndJob active skill!");

						}

					});
		}
	}

}
