
public class LittleCity extends Character {

	public LittleCity(Player player) {
		super(player, 23);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setCharacter() {
		// TODO Auto-generated method stub
		if (isFirstJob()) {

			setValues(true, SABER, true, 1, 2, 1, 1, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using LittleCity's 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using LittleCity's 1stJob active skill!");

						}

					});

		} else {

			setValues(true, CASTER, false, 3, 2, 3, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using LittleCity's 2ndJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using LittleCity's 2ndJob active skill!");

						}

					});
		}
	}

}
