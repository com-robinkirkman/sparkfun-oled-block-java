package org.esialb.edison.sfo;

import java.awt.image.DataBuffer;

public class OledDataBuffer extends DataBuffer {
	
	private byte[] buffer;
	
	public OledDataBuffer(int size) {
		super(TYPE_BYTE, size);
		buffer = new byte[size];
	}
	
	@Override
	public int getElem(int bank, int i) {
		return 0xFF & buffer[i];
	}

	@Override
	public void setElem(int bank, int i, int val) {
		buffer[i] = (byte) val;
	}
	
	public byte[] getBuffer() {
		return buffer;
	}
	
}
