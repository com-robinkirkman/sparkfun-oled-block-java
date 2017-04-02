package org.esialb.edison.sfo;

import mraa.I2c;

public class Multiplexer {
	private I2c i2c;
	private short address;
	
	public Multiplexer(I2c i2c, short address) {
		this.i2c = i2c;
		this.address = address;
	}
	
	public void select(int n) {
		i2c.address(address);
		byte[] b = new byte[1];
		b[0] = (byte) (1 << (n & 0x7));
		i2c.write(b);
	}
	
	public Runnable selector(final int n) {
		return new Runnable() {
			@Override
			public void run() {
				select(n);
			}
		};
	}
}
