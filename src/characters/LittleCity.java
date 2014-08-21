public class LittleCity extends Character {

	private int count = 0; // for job 1 passive skill 1

	/**
	 * Call this method in Player.changeHP() to calculate how many attack should
	 * be added according to HP damaged.
	 * 
	 * @param HP
	 *            HP damaged (e.g. if player -1 HP, pass 1)
	 */
	public void insane(int HP) {
		while (HP != 0) {
			HP--;
			if (++count == 3) {
				Play.printlnLog(Lang.littleCity_insane);
				changeAttack(2, FOR_ROUND_END);
				count = 0;
			}
		}
	}
	
	@Override
	public void jobChangeExtra() {
		if (isFirstJob()) {
			changeOrder(-100);
		} else {
			changeOrder(100);
		}
	}

	/* === Above are LittleCity's unique fields and methods === */

	public LittleCity(Player player) {
		super(player, 23);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 1, 2, 1, 1, false);

			passiveSkills = new CharSkill[2];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using LittleCity's 1stJob passive skill 1!");

				}

			}, 0);
			passiveSkills[1] = new CharSkill(this, false, 1, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETED
				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							getPlayer().changeHP(-1);
						}

					}, 0);

		} else {

			setValues(true, CASTER, false, 5, 2, 5, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using LittleCity's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using LittleCity's 2ndJob active skill!");

						}

					}, 0);
		}
	}
}
