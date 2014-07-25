import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LoadingScreen extends JFrame {

	public LoadingScreen() {
		setUndecorated(true);
		JLabel loadingLabel = new JLabel(Lang.loading);
		loadingLabel.setFont(new Font(Lang.font, Font.BOLD, 50));
		add(loadingLabel);
		pack();
		WindowHandler.locateCenter(this);
	}

}
