/**
 * This represents a Skill Card (NOT CharSkill).
 * 
 * @author Ivan Ng
 * 
 */
public class Skill extends Card {

	private int occasion; // When can this card be used?

	/**
	 * @param number
	 *            number of the card (See Card())
	 */
	public Skill(int number) {
		super(Lang.SkillTypes[number], number);
		switch (number) {
		case 2:
		case 3:
		case 4:
		case 6:
		case 8:
		case 10:
		case 11:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
			occasion = Command.BEFORE_BATTLE;
			break;
		default:
			occasion = Command.DURING_BATTLE;
			break;
		}
	}

	public String getInfo() {
		return "<font color=blue>" + Lang.occasion[occasion] + "</font><br>"
				+ Lang.SkillInfos[number];

	}

	public int getOccasion() {
		return occasion;
	}

}
