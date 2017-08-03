package org.game.map.pathfinding;

import org.game.MainGame;

public class PathTest {

	private GameMap map = new GameMap(MainGame.game.player);
	private AStarPathFinder finder;
	private Path path;

	public PathTest() {
		finder = new AStarPathFinder(map, 144, true);
	}

	public int baseX = 0;
	public int baseY = 0;
	public Path getPath(int x, int y, int toX, int toY) {
		try {
			baseX = x;
			baseY = y;
			if(toX < 0 || toX > (12 * 64) || toY < 0 || toY > (12 * 64)) {
				return null;
			}
			path = finder.findPath(MainGame.game.player.getCurrentTile().getX() / 64, MainGame.game.player.getCurrentTile().getY() / 64, toX / 64, toY / 64);
			return path;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}