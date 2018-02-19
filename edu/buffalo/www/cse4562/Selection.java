package edu.buffalo.www.cse4562;
import net.sf.jsqlparser.expression.*;
public class Selection extends Tuple implements RelationalAlgebra 
{
  public Expression expression;	
  public boolean api(Tuple tupleobj)
  {
	  String s1="selection";
	  System.out.println(s1);
	  return true;
  }
}
