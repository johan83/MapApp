package places;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class DescribedPlace extends Place{

	private String description;

	public DescribedPlace(String name, Position position, TravelCategory color, String description){
		super(name, position, color, PlaceType.Described);
		this.description = description;
	}

	@Override
	String[] getSpecialsToDb() {
		String[] specials = {description};
		return specials;
	}

	@Override
	protected void drawSpecial(Graphics g, Rectangle nameRect) {
		Graphics2D g2d = (Graphics2D) g;
		String[] textToDisplay = description.split("\n");
		Font font = new Font("TimesRoman", Font.PLAIN, 18);
		g2d.setFont(font);

		int maxStringWidth = 0; // width = the rendered width of the text _NOT_ number of chars
		for (String s : textToDisplay) {
			Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(s, g2d);
			int thisStringWidth = (int) bounds.getWidth();
			if (thisStringWidth > maxStringWidth)
				maxStringWidth = thisStringWidth;
		}
		int totalWidth = maxStringWidth + getSizeX();
		int fontHeight = g2d.getFontMetrics(font).getHeight();

		int textHeight = fontHeight * textToDisplay.length;
		int totalHeight = textHeight + nameRect.height;
		
		int maxBoundsWidth = nameRect.width;
		if(nameRect.width<maxStringWidth)
			maxBoundsWidth = maxStringWidth;
		
		if (getSizeY() < totalHeight)
			this.setBounds(
					getX(),
					getY(),
					maxBoundsWidth,
					totalHeight
					);
		
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
