public class AdventurerSword extends Equipment {

	/**
	 * Card Number: 15
	 */
	public AdventurerSword() {
		super(15, true, true, false, false, true, true, true);
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
