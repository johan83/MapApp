package places;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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

		int maxStringWidth = 0; // width = the rendered width of the text _NOT_ number of chars
		for (String s : textToDisplay) {
			int thisStringWidth = g2d.getFontMetrics(font).stringWidth(s);
			if (thisStringWidth > maxStringWidth)
				maxStringWidth = thisStringWidth;
		}
		int totalWidth = maxStringWidth + getSizeX();
		int fontHeight = g2d.getFontMetrics(font).getHeight();

		int textHeight = fontHeight * textToDisplay.length;
		int totalHeight = textHeight + nameRect.height;
		if (getSizeY() < totalHeight)
			this.setBounds(
					getPosition().getX() - getSizeX() / 2,
					getPosition().getY() - getSizeY(),
					totalWidth,
					totalHeight
					);
		
		// make a white rectangle around the text
		g2d.setColor(Color.WHITE);
		g2d.fillRect(getSizeX() + 1, nameRect.height, totalWidth, textHeight);

		// draw the text to the right of the polygon
		g2d.setColor(Color.BLACK);
		g2d.setFont(font);
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
