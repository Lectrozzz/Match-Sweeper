//Main game logic
package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gui.Game.CardSquare;
import main.SceneController;

public class GameLogic {

	private static GameLogic instance = null;
	private boolean isGameEnd;
	private boolean isGamePause;
	private boolean waitForFlipDown = false;
	private int bombLeft = 6;
	private int cardLeft = 30;
	private int score = 0;
	private int moveCount;
	private SquareMark[][] boardMark = new SquareMark[6][6];
	private SquareType[][] boardType = new SquareType[6][6];
	private SquareState[][] boardState = new SquareState[6][6];
	private List<SquareType> boardTypeRef = new ArrayList<>();
	private final List<SquareMark> boardMarkRef = Arrays.asList(SquareMark.ZERO, SquareMark.ONE, SquareMark.TWO,
			SquareMark.THREE, SquareMark.FOUR, SquareMark.FIVE, SquareMark.SIX);

	private CardSquare flipUpCard; // Match system
	private boolean readyToMatch = false;

	public GameLogic() {
		this.newGame();

	}

	// set up cards and mines location
	public void newGame() {
		this.setGameEnd(false);
		this.setWaitForFlipDown(false);

		boardTypeRef = Arrays.asList(SquareType.CARD1, SquareType.CARD2, SquareType.CARD3, SquareType.CARD4,
				SquareType.CARD5, SquareType.CARD6, SquareType.CARD7, SquareType.CARD8, SquareType.CARD9,
				SquareType.CARD10, SquareType.CARD11, SquareType.CARD12, SquareType.CARD13, SquareType.CARD14,
				SquareType.CARD15, SquareType.MINE, SquareType.MINE, SquareType.MINE, SquareType.CARD1,
				SquareType.CARD2, SquareType.CARD3, SquareType.CARD4, SquareType.CARD5, SquareType.CARD6,
				SquareType.CARD7, SquareType.CARD8, SquareType.CARD9, SquareType.CARD10, SquareType.CARD11,
				SquareType.CARD12, SquareType.CARD13, SquareType.CARD14, SquareType.CARD15, SquareType.MINE,
				SquareType.MINE, SquareType.MINE);

		Collections.shuffle(boardTypeRef);

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				// initialize Type of Square
				boardType[i][j] = boardTypeRef.get(6 * i + j);
				if (boardTypeRef.get(6 * i + j) != SquareType.MINE) {
					// initialize State of Card
					boardState[i][j] = SquareState.FLIPDOWN;
				} else {
					// initialize State of Mine
					boardState[i][j] = SquareState.CONCEALED;
				}
			}
		}
		initializeBoardMark();
	}

	public void pauseGame() {
		setGamePause(!isGamePause);
	}

	public boolean isGameEnd() {
		return isGameEnd;
	}

	public void setGameEnd(boolean gameEnd) {
		isGameEnd = gameEnd;
	}

	public boolean isGamePause() {
		return isGamePause;
	}

	public void setGamePause(boolean gamePause) {
		isGameEnd = gamePause;
	}

	public boolean isWaitForFlipDown() {
		return waitForFlipDown;
	}

	public void setWaitForFlipDown(boolean waitForFlipDown) {
		this.waitForFlipDown = waitForFlipDown;
	}

	public static GameLogic getInstance() {
		if (instance == null) {
			instance = new GameLogic();
		}
		return instance;
	}

	public SquareMark[][] getBoardMark() {
		return boardMark;
	}

	public SquareState[][] getBoardState() {
		return boardState;
	}

	public SquareType[][] getBoardType() {
		return boardType;
	}

	public int getBombLeft() {
		return bombLeft;
	}

	public void setBombLeft(int bombLeft) {
		if (bombLeft < 0)
			this.bombLeft = 0;
		else
			this.bombLeft = bombLeft;
	}

	public void updateBombLeft(int updatedBomb) {
		setBombLeft(getBombLeft() + updatedBomb);
	}

	public int getCardLeft() {
		return cardLeft;
	}

	public void setCardLeft(int cardLeft) {
		if (cardLeft < 0)
			this.cardLeft = 0;
		else
			this.cardLeft = cardLeft;
	}

	public void updateCardLeft(int updatedCard) {
		setCardLeft(getCardLeft() + updatedCard);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addScore(int addedScore) {
		this.score = this.score + addedScore;
	}

	public CardSquare getFlipUpCard() {
		return flipUpCard;
	}

	public void setFlipUpCard(CardSquare flipUpCard) {
		this.flipUpCard = flipUpCard;
	}

	public boolean isReadyToMatch() {
		return readyToMatch;
	}

	public void setReadyToMatch(boolean prepareToMatch) {
		this.readyToMatch = prepareToMatch;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	public void addMove() {
		this.moveCount++;
	}

	public void initializeBoardMark() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				int mark = 0;

				if (i == 0 && j == 0) { // TopLeft
					for (int k = i; k < i + 2; k++) {
						for (int l = j; l < j + 2; l++) {
							if ((k != i || l != j) && boardType[k][l] == SquareType.MINE) {
								mark++;
							}
						}
					}
				} else if (i == 0 && j == 5) { // TopRight
					for (int k = i; k < i + 2; k++) {
						for (int l = j; l > j - 2; l--) {
							if ((k != i || l != j) && boardType[k][l] == SquareType.MINE) {
								mark++;
							}
						}
					}
				} else if (i == 5 && j == 0) { // BottomLeft
					for (int k = i; k > i - 2; k--) {
						for (int l = j; l < j + 2; l++) {
							if ((k != i || l != j) && boardType[k][l] == SquareType.MINE) {
								mark++;
							}
						}
					}
				} else if (i == 5 && j == 5) { // BottomRight
					for (int k = i; k > i - 2; k--) {
						for (int l = j; l > j - 2; l--) {
							if ((k != i || l != j) && boardType[k][l] == SquareType.MINE) {
								mark++;
							}
						}
					}
				} else if (i == 0 && j != 0 && j != 5) { // TopEdge
					for (int k = i; k < i + 2; k++) {
						for (int l = j - 1; l < j + 2; l++) {
							if ((k != i || l != j) && boardType[k][l] == SquareType.MINE) {
								mark++;
							}
						}
					}
				} else if (j == 0 && i != 0 && i != 5) { // LeftEdge
					for (int k = i - 1; k < i + 2; k++) {
						for (int l = j; l < j + 2; l++) {
							if ((k != i || l != j) && boardType[k][l] == SquareType.MINE) {
								mark++;
							}
						}
					}
				} else if (j == 5 && i != 0 && i != 5) { // RightEdge
					for (int k = i - 1; k < i + 2; k++) {
						for (int l = j; l > j - 2; l--) {
							if ((k != i || l != j) && boardType[k][l] == SquareType.MINE) {
								mark++;
							}
						}
					}
				} else if (i == 5 && j != 0 && j != 5) { // BottomEdge
					for (int k = i; k > i - 2; k--) {
						for (int l = j - 1; l < j + 2; l++) {
							if ((k != i || l != j) && boardType[k][l] == SquareType.MINE) {
								mark++;
							}
						}
					}
				} else { // Middle
					for (int k = i - 1; k < i + 2; k++) {
						for (int l = j - 1; l < j + 2; l++) {
							if ((k != i || l != j) && boardType[k][l] == SquareType.MINE) {
								mark++;
							}
						}
					}
				}
				boardMark[i][j] = boardMarkRef.get(mark);
			}
		}
	}

	// Update current state of the board.
	public void updateState(int x, int y, SquareState state) {

		boardState[x][y] = state;
		SceneController.dashboard.getStatus().updateStatus();

		checkGameEnd();
		if (isGameEnd) {
			// move to game end section
			SceneController.dashboard.endGameHandler();
		}
	}

	// Check that the game is finished or not.
	public void checkGameEnd() {
		int ClearCard = 0;

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (boardState[i][j] == SquareState.MATCHED) {
					ClearCard++;
				} else if (boardState[i][j] == SquareState.SECURED || boardState[i][j] == SquareState.REVEALED) {
					ClearCard++;
				}
			}
		}
		if (ClearCard == 36) {
			this.setGameEnd(true);
		}
	}

	public static void initializeNewGame() {
		getInstance().newGame();
		getInstance().setFlipUpCard(null);
		getInstance().setReadyToMatch(false);
		getInstance().setBombLeft(6);
		getInstance().setCardLeft(30);
		getInstance().setScore(0);
		getInstance().setMoveCount(0);
		getInstance().setGamePause(true);
	}
}