public class ManaStudentWand extends Equipment {

	/**
	 * Card Number: 19
	 */
	public ManaStudentWand() {
		super(19, false, false, true, true, true, true, true);
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
