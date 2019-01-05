package go.game;

import javafx.scene.image.ImageView;

/**
 * Class representing a space on a board. Provides methods
 * to change the state and image of the object.
 * 
 * @author Benjamin C. McKinley
 */
public class Space extends ImageView {

	private Resident resident;
	private boolean seen = false;

	SpriteSheet pieces;

	/**
	 * Constructor for a vacant <code>Space</code>.
	 * 
	 * @param pieces <code>SpriteSheet</code> of different states of a <code>Space</code>
	 */
	public Space(SpriteSheet pieces) {
		this.pieces = pieces;
		setImage(pieces.getSheet());
		setViewport(pieces.getSprite(4));
		resident = Resident.VACANT;

	} //Space

	/**
	 * Enumeration for the different states of a <code>Space</code>.
	 */
	public enum Resident {
		WHITE, BLACK, VACANT
	} //Resident

	/**
	 * Updates the image of a piece to match the current state.
	 */
	public void updatePiece() {

		switch(resident) {
		case WHITE:
			setViewport(pieces.getSprite(2));
			break;
		case BLACK:
			setViewport(pieces.getSprite(0));
			break;
		case VACANT:
			setViewport(pieces.getSprite(4));
		}
	} //updatePiece

	/**
	 * Changes sprite of a vacant <code>Space</code> to indicate it is being hovered over.
	 */
	public void highlight() {

		if (resident == Resident.VACANT) {
			setViewport(pieces.getSprite(7));
		}

	} //highlight

	/**
	 * Indicates whether a piece belongs to the same type as the
	 * parameter piece.
	 * 
	 * @param space piece to check
	 * @return boolean value of whether a piece belongs to the opponent
	 */
	public boolean belongsToOpponent(Space space) {
		return resident != space.getResident();
	} //belongsToOpponent

	/**
	 * Sets pieces state to the parameter state and updates
	 * the sprite.
	 * 
	 * @param resident updated state
	 */
	public void setResident(Resident resident) {
		this.resident = resident;
		updatePiece();
	} //setResident

	/**
	 * Sets if the object has been checked.
	 * 
	 * @param seen boolean value for if a <code>Space</code> has been checked yet
	 */
	public void setSeen(boolean seen) {
		this.seen = seen;
	} //setSeen

	/**
	 * Getter method for state of <code>Space</code>.
	 * 
	 * @return <code>Resident</code> of object
	 */
	public Resident getResident() {
		return resident;
	} //getResident

	/**
	 * Getter method for <code>seen</code>.
	 * 
	 * @return whether the piece has been checked yet
	 */
	public boolean getSeen() {
		return seen;
	} //getSeen

} //Space
