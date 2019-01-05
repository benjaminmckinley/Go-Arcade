package go.game;

import go.game.Space.Resident;

/**
 * Class responsible for running general cycle of a game and starting new games.
 * 
 * @author Benjamin C. McKinley
 */
public class Game {

	private boolean running = true;
	private Player player, player2;
	private Board gameBoard;
	private ScoreBoard scoreBoard;
	private Thread gameCycle;
	
	/**
	 * Constructor for a new <code>Game</code> that has a <code>Board</code> of 
	 * a parameter size.
	 * 
	 * @param size used to create <code>Board</code> of size x size
	 */
	public Game(int size) {		
		
		gameBoard = new Board(size);
		scoreBoard = new ScoreBoard();
		
		player = new Player(gameBoard, Resident.BLACK);
		player2 = new Player(gameBoard, Resident.WHITE);

		gameCycle = new Thread(() -> {

			run();

		});

		gameCycle.setDaemon(false);
		gameCycle.start();
	} //Game

	/**
	 * Loop that controls structure of the <code>Game</code>.
	 */
	public void run() {

		while(running) {
			
			player.move();
			scoreBoard.updateScoreBoard(gameBoard.getBlackScore(), gameBoard.getWhiteScore(), Resident.WHITE);
			
			player2.move();
			scoreBoard.updateScoreBoard(gameBoard.getBlackScore(), gameBoard.getWhiteScore(), Resident.BLACK);
		}
	} //run

	/**
	 * Shuts down game loop.
	 */
	public void stop() {
		running = false;
		gameCycle.stop();
	} //stop
	
	/**
	 * Getter method for <code>gameBoard</code>.
	 * 
	 * @return the current </code>Board</code>
	 */
	public Board getBoard() {
		return gameBoard;	
	} //getBoard

	/**
	 * Getter method for <code>ScoreBoard</code>.
	 * 
	 * @return object that extends <code>VBox</code> containing scores
	 */
	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	} //getScoreBoard
	
	
} //Game