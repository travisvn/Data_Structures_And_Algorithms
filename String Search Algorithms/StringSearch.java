import java.util.List;
import java.util.ArrayList;
import java.math.*;

public class StringSearch {

	/**
	 * Find all instances of pattern in text using Boyer-Moore algorithm.
	 * Use buildBoyerMooreCharTable to build your reference table.
	 * 
	 * @param pattern
	 * The String to find
	 * @param test
	 * The String to look through
	 * @return
	 * A List of starting indices where pattern was found in text
	 */
	public static List<Integer> boyerMoore(String pattern, String text) {
		int[] ctable = buildBoyerMooreCharTable(pattern);
		int n = text.length();
		int m = pattern.length();
		int i = m-1;
		
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		if (text.length() < pattern.length() || pattern.length() == 0) return indices;
		if (i > n-1) return indices;
		
		int j = m - 1;
		
		do {
			
			if (pattern.charAt(j) == text.charAt(i))
				if (j==0){
					indices.add(i);
					j = m - 1;
					i += m;
					
				}
				else{
					i--;
					j--;
				}
			
			else {
				i = i+ m - Math.min(j, 1 + ctable[text.charAt(i)]);
				j = m - 1;
			}
			
		} while (i <= n - 1);
		
		
		return indices;
	}

	/**
	 * Creates a table of distances from each character to the end.
	 * Has an entry for every character from 0 to Character.MAX_VALUE.
	 * For use with Boyer-Moore.
	 *
	 * If the character is in the string:
	 * 		map[c] = length - last index of c - 1
	 * Otherwise:
	 * 		map[c] = length
	 *
	 * @param pattern
	 * The string being searched for
	 * @return
	 * An int array for Boyer-Moore
	 */
	public static int[] buildBoyerMooreCharTable(String pattern) {
		int[] map = new int[Character.MAX_VALUE + 1];
		int min = 1;
		int len = pattern.length();
		int charMax = 0;
		
		for (int i = 0; i < len; i++){
			map[i] = -1;
			
		}
		for (int i = 0; i< len ; i++){
			map[pattern.charAt(i)]  = i;
		}
		return map;
	}



	/**
	 * Find all instances of pattern in text using Knuth-Morris-Pratt algorithm.
	 * Use buildKmpSuffixTable to build your reference table.
	 * 
	 * @param pattern
	 * The String to find
	 * @param test
	 * The String to look through
	 * @return
	 * A List of starting indices where pattern was found in text
	 */
	public static List<Integer> kmp(String pattern, String text) {
		ArrayList<Integer> indices = new ArrayList<Integer>(); 
		int n = text.length();
		int m = pattern.length();
		if (text.length() < pattern.length() || pattern.length() == 0) return indices;
		int[] st = buildKmpSuffixTable(pattern);
		int i = 0;
		int j = 0;
		
		while (i<n){
			if (pattern.charAt(j) == text.charAt(i) && !indices.contains(i-j)){
				if (j == m-1){
					indices.add(i - j);
					i -= j;
					j=0;
				}
				else{
					i++;
					j++;
				}
			}
			else if (j > 0) j = st[j-1];
			else i++;
		}
		return indices;
	}

	/**
	 * Creates a table of matching suffix and prefix sizes.
	 * For use with Knuth-Morris-Pratt.
	 *
	 * If i = 0:
	 * 		map[i] = -1
	 * If i > 0:
	 * 		map[i] = size of largest common prefix and suffix for substring of size i
	 *
	 * @param pattern
	 * The string bing searched for
	 * @return
	 * An int array for Knuth-Morris-Pratt
	 */
	public static int[] buildKmpSuffixTable(String pattern) {
		int[] st = new int[pattern.length()];
		st[0] = 0;
		int m = pattern.length();
		int j = 0;
		int i = 1;
		while( i  < m){
			if (pattern.charAt(j) == pattern.charAt(i)){
				st[i] = j + 1;
				i++;
				j++;
			}
			else if (j>0){
				j = st[j-1];
			}
			else {
				st[i] = 0;
				i++;
			}
		}
		return st;
	}

	// This is the base to be used for Rabin-Karp. No touchy.
	private static final int BASE = 997;

	/**
	 * Find all instances of pattern in text using Rabin-Karp algorithm.
	 * Use hashString and updateHash to handle hashing.
	 *
	 * @param pattern
	 * The String to find
	 * @param test
	 * The String to look through
	 * @return
	 * A List of starting indices where pattern was found in text
	 */
	public static List<Integer> rabinKarp(String pattern, String text) {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		if (text.length() < pattern.length() || pattern.length() == 0) return indices;
		int hp = hashString(pattern);
		int ht = hashString(text.substring(0, pattern.length()));
		
		

		for (int i = 0; i+pattern.length() < text.length(); i++){
			if (hp == ht){
				indices.add(i);
			}
			ht = updateHash(ht, text.charAt(i+pattern.length()), text.charAt(i), pattern.length());
		}
		if (hp == ht) indices.add(text.length()-pattern.length());
		return indices;
	}

	/**
	 * Hashes a string in a specified way.
	 * For use with Rabin-Karp.
	 *
	 * This hash function will use BASE and the indices of the characters.
	 * Each character at i is multiplied by BASE raised to the power of length - 1 - i
	 * These values are summed to determine the entire hash.
	 *
	 * For example:
	 * Hashing "bunn" as a substring of "bunny" with base 433
	 * hash = b * 433 ^ 3 + u * 433 ^ 2 + n * 433 ^ 1 + n * 433 ^ 0
	 *      = 98 * 433 ^ 3 + 117 * 433 ^ 2 + 110 * 433 ^ 1 + 110 * 433 ^ 0
	 *      = 7977892179
	 *
	 * @param pattern
	 * The String to be hashed
	 * @return
	 * A hash value for the string
	 */
	public static int hashString(String pattern) {
		int hashbrown = 0;
		int pow = 1;
		
		
		for (int i = 0; i < pattern.length(); i++){
			for (int j =  0; j < pattern.length()-1 -i; j++){
				pow*=BASE;
			}
			hashbrown += pattern.charAt(i)*pow;
			pow = 1;
		}
		return hashbrown;
	}

	/**
	 * Updates the oldHash in a specified way.
	 * Follows the same hash formula as hashString.
	 * For use with Rabin-Karp.
	 *
	 * To update the hash, remove the oldChar times BASE raised to the length - 1,
	 * 		multiply by BASE, and add the newChar.
	 * For example:
	 * Shifting from "bunn" to "unny" in "bunny" with base 433
	 * hash("unny") = (hash("bunn") - b * 433 ^ 3) * 433 + y * 433 ^ 0
	 *              = (7977892179 - 98 * 433 ^ 3) * 433 + 121 * 433 ^ 0
	 *              = 9519051770
	 *
	 * @param oldHash
	 * The old hash to be updated
	 * @param newChar
	 * The new character added at the end of the substring
	 * @param oldChar
	 * The old character being removed from the front of the substring
	 * @param length
	 * The length of the hashed substring
	 */
	public static int updateHash(int oldHash, char newChar, char oldChar, int length) {
		//int newHash = 
		int pow = 1;
		for (int i = 0; i < length-1; i++){
			pow*=BASE;
		}
		return (oldHash - oldChar*pow)*BASE + newChar;
	}
}