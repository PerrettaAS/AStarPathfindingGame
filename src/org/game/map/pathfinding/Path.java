package org.game.map.pathfinding;

import java.util.ArrayList;

import org.game.map.Tile;

public class Path {
	
	/**
	 * The list of steps building up this path
	 */
	private ArrayList<Step> steps = new ArrayList<Step>();

	public int getLength() {
		return steps.size();
	}
	
	public Step getStep(int index) {
		return steps.get(index);
	}
	
	public void addFrontStep(int x, int y) {
		steps.add(0, new Step(x, y));
	}

	public static class Step {
		
		private int x;
		
		private int y;

		/**
		 * Constructs a new Step at the location x, y.
		 * @param x, y - x, y coordinate on the map * 64 because of the tile size.
		 */
		public Step(int x, int y) {
			this.x = x * Tile.TILE_SIZE;
			this.y = y * Tile.TILE_SIZE;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

	}
	
}
