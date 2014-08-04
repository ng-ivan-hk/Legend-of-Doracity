public class Gambeson extends Equipment { // = Cloth Armor

	/**
	 * Card Number: 23
	 */
	public Gambeson() {
		super(23, true, true, true, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {
		c.changeDefP(1, Character.FOR_EQUIPMENT);
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
