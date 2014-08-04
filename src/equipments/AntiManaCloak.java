public class AntiManaCloak extends Equipment {

	/**
	 * Card Number: 5
	 */
	public AntiManaCloak() {
		super(5, true, true, true, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {
		Play.printlnLog("Using Anti Mana Cloak!");
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		Play.printlnLog("Removing Anti Mana Cloak!");
	}

}
