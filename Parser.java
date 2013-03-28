/**
 * Parser.java - Parser class for a calculator.
 * COMP141 - Spring 2013
 * Author: Neil Panchal
 * 
 * Calculator Grammar:
 * Expr := Term { '+' | '-' Term }
 * Factor := Number | '~' Number | '(' Expr ') '
 * 
 * Note: ~number is used for negative numbers (as in ML)
 */
import java.util.*;

public class Parser {

	private static List<Token> tokens;
	private static int token_iter;
	private static Token curr_token;
	
	public static ExpressionTree parse(List<Token> _tokens) throws CalcException
	{
		tokens = _tokens;
		token_iter = 0;
		if (token_iter == tokens.size()) 
			throw new CalcException("Parser received an empty token list.");
		curr_token = tokens.get(token_iter);
		ExpressionTree etree = parseExpression();
		if (curr_token != null) 
			throw new CalcException("Unconsumed tokens at end of expression.");
		return etree;
	}

	public static void consumeToken()
	{
		if (token_iter != tokens.size())
			token_iter++;
		if (token_iter == tokens.size())
			curr_token = null;
		else curr_token = tokens.get(token_iter);
	}

	public static ExpressionTree parseExpression() throws CalcException
	{
		ExpressionTree expr = parseTerm();
		while ((curr_token != null) &&
			   (curr_token.getType() == TokenType.OPERATOR_TOKEN) && 
			   ((((OperatorToken)curr_token).getValue() == '+') || 
			    (((OperatorToken)curr_token).getValue() == '-')))
		{
			char oper = ((OperatorToken)curr_token).getValue();
			consumeToken();
			expr = new ExprTreeOperatorNode(oper, expr, parseTerm());
		}
		return expr;
	}

	public static ExpressionTree parseTerm() throws CalcException
	{
		ExpressionTree term = parseFactor();
		while ((curr_token != null) &&
			   (curr_token.getType() == TokenType.OPERATOR_TOKEN) && 
			   ((((OperatorToken)curr_token).getValue() == '*') || 
			    (((OperatorToken)curr_token).getValue() == '/')))
		{
			char oper = ((OperatorToken)curr_token).getValue();
			consumeToken();
			term = new ExprTreeOperatorNode(oper, term, parseFactor());
		}
		return term;
	}

	public static ExpressionTree parseFactor() throws CalcException
	{
		if (curr_token == null) 
			throw new CalcException("Reached end of tokens while expecting a factor.");
		ExpressionTree factor = null;
		if ((curr_token.getType() == TokenType.PAREN_TOKEN) && (((ParenToken)curr_token).getValue() == '('))
		{
			consumeToken();
			factor = parseExpression();
			if ((curr_token == null) || 
				(curr_token.getType() != TokenType.PAREN_TOKEN) || (((ParenToken)curr_token).getValue() != ')'))
				throw new CalcException("Badly formed parenthesized expression");
			consumeToken();
		}
		else if ((curr_token.getType() == TokenType.OPERATOR_TOKEN) && (((OperatorToken)curr_token).getValue() == '~'))
		{
			consumeToken();
			ExpressionTree sub_expr = parseNumber();
			factor = new ExprTreeOperatorNode('~', null, sub_expr);
		}
		else 
		{
			factor = parseNumber();
		}
		return factor;
	}

	public static ExpressionTree parseNumber() throws CalcException
	{
		if (curr_token == null) 
			throw new CalcException("Reached end of tokens while expecting a number.");
		if (curr_token.getType() == TokenType.NUMBER_TOKEN)
		{
			double value = ((NumberToken)curr_token).getValue();
			consumeToken();
			return new ExprTreeNumberNode(value);
		}
		else
		{
			throw new CalcException("Expected a number.");
		}
	}

	
}
