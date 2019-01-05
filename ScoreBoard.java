package go.game;


import go.game.Space.Resident;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Class containing a visual representation of the
 * scores. Additionally indicates the current turn.
 * 
 * @author Benjamin C. McKinley
 */
public class ScoreBoard extends VBox {

	private Text bScore, wScore;
	private Rectangle bTurn, wTurn;

	/**
	 * Constructor for <code>ScoreBoard</code>.
	 */
	public ScoreBoard() {

		HBox black = new HBox(10);
		HBox white = new HBox(10);

		bTurn = new Rectangle(4, 4, Color.TRANSPARENT);
		bTurn.setStroke(Color.WHITE);
		bTurn.setStrokeWidth(1);

		wTurn = new Rectangle(4, 4, Color.WHITE);
		wTurn.setStroke(Color.WHITE);
		wTurn.setStrokeWidth(1);

		bScore = new Text("0 Komi");
		wScore = new Text("0 Komi");

		StackPane bToken = new StackPane();
		StackPane wToken = new StackPane();
		bToken.setAlignment(Pos.CENTER);
		wToken.setAlignment(Pos.CENTER);

		bToken.getChildren().addAll(new Circle(8, Color.BLACK), bTurn);
		wToken.getChildren().addAll(new Circle(8, Color.WHITE), wTurn);

		black.getChildren().addAll(bToken, bScore);

		white.getChildren().addAll(wToken, wScore);

		getChildren().addAll(black, white);
		this.setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
		setMinWidth(75);
		setAlignment(Pos.CENTER);
	}

	/**
	 * Updates the scores on the <code>ScoreBoard</code> and updates
	 * the color of the current move.
	 * 
	 * @param bNum number of black pieces
	 * @param wNum number of white pieces
	 * @param current the current color in play
	 */
	public void updateScoreBoard(int bNum, int wNum, Resident current) {
		bScore.setText(bNum + " Komi");
		wScore.setText(wNum + " Komi");

		if (current == Resident.WHITE) {
			bTurn.setStroke(Color.BLACK);
			wTurn.setStroke(Color.BLACK);
		}
		else {
			bTurn.setStroke(Color.WHITE);
			wTurn.setStroke(Color.WHITE);
		}
	}
}