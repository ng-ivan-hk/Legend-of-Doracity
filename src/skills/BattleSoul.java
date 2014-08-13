public class BattleSoul extends Skill {

	/**
	 * Card Number: 3
	 */
	public BattleSoul() {
		super(3, true, false, false, false, 1, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Play.printlnLog("Using" + this);
	}

}
