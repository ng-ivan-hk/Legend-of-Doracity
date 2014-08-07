
public class TruthBlade extends Equipment {

	/**
	 * Card Number: 17
	 */
	public TruthBlade() {
		super(17, true, false, false, false, true, true);
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
