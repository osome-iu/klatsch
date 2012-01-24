package edu.iu.cnets.klatsch.misc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


/**
 * This class contains static utility functions that have no other logical place
 * to go.  Its existence is horribly un-object-y and all that, but so is java.lang.Math.
 */
public class Utility {

	public static Random rnd = new Random();

	
	/**
	 * Joins two arrays of doubles together into a new array.
	 * 
	 * @param a  the first array
	 * @param b  the second array
	 * @return the joined array
	 */
	public static double[] joinDoubleArrays(double[] a, double[] b)
	{
		double[] c = new double[a.length + b.length];
		
		for (int i = 0; i < a.length; ++i)	c[i]            = a[i];
		for (int i = 0; i < b.length; ++i)	c[i + a.length] = b[i];
		
		return c;
	}
	
	
	/**
	 * Joins two arrays of ints together into a new array.
	 * 
	 * @param a  the first array
	 * @param b  the second array
	 * @return the joined array
	 */
	public static int[] joinIntArrays(int[] a, int[] b)
	{
		int[] c = new int[a.length + b.length];
		
		for (int i = 0; i < a.length; ++i)	c[i]            = a[i];
		for (int i = 0; i < b.length; ++i)	c[i + a.length] = b[i];
		
		return c;
	}
	
	
	/**
	 * Pretty-prints an array of objects so that they're separated by ", " substrings.
	 * 
	 * @param array  the input array
	 * @return the array in string form
	 */
	public static String listString(Object[] array)
	{
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < (array.length - 1); i++) {
			buffer.append(array[i].toString());
			buffer.append(", ");
		}
		if (array.length > 0)
			buffer.append(array[array.length - 1].toString());
		
		return buffer.toString();
	}
	
	
	private static final String[] CONSONANT = { "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "qu", "r", "s", "t", "v", "w", "z", "th", "sh", "ch" };
	private static final String[] VOWEL     = { "a", "e", "i", "o", "u" };
	private static final String[] PATTERN   = { "cvc", "cvvc", "cvcv", "cvcv", "vcvc", "cvcvc", "vcvcv", "cvvcv", "cvcvv", "vccvv", "vccvc", 
																							"cvccvc", "cvcvcv", "cvvcvc", "vcvcvc", "cvcvvc", "cvvccv" };

	/**
	 * Creates an artificial name for data generation.
	 * 
	 * @return a new name 
	 */
	public static String randomName()
	{
		String       pattern = PATTERN[rnd.nextInt(PATTERN.length)];
		StringBuffer buffer  = new StringBuffer();
		
		for (int i = 0; i < pattern.length(); ++i)
			buffer.append((pattern.charAt(i) == 'c') ? CONSONANT[rnd.nextInt(CONSONANT.length)] : VOWEL[rnd.nextInt(VOWEL.length)]); 
		
		return buffer.toString();
	}
	
	
	/**
	 * Reads the contents of an entire text file into a single string.
	 * 
	 * @param path  the file to read
	 * @return the string read
	 */
	public static String readFile(String path)
	{
		StringBuffer data = new StringBuffer();
		
		try {

			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null)
				data.append(line + "\n");

			reader.close();
			return data.toString();
		
		} catch (IOException e) {
			System.err.println("I/O error reading '" + path + '"');
			return null;
		}
	}
	
	
	/**
	 * Prepares a {@link String} for use in a SQL query by escaping characters as necessary
	 * and adding single quotes.
	 * 
	 * @param str  the string to wrap
	 * @return the escaped string
	 */
	public static String sqlString(String str)
	{
		StringBuffer buffer = new StringBuffer(str.length() + 2);
		
		buffer.append('\'');
		for (char ch : str.toCharArray()) {
			if ((ch == '\'') || (ch == '\\'))
				buffer.append('\\');
			buffer.append(ch);
		}
		buffer.append('\'');
		
		return buffer.toString();
	}
}
