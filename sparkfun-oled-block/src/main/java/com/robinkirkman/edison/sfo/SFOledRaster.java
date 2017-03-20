package com.robinkirkman.edison.sfo;

import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.WritableRaster;

public class SFOledRaster extends WritableRaster {
	public SFOledRaster() {
		super(SFOledSampleModel.get(), new SFOledDataBuffer(), new Point(0, 0));
	}
}
