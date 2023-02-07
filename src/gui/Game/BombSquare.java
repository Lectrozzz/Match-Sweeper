//Initialize a square that has a bomb inside.
package gui.Game;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import logic.GameLogic;
import logic.SquareState;

public class BombSquare extends Square {

	private final String mineURL;
	private final Media explosionMedia;

	public BombSquare(int x, int y) {
		super(x, y);
		mineURL = ClassLoader.getSystemResource("bomb.png").toString();

		explosionMedia = new Media(ClassLoader.getSystemResource("explosionSound.mp3").toString());

		this.setOnMouseClicked(event -> onClickHandler(event));
	}

	// Changing bomb square state and color. Then play a sound.
	private void onClickHandler(MouseEvent e) {
		GameLogic instance = GameLogic.getInstance();
		if (!instance.isGameEnd()) {
			if (!instance.isGamePause()) {
				if (e.getButton() == MouseButton.PRIMARY
						&& instance.getBoardState()[getxPosition()][getyPosition()] != SquareState.SECURED) {
					// Flip this card
					instance.addMove();
					if (instance.getBoardState()[getxPosition()][getyPosition()] != SquareState.REVEALED
							&& !instance.isWaitForFlipDown()) {

						instance.addScore(-100);
						instance.updateBombLeft(-1);

						DrawThread drawMine = new DrawThread(mineURL, "rgb(255, 153, 153)");
						Thread drawMineThread = new Thread(drawMine);

						DrawThread drawSquareMark = new DrawThread(listMarkURL.get(getSquareMarkIndex()),
								"rgb(255, 153, 153)");
						Thread drawSquareMarkThread = new Thread(drawSquareMark);

						PlaySound playSound = new PlaySound(explosionMedia, 0.3);
						Thread playSoundThread = new Thread(playSound);

						new Thread() {
							public void run() {
								playSoundThread.start();
								drawMineThread.start();
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								drawSquareMarkThread.start();
							}
						}.start();

						instance.updateState(getxPosition(), getyPosition(), SquareState.REVEALED);
					}
				} else if (e.getButton() == MouseButton.SECONDARY
						&& instance.getBoardState()[getxPosition()][getyPosition()] != SquareState.REVEALED) {
					// Enable secure mode
					if (instance.getBoardState()[getxPosition()][getyPosition()] != SquareState.SECURED) {

						DrawThread drawSecure = new DrawThread(secureStateURL, "rgb(255, 204, 0)");
						Thread drawSecureThread = new Thread(drawSecure);
						PlaySound playSound = new PlaySound(secureSoundMedia, 0.1);
						Thread playSoundThread = new Thread(playSound);

						playSoundThread.setDaemon(true);
						playSoundThread.start();
						drawSecureThread.setDaemon(true);
						drawSecureThread.start();

						instance.updateState(getxPosition(), getyPosition(), SquareState.SECURED);
					} else {
						PlaySound playSound = new PlaySound(unsecureSoundMedia, 0.1);
						Thread playSoundThread = new Thread(playSound);

						playSoundThread.setDaemon(true);
						playSoundThread.start();
						this.initializeCellColor();
						instance.updateState(getxPosition(), getyPosition(), SquareState.CONCEALED);
					}
				}
			}
		}
	}
}