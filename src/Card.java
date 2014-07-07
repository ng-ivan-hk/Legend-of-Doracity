abstract public class Card {

	private String name;
	protected int number;
	private String info;

	public Card(String name, int number) {
		this.name = name;
		this.number = number;
	}

	public int getNumber(){
		return number;
	}
	
	public String toString() {
		return name;
	}
	
	public String getInfo(){
		return info;
	}

}
