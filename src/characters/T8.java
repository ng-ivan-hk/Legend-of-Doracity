public class T8 extends Character {

	public T8(Player player) {
		super(player, 27);
	}

	@Override
	protected void setCharacter() {
		// TODO Auto-generated method stub
		if (isFirstJob()) {

			setValues(true, SUPPORT, false, 2, 2, 2, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using T8's 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using T8's 1stJob active skill!");

						}

					});

		} else {

			setValues(true, SUPPORT, false, 3, 2, 2, 2, false);

			passiveSkills = new CharSkill[2]; // This character has 2 passive
												// skills
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using T8's 2ndJob passive skill 1!");

				}

			});
			passiveSkills[1] = new CharSkill(this, false, 1, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using T8's 2ndJob passive skill 2!");

				}

			});
			activeSkills = new CharSkill[3]; // This character has 3 active
												// skills
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using T8's 2ndJob active skill 1!");

						}

					});
			activeSkills[1] = new CharSkill(this, true, 1, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using T8's 2ndJob active skill 2!");

						}

					});
			activeSkills[2] = new CharSkill(this, true, 2, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using T8's 2ndJob active skill 3!");

						}

					});
		}
	}

}