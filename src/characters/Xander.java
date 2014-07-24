public class Xander extends Character {

	private boolean wall = false;

	@Override
	public void roundEndExtra() {
		if (wall) {
			Play.printlnLog(Lang.xander_wall_end);
			setDefP(getDefP() - 1);
			wall = false;
		}
	}

	/* === Above are Xander's unique fields and methods === */

	public Xander(Player player) {
		super(player, 11);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, CASTER, false, 4, 3, 3, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Xander's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							wall = true;
							setDefP(getDefP() + 1);
						}

					}, 3);

		} else {

			setValues(true, SUPPORT, false, 4, 3, 3, 2, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Xander's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[2];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.draw(getPlayer());
							Play.draw(getPlayer());
						}

					}, 5);

			activeSkills[1] = new CharSkill(this, true, 1, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {

							new CharSkill.CardSelectDialog(getPlayer(), new TargetMethod() {

								@Override
								public void targetMethod(Character currentChar, Character target) {
									getPlayer().changeMP(2);
								}

							});

						}

					}, 0);
		}
	}

}
