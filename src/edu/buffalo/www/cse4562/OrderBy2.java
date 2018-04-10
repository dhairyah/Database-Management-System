package edu.buffalo.www.cse4562;

import java.io.IOException;
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

public class OrderBy2 extends RelationalAlgebra2
{

	ArrayList<ArrayList<PrimitiveValue>> otable = new ArrayList<ArrayList<PrimitiveValue>>();

	int  tai =0;
	int orderByIndex_1, orderByIndex_2;
	List<OrderByElement> element; 
	Tuple ne;
	public String subQuery_alias;	
	 

	@Override
	boolean api(Tuple tupleobj) throws SQLException {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	List<Column> open() throws IOException {
		// TODO Auto-generated method stub
		colNamesChild = leftChild.open();
		colNamesParent.addAll(colNamesChild);
		return colNamesParent;
	}

	@Override
	void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	Tuple retNext() throws SQLException {

	    Tuple tupleobj = leftChild.retNext();
		if(tupleobj!=null)
		{
		   	
			otable.add(new ArrayList<PrimitiveValue>());
			orderByIndex_1 = colNamesChild.indexOf((Column) element.get(0).getExpression());
			//System.out.println("tuple.col"+tupleobj.colNames);
			//System.out.println("OXXX:"+(Column) element.get(0).getExpression());
			if(element.size() > 1)
				orderByIndex_2 = colNamesChild.indexOf((Column) element.get(1).getExpression());
			else
				orderByIndex_2 = -1;

			for(int i = 0; i < tupleobj.tuple.size(); i++)
			{
				 otable.get(tai).add(tupleobj.tuple.get(i));  
				 
			}
			tai++;
				
		// System.out.println("OOO:"+otable);	
		}
		return tupleobj;
	}
	//public void sortAndPrint(int ll) throws InvalidPrimitive
	public void sortAndPrint(int ll) throws InvalidPrimitive
	{
         int c=0;
         //System.out.println("o1:"+orderByIndex_1);
         //System.out.println("o2:"+orderByIndex_2);
         //System.out.println("orde:"+element.get(0));
		 Collections.sort(otable, new CompareOverride(orderByIndex_1, orderByIndex_2, element.get(0).isAsc()));
		 
		 for(int i=0;i<otable.size();i++)
		 {
		     if(c==ll) {break;}
			 for(int j = 0; j < otable.get(i).size() - 1; j++)
			 {
				System.out.print(otable.get(i).get(j) + "|");
			 }
			 System.out.println(otable.get(i).get(otable.get(i).size() - 1));
			 c++;
		 }
	}

	@Override
	boolean hasNext() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	void reset() {
		// TODO Auto-generated method stub
		
	}

 	 
}
