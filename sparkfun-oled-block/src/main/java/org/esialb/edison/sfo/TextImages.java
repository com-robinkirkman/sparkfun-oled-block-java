package org.esialb.edison.sfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TextImages {
	public static final Font FONT = Font.decode(Font.SANS_SERIF).deriveFont(7f);
	public static final FontMetrics FONT_METRICS =
			new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
			.createGraphics()
			.getFontMetrics(FONT);

	public static BufferedImage createUnwrapped(String text) {
		return createUnwrapped(text, Color.WHITE);
	}
	
	public static BufferedImage createUnwrapped(String text, Color color) {
		int height = 9;
		int width = FONT_METRICS.stringWidth(text);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setFont(FONT);
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(0, 0, width, height);
		g.setColor(color);
		g.drawString(text, 0, 7);
		return image;
	}
	
	static boolean isBoundary(String text, int idx) {
		return Character.isWhitespace(text.charAt(idx-1)) ^ Character.isWhitespace(text.charAt(idx));
	}
	
	static boolean isOverrun(String text, int width) {
		return FONT_METRICS.stringWidth(text) > width;
	}

	static List<String> wrappedSplit(int width, String text) {
		if(text.isEmpty())
			return Collections.emptyList();
		List<String> lines = new ArrayList<String>();
		String current = "";
		int idx = 0;
		while(Character.isWhitespace(text.charAt(idx)))
			idx++;
		current += text.charAt(idx++);
		while(idx < text.length()) {
			if(text.charAt(idx) == '\n') {
				lines.add(current);
				current = "";
				idx++;
				continue;
			}
			if(Character.isWhitespace(text.charAt(idx))) {
				if(!isOverrun(current + " ", width))
					current += " ";
				else {
					lines.add(current);
					current = "";
				}
				while(Character.isWhitespace(text.charAt(idx)))
					idx++;
			}
			boolean overrun = isOverrun(current + text.charAt(idx), width);
			if(overrun) {
				if(current.contains(" ")) {
					int cidx = current.lastIndexOf(" ");
					lines.add(current.substring(0, cidx));
					current = current.substring(cidx + 1);
				} else {
					lines.add(current);
					current = "";
				}
			} else
				current += text.charAt(idx++);
		}
		if(!current.isEmpty())
			lines.add(current);
		return lines;
	}
	
	public static BufferedImage createWrapped(int width, String text, Color color) {
		List<String> lines = wrappedSplit(width, text);
		BufferedImage image = new BufferedImage(width, lines.size() * 8, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0, 0, width, image.getHeight());
		for(int i = 0; i < lines.size(); i++) {
			g.drawImage(createUnwrapped(lines.get(i)), 0, 8*i, null);
		}
		return image;
	}
}