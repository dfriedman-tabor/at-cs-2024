package random;

import java.util.HashSet;

public class Anagrams {
	
//	# def jumbles(word):
//		#
//		#     return (jumbles2(word, [""]))
//		#
//		# def jumbles2(word, soln):
//		#     if len(word) == 0: return soln
//		#     newsoln = set()
//		#     for s in soln:
//		#         for i in range(len(s)):
//		#             newWord = s[:i] + word[0] + s[i:]
//		#             newsoln.add(newWord)
//		#         newsoln.add(s + word[0])
//		#     return jumbles2(word[1:], newsoln)
//	
	
	
	public static HashSet<String> anagrams(String word) {
		
		HashSet<String> soln = new HashSet<String>();
		soln.add("");
		
		return anagrams(word, soln);
		
	}
	
	public static HashSet<String> anagrams(String word, HashSet<String> soln) {
	
		if (word.length() == 0)  return soln;
		
		HashSet<String> newSoln = new HashSet<String>();
		
		for (String s : soln) {
			
			for (int i = 0; i < s.length(); i++) {
				
				String newWord = s.substring(0, i) + word.charAt(0) + s.substring(i);
				newSoln.add(newWord);
			}
			newSoln.add(s + word.charAt(0));			
		}
		return anagrams(word.substring(1), newSoln);

		
		
		
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(		anagrams("abb"));
		
	}

}
