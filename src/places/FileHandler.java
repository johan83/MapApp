package places;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class FileHandler {
	
	public static ArrayList<String> readFileContent(Scanner sc){
		ArrayList<String> fileContent = new ArrayList<>();
		while(sc.hasNextLine()){
			fileContent.add(sc.nextLine());
		}
		return fileContent;
	}
	public static void writePlaceToFile(Set<Entry<Position, Place>> set , PrintWriter writer) throws FileNotFoundException, IOException{
		for(Entry<Position,Place> entry : set){
			writer.println(entry.getValue().toDb());
		}
	}
}
