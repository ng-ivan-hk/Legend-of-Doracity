public class Nana extends Character {

	@Override
	public void jobChangeExtra() {
		if (isFirstJob()) {
			Play.printlnLog(Lang.nana_evil);
			Character[] opponentChars = getPlayer().getOpponent().getCharacters();
			for (int i = 0; i < opponentChars.length; i++) {
				opponentChars[i].changeSpeed(-1, FOR_EVER);
			}
		} else {
			Play.printlnLog(Lang.nana_evil_end);
			Character[] opponentChars = getPlayer().getOpponent().getCharacters();
			for (int i = 0; i < opponentChars.length; i++) {
				opponentChars[i].changeSpeed(1, FOR_EVER);
			}
		}
	}

	/* === Above are Nana's unique fields and methods === */

	public Nana(Player player) {
		super(player, 9);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, CASTER, false, 3, 2, 2, 2, true);

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
							Play.printlnLog("Using Nana's 1stJob active skill!");

						}

					}, 8);

		} else {

			setValues(false, SUPPORT, false, 3, 2, 2, 2, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Nana's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Nana's 2ndJob active skill!");

						}

					}, 5);
		}
	}

}
