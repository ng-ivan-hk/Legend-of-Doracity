import java.awt.*;
import javax.swing.*;

public class Undecorated {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("Testing");
				frame.setUndecorated(true);
				frame.setBackground(new Color(255, 255, 255, 254)); // THIS LINE
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new JLabel("We did it できた"));
				frame.setSize(new Dimension(200, 200));
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});

	}

}
