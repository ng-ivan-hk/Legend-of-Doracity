import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Kuru extends Character {

	public Kuru(Player player) {
		super(player, 19);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 3, 2, 2, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETETD
				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Kuru's 1stJob active skill!");

						}

					}, 3);

		} else {

			setValues(true, CASTER, false, 3, 3, 3, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Kuru's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {

							// Override CardSelectPanel.actionPerformed()
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
											CardSelectDialog.this.dispose();
											Play.draw(getPlayer());

											ArrayList<Card> handCards = getPlayer().getHandCards();
											Card newestCard = handCards.get(handCards.size() - 1);

											String message = Lang.kuru_gambling_result + newestCard
													+ "\n";

											if (newestCard instanceof Equipment) {
												message += Lang.kuru_gambling_equipment;
												JOptionPane.showMessageDialog(this, message);
											} else {
												message += Lang.kuru_gambling_notEquipment;
												JOptionPane.showMessageDialog(this, message);
												handCards.remove(card);
												handCards.remove(newestCard);
											}

											Play.printlnLog(message);
										}

									}
								}

							}
							
							// Select a Card
							new CardSelectDialog(getPlayer(), new TargetMethod() {

								@Override
								public void targetMethod(Character currentChar, Character target) {
									// This will not be called...
								}

							});

						}

					}, 0);
		}
	}

}
