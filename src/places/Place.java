package places;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;


@SuppressWarnings("serial")
public abstract class Place extends JComponent{
	private String type;
	private String name;
	private Position position;
	private TravelCategory color;
	private boolean showInfo, marked;
	private int sizeX, sizeY;
	Font font;

	public Place(String name, Position position, TravelCategory color,String type) {
		sizeX = 25;
		sizeY = 30;
		font = new Font("TimesRoman", Font.PLAIN, 18);
		this.name = name;
		this.position = position;
		this.color = color;
		this.type = type;
		this.setBounds(position.getX() - sizeX / 2, position.getY() - sizeY, sizeX, sizeY);
		this.setPreferredSize(new Dimension(sizeX, sizeY));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.addMouseListener(new PlaceMarker());
		setVisible(true);
	}

	private void moveToFront() {
		JLayeredPane parent = (JLayeredPane) this.getParent();
		parent.moveToFront(this);
	}

	class PlaceMarker extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				marked = !marked;
				break;
			case MouseEvent.BUTTON3:
				if(showInfo){
					Place.this.setBounds(position.getX() - sizeX / 2, position.getY() - sizeY, sizeX, sizeY);
				}
				showInfo = !showInfo;
				break;
			}
			moveToFront();
			repaint();
		}
	}

	public String getMarkedText() {
		return name + getSpecialText();
	}

	abstract String getSpecialText();

	public String getName() {
		return name;
	}

	public Position getPosition() {
		return position;
	}

	public TravelCategory getColor() {
		return color;
	}

	public boolean getShowInfo() {
		return showInfo;
	}

	public boolean getMarked() {
		return marked;
	}

	public void setMarked(boolean bol) {
		marked = bol;
	}

	public String toString() {
		return name + " " + position + " " + color + " " + getMarkedText();
	}

	private Color getCategoryColor() { // bad? should category be object(String
										// category, Color c)?
		Color c;
		switch (color) {
		case Buss:
			c = Color.RED;
			break;
		case Tåg:
			c = Color.GREEN;
			break;
		case Tunnelbana:
			c = Color.BLUE;
			break;
		default:
			c = Color.BLACK;
			break;
		}
		return c;
	}

	private Polygon getPolygon() {
		// points of the polygon in order: top left, tip, top right
		int[] xpoints = { 0, sizeX / 2, sizeX };
		int[] ypoints = { 0, sizeY, 0 };
		Polygon gfx = new Polygon(xpoints, ypoints, 3);
		return gfx;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(getCategoryColor());
		g2d.fillPolygon(getPolygon());

		if (marked) {
			g2d.drawRect(0, 0, sizeX - 1, sizeY - 1);
		}
		if(showInfo){
			String[] textToDisplay = getMarkedText().split("\n");

			int maxStringWidth = 0; // width = the rendered width of the text _NOT_ number of chars
			for (String s : textToDisplay) {
				int thisStringWidth = g2d.getFontMetrics(font).stringWidth(s);
				if (thisStringWidth > maxStringWidth)
					maxStringWidth = thisStringWidth;
			}
			int totalWidth = maxStringWidth + sizeX;
			int fontHeight = g2d.getFontMetrics(font).getHeight();

			int textHeight = fontHeight * textToDisplay.length;
			if (sizeY > textHeight) {
				this.setBounds(position.getX() - sizeX / 2, position.getY() - sizeY, totalWidth, sizeY);
			} else {
				this.setBounds(position.getX() - sizeX / 2, position.getY() - sizeY, totalWidth, textHeight);
			}

			// make a white rectangle around the text
			g2d.setColor(Color.WHITE);
			g2d.fillRect(sizeX + 1, 0, totalWidth, textHeight);

			// draw the text to the right of the polygon
			g2d.setColor(Color.BLACK);
			g2d.setFont(font);
			int y = 0;
			for (String s : textToDisplay) {
				g2d.drawString(s, sizeX, y += fontHeight);
			}	
		}
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	public String toDb() {
		String delim = ",";
		String toDB = type +delim+ color +delim+ position.toDb() +delim+ name;
		String[] specials = getSpecialsToDb();
		if(specials!=null){
			for(String s : specials){
				toDB += delim+ s;
			}
		}
		return toDB;
	}
	abstract String[] getSpecialsToDb();
		
	
}
