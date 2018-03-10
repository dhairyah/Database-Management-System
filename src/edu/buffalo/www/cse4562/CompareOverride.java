package edu.buffalo.www.cse4562;
 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
 
import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.PrimitiveValue.InvalidPrimitive;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.schema.Column;
 
public class CompareOverride implements Comparator<ArrayList<PrimitiveValue>> {
	  int nn;
	  boolean isAsc;
 
 
	    public CompareOverride(int nn, boolean isAsc) {
	        this.nn = nn;
	        this.isAsc = isAsc;
 
	    } 
	@Override
 
	public int compare(ArrayList<PrimitiveValue> arg0, ArrayList<PrimitiveValue> arg1) {
		PrimitiveValue v1=arg0.get(nn);
		PrimitiveValue v2=arg1.get(nn);
		/*long vv1=0;
		long vv2=0;
		if(v1 instanceof LongValue)
		{
			 vv1=((LongValue)v1).toLong();
			 vv2=((LongValue)v2).toLong();
		}*/
		if(v1 instanceof StringValue)
		{
			String temp_v1 = v1.toString();
			String temp_v2 = v2.toString();
			int result = temp_v1.compareTo(temp_v2);
			if(isAsc)
			{
				if(result > 0)
					return 1;
				else
					return -1;
			}
			else
			{
				if(result > 0)
					return -1;
				else
					return 1;
			}
		}
		Eval eval = new Eval() {
 
			@Override
			public PrimitiveValue eval(Column arg0) throws SQLException {
				return null;
			}};
		PrimitiveValue result = null;
 
	    try {
			result = eval.eval( new GreaterThan(v1,v2));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
		Boolean r = null;
		try {
			r = result.toBool();
		} catch (InvalidPrimitive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isAsc)
		{
			if(r==false)
		   {
			   return -1;
		   }
		   else
		   {
			   return 1;
		   }
		}
		else
		{
			if(r==false)
		   {
			   return 1;
		   }
		   else
		   {
			   return -1;
		   }
		}
 
 
 
	}
 
 
}