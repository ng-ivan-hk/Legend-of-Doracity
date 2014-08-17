import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
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
							
							// Create a new class for selecting card
							@SuppressWarnings("serial")
							class EquipmentSelector extends CharSkill.CardSelectDialog {
								public EquipmentSelector(Player player, TargetMethod method) {
									super(player, method);
								}

								@Override
								protected CardSelectPanel getCardSelectPanel() {
									return new CardSelectPanel();
								}

								class CardSelectPanel extends
										CharSkill.CardSelectDialog.CardSelectPanel {

									@Override
									protected void setCardButtons() {

										// Add Equipment Card from my Characters
										Character[] selfChars = getPlayer().getCharacters();
										for (int i = 0; i < selfChars.length; i++) {
											Equipment maybeEquipment = selfChars[i].getEquipment();
											if (maybeEquipment != null) {
												add(new EquipmentButton(selfChars[i],
														maybeEquipment));
											}
										}
										
										// Add Equipment Card from opponent's Characters
										Character[] opponenChars = getPlayer().getOpponent().getCharacters();
										for (int i = 0; i < opponenChars.length; i++) {
											Equipment maybeEquipment = opponenChars[i].getEquipment();
											if (maybeEquipment != null) {
												add(new EquipmentButton(opponenChars[i],
														maybeEquipment));
											}
										}

									}
								}
								
								/**
								 * Represents an Equipment Card that is
								 * currently equipped by a Character.
								 * 
								 * @author Ivan Ng
								 * 
								 */
								class EquipmentButton extends JButton implements ActionListener {
									private Character character = null;

									public EquipmentButton(Character c, Equipment e) {
										this.character = c;
										setText(e.toString() + " (" + c + ")");
										addActionListener(this);
									}

									@Override
									public void actionPerformed(ActionEvent evt) {
										Class<? extends Equipment> targetEquipment = character.getEquipment().getClass();
										try {
											getPlayer().addCard(targetEquipment.newInstance());
										} catch (InstantiationException | IllegalAccessException e) {
											e.printStackTrace();
										}
										dispose();
										activeSkills[0].setDoNotPass(false);
									}

								}
								
							}

							// Select a Card
							new CharSkill.CardSelectDialog(getPlayer(), new TargetMethod() {

								@Override
								public void targetMethod(Character currentChar, Character target) {
									new EquipmentSelector(getPlayer(), null);
								}

							});

						}

					}, 3);
			activeSkills[0].setDoNotPass(true);

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
											activeSkills[0].setDoNotPass(false);
										}

									}
								}

							}
							
							// Select a Card
							new CardSelectDialog(getPlayer(), null);

						}

					}, 0);
			activeSkills[0].setDoNotPass(false);
		}
	}

}
