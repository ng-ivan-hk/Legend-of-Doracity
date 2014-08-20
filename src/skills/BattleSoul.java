public class BattleSoul extends Skill {

	/**
	 * Card Number: 3
	 */
	public BattleSoul() {
		super(3, true, false, false, false, 1, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		c.changeAttack(2, Character.FOR_ROUND_END);
		c.changeDefP(2, Character.FOR_ROUND_END);
	}

}
