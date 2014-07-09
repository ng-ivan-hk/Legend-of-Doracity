import java.util.ArrayList;
import java.util.Arrays;

public class Player {

	private String name; // player's name
	private boolean player1;
	private int HP = Play.INIT_HP;
	private int MP = Play.INIT_MP;
	private Character[] characters = new Character[Play.CHAR_MAX];
	private ArrayList<Card> handCards = new ArrayList<Card>();

	public Player(String name, boolean player1) {
		this.name = name;
		this.player1 = player1;
	}

	public void setCharacters(Character c1, Character c2, Character c3,
			Character c4, Character c5) {

		characters[0] = c1;
		characters[1] = c2;
		characters[2] = c3;
		characters[3] = c4;
		characters[4] = c5;

		/* List characters */
		System.out.println("Player " + (player1 ? 1 : 2) + ": " + name
				+ "'s characters: ");
		for (int i = 0; i < Play.CHAR_MAX; i++)
			System.out.print(characters[i] + " ");
		System.out.println();
	}

	public String getName() {
		return name;
	}

	public boolean isPlayer1() {
		return player1;
	}

	public void listCards() {

		System.out.println("--- Listing Player: " + name + "'s cards...");
		if (handCards.isEmpty()) {
			System.out.println(name + " holds no cards!");
		} else {
			for (int i = 0; i < handCards.size(); i++) {
				System.out.println(i + 1 + ". " + handCards.get(i));
			}
		}

	}

	public void addCard(Card card) {
		handCards.add(card);
	}

	public void removeCard(int index) {
		handCards.remove(index - 1);
	}

	public void removeCard(Card card) {
		handCards.remove(card);
	}

	public void listStatus() {
		System.out.println("--- Listing Player: " + name + "'s status...");
		System.out.println("HP: " + HP + " | MP: " + MP);

	}

	public int changeHP(int HP) {

		// Return 0 if success
		// Return 1 if player HP <= 0 (dead)
		this.HP += HP;
		System.out.println("Player: " + name + " " + (HP > 0 ? "+" : "") + HP
				+ " HP");

		if (this.HP > Play.MAX_HP) {
			this.HP = Play.MAX_HP;
		}

		if (this.HP <= 0) {
			this.HP = 0;
			System.out.println("Player: " + name + " lost!");
			return 1;
		}

		return 0;
	}

	public int changeMP(int MP) {

		// Return 0 if success
		// Return 1 if MP is not enough (cannot use skills)
		if (this.MP + MP < 0) {
			System.out.println("Player: " + name + " don't have enough MP!");
			return 1;
		}

		this.MP += MP;
		System.out.println("Player: " + name + " " + (MP > 0 ? "+" : "") + MP
				+ " MP");

		if (this.MP > Play.MAX_MP) {
			this.MP = Play.MAX_MP;
		}

		return 0;
	}

	public int getHP() {
		return HP;
	}

	public int getMP() {
		return MP;
	}

	protected Character[] getCharacters() {
		return characters;
	}

	protected int indexOfChar(Character target) {
		for (int i = 0; i < characters.length; i++) {
			if (target == characters[i]) {
				return i;
			}
		}
		return -1;
	}

	protected ArrayList<Card> getHandCards() {
		return handCards;
	}

	protected int getNoOfHandCards() {
		return handCards.size();
	}

	protected Card getCard(int index) {
		return handCards.get(index - 1);
	}
	
	protected void sortChars(){
		Arrays.sort(characters, Play.charComparator);
	}

}
