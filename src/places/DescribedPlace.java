package places;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class DescribedPlace extends Place{

	private String description;

	private DescribedPlace(String name, Position position, Category color, String description){
		super(name, position, color, PlaceType.Described);
		this.description = description;
	}
	
	public static DescribedPlace createSafeDescribedPlace(String name, Position pos, Category cat, String desc){
		DescribedPlace place = null;
		
		try{
			place = new DescribedPlace(name,pos,cat,desc);
		}catch(IllegalArgumentException e){
			System.out.println("Wrong arguments supplied\n" + e.getMessage());			
		}catch(NullPointerException e){
			System.out.println("Null argument\n" + e.getMessage());				
		}
		
		return place;
	}

	@Override
	String[] getSpecialsToDb() {
		String[] specials = {description};
		return specials;
	}

	@Override
	protected void drawSpecial(Graphics g, Rectangle nameRect) { //FIXME move calculations out of the method
		if(description == null)
			return;			
		
		Graphics2D g2d = (Graphics2D) g;
		String[] textToDisplay = description.split("\n");

		int maxStringWidth = 0; // = the rendered width of the text _NOT_ length
		for (String s : textToDisplay) {
			Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(s, g2d);
			int thisStringWidth = (int) bounds.getWidth();
			if (thisStringWidth > maxStringWidth)
				maxStringWidth = thisStringWidth;
		}
		int totalWidth = maxStringWidth + getSizeX();
		int fontHeight = g2d.getFontMetrics().getHeight();
		int textHeight = fontHeight * textToDisplay.length; 
		int totalHeight = textHeight + nameRect.height;
		
		int maxBoundsWidth = nameRect.width;
		if(totalWidth > nameRect.width )
			maxBoundsWidth = totalWidth;
		
		if (totalHeight > getSizeY())
			this.setBounds(getX(), getY(), maxBoundsWidth, totalHeight);
		
		// make a white rectangle around the text
		g2d.setColor(Color.WHITE);
		g2d.fillRect(getSizeX(), nameRect.height, totalWidth, textHeight);

		// draw the text to the right of the polygon
		g2d.setColor(Color.BLACK);
		int y = (int) nameRect.getMaxY();
		for (String s : textToDisplay) {
			g2d.drawString(s, getSizeX(), y += fontHeight);
		}	
	}

	@Override
	String getSpecialText() {
		return description;
	}
}
