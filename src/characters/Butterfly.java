public class Butterfly extends Character {

	/* === Above are Butterfly's unique fields and methods === */

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
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Butterfly's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Butterfly's 1stJob active skill!");

						}

					}, 5);

		} else {

			setValues(false, CASTER, false, 4, 2, 3, 2, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Butterfly's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {

							new CharSkill.CardSelectDialog(getPlayer(), new TargetMethod() {

								@Override
								public void targetMethod(Character currentChar, Character target) {
									changeAttack(2, FOR_ROUND_END);
									activeSkills[0].setDoNotPass(false);
								}

							});

						}

					}, 0);
			activeSkills[0].setDoNotPass(true);
		}
	}

}
