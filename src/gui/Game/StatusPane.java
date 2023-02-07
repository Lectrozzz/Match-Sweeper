//The pane that show current status of the game. (score, current card count, current bomb count and current move count)
package gui.Game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.GameLogic;

public class StatusPane extends VBox {
	private Text currentScore;
	private Text currentCardLeft;
	private Text currentBombLeft;
	private Text currentMoveCount;
	private int score;
	private int cardLeft;
	private int bombLeft;
	private int moveCount;

	public StatusPane() {
		GameLogic instance = GameLogic.getInstance();
		score = instance.getScore();
		cardLeft = instance.getCardLeft();
		bombLeft = instance.getBombLeft();
		moveCount = instance.getMoveCount();

		GridPane ScorePane = new GridPane();

		Text scoreHeader = new Text("Score : ");
		scoreHeader.setFont(new Font(35));

		currentScore = new Text(String.valueOf(score));
		currentScore.setFont(new Font(35));

		ScorePane.add(scoreHeader, 1, 0);
		ScorePane.add(currentScore, 2, 0);
		ScorePane.setHgap(5);
		ScorePane.setAlignment(Pos.CENTER);
		ScorePane.setPrefHeight(50);
		ScorePane.setStyle("-fx-background-color: lightgray");// optional

		GridPane MoveCountPane = new GridPane();

		Text moveCountHeader = new Text("Move : ");
		moveCountHeader.setFont(new Font(35));

		currentMoveCount = new Text(String.valueOf(moveCount));
		currentMoveCount.setFont(new Font(35));

		MoveCountPane.add(moveCountHeader, 1, 0);
		MoveCountPane.add(currentMoveCount, 2, 0);
		MoveCountPane.setHgap(5);
		MoveCountPane.setAlignment(Pos.CENTER);
		MoveCountPane.setPrefHeight(50);
		MoveCountPane.setStyle("-fx-background-color: lightgray");

		GridPane SquareStatusPane = new GridPane();
		///////////////////////////////////////////
		HBox BombLeftPane = new HBox();

		Text BombLeftHeader = new Text("Bomb Left : ");
		BombLeftHeader.setFont(new Font(25));

		currentBombLeft = new Text(String.valueOf(bombLeft));
		currentBombLeft.setFont(new Font(25));

		BombLeftPane.getChildren().add(BombLeftHeader);
		BombLeftPane.getChildren().add(currentBombLeft);
		BombLeftPane.setSpacing(5);
		BombLeftPane.setPadding(new Insets(5, 10, 5, 10));
		BombLeftPane.setStyle("-fx-background-color: lightgray");// optional
		///////////////////////////////////////////
		HBox CardLeftPane = new HBox();

		Text CardLeftHeader = new Text("Card Left : ");
		CardLeftHeader.setFont(new Font(25));

		currentCardLeft = new Text(String.valueOf(cardLeft));
		currentCardLeft.setFont(new Font(25));

		CardLeftPane.getChildren().add(CardLeftHeader);
		CardLeftPane.getChildren().add(currentCardLeft);
		CardLeftPane.setSpacing(5);
		CardLeftPane.setPadding(new Insets(5, 10, 5, 10));
		CardLeftPane.setStyle("-fx-background-color: lightgray");
		///////////////////////////////////////////
		SquareStatusPane.add(BombLeftPane, 0, 0);
		SquareStatusPane.add(CardLeftPane, 1, 0);
		SquareStatusPane.setHgap(30);
		SquareStatusPane.setAlignment(Pos.CENTER);
		SquareStatusPane.setPrefHeight(50);
		/////////////////////////////////////////////////////////////////////////

		setAlignment(Pos.CENTER);
		setPrefWidth(380);
		setSpacing(20);

		this.getChildren().add(ScorePane);
		this.getChildren().add(MoveCountPane);
		this.getChildren().add(SquareStatusPane);
	}

	// Update game status.
	public void updateStatus() {
		GameLogic instance = GameLogic.getInstance();
		int newScore = instance.getScore();
		int newCardLeft = instance.getCardLeft();
		int newBombLeft = instance.getBombLeft();
		moveCount = instance.getMoveCount();

		if (score > newScore) {
			score = newScore;
			ChangeColorText updateScoreThread = new ChangeColorText(currentScore, score, Color.RED);
			Thread updateThread = new Thread(updateScoreThread);
			updateThread.setDaemon(true);
			updateThread.start();
		} else if (score < newScore) {
			score = newScore;
			ChangeColorText updateScoreThread = new ChangeColorText(currentScore, score, Color.GREEN);
			Thread updateThread = new Thread(updateScoreThread);
			updateThread.setDaemon(true);
			updateThread.start();
		}

		if (bombLeft != newBombLeft) {
			bombLeft = newBombLeft;
			ChangeColorText updateBombLeftThread = new ChangeColorText(currentBombLeft, bombLeft, Color.RED);
			Thread updateThread = new Thread(updateBombLeftThread);
			updateThread.setDaemon(true);
			updateThread.start();
		}

		if (cardLeft != newCardLeft) {
			cardLeft = newCardLeft;
			ChangeColorText updateCardLeftThread = new ChangeColorText(currentCardLeft, cardLeft, Color.GREEN);
			Thread updateThread = new Thread(updateCardLeftThread);
			updateThread.setDaemon(true);
			updateThread.start();
		}
		currentMoveCount.setText(String.valueOf(moveCount));
	}

	public StatusPane getStatusPaneInstance() {
		return new StatusPane();
	}

	// Change text color when the game status updated.
	public class ChangeColorText implements Runnable {
		private Text currentText;
		private int newValue;
		private Color changeColor;

		public ChangeColorText(Text currentText, int newValue, Color changeColor) {
			this.currentText = currentText;
			this.newValue = newValue;
			this.changeColor = changeColor;
		}

		public void run() {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					currentText.setText(String.valueOf(newValue));
					currentText.setFill(changeColor);
				}
			});
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					currentText.setFill(Color.BLACK);
					;
				}
			});
		}
	}
}