package org.game.gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public String path;
	public int width;
	public int height;
	public int[] pixels;
	
	public SpriteSheet(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(image == null) { 
			return; 
		}
		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.pixels = image.getRGB(0, 0, width, height, null, 0, width);
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = (pixels[i] & 0xFF) / 64; //removes alpha channel from the pixel color. Also puts a 0 1 2 3 or 4 in the sprite sheet.
		}
	}

}
