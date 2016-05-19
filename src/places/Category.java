package places;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Category {
//	private static HashMap<String,Category> currentCategories;
	private static class categoryHolder{
		private static final Map<String,Category> CURRENT_CATEGORIES = new HashMap<>();
		static{
			CURRENT_CATEGORIES.put("Buss", new Category("Buss",Color.red));	
			CURRENT_CATEGORIES.put("Tunnelbana", new Category("Tunnelbana", Color.BLUE));	
			CURRENT_CATEGORIES.put("Tåg", new Category("Tåg", Color.GREEN));	
			CURRENT_CATEGORIES.put("None", new Category("None", Color.BLACK));
		}
	}
	private static Map<String,Category> getCurrentCategories(){
		return categoryHolder.CURRENT_CATEGORIES;
	}
	
	private final String type; //String to facilitate user creating additional categories. If that is not wanted, change to enum
	private final Color color;
	
	private Category(String type, Color color){
		this.type = type;
		this.color = color;
	}
	
	public static Category getCategoryInstance(String type) {
		return getCurrentCategories().get(type);
	}
	public static Set<Entry<String, Category>> getCurrentTypes(){
		return getCurrentCategories().entrySet();
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
