/**
 * This represents an Equipment Card.
 * 
 * @author Ivan Ng
 * 
 */
abstract public class Equipment extends Card {

	private boolean saberOK;
	private boolean archerOK;
	private boolean casterOK;
	private boolean supportOK;
	private boolean doracityOK;
	private boolean academyOK;

	/**
	 * @param number
	 *            Equipment Number (e.g. E-001 -> pass 1)
	 * @param saberOK
	 *            Can Saber equip this?
	 * @param archerOK
	 *            Can Archer equip this?
	 * @param casterOK
	 *            Can Caster equip this?
	 * @param supportOK
	 *            Can Support equip this?
	 * @param doracityOK
	 *            Can a Doracity character equip this?
	 * @param academyOK
	 *            Can an Academy character equip this?
	 */
	public Equipment(int number, boolean saberOK, boolean archerOK, boolean casterOK,
			boolean supportOK, boolean doracityOK, boolean academyOK) {
		super(Lang.EquipmentTypes[number], number);
		this.saberOK = saberOK;
		this.archerOK = archerOK;
		this.casterOK = casterOK;
		this.supportOK = supportOK;
		this.doracityOK = doracityOK;
		this.academyOK = academyOK;
	}

	final public String getInfo() {
		String info = Lang.EquipmentInfos[number] + "<br>" + Lang.availableJob
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

	/**
	 * Check if the Character can use the card (matches this Equipment's
	 * requirement).
	 * 
	 * @param c
	 *            the Character who uses the card (to be equipped)
	 * @return 0 if success<br>
	 *         1 if wrong job<br>
	 *         10 if not Academy<br>
	 *         11 if not Doracity
	 */
	final public int check(Character c) {

		if ((saberOK && c.getJob() == Character.SABER)
				|| (archerOK && c.getJob() == Character.ARCHER)
				|| (casterOK && c.getJob() == Character.CASTER)
				|| (supportOK && c.getJob() == Character.SUPPORT)) {

			if (!doracityOK && c.isDoracity()) { // Not Academy
				return 10;
			} else if (!academyOK && !c.isDoracity()) { // Not Doracity
				return 11;
			} else {
				return 0;
			}

		} else { // Wrong Job
			return 1;
		}
	}

	/**
	 * Actions to be performed when equipping this Equipment. Should be only
	 * called by {@link Character#setEquipment(Equipment)}.
	 * 
	 * @param c
	 *            <code>Character</code> who equips this Equipment
	 */
	abstract protected void equipmentEffect(Character c);

	/**
	 * Actions to be performed when removing this Equipment. Should be only
	 * called by {@link Character#setEquipment(Equipment)}.
	 * 
	 * @param c
	 *            <code>Character</code> who equips this Equipment
	 */
	abstract protected void removeEquipmentEffect(Character c);

}
