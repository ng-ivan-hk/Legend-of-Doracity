public class HealingWand extends Equipment {

	/**
	 * Card Number: 21
	 */
	public HealingWand() {
		super(21, false, false, true, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeAttack(1, Character.FOR_EQUIPMENT);
		c.changeDefP(2, Character.FOR_EQUIPMENT);

	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
