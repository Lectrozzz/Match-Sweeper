//Create a base square for initializing the base color and card picture.
package gui.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import logic.GameLogic;
import logic.SquareMark;

public class Square extends Pane {

	private Color baseColor;
	private int xPosition;
	private int yPosition;
	protected final String secureStateURL;
	protected final String oneURL;
	protected final String twoURL;
	protected final String threeURL;
	protected final String fourURL;
	protected final String fiveURL;
	protected final String sixURL;
	protected final String zeroURL;

	protected final Media secureSoundMedia;
	protected final Media unsecureSoundMedia;

	protected SquareMark squareMark;
	protected final int squareMarkIndex;

	protected List<String> listMarkURL = new ArrayList<>();

	protected final List<SquareMark> squareMarkRef = Arrays.asList(SquareMark.ZERO, SquareMark.ONE, SquareMark.TWO,
			SquareMark.THREE, SquareMark.FOUR, SquareMark.FIVE, SquareMark.SIX);

	public Square(int x, int y) {
		setxPosition(x);
		setyPosition(y);
		setPrefSize(110, 110);
		setBaseColor(Color.AZURE);
		setBorder(new Border(
				new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		initializeCellColor();

		oneURL = ClassLoader.getSystemResource("one.png").toString();
		twoURL = ClassLoader.getSystemResource("two.png").toString();
		threeURL = ClassLoader.getSystemResource("three.png").toString();
		fourURL = ClassLoader.getSystemResource("four.png").toString();
		fiveURL = ClassLoader.getSystemResource("five.png").toString();
		sixURL = ClassLoader.getSystemResource("six.png").toString();
		zeroURL = ClassLoader.getSystemResource("zero.png").toString();
		this.listMarkURL = Arrays.asList(zeroURL, oneURL, twoURL, threeURL, fourURL, fiveURL, sixURL);

		squareMark = GameLogic.getInstance().getBoardMark()[getxPosition()][getyPosition()];
		squareMarkIndex = squareMarkRef.indexOf(squareMark);

		secureStateURL = ClassLoader.getSystemResource("securedSquare.png").toString();

		secureSoundMedia = new Media(ClassLoader.getSystemResource("secure.mp3").toString());
		unsecureSoundMedia = new Media(ClassLoader.getSystemResource("unSecure.mp3").toString());
	}

	// Create a square style.
	public void initializeCellColor() {
		setStyle("-fx-border-radius: 15;" + "-fx-background-radius: 15;" + "-fx-background-color: " + "rgb("
				+ getBaseColor().getRed() * 250 + "," + getBaseColor().getGreen() * 250 + ","
				+ getBaseColor().getBlue() * 250 + ");");
	}

	// Draw a picture into a square.
	public void draw(String imageURL, String backgroundColorName) {
		setStyle(
				"-fx-border-radius: 15;" + "-fx-background-radius: 15;" + "-fx-background-color: " + backgroundColorName
						+ ";" + "-fx-background-image: url(\"" + imageURL + "\");" + "-fx-background-size: cover;");

	}

	// Play the sound when the card was clicked.
	protected class PlaySound implements Runnable {
		private Media soundMedia;
		private double volumeLevel;

		public PlaySound(Media soundMedia, double volumeLevel) {
			this.soundMedia = soundMedia;
			this.volumeLevel = volumeLevel;
		}

		public void run() {
			MediaPlayer secureSound = new MediaPlayer(soundMedia);
			secureSound.setVolume(volumeLevel);
			secureSound.play();
		}
	}

	public class DrawThread implements Runnable {
		private String imageURL;
		private String colorName;

		public DrawThread(String imageURL, String colorName) {
			this.imageURL = imageURL;
			this.colorName = colorName;
		}

		public void run() {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					draw(imageURL, colorName);
				}
			});
		}
	}

	public Color getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(Color baseColor) {
		this.baseColor = baseColor;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public void setSquareMark(SquareMark squareMark) {
		this.squareMark = squareMark;
	}

	public int getSquareMarkIndex() {
		return squareMarkIndex;
	}
}
