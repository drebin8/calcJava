/**
 * CalcException.java - Exception class for calculator.
 * COMP141 - Spring 2013
 * Author: Neil Panchal
 */

public class CalcException extends Exception
{
	private static final long serialVersionUID = 1L;
	private String msg;

	public CalcException()
	{
		msg = "UNIDENTIFIED CalcException";
	}

	public CalcException(String _msg)
	{
		msg = "CalcException: " + _msg;
	}

	public String getMessage() 
	{ 
		return msg; 
	} 
}
