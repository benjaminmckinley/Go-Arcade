package go.game;

import go.game.Space.Resident;

/**
 * Class representing a player in a game of Go. Contains
 * methods to make moves on the board and retrieve player data.
 * 
 * @author Benjamin C. McKinley
 */
public class Player {

	protected boolean turn;
	protected Resident selected = Resident.BLACK;
	protected Board gameBoard;

	/**
	 * Constructor for a <code>Player</code>.
	 * 
	 * @param gameBoard board the player is using
	 * @param selected color of player's pieces
	 */
	public Player(Board gameBoard, Resident selected) {
		this.gameBoard = gameBoard;
		this.selected = selected;
	} //Player

	/**
	 * Method waits until the player clicks a valid spot for a piece to be placed and
	 * then places piece on the <code>gameBoard</code>.
	 */
	public void move() {
		turn = true;
		while (turn) {
			while (gameBoard.getLastX() == -1) {
				System.out.print("");
			}

			if (gameBoard.checkValidMove(selected, gameBoard.getLastX(), gameBoard.getLastY())) {

				gameBoard.placePiece(selected, gameBoard.getLastX(), gameBoard.getLastY());
				gameBoard.resetLastMove();
				turn = false;
			}

			gameBoard.resetLastMove();
		}
	} //move

	/**
	 * Getter method for color of player's stones.
	 * 
	 * @return <code>Resident</code> player uses in the game
	 */
	public Resident getSelected() {
		return selected;
	} //getSelected


} //Player
