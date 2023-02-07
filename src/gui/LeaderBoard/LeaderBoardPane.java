//This pane shows the leader board section.
package gui.LeaderBoard;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.GameRecord;
import logic.ScoreData;
import main.SceneController;

public class LeaderBoardPane extends VBox {
	private VBox indexBox;
	private VBox timeBox;
	private VBox scoreBox;
	private VBox moveBox;
	private Button menuBox;
	private ArrayList<GameRecord> gameData;
	private Boolean isEmpty;

	public LeaderBoardPane() {
		setPrefHeight(720);
		setPrefWidth(1280);
		setSpacing(30);
		setAlignment(Pos.TOP_CENTER);

		initializeGameData();
		initializeIndexBox();
		initializeTimeBox();
		initializeScoreBox();
		initializeMoveBox();
		initializeMenuBox();

		HBox scoreBoard = new HBox();
		scoreBoard.setAlignment(Pos.CENTER);
		scoreBoard.setSpacing(40);
		scoreBoard.getChildren().add(indexBox);
		scoreBoard.getChildren().add(timeBox);
		scoreBoard.getChildren().add(scoreBox);
		scoreBoard.getChildren().add(moveBox);

		Text disclaimer = new Text("Disclaimer: The leaderboard will reset whenever the game restart.");
		disclaimer.setFont(new Font(15));

		getChildren().add(scoreBoard);
		getChildren().add(disclaimer);
		getChildren().add(menuBox);
	}

	// Load the game data from score data.
	private void initializeGameData() {
		gameData = ScoreData.loadData();
		if (gameData.size() == 0) {
			setIsEmpty(true);
		} else {
			setIsEmpty(false);
		}
		Text header = new Text("Leaderboard");
		header.setFont(new Font(50));
		getChildren().add(header);
	}

	private void initializeIndexBox() {
		indexBox = new VBox();
		indexBox.setAlignment(Pos.CENTER);
		Text header = new Text("Player");
		header.setFont(new Font(35));
		indexBox.getChildren().add(header);
		if (!getIsEmpty()) {
			for (int count = 1; count < gameData.size() + 1; count++) {
				Text content = new Text(Integer.toString(count));
				content.setFont(new Font(30));
				indexBox.getChildren().add(content);
			}
		} else {
			Text content = new Text("N/A");
			content.setFont(new Font(30));
			indexBox.getChildren().add(content);
		}
	}

	private void initializeTimeBox() {
		timeBox = new VBox();
		timeBox.setAlignment(Pos.CENTER);
		Text header = new Text("Time");
		header.setFont(new Font(35));
		timeBox.getChildren().add(header);
		if (!getIsEmpty()) {
			for (GameRecord theRecord : gameData) {
				Text content = new Text(theRecord.getTimeTaken());
				content.setFont(new Font(30));
				timeBox.getChildren().add(content);
			}
		} else {
			Text content = new Text("N/A");
			content.setFont(new Font(30));
			timeBox.getChildren().add(content);
		}
	}

	private void initializeScoreBox() {
		scoreBox = new VBox();
		scoreBox.setAlignment(Pos.CENTER);
		Text header = new Text("Score");
		header.setFont(new Font(35));
		scoreBox.getChildren().add(header);
		if (!getIsEmpty()) {
			for (GameRecord theRecord : gameData) {
				Text content = new Text(theRecord.getTotalScore());
				content.setFont(new Font(30));
				scoreBox.getChildren().add(content);
			}
		} else {
			Text content = new Text("N/A");
			content.setFont(new Font(30));
			scoreBox.getChildren().add(content);
		}
	}

	private void initializeMoveBox() {
		moveBox = new VBox();
		moveBox.setAlignment(Pos.CENTER);
		Text header = new Text("Move");
		header.setFont(new Font(35));
		moveBox.getChildren().add(header);
		if (!getIsEmpty()) {
			for (GameRecord theRecord : gameData) {
				Text content = new Text(theRecord.getTotalMove());
				content.setFont(new Font(30));
				moveBox.getChildren().add(content);
			}
		} else {
			Text content = new Text("N/A");
			content.setFont(new Font(30));
			moveBox.getChildren().add(content);
		}
	}

	public Boolean getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(Boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	private void initializeMenuBox() {
		menuBox = new Button("Main Menu");
		menuBox.setId("MainButtonStyle");
		menuBox.setPrefSize(200, 45);
		menuBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SceneController.switchToMainMenu(arg0);
			}
		});
	}
}
