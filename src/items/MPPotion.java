public class MPPotion extends Item {

	public MPPotion() {
		super(2);
	}

	@Override
	public void useItem(Player player) {

		player.changeMP(5);

		// Check for Sasa's job 1 passive skill: Pharmacist
		Character maybeSasa = (player.contains(Sasa.class));
		if (maybeSasa != null && maybeSasa.isFirstJob()) {
			Play.printlnLog(Lang.sasa_pharmacist);
			player.changeHP(1);
		}

	}

}
