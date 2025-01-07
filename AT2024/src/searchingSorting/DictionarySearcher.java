package searchingSorting;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class DictionarySearcher {
	
	private static boolean binary = false;	// controls which type of search to use
	
	// sequential search
	public static int seqSearch(String target, List<String> words) {
		
		for (int i = 0; i < words.size(); i++) if (words.get(i).equals(target)) return i;
		
		return -1;
	}
	
	// binary search
	public static int binSearch(String target, List<String> words, int start, int end) {
		
		int mid = (start + end)/2;
		if (words.get(mid).equals(target)) return mid;
		
		if (start > end) return -1;
		
		if( words.get(mid).compareTo(target) < 0) return binSearch(target,words, mid+1, end);
		
		else return binSearch(target, words, start, mid-1);
	
	}

	// generates the list of words from words.txt, then runs the search
	public static void main(String[] args) throws IOException {
		// generates the word list from the dictionary file
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		BufferedReader in = new BufferedReader(new FileReader(file));
		List<String> words = new ArrayList<String>();
		for (String line = in.readLine(); line != null; line = in.readLine()) {
			words.add(line.trim());
		}
		in.close();
		
		// get user input, then run our search on it. 
		Scanner s = new Scanner(System.in);
		
		while (true) {
			
			System.out.println("Enter a word or type q: ");
			String search = s.nextLine().trim().toLowerCase();
			
			if (search.equals("q")) break;
			
			long startTime = System.nanoTime();
			if (!binary)
				System.out.println(search + " appears at line " + (seqSearch(search, words)+1));
			
			else
				System.out.println(search + " appears at line " + (binSearch(search, words, 0, words.size()-1)+1));
			
			// print the runtime of the search
			long elapsed = System.nanoTime() - startTime;
			System.out.println(elapsed);
			System.out.println("runtime: " + (elapsed/1000000.0+"").substring(0,4) + "ms\n\n");

		}
		s.close();
	}
}
