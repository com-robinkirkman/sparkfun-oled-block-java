package com.robinkirkman.edison.sfo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.junit.Test;

public class ImageTest {
	@Test
	public void testDraw() {
		Graphics2D g = SFOledImage.get().createGraphics();
		g.setColor(Color.WHITE);
		g.drawLine(0, 0, 63, 47);
		g.setColor(Color.BLACK);
		g.drawLine(0, 47, 63, 0);
		SFOledImage.get().display();
	}
}
