public class EagleEye extends Skill {

	/**
	 * Card Number: 8
	 */
	public EagleEye() {
		super(8, false, true, false, false, 1, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Play.printlnLog("Using" + this);
	}

}
