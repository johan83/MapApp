package places;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
	private File file;
	private Scanner sc;
	private PrintWriter writer;
	
	public FileHandler(File fileToLoad, Scanner sc){
		this.file = fileToLoad;
		this.sc = sc;
	}
	public FileHandler(File fileToWrite, PrintWriter writer){
		this.file = fileToWrite;
		this.writer = writer;
	}
	
	public ArrayList<String> getFileContent(){
		ArrayList<String> fileContent = new ArrayList<>();
		while(sc.hasNextLine()){
			fileContent.add(sc.nextLine());
		}
		return fileContent;
	}
	public boolean writeToFile(ArrayList<String> toWrite){
		if(file.exists() && file.isFile()){
			file.delete();			
		}else{
			for(String s : toWrite){
				writer.println(s);
			}
			return true;
		}
		return false;
	}
}
