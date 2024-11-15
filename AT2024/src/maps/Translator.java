package maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Translator {

	
	public Translator() throws IOException {
		
		try {
			BufferedReader in = new BufferedReader(new FileReader("Text Files/Arabic Dictionary"));
		
			String line;
			
			HashMap<String, String> dict = new HashMap<String, String>();
			
			while ((line = in.readLine()) != null) {
				
				dict.put(line.trim().toLowerCase(), in.readLine().trim().toLowerCase());
			}
			
		
			Scanner input = new Scanner(System.in);
			
			
			String command;
			System.out.print("Enter an English word: ");
			while (!(command = input.nextLine().trim().toLowerCase()).equals("q")) {
			
				if (dict.keySet().contains(command)) {
					System.out.println("Arabic translation: " + dict.get(command));
				}
				else {
					System.out.println("That word is not in this dictionary.");
				}
				
				System.out.println("\n\nEnter another English word or press q to quit: ");

				
			}
			
			
		
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found :(");
		}
		
		
	}
	
	
	public static void main(String [] args) throws IOException {
		
		new Translator();
		
	}
}
