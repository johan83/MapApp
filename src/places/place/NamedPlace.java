package places.place;

import java.awt.Graphics;
import java.awt.Rectangle;

import places.Category;
import places.Position;

@SuppressWarnings("serial")
public class NamedPlace extends Place{
	
	private NamedPlace(String name, Position position, Category color){
		super(name, position, color,PlaceType.Named);
	}
	public static NamedPlace createSafeNamedPlace(String name, Position pos, Category cat){
		NamedPlace place = null;
		
		try{
			place = new NamedPlace(name,pos,cat);
		}catch(IllegalArgumentException e){
			e.printStackTrace();			
		}catch(NullPointerException e){
			e.printStackTrace();			
		}
		
		return place;
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
