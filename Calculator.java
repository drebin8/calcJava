/**
 * Calculator.java - Calculator interface
 * COMP141 - Spring 2013
 * Author: Neil Panchal
 * 
 * This interface currently only runs the scanner/tokenizer 
 * module of the calculator and prints the resulting token list 
 * to the console.
 * 
 * The program presents a prompt, at which the user is expected to type
 * an expression on a single line. The program terminates when the
 * user types a single carriage return (Enter key) at the prompt.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Calculator
{
	private static void printTokenList(List<Token> tokens)
	{
		Iterator<Token> iter = tokens.iterator();
		while (iter.hasNext() )
		{
			Token current = (Token) iter.next();
			switch(current.getType())
			{
			case NUMBER_TOKEN:
				System.out.println("number:\t" + ((NumberToken)current).getValue() + "\n");
				break;
			case OPERATOR_TOKEN:
				System.out.println("oper:\t" + ((OperatorToken)current).getValue() + "\n");
				break;
			case PAREN_TOKEN:
				System.out.println("paren:\t" + ((ParenToken)current).getValue() + "\n");
				break;
			default:
				System.out.println("invalid token");
			}
		}
	}
	
	public static void printExpressionTree(ExpressionTree root, int spaces)
	{
		if ((root.nodeType() == ExprTreeType.OPERATOR_NODE) && (root.rightSubtree() != null))
			printExpressionTree(root.rightSubtree(), spaces+5);

		for (short i=0; i<spaces; i++)
			System.out.println(" ");
		if (root.nodeType() == ExprTreeType.OPERATOR_NODE)
			System.out.println("[" + ((ExprTreeOperatorNode)root).getOperator() + "]");
		else if (root.nodeType() == ExprTreeType.NUMBER_NODE)
			System.out.println("<" + ((ExprTreeNumberNode)root).getValue() + ">");
		
		if ((root.nodeType() == ExprTreeType.OPERATOR_NODE) && (root.leftSubtree() != null))
			printExpressionTree(root.leftSubtree(), spaces+5);
	}


	public static void main(String[] args)
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String line = new String();
		while (true)
		{
			System.out.println("CALCULATE: ");
			try { line = br.readLine(); }
			catch(IOException e)
			{
				System.out.println("BAD INPUT");
			}
			//if (line.equals("")) break;
			
			if (line.length() == 0) break;

			try
			{
				Scanner s = new Scanner();
				List<Token> tokens = s.parseExpression(line);
				printTokenList(tokens);
				
				Parser p = new Parser();
				ExpressionTree tree = Parser.parse(tokens);
				//printExpressionTree(tree);
				
				Evaluator e = new Evaluator();
				double value = e.evaluate(tree);
				System.out.println("RESULT: " + value + "\n");
			}
			catch (CalcException ce)
			{
				System.out.println("Calculator error: " + ce.getMessage() + "\n");
			}
		}
	}
}