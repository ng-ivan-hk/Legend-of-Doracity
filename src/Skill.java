/**
 * This represents a Skill Card (NOT CharSkill).
 * 
 * @author Ivan Ng
 * 
 */
abstract public class Skill extends Card {

	private boolean saberOK;
	private boolean archerOK;
	private boolean casterOK;
	private boolean supportOK;
	private int requiredMP;
	private int occasion; // When can this card be used?

	/**
	 * @param number
	 *            number of the card (See Card())
	 */
	/**
	 * @param number
	 *            Skill Number (e.g. S-001 -> pass 1)
	 * @param saberOK
	 *            Can Saber equip this?
	 * @param archerOK
	 *            Can Archer equip this?
	 * @param casterOK
	 *            Can Caster equip this?
	 * @param supportOK
	 *            Can Support equip this?
	 * @param requiredMP
	 *            How many MP is required for this skill?
	 * @param occasion
	 *            See class Command: NA, BEFORE_BATTLE, DURING_BATTLE,
	 *            AFTER_BATTLE
	 */
	public Skill(int number, boolean saberOK, boolean archerOK, boolean casterOK,
			boolean supportOK, int requiredMP, int occasion) {
		super(Lang.SkillTypes[number], number);
		this.saberOK = saberOK;
		this.archerOK = archerOK;
		this.casterOK = casterOK;
		this.supportOK = supportOK;
		this.requiredMP = requiredMP;
		this.occasion = occasion;
	}

	final public String getInfo() {

		String info = "<font color=blue>" + Lang.occasion[occasion] + Lang.consume + requiredMP
				+ "MP</font><br>" + Lang.SkillInfos[number] + "<br>" + Lang.availableJob
				+ "<font color=blue>";
		if (saberOK) {
			info += Lang.JobNames[Character.SABER] + Lang.comma2;
		}
		if (archerOK) {
			info += Lang.JobNames[Character.ARCHER] + Lang.comma2;
		}
		if (casterOK) {
			info += Lang.JobNames[Character.CASTER] + Lang.comma2;
		}
		if (supportOK) {
			info += Lang.JobNames[Character.SUPPORT] + Lang.comma2;
		}

		// Remove the last comma2
		if (info.length() > 0) {
			info = info.substring(0, info.length() - 1);
		}

		info += "</font>";

		return info;

	}

	public int getOccasion() {
		return occasion;
	}

	/**
	 * Check if the Character can use the card (matches this Skill's
	 * requirement).
	 * 
	 * @param c
	 *            the Character who uses the card (the skill)
	 * @return 0 if success<br>
	 *         1 if wrong job<br>
	 */
	final public int check(Character c) {

		if ((saberOK && c.getJob() == Character.SABER)
				|| (archerOK && c.getJob() == Character.ARCHER)
				|| (casterOK && c.getJob() == Character.CASTER)
				|| (supportOK && c.getJob() == Character.SUPPORT) || c.getJob() == Character.NA) {

			return 0;

		} else { // Wrong Job
			return 1;
		}
	}

	final public void useSkill(Character c) {
		// Really use skill!!!
		skillEffect(c);
	}

	abstract protected void skillEffect(Character c);

}
