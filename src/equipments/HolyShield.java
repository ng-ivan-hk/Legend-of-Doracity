public class HolyShield extends Equipment {

	/**
	 * Card Number: 14
	 */
	public HolyShield() {
		super(14, true, false, false, false, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {
		Play.printlnLog("Using Holy Shit!");
	}

}
