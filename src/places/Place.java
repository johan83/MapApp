package places;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JComponent; // kanske ladda allt? allts� -> * 

@SuppressWarnings("serial")
public abstract class Place extends JComponent {

	private String name;
	private Position position;
	private TravelCategory color;// kategori - buss, t�g, t-bana
	private boolean showInfo;
	//private boolean visible; // beh�vs ej? JComponent -> setVisible()
	private boolean marked;
	private int sizex;
	private int sizey;
	
	public Place(String name, Position position, TravelCategory color){
		sizex = 24;
		sizey = 35;
		this.name = name;
		this.position = position;
		this.color = color;
		this.setBounds(position.getX()-sizex/2,position.getY()-sizey,sizex,sizey);
		this.setPreferredSize(new Dimension(50,50));
		this.setMinimumSize(new Dimension(50,50));
		this.setMaximumSize(new Dimension(50,50));
		setVisible(true);
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
	}
}
