import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * This is the GUI to start up the Play class.
 * 
 * @author Ivan Ng
 * 
 */

public class Preload extends JPanel {

	private CharSelectPanel p1 = null;
	private CharSelectPanel p2 = null;

	public Preload() {
		add(p1 = new CharSelectPanel());
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

			try {
				new Play().start();
				setVisible(false);

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class CharSelectPanel extends JPanel {
		public CharSelectPanel() {
			setLayout(new GridLayout(0, 6));
			for (int i = 0; i < 30; i++) {
				add(new CharButton(i + 1));
			}
		}
	}

	private class CharButton extends JButton implements ActionListener {
		private int number;

		public CharButton(int number) {
			this.number = number;
			setText(Lang.CharNames[number]);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

}
