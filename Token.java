/**
 * Token.java - Hierarchy of token classes for a calculator.
 * COMP141 - Spring 2013
 * Author: Neil Panchal
 */

//Identifiers for the possible token subclasses:
enum TokenType { NUMBER_TOKEN, OPERATOR_TOKEN, PAREN_TOKEN, Null };

public class Token
{
	// Abstract base class for all token objects
	public TokenType getType(){return TokenType.Null;};
}
//A NumberToken stores a real number as a double
class NumberToken extends Token {
	public NumberToken(double _value) { value = _value; }
	public TokenType getType() { return TokenType.NUMBER_TOKEN; }
	public double getValue() { return value; }

	private double value;
}


// An OperatorToken stores a single character
class OperatorToken extends Token
{
	public OperatorToken(char _value) { value = _value; }
	public TokenType getType() { return TokenType.OPERATOR_TOKEN; }
	public char getValue() { return value; }

	private char value;
}

// A ParenToken stores a single character
class ParenToken extends Token
{
	public ParenToken(char _value) { value = _value; }
	public TokenType getType() { return TokenType.PAREN_TOKEN; }
	public char getValue() { return value; }

	private char value;
}