package places;
import javax.swing.*; // kanske ladda allt? alltså -> * 

import places.ProgramTest.ImageArea;
import places.ProgramTest.MouseLyss;

//import java.awt.event.*; Place skulle kunna ---> implements ActionListener----> prövar att addera detta till program klassen istället.
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
		setVisible(true);
		setBounds(position.getX(), position.getY(), 250, 250);
		setPreferredSize (new Dimension(250,250));
		setMaximumSize (new Dimension(250,250));
		setMinimumSize (new Dimension(250,250));
		
	}
//	public Place(String name, Position position, TravelCategory color){
//		this(name, position);
//		this.color = color;
//		setVisible(true);
//
//	}
	
	public String getName(){
		return name;
	} // dessa getMetoder borde inte behövas då den ärver från JComponent och alltså
	//ska representeras grafiskt som en trekant på kartan.
	
	public Position getPosition(){
		return position;
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
		g.fillRect(position.getX(),position.getY(),250,250);				// EX målning... ska sen bli en polygon --> trekant.
		
		
	}
	protected abstract void paintPlaceInfo(Graphics g);
		// OBS!  måste va abstract och definieras i varje given Place-child 
	
	
}
