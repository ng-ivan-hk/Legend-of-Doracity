public class Livia extends Character {

	private boolean golem = false;
	private int recoverHP = 2;

	@Override
	public void gameStart() {
		if (getPlayer().contains(Phoebell.class) != null) {
			Play.printlnLog(Lang.livia_together_job1);
			setDefP(getDefP() + 1);
			setDefM(getDefM() + 1);
		}
	}

	@Override
	public void jobChangeExtra() {
		if (getPlayer().contains(Phoebell.class) != null) {
			if (isFirstJob()) {
				Play.printlnLog(Lang.livia_together_job1);
				setDefP(getDefP() + 1);
				setDefM(getDefM() + 1);
			} else {
				// No need to remove defP and defM since they are set by value
				recoverHP = 3;
			}
		}
	}

	@Override
	public void roundEndExtra() {
		if (golem) {
			Livia.this.setAttack(getAttack() - 3);
			Livia.this.setDefP(getDefP() - 2);
			Livia.this.setDefM(getDefM() - 2);
			golem = false;
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
									golem = true;
									Livia.this.setAttack(getAttack() + 3);
									Livia.this.setDefP(getDefP() + 2);
									Livia.this.setDefM(getDefM() + 2);
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
							System.out.println("Using Livia's 2ndJob active skill!");
							getPlayer().changeHP(recoverHP);
							if (recoverHP == 3) {
								Play.printlnLog(Lang.livia_together_job2);
							}

						}

					}, 5);
		}
	}

}
