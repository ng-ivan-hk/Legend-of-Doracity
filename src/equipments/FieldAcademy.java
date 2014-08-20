public class FieldAcademy extends Equipment {

	/**
	 * Card Number: 25
	 */
	public FieldAcademy() {
		super(25, true, true, true, true, false, true, false);
	}

	@Override
	public void equipmentEffect(Character c) {

		Character[] charTemp = c.getPlayer().getCharacters();
		for (int i = 0; i < charTemp.length; i++) {
			if (!charTemp[i].isDoracity()) {
				charTemp[i].changeDefP(1, Character.FOR_EVER);
				charTemp[i].changeDefM(1, Character.FOR_EVER);
			}
		}

	}

	@Override
	protected void removeEquipmentEffect(Character c) {

		Character[] charTemp = c.getPlayer().getCharacters();
		for (int i = 0; i < charTemp.length; i++) {
			if (!charTemp[i].isDoracity()) {
				charTemp[i].changeDefP(-1, Character.FOR_EVER);
				charTemp[i].changeDefM(-1, Character.FOR_EVER);
			}
		}

	}

}
