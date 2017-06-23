package org.esialb.edison.sfo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
		
		if("-b".equals(args[0])) {
			args = Arrays.copyOfRange(args, 1, args.length);
			SFOled.begin();
		}
		
		if(args.length == 0)
			System.exit(-1);
		
		String title = null;
		if("-t".equals(args[0])) {
			if(args.length < 2)
				System.exit(-1);
			title = args[1];
			args = Arrays.copyOfRange(args, 2, args.length);
		}
		
		if(args.length == 0)
			System.exit(-1);
		
		Menu menu = new Menu(title);
		for(final String arg : args) {
			menu.add(new MenuItem(arg, new MenuItem.MenuAction() {
				@Override
				public boolean perform(Button button, MenuItem source) {
					System.out.println(arg);
					return true;
				}
			}));
		}
		menu.show();
		System.exit(0);
	}
	
	protected String title;
	protected List<MenuItem> items = new ArrayList<MenuItem>();
	
	protected int top;
	protected int pos;
	
	public Menu(String title, MenuItem... items) {
		this.title = title;
		this.items.addAll(Arrays.asList(items));
	}
	public Menu(MenuItem... items) {
		this(null, items);
	}
	
	public void add(MenuItem item) {
		items.add(item);
	}
	
	public int show() {
		int MAX_ITEMS = (title == null ? 6 : 5);
		int ITEM_OFFSET = (title == null ? 0 : 8);
		
		byte[] screenWas = new byte[SFOled.BUFFER_SIZE];
		SFOled.read(screenWas);
		OledImage image = SFOled.createImage();
		Graphics2D g = image.createGraphics();
		for(;;) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
			if(title != null) {
				g.setColor(Color.WHITE);
				for(int y = 0; y < 8; y+=2)
					g.drawRect(0, y, image.getWidth(), 0);
				BufferedImage ti = TextImages.createUnwrapped(title, Color.WHITE);
				g.setColor(Color.BLACK);
				g.fillRect((image.getWidth() - ti.getWidth()) / 2 -1, -1, ti.getWidth() + 3, 8);
				g.drawImage(ti, (image.getWidth() - ti.getWidth()) / 2, -1, null);
				
			}
			for(int i = top; i < Math.min(top + items.size(), top + MAX_ITEMS); i++) {
				Color c = Color.WHITE;
				if(i == pos) {
					c = Color.BLACK;
					g.setColor(Color.WHITE);
					g.fillRect(0, 8*(i-top) + ITEM_OFFSET, image.getWidth(), TextImages.FONT_METRICS.getHeight());
					g.setColor(Color.BLACK);
				}
				Image mi = TextImages.createUnwrapped(items.get(i).getText(), c);
				g.drawImage(mi, 0, 8*(i-top) + ITEM_OFFSET, null);
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
				if(pos >= top + MAX_ITEMS)
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
