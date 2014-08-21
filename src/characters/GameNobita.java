public class GameNobita extends Character {

	// For job 2 passive skill: Doracity
	private boolean doracity = false;
	private int noOfDoracity = 0;
	private int addDefP = 0;
	private int addDefM = 0;

	@Override
	public void jobChangeExtra() {
		if (isFirstJob()) {
			if (doracity) { // Remove effect of Skill Doracity
				Play.printlnLog(Lang.gamenobita_doracity_end);
				Character[] selfChars = getPlayer().getCharacters();
				for (int i = 0; i < selfChars.length; i++) {
					
					if (selfChars[i].isDoracity()) {
						
						// No need to set value for GameNobita again
						if (selfChars[i] != this) {
							selfChars[i].changeDefP(-addDefP, FOR_EVER);
							selfChars[i].changeDefM(-addDefM, FOR_EVER);
						}

					}
				}

				doracity = false;
			}

		} else {

			doracity = true;
			noOfDoracity = 0;
			addDefP = 0;
			addDefM = 0;

			// How many doracity characters?
			Character[] selfChars = getPlayer().getCharacters();
			for (int i = 0; i < selfChars.length; i++) {
				if (selfChars[i].isDoracity()) {
					noOfDoracity++;
				}
			}

			// What effects should be added?

			String skillMessage = "";
			switch (noOfDoracity) {
			case 1:
				addDefP = 1;
				skillMessage = Lang.gamenobita_doracity_effect[0];
				break;
			case 2:
			case 3:
				addDefP = 1;
				addDefM = 1;
				skillMessage = Lang.gamenobita_doracity_effect[1];
				break;
			case 4:
				addDefP = 2;
				addDefM = 1;
				skillMessage = Lang.gamenobita_doracity_effect[2];
				break;
			case 5:
				addDefP = 2;
				addDefM = 2;
				skillMessage = Lang.gamenobita_doracity_effect[3];
				break;
			}

			Play.printlnLog(Lang.gamenobita_doracity[0] + noOfDoracity
					+ Lang.gamenobita_doracity[1] + skillMessage);

			for (int i = 0; i < selfChars.length; i++) {
				if (selfChars[i].isDoracity()) {
					selfChars[i].changeDefP(addDefP, FOR_EVER);
					selfChars[i].changeDefM(addDefM, FOR_EVER);
				}
			}

		}
	}

	/* === Above are GameNobita's unique fields and methods === */

	public GameNobita(Player player) {
		super(player, 10);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 4, 4, 2, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using GameNobita's 1stJob passive skill 1!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using GameNobita's 1stJob active skill!");

						}

					}, 2);

		} else {

			setValues(true, SABER, true, 4, 4, 3, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using GameNobita's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using GameNobita's 2ndJob active skill!");

						}

					}, 6);
		}
	}

}
