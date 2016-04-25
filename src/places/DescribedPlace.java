package places;

public class DescribedPlace extends Place{

	private String description;
	
	public DescribedPlace(String name, Position position, String description){
		super(name, position);
		this.description = description;
		
	}

	public DescribedPlace(String name, Position position, TravelCategory color, String description){		// Om vi ska sätta kategori via Place, behövs inte denna konstruktor..!?
		super(name, position, color);
		this.description = description;
		
	}
}
