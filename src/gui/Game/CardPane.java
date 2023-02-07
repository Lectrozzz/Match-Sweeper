//Initialize every card into the board.
package gui.Game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import logic.GameLogic;
import logic.SquareType;

public class CardPane extends GridPane {

	public CardPane() {
		// Setup the pane.
		setHgap(8);
		setVgap(8);
		setPadding(new Insets(8));
		setPrefWidth(900);
		setAlignment(Pos.CENTER);
		setBorder(new Border(
				new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		// Load the card into the pane.
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (GameLogic.getInstance().getBoardType()[i][j] != SquareType.MINE) {
					CardSquare theCard = new CardSquare(i, j);
					this.add(theCard, i, j);
				} else {
					BombSquare theCard = new BombSquare(i, j);
					this.add(theCard, i, j);
				}
			}
		}
	}
}
