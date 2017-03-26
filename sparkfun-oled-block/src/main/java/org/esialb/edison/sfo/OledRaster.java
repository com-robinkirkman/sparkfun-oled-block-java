package org.esialb.edison.sfo;

import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.WritableRaster;

public class OledRaster extends WritableRaster {
	public OledRaster() {
		super(OledSampleModel.get(), new OledDataBuffer(), new Point(0, 0));
	}
}
