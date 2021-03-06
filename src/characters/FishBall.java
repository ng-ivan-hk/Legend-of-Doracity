public class FishBall extends Character {
	
	private boolean limit = false;
	
	/**
	 * For Job 1 Passive Skill
	 */
	private void limit(){
		Play.printlnLog(Lang.fishball_limit);
		changeAttack(1, FOR_ROUND_END);
		limit = true;
	}
	
	public boolean isLimit(){
		return limit;
	}
	
	@Override
	public void roundEndExtra(){
		limit = false;
	}
	
	/* === Above are FishBall's unique fields and methods === */

	public FishBall(Player player) {
		super(player, 6);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 3, 4, 2, 3, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETED
				}

			}, 0);
			activeSkills = new CharSkill[2];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {

							// Select character
							new CharSkill.CharSelectDialog(currentChar, opponent,
									new TargetMethod() {

										@Override
										public void targetMethod(Character currentChar,
												Character target) {
											target.setDestroyDefP(true);
											limit();
											activeSkills[0].setDoNotPass(false);
										}

									});

						}

					}, 5);
			activeSkills[0].setDoNotPass(true);
			activeSkills[1] = new CharSkill(this, true, 1, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {

							// Select character
							new CharSkill.CharSelectDialog(currentChar, opponent,
									new TargetMethod() {

										@Override
										public void targetMethod(Character currentChar,
												Character target) {
											target.setGiveUpSkills(true);
											limit();
											activeSkills[1].setDoNotPass(false);
										}

									});
							
						}

					}, 5);
			activeSkills[1].setDoNotPass(true);

		} else {

			setValues(true, ARCHER, false, 4, 2, 2, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using FishBall's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using FishBall's 2ndJob active skill!");

						}

					}, 5); // TODO: This skill can be used many times unless no MP
		}
	}

}
