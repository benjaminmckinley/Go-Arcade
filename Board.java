package go.game;

import go.game.Space.Resident;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Class representing a <code>GridPane</code> object consisting of
 * either vacant or occupied spaces. Contains methods relating to checking
 * move validity and placing pieces at indices.
 * 
 * @author Benjamin C. McKinley
 */
public class Board extends GridPane {

	private int size;
	private int lastX = -1;
	private int lastY = -1;
	private int blackScore = 0;
	private int whiteScore = 0;
	
	private SpriteSheet pieces = new SpriteSheet("https://i.imgur.com/UgzVv6H.png", 24, 24, 8);
	
	private Space[] spaces;

	/**
	 * Constructor for the game board. Fills board with vacant
	 * <code>Space</code> array.
	 * 
	 * @param size size of row and col for board
	 */
	public Board(int size) {

		this.size = size;
		setAlignment(Pos.CENTER);

		spaces = new Space[size * size];

		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {

				spaces[size * x + y] = new Space(pieces);
				int fauxX = x;
				int fauxY = y;
				spaces[size * x + y].setOnMousePressed(event -> {

					setClickEvent(fauxX, fauxY);
				});
				spaces[size * x + y].setOnMouseEntered(event -> {
					spaces[size * fauxX + fauxY].highlight();
				});
				spaces[size * x + y].setOnMouseExited(event -> {
					spaces[size * fauxX + fauxY].updatePiece();
				});

				setSpace(Resident.VACANT, x, y);
				add(getSpace(x, y), x, y);
			}
		}
	} //Board

	/**
	 * Check if move entered is valid by placing the piece
	 * at the designated location, checking how that affects the board, 
	 * and removing it.
	 * 
	 * @param selected color of piece to validate
	 * @param x coordinate in x direction to check
	 * @param y coordinate in y direction to check
	 * @return boolean value for validity
	 */
	public boolean checkValidMove(Resident selected, int x, int y) {

		setSpace(selected, x, y);

		if (checkSurrounded(x, y)) {
			setSpace(Resident.VACANT, x, y);
			return false;
		}

		setSpace(Resident.VACANT, x, y);
		return true;

	} //checkValidMove

	/**
	 * Place selected piece at the designated location and updates the board
	 * and the score.
	 * 
	 * @param resident type of piece to place
	 * @param x coordinate of x location to place piece
	 * @param y coordinate of y location to place piece
	 */
	public void placePiece(Resident resident, int x, int y) {

		setSpace(resident, x, y);

		updateBoard();

		//re-sets piece in case updateBoard removed it
		setSpace(resident, x, y);
		
		score();

	} //placePiece

	/**
	 * Checks every spot on the <code>Board</code> to see if it has enough liberties
	 * to remain alive. Removes dead pieces.
	 */
	public void updateBoard() {

		boolean[][] toRemove = new boolean[size][size];

		//for each index
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {

				if (checkSurrounded(x, y)) {
					toRemove[x][y] = true;
				}

				for (int xx = 0; xx < size; xx++) {
					for (int yy = 0; yy < size; yy++) {
						getSpace(xx, yy).setSeen(false);
					}
				}

			}
		}

		//remove spaces
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (toRemove[x][y]) {
					getSpace(x, y).setResident(Resident.VACANT);
				}
				getSpace(x, y).setSeen(false);
			}
		}
	} //updateBoard

	/**
	 * Recursively checks whether an individual piece or the group of the same
	 * type it is connected to has any liberties.
	 * 
	 * @param x coordinate of piece to check in the x direction
	 * @param y coordinate of piece to check in the y direction
	 * @return boolean value whether location is surrounded
	 */
	public boolean checkSurrounded(int x, int y) {

		Space stone = getSpace(x, y);

		if (stone.getResident() == Resident.VACANT) {
			return false;
		}

		stone.setSeen(true);

		//check each direction
		int[] offX = {0, 1, -1, 0};
		int[] offY = {1, 0, 0, -1};

		for (int dir = 0; dir < 4; dir++) {

			//if out of bounds of grid count as a filled space
			if (x + offX[dir] < size & y + offY[dir] < size & x + offX[dir] >= 0 & y + offY[dir] >= 0) {

				Space spot = getSpace(x + offX[dir], y + offY[dir]);

				//base case: if there is a vacant spot connected to any element of the group then it can remain alive
				if (spot.getResident() == Resident.VACANT) {

					return false;
				}
				else {
					//if this space has never been looked at and belongs to group
					if (!spot.getSeen() && !stone.belongsToOpponent(spot)) {

						//if any element has a vacant spot then return false all the way back up the chain
						if (!checkSurrounded(x + offX[dir], y + offY[dir])) {

							return false;
						}
					}
				}
			}
		}

		return true;
	} //checkSurrounded

	/**
	 * Getter method for <code>lastX</code>.
	 * 
	 * @return lastX
	 */
	public int getLastX() {
		return lastX;
	}

	/**
	 * Getter method for <code>lastY</code>.
	 * 
	 * @return lastY
	 */
	public int getLastY() {
		return lastY;
	}

	/**
	 * Resets <code>lastX</code> and <code>lastY</code> to -1.
	 */
	public void resetLastMove() {
		lastX = -1;
		lastY = -1;
	}
	/**
	 * Sets <code>lastX</code> and <code>lastY</code> to the clicked
	 * coordinates.
	 * 
	 * @param x clicked x coordinate
	 * @param y clicked y coordinate
	 */
	public void setClickEvent(int x, int y) {

		if (getSpace(x, y).getResident() == Resident.VACANT) {
			lastX = x;
			lastY = y;
		}
	}

	/**
	 * Sets space at param coordinates to the selected type.
	 * 
	 * @param resident type to update piece to
	 * @param x coordinate in x direction of piece to update
	 * @param y coordinate in y direction of piece to update
	 */
	public void setSpace(Resident resident, int x, int y) {
		spaces[size * x + y].setResident(resident);
	} //setSpace

	/**
	 * Counts the number of each stone in play and updates scores.
	 */
	public void score() {
		
		blackScore = 0;
		whiteScore = 0;
		
		for (Space current : spaces) {
			if (current.getResident() == Resident.BLACK) {
				blackScore++;
			}
			if (current.getResident() == Resident.WHITE) {
				whiteScore++;
			}
		}
	} //score
	
	//gets space at x, y
	/**
	 * Gets space at specified location.
	 * 
	 * @param x location in x direction to retrieve piece
	 * @param y location in y direction to retrieve piece
	 * @return <code>Space</code> at param location
	 */
	public Space getSpace(int x, int y) {
		return spaces[size * x + y];
	}

	/**
	 * Getter method for <code>size</code> of board.
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Getter method for <code>blackScore</code>.
	 * 
	 * @return blackScore
	 */
	public int getBlackScore() {
		return blackScore;
	} //getBlackScore
	
	/**
	 * Getter method for <code>whiteScore</code>.
	 * 
	 * @return whiteScore
	 */
	public int getWhiteScore() {
		return whiteScore;
	} //getWhiteScore
	
} //Board