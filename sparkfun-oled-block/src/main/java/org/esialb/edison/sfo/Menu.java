package org.esialb.edison.sfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.esialb.edison.sfo.SFOled.Button;

public class Menu {
	public static void main(String[] args) {
		if(args.length == 0)
			System.exit(-1);
		
		Menu menu = new Menu();
		for(String arg : args) {
			menu.add(new MenuItem(arg, (b, i) ->  {
				System.out.print(arg);
				return true;
			}));
		}
		menu.show();
		System.exit(0);
	}
	
	protected List<MenuItem> items = new ArrayList<>();
	
	protected int top;
	protected int pos;
	
	public Menu(MenuItem... items) {
		this.items.addAll(Arrays.asList(items));
	}
	
	public void add(MenuItem item) {
		items.add(item);
	}
	
	public int show() {
		byte[] screenWas = new byte[SFOled.BUFFER_SIZE];
		SFOled.read(screenWas);
		OledImage image = new OledImage();
		Graphics2D g = image.createGraphics();
		for(;;) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
			for(int i = top; i < Math.min(top + items.size(), top + 6); i++) {
				Color c = Color.WHITE;
				if(i == pos) {
					c = Color.BLACK;
					g.setColor(Color.WHITE);
					g.fillRect(0, 8*(i-top), image.getWidth(), TextImages.FONT_METRICS.getHeight());
					g.setColor(Color.BLACK);
				}
				Image mi = TextImages.createUnwrapped(items.get(i).getText(), c);
				g.drawImage(mi, 0, 8*(i-top), null);
			}
			image.paint();
			Button b = SFOled.awaitClick();
			if(b == Button.UP) {
				if(pos > 0)
					pos--;
				if(pos < top)
					top--;
			} else if(b == Button.DOWN) {
				if(pos < items.size() - 1)
					pos++;
				if(pos >= top + 6)
					top++;
			} else {
				MenuItem mi = items.get(pos);
				if(mi.perform(b)) {
					SFOled.write(screenWas);
					SFOled.display();
					return pos;
				}
			}
			
		}
	}
}
