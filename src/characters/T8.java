import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class T8 extends Character {

	public T8(Player player) {
		super(player, 27);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SUPPORT, false, 2, 2, 2, 2, true);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using T8's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.AFTER_BATTLE,
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
									protected void setCardButtons() {

										// Add Equipment Card from hand cards
										ArrayList<Card> cards = getPlayer().getHandCards();
										for (int i = cards.size() - 1; i >= 0; i--) {
											Card tempCard = cards.get(i);
											if (tempCard instanceof Equipment) {
												add(getCardButton(tempCard));
											}
										}

										// Add Equipment Card from Character
										Character[] selfChars = getPlayer().getCharacters();
										for (int i = 0; i < selfChars.length; i++) {
											Equipment maybeEquipment = selfChars[i].getEquipment();
											if (maybeEquipment != null) {
												add(new CharEquipmentButton(selfChars[i],
														maybeEquipment));
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
									class CharEquipmentButton extends JButton implements
											ActionListener {
										private Character character = null;

										public CharEquipmentButton(Character c, Equipment e) {
											this.character = c;
											setText(e.toString() + " (" + c + ")");
											addActionListener(this);
										}

										@Override
										public void actionPerformed(ActionEvent evt) {
											CardSelectDialog.this.dispose();
											character.setEquipment(null);
											method.targetMethod(null, null);
										}

									}

								}

							}

							// Select a Card
							new CardSelectDialog(getPlayer(), new TargetMethod() {

								@Override
								public void targetMethod(Character currentChar, Character target) {
									getPlayer().changeMP(5);
								}

							});
						}

					}, 0);

		} else {

			setValues(true, SUPPORT, false, 3, 2, 2, 2, false);

			passiveSkills = new CharSkill[2];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using T8's 2ndJob passive skill 1!");

				}

			}, 0);
			passiveSkills[1] = new CharSkill(this, false, 1, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using T8's 2ndJob passive skill 2!");

				}

			}, 0);
			activeSkills = new CharSkill[3]; // This character has 3 active
												// skills
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using T8's 2ndJob active skill 1!");

						}

					}, 4);
			activeSkills[1] = new CharSkill(this, true, 1, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using T8's 2ndJob active skill 2!");

						}

					}, 4);
			activeSkills[2] = new CharSkill(this, true, 2, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using T8's 2ndJob active skill 3!");

						}

					}, 4);
		}
	}

}