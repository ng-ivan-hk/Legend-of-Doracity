public class Anthony extends Character {

	public Anthony(Player player) {
		super(player, 28);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SUPPORT, true, 3, 2, 2, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character  currentChar, Player opponent) {
					System.out.println("Using Anthony's 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character  currentChar, Player opponent) {
							System.out.println("Using Anthony's 1stJob active skill!");

						}

					});

		} else {

			setValues(true, CASTER, false, 3, 2, 2, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character  currentChar, Player opponent) {
							System.out.println("Using Anthony's 2ndJob passive skill!");

						}

					});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character  currentChar, Player opponent) {
							System.out.println("Using Anthony's 2ndJob active skill!");

						}

					});
		}
	}

}
