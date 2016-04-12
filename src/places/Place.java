package places;
import javax.swing.JComponent; // kanske ladda allt? alltså -> * 

public abstract class Place extends JComponent {

	private String name;
	private Position position;
	private TravelCategory color = TravelCategory.NO_CATEGORY;// kategori - buss, tåg, t-bana
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
		int i = 0;
	}
	
	public String getName(){
		return name;
	} // dessa getMetoder borde inte behövas då den ärever från JComponent och alltså
	//ska representeras grafiskt som en trekant på kartan.
	
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
	
	
	
}
