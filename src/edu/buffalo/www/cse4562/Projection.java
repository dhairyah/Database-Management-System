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
import java.nio.file.Files;
import java.nio.file.Paths;
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
		  int ts=t.table.getColumnDefinitions().size();
		  List<String> sl = new ArrayList<String>();
		  List<Integer> il = new ArrayList<Integer>();
		  List<PrimitiveValue> tempTuple = new ArrayList<PrimitiveValue>();
		  List<String> tempColumnNames = new ArrayList<String>();
			  for(int j=0;j<ts;j++)
			  {
				String tt= t.table.getColumnDefinitions().get(j).getColumnName();
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
						String columnName = arg0.getColumnName();
						String lowercolname = columnName.toLowerCase();
						//int index = sl.indexOf(columnName);
						int index = t.columnNames.indexOf(lowercolname);
						if(index >= 0)
							return t.tuple.get(index);
						else
						{
							System.out.println(lowercolname);
							try {
								Reader reader = Files.newBufferedReader(Paths.get("data//"+"prajin.dat"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							int test = 0;
							PrimitiveValue val = new LongValue(Long.valueOf(test));
							return val;
						}
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
						 tempColumnNames.add(alias.toLowerCase());
					 }
					 else
					 {
						 tempColumnNames.add(expr.toString().toLowerCase());
					 }
					 int lop = 2;
				 }
				 else if(i instanceof AllColumns || i instanceof AllTableColumns)
				 {
					 tempTuple.addAll(t.tuple);
				 }
				 
		  }
		  
		  t.tuple.clear();
		  t.tuple.addAll(tempTuple);
		  t.columnNames.clear();
		  t.columnNames.addAll(tempColumnNames);
			 
		  return true;
	 }
}
