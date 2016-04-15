package places;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JComponent; // kanske ladda allt? allts� -> * 

public abstract class Place extends JComponent {

	private String name;
	private Position position;
	private TravelCategory color;// kategori - buss, t�g, t-bana
	private boolean showInfo;
	//private boolean visible; // beh�vs ej? JComponent -> setVisible()
	private boolean marked;
	
	public Place(String name, Position position, TravelCategory color){
		this.name = name;
		this.position = position;
		this.color = color;
		setVisible(true);
	}
	
	public String getName(){
		return name;
	} // dessa getMetoder borde inte beh�vas d� den �rever fr�n JComponent och allts�
	//ska representeras grafiskt som en trekant p� kartan.
	
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
	public String toString(){
		return name +" "+ position +" "+ color;
	}
	public Color getCategoryColor(){ //dåligt? bättre om category är ett objekt med String name + Color color ? 
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
	public Polygon getPolygon(){
		int sizeXDelta = 12;
		int sizeYDelta = 25;
		
		int[] topLeft = {position.getX()-sizeXDelta,position.getY()-sizeYDelta};
		int[] topRight = {position.getX()+sizeXDelta,position.getY()-sizeYDelta};
		int[] tip = {position.getX(),position.getY()};
		
		int[] xpoints = {topLeft[0],tip[0],topRight[0]};
		int[] ypoints = {topLeft[1],tip[1],topRight[1]};
		Polygon gfx = new Polygon(xpoints,ypoints,3);
		return gfx;
	}
}
