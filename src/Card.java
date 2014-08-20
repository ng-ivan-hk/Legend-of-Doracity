import java.awt.Color;

/**
 * This represents a card (including Equipment, Item and Skill).
 * 
 * @author Ivan Ng
 * 
 */
abstract public class Card {

	private String name;
	protected int number;

	/**
	 * @param name
	 *            Name of the card
	 * @param number
	 *            Number of the card (E.g. E-001 for Adventurer's Sword, pass 1)
	 */
	public Card(String name, int number) {
		this.name = name;
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public String toString() {
		return name;
	}

	public String getInfo() {
		String type = null;
		Color color = null;
		if (this instanceof Equipment) {
			type = Lang.equipment;
			color = Style.equipmentDarkColor;
		} else if (this instanceof Item) {
			type = Lang.item;
			color = Style.itemDarkColor;
		} else {
			type = Lang.skill;
			color = Style.skillDarkColor;
		}
		return "<font color=" + Style.toRGB(color) + " >" + name + " (" + type + ")</font>";
	}

}
