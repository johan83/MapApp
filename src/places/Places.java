package places;

import java.util.ArrayList;
import java.util.HashMap;

public class Places {
	
	private HashMap<Position,Place> placesByPosition;
	private HashMap<String,ArrayList<Place>> placesByName; //Förmodligen så nära O(1) man kan komma för namn
	private HashMap<TravelCategory,ArrayList<Place>> placesByCategory;
	
	public Places(HashMap<Position,Place> placesByPosition, HashMap<String,ArrayList<Place>> placesByName,HashMap<TravelCategory,ArrayList<Place>> placesByCategory){
		this.placesByPosition = placesByPosition;
		this.placesByName = placesByName;
		this.placesByCategory = placesByCategory;
	}
	
	public Place add(Place place){
		Position pos = place.getPosition();
		String name = place.getName();
		TravelCategory cat = place.getColor();
		
		ArrayList<Place> names = placesByName.get(name);
		if(names == null)
			names = new ArrayList<Place>();
		if(!names.contains(place))
			names.add(place);
		placesByName.put(name, names);
		
		ArrayList<Place> cats = placesByCategory.get(cat);
		if(cats == null)
			cats = new ArrayList<Place>();
		if(!cats.contains(place))
			cats.add(place);
		placesByCategory.put(cat, cats);
		
		return placesByPosition.put(pos, place);
	}
	public HashMap<Position,Place> getAllPlaces(){
		return placesByPosition;
	}
	public void setVisibleByCategory(TravelCategory cat, boolean visible){
		ArrayList<Place> places = placesByCategory.get(cat);
		System.out.println(cat.toString());
		for(Place p : places){
			p.setVisible(visible);
		}
	}
	public void setVisibleByMarked(){
		
	}
	public void setVisiblityByName(String name,boolean visible){
		ArrayList<Place> places = placesByName.get(name);
		for(Place p : places){
			p.setVisible(visible);
		}
	}
	public Place getPlaceByPosition(Position p){
		return placesByPosition.get(p);
	}
	public ArrayList<Place> getPlaceByName(String s){
		return placesByName.get(s);		
	}
}
