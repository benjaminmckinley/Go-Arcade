package go.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Class representing list of <code>Rectangle2D</code> objects
 * from a sprite sheet. Pulls data from <code>Image</code> sheet
 * with one row and stores images. Provides methods to retrieve sprites.
 * 
 * @author Benjamin C. McKinley
 */
public class SpriteSheet {

	private Image sheet;
	private Rectangle2D[] sprites;

	private int height;
	private int width;
	private int num;

	/**
	 * Constructor for <code>SpriteSheet</code>.
	 * 
	 * @param loc URL of <code>SpriteSheet</code>
	 * @param height size of each sprites height
	 * @param width size of each sprites width
	 * @param num number of sprites in sheet
	 */
	public SpriteSheet(String loc, int height, int width, int num) {

		sheet = new Image(loc);
		sprites = new Rectangle2D[num];
		this.height = height;
		this.width = width;
		this.num = num;

		loadSprites();

	} //SpriteSheet

	/**
	 * Constructs array of <code>Rectangle2D</code> objects from
	 * <code>sheet</code>.
	 */
	private void loadSprites() {
		for (int i = 0; i < num; i++) {
			sprites[i] = new Rectangle2D(width * i, 0, width, height);	
		}
	} //loadSprites

	/**
	 * Getter method for <code>sheet</code>.
	 * 
	 * @return <code>Image</code> of current <code>SpriteSheet</code>
	 */
	public Image getSheet() {
		return sheet;
	} //getSheet

	/**
	 * Gets <code>Rectangle2D</code> object for parameter sprite.
	 * 
	 * @param index index of sprite to retrieve
	 * @return <code>Rectangle2D</code> of selected sprite
	 */
	public Rectangle2D getSprite(int index) {
		return sprites[index];
	} //getSprite

} //SpriteSheet