import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class WindowHandler {

	/**
	 * Shake the window.
	 * 
	 * @param frame
	 *            The frame to be shaken
	 * @param distance
	 *            Shake Distance
	 * @param duration
	 *            Shake Duration
	 */
	public static void shake(final Window frame, final int distance, final int duration) {

		class ShakeHandler {
			final Point naturalLocation = frame.getLocation();

			final long startTime = System.currentTimeMillis();

			final Timer shakeTimer = new Timer(5, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					double TWO_PI = Math.PI * 2.0;
					double SHAKE_CYCLE = 50;

					long elapsed = System.currentTimeMillis() - startTime;
					double waveOffset = (elapsed % SHAKE_CYCLE) / SHAKE_CYCLE;
					double angle = waveOffset * TWO_PI;

					int SHAKE_DISTANCE = distance;

					int shakenX = (int) ((Math.sin(angle) * SHAKE_DISTANCE) + naturalLocation.x);
					frame.setLocation(shakenX, naturalLocation.y);
					frame.repaint();

					int SHAKE_DURATION = duration;
					if (elapsed >= SHAKE_DURATION) { // Stop shaking
						shakeTimer.stop();
						frame.setLocation(naturalLocation);
						frame.repaint();
					}
				}
			});

			ShakeHandler() {
				shakeTimer.start();
			}

		}

		new ShakeHandler();

	}

}
