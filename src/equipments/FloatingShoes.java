public class FloatingShoes extends Equipment {

	/**
	 * Card Number: 22
	 */
	public FloatingShoes() {
		super(22, true, true, true, true, true, true, false);
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
