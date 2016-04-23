package places;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Category {
	public enum CategoryType { Buss, Tåg, Tunnelbana, None };
	private static HashMap<CategoryType,Category> currentCategories = new HashMap<>();
	
	private final CategoryType type;
	private final Color color;
	
	private Category(CategoryType type, Color color){
		this.type = type;
		this.color = color;
	}
	//Factory method
	public static Category createCategory(CategoryType type, Color color){
		Category cat = new Category(type,color);
		currentCategories.put(type, cat);
		return cat;
	}
	
	public static Category getCategoryInstance(CategoryType type) {
		return currentCategories.get(type);
	}
	public static Set<Entry<CategoryType, Category>> getCurrentTypes(){
		return currentCategories.entrySet();
	}
	@Override
	public String toString(){
		return type.toString();
	}
	//getters
	public CategoryType getType() {
		return type;
	}
	public Color getColor() {
		return color;
	}

}
