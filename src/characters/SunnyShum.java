public class SunnyShum extends Character {

	@Override
	public void jobChangeExtra() {
		if (isFirstJob()) {

			// Check for job 1 passive skill: Chasteness
			// If no female character on my side, attack+3
			Character[] selfChars = getPlayer().getCharacters();
			for (int i = 0; i < selfChars.length; i++) {
				if (!selfChars[i].isMale()) {
					return;
				}
			}
			Play.printlnLog(Lang.sunnyshum_chasteness);
			changeAttack(3, FOR_JOB_CHANGE);
		} else {
		}
	}

	/* === Above are SunnyShum's unique fields and methods === */

	public SunnyShum(Player player) {
		super(player, 30);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, CASTER, false, 2, 2, 4, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETED
				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Character[] selfChars = getPlayer().getCharacters();
							for (int i = 0; i < selfChars.length; i++) {
								if (selfChars[i].isMale()) {
									selfChars[i].changeAttack(1, FOR_ROUND_END);
								}
							}
						}

					}, 5);

		} else {

			setValues(true, ARCHER, true, 4, 3, 3, 4, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using SunnyShum''s 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using SunnyShum's 2ndJob active skill!");

						}

					}, 2);
		}
	}

}
