import java.util.ArrayList;
import java.util.Arrays;

/**
 * This represents a Player.
 * 
 * @author Ivan Ng
 * 
 */
public class Player {

	private String name; // player's name
	private boolean player1;
	private Player opponent = null;
	private int HP = Play.INIT_HP;
	private int MP = Play.INIT_MP;
	private Character[] characters = new Character[Play.CHAR_MAX];
	private ArrayList<Card> handCards = new ArrayList<Card>();

	public Player(String name, boolean player1) {
		this.name = name;
		this.player1 = player1;
	}

	public void setCharacters(Character c1, Character c2, Character c3, Character c4, Character c5) {

		characters[0] = c1;
		characters[1] = c2;
		characters[2] = c3;
		characters[3] = c4;
		characters[4] = c5;

		/* List characters */
		System.out.println("Player " + (player1 ? 1 : 2) + ": " + name + "'s characters: ");
		for (int i = 0; i < Play.CHAR_MAX; i++)
			System.out.print(characters[i] + " ");
		System.out.println();
	}

	public void setCharacters(int[] chars) {

		for (int i = 0; i < Play.CHAR_MAX; i++) {
			switch (chars[i]) {
			case 1:
				characters[i] = new Tea(this);
				break;
			case 2:
				characters[i] = new Livia(this);
				break;
			case 3:
				characters[i] = new Phoebell(this);
				break;
			case 4:
				characters[i] = new Map(this);
				break;
			case 5:
				characters[i] = new Iron(this);
				break;
			case 6:
				characters[i] = new FishBall(this);
				break;
			case 7:
				characters[i] = new Shirogane(this);
				break;
			case 8:
				characters[i] = new NonkiNobita(this);
				break;
			case 9:
				characters[i] = new Nana(this);
				break;
			case 10:
				characters[i] = new GameNobita(this);
				break;
			case 11:
				characters[i] = new Xander(this);
				break;
			case 12:
				characters[i] = new Butterfly(this);
				break;
			case 13:
				characters[i] = new Feather(this);
				break;
			case 14:
				characters[i] = new Kurokawa(this);
				break;
			case 15:
				characters[i] = new Herohim(this);
				break;
			case 16:
				characters[i] = new Knight(this);
				break;
			case 17:
				characters[i] = new Cloud(this);
				break;
			case 18:
				characters[i] = new MandyLee(this);
				break;
			case 19:
				characters[i] = new Kuru(this);
				break;
			case 20:
				characters[i] = new AK(this);
				break;
			case 21:
				characters[i] = new Kuzmon(this);
				break;
			case 22:
				characters[i] = new KaitoDora(this);
				break;
			case 23:
				characters[i] = new LittleCity(this);
				break;
			case 24:
				characters[i] = new WindSound(this);
				break;
			case 25:
				characters[i] = new Shin(this);
				break;
			case 26:
				characters[i] = new Mini(this);
				break;
			case 27:
				characters[i] = new T8(this);
				break;
			case 28:
				characters[i] = new Anthony(this);
				break;
			case 29:
				characters[i] = new Sasa(this);
				break;
			case 30:
				characters[i] = new SunnyShum(this);
				break;

			}
		}

	}

	public String toString() {
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

	/**
	 * @param HP
	 *            How much HP is changed? (e.g. pass 2 if +2HP, pass -1 if -1P)
	 * @return 0 if success<br>
	 *         1 if player HP <= 0
	 */
	public int changeHP(int HP) {

		if (HP < 0) {
			Play.shake(5, 300);
		}

		this.HP += HP;
		Play.printlnLog(Lang.player + ": " + name + " " + (HP >= 0 ? "+" : "") + HP + " HP");

		if (this.HP > Play.MAX_HP) {
			this.HP = Play.MAX_HP;
		}

		if (this.HP <= 0) {
			this.HP = 0;
			Play.printlnLog(Lang.player + ": " + name + Lang.log_lost);
			return 1;
		}

		return 0;
	}

	/**
	 * @param MP
	 *            How much MP is changed? (e.g. pass 2 if +2MP, pass -1 if -1MP)
	 * @return 0 if success<br>
	 *         1 if MP is not enough
	 */
	public int changeMP(int MP) {

		if (this.MP + MP < 0) {
			return 1;
		}

		this.MP += MP;
		Play.printlnLog(Lang.player + ": " + name + " " + (MP >= 0 ? "+" : "") + MP + " MP");

		if (this.MP > Play.MAX_MP) {
			this.MP = Play.MAX_MP;
		}

		return 0;
	}
	
	public void setOpponent(Player opponent){
		this.opponent = opponent;
	}
	
	public Player getOpponent() {
		return opponent;
	}

	public int getHP() {
		return HP;
	}

	public int getMP() {
		return MP;
	}

	public Character[] getCharacters() {
		return characters;
	}

	/**
	 * Check if this Player contains a specific Character.
	 * 
	 * @param charClass
	 *            Character.class
	 * @return that specific Character object; null if not found
	 */
	protected Character contains(Class<?> charClass) {
		for (int i = 0; i < Play.CHAR_MAX; i++) {
			if (charClass.isInstance(characters[i])) {
				return characters[i];
			}
		}
		return null;
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

	protected void sortChars() {
		Arrays.sort(characters, Play.charComparator);
	}

}
