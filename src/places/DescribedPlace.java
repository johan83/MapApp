package places;

import java.awt.Color;
import java.awt.Graphics;

public class DescribedPlace extends Place{

	private String description;
	
	public DescribedPlace(String name, Position position, String description){
		super(name, position);
		this.description = description;
		
	}
	
	protected void paintPlaceInfo(Graphics g){
		
		int newX = getSizeX() *3;			// EXPERIMENT
		int newY = getSizeY() *2;
		
		
		System.out.println(getSizeX());
		System.out.println(newX);
		
		this.setBounds(getPositionX()-15,getPositionY()-25, newX , newY);		
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0,getBounds().width-1, getBounds().height -1);
		g.setColor(Color.BLACK);
		g.drawString(getName(), 0, 0 + (getBounds().height - getSizeY()));
		g.drawString(description, 0, 0 + (getBounds().height -1));
		
		
		drawIfMarked(g);
		
	}
}
