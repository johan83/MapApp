package places;

import places.Place.PlaceType;

public class ParserFactory {
	//Singletons
	public static TextPlaceParser 
		namedParser,
		describedParser;
	
	public static TextPlaceParser getParserInstance(PlaceType type){
		switch(type){
		case Named:
			if(namedParser == null)
				namedParser = new NamedParser();
			return namedParser;
		case Described:
			if(namedParser == null)
				describedParser = new DescribedParser();
			return describedParser;
		default:
			throw new IllegalArgumentException("Invalid PlaceType: "+ type);
		}
	}
}
