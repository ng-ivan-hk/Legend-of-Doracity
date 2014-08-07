
public class FireWand extends Equipment {

	/**
	 * Card Number: 9
	 */
	public FireWand() {
		super(9, false, false, true, false, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		c.changeAttack(1, Character.FOR_EQUIPMENT);
		
	}

	@Override
	protected void removeEquipmentEffect(Character c) {
		//completed
	

	}

}
