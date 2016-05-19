package places.parsers;

import places.Category;
import places.Places;
import places.Position;
import places.parsers.exceptions.IllegalStringArrayFormatException;
import places.place.Place;
import places.place.PlaceData;
import places.place.PlaceFactory;
import places.place.Place.PlaceType;

public class DescribedParser implements TextPlaceParser{
	@Override
	public Place parse(String[] input, Places places) throws IllegalStringArrayFormatException {
		if(input.length != 6)
			throw new IllegalStringArrayFormatException("Wrong number of arguments, expected: 6");
		
		Category cat = Category.getCategoryInstance(input[1]);
		int x = Integer.parseInt(input[2]);
		int y = Integer.parseInt(input[3]);
		Position pos = Position.createPosition(x, y);
		String name = input[4];
		String desc = input[5];
		
		PlaceData data = PlaceData.createPlaceData(places)
				.category(cat)
				.name(name)
				.description(desc)
				.position(pos);
		return PlaceFactory.createSafePlace(PlaceType.Described, data);
	}
}
