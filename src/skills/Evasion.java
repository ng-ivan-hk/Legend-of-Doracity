public class Evasion extends Skill {

	/**
	 * Card Number: 7
	 */
	public Evasion() {
		super(7, false, true, false, false, 1, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Play.printlnLog("Using" + this);
	}

}
