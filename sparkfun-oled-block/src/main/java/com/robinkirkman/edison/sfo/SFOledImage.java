package com.robinkirkman.edison.sfo;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class SFOledImage extends BufferedImage {
	public SFOledImage(boolean immediate) {
		super(new IndexColorModel(1, 2, new byte[] {0, -1}, new byte[] {0,-1}, new byte[] {0,-1}), 
				new SFOledRaster(immediate), 
				true, 
				null);
	}
	
	public void paint() {
		SFOledDataBuffer dataBuffer = (SFOledDataBuffer) getRaster().getDataBuffer();
		SFOled.write(dataBuffer.getBuffer());
	}
}
