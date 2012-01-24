package edu.iu.cnets.klatsch.exception;


/**
 * This class supports exception handling for problems at the parser level.
 */
public class ParserException extends KlatschException
{
	/** make Eclipse happy */
	private static final long serialVersionUID = 5949969259465594529L;

	/** the error message */
	String message;
	
	/** possible contextual information on this error */
	TokenException tokenError = null;


	/**
	 * Initializes without an underlying {@link TokenException}.
	 * 
	 * @param message  the error message
	 */
	public ParserException(String message)
	{
		this.tokenError = null;
		this.message    = message;
	}


	/**
	 * Initializes with a tokenization problem and an error message.
	 * 
	 * @param tokenError  the tokenization error
	 * @param message     the error message
	 */
	public ParserException(TokenException tokenError, String message)
	{
		this.tokenError = tokenError;
		this.message    = message;
	}


	/**
	 * Converts the exception to a printable form.
	 * 
	 * @return the error message
	 */
	public String toString()
	{
		return "Syntax error in " + message +
			((tokenError != null) ? (": " + tokenError.toString()) : "");
	}
}
