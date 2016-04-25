package places;

public enum TravelCategory {
	BUS ("Bus"),
	TRAIN ("Train"),
	SUBWAY	("Subway");
	//NO_CATEGORY
	
	private String category;
	
	
	private TravelCategory(String category){
		this.category = category;
	}
	
}

