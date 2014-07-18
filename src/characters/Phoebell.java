import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class Phoebell extends Character {

	@Override
	public void jobChangeExtra() {
		if (getPlayer().contains(Livia.class) != null) {
			if (isFirstJob()) {
				Play.printlnLog(Lang.phoebell_together);
				setAttack(getAttack() + 1);
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
							System.out.println("Using Phoebell's 1stJob active skill!");

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
							System.out.println("Using Phoebell's 2ndJob active skill!");

						}

					}, 15);
		}
	}

}
