package edu.buffalo.www.cse4562;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.parser.CCJSqlParser.*;
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
	 public boolean api(Tuple t)
	 {
		  String s1="Jio";
		  System.out.println(projection);
		  int ps= projection.size();
		  int ts=t.table.getColumnDefinitions().size();
		  List<String> sl = new ArrayList<String>();
		  List<Integer> il = new ArrayList<Integer>();
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
		  System.out.println(sl);
		  System.out.println(il);
		  return true;
	 }
}
