package places;

@SuppressWarnings("serial")
public class DescribedPlace extends Place{

	private String description;

	public DescribedPlace(String name, Position position, TravelCategory color, String description){
		super(name, position, color, PlaceType.Described);
		this.description = description;
	}

	@Override
	String getSpecialText() {
		return "\n"+description;
	}

	@Override
	String[] getSpecialsToDb() {
		String[] specials = {description};
		return specials;
	}
}
