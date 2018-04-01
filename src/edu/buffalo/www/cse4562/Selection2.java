package edu.buffalo.www.cse4562;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.BooleanValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

public class Selection2 extends RelationalAlgebra2{
	
	public Expression expression;	

	@Override
	boolean api(Tuple tupleobj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	Tuple retNext() throws SQLException {
		Tuple tupleobj = leftChild.retNext();
		if(tupleobj == null)
		{
			return null;
		}

		Eval eval = new Eval() {
			@Override
			public PrimitiveValue eval(Column arg0) throws SQLException {
				int index = colNamesChild.indexOf(arg0);
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
				return tupleobj.tuple.get(index);
			}

		};
		//System.out.println("exp:"+expression);	  
		PrimitiveValue type = eval.eval(expression);
		if(type.toBool() == true)
		{
			return tupleobj;
		}
		else
		{
			Tuple tp = this.retNext();
			return tp;
		}
	}

	@Override
	List<Column> open() throws IOException {
		colNamesChild = leftChild.open();
		colNamesParent.addAll(colNamesChild);
		return colNamesParent;
	}

	@Override
	void close() {
		// TODO Auto-generated method stub
		
	}

}
