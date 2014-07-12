/**
 * This represents a card (including Equipment, Item and Skill).
 * 
 * @author Ivan Ng
 * 
 */
abstract public class Card {

	private String name;
	protected int number;
	private String info;

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
		return info;
	}

}
