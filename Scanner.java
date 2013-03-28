/**
 * Scanner.java - Scanner class for a calculator.
 * COMP141 - Spring 2013
 * Author: Neil Panchal
 */

import java.util.*;

public class Scanner {
	private List<Token> tokens;
	private short cursor;
	private boolean end_of_expr;
	private char next_char;
	private char [] expr;

	public Scanner()
	{
	tokens = new ArrayList <Token> ();
	}
	
	public List<Token> parseExpression(String expression) throws CalcException
	{
		expr = expression.toCharArray();
		end_of_expr = false;
		cursor = -1;
		advanceCursor();

		Token token = null;
		do {
			token = getNextToken();
			if (token != null) tokens.add(token);
		} while (token != null);

		return tokens;
	}




	private Token getNextToken() throws CalcException
	{
		Token token = null;
		skipWhiteSpace();
		if (end_of_expr) 
		{
			token = null;
		}
		else if (isParen(next_char)) 
		{
			token = new ParenToken(next_char);
			advanceCursor();
		}
		else if (isOperator(next_char))
		{
			token =  new OperatorToken(next_char);;
			advanceCursor();
		}
		else if (isDigitOrDecimal(next_char))
		{
			token = new NumberToken(scanNumber());
		}
		else
		{
			throw new CalcException("Invalid Character: " + next_char);
		}
		return token;
	}
	void skipWhiteSpace()
	{
		while (!end_of_expr && isWhiteSpace(next_char))
			advanceCursor();
	}
	private double scanNumber() throws CalcException
	{
		boolean found_a_digit = false;
		double whole_part = 0.0;
		double fract_part = 0.0;
		double fract_multiplier = 0.1;
		boolean past_decimal = false;
		while (!end_of_expr && isDigitOrDecimal(next_char))
		{
			if (next_char == '.')
			{
				if (past_decimal) throw new CalcException("badly formed number - multiple decimal points");
				past_decimal = true;
			}
			else
			{
				found_a_digit = true;
				if (!past_decimal)
				{
					whole_part = whole_part*10 + next_char-'0';
				}
				else
				{
					fract_part = fract_part + (next_char-'0')*fract_multiplier;
					fract_multiplier = fract_multiplier/10.0;
				}
			}
			advanceCursor();
		}
		if (!found_a_digit)
		{
			throw new CalcException("badly formed number - decimal point with no digits");
		}
		return whole_part+fract_part;
	}
	private void advanceCursor()
	{
		cursor++;
		if (cursor >= expr.length) end_of_expr = true;
		else next_char = expr[cursor];	}

	private boolean isWhiteSpace(char c)
	{
		return ((c == ' ') || (c == '\t') || (c == '\n'));
	}

	private boolean isParen(char c)
	{
		return ((c == '(') || (c == ')'));
	}

	private boolean isOperator(char c)
	{
		return ((c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '~'));
	}

	private boolean isDigitOrDecimal(char c)
	{
		return (((c >= '0') && (c <= '9')) || (c == '.'));
	}
}