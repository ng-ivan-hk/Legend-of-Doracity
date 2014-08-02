public class AdventurerSword extends Equipment {

	/**
	 * Card Number: 1
	 */
	public AdventurerSword() {
		super(1, true, true, false, false, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {
		Play.printlnLog("Using Adventurer's Sword!");
	}

}
