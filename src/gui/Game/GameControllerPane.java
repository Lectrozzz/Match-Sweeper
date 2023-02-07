//The pane that control the whole game.
package gui.Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.GameLogic;
import logic.GameRecord;
import logic.ScoreData;
import main.Main;
import main.SceneController;

public class GameControllerPane extends VBox {
	private Text timer;
	private Thread timerThread;

	private int currentSecond;
	private int currentMinute;
	private String secondText;
	private String minuteText;

	private Button newGameButton;
	private Button pauseButton;
	private Button resetButton;
	private Button muteButton;
	private Button menuButton;
	private Boolean isMute;
	private Boolean isPause;

	private static StatusPane status;

	public GameControllerPane() {
		setPrefWidth(380);
		setAlignment(Pos.CENTER);
		setSpacing(20);

		initializeTimer();
		initializeNewGameButton();
		initializePauseButton();
		initializeResetButton();
		initializeMuteButton();
		initializeMainmenuButton();
		status = new StatusPane();

		getChildren().add(timer);
		getChildren().add(status);
		getChildren().add(newGameButton);
		getChildren().add(pauseButton);
		getChildren().add(resetButton);
		getChildren().add(muteButton);
		getChildren().add(menuButton);
	}

	private void initializeTimer() {
		timer = new Text("00:00");
		timer.setFont(new Font(50));
		timer.setStyle("-fx-font-family:Arial Rounded MT Bold;");
	}

	// Run timer when the game has started
	private void runTimer() {
		setCurrentSecond(0);
		setCurrentMinute(0);
		this.timerThread = new Thread(() -> {
			while (true) {
				if (!getIsPause()) {
					currentSecond++;
				}
				synchronized (this) {
					try {
						Thread.sleep(1000);
						// Calculate time
						setSecondText(calculateSecond());
						setMinuteText(calculateMinute());
						updateTime();
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.out.println("Stop Timer Thread");
					}
				}
			}
		});
		this.timerThread.setDaemon(true);
		this.timerThread.start();
	}

	// Update timer text
	private void updateTime() {
		timer.setText(getMinuteText() + ":" + getSecondText());
	}

	// Calculate time in second
	public String calculateSecond() {
		if (currentSecond < 10) {
			return "0" + Integer.toString(getCurrentSecond());
		} else if (currentSecond == 60) {
			setCurrentSecond(0);
			currentMinute++;
			return "00";
		} else {
			return Integer.toString(getCurrentSecond());
		}
	}

	// Calculate time in minute
	public String calculateMinute() {
		if (currentMinute < 10) {
			return "0" + Integer.toString(getCurrentMinute());
		} else {
			return Integer.toString(getCurrentMinute());
		}
	}

	public int getCurrentSecond() {
		return currentSecond;
	}

	public void setCurrentSecond(int currentSecond) {
		this.currentSecond = currentSecond;
	}

	public int getCurrentMinute() {
		return currentMinute;
	}

	public void setCurrentMinute(int currentMinute) {
		this.currentMinute = currentMinute;
	}

	public String getSecondText() {
		return secondText;
	}

	public void setSecondText(String secondText) {
		this.secondText = secondText;
	}

	public String getMinuteText() {
		return minuteText;
	}

	public void setMinuteText(String minuteText) {
		this.minuteText = minuteText;
	}

