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
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import net.sf.jsqlparser.expression.BooleanValue;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
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
		//System.out.println("R:"+root.getClass());
		root.open();
		OrderBy2 od;//= new OrderBy2();
		if(!(root instanceof OrderBy2))
		{
			tupleobj = root.retNext();
			//System.out.println("Type:"+tupleobj.record);
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
				System.out.println("Completed");
				tupleobj = root.retNext();
			}
			
		}
		else
		{
			tupleobj = root.retNext();
			//System.out.println("Type:"+tupleobj.record);
			while(tupleobj != null)
			{
				
				//if(c==l)
				//{
					//break;
				//}
				//for(int i = 0; i < tupleobj.tuple.size() - 1; i++)
				//{
					//System.out.print(tupleobj.tuple.get(i) + "|");
				//}
				//System.out.println(tupleobj.tuple.get(tupleobj.tuple.size() - 1));
				//c++;
				//System.out.println("Completed");
				tupleobj = root.retNext();
			}
			od=(OrderBy2)root;
			od.sortAndPrint(l);
			//System.out.println("c:"+root.getClass());
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
		
		int aggr = 0; 
		List<Function> functionList = new ArrayList<Function>();  //list of aggr functions in order
		List<Integer> functionIndex = new ArrayList<Integer>(); 
		List<SelectItem> aggrProjection = new ArrayList<SelectItem>(); //SelectItem stmtm without functions to be passed to proj below aggr 
		String projSelectStmt = "";
		List<Column> parentProj = new ArrayList<Column>(); //list of columns with aliases after applying aggr to be passed above
		
		Iterator<SelectItem> columnName =  query.getSelectItems().iterator();
		int index=0;
		while(columnName.hasNext())
		{
			
			SelectItem currSelectItem = columnName.next();
			
			if(currSelectItem instanceof AllColumns || currSelectItem instanceof AllTableColumns)
			{
				break;
			}
			else
			{
				SelectExpressionItem currSelectExpression = ((SelectExpressionItem) currSelectItem);
				Expression currItem = currSelectExpression.getExpression();
				if(currItem instanceof Function)
				{
					
					functionList.add(((Function) currItem));
					functionIndex.add(index);
					if(projSelectStmt.equals(""))
					{
						projSelectStmt = projSelectStmt + (((Function) currItem).getParameters().getExpressions().get(0).toString());
					}
					else
					{
						projSelectStmt = projSelectStmt + ","+((Function) currItem).getParameters().getExpressions().get(0).toString();
					}
					
					aggr=1;
				}
				else
				{
					if(projSelectStmt.equals(""))
					{
						projSelectStmt = projSelectStmt + currSelectItem.toString();
					}
					else
					{
						projSelectStmt = projSelectStmt + ","+currSelectItem.toString();
					}
				}
				
				
				Column col = new Column();
				String als = currSelectExpression.getAlias();
				Table tab_temp = new Table();
				if(als!=null)
				{
					col.setColumnName(als);
				}
				else
				{
					col.setColumnName(currSelectExpression.toString());
				}
				
				col.setTable(tab_temp);
				parentProj.add((Column)col);
			}
			
			index++;
			
		}
		
		
		if(aggr==1)
		{
			Reader input = new StringReader("select "+projSelectStmt+" from xyz");
			CCJSqlParser parser = new CCJSqlParser(input);
			Statement statement = null;
			try {
				statement = parser.Statement();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			aggrProjection =  ((PlainSelect) ((Select) statement).getSelectBody()).getSelectItems();
			
			RelationalAlgebra2 op = new Aggregate2();
			Aggregate2 op1 = (Aggregate2)op;
			op1.groupByColumns = query.getGroupByColumnReferences();
			op1.aggrFunctions.addAll(functionList);
			op1.colNamesParent.addAll(parentProj);
			op1.functionIndex.addAll(functionIndex);
			op= (RelationalAlgebra2)op1 ;
			if(rootCreated == 0)
			{
				op.parent = null;
				root = op;
			}
			else
			{
				op.parent = parent;
			}
			
			op.leftChild= null;
			op.rightChild = null;
			parent = op;
			
			RelationalAlgebra2 opproj = new Projection2();
			Projection2 opproj1 = (Projection2)opproj;
			
			opproj1.projection = aggrProjection;
			opproj= (RelationalAlgebra2)opproj1;
			opproj.parent = parent;
			opproj.leftChild= null;
			opproj.rightChild = null;
			parent.leftChild = opproj;
			parent = opproj;
			
		}
		else
		{	
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
					op.leftChild= null;
					op.rightChild = null;
					root = op;
				}
				else
				{
					op= (RelationalAlgebra2)op1;
					op.leftChild= null;
					op.rightChild = null;
					op.parent = parent;
					parent.leftChild = op;
					
				}
				
				
				parent = op;
			}
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

				int joinCnt=query.getJoins().size();
				
				RelationalAlgebra2 op = new Join2();
				
				Scan2 leftChild = new Scan2();
				Scan2 rightChild = new Scan2();
				
				leftChild.fromitem = from;
				rightChild.fromitem = (FromItem) query.getJoins().get(0).getRightItem();
				
				op.leftChild = leftChild;
				op.rightChild = rightChild;
				
				for(int i=1;i<joinCnt;i++)
				{
					
					RelationalAlgebra2 opp = new Join2();
					
					Scan2 right = new Scan2();	
					right.fromitem = (FromItem) query.getJoins().get(i).getRightItem();
					
					opp.leftChild = op;
					opp.rightChild = right;
					
					op.parent = opp;
					op = opp;
					
				}
				
				parent.leftChild = op;
				parent = op;
				
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
