import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LoadingScreen extends JFrame {

	public LoadingScreen() {
		try {
			setIconImage(new ImageIcon(Play.class.getResource("/resources/app_icon.png"))
					.getImage().getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING));
		} catch (NullPointerException e) {
		}
		
		setUndecorated(true);
		JLabel loadingLabel = new JLabel(Lang.loading);
		loadingLabel.setFont(new Font(Lang.font, Font.BOLD, 50));
		add(loadingLabel);
		pack();
		WindowHandler.locateCenter(this);
	}

}
