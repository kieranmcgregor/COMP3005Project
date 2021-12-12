/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import java.sql.*;

import java.util.*;
import java.math.*;

public class DBDelete
{
    static private final String BOOK_DELETE = "DELETE FROM books WHERE isbn=?";
    static private final String SELECTS_DELETE = "DELETE FROM selects WHERE username=? AND isbn=?";
    
    /*
    Function:   deleteItem
    Purpose:    delete an item from a table
    in:         prepared_statement statement to delete tuple from table
    return:     row number if deleted, -1 otherwise
    */
    protected static Integer deleteItem(String prepared_statement
                                        , ArrayList<String> entityDetails
                                        , int[] stringIntFlag)
    {
        try
        {
            Connection conn = DriverManager.getConnection(LookInnaBook.DB_URL, LookInnaBook.USER, LookInnaBook.PW);
            PreparedStatement prepStmt = conn.prepareStatement(prepared_statement);
            
            for (int i = 0; i < stringIntFlag.length; ++i)
            {
                System.out.println(entityDetails.get(i));
                if (stringIntFlag[i] == 0)
                {
                    prepStmt.setString(i+1, entityDetails.get(i));
                }
                else if (stringIntFlag[i] == 1)
                {
                    prepStmt.setLong(i+1, Long.parseUnsignedLong(entityDetails.get(i)));
                }
                else
                {
                    prepStmt.setBigDecimal(i+1, new BigDecimal(entityDetails.get(i)));
                }
            }
            
            int row = prepStmt.executeUpdate();

            Class.forName("org.postgresql.Driver");

            return row;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    /*
    Function:   deleteBook
    Purpose:    delete an book from books table
    in:         isbn  (isbn to delete from books table)
    return:     true if deleted, false otherwise
    */
    public static Boolean deleteBook(String isbn)
    {
        System.out.println("Deleting book from DB...");
        return deleteItem(BOOK_DELETE, new ArrayList<>(Arrays.asList(isbn)), new int[]{0}) >= 0;
    }

    /*
    Function:   deleteBookFromSelects
    Purpose:    delete an book from selects table
    in:         selectsBookDetails (username and isbn to delete from selects table)
    return:     true if deleted, false otherwise
    */
    public static Boolean deleteBookFromSelects(ArrayList<String> selectsBookDetails)
    {
        System.out.println("Deleting book from basket...");
        return deleteItem(SELECTS_DELETE,  selectsBookDetails, new int[]{0,0}) >= 0;
    }

}