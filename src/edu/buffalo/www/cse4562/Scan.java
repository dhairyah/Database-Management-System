package edu.buffalo.www.cse4562;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.parser.CCJSqlParser.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.select.SelectBody.*;
import net.sf.jsqlparser.statement.create.table.*;
import net.sf.jsqlparser.statement.create.table.*;
import java.lang.Object;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import net.sf.jsqlparser.parser.*;

public class Scan  extends Tuple implements RelationalAlgebra 
{
   
   public FromItem fromitem;
   Reader reader=null;
   public String tablename;
   CSVParser parser=null;
   CreateTable create=new CreateTable();
   Tuple tupleobj = null;
   Iterator<CSVRecord> tupplelist = null;
   public boolean isOpen = false;
   
   public void open() throws IOException
   {
	   
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
	   
	 //  System.err.println("size : " +parser.getRecords().size());
	  // System.out.println("size11 : " +parser.getRecords().size());
	   isOpen = true;
   }
   
   public void reset() throws IOException
   {
	   reader = Files.newBufferedReader(Paths.get("data//"+tablename+".dat"));
	   parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withDelimiter('|'));
	   tupplelist = parser.iterator(); 
	   isOpen = false;
   }
   
   boolean hasNext()
   {
	   return tupplelist.hasNext();
   }
	
   public boolean api(Tuple tupleobj)
   {
 	  String s1="scanning";
 	 // System.out.println(s1);
 	  return true;
   }
   
   public Tuple retNext()
   {
	      
	   if(tupplelist.hasNext())
	   {
		
		CSVRecord tupple = tupplelist.next();
		tupleobj.record = tupple;
		tupleobj.tuple.clear();
		//tupleobj.columnNames.clear();
		tupleobj.colNames.clear();
		//System.out.println("coldef:"+create.getColumnDefinitions());
		int numColumns = create.getColumnDefinitions().size();
		for(int i = 0; i < numColumns; i++)
		{
			//String dataType = create.getColumnDefinitions().get(i).getColDataType().toString();
			ColDataType dataType = create.getColumnDefinitions().get(i).getColDataType();
			String colName = create.getColumnDefinitions().get(i).getColumnName();
			String lowercolname = colName.toLowerCase();
				//System.out.println("l:"+dataType);
			//tupleobj.columnNames.add(lowercolname);
			Column tempCol = new Column(create.getTable(), colName);
			tupleobj.colNames.add(tempCol);
			
			if(dataType.getDataType().equals("INTEGER"))
			{
				//System.out.println("fdffd555");
				String temp = tupple.get(i);
				PrimitiveValue d = new LongValue(Long.valueOf(temp));
				tupleobj.tuple.add(d);
			}
			else if(dataType.getDataType().equals("INT"))
			{
				//System.out.println("fdffd555");
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
				//System.out.println("gonr");
				String temp = tupple.get(i);
				PrimitiveValue d = new DateValue(temp);
				//System.out.println("d");
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
		//System.out.println("InScan_retNext:"+tupleobj.table.getColumnDefinitions());
		//System.out.println("InSCan:"+fromitem.getAlias());
		tupleobj.table.getTable().setAlias(fromitem.getAlias());//Changes 3/15 ////////////
		//System.out.println("In_Scann:"+tupleobj.table.getTable().getAlias());
   
   }
	/*	for(int i = 0; i < tupleobj.tuple.size() - 1; i++)
		{
			System.err.print(tupleobj.tuple.get(i) + "|");
		}
		///System.out.println("not_going");
		System.err.println(tupleobj.tuple.get(tupleobj.tuple.size() - 1));
		*/
   return tupleobj;
   }
   else
   {
	   return null;
   }
	   
  }
}
