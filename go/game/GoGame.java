package go.game;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class representing a multiplayer game of Go. Builds <code>Stage</code> and
 * starts game cycle.
 * 
 * Players take turns placing pieces of their color down on the board. If a piece or group
 * of same color pieces have no liberties or connections to a vacant, non-edge space after 
 * a particular move the group or individual piece is removed from the board. The winner is
 * decided by who has the most stones when the players decide to finish the game.
 * 
 * Suicide moves or moves that allow a player to jeopardize their own pieces are not allowed.
 * 
 * @author Benjamin C. McKinley
 */
public class GoApp extends Application {

	Random rng = new Random();
	public String version = "1.0.0";
	private int[] sizeArray = {5, 12, 19};
	private int size = 1; //between 0-2
	private Stage stage;
	private Game game;
	private Scene gameScene;
	
	/**
	 * Constructor for a game of Go. Builds <code>Board</code>
	 * and <code>Stage</code> and instantiates a new game cycle.
	 */
	public void start(Stage stage) {
		
		this.stage = stage;
		
		startGame();

		//stage building
		stage.setTitle("Go " + version);
		stage.setScene(gameScene);
		gameScene.setFill(Color.BLACK);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.sizeToScene();
		stage.getIcons().add(new Image("https://i.imgur.com/RdAEXIc.png"));
		stage.show();
		stage.setOnCloseRequest(event -> {
			game.stop();
		});
	} //start
	
	/**
	 * Records high score for the current game in a text file for
	 * later retrieval and launches new game.
	 * 
	 */
	public void recordHighscore() {
		
		int wScore = game.getBoard().getWhiteScore();
		int bScore = game.getBoard().getBlackScore();
		int hScore = (wScore > bScore) ? wScore : bScore;
		
		WritableImage screenshot = stage.getScene().snapshot(null);
		ImageView scImv = new ImageView(screenshot);
		scImv.setEffect(new GaussianBlur(15));
		
		VBox vbox = new VBox(15);
		vbox.setAlignment(Pos.CENTER);
		Text initialRequest = new Text("Enter Initials");
		initialRequest.setStyle("-fx-font: 24 arial;");
		initialRequest.setStroke(Paint.valueOf("WHITE"));
		initialRequest.setFill(Paint.valueOf("WHITE"));
		
		TextField textField = new TextField();
		textField.setMaxWidth(50);
		
		Button submit = new Button("SUBMIT");
		submit.setOnAction(event -> {
			
			String filepath = "res/highscore_go.txt";
			try { 
				File file = new File(filepath);
				FileWriter fileWriter = new FileWriter(file, true);
				fileWriter.write(textField.getText() + " " + hScore + "\n");
				fileWriter.close();
			} catch (Exception e) {
				System.out.println("Error recording score: " + e);
				System.exit(1);
			} //try
			
			startGame();
			
		});
		
		vbox.getChildren().addAll(initialRequest, textField, submit);
		
		StackPane sp = new StackPane();
		sp.getChildren().addAll(scImv, vbox);	
		Scene scoreScene = new Scene(sp);
		stage.setScene(scoreScene);
	} //recordHighscore
	
	/**
	 * Builds new scene and starts a new game of Go.
	 */
	public void startGame() {
		game = new Game(sizeArray[size]);
		
		HBox title = new HBox();
		title.setAlignment(Pos.BASELINE_CENTER);
		title.setMinHeight(25);

		title.getChildren().add(new ImageView(new Image("https://i.imgur.com/FSAcfia.png")));

		StackPane playBoard = new StackPane();
		Rectangle background = new Rectangle(24 * sizeArray[size] + 8, 24 * sizeArray[size] + 8);
		background.setFill(Paint.valueOf("572A00"));
		playBoard.getChildren().addAll(background, game.getBoard());

		VBox screen = new VBox();
		screen.setMinWidth(460);

		HBox info = new HBox(25);
		info.setAlignment(Pos.CENTER);
		
		Button endGame = new Button("Finish Game");
		endGame.setOnAction(event -> {	
			game.getBoard().setDisable(true);
			game.stop();	
			recordHighscore();
		});
		
		Button changeSize = new Button(sizeArray[size] + "x" + sizeArray[size]);
		changeSize.setOnAction(event -> {
			
			if (changeSize.getText().toString().charAt(1) == '2') {
				size = 2;
				changeSize.setText("19x19");
			} else if (changeSize.getText().toString().charAt(1) == '9') {
				size = 0;
				changeSize.setText("5x5");
			} else {
				size = 1;
				changeSize.setText("12x12");
			}
			
		});

		info.getChildren().addAll(game.getScoreBoard(), endGame, changeSize);
		
		screen.getChildren().addAll(title, playBoard, info);

		gameScene = new Scene(screen);
		
		stage.setScene(gameScene);
		stage.show();
	} //startGame
	
	/**
	 * Launches application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch();
	} //main

} //GoApp
