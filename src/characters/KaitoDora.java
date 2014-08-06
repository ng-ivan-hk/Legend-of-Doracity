import java.util.ArrayList;
import java.util.Random;

public class KaitoDora extends Character {

	public KaitoDora(Player player) {
		super(player, 22);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SUPPORT, true, 3, 2, 2, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using KaitoDora's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							ArrayList<Card> opponentHandCards = opponent.getHandCards();
							int randomIndex = new Random().nextInt(opponentHandCards.size());
							Card randomCard = opponentHandCards.get(randomIndex);
							Play.printlnLog(KaitoDora.this + Lang.kaitodora_steal + randomCard);
							getPlayer().addCard(randomCard);
							getPlayer().getOpponent().removeCard(randomCard);
						}

					}, 3);

		} else {

			setValues(true, ARCHER, true, 5, 2, 4, 4, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using KaitoDora's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using KaitoDora's 2ndJob active skill!");

				}

			}, 3);
		}
	}

}
