//Initialize a square that has a normal card inside.
package gui.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import logic.GameLogic;
import logic.SquareState;
import logic.SquareType;

public class CardSquare extends Square implements flipAble, matchAble {
	private boolean usedToFlipUp;

	private final String cardURL1;
	private final String cardURL2;
	private final String cardURL3;
	private final String cardURL4;
	private final String cardURL5;
	private final String cardURL6;
	private final String cardURL7;
	private final String cardURL8;
	private final String cardURL9;
	private final String cardURL10;
	private final String cardURL11;
	private final String cardURL12;
	private final String cardURL13;
	private final String cardURL14;
	private final String cardURL15;

	private final Media flipSoundMedia;
	private final Media matchSoundMedia;

	private final List<SquareType> squareTypeRef = Arrays.asList(SquareType.CARD1, SquareType.CARD2, SquareType.CARD3,
			SquareType.CARD4, SquareType.CARD5, SquareType.CARD6, SquareType.CARD7, SquareType.CARD8, SquareType.CARD9,
			SquareType.CARD10, SquareType.CARD11, SquareType.CARD12, SquareType.CARD13, SquareType.CARD14,
			SquareType.CARD15);

	private List<String> listCardURL = new ArrayList<>();

	private SquareType squareType;
	private final int squareTypeIndex;

	public CardSquare(int x, int y) {

		super(x, y);
		setUsedToFlipUp(false);

		// Load the picture URL of each card.
		cardURL1 = ClassLoader.getSystemResource("card1.png").toString();
		cardURL2 = ClassLoader.getSystemResource("card2.png").toString();
		cardURL3 = ClassLoader.getSystemResource("card3.png").toString();
		cardURL4 = ClassLoader.getSystemResource("card4.png").toString();
		cardURL5 = ClassLoader.getSystemResource("card5.png").toString();
		cardURL6 = ClassLoader.getSystemResource("card6.png").toString();
		cardURL7 = ClassLoader.getSystemResource("card7.png").toString();
		cardURL8 = ClassLoader.getSystemResource("card8.png").toString();
		cardURL9 = ClassLoader.getSystemResource("card9.png").toString();
		cardURL10 = ClassLoader.getSystemResource("card10.png").toString();
		cardURL11 = ClassLoader.getSystemResource("card11.png").toString();
		cardURL12 = ClassLoader.getSystemResource("card12.png").toString();
		cardURL13 = ClassLoader.getSystemResource("card13.png").toString();
		cardURL14 = ClassLoader.getSystemResource("card14.png").toString();
		cardURL15 = ClassLoader.getSystemResource("card15.png").toString();
		this.listCardURL = Arrays.asList(cardURL1, cardURL2, cardURL3, cardURL4, cardURL5, cardURL6, cardURL7, cardURL8,
				cardURL9, cardURL10, cardURL11, cardURL12, cardURL13, cardURL14, cardURL15);

		// Load the flip and match sound URL
		flipSoundMedia = new Media(ClassLoader.getSystemResource("flipSound.mp3").toString());
		matchSoundMedia = new Media(ClassLoader.getSystemResource("match.mp3").toString());

		squareType = GameLogic.getInstance().getBoardType()[getxPosition()][getyPosition()];
		squareTypeIndex = squareTypeRef.indexOf(squareType);

		this.setOnMouseClicked(event -> onClickHandler(event));
	}

