package places;

import java.awt.Color;
import java.awt.Graphics;

public class NamedPlace extends Place{
	
	public NamedPlace(String name, Position position){
		super(name, position);
		
	}
	
	@Override
	protected void paintPlaceInfo(Graphics g) {
		int x = getBounds().width;
		int y = getBounds().height;
		
//		this.setBounds(getPositionX()-15,getPositionY()-25, x *3 ,y );		// Experiment vill göra längre textruta!
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0,getBounds().width-1, getBounds().height -1);
		g.setColor(Color.BLACK);
		g.drawString(getName(), 0, 0 + (getBounds().height -1));
		repaint();
	}
}
