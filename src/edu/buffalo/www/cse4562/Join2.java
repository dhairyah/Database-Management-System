package edu.buffalo.www.cse4562;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.schema.Column;

public class Join2 extends RelationalAlgebra2{

	public Tuple current_left_tuple;
	@Override
	boolean api(Tuple tupleobj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	Tuple retNext() throws SQLException {
		
		Tuple tupleobj = new Tuple();
		
		Tuple rightTuple = new Tuple();			
		
		while (true)
		{
			
			if(current_left_tuple == null)
			{
				current_left_tuple = leftChild.retNext();
				if(current_left_tuple==null)
				{
					break;
				}
			}
 
			while((rightTuple=rightChild.retNext())!=null)
			{
				tupleobj.tuple.clear();
				tupleobj.colNames.clear();
				tupleobj.tuple.addAll(current_left_tuple.tuple);
				//tupleobj.colNames.addAll(current_left_tuple.colNames);
				tupleobj.tuple.addAll(rightTuple.tuple);
				//tupleobj.colNames.addAll(rightTuple.colNames);

				return tupleobj;
			}
			current_left_tuple = null;
			rightChild.reset();
		}
			
			
		return null;
		
		
	}
	
	@Override
	List<Column> open() throws IOException {
		
		List<Column> leftChildCols = new ArrayList<Column>();
		List<Column> rightChildCols = new ArrayList<Column>();
		
		leftChildCols = leftChild.open();
		rightChildCols = rightChild.open();
		colNamesChild.addAll(leftChildCols);
		colNamesChild.addAll(rightChildCols);
		colNamesParent.addAll(colNamesChild);
		return colNamesParent;
	}

	@Override
	void close() {
		// TODO Auto-generated method stub
		
	}


	@Override
	boolean hasNext() throws SQLException {
		return leftChild.hasNext();
	}


	@Override
	void reset() {
		leftChild.reset();
		
	}


}