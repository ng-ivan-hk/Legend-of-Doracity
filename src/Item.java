public class Item extends Card {

	public Item(int number) {
		super(Lang.ItemTypes[number], number);
	}

	public String getInfo() {
		return Lang.ItemInfos[number];

	}

}
