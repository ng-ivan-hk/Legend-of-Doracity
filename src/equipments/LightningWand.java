
public class LightningWand extends Equipment {

	/**
	 * Card Number: 10
	 */
	public LightningWand() {
		super(10, false, false, true, false, true, true);
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
