public class WallDoracity extends Equipment {

	/**
	 * Card Number: 26
	 */
	public WallDoracity() {
		super(26, true, true, true, true, true, false, false);
	}

	@Override
	public void equipmentEffect(Character c) {

		Character[] charTemp = c.getPlayer().getCharacters();
		for (int i = 0; i < charTemp.length; i++) {
			if (charTemp[i].isDoracity()) {
				charTemp[i].changeDefP(1, Character.FOR_EVER);
				charTemp[i].changeDefM(1, Character.FOR_EVER);
			}
		}

	}

	@Override
	protected void removeEquipmentEffect(Character c) {

		Character[] charTemp = c.getPlayer().getCharacters();
		for (int i = 0; i < charTemp.length; i++) {
			if (charTemp[i].isDoracity()) {
				charTemp[i].changeDefP(-1, Character.FOR_EVER);
				charTemp[i].changeDefM(-1, Character.FOR_EVER);
			}
		}

	}

}
