import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class LoadingScreen extends JWindow {

	public LoadingScreen() {
		try {
			setIconImage(new ImageIcon(Play.class.getResource("/resources/app_icon.png"))
					.getImage().getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING));
		} catch (NullPointerException e) {
		}
		
		JLabel loadingLabel = new JLabel(Lang.loading);
		loadingLabel.setFont(new Font(Lang.font, Font.BOLD, 50));
		add(loadingLabel);
		pack();
		WindowHandler.locateCenter(this);
	}

}
