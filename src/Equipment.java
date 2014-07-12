/**
 * This represents an Equipment Card.
 * 
 * @author Ivan Ng
 * 
 */
public class Equipment extends Card {

	public Equipment(int number) {
		super(Lang.EquipmentTypes[number], number);
	}

	public String getInfo() {
		return Lang.EquipmentInfos[number];

	}

}
