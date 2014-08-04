public class FloatingShoes extends Equipment {

	/**
	 * Card Number: 3
	 */
	public FloatingShoes() {
		super(3, true, true, true, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {
		Play.printlnLog("Using Floating Shoes!");
	}
	
	@Override
	protected void removeEquipmentEffect(Character c) {
		Play.printlnLog("Removing Adventurer's Sword!");
	}

}
