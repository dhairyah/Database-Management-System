package edu.buffalo.www.cse4562;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.statement.*;
import net.sf.jsqlparser.parser.CCJSqlParser.*;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.expression.operators.relational.*;
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

public class Projection extends Tuple implements RelationalAlgebra 
{
	 //ItemsList ll;
	 public List<SelectItem> projection;
	 public void api()
	 {
		  String s1="Jio";
		  System.out.println(s1);
	 }
}
