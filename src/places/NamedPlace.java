package places;

import java.awt.Graphics;

public class NamedPlace extends Place{
	
	public NamedPlace(String name, Position position){
		super(name, position);
		
	}
	public NamedPlace(String name, Position position, TravelCategory color){			// Om vi ska sätta kategori via Place, behövs inte denna konstruktor..!?
		super(name, position, color);
		
	}
	
	protected void paintPlaceInfo(Graphics g){
		
	}
}
