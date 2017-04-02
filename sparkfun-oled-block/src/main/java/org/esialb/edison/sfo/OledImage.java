package org.esialb.edison.sfo;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public abstract class OledImage extends BufferedImage {
	
	public abstract boolean shouldPaint();
	public abstract void paint();
	
	public OledImage(int width, int height) {
		super(new IndexColorModel(1, 2, new byte[] {0, -1}, new byte[] {0,-1}, new byte[] {0,-1}), 
				new OledRaster(width, height), 
				true, 
				null);
	}
	
}
