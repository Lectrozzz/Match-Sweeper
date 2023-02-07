//Main menu of the game. Which appear when you start the game.
package main;

import gui.MainMenu.ControlPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
	public static AudioController audioPlayer;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialize the component
		HBox root = new HBox();
		root.setPadding(new Insets(10));
		root.setAlignment(Pos.BOTTOM_RIGHT);
		root.setPrefHeight(720);
		root.setPrefWidth(1280);

		// setup the pane
		ControlPane selectionBox = new ControlPane();
		root.getChildren().add(selectionBox);

		// setup the scene
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

		// setup main audio player
		audioPlayer = new AudioController();
		audioPlayer.playBackGroundMusic();

		// setup the stage
		primaryStage.setScene(theScene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("MatchSweeper");
		primaryStage.getIcons().add(new Image(ClassLoader.getSystemResource("GameIcon.png").toString()));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}