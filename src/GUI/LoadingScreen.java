import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class LoadingScreen extends JWindow {

	private Point mouseDownCompCoords;

	private JProgressBar progressBar = null;
	private float progress = 0;
	private final int noOfTask = 73;
	private static Image bgImage = null;

	public LoadingScreen() {

		setContentPane(new MyComponent());
		Container cc = this.getContentPane();
		cc.setLayout(new FlowLayout());
		setBackgroundImage();
		add(Box.createRigidArea(new Dimension(400, 15)));
		add(progressBar = new JProgressBar(0, 100) {
			@Override
			// For Painting Border
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.GRAY);
				// Top
				g.fillRect(0, 0, getWidth(), 1);
				// Left
				g.fillRect(0, 0, 1, getHeight());
				// Bottom
				g.fillRect(0, getHeight() - 1, getWidth(), 1);
				// Right
				g.fillRect(getWidth() - 1, 0, 1, getHeight());

			}
		});

		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setPreferredSize(new Dimension(400, 50));
		progressBar.setBorderPainted(false);

		setSize(new Dimension(600, 315));
		setLocationRelativeTo(null);

		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent evt) {
			}

			public void mousePressed(MouseEvent evt) {
				mouseDownCompCoords = evt.getPoint();
			}

			public void mouseExited(MouseEvent evt) {
			}

			public void mouseEntered(MouseEvent evt) {
			}

			public void mouseClicked(MouseEvent evt) {
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent evt) {
			}

			public void mouseDragged(MouseEvent evt) {
				Point currCoords = evt.getLocationOnScreen();
				setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y
						- mouseDownCompCoords.y);
			}
		});

	}

	public void setProgress(int unit, String message) {
		progress += ((float) 100 / noOfTask * unit);
		progressBar.setValue((int) progress);
		progressBar.setString(message);
		repaint();
	}

	private static class MyComponent extends JPanel {

		public MyComponent() {
			setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
		}
	}

	public void setBackgroundImage() {
		BufferedImage src = null;
		try {
			src = ImageIO.read(Play.class.getResource("/resources/loadingScreenBG.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Resize Image smoothly
		bgImage = src.getScaledInstance(600, 315, Image.SCALE_AREA_AVERAGING);
	}

}
