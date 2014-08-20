public class HolyShield extends Equipment {

	/**
	 * Card Number: 2
	 */
	public HolyShield() {
		super(2, true, false, false, false, true, true, false);
	}

	@Override
	public void equipmentEffect(Character c) {

		Character[] charTemp = c.getPlayer().getCharacters();
		for (int i = 0; i < charTemp.length; i++) {
			charTemp[i].changeDefP(1, Character.FOR_EVER);
			charTemp[i].changeDefM(1, Character.FOR_EVER);
		}
		
	}

	@Override
	protected void removeEquipmentEffect(Character c) {

		Character[] charTemp = c.getPlayer().getCharacters();
		for (int i = 0; i < charTemp.length; i++) {
			charTemp[i].changeDefP(-1, Character.FOR_EVER);
			charTemp[i].changeDefM(-1, Character.FOR_EVER);
		}

	}

}
