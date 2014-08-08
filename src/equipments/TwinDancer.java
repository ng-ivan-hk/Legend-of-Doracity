public class TwinDancer extends Equipment {

	/**
	 * Card Number: 3
	 */
	public TwinDancer() {
		super(3, true, false, false, false, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeSpeed(1, Character.FOR_EQUIPMENT);

	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
