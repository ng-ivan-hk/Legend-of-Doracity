public class Iron extends Character {

	private boolean morale = false; // for job 1 active skill

	@Override
	public void jobChangeExtra() {
		if (isFirstJob()) {
			// Job 1 passive skill: Diligent
			jobChangeMP = 20;
		} else {
			jobChangeMP = 15;
		}
	}

	@Override
	public void roundEndExtra() {
		if (morale) {
			Play.printlnLog(Lang.iron_morale_end);
			int addAttack = 1;
			Character[] selfChars = getPlayer().getCharacters();
			for (int i = 0; i < selfChars.length; i++) {
				if (selfChars[i].getJob() == SUPPORT) {
					addAttack++;
				}
			}
			setAttack(getAttack() - addAttack);
			morale = false;
		}
	}

	/* === Above are Iron's unique fields and methods === */

	public Iron(Player player) {
		super(player, 5);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 2, 2, 2, 3, true);

			passiveSkills = new CharSkill[2];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETED
				}

			}, 0);
			passiveSkills[1] = new CharSkill(this, false, 1, Command.NA, new CharSkillMethod() {

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
							morale = true;
							int addAttack = 1;
							Character[] selfChars = getPlayer().getCharacters();
							for (int i = 0; i < selfChars.length; i++) {
								if (selfChars[i].getJob() == SUPPORT) {
									addAttack++;
								}
							}
							setAttack(getAttack() + addAttack);
						}

					}, 3);

		} else {

			setValues(true, SABER, true, 6, 6, 5, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

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
							Play.printlnLog("Using Iron's 2ndJob active skill!");

						}

					}, 3);
		}
	}

}
