public class Livia extends Character {

	private int recoverHP = 2;

	@Override
	public void jobChangeExtra() {
		if (getPlayer().contains(Phoebell.class) != null) {
			if (isFirstJob()) {
				Play.printlnLog(Lang.livia_together_job1);
				changeDefP(1, FOR_JOB_CHANGE);
				changeDefM(1, FOR_JOB_CHANGE);
			} else {
				// No need to remove defP and defM since they are set by value
				recoverHP = 3;
			}
		}
	}

	/* === Above are Livia's unique fields and methods === */

	public Livia(Player player) {
		super(player, 2);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(false, CASTER, false, 3, 2, 2, 3, true);

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

							new CharSkill.CardSelectDialog(getPlayer(), new TargetMethod() {

								@Override
								public void targetMethod(Character currentChar, Character target) {
									changeAttack(3, FOR_ROUND_END);
									changeDefP(2, FOR_ROUND_END);
									changeDefM(2, FOR_ROUND_END);
								}

							});

						}

					}, 3);

		} else {

			setValues(false, SUPPORT, false, 3, 2, 3, 2, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETED
				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							getPlayer().changeHP(recoverHP);
							if (recoverHP == 3) {
								Play.printlnLog(Lang.livia_together_job2);
							}

						}

					}, 5);
		}
	}

}
