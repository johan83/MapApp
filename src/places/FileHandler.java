package places;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
	private File fileToLoad;
	
	public FileHandler(File fileToLoad){
		this.fileToLoad = fileToLoad;
	}
	
	public ArrayList<String> getFileContent(){
		ArrayList<String> fileContent = new ArrayList<>();
		try(Scanner sc = new Scanner(fileToLoad)){
			while(sc.hasNextLine()){
				fileContent.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			return null;
		}
		return fileContent;
	}
	public boolean writeToFile(ArrayList<String> toWrite){
		if(fileToLoad.exists() && fileToLoad.isFile()){
			fileToLoad.delete();			
		}else{
			try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileToLoad,true)))){
				for(String s : toWrite){
					writer.println(s);
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}
}
