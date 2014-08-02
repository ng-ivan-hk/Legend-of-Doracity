public class Gambeson extends Equipment { // = Cloth Armor

	/**
	 * Card Number: 4
	 */
	public Gambeson() {
		super(4, true, true, true, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {
		Play.printlnLog("Using Gamebeson (Cloth Armor)!");
	}

}
