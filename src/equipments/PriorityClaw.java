public class PriorityClaw extends Equipment {

	/**
	 * Card Number: 18
	 */
	public PriorityClaw() {
		super(18, true, true, false, false, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeAttack(1, Character.FOR_EQUIPMENT);
		c.changeSpeed(1, Character.FOR_EQUIPMENT);
		c.changeOrder(1);

	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		c.changeOrder(-1);
	}

}
