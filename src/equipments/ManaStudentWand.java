public class ManaStudentWand extends Equipment {

	/**
	 * Card Number: 2
	 */
	public ManaStudentWand() {
		super(2, false, false, true, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {
		Play.printlnLog("Using Mana Student's Wand!");
	}

}
