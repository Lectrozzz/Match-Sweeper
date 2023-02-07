//This pane show the whole main menu user interface.
package gui.MainMenu;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.Main;
import main.SceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControlPane extends VBox {
	private Button playButton;
	private Button leaderboardButton;
	private Button tutorialButton;
	private Button muteButton;
	private Button exitButton;

	private Boolean isMute;

	public ControlPane() {
		// Pane setup
		setAlignment(Pos.CENTER);
		setPrefSize(460, 200);
		setSpacing(25);

		// Initialize the component
		initializePlayButton();
		initinitializeLeaderboardButton();
		initinitializeTutorialButton();
		initinitializeMuteButton();
		initinitializeExitButton();

		// Create illusion spacing
		Text test = new Text(" ");
		test.setFont(new Font(125));

		// Add component to the pane
		getChildren().add(test);
		getChildren().add(this.playButton);
		getChildren().add(this.leaderboardButton);
		getChildren().add(this.tutorialButton);
		getChildren().add(this.muteButton);
		getChildren().add(this.exitButton);
	}

	private void initializePlayButton() {
		playButton = new Button("Play");
		playButton.setFont(new Font(30));
		playButton.setPrefSize(250, 50);
		playButton.setId("TransparentButton");
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SceneController.switchToGame(arg0);
			}
		});
	}

	private void initinitializeLeaderboardButton() {
		leaderboardButton = new Button("LeaderBoard");
		leaderboardButton.setPrefSize(250, 50);
		leaderboardButton.setId("TransparentButton");
		leaderboardButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SceneController.switchToLeaderboard(arg0);
			}
		});
	}

	private void initinitializeTutorialButton() {
		tutorialButton = new Button("Tutorial");
		tutorialButton.setPrefSize(250, 50);
		tutorialButton.setId("TransparentButton");
		tutorialButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tutorialAlert();
			}
		});
	}

	// Show the alert box that include basic tutorial of the game.
	public void tutorialAlert() {
		Alert alertBox = new Alert(AlertType.INFORMATION);
		alertBox.setTitle("Tutorial");
		alertBox.setHeaderText("How to play the game:");
		alertBox.setContentText("Click the New Game button to start the game.\n" + "The timer will start to run.\n"
				+ "Then click on the square so the card will flip to show the picture.\n"
				+ "If the next card was clicked and has the same picture\n"
				+ "you will get the the score, or else it will be flipped over again.\n"
				+ "When both cards were flipped it will show the number which \n"
				+ "tells you the amount of bombs the were placed next to the card.\n"
				+ "(Including both straight and diagonal side)\n"
				+ "If you click on the bomb card you will lose the score.\n\n"
				+ "You can also use right click button on the card to enable\n"
				+ "securemode which makes the card unclickable until you disable it.\n\n"
				+ "You can also pause the timer while playing if you want to break.\n"
				+ "But keep in mind that using pause will make the card unclickable.\n\n"
				+ "Try to finish the game by matching all the picture and avoid the bomb."
				+ "Or challenge yourself to get the highest score, lowest time and\n" + "lowest move count possible\n\n"
				+ "Also keep in mind that every time that the game start\n"
				+ "all of the cards placement will be randomized.\n"
				+ "So don't try to remember the board and restart over.\n\n"
				+ "For more information please visit our report which include\n"
				+ "both detailed tutorial and the whole information of our game.");
		alertBox.showAndWait();
	}

	private void initinitializeMuteButton() {
		muteButton = new Button("Mute");
		muteButton.setPrefSize(250, 50);
		muteButton.setId("TransparentButton");
		setIsMute(false);
		muteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				muteHandler();
			}
		});
	}

	// Mute or Unmute the main menu music.
	private void muteHandler() {
		if (!getIsMute()) {
			Main.audioPlayer.muteBackGroundMusic();
			muteButton.setText("Unmute");
			setIsMute(true);
		} else {
			Main.audioPlayer.playBackGroundMusic();
			muteButton.setText("Mute");
			setIsMute(false);
		}
	}

	public Boolean getIsMute() {
		return isMute;
	}

	public void setIsMute(Boolean isMute) {
		this.isMute = isMute;
	}

	private void initinitializeExitButton() {
		exitButton = new Button("Exit");
		exitButton.setPrefSize(250, 50);
		exitButton.setId("TransparentButton");
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// Exit the game
				Main.audioPlayer.muteBackGroundMusic();
				Platform.exit();
			}
		});
	}
}