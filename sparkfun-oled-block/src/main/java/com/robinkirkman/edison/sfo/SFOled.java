package com.robinkirkman.edison.sfo;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class SFOled {
	public static final int BUFFER_SIZE = 384;
	public static final int WIDTH = 64;
	public static final int HEIGHT = 48;
	
	private static BufferedImage bufferedImage = new BufferedImage(
			new IndexColorModel(1, 2, new byte[] {0, -1}, new byte[] {0,-1}, new byte[] {0,-1}), 
			SFOledRaster.get(), 
			true, 
			null);
	
	static {
		NarSystem.loadLibrary();
		begin0();
	}

	public static boolean isUpPressed() { return isUpPressed0(); }
	public static boolean isDownPressed() { return isDownPressed0(); }
	public static boolean isLeftPressed() { return isLeftPressed0(); }
	public static boolean isRightPressed() { return isRightPressed0(); }
	public static boolean isSelectPressed() { return isSelectPressed0(); }
	public static boolean isAPressed() { return isAPressed0(); }
	public static boolean isBPressed() { return isBPressed0(); }
	
	public static void invert(boolean invert) { invert0(invert); }
	public static void display() { display0(); }
	public static void write(byte[] buf) {
		if(buf.length != BUFFER_SIZE)
			throw new IllegalArgumentException();
		write0(buf);
	}
	public static void read(byte[] buf) {
		if(buf.length != BUFFER_SIZE)
			throw new IllegalArgumentException();
		read0(buf);
	}
	
	private static native boolean isUpPressed0();
	private static native boolean isDownPressed0();
	private static native boolean isLeftPressed0();
	private static native boolean isRightPressed0();
	private static native boolean isSelectPressed0();
	private static native boolean isAPressed0();
	private static native boolean isBPressed0();
	
	private static native void begin0();
	private static native void invert0(boolean invert);
	private static native void display0();
	private static native void write0(byte[] buf);
	private static native void read0(byte[] buf);
}
