package org.game.entity;

import java.awt.Graphics;
import java.io.IOException;

import org.game.MainGame;
import org.game.event.Event;
import org.game.gfx.Sprite;
import org.game.map.Map;
import org.game.map.Tile;
import org.game.map.pathfinding.Path;

public class Player {
	
	/**
	 * Size of the player sprite (64 x 64)
	 */
	public static int PLAYER_SIZE = 64;
	
	private int x;
	private int y; 
	
	public Player() {
		x = 0;
		y = 0;
	}
	
	public void drawPlayer(Graphics graphics) {
		graphics.drawImage(new Sprite("player").getImage(), x, y, PLAYER_SIZE, PLAYER_SIZE, null);
	}
	
	/**
	 * Returns true if the tile selected is walkable. 
	 * @param Tile tile - destination tile for player.
	 */
	public boolean clipTile(Tile tile) {
		if(tile.getImage().getName().contains("r")) {
			return false;
		}
		return true;
	}
	
	public Tile getCurrentTile() {
		Tile[][] tiles = Map.tiles;
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				Tile tile = tiles[i][j];
				if(x >= tile.getX() && x <= tile.getX() && y >= tile.getY() & y <= tile.getY()) {
					return tile;
				}
			}
		}
		return null;
	}
	
	public void moveToTile(int x, int y) {
		Tile[][] tiles = Map.tiles;
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				Tile tile = tiles[i][j];
				if(x >= tile.getX() && x <= tile.getX() + Tile.TILE_SIZE && y >= tile.getY() & y <= tile.getY() + Tile.TILE_SIZE) {
					if(clipTile(tile)) {
						this.x = tile.getX();
						this.y = tile.getY();
					}
				}
			}
		}
	}

}
