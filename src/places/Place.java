package places;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public abstract class Place extends JComponent {

	private String name;
	private Position position;
	private TravelCategory color;
	private boolean showInfo, marked;
	private int sizex, sizey;
	
	public Place(String name, Position position, TravelCategory color){
		sizex = 25;
		sizey = 30;
		this.name = name;
		this.position = position;
		this.color = color;
		this.setBounds(position.getX()-sizex/2,position.getY()-sizey,sizex,sizey);
		this.setPreferredSize(new Dimension(sizex,sizey));
		this.setMinimumSize(new Dimension(sizex,sizey));
		this.setMaximumSize(new Dimension(sizex,sizey));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.addMouseListener(new PlaceMarker());
		setVisible(true);
	}

	class PlaceMarker extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			switch(e.getButton()){
			case MouseEvent.BUTTON1:
				Place.this.setBounds(position.getX()-sizex/2,position.getY()-sizey,200,200);
				marked = true;
				break;
			case MouseEvent.BUTTON3:
				Place.this.setBounds(position.getX()-sizex/2,position.getY()-sizey,sizex,sizey);
				marked = false;
				break;
			}
			System.out.println(marked);
			repaint();
		}
	}
	public String getMarkedText(){
		return name +"\n"+getSpecialText();
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
		return name +" "+ position +" "+ color;
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
		int[] xpoints = {0,sizex/2,sizex};
		int[] ypoints = {0,sizey,0};
		Polygon gfx = new Polygon(xpoints,ypoints,3);
		return gfx;
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(getCategoryColor());
		g.fillPolygon(getPolygon());
		if(marked){
			g.setColor(Color.BLACK);
			g.drawString("test", sizex, 0);
			System.out.println("drawing");
		}
	}
}
