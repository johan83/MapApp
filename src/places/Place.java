package places;
import javax.swing.*; // kanske ladda allt? alltså -> * 


import java.awt.*;

public abstract class Place extends JComponent {

	private String name;
	private Position position;
	private TravelCategory color;// kategori - buss, tåg, t-bana
	private boolean showInfo = false;
	private boolean marked = false;

	
	public Place(String name, Position position){
		this.name = name;
		this.position = position;
		
		this.setBounds(position.getX()-20,position.getY()-25,40,25);
		this.setPreferredSize (new Dimension(100,100));
		this.setMaximumSize (new Dimension(100,100));
		this.setMinimumSize (new Dimension(100,100));
		setVisible(true);
		
	}
	
	public String getName(){
		return name;
	} // dessa getMetoder borde inte behövas då den ärver från JComponent och alltså
	//ska representeras grafiskt som en trekant på kartan.
	
	public Position getPosition(){
		return position;
	}
	public int getPositionX(){
		return position.getX();
	}
	public int getPositionY(){
		return position.getY();
	}
	
	public TravelCategory getColor(){
		return color;
	}
	
	public void setCategory(TravelCategory color){
		try{
		this.color = color;
		}catch(NullPointerException e){
			// Inget problem, då Place kan va kategorilös!
		}
	}
	
	
	public boolean getShowInfo(){
		return showInfo;
	}
	public boolean getMarked(){
		return marked;
	}
	
	public String toString(){
		//if ()										// kolla child
		return position + name ;			// först + PLATSTYP +  sist + BESKRIVNING
	}
	
	@Override
	protected void paintComponent(Graphics g){
		if(showInfo){
			paintPlaceInfo(g);
		}else{
			paintPlace(g);
		}
	}
	
	private void paintPlace(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		int[] xLed = {0,20,40};
		int[] yLed = {0,25,0};
		
		if(color != null){
			switch(color.toString()){
			
			case "BUS":
				g.setColor(Color.RED);
				break;
			
			case "TRAIN":
				g.setColor(Color.GREEN);
				break;
				
			case "SUBWAY":
				g.setColor(Color.BLUE);
				break;
			default:
				}
		}
		
		g.fillPolygon(xLed, yLed, 3);	
	}
	protected abstract void paintPlaceInfo(Graphics g);
		// OBS!  måste va abstract och definieras i varje given Place-child 
	
	
}
