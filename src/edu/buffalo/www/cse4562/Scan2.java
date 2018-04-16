package edu.buffalo.www.cse4562;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.FromItem;

public class Scan2 extends RelationalAlgebra2 {
	public FromItem fromitem;
	Reader reader=null;
	public String tablename;
	CSVParser parser=null;
	CreateTable create=new CreateTable();
	Tuple tupleobj = null;
	Iterator<CSVRecord> tupplelist = null;
	public boolean isOpen = false;
	public Expression expression;
	public boolean testing =false;

	@Override
	boolean api(Tuple tupleobj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Column> open() throws IOException
	{
		List<Column> cn = this.colNamesChild;
		tablename = ((Table) fromitem).getName();
		reader = Files.newBufferedReader(Paths.get("data//"+tablename+".dat"));
		parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withDelimiter('|'));
		CreateTable temp = Main.map.get(tablename.toLowerCase());
		
		List<ColumnDefinition> temp_colDef = new ArrayList<ColumnDefinition>();
		temp_colDef = temp.getColumnDefinitions();
		create.setColumnDefinitions(temp_colDef);
		create.setIndexes(temp.getIndexes());
		Table temp_table = new Table(temp.getTable().getName());
		create.setTable(temp_table);
		create.setTableOptionsStrings(temp.getTableOptionsStrings());
		create.getTable().setAlias(fromitem.getAlias());
		tupleobj = new Tuple();
		tupplelist = parser.iterator();
        for(int i=0;i<temp.getColumnDefinitions().size();i++)
        {
        	String colName = create.getColumnDefinitions().get(i).getColumnName();
        	Column tempCol = new Column(create.getTable(), colName);
			cn.add(tempCol);
        }
		//  System.err.println("size : " +parser.getRecords().size());
		// System.out.println("size11 : " +parser.getRecords().size());
		isOpen = true;
		colNamesParent=cn;
		colNamesChild=cn;
		return (cn);
		
	}

	public void reset()
	{
		try {
			reader = Files.newBufferedReader(Paths.get("data//"+tablename+".dat"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withDelimiter('|'));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tupplelist = parser.iterator(); 
		isOpen = false;
	}

	boolean hasNext()
	{
		return tupplelist.hasNext();
	}

	@Override
	Tuple retNext() throws SQLException {
		if(tupplelist.hasNext())
		{
			CSVRecord tupple = tupplelist.next();
			//tupleobj.record = tupple;
			tupleobj.tuple.clear();
			
			int numColumns = create.getColumnDefinitions().size();
			for(int i = 0; i < numColumns; i++)
			{
				ColDataType dataType = create.getColumnDefinitions().get(i).getColDataType();

				if(dataType.getDataType().equals("INTEGER"))
				{
					String temp = tupple.get(i);
					PrimitiveValue d = new LongValue(Long.valueOf(temp));
					tupleobj.tuple.add(d);
				}
				else if(dataType.getDataType().equals("INT"))
				{
					String temp = tupple.get(i);
					PrimitiveValue d = new LongValue(Long.valueOf(temp));
					tupleobj.tuple.add(d);
				}
				else if(dataType.getDataType().equals("STRING"))
				{
					String temp = tupple.get(i);
					PrimitiveValue d = new StringValue(temp);
					tupleobj.tuple.add(d);
				}
				else if(dataType.getDataType().equals("DATE"))
				{
					String temp = tupple.get(i);
					PrimitiveValue d = new DateValue(temp);
					tupleobj.tuple.add(d);
				}
				else if(dataType.getDataType().equals("VARCHAR"))
				{
					String temp = tupple.get(i);
					PrimitiveValue d = new StringValue(temp);
					tupleobj.tuple.add(d);
				}
				else if(dataType.getDataType().equals("CHAR"))
				{
					String temp = tupple.get(i);
					PrimitiveValue d = new StringValue(temp);
					tupleobj.tuple.add(d);
				}
				else if(dataType.getDataType().equals("DOUBLE"))
				{
					String temp = tupple.get(i);
					PrimitiveValue d = new DoubleValue(temp);
					tupleobj.tuple.add(d);
				}
				else
				{
					//System.out.println("dffddf");
					//int err = 3/0;

				}

				//tupleobj.table = create;
				
//				tupleobj.table.getTable().setAlias(fromitem.getAlias());

			}
			
			if(testing == true)
			{

				AndExpression exp = (AndExpression)expression;
				GreaterThan left = (GreaterThan) ((AndExpression)(exp.getLeftExpression())).getRightExpression();
				GreaterThan right = (GreaterThan)exp.getRightExpression();
				Eval eval = new Eval() {

					@Override
					public PrimitiveValue eval(Column arg0) throws SQLException {
						//	System.out.println("ts:"+t.tuple+" andarg : "+arg0);
						// TODO Auto-generated method stub
						//String columnName = arg0.getColumnName();
						//String lowercolname = columnName.toLowerCase();
						//int index = sl.indexOf(columnName);
						//int index = t.columnNames.indexOf(lowercolname);
						int index = tupleobj.colNames.indexOf(arg0);
						//below code changes is add to handle alias case. In case of alias, arg0's table name has the alias. So table name needs to be compared with alias.
						if(index == -1)
						{
							int size = tupleobj.colNames.size();
							for(int it = 0; it < size; it++)
							{
								//System.out.print(" "+t.colNames.get(it).getTable().getAlias());
								//System.out.print("x"+arg0.getColumnName());
								//System.out.println(" "+t.colNames.get(it).getColumnName());
								//System.out.println(arg0.getColumnName().equalsIgnoreCase(t.colNames.get(it).getColumnName()));
								if((arg0.getTable().getName().equalsIgnoreCase(tupleobj.colNames.get(it).getTable().getAlias())) && 
										(arg0.getColumnName().equalsIgnoreCase(tupleobj.colNames.get(it).getColumnName())))
								{
									index = it;
									break;
								}
							}

						}
						//	System.out.println("te:"+t.tuple+"index:"+index);
						if(index > -1)
						{
							return tupleobj.tuple.get(index);
						}
						else
						{
							return null;
						}

					}
				};

				PrimitiveValue type_left = eval.eval(left);
				PrimitiveValue type_right = eval.eval(right);

				if((type_left != null && !type_left.toBool()) || (type_right !=null && !type_right.toBool()))
				{
					if(tupplelist.hasNext())
					{
						tupleobj=retNext();
					}
					else
					{
						return tupleobj;
					}
				}

			}
			return tupleobj;
		}
		else
		{
			return null;
		}


	}

	@Override
	void close() {
		// TODO Auto-generated method stub

	}

}
