public class Iron extends Character {

	@Override
	public void jobChangeExtra() {
		if (isFirstJob()) {
			// Job 1 passive skill: Diligent
			jobChangeMP = 20;
		} else {
			jobChangeMP = 15;
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
							int addAttack = 1;
							Character[] selfChars = getPlayer().getCharacters();
							for (int i = 0; i < selfChars.length; i++) {
								if (selfChars[i].getJob() == SUPPORT) {
									addAttack++;
								}
							}
							changeAttack(addAttack, FOR_ROUND_END);
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

						@SuppressWarnings("serial")
						@Override
						public void skillMethod(Character currentChar, final Player opponent) {

							// Override CharSelectPanel.add()
							class CharSelectDialog extends CharSkill.CharSelectDialog {

								public CharSelectDialog(Character currentChar, Player opponent,
										TargetMethod method) {
									super(currentChar, opponent, method);
								}

								@Override
								// Need to override this so CharSelectPanel
								// refers to here but not superclass
								protected CharSelectPanel getCharSelectPanel(boolean b) {
									return new CharSelectPanel(b);
								}

								class CharSelectPanel extends
										CharSkill.CharSelectDialog.CharSelectPanel {

									public CharSelectPanel(boolean listOpponent) {
										super(listOpponent);
									}

									@Override
									public void add(Character character) {
										CharButton charButton = new CharButton(character);
										if (!character.isDefense()) {
											charButton.setEnabled(false);
										}
										add(charButton);
									}

								}

							}

							// Select Character
							new CharSelectDialog(currentChar, opponent, new TargetMethod() {

								@Override
								public void targetMethod(Character currentChar, Character target) {
									target.setDefense(false);
									attack(target);
									target.setDefense(true);
									activeSkills[0].setDoNotPass(false);
								}

							});

						}

					}, 3);
			activeSkills[0].setDoNotPass(true);
		}
	}

}
