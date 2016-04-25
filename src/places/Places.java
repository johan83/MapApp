package places;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Places {
	
	private Map<Position,Place> placesByPosition;
	private Map<String,List<Place>> placesByName;
	private Map<Category,List<Place>> placesByCategory;
	private Set<Place> markedPlaces;
	
	private Places(Map<Position, Place> placesByPosition, Map<String,List<Place>> placesByName, Map<Category,List<Place>> placesByCategory, Set<Place> markedPlaces){
		this.placesByPosition = placesByPosition;
		this.placesByName = placesByName;
		this.placesByCategory = placesByCategory;
		this.markedPlaces = markedPlaces;
	}
	public static Places createPlaces(){
		return new Places(new HashMap<>(),new HashMap<>(),new HashMap<>(),new HashSet<>());
	}
	
	public Place add(Place place){
		place.setPlaces(this);
		Position pos = place.getPosition();
		String name = place.getName();
		Category cat = place.getCategory();
		
		List<Place> names = placesByName.get(name);
		if(names == null)
			names = new ArrayList<>();
		if(!names.contains(place))
			names.add(place);
		placesByName.put(name, names);
		
		List<Place> cats = placesByCategory.get(cat);
		if(cats == null)
			cats = new ArrayList<>();
		if(!cats.contains(place))
			cats.add(place);
		placesByCategory.put(cat, cats);
				
		return placesByPosition.put(pos, place);
	}
	public void remove(Place place){
		Position pos = place.getPosition();
		String name = place.getName();
		Category cat = place.getCategory();
		
		placesByPosition.remove(pos);
		List<Place> names = placesByName.get(name);
		names.remove(place);		
		List<Place> cats = placesByCategory.get(cat);
		cats.remove(place);		
		markedPlaces.remove(place);		
	}
	public Place[] removeMarked(){
		Place[] toReturn = markedPlaces.toArray(new Place[markedPlaces.size()]);
		for(Iterator<Place> i = markedPlaces.iterator(); i.hasNext();){
			Place place = i.next();
			Position pos = place.getPosition();
			String name = place.getName();
			Category cat = place.getCategory();
			
			placesByPosition.remove(pos);
			List<Place> names = placesByName.get(name);
			names.remove(place);		
			List<Place> cats = placesByCategory.get(cat);
			cats.remove(place);		
			i.remove();
			place.removeFromParent();
		}
		return toReturn;
	}
	public Map<Position,Place> getAllPlaces(){
		return placesByPosition;
	}
	public void setVisibleByCategory(Category cat, boolean visible){
		List<Place> places = placesByCategory.get(cat);
		if(places != null){
			for(Place p : places){
				setVisibilityByPlace(p,visible);
			}
		}
	}
	public void setMarked(Place place,boolean marked){
		if(marked)
			markedPlaces.add(place);
		else
			markedPlaces.remove(place);
		place.setMarked(marked);	
	}
	public void unMarkAll(){
		if(markedPlaces != null && markedPlaces.size()>0){
			for(Place p : markedPlaces)
				p.setMarked(false);
			markedPlaces.clear();
			}
	}
	public void hideMarked(){
		if(markedPlaces != null && markedPlaces.size()>0){
			for(Place p : markedPlaces){
				setVisibilityByPlace(p,false);
				p.setMarked(false);
			}
		}
	}
	public void setVisiblityByName(String name,boolean visible){
		List<Place> places = placesByName.get(name);
		for(Place p : places){
			setVisibilityByPlace(p,visible);
		}
	}
	public void setVisibilityByPlace(Place p,boolean visible){
		p.setVisible(visible);
	}
	public void setVisibilityByPosition(Position pos){
		Place place = getPlaceByPosition(pos);
		if(place != null)
			place.setVisible(true);
	}
	public Place getPlaceByPosition(Position p){
		return placesByPosition.get(p);
	}
	public List<Place> getPlacesByName(String s){
		return placesByName.get(s);		
	}
}
