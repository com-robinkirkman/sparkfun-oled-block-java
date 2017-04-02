package org.esialb.edison.sfo;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import mraa.I2c;

public class I2COled {
	public static final int WIDTH = 128;
	public static final int HEIGHT = 64;
	public static final int BUFFER_SIZE = 1024;
	
	public static final Font FONT = Font.decode(Font.SANS_SERIF).deriveFont(15f);
	public static final FontMetrics FONT_METRICS = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).createGraphics().getFontMetrics(FONT);
	
	private static final short SSD1306_I2C_ADDRESS = 0x3C;

	private static final short SSD1306_LCDWIDTH = 128;
	private static final short SSD1306_LCDHEIGHT = 64;


	private static final short SSD1306_SETCONTRAST = 0x81;
	private static final short SSD1306_DISPLAYALLON_RESUME = 0xA4;
	private static final short SSD1306_DISPLAYALLON = 0xA5;
	private static final short SSD1306_NORMALDISPLAY = 0xA6;
	private static final short SSD1306_INVERTDISPLAY = 0xA7;
	private static final short SSD1306_DISPLAYOFF = 0xAE;
	private static final short SSD1306_DISPLAYON = 0xAF;

	private static final short SSD1306_SETDISPLAYOFFSET = 0xD3;
	private static final short SSD1306_SETCOMPINS = 0xDA;

	private static final short SSD1306_SETVCOMDETECT = 0xDB;

	private static final short SSD1306_SETDISPLAYCLOCKDIV = 0xD5;
	private static final short SSD1306_SETPRECHARGE = 0xD9;

	private static final short SSD1306_SETMULTIPLEX = 0xA8;

	private static final short SSD1306_SETLOWCOLUMN = 0x00;
	private static final short SSD1306_SETHIGHCOLUMN = 0x10;

	private static final short SSD1306_SETSTARTLINE = 0x40;

	private static final short SSD1306_MEMORYMODE = 0x20;
	private static final short SSD1306_COLUMNADDR = 0x21;
	private static final short SSD1306_PAGEADDR = 0x22;

	private static final short SSD1306_COMSCANINC = 0xC0;
	private static final short SSD1306_COMSCANDEC = 0xC8;

	private static final short SSD1306_SEGREMAP = 0xA0;

	private static final short SSD1306_CHARGEPUMP = 0x8D;

	private static final short SSD1306_EXTERNALVCC = 0x1;
	private static final short SSD1306_SWITCHCAPVCC = 0x2;

	private static final short SSD1306_ACTIVATE_SCROLL = 0x2F;
	private static final short SSD1306_DEACTIVATE_SCROLL = 0x2E;
	private static final short SSD1306_SET_VERTICAL_SCROLL_AREA = 0xA3;
	private static final short SSD1306_RIGHT_HORIZONTAL_SCROLL = 0x26;
	private static final short SSD1306_LEFT_HORIZONTAL_SCROLL = 0x27;
	private static final short SSD1306_VERTICAL_AND_RIGHT_HORIZONTAL_SCROLL = 0x29;
	private static final short SSD1306_VERTICAL_AND_LEFT_HORIZONTAL_SCROLL = 0x2A;

	protected I2c i2c;
	protected byte[] buffer;
	protected Runnable select;

	protected void select() {
		if(select != null)
			select.run();
	}
	
	public I2COled(I2c i2c) {
		this(i2c, null);
	}
	
	public I2COled(I2c i2c, Runnable select) {
		this.i2c = i2c;
		this.select = select;
		buffer = new byte[BUFFER_SIZE];
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public OledImage createImage() {
		return new OledImage(WIDTH, HEIGHT) {
			@Override
			public void paint() {
				OledDataBuffer dataBuffer = (OledDataBuffer) getRaster().getDataBuffer();
				System.arraycopy(dataBuffer.getBuffer(), 0, buffer, 0, BUFFER_SIZE);
				display();
			}
			
			@Override
			public boolean shouldPaint() {
				byte[] imageBuffer = ((OledDataBuffer) getRaster().getDataBuffer()).getBuffer();
				byte[] oledBuffer = buffer;
				return !Arrays.equals(imageBuffer, oledBuffer);
			}
		};
	}

	private void ssd1306_command(int command) {
		byte[] b = new byte[2];
		b[1] = (byte) command;
		i2c.address(SSD1306_I2C_ADDRESS);
		i2c.write(b);
	}

	public void begin() {
		select();
		// Init sequence
		ssd1306_command(SSD1306_DISPLAYOFF);                    // 0xAE
		ssd1306_command(SSD1306_SETDISPLAYCLOCKDIV);            // 0xD5
		ssd1306_command(0x80);                                  // the suggested ratio 0x80

		ssd1306_command(SSD1306_SETMULTIPLEX);                  // 0xA8
		ssd1306_command(SSD1306_LCDHEIGHT - 1);

		ssd1306_command(SSD1306_SETDISPLAYOFFSET);              // 0xD3
		ssd1306_command(0x0);                                   // no offset
		ssd1306_command(SSD1306_SETSTARTLINE | 0x0);            // line #0
		ssd1306_command(SSD1306_CHARGEPUMP);                    // 0x8D
		ssd1306_command(0x14);
		ssd1306_command(SSD1306_MEMORYMODE);                    // 0x20
		ssd1306_command(0x00);                                  // 0x0 act like ks0108
		ssd1306_command(SSD1306_SEGREMAP | 0x1);
		ssd1306_command(SSD1306_COMSCANDEC);

		ssd1306_command(SSD1306_SETCOMPINS);                    // 0xDA
		ssd1306_command(0x12);
		ssd1306_command(SSD1306_SETCONTRAST);                   // 0x81
		ssd1306_command(0xCF);


		ssd1306_command(SSD1306_SETPRECHARGE);                  // 0xd9
		ssd1306_command(0xF1);
		ssd1306_command(SSD1306_SETVCOMDETECT);                 // 0xDB
		ssd1306_command(0x40);
		ssd1306_command(SSD1306_DISPLAYALLON_RESUME);           // 0xA4
		ssd1306_command(SSD1306_NORMALDISPLAY);                 // 0xA6

		ssd1306_command(SSD1306_DEACTIVATE_SCROLL);

		ssd1306_command(SSD1306_DISPLAYON);//--turn on oled panel

	}

	public void display() {
		select();
		ssd1306_command(SSD1306_COLUMNADDR);
		ssd1306_command(0);   // Column start address (0 = reset)
		ssd1306_command(SSD1306_LCDWIDTH-1); // Column end address (127 = reset)

		ssd1306_command(SSD1306_PAGEADDR);
		ssd1306_command(0); // Page start address (0 = reset)

		ssd1306_command(7); // Page end address

		// I2C
		for (int i=0; i<(SSD1306_LCDWIDTH*SSD1306_LCDHEIGHT/8); i++) {
			// send a bunch of data in one xmission
			byte[] b = new byte[17];
			b[0] = (byte) 0x40;
			
			for (int x=0; x<16; x++) {
				b[x+1] = buffer[i];
				i++;
			}
			i--;
			
			i2c.address(SSD1306_I2C_ADDRESS);
			i2c.write(b);
		}
	}
	
	public void displayOff() {
		select();
		ssd1306_command(SSD1306_DISPLAYOFF);
	}
	
	public void displayOn() {
		select();
		ssd1306_command(SSD1306_DISPLAYON);
	}
}
