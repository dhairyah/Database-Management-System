package edu.buffalo.www.cse4562;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import net.sf.jsqlparser.schema.Column;

public abstract class RelationalAlgebra2
{
   abstract boolean api(Tuple tupleobj) throws SQLException;
   
   public RelationalAlgebra2 parent;
   public RelationalAlgebra2 leftChild;
   public RelationalAlgebra2 rightChild;
   public List<Column> colNamesParent;
   public List<Column> colNamesChild;
   
   abstract List<Column> open() throws IOException;
   abstract void close();
   abstract Tuple retNext() throws SQLException;
   
}
