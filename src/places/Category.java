package places;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Category {
	private static HashMap<String,Category> currentCategories = new HashMap<>();
	
	private final String type;
	private final Color color;
	
	private Category(String type, Color color){
		this.type = type;
		this.color = color;
	}
	//Factory method
	public static Category createCategory(String type, Color color){
		Category cat = new Category(type,color);
		currentCategories.put(type, cat);
		return cat;
	}
	
	public static Category getCategoryInstance(String type) {
		return currentCategories.get(type);
	}
	public static Set<Entry<String, Category>> getCurrentTypes(){
		return currentCategories.entrySet();
	}
	@Override
	public String toString(){
		return type.toString();
	}
	//getters
	public String getType() {
		return type;
	}
	public Color getColor() {
		return color;
	}

}
