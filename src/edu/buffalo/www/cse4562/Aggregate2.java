package edu.buffalo.www.cse4562;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.PrimitiveValue.InvalidPrimitive;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class Aggregate2 extends RelationalAlgebra2 {
	
	List<Column> groupByColumns = new ArrayList<Column>();
	List<Function> aggrFunctions = new ArrayList<Function>();
	List<Integer> groupByIndex = new ArrayList<Integer>();
	List<Integer> functionIndex = new ArrayList<Integer>(); 
	HashMap<String,  ArrayList<Tuple>> hashAggr;
//	HashMap<String,  Long> hashSum=new HashMap<>();
	Iterator<String> hashItr;
	String groupByColVals="";
	Integer init=0;

	@Override
	boolean api(Tuple tupleobj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	List<Column> open() throws IOException {
		colNamesChild = leftChild.open();
		for(int i=0;i<colNamesChild.size();i++)
		{
			for(int j=0;j<groupByColumns.size();j++)
			{
				if(colNamesChild.get(i).getColumnName().equalsIgnoreCase(groupByColumns.get(j).getColumnName()))
				{
					groupByIndex.add(i);
				}
			}
		}
		return colNamesParent;
	}

	@Override
	void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	Tuple  retNext() throws SQLException {
		if(init==0)
		{
			hashAggr=new HashMap<String, ArrayList<Tuple>>();
			 while(leftChild.hasNext())
			{
				Tuple retTuple = new Tuple();
				retTuple.tuple.addAll(leftChild.retNext().tuple);
				for(int i=0;i<groupByIndex.size();i++)
				{
					groupByColVals = groupByColVals+retTuple.tuple.get(groupByIndex.get(i))+"||";
				}
				
				if(hashAggr.containsKey(groupByColVals))
				{
					hashAggr.get(groupByColVals).add(retTuple);
					
				}
				else	
				{
					ArrayList<Tuple> tupleList = new ArrayList<Tuple>();
					tupleList.add(retTuple);
					hashAggr.put(groupByColVals, tupleList);
				}
	
				groupByColVals="";
				
			}
		init=1;
		hashItr = hashAggr.keySet().iterator();
		}
		String keyVal="";
		List<Tuple> groupByTuples =new ArrayList<Tuple>();
		while(hashItr.hasNext())
		{
			keyVal = hashItr.next();
			groupByTuples = hashAggr.get(keyVal);
			//List<PrimitiveValue> aggrResults = new ArrayList<PrimitiveValue>();
			Tuple retTuple=new Tuple();
			
			for(int i=0;i<colNamesParent.size();i++)
			{
				if(groupByIndex.contains(i))
				{
					retTuple.tuple.add(groupByTuples.get(0).tuple.get(i));
				}
				else
				{
					if(aggrFunctions.get(functionIndex.indexOf(i)).getName().equalsIgnoreCase("sum"))
					{
						retTuple.tuple.add(getSumAggr(groupByTuples,functionIndex.get(functionIndex.indexOf(i))));
					}
				}
			}
				
			
						
			
			return retTuple;
			
		}
		return null;
	}

	@Override
	boolean hasNext() throws SQLException {
		return false; //since it is a blocking operator
	}

	@Override
	void reset() {
		leftChild.reset();
		
	}
	
	PrimitiveValue getSumAggr(List<Tuple> allTuples,Integer aggrIndex) throws InvalidPrimitive
	{
		Iterator<Tuple> tupleItr = allTuples.iterator();
		long longSum=0;
		double doubleSum=0.0; 
		int retType=-1; // 0 for long and 1 for double
		while(tupleItr.hasNext())
		{
			PrimitiveValue val = tupleItr.next().tuple.get(aggrIndex);
			if(val instanceof LongValue)
			{
				longSum=longSum+val.toLong();
				retType=0;
			}
			else if(val instanceof DoubleValue)
			{
				doubleSum = doubleSum+val.toDouble();
				retType=1;
			}
			
		}
		
		if(retType==0)
		{
			PrimitiveValue d = new LongValue(longSum);
			return d;
		}
		else
		{
			PrimitiveValue d = new DoubleValue(doubleSum);
			return d;
		}
		
	}

}
