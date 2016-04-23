package places;

import java.awt.Component;

import places.Place.PlaceType;

public interface PlaceFactoryImp {
	NamedPlace createSafeNamedPlace(String name, Position pos, Category cat);  
	DescribedPlace createSafeDescribedPlace(String name, Position pos, Category cat, String desc);
	Place createQueriedPlace(PlaceType type, Component parent, Position pos, Category cat);
}
