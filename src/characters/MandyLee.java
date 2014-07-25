public class MandyLee extends Character {

	public MandyLee(Player player) {
		super(player, 18);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, SABER, true, 3, 3, 3, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Mandy_Lee's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {

							// Choose a constructor that only list my characters
							new CharSkill.CharSelectDialog(currentChar, new TargetMethod() {

								@Override
								public void targetMethod(Character currentChar, Character target) {
									// set target defense on
									target.setDefense(true);
								}

							});

						}

					}, 5);

		} else {

			setValues(false, SABER, true, 3, 3, 3, 2, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Mandy_Lee's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							setGiveUp(true);
							getPlayer().changeHP(2);
						}

					}, 0);
		}
	}

}
