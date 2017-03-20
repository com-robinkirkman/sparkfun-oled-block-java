package com.robinkirkman.edison.sfo;

import java.awt.image.DataBuffer;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.SampleModel;

public class SFOledSampleModel extends SampleModel {
	private static SFOledSampleModel instance = new SFOledSampleModel();
	public static SFOledSampleModel get() {
		return instance;
	}
	
	
	private SFOledSampleModel() {
		super(DataBuffer.TYPE_BYTE, SFOled.WIDTH, SFOled.HEIGHT, 1);
	}


	@Override
	public int getNumDataElements() {
		return 1;
	}


	@Override
	public Object getDataElements(int x, int y, Object obj, DataBuffer data) {
		byte[] b = (obj != null) ? (byte[]) obj : new byte[1];
		int idx = x + SFOled.WIDTH * (y / 8);
		b[0] = (byte) data.getElem(idx);
		b[0] = (byte) ((b[0] >> (y % 8)) & 1);
		return b;
	}


	@Override
	public void setDataElements(int x, int y, Object obj, DataBuffer data) {
		int idx = x + SFOled.WIDTH * (y / 8);
		byte b = ((byte[]) obj)[0];
		int mask = 1 << (y % 8);
		int elem = data.getElem(idx);
		if(b == 0)
			elem &= ~mask;
		else
			elem |= mask;
		data.setElem(idx, elem);
	}


	@Override
	public int getSample(int x, int y, int b, DataBuffer data) {
		int idx = x + SFOled.WIDTH * (y / 8);
		return (data.getElem(idx) >>> (y % 8)) & 1;
	}


	@Override
	public void setSample(int x, int y, int b, int s, DataBuffer data) {
		int idx = x + SFOled.WIDTH * (y / 8);
		int elem = data.getElem(idx);
		int mask = 1 << (y % 8);
		if(s == 0)
			elem &= ~mask;
		else
			elem |= mask;
		data.setElem(idx, elem);
	}


	@Override
	public SampleModel createCompatibleSampleModel(int w, int h) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SampleModel createSubsetSampleModel(int[] bands) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DataBuffer createDataBuffer() {
		return null;
	}


	@Override
	public int[] getSampleSize() {
		return new int[] {1};
	}


	@Override
	public int getSampleSize(int band) {
		return 1;
	}
	
}
