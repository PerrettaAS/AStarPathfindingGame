package org.game.map;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Map {
	
	public static int MAP_SIZE = 12;
	
	public static String MAP_PATH = "./res/maps/";
	
	public static Tile[][] tiles = new Tile[MAP_SIZE][MAP_SIZE];
	
	private String name;
	
	private boolean rendered;
	
	public Map(String name) {
		this.name = MAP_PATH + name;
		rendered = false;
		init();
	}
	public Tile getTile(int x, int y) {
		return tiles[y][x];
	}
	
	/**
	 * Initializes the tiles for the specified map. 
	 */
	public void init() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(name)));
			String line = "";
			int i = 0, x = 0, y = 0;
			while((line = reader.readLine()) != null) {
				String[] type = line.split(" ");
				for(int j = 0; j < tiles[i].length; j++) {
					tiles[i][j] = new Tile(type[j], x, y);
					x += Tile.TILE_SIZE;
				}
				y += Tile.TILE_SIZE;
				x = 0;
				i++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void drawMap(Graphics graphics) {
		int x = 0;
		int y = 0;
		if(!rendered) {
			for(int i = 0; i < tiles.length; i++) {
				for(int j = 0; j < tiles[i].length; j++) {
					graphics.setColor(Color.BLACK);
					graphics.drawImage(tiles[i][j].getImage().getImage(), x, y, tiles[i][j].getImage().getX(), tiles[i][j].getImage().getY(), null);
					x += Tile.TILE_SIZE;
				}
				y += Tile.TILE_SIZE;
				x = 0;
			}
		}
	}

}
