package com.robinkirkman.edison.sfo;

import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.WritableRaster;

public class SFOledRaster extends WritableRaster {
	private static SFOledRaster instance = new SFOledRaster();
	public static SFOledRaster get() {
		return instance;
	}
	
	private SFOledRaster() {
		super(new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE, SFOled.WIDTH, SFOled.HEIGHT, 1), SFOledDataBuffer.get(), new Point(0, 0));
	}
}
