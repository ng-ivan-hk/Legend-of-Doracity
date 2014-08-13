public class Mute extends Skill {

	/**
	 * Card Number: 15
	 */
	public Mute() {
		super(15, false, false, false, true, 3, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Play.printlnLog("Using" + this);
	}

}
