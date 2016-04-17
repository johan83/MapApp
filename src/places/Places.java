package places;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Places {
	
	private HashMap<Position,Place> placesByPosition;
	private HashMap<String,ArrayList<Place>> placesByName; //Förmodligen så nära O(1) man kan komma för namn
	private HashMap<TravelCategory,ArrayList<Place>> placesByCategory;
	private HashSet<Place> markedPlaces;
	
	public Places(){
		this.placesByPosition = new HashMap<>();
		this.placesByName = new HashMap<>();
		this.placesByCategory = new HashMap<>();
		this.markedPlaces = new HashSet<>();
	}
	
	public Place add(Place place){
		place.setPlaces(this);
		Position pos = place.getPosition();
		String name = place.getName();
		TravelCategory cat = place.getColor();
		
		ArrayList<Place> names = placesByName.get(name);
		if(names == null)
			names = new ArrayList<>();
		if(!names.contains(place))
			names.add(place);
		placesByName.put(name, names);
		
		ArrayList<Place> cats = placesByCategory.get(cat);
		if(cats == null)
			cats = new ArrayList<>();
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
		for(Place p : places){
			setVisibilityByPlace(p,visible);
		}
	}
	public void setMarked(Place place,boolean marked){
		if(marked)
			markedPlaces.add(place);
		else
			markedPlaces.remove(place);	
	}
	public void hideMarked(){
		for(Place p : markedPlaces){
			setVisibilityByPlace(p,false);
			p.setMarked(false);
		}
	}
	public void setVisiblityByName(String name,boolean visible){
		ArrayList<Place> places = placesByName.get(name);
		for(Place p : places){
			setVisibilityByPlace(p,visible);
		}
	}
	public void setVisibilityByPlace(Place p,boolean visible){
		p.setVisible(visible);
	}
	public Place getPlaceByPosition(Position p){
		return placesByPosition.get(p);
	}
	public ArrayList<Place> getPlaceByName(String s){
		return placesByName.get(s);		
	}
}
