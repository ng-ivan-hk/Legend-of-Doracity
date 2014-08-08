public class IceWand extends Equipment {

	/**
	 * Card Number: 8
	 */
	public IceWand() {
		super(8, false, false, true, false, true, true);
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
