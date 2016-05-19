package places;

import places.Place.PlaceType;

public class NamedParser implements TextPlaceParser{
	@Override
	public Place parse(String[] input, Places places) {
		if(input.length != 5)
			throw new IllegalArgumentException("Wrong number of arguments");
		
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
