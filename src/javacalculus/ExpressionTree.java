package javacalculus;

import java.util.ArrayList;

public class ExpressionTree 
{
	private Node head;
	
	public ExpressionTree (String expression)
	{
		makeMyTree(expression);
	}
	
	public ExpressionTree (ArrayList<String> expression)
	{	
		StringBuffer buffer = new StringBuffer();
		for (String element: expression)
			buffer.append(element);
		makeMyTree(buffer.toString());
	}
	
	public ExpressionTree (String[] expression)
	{	
		StringBuffer buffer = new StringBuffer();
		for (String element: expression)
			buffer.append(element);
		makeMyTree(buffer.toString());
	}
	
	private Node makeMyTree(String expression)
	{
	
		Node current=head;
		for (int i=0; i<expression.length(); i++)
		{
			char a=expression.charAt(i);
			char prev=expression.charAt(i-1);
			//Farms out parentheses recursively
			if(a=='(')
			{
				int leftCounter=0;
				for (int j=i+1;j<expression.length();j++)
				{
					char b=expression.charAt(j);
					if(b=='(')
						leftCounter++;
					else if (b==')')
					{
						if (leftCounter==0)
						{
							current=makeMyTree(expression.substring(i+1,j-1));
							break;
						}
						else
							leftCounter--;
					}
				}
			}
			//makes the bottom double
			if(Character.isDigit(a)||a=='.')
			{
				StringBuffer number=new StringBuffer();
				number.append(a);
				for (int j=i+1;j<expression.length();j++)
				{
					char b=expression.charAt(j);
					if(Character.isDigit(b)||b=='.')
						number.append(b);
					else
					{
						current=new Node(number.toString(),current,null,null);
						break;
					}
				}
			}
			
			
			if (a=='+'||a=='*'||a=='/'||a=='%'||(a=='-'&&(prev=='+'||prev=='*'||prev=='/'||prev=='%')))
			{
				current=new Node(""+a,current,makeMyTree(expression.substring(0,i-1)),
						makeMyTree(expression.substring(i+1,expression.length()-1)));
			}
			
			
		}
		return null;
		
	}
	

private class Node
{
	private String myValue;
	private Node left;
	private Node right;
	private Node parent;
	
	public Node (String value, Node parent, Node left, Node right)
	{
		myValue=value;
		this.left=left;
		this.right=right;
		this.parent=parent;
	}
	
	public Node getLeft()
	{	return left;	}
	
	public Node getRight()
	{	return right;	}
	
	public Node getParent()
	{	return parent;	}
	
	public void setParent(Node p)
	{	parent=p;	}
	
	public void setLeft(Node l)
	{	left=l;	}
	
	public void setRight(Node r)
	{	right=r;	}
	
	public double eval()
	{
		if (left==null&&right==null) //if a constant
		{
			if (myValue.equalsIgnoreCase("PI"))
				return Math.PI;
			else if (myValue.equalsIgnoreCase("e"))
				return Math.E;
			else
				return Double.parseDouble(myValue);
		}
		
		else if (left!=null&&right!=null) //binary operators
			{
			if (myValue.charAt(0)=='+')
				return left.eval()+right.eval();
			if (myValue.charAt(0)=='-')
					return left.eval()-right.eval();
			if (myValue.charAt(0)=='*')
				return left.eval()*right.eval();
			if (myValue.charAt(0)=='/')
				return left.eval()/right.eval();
			if (myValue.charAt(0)=='^')
				return Math.pow(left.eval(),right.eval());
			if (myValue.charAt(0)=='%')
				return left.eval()%right.eval();
			}
		else //unary operators
		{
			if (myValue.charAt(0)=='-')
				return -(left.eval());
			if (myValue.equalsIgnoreCase("abs"))
				return (Math.abs(left.eval()));
			if (myValue.equalsIgnoreCase("sqrt"))
				return (Math.sqrt(left.eval()));
			
			//trigs, including inverses, hyperbolics
			if (myValue.equalsIgnoreCase("tan"))
				return (Math.tan(left.eval()));
			if (myValue.equalsIgnoreCase("sin"))
				return (Math.sin(left.eval()));
			if (myValue.equalsIgnoreCase("cos"))
				return (Math.cos(left.eval()));
			if (myValue.equalsIgnoreCase("cot"))
				return (1/Math.tan(left.eval()));
			if (myValue.equalsIgnoreCase("sec"))
				return (1/Math.cos(left.eval()));
			if (myValue.equalsIgnoreCase("csc"))
				return (1/Math.sin(left.eval()));
			if (myValue.equalsIgnoreCase("arctan"))
				return (Math.atan(left.eval()));
			if (myValue.equalsIgnoreCase("atan"))
				return (Math.atan(left.eval()));
			if (myValue.equalsIgnoreCase("arcsin"))
				return (Math.asin(left.eval()));
			if (myValue.equalsIgnoreCase("asin"))
				return (Math.asin(left.eval()));
			if (myValue.equalsIgnoreCase("tan"))
				return (Math.tan(left.eval()));
			if (myValue.equalsIgnoreCase("arccos"))
				return (Math.acos(left.eval()));
			if (myValue.equalsIgnoreCase("acos"))
				return (Math.acos(left.eval()));
			if (myValue.equalsIgnoreCase("tanh"))
				return (Math.tanh(left.eval()));
			if (myValue.equalsIgnoreCase("cosh"))
				return (Math.cosh(left.eval()));
			if (myValue.equalsIgnoreCase("sinh"))
				return (Math.sinh(left.eval()));
		}
		return 0;
	}
	
	
}



}