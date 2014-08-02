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
		return Lang.EquipmentInfos[number];
	}

	/**
	 * Check if the Character can use the card. If yes, use. Otherwise, return
	 * an error code.
	 * 
	 * @param c
	 *            the Character who uses the card
	 * @return 0 if success<br>
	 *         1 if wrong job<br>
	 *         10 if not Academy<br>
	 *         11 if not Doracity
	 */
	final public int useEquipment(Character c) {

		if ((saberOK && c.getJob() == Character.SABER)
				|| (archerOK && c.getJob() == Character.ARCHER)
				|| (casterOK && c.getJob() == Character.CASTER)
				|| (supportOK && c.getJob() == Character.SUPPORT)) {

			if (!doracityOK && c.isDoracity()) { // Not Academy
				return 10;
			} else if (!academyOK && !c.isDoracity()) { // Not Doracity
				return 11;
			} else {
				equipmentEffect(c);
				return 0;
			}

		} else { // Wrong Job
			return 1;
		}
	}

	/**
	 * Really use the equipment.
	 * 
	 * @param c
	 */
	abstract protected void equipmentEffect(Character c);

}
