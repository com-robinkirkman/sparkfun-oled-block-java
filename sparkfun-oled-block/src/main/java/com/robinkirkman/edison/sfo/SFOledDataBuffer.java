package com.robinkirkman.edison.sfo;

import java.awt.image.DataBuffer;

public class SFOledDataBuffer extends DataBuffer {
	
	private boolean immediate;
	private byte[] buffer;
	
	public SFOledDataBuffer(boolean immediate) {
		super(TYPE_BYTE, SFOled.BUFFER_SIZE);
		this.immediate = immediate;
		buffer = new byte[SFOled.BUFFER_SIZE];
	}
	
	@Override
	public int getElem(int bank, int i) {
		return 0xFF & buffer[i];
	}

	@Override
	public void setElem(int bank, int i, int val) {
		buffer[i] = (byte) val;
		if(immediate) {
			SFOled.write(buffer);
			SFOled.display();
		}
	}
	
	public byte[] getBuffer() {
		return buffer;
	}
	
}
