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
public abstract class Place extends JComponent {

	private String name;
	private Position position;
	private TravelCategory color;
	private boolean showInfo, marked;
	private int sizex, sizey;
	Font font;
	
	public Place(String name, Position position, TravelCategory color){
		sizex = 25;
		sizey = 30;
		font = new Font("TimesRoman",Font.PLAIN,18);
		this.name = name;
		this.position = position;
		this.color = color;
		this.setBounds(position.getX()-sizex/2,position.getY()-sizey,sizex,sizey);
		this.setPreferredSize(new Dimension(sizex,sizey));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.addMouseListener(new PlaceMarker());
		setVisible(true);
	}
	private void moveToFront(){
		JLayeredPane parent = (JLayeredPane) this.getParent();
		parent.moveToFront(this);
	}
	class PlaceMarker extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			switch(e.getButton()){
			case MouseEvent.BUTTON1:
				moveToFront();
				marked = true;
				break;
			case MouseEvent.BUTTON3:
				Place.this.setBounds(position.getX()-sizex/2,position.getY()-sizey,sizex,sizey);
				marked = false;
				break;
			}
			repaint();
		}
	}
	@Override 
	public Dimension getMinimumSize(){
		return getPreferredSize();		
	}
	@Override 
	public Dimension getMaximumSize(){
		return getPreferredSize();		
	}
	public String getMarkedText(){
		return name + getSpecialText();
	}
	
	abstract String getSpecialText();
	
	public String getName(){
		return name;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public TravelCategory getColor(){
		return color;
	}
	
	public boolean getShowInfo(){
		return showInfo;
	}
	public boolean getMarked(){
		return marked;
	}
	public void setMarked(boolean bol){
		marked = bol;
	}
	public String toString(){
		return name +" "+ position +" "+ color +" "+ getMarkedText();
	}
	private Color getCategoryColor(){ //dåligt? bättre om category är ett objekt med String name + Color color ? 
		Color c;
		switch(color){
		case BUS:
			c = Color.RED;
			break;
		case TRAIN:
			c = Color.GREEN;
			break;
		case SUBWAY:
			c = Color.BLUE;
			break;
		default:
			c = Color.BLACK;
				break;
		}
		return c;
	}
	private Polygon getPolygon(){
//		points of the poly in order: top left, tip, top right
		int[] xpoints = {0,sizex/2,sizex};
		int[] ypoints = {0,sizey,0};
		Polygon gfx = new Polygon(xpoints,ypoints,3);
		return gfx;
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //use AA
		
		g2d.setColor(getCategoryColor());
		g2d.fillPolygon(getPolygon());
		if(marked){
//			draw border on marked place
			g2d.drawRect(0, 0, sizex, sizey-1); 
			
//			the text to display split where "\n" is found
			String[] textToDisplay = getMarkedText().split("\n");
			
//			width = the rendered width of the text _NOT_ number of chars
			int maxStringWidth = 0;
//			go through the string[] and find the largest string
			for(String s : textToDisplay){
				int thisStringWidth = g2d.getFontMetrics(font).stringWidth(s);
				if(thisStringWidth>maxStringWidth)
					maxStringWidth = thisStringWidth;
			}
//			add the width of the polygon to the max string width to get total width
			int totalWidth = maxStringWidth+sizex;
//			the standard height of the font
			int fontHeight = g2d.getFontMetrics(font).getHeight();
//			height is the standard font height * the number of strings(lines)
			int height = fontHeight*textToDisplay.length;
//			check if sizey is larger than the string height
			if(sizey>height){
//				if it is the height of bounds should be sizey
				this.setBounds(position.getX()-sizex/2,position.getY()-sizey,totalWidth,sizey);
			}else{
//				else it should be height
				this.setBounds(position.getX()-sizex/2,position.getY()-sizey,totalWidth,height);
			}
			
//			make a white rectangle around the text
			g2d.setColor(Color.WHITE);
			g2d.fillRect(sizex+1, 0, totalWidth, height);
			
//			draw the text to the right of the polygon
			g2d.setColor(Color.BLACK);
			g2d.setFont(font);
			int y = 0;
			for(String s : textToDisplay){
				g2d.drawString(s, sizex, y+=fontHeight);
			}
		}
	}
}
