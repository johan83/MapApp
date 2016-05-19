package places;

public interface TextPlaceParser {
	Place parse(String[] input, Places places) throws IllegalStringArrayFormatException;
}