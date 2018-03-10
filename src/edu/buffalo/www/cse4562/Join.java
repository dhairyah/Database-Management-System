package edu.buffalo.www.cse4562;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class Join implements RelationalAlgebra{

	public RelationalAlgebra node1, node2;
	private Tuple current_left_tuple;
	@Override
	public boolean api(Tuple tupleobj) throws SQLException {
		// TODO Auto-generated method stub
		if(node1 instanceof Scan && node2 instanceof Scan)
		{
 
			Scan scan1 = (Scan)node1;
			Scan scan2  = (Scan)node2;
			Tuple rightTuple = new Tuple();
			
			
			
			
			while ((current_left_tuple != null) || scan1.hasNext())
			{
				//System.out.println("this: " +scan1.hasNext());
				if(current_left_tuple == null)
				{
				//	System.out.println("aaglu ayu");
					current_left_tuple = scan1.retNext();
				//	System.out.println("aaglu ayur : " +current_left_tuple.record.toString());
				}
 
				while(scan2.hasNext())
				{
					//System.out.println("aaglu ret ayu");
					rightTuple = scan2.retNext();
				//	System.out.println("aaglu ret ayu : "+ rightTuple.record.toString());
					tupleobj.tuple.clear();
					tupleobj.columnNames.clear();
					tupleobj.tuple.addAll(current_left_tuple.tuple);
					tupleobj.columnNames.addAll(current_left_tuple.columnNames);
					tupleobj.tuple.addAll(rightTuple.tuple);
					tupleobj.columnNames.addAll(rightTuple.columnNames);
					return true;
				}
				current_left_tuple = null;
				try {
					scan2.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
		}
		return false;
	}

}