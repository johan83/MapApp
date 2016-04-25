package places;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
	
	public static List<String> readFileContent(Scanner sc){
		List<String> fileContent = new ArrayList<>();
		while(sc.hasNextLine())
			fileContent.add(sc.nextLine());		
		return fileContent;
	}
	public static void writePlacesToFile(Place[] places , PrintWriter writer) throws FileNotFoundException, IOException{
		for(Place p : places)
			writer.println(p.toDb());		
	}
}
