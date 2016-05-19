package places.parsers;

import places.place.Place.PlaceType;

public class ParserFactory {	
	public static TextPlaceParser getParserInstance(PlaceType type){
		switch(type){
		case Named:
			return NamedParser.getInstance();
		case Described:
			return DescribedParser.getInstance();
		default:
			throw new IllegalArgumentException("Invalid PlaceType: "+ type);
		}
	}
}
