package edu.iu.cnets.klatsch.exception;


/**
 * This class supports exception handling for problems at the evaluation level.
 * 
 * The {@link TokenException} and {@link ParserException} classes can be thrown during the
 * parsing phase; this one is thrown at runtime.
 */
public class EvaluationException extends KlatschException
{
	/** make Eclipse happy */
	private static final long serialVersionUID = 2086526771999393540L;

	/** the error message */
	String message;


	/**
	 * Initializes with an error message.
	 * 
	 * @param message  the error message
	 */
	public EvaluationException(String message)
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
		return "Error: " + message;
	}
}
