package places;

public class NamedPlace extends Place{
	
	public NamedPlace(String name, Position position){
		super(name, position);
		
	}
	public NamedPlace(String name, Position position, TravelCategory color){
		super(name, position, color);
		
	}
}
