/**
 * This represents an Item Card.
 * 
 * @author Ivan Ng
 * 
 */
public class Item extends Card {

	public Item(int number) {
		super(Lang.ItemTypes[number], number);
	}

	public String getInfo() {
		return Lang.ItemInfos[number];

	}

}
