package com.robinkirkman.edison.sfo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.junit.Test;

public class ImageTest {
	@Test
	public void testDraw() {
		SFOledImage image = new SFOledImage();
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillOval(0, 0, 64, 48);
		image.paint();
	}
}
