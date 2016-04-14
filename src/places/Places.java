package places;

import java.util.HashMap;

public class Places {
	
	private HashMap<Position,Place> placesByPosition;
	private HashMap<String,Place> placesByName;
	
	public Places(HashMap<Position,Place> placesByPosition,HashMap<String,Place> placesByName){
		this.placesByPosition = placesByPosition;
		this.placesByName = placesByName;
	}
	
	public Place add(Place place){
		Position pos = place.getPosition();
		String name = place.getName();
		placesByName.put(name, place);
		return placesByPosition.put(pos, place);
	}
}
