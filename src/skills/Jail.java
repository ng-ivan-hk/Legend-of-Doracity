public class Jail extends Skill {

	/**
	 * Card Number: 17
	 */
	public Jail() {
		super(17, false, false, false, true, 3, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {

		new CharSkill.CharSelectDialog(c, c.getPlayer().getOpponent(), new TargetMethod() {

			@Override
			public void targetMethod(Character currentChar, Character target) {
				target.setGiveUpNormalAttack(true);
			}

		});

	}

}
