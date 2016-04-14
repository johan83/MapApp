package places;

import java.util.ArrayList;
import java.util.HashMap;

public class Places {
	
	private HashMap<Position,Place> placesByPosition;
	private HashMap<String,ArrayList<Place>> placesByName; //Förmodligen så nära O(1) man kan komma för namn
	
	public Places(HashMap<Position,Place> placesByPosition, HashMap<String,ArrayList<Place>> placesByName){
		this.placesByPosition = placesByPosition;
		this.placesByName = placesByName;
	}
	
	public Place add(Place place){
		Position pos = place.getPosition();
		String name = place.getName();
		
		ArrayList<Place> names = placesByName.get(name);
		if(!names.contains(place))
			names.add(place);
		
		return placesByPosition.put(pos, place);
	}
	
	public Place getPlaceByPosition(Position p){
		return placesByPosition.get(p);
	}
	public ArrayList<Place> getPlaceByName(String s){
		return placesByName.get(s);		
	}
}
