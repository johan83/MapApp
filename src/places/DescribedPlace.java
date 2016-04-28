package places;

import java.awt.Graphics;

public class DescribedPlace extends Place{

	private String description;
	
	public DescribedPlace(String name, Position position, String description){
		super(name, position);
		this.description = description;
		
	}
	
	protected void paintPlaceInfo(Graphics g){
		
		
	}
}
