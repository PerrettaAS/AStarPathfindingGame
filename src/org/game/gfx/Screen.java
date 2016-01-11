package org.game.gfx;

public class Screen {
	
	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	
	public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH];	
	public int[] colours = new int[MAP_WIDTH * MAP_WIDTH * 4];
	
	public int xOffset = 0;
	public int yOffset = 0;
	
	public int width;
	public int height;
	public SpriteSheet spriteSheet;
	
	public Screen(int width, int height, SpriteSheet spriteSheet) {
		this.width = width;
		this.height = height;
		this.spriteSheet = spriteSheet;
		for(int i = 0; i < tiles.length; i++) {
			colours[i * 4] = 0xFF00FF;
			colours[i * 4 + 1] = 0x00FFFF;
			colours[i * 4 + 2] = 0xFFFF00;
			colours[i * 4 + 3] = 0xFFFFFF;
		}
	}
	
	public void render(int[] pixels, int offset, int row) {
		for(int yTile = yOffset >> 3; yTile <= (yOffset + height) >> 3; yTile++) {
			int yMin = yTile * 8 - yOffset;
			int yMax = yMin + 8;
			if(yMin < 0) 
				yMin = 0;
			if(yMax > height) 
				yMax = height;
			for(int xTile = xOffset >> 3; xTile <= (xOffset + height) >> 3; xTile++) {
				int xMin = xTile * 8 - xOffset;
				int xMax = xMin + 8;
				if(xMin < 0) 
					xMin = 0;
				if(xMax > height) 
					xMax = height;
				int tileIndex = (xTile &(MAP_WIDTH_MASK)) + (yTile &(MAP_WIDTH_MASK)) * MAP_WIDTH;
				
				for(int y = yMin; y < yMax; y++) {
					int sheetPixel = ((y + yOffset) & 7) * spriteSheet.width + ((xMin + xOffset) & 7);
					int tilePixel = offset + xMin + y * row;
					for(int x = xMin; x < xMax; x++) {
						int colour = tileIndex * 4 + spriteSheet.pixels[sheetPixel++];
						pixels[tilePixel++] = colours[colour];
					}
				}
			}
		}
	}

}
