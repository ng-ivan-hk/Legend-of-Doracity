public class Map extends Character {

	public Map(Player player) {
		super(player, 4);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 5, 2, 4, 7, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							System.out.println("Using Map's 1stJob passive skill 1!");

						}

					}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							System.out.println("Using Map's 1stJob active skill!");

							// Select character
							new CharSkill.CharSelectDialog(currentChar, opponent,
									new TargetMethod() {

										@Override
										public void targetMethod(Character currentChar,
												Character target) {
											Play.printlnLog(Lang.map_choose + target);
											// set target assassin on
											target.setAssassin(true);
										}

									});
						}

					}, 2);

		} else {

			setValues(true, SUPPORT, true, 4, 2, 3, 5, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					System.out.println("Using Map's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							System.out.println("Using Map's 2ndJob active skill!");

						}

					}, 5);
		}
	}

}
