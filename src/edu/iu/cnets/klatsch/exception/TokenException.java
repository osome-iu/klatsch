package edu.iu.cnets.klatsch.exception;

import edu.iu.cnets.klatsch.parser.Parser;


/**
 * This class supports exception handling for problems at the tokenization level.
 * 
 * Tokenization problems don't make it all the way to the user in isolation; they're caught
 * by the {@link Parser} and used as annotations for a {@link ParserException} that contains
 * a higher-level description of the error.
 */
public class TokenException extends KlatschException
{
	/** make Eclipse happy */
	private static final long serialVersionUID = -443507588169064964L;

	/** the error message */
	String message;


	/**
	 * Initialize given an error message to relay to the user.
	 * 
	 * @param message  the error message
	 */
	public TokenException(String message)
	{
		this.message = message;
	}


	/**
	 * Converts the exception to a printable form.
	 * 
	 * @return the error message
	 */
	public String toString()
	{
		return message;
	}
}
