package org.game.map.pathfinding;


import java.util.ArrayList;
import java.util.Collections;

import org.game.misc.SortedList;

public class AStarPathFinder {
	
	/**
	 * List of nodes that have been checked.
	 */
	private ArrayList<Node> closed = new ArrayList<Node>();

	/**
	 * List of nodes that still need to be checked.
	 */
	private SortedList open = new SortedList();

	private GameMap map;

	private int maxSearchDistance;

	private Node[][] nodes;

	private boolean allowDiagMovement;

	private Heuristic heuristic;

	public AStarPathFinder(GameMap map, int maxSearchDistance, boolean allowDiagMovement) {
		this(map, maxSearchDistance, allowDiagMovement, new Heuristic());
	}

	public AStarPathFinder(GameMap map, int maxSearchDistance,
	                       boolean allowDiagMovement, Heuristic heuristic) {
		this.heuristic = heuristic;
		this.map = map;
		this.maxSearchDistance = maxSearchDistance;
		this.allowDiagMovement = allowDiagMovement;
		nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
		for(int x = 0; x < map.getWidthInTiles(); x++) {
			for(int y = 0; y < map.getHeightInTiles(); y++) {
				nodes[x][y] = new Node(x, y);
			}
		}
	}

	public Path findPath(int sx, int sy, int tx, int ty) {
		nodes[sx][sy].cost = 0;
		nodes[sx][sy].depth = 0;
		closed.clear();
		open.clear();
		open.add(nodes[sx][sy]);
        if(tx < map.getWidthInTiles() && ty < map.getHeightInTiles()) {
            nodes[tx][ty].parent = null;
        }
		int maxDepth = 0;
		while((maxDepth < maxSearchDistance) && (open.size() != 0)) {
			Node current = getFirstInOpen();
			if(current == nodes[tx][ty]) {
				break;
			}
			removeFromOpen(current);
			addToClosed(current);
			for(int x = -1; x < 2; x++) {
				for(int y = -1; y < 2; y++) {
					if((x == 0) && (y == 0)) {
						continue;
					}
					if(!allowDiagMovement) {
						if((x != 0) && (y != 0)) {
							continue;
						}
					}
					int xp = x + current.x;
					int yp = y + current.y;
					if(isValidLocation(current.x, current.y, xp, yp)) {
						float nextStepCost = current.cost + getMovementCost(current.x, current.y, xp, yp);
						Node neighbour = nodes[xp][yp];
						map.pathFinderVisited(xp, yp);
						if(nextStepCost < neighbour.cost) {
							if(inOpenList(neighbour)) {
								removeFromOpen(neighbour);
							}
							if(inClosedList(neighbour)) {
								removeFromClosed(neighbour);
							}
						}
						if(!inOpenList(neighbour) && ! (inClosedList(neighbour))) {
							neighbour.cost = nextStepCost;
							neighbour.heuristic = getHeuristicCost(xp, yp, tx, ty);
							maxDepth = Math.max(maxDepth, neighbour.setParent(current));
							addToOpen(neighbour);
						}
					}
				}
			}
		}
		if(nodes[tx][ty].parent == null) {
			return null;
		}
		Path path = new Path();
		Node target = nodes[tx][ty];
		while(target != nodes[sx][sy]) {
			path.addFrontStep(target.x, target.y);
			target = target.parent;
		}
		path.addFrontStep(sx, sy);
		return path;
	}

	protected Node getFirstInOpen() {
		return (Node) open.first();
	}

	protected void addToOpen(Node node) {
		open.add(node);
	}

	protected boolean inOpenList(Node node) {
		return open.contains(node);
	}

	protected void removeFromOpen(Node node) {
		open.remove(node);
	}

	protected void addToClosed(Node node) {
		closed.add(node);
	}

	protected boolean inClosedList(Node node) {
		return closed.contains(node);
	}

	protected void removeFromClosed(Node node) {
		closed.remove(node);
	}

	protected boolean isValidLocation(int sx, int sy, int x, int y) {
		boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles());
		if((!invalid) && ((sx != x) || (sy != y))) {
			invalid = map.blocked(sx, sy, x, y);
		}
		return !invalid;
	}

	public float getMovementCost(int sx, int sy, int tx, int ty) {
		return map.getCost(sx, sy, tx, ty);
	}

	public float getHeuristicCost(int x, int y, int tx, int ty) {
		return heuristic.getCost(x, y, tx, ty);
	}

	private class Node implements Comparable {
		
		private int x;
		private int y;
		private float cost;
		private Node parent;
		private float heuristic;
		private int depth;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;

			return depth;
		}

		public int compareTo(Object other) {
			Node o = (Node) other;

			float f = heuristic + cost;
			float of = o.heuristic + o.cost;

			if(f < of) {
				return - 1;
			} else if(f > of) {
				return 1;
			} else {
				return 0;
			}
		}
		
	}
	
}