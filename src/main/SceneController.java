//Use to switch between scene.
package main;

import gui.Game.CardPane;
import gui.Game.GameControllerPane;
import gui.LeaderBoard.LeaderBoardPane;
import gui.MainMenu.ControlPane;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.GameLogic;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class SceneController {
	public static GameControllerPane dashboard;

	public static void switchToMainMenu(ActionEvent event) {
		// Initialize the component
		HBox root = new HBox();
		root.setPadding(new Insets(10));
		root.setAlignment(Pos.BOTTOM_RIGHT);
		root.setPrefHeight(720);
		root.setPrefWidth(1280);

		// setup the pane
		ControlPane selection = new ControlPane();
		root.getChildren().add(selection);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene theScene = new Scene(root);
		String styleLink = ClassLoader.getSystemResource("StyleSheet.css").toString();
		theScene.getStylesheets().add(styleLink);

		// setup background
		String wallPaper = ClassLoader.getSystemResource("mainMenuBackground.png").toString();
		Image img = new Image(wallPaper);
		BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background backgroundPicture = new Background(bImg);
		root.setBackground(backgroundPicture);

		stage.setResizable(false);
		stage.setScene(theScene);
		stage.setTitle("MatchSweeper");
		stage.getIcons().add(new Image(ClassLoader.getSystemResource("GameIcon.png").toString()));
		stage.show();
	}

	public static void switchToGame(ActionEvent event) {
		GameLogic.initializeNewGame();
		HBox root = new HBox();

		CardPane theCardPane = new CardPane();
		root.getChildren().add(theCardPane);

		dashboard = new GameControllerPane();
		root.getChildren().add(dashboard);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene theScene = new Scene(root);
		String styleLink = ClassLoader.getSystemResource("StyleSheet.css").toString();
		theScene.getStylesheets().add(styleLink);

		// reset audio
		Main.audioPlayer.muteBackGroundMusic();
		Main.audioPlayer.muteInGameMusic();

		stage.setResizable(false);
		stage.setScene(theScene);
		stage.setTitle("MatchSweeper");
		stage.getIcons().add(new Image(ClassLoader.getSystemResource("GameIcon.png").toString()));
		stage.show();
	}

	public static void switchToLeaderboard(ActionEvent event) {
		HBox root = new HBox();

		LeaderBoardPane theBoardPane = new LeaderBoardPane();
		root.getChildren().add(theBoardPane);

		// setup background
		String wallPaper = ClassLoader.getSystemResource("LeaderBoardBackground.png").toString();
		Image img = new Image(wallPaper);
		BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background backgroundPicture = new Background(bImg);
		root.setBackground(backgroundPicture);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene theScene = new Scene(root);
		String styleLink = ClassLoader.getSystemResource("StyleSheet.css").toString();
		theScene.getStylesheets().add(styleLink);

		stage.setResizable(false);
		stage.setScene(theScene);
		stage.setTitle("MatchSweeper");
		stage.getIcons().add(new Image(ClassLoader.getSystemResource("GameIcon.png").toString()));
		stage.show();
	}
}