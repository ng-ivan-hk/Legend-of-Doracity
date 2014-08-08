public class KnightArmor extends Equipment {

	/**
	 * Card Number: 1
	 */
	public KnightArmor() {
		super(1, true, false, false, false, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeDefP(2, Character.FOR_EQUIPMENT);

	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
