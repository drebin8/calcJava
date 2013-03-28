/**
 * Evaluator.cpp - Evaluator class for a calculator.
 * COMP141 - Spring 2013
 * Author: Neil Panchal
 */	

public class Evaluator {

	double evaluate(ExpressionTree root)
	{
		if (root.nodeType() == ExprTreeType.NUMBER_NODE) 
			return ((ExprTreeNumberNode)root).getValue();
		char oper = ((ExprTreeOperatorNode)root).getOperator();
		if (oper == '~')
		{
			return -1.0*evaluate(root.rightSubtree());
		}
		else 
		{
			double lhs = evaluate(root.leftSubtree());
			double rhs = evaluate(root.rightSubtree());
			switch (oper)
			{
			case '+': return lhs + rhs;
			case '-': return lhs - rhs;
			case '*': return lhs * rhs;
			case '/': return lhs / rhs;
			}
		}
		return 0.0;
	
	}
}
