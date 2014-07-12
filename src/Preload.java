import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

			public CharButton(int number) {
				this.number = number;
				setText(Lang.CharNames[number]);
				addActionListener(this);
			}

			public int getNumber() {
				return number;
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
		}
	}

}
