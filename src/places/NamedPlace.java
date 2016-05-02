package places;

import java.awt.Color;
import java.awt.Graphics;

public class NamedPlace extends Place{
	
/*----------------------------------------------------CONSTRUCTOR--------------------------------------------------*/	
	public NamedPlace(String name, Position position, Registry register){
		super(name, position, register);	
	}
	
/*------------------------------------------------------METHODS----------------------------------------------------*/	
	@Override
	protected void paintPlaceInfo(Graphics g) {
		
		int newX = getSizeX() *3;			// EXPERIMENT
		
		System.out.println(getSizeX());
		System.out.println(newX);
		
		this.setBounds(getPositionX()-15,getPositionY()-25, newX ,getSizeY() );		
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0,getBounds().width-1, getBounds().height -1);
		g.setColor(Color.BLACK);
		g.drawString(getName(), 0, 0 + (getBounds().height -1));
		
		drawIfMarked(g);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Named" +","+ getColor() +","+ getPosition().getX() +","+ getPosition().getY() +","+ getName();
	}
}
