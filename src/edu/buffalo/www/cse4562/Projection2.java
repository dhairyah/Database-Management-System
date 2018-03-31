package edu.buffalo.www.cse4562;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class Projection2 extends RelationalAlgebra2{

	public List<SelectItem> projection;
	public String subQuery_alias="";
	
	@Override
	boolean api(Tuple tupleobj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	List<Column> open() throws IOException {
		List<Column> cn=null;
		cn= Colu
		return null;
	}

	@Override
	void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	Tuple retNext() throws SQLException {
		int ps= projection.size();
		Tuple t = leftChild.retNext();
		int ts = t.colNames.size();
		Tuple X;
		X = t;
		List<String> sl = new ArrayList<String>();
		//List<Integer> il = new ArrayList<Integer>();
		List<PrimitiveValue> tempTuple = new ArrayList<PrimitiveValue>();
		List<Column> tempColumnNames = new ArrayList<Column>();
		for(int j=0;j<ts;j++)
		{
			String tt = t.colNames.get(j).getColumnName();
			sl.add(tt);
		}

		for (int j = 0; j < ps; j++)
		{
			Eval eval = new Eval() {

				@Override
				public PrimitiveValue eval(Column arg0) throws SQLException {
					int index = t.colNames.indexOf(arg0);
					//below code changes is add to handle alias case. In case of alias, arg0's table name has the alias. So table name needs to be compared with alias.
					if(index == -1)
					{
						int size = t.colNames.size();
						for(int it = 0; it < size; it++)
						{
							if((arg0.getTable().getName().equalsIgnoreCase(t.colNames.get(it).getTable().getAlias())) && 
									(arg0.getColumnName().equalsIgnoreCase(t.colNames.get(it).getColumnName())))
							{
								index = it;
								break;
							}
						}

					}
					return t.tuple.get(index);

				}
			};
			SelectItem i = projection.get(j);
			if(i instanceof SelectExpressionItem)
			{
				// System.out.println("selexpr");
				SelectExpressionItem k = (SelectExpressionItem)i;
				String alias = k.getAlias();

				Expression expr = k.getExpression();
				// System.out.println("sel :"+expr);
				PrimitiveValue type = eval.eval(expr);

				tempTuple.add(type);
				if(alias != null)
				{
					//need to modify schema;
					//int test = 0;
					Column col = new Column();
					col.setColumnName(alias);
					Table tab_temp = new Table();
					col.setTable(tab_temp);
					tempColumnNames.add((Column)col);
				}
				else
				{
					tempColumnNames.add((Column)expr);
				}
				int lop = 2;
			}
			else if(i instanceof AllColumns )
			{
				//	 System.out.println("saalc");
				tempTuple.addAll(t.tuple);
				tempColumnNames.addAll(X.colNames);
			}
			else if (i instanceof AllTableColumns)
			{
				//	 System.out.println("alltabc");
				AllTableColumns tab = (AllTableColumns) i;
				Table tab_name = tab.getTable();
				int numCols = t.colNames.size();
				for(int ind = 0; ind < numCols; ind++)
				{

					// System.out.println("inside for");
					if(t.colNames.get(ind).getTable().getAlias() != null)
					{
						// System.out.println("pehif+ pec : "+tab_name.getName()+" and tabname +"+ t.colNames.get(ind).getTable().getName()+" and ali+"+t.colNames.get(ind).getTable().getAlias());

						if((t.colNames.get(ind).getTable().getName().equalsIgnoreCase(tab_name.getName())) || (t.colNames.get(ind).getTable().getAlias().equalsIgnoreCase(tab_name.getName())))
						{

							// System.out.println("beef");
							PrimitiveValue type = eval.eval(t.colNames.get(ind));
							tempTuple.add(type);
							Table temp_tab = new Table(t.colNames.get(ind).getTable().getName());
							temp_tab.setAlias(t.colNames.get(ind).getTable().getAlias());
							Column temp = new Column(temp_tab, t.colNames.get(ind).getColumnName());
							tempColumnNames.add(temp);

						}
					}

				}
				//tempColumnNames.addAll(X.colNames); 
			}

		}

		if(subQuery_alias != null && !subQuery_alias.isEmpty()) //Changes 3/15
		{
			int size = tempColumnNames.size();
			for(int i = 0; i < size; i++)
			{

				//System.out.println("Alias set+"+tempColumnNames.get(i).getTable()+" and subQuery_alias + "+ subQuery_alias);
				tempColumnNames.get(i).getTable().setAlias(subQuery_alias);
			}
		}

		t.tuple.clear();
		t.tuple.addAll(tempTuple);
		t.colNames.clear();
		t.colNames.addAll(tempColumnNames);

		return t;
	}
	

}
