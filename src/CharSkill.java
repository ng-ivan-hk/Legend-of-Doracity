

public class CharSkill {

	private Character character;
	private boolean active; // if false, passive
	private int number;
	private int occasion;

	public CharSkill(Character character, boolean active, int number, int occasion) {
		this.character = character;
		this.active = active;
		this.number = number;
		this.occasion = occasion;
	}

	public boolean isActive() {
		return active;
	}

	public int getNumber() {
		return number;
	}

	public int getOccasion() {
		return occasion;
	}

	public String getName() {
		return (character.isFirstJob() ? Lang.CharSkills1 : Lang.CharSkills2)[character.getNumber()][active ? 1
				: 0][number][0];
	}

	public String getInfo() {
		return (character.isFirstJob() ? Lang.CharSkills1 : Lang.CharSkills2)[character.getNumber()][active ? 1
				: 0][number][1];
	}

}
