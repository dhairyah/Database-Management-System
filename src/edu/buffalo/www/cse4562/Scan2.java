package edu.buffalo.www.cse4562;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
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
	BufferedReader reader=null;
	public String tablename;
	CSVParser parser=null;
	CreateTable create=new CreateTable();
	Tuple tupleobj = null;
	//Iterator<CSVRecord> tupplelist = null;
	public boolean isOpen = false;
	public Expression expression;
	public boolean testing =false;
	File csvData=null;
	Runtime r;

	@Override
	boolean api(Tuple tupleobj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Column> open() throws IOException
	{
		List<Column> cn = this.colNamesChild;
		tablename = ((Table) fromitem).getName();
		//reader = Files.newBufferedReader(Paths.get("data//"+tablename+".dat"));
		csvData = new File("data//"+tablename+".dat");
		parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT.withDelimiter('|'));
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
		//tupplelist = parser.iterator();
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
		r = Runtime.getRuntime();
		return (cn);
		
	}

	public void reset()
	{
		//reader.close();
		//reader = Files.newBufferedReader(Paths.get("data//"+tablename+".dat"));
		try {
			parser.close();
			r.gc();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		csvData = new File("data//"+tablename+".dat");
		try {
			parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT.withDelimiter('|'));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tupplelist = parser.iterator();
		isOpen = false;
	}

	boolean hasNext()
	{
		return true;
	}

	@Override
	Tuple retNext() throws SQLException {
		
		for(CSVRecord tupple : parser)
		{
			//tupple = tupplelist.next();
			//System.out.println("hererererer");
			tupleobj.record = tupple;
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

				tupleobj.table = create;
				
//				tupleobj.table.getTable().setAlias(fromitem.getAlias());

			}
			//r.gc();
			return tupleobj;
		}
		
			try {
				parser.close();
				//csvData.;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		


	}

	@Override
	void close() {
		// TODO Auto-generated method stub

	}

}
