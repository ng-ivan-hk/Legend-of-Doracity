public class ManaField extends Equipment {

	public ManaField() {
		super(13, false, false, false, true, true, true);
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
