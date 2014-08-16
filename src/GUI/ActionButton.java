import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * This is the super class of the five action buttons: AttackButton,
 * CastSkillButton, JobChangeButton, DrawButton and PassButton. This styles them
 * in an unified way.
 * 
 * @author Ivan Ng
 * 
 */
@SuppressWarnings("serial")
public class ActionButton extends JButton {

	public final static Color normalColor = new Color(250, 250, 255);
	public final static Color disabledColor = new Color(220, 220, 230);
	public final static Color hoverColor = new Color(213, 225, 242);
	public final static Color borderColor = new Color(100, 100, 100);

	public ActionButton() {

		setMinimumSize(new Dimension(91, 30));
		setPreferredSize(new Dimension(91, 30));
		setMaximumSize(new Dimension(91, 30));
		setMouseExitedStyle();
		setFocusPainted(false);

		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				if (isEnabled()) {
					setBackground(hoverColor);
				}
			}

			public void mouseExited(MouseEvent evt) {
				setMouseExitedStyle();
			}
			
		});
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		setMouseExitedStyle();
	}

	private void setMouseExitedStyle() {
		if (isEnabled()) {
			setBackground(normalColor);
			setBorder(BorderFactory.createLineBorder(borderColor));
		} else {
			setBackground(disabledColor);
			setBorder(BorderFactory.createLineBorder(disabledColor));
		}
	}
}
