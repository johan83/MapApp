package places.parsers;

import places.Places;
import places.place.Place;

public interface TextPlaceParser {
	Place parse(String[] input, Places places) throws IllegalStringArrayFormatException;
}