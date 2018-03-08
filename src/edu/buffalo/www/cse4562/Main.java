/*package edu.buffalo.www.cse4562;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.parser.CCJSqlParser.*;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.select.SelectBody.*;
import net.sf.jsqlparser.statement.create.table.*;
import net.sf.jsqlparser.statement.create.table.*;
import java.lang.Object;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import net.sf.jsqlparser.parser.*;
public class f   {
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
				BinaryExpression tt=(BinaryExpression)sv.getWhere();
				
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
package edu.buffalo.www.cse4562;
 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import net.sf.jsqlparser.expression.BooleanValue;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SubSelect;
 
public class Main {
	
	//static CreateTable create;
	static HashMap<String, CreateTable> map = new HashMap<>();
	static int l=-1;
	static int c=0;
	
	private static void ParseTree(RelTreeObj leafnode) throws IOException, SQLException
	{
		
		Scan table = (Scan)leafnode.getOperator();
		Tuple tupleobj = new Tuple();
		RelTreeObj parentnode = null;
		//ArrayList<PrimitiveValue> pp= new ArrayList<PrimitiveValue>();
		ArrayList<ArrayList<PrimitiveValue>> ta = new ArrayList<ArrayList<PrimitiveValue>>();
		int tai=0;
		int printflag = 1;
				
		Reader reader = Files.newBufferedReader(Paths.get("D://Eclipse//DB//CSE4562SP18//src//"+table.fromitem+".csv"));
		CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withDelimiter('|'));
		CreateTable create = map.get(table.fromitem.toString());
		
		for (CSVRecord tupple : parser.getRecords()) 
		{
			
			//System.out.println("l:"+l);
			if(c==l)
			{
				
				//System.out.println("vvv");
				break;
			}
			tupleobj.record = tupple;
			tupleobj.tuple.clear();
			tupleobj.columnNames.clear();
			int numColumns = create.getColumnDefinitions().size();
			for(int i = 0; i < numColumns; i++)
			{
				String dataType = create.getColumnDefinitions().get(i).getColDataType().toString();
				String colName = create.getColumnDefinitions().get(i).getColumnName();
				String lowercolname = colName.toLowerCase();
					
				tupleobj.columnNames.add(lowercolname);
				//System.out.println("TTTtupple:"+tupleobj.columnNames);
				
				if(dataType.equalsIgnoreCase("int"))
				{
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
					String temp = tupple.get(i);
					PrimitiveValue d = new DateValue(temp);
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
				else if(dataType.equalsIgnoreCase("decimal"))
				{
					String temp = tupple.get(i);
					PrimitiveValue d = new DoubleValue(temp);
					tupleobj.tuple.add(d);
				}
				else
				{
					//int err = 3/0;
				}
			}
			// System.out.println("fgf"+tupleobj.columnNames);
			tupleobj.table = create;
			parentnode = leafnode.retParent();
			printflag = 1;
			
			
			while(parentnode != null)
			{
				 //System.out.println("Class"+parentnode.operator.getClass());
				 //System.out.println("fgf"+tupleobj.columnNames);
				if(parentnode.operator.api(tupleobj)==true)
				{
				 if (parentnode.operator instanceof OrderBy)
				 {
					/// System.out.println("fgf"+tupleobj.columnNames);
					 
					 //OrderBy ob=new OrderBy();
					 ((OrderBy)parentnode.operator).otable.add(new ArrayList<PrimitiveValue>());
					 for(int i = 0; i < tupleobj.tuple.size(); i++)
						{
							//System.out.print(tupleobj.tuple.get(i) + "|");
						 ((OrderBy)parentnode.operator).otable.get(tai).add(tupleobj.tuple.get(i));  
							//ta.get(tai).add(tupleobj.tuple.get(i));
						}
						//System.out.println(tupleobj.tuple.get(tupleobj.tuple.size() - 1));
					    // 	ta.get(tai).add(tupleobj.tuple.get(tupleobj.tuple.size() - 1));
				 }
				 parentnode = parentnode.retParent();
				}
				else
				{
					printflag = 0;
					break;
				}
			}
			
			if(printflag == 1)
			{
				ta.add(new ArrayList<PrimitiveValue>());
				for(int i = 0; i < tupleobj.tuple.size() - 1; i++)
				{
					//System.out.print(tupleobj.tuple.get(i) + "|");
					ta.get(tai).add(tupleobj.tuple.get(i));
				}
				//System.out.println(tupleobj.tuple.get(tupleobj.tuple.size() - 1));
			     	ta.get(tai).add(tupleobj.tuple.get(tupleobj.tuple.size() - 1));
			}
			
			c++;
			tai++;
		}
		
		//OrderBy orderobject =new OrderBy();
		parentnode = leafnode.retParent();
		parentnode = parentnode.retParent();
		//System.out.println("inparse:"+parentnode.operator.getClass());
		/*while(!(parentnode.operator instanceof OrderBy))
		{  System.out.println(parentnode.operator.getClass());
			parentnode = leafnode.retParent();}	*/
		//System.out.println("FinalColumns:"+tupleobj.columnNames);
		System.out.println("Finalo:"+((OrderBy)parentnode.operator).otable);
		/*for (int i=0;i<((OrderBy)parentnode.operator).otable.size();i++)
		{
			//System.out.print(((OrderBy)parentnode.operator).otable.get(i).get(0));
			System.out.print("XXXXXX:"+((OrderBy)parentnode.operator).otable);
		}*/
		//((OrderBy)parentnode.operator).SortColumn();
		((OrderBy)parentnode.operator).fun(tupleobj);
		//System.out.println("Final::"+ta.get(0).get(0).toDouble());
	}
	
	
	
	public static RelTreeObj[] createTree(PlainSelect query) {
		RelTreeObj parent = null;
		RelTreeObj leaf = null;
		RelTreeObj[] treebounds = new RelTreeObj[2];
		List<OrderByElement> cc= query.getOrderByElements(); 
		//if(cc!=null)
		{
			RelationalAlgebra op = new OrderBy();
			OrderBy op1= (OrderBy)op;
			op1.element=cc;
			op = (RelationalAlgebra)op1;
			RelTreeObj child = new RelTreeObj(op);
			treebounds[0] = child;
			//parent.attachChild(child);
			parent = child;
			//leaf = child;
		}
		List<SelectItem> selItem = query.getSelectItems();
		if(!selItem.isEmpty()) {
			RelationalAlgebra op = new Projection();
			Projection op1= (Projection)op;
			op1.projection = selItem;
			op= (RelationalAlgebra)op1 ;
			RelTreeObj child = new RelTreeObj(op);
			parent.attachChild(child);
			//treebounds[0] = child;
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
			
			
			if(from instanceof SubSelect)
			{
				
				RelTreeObj[] subtreebounds = new RelTreeObj[2];
				subtreebounds = createTree((PlainSelect) ((SubSelect) from).getSelectBody());
				parent.attachChild(subtreebounds[0]);
				parent = subtreebounds[0];
				leaf = subtreebounds[1];
				
			}			
			else
			{			
					
				op1.fromitem = from;
				op = (RelationalAlgebra)op1;
				RelTreeObj child = new RelTreeObj(op);
				parent.attachChild(child);
				parent = child;
				leaf = child;
			}
		}
		treebounds[1] = leaf;
		return treebounds;
	}
	private static void test() throws IOException, SQLException
	{
		Reader reader = Files.newBufferedReader(Paths.get("data//"+"prajin.dat"));
	}
	public static void main(String[] args) throws ParseException, SQLException {
		//System.out.println("Hello, World");
		
		RelTreeObj[] treebounds = new RelTreeObj[2];
		RelTreeObj leaf = null;
		String prompt = "$> ";
		
		//int exc = 5/0;
		
		System.out.println(prompt);
        System.out.flush();

        Reader input = new InputStreamReader(System.in);

		
		CCJSqlParser parser = new CCJSqlParser(input);
		Statement statement = parser.Statement();
		while(statement != null) {
			
			//System.out.println(statement);
			
			
			if(statement instanceof Select) {
				Select select = (Select) statement;
				SelectBody body = select.getSelectBody();
				if(body instanceof PlainSelect)
				{
					PlainSelect plain = (PlainSelect)body;
				//	List<OrderByElement> cc= plain.getOrderByElements(); 
				//	System.out.println("coll:"+cc);	
					if (plain.getLimit()!=null)
					{	l=(int)plain.getLimit().getRowCount(); }
					//System.out.println("dff:"+plain.getLimit().getRowCount());
					treebounds = createTree(plain);
					
					try 
					{   
						//RelTreeObj aa=treebounds[1]; 
						//System.out.println("Parent:"+treebounds[0].operator.getClass());
						/*for (int i=0;i<3;i++)
						{
							System.out.println("Thats id:"+aa.operator.getClass());
						    aa=aa.retParent();
						}*/
						//System.out.println("Thats id:"+treebounds[1].operator.getClass());
						ParseTree(treebounds[1]);
						
					} 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				int i = 0;
			}
			else if(statement instanceof CreateTable) {
				//create = (CreateTable)statement;
				CreateTable create1 = (CreateTable) statement;
				int k = 0;
				String tableName = create1.getTable().getName();
				map.put(tableName, create1);
				/*try {
					test();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);*/
			}
			
			
			
 
			
			System.out.println(prompt);
           		 System.out.flush();
			statement = parser.Statement();
		}
	}
}