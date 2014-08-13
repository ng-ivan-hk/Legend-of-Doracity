public class FireRain extends Skill {

	/**
	 * Card Number: 9
	 */
	public FireRain() {
		super(9, false, false, true, false, 8, Command.DURING_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Play.printlnLog("Using" + this);
	}

}
