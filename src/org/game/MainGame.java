package org.game;

import java.io.IOException;

import org.game.event.Event;

/**
 * This game is a basic representation of the A* pathfinding algorithm. The 
 * game has no objective or goal other than walking around objects. 
 * 
 * @author Scott Perretta
 *
 */
public class MainGame {
	
	public static Game game;
	
	public static void main(String[] args) {
		game = new Game();
		game.start();
	}

}
