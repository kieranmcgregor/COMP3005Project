/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import java.sql.*;

import java.util.*;
import java.math.*;

public class DBUpdate
{
    static private final String DB_URL = "jdbc:postgresql://localhost:5433/lookinnabook";
    static private final String USER = "postgres";
    static private final String PW = "S##+d57750!9";

    static private final String AUTHOR_UPDATE = "UPDATE author SET";

    protected static Boolean updateItem(ArrayList<String> entityDetails
                                        , String prepared_statement
                                        , int[] stringIntFlag)
    {
        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PW);
            PreparedStatement prepStmt = conn.prepareStatement(prepared_statement);

            for (int i = 0; i < entityDetails.size(); ++i)
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

            int i = prepStmt.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    protected static Boolean editAuthor(ArrayList<String> authorDetails)
    {
        System.out.println("Editing author...");
        String preparedStatement = AUTHOR_UPDATE;
        ArrayList<String> attributeValues = new ArrayList<String>();
        ArrayList<String> attributeNames = new ArrayList<>(Arrays.asList("first_name", "middle_name", "last_name", "bio"));
        int[] fullMask = new int[]{0,0,0,0,1};
        int[] changeMask = new int[fullMask.length];


        for (int i = 1; i < authorDetails.size(); ++i)
        {
            if (!authorDetails.get(i).isEmpty())
            {
                if (i > 1)
                {
                    preparedStatement += ", ";
                }
                changeMask[attributeValues.size()] = fullMask[i-1];
                attributeValues.add(authorDetails.get(i));
                preparedStatement += " " + attributeNames.get(i-1) +"=?";
            }
        }

        if (attributeValues.size() > 0)
        {
            changeMask[attributeValues.size()] = fullMask[fullMask.length-1];
            attributeValues.add(authorDetails.get(0));
            preparedStatement += " WHERE ID=?";
            System.out.println(preparedStatement);
            updateItem(attributeValues, preparedStatement, changeMask);
            return true;
        }
        return false;
    }

    public static void checkAndEditAuthor(ArrayList<String> authorDetails)
    {
        if (!DBQuery.authorExists(authorDetails))
        {
            DBCreate.addAuthor(authorDetails);
        }
        else
        {
            editAuthor(authorDetails);
        }
    }
}