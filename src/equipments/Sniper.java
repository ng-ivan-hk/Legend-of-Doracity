public class Sniper extends Equipment {

	/**
	 * Card Number: 6
	 */
	public Sniper() {
		super(6, false, true, false, false, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeAttack(1, Character.FOR_EQUIPMENT);
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
