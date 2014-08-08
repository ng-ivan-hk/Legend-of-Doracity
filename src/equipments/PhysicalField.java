public class PhysicalField extends Equipment {

	/**
	 * Card Number: 12
	 */
	public PhysicalField() {
		super(12, false, false, false, true, true, true);
	}

	@Override
	public void equipmentEffect(Character c) {

		Character[] charTemp = c.getPlayer().getCharacters();
		for (int i = 0; i < charTemp.length; i++) {
			charTemp[i].changeDefM(1, Character.FOR_EVER);
		}
	}

	@Override
	protected void removeEquipmentEffect(Character c) {

		Character[] charTemp = c.getPlayer().getCharacters();
		for (int i = 0; i < charTemp.length; i++) {
			charTemp[i].changeDefM(-1, Character.FOR_EVER);
		}

	}

}
