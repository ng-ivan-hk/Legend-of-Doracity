import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class LoadingScreen extends JWindow {

	private JProgressBar progressBar = null;

	public LoadingScreen() {
//		JLabel loadingLabel = new JLabel(Lang.loading);
//		loadingLabel.setFont(new Font(Lang.font, Font.BOLD, 50));
//		add(loadingLabel);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		add(progressBar);

		setSize(new Dimension(500, 50));
		setLocationRelativeTo(null);
		
	}

	public void setProgress(int progress, String message) {
		progressBar.setValue(progressBar.getValue() + progress);
		progressBar.setString(message);
		progressBar.update(progressBar.getGraphics());
	}

	public int getProgress() {
		return progressBar.getValue();
	}

}
