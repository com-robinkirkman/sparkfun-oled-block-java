package org.esialb.edison.sfo;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.esialb.edison.sfo.SFOled.Button;

public class MenuItem {
	public static interface MenuAction {
		public boolean perform(Button button, MenuItem source);
	}
	
	public static MenuAction RETURN = new MenuAction() {
		@Override
		public boolean perform(Button button, MenuItem source) {
			return true;
		}
	};
	
	protected String text;
	protected Map<Button, MenuAction> actions = new EnumMap<Button, MenuAction>(Button.class);
	
	public MenuItem(String text, Object... buttonActionPairs) {
		this.text = text;
		Set<Button> bset = EnumSet.noneOf(Button.class);
		for(int i = 0; i < buttonActionPairs.length; i++) {
			if(buttonActionPairs[i] instanceof MenuAction) {
				for(Button b : bset)
					actions.put(b, (MenuAction) buttonActionPairs[i]);
				bset.clear();
			} else
				bset.add((Button) buttonActionPairs[i]);
		}
	}
	
	public MenuItem(String text, MenuAction action) {
		this(text, Button.A, Button.SELECT, Button.RIGHT, action, Button.B, Button.LEFT, RETURN);
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Map<Button, MenuAction> getActions() {
		return actions;
	}
	
	public boolean perform(Button b) {
		return actions.getOrDefault(b, RETURN).perform(b, this);
	}
}
