public class FieldAcademy extends Equipment {

	/**
	 * Card Number: 21
	 */
	public FieldAcademy() {
		super(21, true, true, true, true, false, true);
	}

	@Override
	public void equipmentEffect(Character c) {
		Play.printlnLog("Using Field Academy!");
	}
	
	@Override
	protected void removeEquipmentEffect(Character c) {
		Play.printlnLog("Removing Field Academy!");
	}

}
