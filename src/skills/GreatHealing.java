public class GreatHealing extends Skill {

	/**
	 * Card Number: 13
	 */
	public GreatHealing() {
		super(13, false, false, false, true, 7, Command.DURING_BATTLE);
	}

	@Override
	protected void skillEffect(Character c) {
		c.getPlayer().changeHP(10);
	}

}
