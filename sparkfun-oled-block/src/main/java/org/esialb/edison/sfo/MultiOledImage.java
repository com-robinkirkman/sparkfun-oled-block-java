package org.esialb.edison.sfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MultiOledImage extends OledImage {
	
	protected Map<OledImage, int[]> backing = new HashMap<OledImage, int[]>();
	
	public MultiOledImage(int width, int height) {
		super(width, height);
	}
	
	public void add(OledImage oled, int x, int y) {
		backing.put(oled, new int[] {x, y});
	}
	
	public void remove(OledImage oled) {
		backing.remove(oled);
	}

	public void paint(boolean display) {
		for(Entry<OledImage, int[]> e : backing.entrySet()) {
			OledImage oled = e.getKey();
			int x = e.getValue()[0];
			int y = e.getValue()[1];
			oled.createGraphics().drawImage(this, -x, -y, null);
			if(display)
				oled.paint();
		}
	}
	
	@Override
	public boolean shouldPaint() {
		paint(false);
		for(OledImage oled : backing.keySet()) {
			if(oled.shouldPaint())
				return true;
		}
		return false;
	}

	@Override
	public void paint() {
		paint(true);
	}

}
