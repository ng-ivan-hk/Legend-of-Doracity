/**
 * Used when a CharSkill requires a target to be selected. What to do after a
 * character is selected in CharSkill.CharSelectDialog?
 * 
 * @author Ivan Ng
 * 
 */
public interface TargetMethod {
	public void targetMethod(Character currentChar, Character target);
}
