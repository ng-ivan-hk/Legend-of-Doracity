public class AntiManaCloak extends Equipment {

	/**
	 * Card Number: 24
	 */
	public AntiManaCloak() {
		super(24, true, true, true, true, true, true, false);
	}

	@Override
	public void equipmentEffect(Character c) {
		c.changeDefM(1, Character.FOR_EQUIPMENT);
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
