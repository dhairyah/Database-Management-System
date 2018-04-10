package edu.buffalo.www.cse4562;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.PrimitiveValue.InvalidPrimitive;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.schema.Column;

public class CompareOverride implements Comparator<ArrayList<PrimitiveValue>> {
	int orderByIndex_1, orderByIndex_2;
	boolean isAsc;


	public CompareOverride(int index1, int index2, boolean isAsc) {
		this.orderByIndex_1 = index1;
		this.orderByIndex_2 = index2;
		this.isAsc = isAsc;

	} 
	@Override

	public int compare(ArrayList<PrimitiveValue> arg0, ArrayList<PrimitiveValue> arg1) {
		PrimitiveValue v1=arg0.get(orderByIndex_1);
		PrimitiveValue v2=arg1.get(orderByIndex_1);
		String temp_v1, temp_v2;
		int result_int;
		PrimitiveValue result_prim = null;
		Boolean r = null;

		Eval eval = new Eval() {

			@Override
			public PrimitiveValue eval(Column arg0) throws SQLException {
				return null;
			}};

			if(v1 instanceof StringValue)
			{
				temp_v1 = v1.toString();
				temp_v2 = v2.toString();
				result_int = temp_v1.compareTo(temp_v2);
				if(result_int == 0 && orderByIndex_2 != -1)
				{
					v1=arg0.get(orderByIndex_2);
					v2=arg1.get(orderByIndex_2);
					if(v1 instanceof StringValue)
					{
						temp_v1 = v1.toString();
						temp_v2 = v2.toString();
						result_int = temp_v1.compareTo(temp_v2);
						if(isAsc)
						{
							return result_int;
						}
						else
						{
							return result_int * (-1);
						}
					}
					else
					{
						try {
							result_prim = eval.eval( new GreaterThan(v1,v2));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							r = result_prim.toBool();
						} catch (InvalidPrimitive e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(isAsc)
						{
							if(r == false)
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
							if(r == false)
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
				if(isAsc)
				{
					return result_int;
				}
				else
				{
					return result_int * (-1);
				}
			}

			try {
				result_prim = eval.eval( new EqualsTo(v1,v2));
				try {
					r = result_prim.toBool();
				} catch (InvalidPrimitive e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(r == true && orderByIndex_2 != -1)
				{
					v1=arg0.get(orderByIndex_2);
					v2=arg1.get(orderByIndex_2);
					if(v1 instanceof StringValue)
					{
						temp_v1 = v1.toString();
						temp_v2 = v2.toString();
						result_int = temp_v1.compareTo(temp_v2);
						if(isAsc)
						{
							return result_int;
						}
						else
						{
							return result_int * (-1);
						}
					}
					else
					{
						try {
							result_prim = eval.eval( new GreaterThan(v1,v2));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							r = result_prim.toBool();
						} catch (InvalidPrimitive e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(isAsc)
						{
							if(r == false)
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
							if(r == false)
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
				else
				{
					result_prim = eval.eval( new GreaterThan(v1,v2));
				}
			} 
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				r = result_prim.toBool();
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
