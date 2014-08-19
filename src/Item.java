/**
 * This represents an Item Card.
 * 
 * @author Ivan Ng
 * 
 */
abstract public class Item extends Card {

	public Item(int number) {
		super(Lang.ItemTypes[number], number);
	}

	final public String getInfo() {
		return super.getInfo() + "<br>" + Lang.ItemInfos[number];
	}

	abstract public void useItem(Player player);

}
