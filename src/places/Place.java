package places;
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
	
	
	
}
