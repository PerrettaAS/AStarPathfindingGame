package org.game.map;

import org.game.gfx.Sprite;

public class Tile {
	
	public static int TILE_SIZE = 64;
	
	private int x;
	
	private int y;

	private int width;

	private int height;
	
	private Sprite image;
	
	public Tile(String image, int x, int y) {
		this.image = new Sprite(image);
		this.x = x;
		this.y = y;
		this.width = TILE_SIZE;
		this.height = TILE_SIZE;
	}
	
	public Sprite getImage() {
		return image;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
