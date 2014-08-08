public class Cloud extends Character {
	
	private boolean focus = false;

	@Override
	public void roundEndExtra() {
		if (focus) {
			focus = false;
		}
	}
	
	@Override
	public int getDefM() {
		if (focus) {
			return 0;
		} else {
			return super.getDefM();
		}
	}
	
	/* === Above are Cloud's unique fields and methods === */

	public Cloud(Player player) {
		super(player, 17);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, ARCHER, true, 4, 2, 3, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Cloud's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Cloud's 1stJob active skill!");

						}

					}, 2);

		} else {

			setValues(true, ARCHER, true, 5, 2, 4, 5, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Cloud's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							focus = true;
							changeAttack(3, FOR_ROUND_END);
						}

					}, 0);
		}
	}

}
