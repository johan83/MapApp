package places.place;

import java.awt.Dimension;

import places.Category;
import places.Places;
import places.Position;

public class PlaceData {	
		private String name;
		private Position pos;
		private Category cat;
		private Dimension size;
		private Places places;
		
		private String description;		
	
		private PlaceData(Places places){
			this.places = places;
		}
		public static PlaceData createPlaceData(Places places){
			return new PlaceData(places);
		}
		public PlaceData name(String name){
			this.name = name;
			return this;			
		}
		public PlaceData position(Position pos){
			this.pos = pos;
			return this;			
		}
		public PlaceData category(Category cat){
			this.cat = cat;
			return this;			
		}
		public PlaceData size(Dimension size){
			this.size = size;
			return this;			
		}
		public PlaceData places(Places places){
			this.places = places;
			return this;			
		}
		public PlaceData description(String desc){
			this.description = desc;
			return this;
		}
		//getters
		public String getName() {
			return name;
		}
		public Position getPosition() {
			return pos;
		}
		public Category getCat() {
			return cat;
		}
		public Dimension getSize() {
			return size;
		}
		public Places getPlaces() {
			return places;
		}
		public String getDescription() {
			return description;
		}
}
