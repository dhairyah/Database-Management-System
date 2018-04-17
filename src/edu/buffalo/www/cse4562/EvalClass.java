package edu.buffalo.www.cse4562;

import java.sql.SQLException;
import java.util.List;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.schema.Column;

public class EvalClass extends Eval{
	Tuple t;
	List<Column> colNamesChild;
	//int operator;
	
	EvalClass(Tuple tuple,List<Column> Schema)
	{
		this.t = tuple;
		this.colNamesChild = Schema;
		//this.operator = op;
	}

	@Override
	public PrimitiveValue eval(Column arg0) throws SQLException {
		if(t!=null)
		{
		int index = colNamesChild.indexOf(arg0);
		//below code changes is add to handle alias case. In case of alias, arg0's table name has the alias. So table name needs to be compared with alias.
		if(index == -1)
		{
			int size = colNamesChild.size();
			for(int it = 0; it < size; it++)
			{
				if((arg0.getTable().getName().equalsIgnoreCase(colNamesChild.get(it).getTable().getAlias())) && 
						(arg0.getColumnName().equalsIgnoreCase(colNamesChild.get(it).getColumnName())))
				{
					index = it;
					break;
				}
			}

		}
		return t.tuple.get(index);
		}
		else
		{
			return null;
		}

	}
}
