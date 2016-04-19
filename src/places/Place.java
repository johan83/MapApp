package places;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public abstract class Place extends JComponent{
	public enum TravelCategory {Buss,Tåg,Tunnelbana,None};
	public enum PlaceType{Named,Described};

	private PlaceType type;
	private String name;
	private Position position;
	private TravelCategory category;
	private boolean showInfo, marked;
	private int sizeX, sizeY;
	private Places places;
	Font font;

	public Place(String name, Position position, TravelCategory category,PlaceType type) {
		sizeX = 25;
		sizeY = 30;
		font = new Font("TimesRoman", Font.PLAIN, 18);
		this.name = name;
		this.position = position;
		this.category = category;
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
	public void setPlaces(Places places){
		this.places = places;
	}
	class PlaceMarker extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				places.setMarked(Place.this,!marked);
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
	public int getSizeX(){
		return sizeX;
	}
	public int getSizeY(){
		return sizeY;
	}

	abstract String getSpecialText();

	public String getName() {
		return name;
	}

	public Position getPosition() {
		return position;
	}

	public TravelCategory getColor() {
		return category;
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
		return name + " " + position + " " + category + " " + getSpecialText();
	}

	private Color getCategoryColor() { // bad? should category be object(String
										// category, Color c)?
		Color c;
		switch (category) {
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
	protected void paintComponent(Graphics g) { //FIXME description should be abstract method(Graphics g)
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(getCategoryColor());
		g2d.fillPolygon(getPolygon());

		if (marked) {
			Rectangle rect = new Rectangle(0, 0, sizeX - 1, sizeY - 1);
			g2d.draw(rect);
		}
		if(showInfo){ //Att endast "fälla ut" showInfo och inte gömma pil godkändes av Jozef Swiatyck

			int maxStringWidth = g2d.getFontMetrics(font).stringWidth(name);
			int totalWidth = maxStringWidth + sizeX;
			int fontHeight = g2d.getFontMetrics(font).getHeight();
			
			if (sizeY > fontHeight) {
				this.setBounds(
						position.getX() - sizeX / 2,
						position.getY() - sizeY,
						totalWidth,
						sizeY
						);
			} else {
				this.setBounds(
						position.getX() - sizeX / 2,
						position.getY() - sizeY,
						totalWidth,
						fontHeight
						);
			}
			g2d.setColor(Color.WHITE);
			Rectangle nameRect = new Rectangle(sizeX, 0, totalWidth, fontHeight);
			g2d.fill(nameRect);

			g2d.setColor(Color.BLACK);
			g2d.setFont(font);
			// draw the text to the right of the polygon
			g2d.drawString(name, sizeX+1, fontHeight);	
			
			drawSpecial(g,nameRect);
		}
	}
	abstract protected void drawSpecial(Graphics g,Rectangle nameRect);

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
		String toDB = type +delim+ category +delim+ position.toDb() +delim+ name;
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
