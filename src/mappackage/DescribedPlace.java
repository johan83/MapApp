package mappackage;

public class DescribedPlace extends Place
{
	private String description;
	
	public DescribedPlace(String placeName, Position position, String description)
	{
		super(placeName, position);
		this.description = description;
	}
	
	public DescribedPlace(String placeName, Position position, PlaceCategory category, String description)
	{
		super(placeName, position, category);
		this.description = description;
	}
}
