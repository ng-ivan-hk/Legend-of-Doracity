public class Punch extends Skill {

	/**
	 * Card Number: 2
	 */
	public Punch() {
		super(2, true, false, false, false, 1, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		c.setDestroyDefP(true);
	}

}
