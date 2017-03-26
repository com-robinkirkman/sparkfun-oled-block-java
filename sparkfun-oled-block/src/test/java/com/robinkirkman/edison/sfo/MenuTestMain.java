package com.robinkirkman.edison.sfo;

import com.robinkirkman.edison.sfo.SFOled.Button;

public class MenuTestMain {
	public static void main(String[] args) {
		Menu menu = new Menu();
		for(int i = 0; i < 10; i++) {
			Menu menu2 = new Menu();
			for(int j = 0; j < 10; j++) {
				menu2.add(new MenuItem("item " + i + j, MenuItem.RETURN));
			}
			
			menu.add(new MenuItem("item " + i, (b, s) -> {
				menu2.show();
				return false;
			}));
		}
		menu.show();
		System.exit(0);
	}
}
