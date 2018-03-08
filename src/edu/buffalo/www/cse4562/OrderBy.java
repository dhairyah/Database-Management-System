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

public class OrderBy extends Tuple implements RelationalAlgebra//,Comparator<Tuple>
{
	ArrayList<ArrayList<PrimitiveValue>> otable = new ArrayList<ArrayList<PrimitiveValue>>();
	//ArrayList<ArrayList<PrimitiveValue>> otable1 = new ArrayList<ArrayList<PrimitiveValue>>();
	//ArrayList<Double> tll=new ArrayList<Double>();
	//boolean[] b;
	
	//int tai;
	int nn;
	List<OrderByElement> element; 
	Tuple ne;
	@Override
	public boolean api(Tuple tupleobj) throws SQLException {
		// TODO Auto-generated method stub
		//otable.
//		System.out.println("Inorder:"+element);
		if(element!=null)
		{
			ne=tupleobj;
			nn=tupleobj.columnNames.indexOf(element.get(0).toString());
			System.out.println("n:"+nn);
			//System.out.println("ColumnNames:"+tupleobj.table.getColumnDefinitions().get(0).getColumnName());
			//System.out.println("Element:"+element);
			//System.out.println("Idnex:"+tupleobj.columnNames.indexOf(element.get(0).toString()));
		}	
		return true;
	}
	public void fun(Tuple tupleobj) throws InvalidPrimitive
	{
	/*	b= new boolean[otable.size()];
		int nn=tupleobj.columnNames.indexOf(element.get(0).toString());
		System.out.println("n:"+nn);
		System.out.println("Eke:"+element);
		System.out.println("Col:"+tupleobj.columnNames);
		for (int i=0;i<otable.size();i++)
		{
			  tll.add(otable.get(i).get(nn).toDouble());
		}
		System.out.println("tll:"+tll);
		List<Double> tll1=new ArrayList<Double>(tll);
		//tll1.add(63.0);
		Collections.sort(tll1);
		System.out.println("tll1:"+tll1);
		for(int i=0;i<tll.size();i++)
		{	
			 System.out.println("i:"+i);
		 System.out.println("Ind:"+tll.indexOf(tll1.get(i)));
		//otable1.add(new ArrayList<PrimitiveValue>());
		 int ind=tll.indexOf(tll1.get(i));
		 while(b[ind]==true)
		 {
		     //ind = tll.indexOf(tll1.get(i));
		     System.out.println("whilind:"+ind);
		     List<Double> sub= tll.subList(ind+1, tll.size());
             int second = sub.indexOf((tll1.get(i)));
             second=second+(tll.size()-sub.size());
             System.out.println("sec:"+second);
			 ind=second;
		 }
		 b[ind]=true;
		 otable1.add(otable.get(ind));
		} 
		 System.out.println("ot1:"+otable1);*/
		 Collections.sort(otable, new compare(nn));
		 System.out.println("AfterSort:"+otable);
	}
		

 	 
}
	
