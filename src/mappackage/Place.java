package mappackage;

import javax.swing.JComponent;

public abstract class Place extends JComponent
{
	private String placeName;
	private Position position;
	private PlaceCategory category = PlaceCategory.NO_CATEGORY;
	private boolean isMarked;
	private boolean extended;
	
	public Place(String placeName, Position position)
	{
		this.placeName = placeName;
		this.position = position;
		setVisible(true);
	}
	
	public Place(String placeName, Position position, PlaceCategory category)
	{
		this(placeName, position);
		this.category = category;
	}
	
	public String getPlaceName()
	{
		return placeName;
	}
}