public class FreezingIce extends Skill {

	/**
	 * Card Number: 10
	 */
	public FreezingIce() {
		super(10, false, false, true, false, 8, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Play.printlnLog("Using" + this);
	}

}
