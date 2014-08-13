public class SupportShot extends Skill {

	/**
	 * Card Number: 6
	 */
	public SupportShot() {
		super(6, false, true, false, false, 1, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Play.printlnLog("Using" + this);
	}

}
