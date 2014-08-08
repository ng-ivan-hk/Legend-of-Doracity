public class WitchHat extends Equipment {

	/**
	 * Card Number: 7
	 */
	public WitchHat() {
		super(7, false, false, true, false, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeAttack(1, Character.FOR_EQUIPMENT);
		c.changeDefM(1, Character.FOR_EQUIPMENT);
	}

	@Override
	protected void removeEquipmentEffect(Character c) {

	}

}
