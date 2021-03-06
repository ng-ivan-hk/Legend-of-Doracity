public class MobilityBoots extends Equipment {

	/**
	 * Card Number: 4
	 */
	public MobilityBoots() {
		super(4, false, true, false, false, true, true, false);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeSpeed(2, Character.FOR_EQUIPMENT);
		c.changeOrder(1);

	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		c.changeOrder(-1);
	}

}
