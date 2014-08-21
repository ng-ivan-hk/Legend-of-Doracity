import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class Kuzmon extends Character {

	public Kuzmon(Player player) {
		super(player, 21);
	}

	@Override
	protected void setCharacter() {
		// TODO Auto-generated method stub
		if (isFirstJob()) {

			setValues(true, CASTER, false, 3, 2, 3, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.DRAW_CARD,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Kuzmon's 1stJob passive skill!");

						}

					}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DRAW_CARD,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {

							// Create a new class for selecting card
							@SuppressWarnings("serial")
							class CardSelectDialog extends CharSkill.CardSelectDialog {
								public CardSelectDialog(Player player, TargetMethod method) {
									super(player, method);
								}

								@Override
								protected CardSelectPanel getCardSelectPanel() {
									return new CardSelectPanel();
								}

								class CardSelectPanel extends
										CharSkill.CardSelectDialog.CardSelectPanel {

									@Override
									protected CardButton getCardButton(Card card) {
										return new CardButton(card);
									}

									class CardButton extends
											CharSkill.CardSelectDialog.CardSelectPanel.CardButton {

										public CardButton(Card card) {
											super(card);
										}

										@Override
										public void actionPerformed(ActionEvent evt) {
											
											activeSkills[0].setDoNotPass(false);

											CardSelectDialog.this.dispose();

											// Draw a random card from opponent
											ArrayList<Card> opponentHandCards = getPlayer()
													.getOpponent().getHandCards();
											int randomIndex = new Random()
													.nextInt(opponentHandCards.size());
											Card randomCard = opponentHandCards.get(randomIndex);
											getPlayer().addCard(randomCard);
											getPlayer().getOpponent().removeCard(randomCard);
											
											// Give my card to the opponent
											getPlayer().removeCard(card);
											getPlayer().getOpponent().addCard(card);
											
											Play.printlnLog(Kuzmon.this
													+ Lang.kuzmon_transferMagic1 + card
													+ Lang.kuzmon_transferMagic2 + randomCard);
											

										}

									}

								}

							}

							// Select a Card
							new CardSelectDialog(getPlayer(), null);

						}

					}, 0);
			activeSkills[0].setDoNotPass(true);

		} else {

			setValues(true, CASTER, false, 3, 3, 4, 3, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Kuzmon's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Kuzmon's 2ndJob active skill!");

						}

					}, 10);
		}
	}

}
