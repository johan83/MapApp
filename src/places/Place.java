package places;
import javax.swing.JComponent; // kanske ladda allt? alltså -> * 
//import java.awt.event.*; Place skulle kunna ---> implements ActionListener----> prövar att addera detta till program klassen istället.

public abstract class Place extends JComponent {

	private String name;
	private Position position;
	private TravelCategory color;// kategori - buss, tåg, t-bana
	private boolean showInfo;
	//private boolean visible; // behövs ej? JComponent -> setVisible()
	private boolean marked;
	
	public Place(String name, Position position){
		this.name = name;
		this.position = position;
		
		setVisible(true);
	}
	public Place(String name, Position position, TravelCategory color){
		this(name, position);
		this.color = color;
		setVisible(true);
	}
	
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
		this.color = color;
	}
	
	
	public boolean getShowInfo(){
		return showInfo;
	}
	public boolean getMarked(){
		return marked;
	}
	
	public String toString(){
		//if ()										// kolla child
		return color.toString() + position + name ;			// först + PLATSTYP +  sist + BESKRIVNING
	}
	
	
}
