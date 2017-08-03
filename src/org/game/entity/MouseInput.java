package org.game.entity;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import org.game.MainGame;
import org.game.event.Event;
import org.game.map.Map;
import org.game.map.Tile;
import org.game.map.pathfinding.Path;
import org.game.map.pathfinding.PathTest;

/**
 * 
 * @author Scott Perretta
 *
 */
public class MouseInput extends MouseAdapter {

	public static int mouseX, mouseY;
	public static Rectangle mouse = new Rectangle(1, 1, 1, 1);
	public boolean eventStarted = false;
	public int step = 1;
	
	@Override
	public void mousePressed(MouseEvent e) {
		final int mouse = e.getButton();
		if(mouse == MouseEvent.BUTTON1) {
			Tile goalTile = Map.tiles[(mouseY / 64)][(mouseX / 64)];
			System.out.println(!goalTile.getImage().getName().equals("g.png"));
			System.out.println(eventStarted);
			if(!goalTile.getImage().getName().equals("g.png") || eventStarted) {
				return;
			}
			PathTest pathTest = new PathTest();
			final Path path = pathTest.getPath(MainGame.game.player.getCurrentTile().getX(), 
					MainGame.game.player.getCurrentTile().getY(), 
					goalTile.getX(), 
					goalTile.getY());
			if(path == null) {
				return;
			}
			while(goalTile.getX() != MainGame.game.player.getCurrentTile().getX() 
					|| goalTile.getY() != MainGame.game.player.getCurrentTile().getY()) {
				if(eventStarted) {
					continue;
				} else {
					eventStarted = true;
					MainGame.game.submit(new Event(500) {
						@Override
						public void execute() throws IOException {
							if(!eventStarted || step >= path.getLength()) {
								stop();
								return;
							}
							if(path.getStep(path.getLength() - 1).getX() == MainGame.game.player.getCurrentTile().getX() && path.getStep(path.getLength() - 1).getY() == MainGame.game.player.getCurrentTile().getY()) {
								stop();
								return;
							}
							MainGame.game.player.moveToTile(path.getStep(step).getX(), path.getStep(step).getY());
							step++;
						}
						@Override
						public void stop() {
							eventStarted = false;
							step = 1;
							super.stop();
						}
					});
				}
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		mouse = new Rectangle(mouseX, mouseY, 1, 1);
	}
	
}

