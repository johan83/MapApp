package places;

@SuppressWarnings("serial")
public class NamedPlace extends Place{
	
	public NamedPlace(String name, Position position, TravelCategory color){
		super(name, position, color,PlaceType.Named);
	}

	@Override
	String getSpecialText() {
		return "";
	}

	@Override
	String[] getSpecialsToDb() {
		return null;
	}
}
