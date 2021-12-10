import java.sql.*;

import java.util.*;
import java.math.*;

public class DBDelete
{
    static private final String DB_URL = "jdbc:postgresql://localhost:5433/lookinnabook";
    static private final String USER = "postgres";
    static private final String PW = "S##+d57750!9";

    static private final String BOOK_DELETE = "DELETE FROM books WHERE isbn=?";

    protected static Integer deleteItem(String prepared_statement, String isbn)
    {
        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PW);
            PreparedStatement prepStmt = conn.prepareStatement(prepared_statement);
            prepStmt.setString(1, isbn);
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

    public static Boolean deleteBook(String isbn)
    {
        System.out.println("Deleting book in DB...");
        return deleteItem(BOOK_DELETE, isbn) >= 0;
    }

}