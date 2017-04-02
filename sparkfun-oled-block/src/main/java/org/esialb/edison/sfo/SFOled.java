package org.esialb.edison.sfo;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.esialb.edison.sfo.NarSystem;

public class SFOled {
	public static final int BUFFER_SIZE = 384;
	public static final int WIDTH = 64;
	public static final int HEIGHT = 48;
	
	public static enum Button {
		UP, DOWN, LEFT, RIGHT, SELECT, A, B,
	}
	
	public static OledImage createImage() {
		return new OledImage(WIDTH, HEIGHT) {
			@Override
			public void paint() {
				OledDataBuffer dataBuffer = (OledDataBuffer) getRaster().getDataBuffer();
				SFOled.write(dataBuffer.getBuffer());
				SFOled.display();
			}
		};
	}

	private static OledSwing swing = null;
	
	public static boolean isSwing() {
		return swing != null;
	}
	
	static {
		if("true".equals(System.getProperty("oled.swing"))) {
			swing = new OledSwing();
			swing.setVisible(true);
		} else {
			try {
				NarSystem.loadLibrary();
				begin0();
			} catch(Throwable t) {
				t.printStackTrace();
				swing = new OledSwing();
				swing.setVisible(true);
			}
		}
	}
	
	public static Map<Button, Boolean> pressed(Map<Button, Boolean> reuse) {
		if(reuse == null)
			reuse = new EnumMap<Button, Boolean>(Button.class);
		reuse.put(Button.UP, isUpPressed());
		reuse.put(Button.DOWN, isDownPressed());
		reuse.put(Button.LEFT, isLeftPressed());
		reuse.put(Button.RIGHT, isRightPressed());
		reuse.put(Button.SELECT, isSelectPressed());
		reuse.put(Button.A, isAPressed());
		reuse.put(Button.B, isBPressed());
		return reuse;
	}
	
	public static Button awaitClick() {
		Map<Button, Boolean> prev = new EnumMap<Button, Boolean>(Button.class);
		Map<Button, Boolean> now = new EnumMap<Button, Boolean>(Button.class);
		for(;;) {
			now = pressed(now);
			for(Button b : Button.values()) {
				if(prev.containsKey(b) && prev.get(b) && !now.get(b))
					return b;
			}
			Map<Button, Boolean> tmp = prev;
			prev = now;
			now = tmp;
		}
	}
	
	public static boolean isUpPressed() { return swing != null ? swing.isUp() : isUpPressed0(); }
	public static boolean isDownPressed() { return swing != null ? swing.isDown() : isDownPressed0(); }
	public static boolean isLeftPressed() { return swing != null ? swing.isLeft() : isLeftPressed0(); }
	public static boolean isRightPressed() { return swing != null ? swing.isRight() : isRightPressed0(); }
	public static boolean isSelectPressed() { return swing != null ? swing.isSelect() : isSelectPressed0(); }
	public static boolean isAPressed() { return swing != null ? swing.isA() : isAPressed0(); }
	public static boolean isBPressed() { return swing != null ? swing.isB() : isBPressed0(); }
	
	public static void invert(boolean invert) { 
		invert0(invert); 
	}
	public static void display() { 
		if(swing != null)
			swing.display0();
		else
			display0(); 
	}
	public static void write(byte[] buf) {
		if(buf.length != BUFFER_SIZE)
			throw new IllegalArgumentException();
		if(swing != null)
			swing.write0(buf);
		else
			write0(buf);
	}
	public static void read(byte[] buf) {
		if(buf.length != BUFFER_SIZE)
			throw new IllegalArgumentException();
		if(swing != null)
			swing.read0(buf);
		else
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
