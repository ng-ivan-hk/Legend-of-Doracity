public class Assault extends Skill {

	/**
	 * Card Number: 1
	 */
	public Assault() {
		super(1, true, false, false, false, 1, Command.DURING_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Play.printlnLog("Using" + this);
	}

}
