package org.esialb.edison.sfo;

import org.esialb.edison.sfo.Menu;
import org.esialb.edison.sfo.MenuItem;
import org.esialb.edison.sfo.SFOled.Button;

public class MenuTestMain {
	public static void main(String[] args) {
		Menu menu = new Menu();
		for(int i = 0; i < 10; i++) {
			final Menu menu2 = new Menu("Submenu " + i);
			for(int j = 0; j < 10; j++) {
				menu2.add(new MenuItem("item " + i + j, MenuItem.RETURN));
			}
			
			menu.add(new MenuItem("item " + i, new MenuItem.MenuAction() {
				@Override
				public boolean perform(Button button, MenuItem source) {
					menu2.show();
					return false;
				}
			}));
		}
		menu.show();
		System.exit(0);
	}
}
