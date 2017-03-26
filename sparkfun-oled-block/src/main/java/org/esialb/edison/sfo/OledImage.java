package org.esialb.edison.sfo;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class OledImage extends BufferedImage {
	public OledImage() {
		super(new IndexColorModel(1, 2, new byte[] {0, -1}, new byte[] {0,-1}, new byte[] {0,-1}), 
				new OledRaster(), 
				true, 
				null);
	}
	
	public void paint() {
		OledDataBuffer dataBuffer = (OledDataBuffer) getRaster().getDataBuffer();
		SFOled.write(dataBuffer.getBuffer());
		SFOled.display();
	}
}
