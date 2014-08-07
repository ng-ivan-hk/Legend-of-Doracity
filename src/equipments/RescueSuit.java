
public class RescueSuit extends Equipment {

	/**
	 * Card Number: 11
	 */
	public RescueSuit() {
		super(11, false, false, false, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeDefP(1, Character.FOR_EQUIPMENT);
		c.changeDefM(1, Character.FOR_EQUIPMENT);
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		//completed
	

	}

}
