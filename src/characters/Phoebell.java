import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class Phoebell extends Character {

	/**
	 * For Job 1 active skill: if false, reduce DefM (Mana Defense)
	 */
	private boolean reduceDefP = true;

	@Override
	public void jobChangeExtra() {
		if (getPlayer().contains(Livia.class) != null) {
			if (isFirstJob()) {
				Play.printlnLog(Lang.phoebell_together);
				changeAttack(+1, FOR_JOB_CHANGE);
			}
		}
	}

	/**
	 * Check if the Character is Phoebell and add an option for Physical Attack
	 * or Mana Attack.
	 * 
	 * @param myChar
	 *            Is my Character Phoebell?
	 * @param panel
	 *            Pass the panel and let this method do the thing
	 */
	public static void checkPhoebell(final Character myChar, SuperCharSelectPanel panel) {
		if (myChar instanceof Phoebell && myChar.isFirstJob()) {
			ActionListener l = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					if (evt.getActionCommand() == "Physical") {
						myChar.setPhysical(true);
					} else {
						myChar.setPhysical(false);
					}
				}
			};
			// Create Radio Buttons
			JRadioButton physicalButton = new JRadioButton(Lang.physical);
			physicalButton.setActionCommand("Physical");
			physicalButton.addActionListener(l);
			physicalButton.setSelected(true);
			JRadioButton manaButton = new JRadioButton(Lang.mana);
			manaButton.setActionCommand("Mana");
			manaButton.addActionListener(l);
			// Group the Buttons
			ButtonGroup group = new ButtonGroup();
			group.add(physicalButton);
			group.add(manaButton);
			// Add to panel
			panel.add(physicalButton);
			panel.add(manaButton);
		}
	}

	/* === Above are Phoebell's unique fields and methods === */

	public Phoebell(Player player) {
		super(player, 3);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 4, 3, 3, 4, true);

			passiveSkills = new CharSkill[2];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETED
				}

			}, 0);
			passiveSkills[1] = new CharSkill(this, false, 1, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETED
				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							
							// Override CharSelectPanel.setCharButtons()
							@SuppressWarnings("serial")
							class CharSelectDialog extends CharSkill.CharSelectDialog {

								public CharSelectDialog(Character currentChar, Player opponent,
										TargetMethod method) {
									super(currentChar, opponent, method);
								}

								@Override
								// Need to override this so CharSelectPanel
								// refers to here but not superclass
								protected CharSelectPanel getCharSelectPanel(boolean b) {
									return new CharSelectPanel(b);
								}

								class CharSelectPanel extends
										CharSkill.CharSelectDialog.CharSelectPanel {

									public CharSelectPanel(boolean listOpponent) {
										super(listOpponent);
									}

									@Override
									public void setCharButtons() {
										super.setCharButtons();
										ActionListener l = new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent evt) {
												if (evt.getActionCommand() == "Physical") {
													reduceDefP = true;
												} else {
													reduceDefP = false;
												}
											}
										};
										// Create Radio Buttons
										JRadioButton physicalButton = new JRadioButton(Lang.defP);
										physicalButton.setActionCommand("Physical");
										physicalButton.addActionListener(l);
										physicalButton.setSelected(true);
										JRadioButton manaButton = new JRadioButton(Lang.defM);
										manaButton.setActionCommand("Mana");
										manaButton.addActionListener(l);
										// Group the Buttons
										ButtonGroup group = new ButtonGroup();
										group.add(physicalButton);
										group.add(manaButton);
										// Add to panel
										add(physicalButton);
										add(manaButton);
									}

								}

							}

							// Select Character
							new CharSelectDialog(currentChar, opponent, new TargetMethod() {

								@Override
								public void targetMethod(Character currentChar, Character target) {
									if (reduceDefP) {
										target.changeDefP(-1, FOR_ROUND_END);
									} else {
										target.changeDefM(-1, FOR_ROUND_END);
									}
								}

							});
						}

					}, 3);

		} else {

			setValues(true, CASTER, false, 4, 3, 4, 3, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					// COMPLETED
				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							int oldHP = opponent.getHP();
							Character[] characters = opponent.getCharacters();
							for (int i = 0; i < characters.length; i++) {
								setPhysical(true);
								attack(characters[i]);
								characters[i].setDefense(false);
								setPhysical(false);
								attack(characters[i]);
								characters[i].setDefense(true);
							}
							int totalDamage = oldHP - opponent.getHP();
							Play.printlnLog(Lang.totalDamage + " -" + totalDamage + "HP");
							Play.shake(10, 1000);
						}

					}, 15);
		}
	}

}
