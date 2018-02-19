package edu.buffalo.www.cse4562;
import org.apache.commons.csv.*;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.parser.CCJSqlParser.*;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.select.AllColumns.*;
import net.sf.jsqlparser.statement.select.SelectBody.*;
import net.sf.jsqlparser.statement.create.table.*;
//import net.sf.jsqlparser.statement.create.table.*;
import java.lang.Object;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import net.sf.jsqlparser.parser.*;
/*public class Main   {
	public static void main(String[] args) throws ParseException, SQLException {
		System.out.println("Hello, World");
		Reader input =  new StringReader("CREATE TABLE R(A int,B int, C int);SELECT A ,B FROM  R,E as  Iso  WHERE R.A=5 ");
		CCJSqlParser parser=  new CCJSqlParser(input);
		Statement statement = parser.Statement();
		while(statement!= null)
		{
			if(statement instanceof Select)
			{
				Select select = (Select)statement ;
				//System.out.println(select);
			    PlainSelect sv=(PlainSelect)select.getSelectBody();                        ;
		//		System.out.println("1:"+sv.getFromItem().getAlias());
				BinaryExpression tt=(BinaryExpression)sv.getWhere();*/
				
		//		System.out.println(tt.getLeftExpression());
				
				//DateValue c=(DateValue)tt;
				//System.out.println(c.getDate());
				/*Reader input1 =  new StringReader(p);
			    CCJSqlParser parser1=  new CCJSqlParser(input1);
				Statement statement1 = parser1.Statement();
				if(statement1 instanceof Select)
				{
					System.out.println("sd");
				}*/
				//PlainSelect sv1=(PlainSelect)pp.getSelectBody();                        
				//System.out.println(statement1);
			/*	if(sv instanceof Select)
				{
					Select select = (Select)statement ;
					//System.out.println(select);
				    PlainSelect sv=(PlainSelect)select.getSelectBody();                        ;
					System.out.println(sv.getFromItem());
					
				}	*/
				
				
	//		}
		/*	else  if(statement instanceof CreateTable)
			{
				//System.out.println("sd");
				CreateTable create=(CreateTable)statement;
				
			
			//	System.out.println(create.getColumnDefinitions());
				Tuple e=new Tuple();
				e.s1=create.getColumnDefinitions().toString();
				System.out.println("13:"+e.s1);
				Tuple s= new Selection();
				s.s1="de";
				System.out.println(s.s1);
				s= new Projection();
				//Projection p= new Projection();
				//p.api();
				 System.out.println(s.s1);
			    //String ss=create.getColumnDefinitions().toString();
			    //System.out.println(ss);
			}
			else
			{
				throw new SQLException("Can't handle:"+statement);
			}
			statement=parser.Statement();
			
		 
		}
		
	}
}*/
/*package edu.buffalo.www.cse4562;
 
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
 
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;*/
 
public class Main 
{
	public static Tuple tup= new Tuple();
	 //siz=0;
	public static RelTreeObj createTree(PlainSelect query)
	{
		RelTreeObj parent = null;
		RelTreeObj leaf = null;
		List<SelectItem> selItem = query.getSelectItems();
		//AllColumns all=AllColumns(selItem.);
		System.out.println("PRP:"+selItem.get(0));
		
		if(!selItem.isEmpty()) {
			RelationalAlgebra op = new Projection();
			Projection op1= (Projection)op;
			op1.projection = selItem;
			System.out.println("FDsf:::::::::::::");
		    op1.api(tup);
			op= (RelationalAlgebra)op1 ;
			RelTreeObj child= new RelTreeObj(op);
			parent = child;
		}
		Expression exp = query.getWhere();
		if(exp != null) {
			RelationalAlgebra op = new Selection();
			Selection op1 = (Selection)op;
			op1.expression = exp;
			op = (RelationalAlgebra)op1;
			RelTreeObj child = new RelTreeObj(op);
			parent.attachChild(child);
			parent = child;
		}
		FromItem from = query.getFromItem();
		if(from != null) {
			RelationalAlgebra op = new Scan();
			Scan op1 = (Scan)op;
			op1.fromitem = from;
			op = (RelationalAlgebra)op1;
			RelTreeObj child = new RelTreeObj(op);
			parent.attachChild(child);
			parent = child;
			leaf = child;
		}
		
		return leaf;
	}
	
	
	public static void main(String[] args) throws ParseException, IOException
	{
		System.out.println("Hello, World");
		int siz=0;
		RelTreeObj leaf = null;
		Reader input = new StringReader("CREATE TABLE R (A int, B date, C int );SELECT A,C FROM R");
		CCJSqlParser parser = new CCJSqlParser(input);
		Statement statement = parser.Statement();
		while(statement != null)
		{
			if(statement instanceof Select) 
			{
				Select select = (Select) statement;
				//CreateTable ct=(CreateTable)statement;
				//System.out.println("def::"+ct.getColumnDefinitions());
				SelectBody body = select.getSelectBody();
				if(body instanceof PlainSelect)
				{
					PlainSelect plain = (PlainSelect)body;
					leaf = createTree(plain);
					System.out.println("Child detail : " + leaf.retParent().getOperator().getClass());
					//leaf.getParent().getData().api(t);
					int k = 0;
					
				}
				int i = 0;
			}
			else if(statement instanceof CreateTable)
			{
				CreateTable create = (CreateTable)statement;
				Index ii=new Index();
				tup.table=create;
				//ii=(Index)create.getIndexes();
				//System.out.println(ii.getColumnsNames());
				//ColumnDefinition cc= (ColumnDefinition)statement;
				//ColumnDefinition cdd= (ColumnDefinition)statement;
				System.out.println("GEy"+create.getColumnDefinitions());
				siz=create.getColumnDefinitions().size();
				System.out.println(siz);
				int k = 0;
			}
 
			statement = parser.Statement();
		}
		 
		//Reader reader = Files.newBufferedReader(Paths.get("D:\\Eclipse\\DB\\DB_project\\CSE4562SP18\\R.csv"));
		//CSVParser parser1 = CSVParser.parse(reader, CSVFormat.DEFAULT.withDelimiter('|').withIgnoreHeaderCase().withTrim());
 
 
		 /*for (CSVRecord csvRecord : parser1.getRecords()) {
             // Accessing values by the names assigned to each column
 
 
			 String col;
             
			 int colNumber =siz;
			for(int i=0;i<colNumber;i++)
			 {
 
				 col = csvRecord.get(i);
 
				 System.out.print(" "+col);
			 }
			 System.out.println("");
         }*/
		 System.out.println(leaf.getOperator().getClass());
		 Scan rr=(Scan) leaf.getOperator();
		 System.out.println(rr.fromitem);
		 System.out.println(leaf.retParent().getOperator().getClass());	
		}
}

