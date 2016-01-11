package org.game.gfx;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 *  
 * @author Scott Perretta
 *
 */
public class Sprite {

	/**
	 * Default location of all in-game sprites.
	 */
	public static final String SPRITE_DIRECTORY = "./res/sprites/";
	
	/**
	 * Name of the sprite.
	 */
	private String name;
	
	/**
	 * Constructor for the sprite.
	 */	
	public Sprite(String name) {
		this.name = name + ".png";
	}
		
	/**
	 * Returns the name of the sprite.
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * Returns the pixel length of the sprite.
	 */
	public int getX() {
		return getImage().getWidth(null); 
	}
		
	/**
	 * Returns the pixel width of the sprite.
	 */
	public int getY() {
		return getImage().getHeight(null);
	}
	
	/**
	 * Returns the ImageIcon object for the sprite.
	 */
	public Image getImage() {
		return new ImageIcon(SPRITE_DIRECTORY + name).getImage();
	}

}
