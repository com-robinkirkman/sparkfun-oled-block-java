package com.robinkirkman.edison.sfo;

import java.awt.Color;
import java.awt.Graphics2D;

import org.junit.Test;

public class ImageTest {
	@Test
	public void nothing() {}
	
//	@Test
	public void testOval() throws Exception {
		SFOledImage image = new SFOledImage();
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.drawOval(0, 0, SFOled.WIDTH, SFOled.HEIGHT);
		image.paint();
		
		while(!SFOled.isAPressed())
			;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SFOled.WIDTH, SFOled.HEIGHT);
		image.paint();
	}
}
