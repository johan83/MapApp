package mappackage;

public class NamedPlace extends Place
{
	public NamedPlace(String placeName, Position position)
	{
		super(placeName, position);
	}
	
	public NamedPlace(String placeName, Position position, PlaceCategory category)
	{
		super(placeName, position, category);
	}
}