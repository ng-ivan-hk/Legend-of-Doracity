import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.imageio.ImageIO;
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

	public final static Color hoverColor = new Color(213, 225, 242);
	public final static Color pressedColor = new Color(163, 189, 227);
	public final static Color borderColor = new Color(100, 100, 100);

	private Image image = null;
	private Image disabledImage = null;

	private boolean mouseOver = false;
	private boolean mousePressed = false;

	public ActionButton() {

		Dimension size = new Dimension(50, 50);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);

		setFocusPainted(false);
		setBorderPainted(false);

		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				mouseOver = true;
				repaint();
			}

			public void mouseExited(MouseEvent evt) {
				mouseOver = false;
				repaint();
			}

			public void mousePressed(MouseEvent evt) {
				mousePressed = true;
				repaint();
			}

			public void mouseReleased(MouseEvent evt) {
				mousePressed = false;
				repaint();
			}

		});

	}

	public void setDescription(String name, String info) {
		setToolTipText("<html><font color=blue>" + name + "</font><br>" + info + "</html>");
	}

	/**
	 * This will load the image from /resources folder according to input text.
	 */
	public void setImageName(String name) {

		BufferedImage src = null;
		try {
			src = ImageIO.read(Play.class.getResource("/resources/icons/ActionButton-" + name
					+ ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Resize Image smoothly
		image = src.getScaledInstance(46, 46, Image.SCALE_AREA_AVERAGING);

		// Make a disabled version
		BufferedImage bi1 = toBufferedImage(image);
		colorImage(bi1, Color.LIGHT_GRAY);
		disabledImage = bi1;

	}

	@Override
	public void paintComponent(Graphics g) {

		// Draw Background
		g.setColor(isEnabled() ? (mouseOver ? (mousePressed ? pressedColor : hoverColor)
				: Color.WHITE) : Style.panelColor);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Draw Image
		g.drawImage(isEnabled() ? image : disabledImage, 2, 2, null);

		if (isEnabled()) {
			// Draw Border: Top Left Bottom Right
			g.setColor(borderColor);
			g.fillRect(0, 0, getWidth(), 1);
			g.fillRect(0, 0, 1, getHeight());
			g.fillRect(0, getHeight() - 1, getWidth(), 1);
			g.fillRect(getWidth() - 1, 0, 1, getHeight());
		}

	}

	/**
	 * Color all non-transparent part of the image to another color.
	 * 
	 * @param image
	 *            image to be colored
	 * @param color
	 *            new color of the image
	 * @return colored BufferedImage
	 */
	private static BufferedImage colorImage(BufferedImage image, Color color) {

		int width = image.getWidth();
		int height = image.getHeight();
		WritableRaster raster = image.getRaster();

		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				pixels[0] = color.getRed();
				pixels[1] = color.getGreen();
				pixels[2] = color.getBlue();
				raster.setPixel(xx, yy, pixels);
			}
		}

		return image;

	}

	/**
	 * Convert an Image to a BufferedImage. In fact this only create a new
	 * BufferedImage but not modifying the original Image.
	 * 
	 * @param img
	 *            Image to be converted
	 * @return BufferedImage
	 */
	public static BufferedImage toBufferedImage(final Image img) {

		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;

	}

}
