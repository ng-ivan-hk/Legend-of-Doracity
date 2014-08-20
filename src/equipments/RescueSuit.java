public class RescueSuit extends Equipment {

	/**
	 * Card Number: 11
	 */
	public RescueSuit() {
		super(11, false, false, false, true, true, true, false);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeDefP(1, Character.FOR_EQUIPMENT);
		c.changeDefM(1, Character.FOR_EQUIPMENT);
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
