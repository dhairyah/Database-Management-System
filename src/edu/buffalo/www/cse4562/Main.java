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
import net.sf.jsqlparser.schema.Table;
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
	static int f=0;
	
	private static void ParseTree(RelationalAlgebra2 root) throws IOException, SQLException
	{		
		Tuple tupleobj;
		root.open();
		if(!(root instanceof OrderBy2))
		{
			tupleobj = root.retNext();
			while(tupleobj != null)
			{
				
				if(c==l)
				{
					break;
				}
				for(int i = 0; i < tupleobj.tuple.size() - 1; i++)
				{
					System.out.print(tupleobj.tuple.get(i) + "|");
				}
				System.out.println(tupleobj.tuple.get(tupleobj.tuple.size() - 1));
				c++;
				
				tupleobj = root.retNext();
			}
			
		}
	}
	
	
	
	public static RelationalAlgebra2 createTree(PlainSelect query, String alias) {
		int rootCreated = 0;
		RelationalAlgebra2 root=null;
		RelationalAlgebra2 parent=null;
		
		List<OrderByElement> orderbyEl = query.getOrderByElements();
		if(orderbyEl != null) {
			RelationalAlgebra2 op = new OrderBy2();
			OrderBy2 op1= (OrderBy2)op;
			op1.element = orderbyEl;
			op= (RelationalAlgebra2)op1 ;
			op.parent = null;
			op.leftChild = null;
			op.rightChild = null;
			root = op;
			parent = op;
			rootCreated = 1;
		}
		
		List<SelectItem> selItem = query.getSelectItems();
		if(!selItem.isEmpty()) {
			RelationalAlgebra2 op = new Projection2();
			Projection2 op1= (Projection2)op;
			op1.projection = selItem;
			
			if(rootCreated == 0)
			{
				op1.subQuery_alias = alias;
				op= (RelationalAlgebra2)op1;
				op.parent = null;
				root = op;
			}
			else
			{
				op= (RelationalAlgebra2)op1;
				op.parent = parent;
				
			}
			
			op.leftChild= null;
			op.rightChild = null;
			parent = op;
		}
		Expression exp = query.getWhere();
		if(exp != null) {
			RelationalAlgebra2 op = new Selection2();
			Selection2 op1 = (Selection2)op;
			op1.expression = exp;
			op = (RelationalAlgebra2)op1;
			op.parent = parent;
			op.leftChild = null;
			op.rightChild = null;
			parent.leftChild = op;
			parent = op;
			
		}
		FromItem from = query.getFromItem();
		if(from != null) {
			
			if(query.getJoins()!=null)
			{

				//not implemented right now
			}
			else
			{
				RelationalAlgebra2 op = new Scan2();
				Scan2 op1 = (Scan2)op;
				
				
				if(from instanceof SubSelect)
				{
					
					RelationalAlgebra2 subTreeRoot = null;;
					subTreeRoot = createTree((PlainSelect) ((SubSelect) from).getSelectBody(), ((SubSelect) from).getAlias());
					parent.leftChild = subTreeRoot;	
				}			
				else
				{			
						
					op1.fromitem = from;
					op = (RelationalAlgebra2)op1;
					op.parent = parent;
					op.leftChild = null;
					op.rightChild = null;
					parent.leftChild = op;
					parent = op;
				}
			}
		}
		
		return root;
	}
	private static void test() throws IOException, SQLException
	{
		Reader reader = Files.newBufferedReader(Paths.get("data//"+"prajin.dat"));
	}
	public static void main(String[] args) throws ParseException, SQLException {
		
		RelationalAlgebra2 treeRoot = null;
		String prompt = "$> ";		
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
					l=-1;
					c=0;
					PlainSelect plain = (PlainSelect)body;
					if (plain.getLimit()!=null)
					{	l=(int)plain.getLimit().getRowCount();
					}
					treeRoot = createTree(plain,"");
					
					try 
					{
						ParseTree(treeRoot);
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
				CreateTable create1 = (CreateTable) statement;
				int k = 0;
				String tableName = create1.getTable().getName().toLowerCase() ;
				map.put(tableName, create1);
			}
			System.out.println(prompt);
            System.out.flush();
			statement = parser.Statement();
		}
	}
}