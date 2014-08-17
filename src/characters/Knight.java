import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

public class Knight extends Character {

	public Knight(Player player) {
		super(player, 16);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 3, 3, 3, 3, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Knight's 1stJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {

							@SuppressWarnings("serial")
							class EffectSelector extends JDialog {
								public EffectSelector() {
									super((java.awt.Frame) null, true);
									Container cc = getContentPane();
									cc.setLayout(new BoxLayout(cc, BoxLayout.Y_AXIS));
									cc.add(new EffectButton(1));
									cc.add(new EffectButton(2));
									cc.add(new EffectButton(3));

									setTitle(Lang.effectSelection);
									pack();
									setLocationRelativeTo(null);
									setResizable(false);
									setVisible(true);
								}

								class EffectButton extends JButton implements ActionListener {
									private int type;

									public EffectButton(int type) {
										this.type = type;
										String info = null;
										switch (type) {
										case 1:
											info = Lang.attack + "+1";
											break;
										case 2:
											info = Lang.defP + "+1";
											break;
										case 3:
											info = Lang.defM + "+1";
											break;
										}
										setText(info);
										addActionListener(this);
									}

									@Override
									public void actionPerformed(ActionEvent e) {
										EffectSelector.this.dispose();
										switch (type) {
										case 1:
											changeAttack(1, FOR_ROUND_END);
											break;
										case 2:
											changeDefP(1, FOR_ROUND_END);
											break;
										case 3:
											changeDefM(1, FOR_ROUND_END);
											break;
										}
										activeSkills[0].setDoNotPass(false);
									}
								}

							}

							new EffectSelector();
						}

					}, 1);
			activeSkills[0].setDoNotPass(true);

		} else {

			setValues(true, SABER, true, 5, 4, 3, 3, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					Play.printlnLog("Using Knight's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.DURING_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							Play.printlnLog("Using Knight's 2ndJob active skill!");

						}

					}, 8);
		}
	}

}
