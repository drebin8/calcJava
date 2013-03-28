/**
 * ExpressionTree.java - Hierarchy of expression tree nodes for a calculator.
 * COMP141 - Spring 2013
 * Author: Neil Panchal
 */	

enum ExprTreeType { OPERATOR_NODE, NUMBER_NODE, NULL };

public class ExpressionTree
{

	public ExprTreeType nodeType(){return ExprTreeType.NULL;};
	public ExpressionTree leftSubtree(){return null;};
	public ExpressionTree rightSubtree(){return null;};

}

class ExprTreeOperatorNode extends ExpressionTree
{
	public ExprTreeOperatorNode(char _oper, ExpressionTree _left, ExpressionTree _right)
	{
		oper = _oper;
		left_child = _left;
		right_child = _right;
	}
	public char getOperator() { return oper; }
	public ExprTreeType nodeType() { return ExprTreeType.OPERATOR_NODE; }
	public ExpressionTree leftSubtree() { return left_child; }
	public ExpressionTree rightSubtree() { return right_child; }

	private char oper;
	private ExpressionTree left_child; 
	private ExpressionTree right_child; 
}

class ExprTreeNumberNode extends ExpressionTree
{
	public ExprTreeNumberNode(double _value) { value = _value; }
	public double getValue() { return value; }
	public ExprTreeType nodeType() { return ExprTreeType.NUMBER_NODE; }
	public ExpressionTree leftSubtree() { return null; }
	public ExpressionTree rightSubtree() { return null; }
	private double value;
}





