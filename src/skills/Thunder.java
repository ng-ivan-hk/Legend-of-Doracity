public class Thunder extends Skill {

	/**
	 * Card Number: 11
	 */
	public Thunder() {
		super(11, false, false, true, false, 8, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Play.printlnLog("Using" + this);
	}

}
