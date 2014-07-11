
public class Mini extends Character {

	public Mini(Player player) {
		super(player, 26);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setCharacter() {
		// TODO Auto-generated method stub
		if (isFirstJob()) {

			setValues(true, SUPPORT, false, 3, 3, 3, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using Mini's 1stJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Mini's 1stJob active skill!");

						}

					});

		} else {

			setValues(true, CASTER, false, 3, 3, 3, 3,true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.BEFORE_BATTLE, new CharSkillMethod() {

				@Override
				public void skillMethod(Character c1, Character c2) {
					System.out.println("Using Mini's 2ndJob passive skill!");

				}

			});
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character c1, Character c2) {
							System.out.println("Using Mini's 2ndJob active skill!");

						}

					});
		}
	}

}
