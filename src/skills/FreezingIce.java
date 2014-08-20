public class FreezingIce extends Skill {

	/**
	 * Card Number: 10
	 */
	public FreezingIce() {
		super(10, false, false, true, false, 8, Command.BEFORE_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		Character[] opponentChars = c.getPlayer().getOpponent().getCharacters();
		for (int i = 0; i < opponentChars.length; i++) {
			opponentChars[i].changeDefM(-1, Character.FOR_ROUND_END);
		}
	}

}
