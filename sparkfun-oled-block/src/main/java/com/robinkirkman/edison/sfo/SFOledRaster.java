package com.robinkirkman.edison.sfo;

import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.WritableRaster;

public class SFOledRaster extends WritableRaster {
	public SFOledRaster(boolean immediate) {
		super(SFOledSampleModel.get(), new SFOledDataBuffer(immediate), new Point(0, 0));
	}
}
