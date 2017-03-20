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
		g.drawOval(0, 0, 64, 48);
	}
}
