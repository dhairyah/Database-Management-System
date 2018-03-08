package edu.buffalo.www.cse4562;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.PrimitiveValue.InvalidPrimitive;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.schema.Column;

public class compare implements Comparator<ArrayList<PrimitiveValue>> {
	  int nn;
	    

	    public compare(int nn) {
	        this.nn = nn;
	        
	    } 
	@Override
   
	public int compare(ArrayList<PrimitiveValue> arg0, ArrayList<PrimitiveValue> arg1) {
		PrimitiveValue v1=arg0.get(nn);
		PrimitiveValue v2=arg1.get(nn);
		long vv1=0;
		long vv2=0;
		if(v1 instanceof LongValue)
		{
			 vv1=((LongValue)v1).toLong();
			 vv2=((LongValue)v2).toLong();
		}
		Eval eval = new Eval() {

			@Override
			public PrimitiveValue eval(Column arg0) throws SQLException {
				return null;
			}};
		PrimitiveValue result = null;
	
	    try {
			result = eval.eval( new GreaterThan(new  LongValue(vv1),new  LongValue(vv2)));
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
	   if(r==false)
	   {
		   return -1;
	   }
	   else
	   {
		   return 1;
	   }
		
		
	}


}
