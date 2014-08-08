public class PriorityClaw extends Equipment {

	/**
	 * Card Number: 18
	 */
	public PriorityClaw() {
		super(18, true, true, false, false, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeAttack(1, Character.FOR_EQUIPMENT);
		c.changeSpeed(1, Character.FOR_EQUIPMENT);
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
