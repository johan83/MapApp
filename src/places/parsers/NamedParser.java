package places.parsers;

import places.Category;
import places.Places;
import places.Position;
import places.parsers.exceptions.IllegalStringArrayFormatException;
import places.place.Place;
import places.place.PlaceData;
import places.place.PlaceFactory;
import places.place.Place.PlaceType;

public class NamedParser implements TextPlaceParser{
	private NamedParser(){}
	private static class NamedParserHolder{
		private static final NamedParser INSTANCE = new NamedParser();
	}
	public static NamedParser getInstance(){
		return NamedParserHolder.INSTANCE;
	}
	@Override
	public Place parse(String[] input, Places places) throws IllegalStringArrayFormatException {
		if(input.length != 5)
			throw new IllegalStringArrayFormatException("Wrong number of arguments, expected: 5");
		
		Category cat = Category.getCategoryInstance(input[1]);
		int x = Integer.parseInt(input[2]);
		int y = Integer.parseInt(input[3]);
		Position pos = Position.createPosition(x, y);
		String name = input[4];
		
		PlaceData data = PlaceData.createPlaceData(places)
				.category(cat)
				.name(name)
				.position(pos);
		return PlaceFactory.createSafePlace(PlaceType.Named, data);
	}
}
