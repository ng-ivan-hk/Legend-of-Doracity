/**
 * For holding a method for character's skill so that method body can be passed
 * in a character subclass to CharSkill().
 * 
 * @author Ivan Ng
 * 
 */
public interface CharSkillMethod {
	public void skillMethod(Character c1, Character c2);
}
