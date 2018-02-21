package edu.buffalo.www.cse4562;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.schema.Column;
public class Selection extends Tuple implements RelationalAlgebra 
{
  public Expression expression;	
  public boolean api(Tuple tupleobj) throws SQLException
  {
	  //String s1="selection";
	  //System.out.println(s1);
	  List<String> sl = new ArrayList<String>();
	  int ts=tupleobj.table.getColumnDefinitions().size();
	  for(int j=0;j<ts;j++)
	  {
		String tt= tupleobj.table.getColumnDefinitions().get(j).getColumnName();
		sl.add(tt);
	  }
	  
	  Eval eval = new Eval() {
		  @Override
		  public PrimitiveValue eval(Column arg0) throws SQLException {
			  // TODO Auto-generated method stub
			  String columnName = arg0.getColumnName();
			  int index = sl.indexOf(columnName);
			  return tupleobj.tuple.get(index);
			  }
		  };
	  PrimitiveValue type = eval.eval(expression);
	  return type.toBool();
  }
}
