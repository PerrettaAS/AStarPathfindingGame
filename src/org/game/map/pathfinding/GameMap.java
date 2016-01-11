package org.game.map.pathfinding;

import org.game.entity.Player;
import org.game.map.Map;

public class GameMap {

	public static final int WIDTH = Map.MAP_SIZE;
	public static final int HEIGHT = Map.MAP_SIZE;

	private boolean[][] visited = new boolean[WIDTH][HEIGHT];
	
	private Player player;

	public GameMap(Player player) {
		this.player = player;
	}

	public void clearVisited() {
		for(int x = 0; x < getWidthInTiles(); x++) {
			for(int y = 0; y < getHeightInTiles(); y++) {
				visited[x][y] = false;
			}
		}
	}

	public boolean visited(int x, int y) {
		return visited[x][y];
	}

	public boolean blocked(int sx, int sy, int x, int y) {
		if(Map.tiles[y][x].getImage().getName().contains("r"))
			return true;
		return false;
	}

	public float getCost(int sx, int sy, int tx, int ty) {
		/* Diagonal */
		if(sx > tx && sy > ty) {
			return 14;
		} else if(sx < tx && sy < ty) {
			return 14;
		} else if(sx > tx && sy < ty) {
			return 14;
		} else if(sx < tx && sy > ty) {
			return 14;
		}
		return 10;
	}

	public int getHeightInTiles() {
		return WIDTH;
	}
	
	public int getWidthInTiles() {
		return HEIGHT;
	}

	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}

}
