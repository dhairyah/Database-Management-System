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
   CreateTable create=null;
   Tuple tupleobj = null;
   Iterator<CSVRecord> tupplelist = null;
   
   public void open() throws IOException
   {
	   
	   tablename = ((Table) fromitem).getName();
	   //System.out.println("Inopen tablename1:"+tablename);
	   reader = Files.newBufferedReader(Paths.get("data"+tablename+".dat"));
	   parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withDelimiter('|'));
	   create = Main.map.get(tablename.toLowerCase());
	  //System.out.println("Inopen tablename2:"+create);
	   //create.getTable().setAlias(string);(fromitem.getAlias());
	   tupleobj = new Tuple();
	   tupplelist = parser.iterator(); 
   }
   
   public void reset() throws IOException
   {
	   reader = Files.newBufferedReader(Paths.get("data"+tablename+".dat"));
	   parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withDelimiter('|'));
	   tupplelist = parser.iterator(); 
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
			String dataType = create.getColumnDefinitions().get(i).getColDataType().toString();
			String colName = create.getColumnDefinitions().get(i).getColumnName();
			String lowercolname = colName.toLowerCase();
				//System.out.println("l:"+dataType);
			//tupleobj.columnNames.add(lowercolname);
			Column tempCol = new Column(create.getTable(), colName);
			tupleobj.colNames.add(tempCol);
			
			if(dataType.equalsIgnoreCase("integer"))
			{
				//System.out.println("fdffd555");
				String temp = tupple.get(i);
				PrimitiveValue d = new LongValue(Long.valueOf(temp));
				tupleobj.tuple.add(d);
			}
			else if(dataType.equalsIgnoreCase("int"))
			{
				//System.out.println("fdffd555");
				String temp = tupple.get(i);
				PrimitiveValue d = new LongValue(Long.valueOf(temp));
				tupleobj.tuple.add(d);
			}
			else if(dataType.equalsIgnoreCase("string"))
			{
				String temp = tupple.get(i);
				PrimitiveValue d = new StringValue(temp);
				tupleobj.tuple.add(d);
			}
			else if(dataType.equalsIgnoreCase("date"))
			{
				//System.out.println("gonr");
				String temp = tupple.get(i);
				PrimitiveValue d = new DateValue(temp);
				//System.out.println("d");
				tupleobj.tuple.add(d);
			}
			else if(dataType.equalsIgnoreCase("varchar"))
			{
				String temp = tupple.get(i);
				PrimitiveValue d = new StringValue(temp);
				tupleobj.tuple.add(d);
			}
			else if(dataType.equalsIgnoreCase("char"))
			{
				String temp = tupple.get(i);
				PrimitiveValue d = new StringValue(temp);
				tupleobj.tuple.add(d);
			}
			else if(dataType.equalsIgnoreCase("double"))
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
   return tupleobj;
   }
   else
   {
	   return null;
   }
	   
  }
}
