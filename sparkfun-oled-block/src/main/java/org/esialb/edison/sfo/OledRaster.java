package org.esialb.edison.sfo;

import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.WritableRaster;

public class OledRaster extends WritableRaster {
	public OledRaster(int width, int height) {
		super(new OledSampleModel(width, height), new OledDataBuffer(width * height / 8), new Point(0, 0));
	}
}
