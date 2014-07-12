public class Butterfly extends Character {

	public Butterfly(Player player) {
		super(player, 12);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, CASTER, false, 4, 2, 2, 2, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using Butterfly's 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Butterfly's 1stJob active skill!");

						}

					});

		} else {

			setValues(false, CASTER, false, 4, 2, 3, 2, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Butterfly's 2ndJob passive skill!");

						}

					});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Butterfly's 2ndJob active skill!");

						}

					});
		}
	}

}
