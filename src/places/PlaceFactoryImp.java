package places;

import java.awt.Component;

import places.Place.PlaceType;
import places.Place.TravelCategory;

public interface PlaceFactoryImp {
	NamedPlace createSafeNamedPlace(String name, Position pos, TravelCategory cat);  
	DescribedPlace createSafeDescribedPlace(String name, Position pos, TravelCategory cat, String desc);
	Place createQueriedPlace(PlaceType type, Component parent, Position pos, TravelCategory cat);
}
