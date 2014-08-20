public class WizardRobe extends Equipment {

	/**
	 * Card Number: 20
	 */
	public WizardRobe() {
		super(20, false, false, true, true, true, true, false);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeAttack(1, Character.FOR_EQUIPMENT);
		c.changeDefM(2, Character.FOR_EQUIPMENT);
		
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		// COMPLETED
	}

}
