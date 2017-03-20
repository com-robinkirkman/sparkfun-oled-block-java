package com.robinkirkman.edison.sfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.junit.Test;

public class ImageTest {
	@Test
	public void testDraw() {
		SFOledImage image = new SFOledImage();
		Graphics2D g = image.createGraphics();
		g.setFont(Font.decode(Font.SANS_SERIF).deriveFont(8f));
		g.drawString("Hello World, I am an Edison", 0, 8);
		
		image.paint();
	}
}
