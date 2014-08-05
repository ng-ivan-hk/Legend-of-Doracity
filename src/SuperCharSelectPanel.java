import javax.swing.JPanel;

/**
 * Act as the super class of
 * Play.PlayerArea.AttackButton.CharSelectDialog.CharSelectPanel and
 * CharSkill.CharSelectPanel
 * 
 * @author Ivan Ng
 * 
 */
@SuppressWarnings("serial")
abstract public class SuperCharSelectPanel extends JPanel {
	abstract public void add(Character character);
}
