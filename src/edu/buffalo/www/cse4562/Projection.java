package edu.buffalo.www.cse4562;
import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.parser.CCJSqlParser.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.select.SelectBody.*;
import net.sf.jsqlparser.statement.create.table.*;
import net.sf.jsqlparser.statement.create.table.*;
import java.lang.Object;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import net.sf.jsqlparser.parser.*;

public class Projection extends Tuple implements RelationalAlgebra 
{
	 //ItemsList ll;
	 public List<SelectItem> projection;
	 public boolean api(Tuple t) throws SQLException	
	 {
		  //System.out.println(projection);
		  int ps= projection.size();
		  //int ts=t.columnNames.size();
		  int ts = t.colNames.size();
		  Tuple X;
		  X = t;
		  List<String> sl = new ArrayList<String>();
		  List<Integer> il = new ArrayList<Integer>();
		  List<PrimitiveValue> tempTuple = new ArrayList<PrimitiveValue>();
		  List<Column> tempColumnNames = new ArrayList<Column>();
			  for(int j=0;j<ts;j++)
			  {
				//String tt= t.columnNames.get(j);
				String tt = t.colNames.get(j).getColumnName();
				sl.add(tt);
			  }
		  //System.out.println(sl);
		  for(int j=0;j<ps;j++)
		  {
             String tt=projection.get(j).toString();
             il.add(sl.indexOf(tt));
		  }
		  //System.out.println("sl = " + sl);
		  //System.out.println("il = " + il);
		  
		  for (int j = 0; j < ps; j++)
		  {
				 Eval eval = new Eval() {

					@Override
					public PrimitiveValue eval(Column arg0) throws SQLException {
						// TODO Auto-generated method stub
						//String columnName = arg0.getColumnName();
						//String lowercolname = columnName.toLowerCase();
						//int index = sl.indexOf(columnName);
						//int index = t.columnNames.indexOf(lowercolname);
						int index = t.colNames.indexOf(arg0);
						//below code changes is add to handle alias case. In case of alias, arg0's table name has the alias. So table name needs to be compared with alias.
						if(index == -1)
						{
							int size = t.colNames.size();
							for(int it = 0; it < size; it++)
							{
								if((arg0.getTable().getName().equalsIgnoreCase(t.colNames.get(it).getTable().getAlias())) && 
								   (arg0.getColumnName().equalsIgnoreCase(t.colNames.get(it).getColumnName())))
								{
									index = it;
									break;
								}
							}
						}
						return t.tuple.get(index);
						
					}
				 };
				 SelectItem i = projection.get(j);
				 if(i instanceof SelectExpressionItem)
				 {
					 SelectExpressionItem k = (SelectExpressionItem)i;
					 String alias = k.getAlias();
					 
					 Expression expr = k.getExpression();
					 PrimitiveValue type = eval.eval(expr);
					 tempTuple.add(type);
					 if(alias != null)
					 {
						 //need to modify schema;
						 //int test = 0;
						 tempColumnNames.add((Column)expr);
					 }
					 else
					 {
						 tempColumnNames.add((Column)expr);
					 }
					 int lop = 2;
				 }
				 else if(i instanceof AllColumns )
				 {
					 tempTuple.addAll(t.tuple);
					 tempColumnNames.addAll(X.colNames);
				 }
				 else if (i instanceof AllTableColumns)
				 {
					 AllTableColumns tab = (AllTableColumns) i;
					 Table tab_name = tab.getTable();
					 int numCols = t.colNames.size();
					 for(int ind = 0; ind < numCols; ind++)
					 {
						 if((t.colNames.get(ind).getTable().getName().equalsIgnoreCase(tab_name.getName())) || (t.colNames.get(ind).getTable().getAlias().equalsIgnoreCase(tab_name.getName())))
						 {
							 PrimitiveValue type = eval.eval(t.colNames.get(ind));
							 tempTuple.add(type);
							 tempColumnNames.add(t.colNames.get(ind));
						 }
					 }
					 
				 }
				 
		  }
		  
		  t.tuple.clear();
		  t.tuple.addAll(tempTuple);
		  t.colNames.clear();
		  t.colNames.addAll(tempColumnNames);
			 
		  return true;
	 }
}
