import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ShadowPane extends JPanel {
	
	public ShadowPane() {
		setLayout(new BorderLayout());
		setOpaque(false);
		setBackground(Color.BLACK);
		setBorder(new EmptyBorder(0, 0, 10, 10));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
		g2d.fillRect(10, 10, getWidth(), getHeight());
		g2d.dispose();
	}
	
}
