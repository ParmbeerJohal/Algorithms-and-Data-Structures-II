/*
   (originally from R.I. Nishat - 08/02/2014)
   (revised by N. Mehta - 11/7/2018)


  * Parm Johal
  * Student ID: V00787710
  * Programming Assignment #4
  * November 25, 2018
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class KMP{
  private String pattern;
  private int[][]dfa;
	private int patternLength;

  public KMP(String pattern){
		this.pattern = pattern;
		patternLength = pattern.length();
		// # of rows in terms of letter represented in ascii characters.
		// example: 85 is the 84 is the decimal representation
		// ascii character of 'T'
		dfa = new int[85][patternLength];
		dfa[pattern.charAt(0)][0] = 1;
		for(int x = 0, j = 1; j < patternLength; j++) {
			for(int row = 0; row < 85; row++) {
				dfa[row][j] = dfa[row][x];
			}
			dfa[pattern.charAt(j)][j] = j+1;
			x = dfa[pattern.charAt(j)][x];
		}
  }

  public int search(String text){
		int textLength = text.length();
		int j; int i;
		for(i = 0, j = 0; i < textLength && j < patternLength; i++) {
			j = dfa[text.charAt(i)][j];
		}
		if(j == patternLength) {return i - patternLength;}
		else {return textLength;}
  }

  public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			}
			catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
			}
			System.out.println("Opened file "+args[0] + ".");
			String text = "";
			while(s.hasNext()){
				text += s.next() + " ";
			}
			System.out.println(text);
			for(int i = 1; i < args.length; i++){
				KMP k = new KMP(args[i]);
				int index = k.search(text);
				if(index >= text.length()){
					System.out.println(args[i] + " was not found.");
				}
				else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
			}
		}
		else{
	    System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}
  }
}
