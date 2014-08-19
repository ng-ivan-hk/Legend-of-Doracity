import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * This UI class overwrites the original JButton style.
 * 
 * @author Ivan Ng
 * 
 */
public class MyButtonUI extends BasicButtonUI {

	private final static MyButtonUI myButtonUI = new MyButtonUI();

	private final static Dimension buttonSize = new Dimension(90, 30);

	public final static Color normalColor = new Color(250, 250, 255);
	public final static Color disabledColor = new Color(0, 0, 0, 10);
	public final static Color hoverColor = new Color(213, 225, 242);
	public final static Color pressedColor = new Color(163, 189, 227);
	public final static Color borderColor = new Color(100, 100, 100);

	public static ComponentUI createUI(JComponent c) {
		return myButtonUI;
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		c.setBackground(normalColor);
		c.setBorder(BorderFactory.createLineBorder(borderColor));
		c.setMinimumSize(buttonSize);
		c.setPreferredSize(buttonSize);
		c.setMaximumSize(buttonSize);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();
		Dimension d = b.getSize();

		if (b.isRolloverEnabled() && model.isRollover()) {
			g.setColor(getHoverColor());
		} else {
			g.setColor(b.getBackground());
		}
		g.fillRect(0, 0, d.width, d.height);

		if (!c.isEnabled()) {
			g.setColor(disabledColor);
			g.fillRect(0, 0, d.width, d.height);
		}

		super.paint(g, c);
	}

	@Override
	protected void paintText(Graphics g, AbstractButton b, java.awt.Rectangle textRect, String text) {

		ButtonModel model = b.getModel();
		FontMetrics fm = g.getFontMetrics();

		/* Draw the Text */
		if (model.isEnabled()) {
			g.setColor(b.getForeground());
			g.drawString(text, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent()
					+ getTextShiftOffset());

		} else {
			g.setColor(Color.GRAY);
			g.drawString(text, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent()
					+ getTextShiftOffset());
		}

	}

	@Override
	public void paintButtonPressed(Graphics g, AbstractButton b) {
		Dimension d = b.getSize();

		g.setColor(getPressedColor());
		g.fillRect(0, 0, d.width, d.height);

	}

	public Color getHoverColor() {
		return hoverColor;
	}

	public Color getPressedColor() {
		return pressedColor;
	}

}