	private void initializeNewGameButton() {
		newGameButton = new Button("New Game");
		newGameButton.setId("MainButtonStyle");
		newGameButton.setPrefSize(200, 50);
		newGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				newGameHandler();
			}
		});
	}

	// Start the game and run the timer.
	public void newGameHandler() {
		newGameButton.setDisable(true);
		pauseButton.setDisable(false);
		muteButton.setDisable(false);

		Main.audioPlayer.playInGameMusic();
		GameLogic.getInstance().setGamePause(false);

		setIsPause(false);
		setIsMute(false);
		runTimer();

	}

	private void initializePauseButton() {
		pauseButton = new Button("Pause");
		pauseButton.setId("MainButtonStyle");
		pauseButton.setPrefSize(200, 50);
		pauseButton.setDisable(true);
		setIsPause(false);
		pauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (getIsPause()) {
					continueGameHandler();
				} else {
					pauseGameHandler();
				}
			}
		});
	}

	// Pause the timer if the timer was running.
	public void pauseGameHandler() {
		pauseButton.setText("Continue");
		resetButton.setDisable(false);
		setIsPause(true);
		GameLogic.getInstance().setGamePause(true);
	}

	// Continue the timer if the timer was paused.
	public void continueGameHandler() {
		pauseButton.setText("Pause");
		resetButton.setDisable(true);
		setIsPause(false);
		GameLogic.getInstance().setGamePause(false);
	}

	// Sent an alert and stop the timer when player has completed the game. Also
	// collect the data and save it to the leader board.
	public void endGameHandler() {
		setIsPause(true);
		resetButton.setDisable(false);
		pauseButton.setDisable(true);
		Main.audioPlayer.playLevelComplete();

		currentSecond++;
		setSecondText(calculateSecond());
		setMinuteText(calculateMinute());
		String secondText = getSecondText();
		String minuteText = getMinuteText();
		String timeText = minuteText + ":" + secondText;
		Integer gameScore = GameLogic.getInstance().getScore();
		Integer gameMoveCount = GameLogic.getInstance().getMoveCount();

		ScoreData.saveData(new GameRecord(timeText, gameScore, gameMoveCount));

		Alert alertBox = new Alert(AlertType.INFORMATION);
		alertBox.setTitle("Congratulations!");
		alertBox.setHeaderText("Congratulations you beat the MatchSweeper!");
		alertBox.setContentText("Total Score: " + gameScore + "     " + "Total move: " + gameMoveCount + "\n"
				+ "Time Taken " + timeText);
		alertBox.showAndWait();
	}

	private void initializeResetButton() {
		resetButton = new Button("Reset");
		resetButton.setId("MainButtonStyle");
		resetButton.setDisable(true);
		resetButton.setPrefSize(200, 50);
		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				resetHandler(arg0);
			}
		});
	}

	// Reset the whole game and timer.
	public void resetHandler(ActionEvent arg0) {
		pauseButton.setText("Pause");
		pauseButton.setDisable(true);
		resetButton.setDisable(true);
		muteButton.setDisable(true);
		newGameButton.setDisable(false);
		setIsPause(true);
		setCurrentSecond(0);
		setCurrentMinute(0);
		timer.setText("00:00");

		SceneController.switchToGame(arg0);
	}

	private void initializeMuteButton() {
		muteButton = new Button("Mute");
		muteButton.setId("MainButtonStyle");
		muteButton.setDisable(true);
		muteButton.setPrefSize(200, 50);
		muteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				muteHandler();
			}
		});

	}

	// Mute the game audio.
	private void muteHandler() {
		if (!getIsMute()) {
			muteButton.setText("Unmute");
			setIsMute(true);
			Main.audioPlayer.muteInGameMusic();
		} else {
			muteButton.setText("Mute");
			setIsMute(false);
			Main.audioPlayer.playInGameMusic();
		}
	}

	public Boolean getIsMute() {
		return isMute;
	}

	public void setIsMute(Boolean isMute) {
		this.isMute = isMute;
	}

	private void initializeMainmenuButton() {
		menuButton = new Button("Main Menu");
		menuButton.setId("MainButtonStyle");
		menuButton.setPrefSize(200, 50);
		menuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// reset audio
				Main.audioPlayer.muteInGameMusic();
				Main.audioPlayer.muteBackGroundMusic();
				Main.audioPlayer.playBackGroundMusic();
				SceneController.switchToMainMenu(arg0);
			}
		});
	}

	public Boolean getIsPause() {
		return isPause;
	}

	public void setIsPause(Boolean isPause) {
		this.isPause = isPause;
	}

	public StatusPane getStatus() {
		return status;
	}
}