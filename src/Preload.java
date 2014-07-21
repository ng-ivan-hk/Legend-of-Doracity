import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 * This is the GUI to start up the Play class.
 * 
 * @author Ivan Ng
 * 
 */

@SuppressWarnings("serial")
public class Preload extends JPanel {

	private Play play = null;
	private JTextField p1Name = null;
	private JTextField p2Name = null;
	private CharSelectPanel p1 = null;
	private CharSelectPanel p2 = null;

	/**
	 * @param play
	 *            pass Play object so that this class can unlock main thread
	 */
	public Preload(Play play) {
		this.play = play;

		add(new JLabel(Lang.player + "1: "));
		add(p1Name = new JTextField("A", 15));
		add(p1 = new CharSelectPanel());

		add(new JLabel(Lang.player + "2: "));
		add(p2Name = new JTextField("B", 15));
		add(p2 = new CharSelectPanel());
		add(new StartButton());

		// For debugging: auto character selector
		JButton debugButton = new JButton();
		debugButton.setText("DEBUG");
		final Play debugPlay = play;
		debugButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				debugPlay.setPlayer("A", "B", new int[] { 1, 7, 13, 19, 25 }, new int[] { 2, 8, 14,
						20, 26 });
				synchronized (debugPlay) {
					debugPlay.notify();
				}
			}
		});
		add(debugButton);
	}

	private class StartButton extends JButton implements ActionListener {
		public StartButton() {
			setText(Lang.confirm);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			/* Set Player */

			// Get Player names
			String p1Name = Preload.this.p1Name.getText();
			String p2Name = Preload.this.p2Name.getText();

			// Is there any name field left blank?
			if (p1Name.equals("") || p2Name.equals("")) {
				JOptionPane.showMessageDialog(this, Lang.playerNameEmpty);
				return;
			}

			// Exactly 5 characters selected?
			if (p1.getCount() != 5 || p2.getCount() != 5) {
				JOptionPane.showMessageDialog(this, Lang.charCountWrong);
				return;
			}

			// Get Character numbers
			int[] p1CharNumbers = p1.getCharNumbers();
			int[] p2CharNumbers = p2.getCharNumbers();

			// No repeated characters?
			for (int i = 0; i < Play.CHAR_MAX; i++) {
				for (int j = 0; j < Play.CHAR_MAX; j++) {
					if (p1CharNumbers[i] == p2CharNumbers[j]) {
						JOptionPane.showMessageDialog(this, Lang.repeatedChar
								+ Lang.CharNames[p1CharNumbers[i]]);
						return;
					}
				}
			}

			// Okay let's do it!
			play.setPlayer(p1Name, p2Name, p1CharNumbers, p2CharNumbers);

			/* Unlock main thread */
			synchronized (play) {
				play.notify();
			}
		}
	}

	private class CharSelectPanel extends JPanel {
		private ArrayList<Integer> charNumbers = new ArrayList<Integer>();
		private int count = 0;

		public CharSelectPanel() {
			setLayout(new GridLayout(0, 6));
			for (int i = 0; i < 30; i++) {
				add(new CharButton(i + 1));
			}
		}

		public int getCount() {
			return count;
		}

		public int[] getCharNumbers() {
			// Convert ArrayList into int[]
			int[] array = new int[charNumbers.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = charNumbers.get(i).intValue();
			}

			return array;
		}

		private class CharButton extends JToggleButton implements ActionListener {
			private int number;
			private BufferedImage charImage = null;

			public CharButton(int number) {
				this.number = number;
				setPreferredSize(new Dimension(150, 40));
				setText(Lang.CharNames[number]);
				setCharImage();
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent e) {

				if (isSelected()) {
					if (count >= Play.CHAR_MAX) {
						setSelected(false);
						return;
					}
					charNumbers.add(number);
					count++;
				} else {
					charNumbers.remove((Integer) number);
					count--;
				}

			}

			public void setCharImage() {
				BufferedImage src = null;
				try {
					src = ImageIO.read(getCharImageURL());
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Crop Image
				charImage = src.getSubimage(120, 195, 300, 100);
				// Set Filter
				RescaleOp rescaleOp = new RescaleOp(0.3f, 182, null);
				rescaleOp.filter(charImage, charImage);
			}

			private URL getCharImageURL() {
				URL imageURL = Play.class.getResource("/resources/CharPics/mC"
						+ String.format("%03d", number) + "-1.png");
				if (imageURL == null) {
					imageURL = Play.class.getResource("/resources/CharPics/null.png");
				}
				return imageURL;
			}

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(charImage, 0, 0, null);
				if (!isSelected()) {
					g.setColor(new Color(0, 0, 0, 150));
					g.fillRect(0, 0, getWidth(), getHeight());
					g.setColor(Color.LIGHT_GRAY);
				} else {
					g.setColor(Color.BLACK);
				}
				g.setFont(new Font(Lang.font, Font.PLAIN, 15));
				g.drawString(Lang.CharNames[number], 5, 15);
			}
		}
	}

}
