package com.robinkirkman.edison.sfo;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class SFOledImage extends BufferedImage {
	private static SFOledImage instance = new SFOledImage();
	public static SFOledImage get() {
		return instance;
	}
	
	
	private SFOledImage() {
		super(new IndexColorModel(1, 2, new byte[] {0, -1}, new byte[] {0,-1}, new byte[] {0,-1}), 
				SFOledRaster.get(), 
				true, 
				null);
	}
	
}
