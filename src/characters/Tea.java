import javax.swing.JLabel;

public class Tea extends Character {

	/**
	 * for Job 1 Active Skill Do-M<br>
	 * if true, opponent's girl characters can attack him only
	 */
	private boolean doM = false;

	public boolean isDoM() {
		return doM;
	}

	/**
	 * Check if the opponent has a Tea which doM is on
	 * 
	 * @param myChar
	 *            Is my Character a girl?
	 * @param opponent
	 *            Does the opponent Player has Tea?
	 * @param panel
	 *            Pass the panel and let this method do the thing
	 */
	public static void checkDoM(Character myChar, Player opponent,
			SuperCharSelectPanel panel) {
		// Does the player has Tea?
		Tea maybeTea = (Tea) opponent.contains(Tea.class);
		if (maybeTea == null) { // No Tea!
			return;
		} else if (maybeTea.isDoM() && !myChar.isMale()) { // Skill Activated!!!
			// Add Tea only
			panel.removeAll();
			panel.add(new JLabel(Lang.tea_DoM_attackTeaOnly));
			panel.add(maybeTea);
		}
	}

	@Override
	public void roundEndExtra() {
		doM = false;
	}

	@Override
	public void setDefense(boolean b) {
		if (!doM) // If doM is on, will not enter defense mode
			defense = b;
	}

	/* === Above are Tea's unique fields and methods === */

	public Tea(Player player) {
		super(player, 1);
	}

	@Override
	protected void setCharacter() {
		if (isFirstJob()) {

			setValues(true, SABER, true, 3, 2, 2, 2, true);

			passiveSkills = new CharSkill[2];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					System.out.println("Using Tea's 1stJob passive skill 1!");

				}

			}, 0);
			passiveSkills[1] = new CharSkill(this, false, 1, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					System.out.println("Using Tea's 1stJob passive skill 2!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							System.out.println("Using Tea's 1stJob active skill!");
							doM = true;
						}

					}, 3);

		} else {

			setValues(true, ARCHER, true, 3, 4, 3, 4, false);

			passiveSkills = new CharSkill[1];
			passiveSkills[0] = new CharSkill(this, false, 0, Command.NA, new CharSkillMethod() {

				@Override
				public void skillMethod(Character currentChar, Player opponent) {
					System.out.println("Using Tea's 2ndJob passive skill!");

				}

			}, 0);
			activeSkills = new CharSkill[1];
			activeSkills[0] = new CharSkill(this, true, 0, Command.BEFORE_BATTLE,
					new CharSkillMethod() {

						@Override
						public void skillMethod(Character currentChar, Player opponent) {
							System.out.println("Using Tea's 2ndJob active skill!");

						}

					}, 7);
		}
	}

}
