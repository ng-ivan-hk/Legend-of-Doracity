public class WallDoracity extends Equipment {

	/**
	 * Card Number: 22
	 */
	public WallDoracity() {
		super(22, true, true, true, true, true, false);
	}

	@Override
	public void equipmentEffect(Character c) {
		Play.printlnLog("Using Wall Doracity!");
	}

}
