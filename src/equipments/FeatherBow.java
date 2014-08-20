public class FeatherBow extends Equipment {

	/**
	 * Card Number: 5
	 */
	public FeatherBow() {
		super(5, false, true, false, false, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {
		c.changeSpeed(1, Character.FOR_EQUIPMENT);
		c.changeAttack(1, Character.FOR_EQUIPMENT);
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
