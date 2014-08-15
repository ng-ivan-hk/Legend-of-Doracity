import java.awt.Dimension;

import javax.swing.JProgressBar;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class LoadingScreen extends JWindow {

	private JProgressBar progressBar = null;
	private float progress = 0;
	private final int noOfTask = 73;

	public LoadingScreen() {

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		add(progressBar);

		setSize(new Dimension(500, 50));
		setLocationRelativeTo(null);

	}

	public void setProgress(int unit, String message) {
		progress += ((float) 100 / noOfTask * unit);
		progressBar.setValue((int) progress);
		progressBar.setString(message);
		progressBar.update(progressBar.getGraphics());
	}

}
