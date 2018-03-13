package edu.buffalo.www.cse4562;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.PrimitiveValue.InvalidPrimitive;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.PrimitiveType;
import net.sf.jsqlparser.statement.select.OrderByElement;

public class OrderBy extends Tuple implements RelationalAlgebra
{
	ArrayList<ArrayList<PrimitiveValue>> otable = new ArrayList<ArrayList<PrimitiveValue>>();

	int orderByIndex, tai =0;
	List<OrderByElement> element; 
	Tuple ne;
	public String subQuery_alias;
	@Override
	public boolean api(Tuple tupleobj) throws SQLException {

		
		otable.add(new ArrayList<PrimitiveValue>());
		orderByIndex=tupleobj.colNames.indexOf((Column) element.get(0).getExpression()); 

		for(int i = 0; i < tupleobj.tuple.size(); i++)
		{
			 otable.get(tai).add(tupleobj.tuple.get(i));  
			 
		}
		tai++;
			
		return true;
	}
	public void sortAndPrint() throws InvalidPrimitive
	{

		 Collections.sort(otable, new CompareOverride(orderByIndex,element.get(0).isAsc()));
		 
		 for(int i=0;i<otable.size();i++)
		 {
			 for(int j = 0; j < otable.get(i).size() - 1; j++)
			 {
				System.out.print(otable.get(i).get(j) + "|");
			 }
			 System.out.println(otable.get(i).get(otable.get(i).size() - 1));
		 }
	}
		

 	 
}