	// Changing normal card square state and color. Then play a sound.
	private void onClickHandler(MouseEvent e) {
		GameLogic instance = GameLogic.getInstance();
		if (!instance.isGameEnd()) {
			if (!instance.isGamePause()) {

				// Use left click to enter flip mode
				if (e.getButton() == MouseButton.PRIMARY) {
					if (instance.getBoardState()[getxPosition()][getyPosition()] == SquareState.FLIPDOWN
							&& !instance.isWaitForFlipDown()) {
						instance.addMove();

						// Enter ready to match state if there is one card that already flip up at the
						// moment.
						if (instance.isReadyToMatch()) {

							// The selected card can't be matched with its own card.
							if (!checkSameCard(instance.getFlipUpCard())) {
								this.flip(listCardURL.get(getSquareTypeIndex()), "BISQUE");

								// Check weather the pair is correct or not.
								if (this.match(instance.getFlipUpCard())) { // If the pair is correct.

									instance.addScore(40);
									instance.updateCardLeft(-2);

									instance.updateState(getxPosition(), getyPosition(), SquareState.MATCHED);
									instance.updateState(instance.getFlipUpCard().getxPosition(),
											instance.getFlipUpCard().getyPosition(), SquareState.MATCHED);

									instance.getBoardType()[getxPosition()][getyPosition()] = SquareType.NOTHING;
									instance.getBoardType()[instance.getFlipUpCard().getxPosition()][instance
											.getFlipUpCard().getyPosition()] = SquareType.NOTHING;

									instance.setWaitForFlipDown(true);

									PlaySound playSound = new PlaySound(matchSoundMedia, 0.1);
									Thread playSoundThread = new Thread(playSound);

									new Thread() {
										public void run() {
											try {
												Thread.sleep(500);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
											unFlip(listMarkURL.get(getSquareMarkIndex()), "lightgreen");
											instance.getFlipUpCard().unFlip(
													listMarkURL.get(instance.getFlipUpCard().getSquareMarkIndex()),
													"lightgreen");
											playSoundThread.start();

											instance.setWaitForFlipDown(false);
										}
									}.start();

								} else { // If the pair is incorrect.

									instance.setWaitForFlipDown(true);

									new Thread() {
										public void run() {
											try {
												Thread.sleep(800);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
											unFlip(listMarkURL.get(getSquareMarkIndex()), "azure");
											instance.getFlipUpCard().unFlip(
													listMarkURL.get(instance.getFlipUpCard().getSquareMarkIndex()),
													"rgb(" + getBaseColor().getRed() * 250 + ","
															+ getBaseColor().getGreen() * 250 + ","
															+ getBaseColor().getBlue() * 250 + ");");
											instance.updateState(getxPosition(), getyPosition(), SquareState.FLIPDOWN);
											instance.updateState(instance.getFlipUpCard().getxPosition(),
													instance.getFlipUpCard().getyPosition(), SquareState.FLIPDOWN);
											instance.setWaitForFlipDown(false);
										}
									}.start();
								}

								instance.setReadyToMatch(false);
							}

						} else { // If there is no card flip up at the moment.
							instance.setFlipUpCard(this);
							instance.setReadyToMatch(true);
							instance.updateState(getxPosition(), getyPosition(), SquareState.FLIPUP);
							this.flip(listCardURL.get(getSquareTypeIndex()), "BISQUE");
						}

					}
				}
				// Right click to enable secure mode
				else if (e.getButton() == MouseButton.SECONDARY && !usedToFlipUp) {
					// The selected card flip down and currently not in secure mode
					if (instance.getBoardState()[getxPosition()][getyPosition()] == SquareState.FLIPDOWN) {

						DrawThread drawSecure = new DrawThread(secureStateURL, "rgb(255, 204, 0)");
						Thread drawSecureThread = new Thread(drawSecure);

						PlaySound playSound = new PlaySound(secureSoundMedia, 0.1);
						Thread playSoundThread = new Thread(playSound);

						playSoundThread.setDaemon(true);
						playSoundThread.start();
						drawSecureThread.setDaemon(true);
						drawSecureThread.start();

						instance.updateState(getxPosition(), getyPosition(), SquareState.SECURED);
					}
					// If this card already in secure mode.
					else if (instance.getBoardState()[getxPosition()][getyPosition()] == SquareState.SECURED
							&& !instance.isWaitForFlipDown()) {
						instance.updateState(getxPosition(), getyPosition(), SquareState.FLIPDOWN);
						PlaySound playSound = new PlaySound(unsecureSoundMedia, 0.1);
						Thread playSoundThread = new Thread(playSound);
						if (usedToFlipUp) {
							DrawThread drawUnSecure = new DrawThread(listMarkURL.get(getSquareMarkIndex()),
									"rgb(" + getBaseColor().getRed() * 250 + "," + getBaseColor().getGreen() * 250 + ","
											+ getBaseColor().getBlue() * 250 + ");");
							Thread drawUnSecureThread = new Thread(drawUnSecure);

							drawUnSecureThread.setDaemon(true);
							drawUnSecureThread.start();
						} else {
							initializeCellColor();
						}
						playSoundThread.setDaemon(true);
						playSoundThread.start();
					}
				}
			}
		}
	}

	@Override
	public void flip(String imageURL, String backgroundColorName) {

		this.setStyle(
				"-fx-border-radius: 15;" + "-fx-background-radius: 15;" + "-fx-background-color: " + backgroundColorName
						+ ";" + "-fx-background-image: url(\"" + imageURL + "\");" + "-fx-background-size: cover;");

		setUsedToFlipUp(true);

		PlaySound playSound = new PlaySound(flipSoundMedia, 0.3);
		Thread playSoundThread = new Thread(playSound);

		playSoundThread.setDaemon(true);
		playSoundThread.start();

	}

	public void unFlip(String imageURL, String backgroundColorName) {

		new Thread() {
			public void run() {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						setStyle("-fx-border-radius: 15;" + "-fx-background-radius: 15;" + "-fx-background-color: "
								+ backgroundColorName + ";" + "-fx-background-image: url(\"" + imageURL + "\");"
								+ "-fx-background-size: cover;");
					}
				});
			}
		}.start();
	}

	// Check weather the card is match with another card or not.
	public boolean match(CardSquare other) {
		if (GameLogic.getInstance().getBoardType()[getxPosition()][getyPosition()]
				.equals(GameLogic.getInstance().getBoardType()[other.getxPosition()][other.getyPosition()])) {
			return true;
		}
		return false;
	}

	// Check if the selected card is the same or not.
	public boolean checkSameCard(CardSquare other) {
		if (this.getxPosition() == other.getxPosition() && this.getyPosition() == other.getyPosition())
			return true;
		else
			return false;
	}

	public boolean isUsedToFlipUp() {
		return usedToFlipUp;
	}

	public void setUsedToFlipUp(boolean usedToFlipUp) {
		this.usedToFlipUp = usedToFlipUp;
	}

	public SquareType getSquareType() {
		return squareType;
	}

	public int getSquareTypeIndex() {
		return squareTypeIndex;
	}
}