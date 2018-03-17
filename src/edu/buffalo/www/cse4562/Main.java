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
	
	private static void ParseTree(RelTreeObj leafnode) throws IOException, SQLException
	{		
		//c=0;
		//System.out.println("tc:"+c+" tl:"+l);
		if(leafnode.getOperator() instanceof Scan)
		{
			Scan table = (Scan)leafnode.getOperator();
			table.open();
			Tuple tupleobj = new Tuple();
			RelTreeObj parentnode = null;
			OrderBy orderby = new OrderBy();
			int printflag = 1;
			int blockOperator = 0;
			
			while(table.hasNext())
			{
				if(c==l && f==0)
				{
					//System.out.println("ccc:"+c+" ll:"+l);
					break;
				}
				tupleobj = table.retNext();
				parentnode = leafnode.retParent();
				printflag = 1;
				
				
				while(parentnode != null)
				{
					
					if(parentnode.operator.api(tupleobj)==true)
					{
				     
					 if(parentnode.getOperator() instanceof OrderBy)
					 {
						 blockOperator = 1;
						 f=1;
						 //System.out.println("block");
						 orderby = (OrderBy)parentnode.getOperator();
					 }
					 parentnode = parentnode.retParent();
					 
					}
					else
					{
						//System.out.println("break");
						printflag = 0;
						break;
					}
				}
				
				if(printflag == 1 && blockOperator == 0)
				{
					for(int i = 0; i < tupleobj.tuple.size() - 1; i++)
					{
						System.out.print(tupleobj.tuple.get(i) + "|");
					}
					///System.out.println("not_going");
					System.out.println(tupleobj.tuple.get(tupleobj.tuple.size() - 1));
					c++;
				}
				//System.out.println("Incrementing");
				//c++;
				
			}
			
			if(blockOperator==1)
			{
				//System.out.println("ddddd");
				orderby.sortAndPrint(l);
			}
		}
		else
		{
			Join join = (Join)leafnode.getOperator();
			Tuple tupleobj = new Tuple();
			RelTreeObj parentnode = null;
			int printflag = 1;
			OrderBy orderby = new OrderBy();
			int blockOperator = 0;
			
			while(join.api(tupleobj))
			{
				parentnode = leafnode.retParent();
				printflag = 1;
				//System.out.println("c:"+c+" l:"+l);
				if(c==l && f==0)
				{
					//System.out.println("bbreakk");
					break;
				}
				
				//added join 3 table join
				if(parentnode.getOperator() instanceof Join)
				{
					//System.out.println("ii");
					Join innode  = (Join)parentnode.getOperator();
					innode.current_left_tuple = tupleobj;
					
					parentnode.operator = (RelationalAlgebra)innode;
					
					//System.out.println("rec pasre tree start");
					ParseTree(parentnode);
					//System.out.println("rec pasre tree end");
					
					continue;
				}
				
				while(parentnode != null)
				{
					
					if(parentnode.operator.api(tupleobj)==true)
					{
						if(parentnode.getOperator() instanceof OrderBy)
						 {
							 blockOperator = 1;
							 f=1;
							 orderby = (OrderBy)parentnode.getOperator();
						 }
						 parentnode = parentnode.retParent();
					}
					else
					{
						printflag = 0;
						//System.out.println("tgee");
						break;
					}
				}
				
				if(printflag == 1 && blockOperator == 0)
				{
					for(int i = 0; i < tupleobj.tuple.size() - 1; i++)
					{
						System.out.print(tupleobj.tuple.get(i) + "|");
					}
					System.out.println(tupleobj.tuple.get(tupleobj.tuple.size() - 1));
					c++;
				}
			//System.out.println("incrementing");	
			//c++;	
			}
			
			if(blockOperator==1)
			{
				//System.out.println("goiing");
				orderby.sortAndPrint(l);
			}
		}
	}
	
	
	
	public static RelTreeObj[] createTree(PlainSelect query, String alias) {
		RelTreeObj parent = null;
		RelTreeObj leaf = null;
		RelTreeObj[] treebounds = new RelTreeObj[2];
		int rootCreated = 0;
		
		List<OrderByElement> orderbyEl = query.getOrderByElements();
		if(orderbyEl != null) {
			RelationalAlgebra op = new OrderBy();
			OrderBy op1= (OrderBy)op;
			op1.element = orderbyEl;
			op1.subQuery_alias = alias;
			op= (RelationalAlgebra)op1 ;
			RelTreeObj child = new RelTreeObj(op);
			treebounds[0] = child;
			rootCreated = 1;
			parent = child;
		}
		
		List<SelectItem> selItem = query.getSelectItems();
		if(!selItem.isEmpty()) {
			RelationalAlgebra op = new Projection();
			Projection op1= (Projection)op;
			op1.projection = selItem;
			if(rootCreated == 0)
			{
				op1.subQuery_alias = alias;
			}
			op= (RelationalAlgebra)op1 ;
			RelTreeObj child = new RelTreeObj(op);
			if(rootCreated == 0)
			{
				treebounds[0] = child;
			}
			else
			{
				parent.attachChild(child);
			}
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
			//System.out.println("xx:"+query.getJoins());
			
			if(query.getJoins()!=null)
			{
				int nux=query.getJoins().size();
				/*if(query.getJoins().size()==1)
				{
					RelationalAlgebra op = new Join();
					Join op1 = (Join)op;
					
					Scan node1 = new Scan();
					Scan node2 = new Scan();
					node1.fromitem = from;
					node2.fromitem = (FromItem) query.getJoins().get(0).getRightItem();
					if(!alias.isEmpty())
					{
						//Added to handle alias in subquery select rr.* from (select * from R) rr;
						node1.fromitem.setAlias(alias);
						node2.fromitem.setAlias(alias);
					}
					
					try {
						node1.open();
						node2.open();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					op1.node1 = node1;
					op1.node2 = node2;
					op = (RelationalAlgebra)op1;
					RelTreeObj child = new RelTreeObj(op);
					parent.attachChild(child);
					parent = child;
					leaf = child;
				}*/
				//else
				{
					while(nux>1)
					{	
					 RelationalAlgebra opp = new Join();
					 Join op11 = (Join)opp;
					
					 Join node11 = new Join();
					 node11 = op11;
					 if(query.getJoins().get(nux-1).getRightItem() instanceof SubSelect)
					 {
						ScanPlainSelect plainSelectnode= new ScanPlainSelect(); 
						plainSelectnode.query = (PlainSelect) ((SubSelect) query.getJoins().get(nux-1).getRightItem()).getSelectBody();
						plainSelectnode.subSelctAlias = query.getJoins().get(nux-1).getRightItem().getAlias();
						
						try {

										plainSelectnode.open();
									   } catch (IOException e) {
										// TODO Auto-generated catch block
										 e.printStackTrace();
									   }
						
						  op11.node1 = node11;
						  op11.node2 = plainSelectnode;	
					 }
					 else
					 {
						 Scan node22 = new Scan();					 
						 node22.fromitem = (FromItem) query.getJoins().get(nux-1).getRightItem();
						 node22.fromitem.setAlias(query.getJoins().get(nux-1).getRightItem().getAlias());
						 
						 
						 
						// System.out.println("xxmain:"+node22.fromitem);
						 if(query.getJoins().get(nux-1).getRightItem().getAlias() != null && !query.getJoins().get(nux-1).getRightItem().getAlias().isEmpty() )
						 {
							//Added to handle alias in subquery select rr.* from (select * from R) rr;
							//node1.fromitem.setAlias(alias);
							node22.fromitem.setAlias(query.getJoins().get(nux-1).getRightItem().getAlias());
						 } 
						
						 try {
				//			node1.open();
							node22.open();
						   } catch (IOException e) {
							// TODO Auto-generated catch block
							 e.printStackTrace();
						   }
						
						  op11.node1 = node11;
						  op11.node2 = node22;
					 }
					 
					
					
			
  					  opp = (RelationalAlgebra)op11;
					  RelTreeObj childd = new RelTreeObj(opp);
					  parent.attachChild(childd);
					  parent = childd;
					  leaf = childd;
					
					  nux--;
					}  
					///////////////////////////////
					
					RelationalAlgebra op = new Join();
					Join op1 = (Join)op;
					
					if(from instanceof SubSelect)
					{
						ScanPlainSelect node1 = new ScanPlainSelect();
						node1.query = (PlainSelect) ((SubSelect) from).getSelectBody();
						if((alias != null && !alias.isEmpty()) || !query.getFromItem().getAlias().isEmpty())
						{
							//Added to handle alias in subquery select rr.* from (select * from R) rr;
							node1.subSelctAlias = query.getFromItem().getAlias();
							
						}
						try {
							node1.open();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						op1.node1 = node1;
					}
					else
					{
						Scan node1 = new Scan();
						node1.fromitem = from;
						if((alias != null && !alias.isEmpty()) || !query.getFromItem().getAlias().isEmpty())
						{
							//Added to handle alias in subquery select rr.* from (select * from R) rr;
							node1.fromitem.setAlias(query.getFromItem().getAlias());
							
						}
						try {
							node1.open();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(query.getJoins().size() == 1 && ((Table) query.getFromItem()).getName().equals(((Table) query.getJoins().get(0).getRightItem()).getName()))
						{
							node1.expression = exp;
							node1.testing = true;
						}
						op1.node1 = node1;
					}
					
					if(query.getJoins().get(0).getRightItem() instanceof SubSelect)
					{
						ScanPlainSelect node2 = new ScanPlainSelect();
						node2.query = (PlainSelect)from;
						if(query.getJoins().get(0).getRightItem().getAlias()!=null && !query.getJoins().get(0).getRightItem().getAlias().isEmpty())
						{
							//Added to handle alias in subquery select rr.* from (select * from R) rr;
							node2.subSelctAlias = query.getJoins().get(0).getRightItem().getAlias();
							
						}
						try {
							node2.open();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						op1.node2 = node2;
					}
					else
					{
						Scan node2 = new Scan();
						node2.fromitem = (FromItem) query.getJoins().get(0).getRightItem();
						if(query.getJoins().get(0).getRightItem().getAlias()!=null && !query.getJoins().get(0).getRightItem().getAlias().isEmpty())
						{
							node2.fromitem.setAlias(query.getJoins().get(0).getRightItem().getAlias());
						}
						
						try {
							node2.open();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(query.getJoins().size() == 1 && ((Table) query.getFromItem()).getName().equals(((Table) query.getJoins().get(0).getRightItem()).getName()))
						{
							node2.expression = exp;
							node2.testing = true;
						}
						op1.node2 = node2;
					}
					
					
					/*Scan node1 = new Scan();
					Scan node2 = new Scan();
					node1.fromitem = from;
					node2.fromitem = (FromItem) query.getJoins().get(0).getRightItem();
					if(alias != null && !alias.isEmpty())
					{
						//Added to handle alias in subquery select rr.* from (select * from R) rr;
						node1.fromitem.setAlias(query.getFromItem().getAlias());
						
					}
					if(query.getJoins().get(0).getRightItem().getAlias()!=null && !query.getJoins().get(0).getRightItem().getAlias().isEmpty())
					{
						node2.fromitem.setAlias(query.getJoins().get(0).getRightItem().getAlias());
					}*/
					
					
					
					
					op = (RelationalAlgebra)op1;
					RelTreeObj child = new RelTreeObj(op);
					parent.attachChild(child);
					parent = child;
					leaf = child;
					
					////////////////////////////////////////////
				}
				
			}
			else
			{
				RelationalAlgebra op = new Scan();
				Scan op1 = (Scan)op;
				
				
				if(from instanceof SubSelect)
				{
					
					RelTreeObj[] subtreebounds = new RelTreeObj[2];
					subtreebounds = createTree((PlainSelect) ((SubSelect) from).getSelectBody(), ((SubSelect) from).getAlias());
					parent.attachChild(subtreebounds[0]);
					parent = subtreebounds[0];
					leaf = subtreebounds[1];
					
				}			
				else
				{			
						
					op1.fromitem = from;
					if(alias != null && !alias.isEmpty()) ///Changes 3/15 ///////////////////
					{
						//System.out.println("li:"+alias);
						//Added to handle alias in subquery select rr.* from (select * from R) rr;
						op1.fromitem.setAlias(alias);
					}
					op = (RelationalAlgebra)op1;
					RelTreeObj child = new RelTreeObj(op);
					parent.attachChild(child);
					parent = child;
					leaf = child;
				}
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
					l=-1;
					c=0;
					PlainSelect plain = (PlainSelect)body;
					if (plain.getLimit()!=null)
					{	l=(int)plain.getLimit().getRowCount();
					//System.out.println("limitL:"+l);
					}
				     //System.out.println("alias:"+plain.getFromItem().getAlias());
					treebounds = createTree(plain,"");
					
					try 
					{
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
				String tableName = create1.getTable().getName().toLowerCase() ;
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
