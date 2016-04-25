package places;

import java.awt.Graphics;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class NamedPlace extends Place{
	
	public NamedPlace(String name, Position position, Category color){
		super(name, position, color,PlaceType.Named);
	}

	@Override
	String[] getSpecialsToDb() {
		return null;
	}

	@Override
	protected void drawSpecial(Graphics g, Rectangle nameRect) {}

	@Override
	String getSpecialText() {
		return null;
	}

}
