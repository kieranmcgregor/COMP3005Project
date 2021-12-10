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
    static private final String AUTHOR_UPDATE = "UPDATE author SET";
    static private final String BOOK_QUANTITY_UPDATE = "UPDATE books SET quantity = quantity + ? WHERE isbn = ?";
    static private final String SELL_BOOKS_UPDATE = "UPDATE books SET quantity = quantity - ? WHERE isbn = ?";
    static private final String SELECTS_UPDATE = "UPDATE selects SET quantity = quantity + ? WHERE username=? AND isbn = ?";

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean updateItem(ArrayList<String> entityDetails
                                        , String prepared_statement
                                        , int[] stringIntFlag)
    {
        try
        {
            Connection conn = DriverManager.getConnection(LookInnaBook.DB_URL, LookInnaBook.USER, LookInnaBook.PW);
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
                    prepStmt.setLong(i+1, Long.parseLong(entityDetails.get(i)));
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

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
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

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
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

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static void buyBooks(ArrayList<String> bookDetails)
    {
        System.out.println("Buying books...");
        ArrayList<String> booksToOrder = new ArrayList<>(Arrays.asList(bookDetails.get(6), bookDetails.get(0)));
        String preparedStatement = BOOK_QUANTITY_UPDATE;
        updateItem(booksToOrder, preparedStatement, new int[]{1,0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static void checkAndBuyBooks(ArrayList<String> bookDetails)
    {
        if (DBQuery.bookExists(bookDetails))
        {
            buyBooks(bookDetails);
        }
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static void sellBooks(ArrayList<String> bookDetails)
    {
        System.out.println("Buying books...");
        ArrayList<String> booksToOrder = new ArrayList<>(Arrays.asList(bookDetails.get(6), bookDetails.get(0)));
        String preparedStatement = SELL_BOOKS_UPDATE;
        updateItem(booksToOrder, preparedStatement, new int[]{1,0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static void checkAndSellBooks(ArrayList<String> bookDetails)
    {
        if (DBQuery.bookExists(bookDetails))
        {
            buyBooks(bookDetails);
        }
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static void updateQuantityOfSelectedBooks(ArrayList<String> selectionDetails)
    {
        System.out.println("Adding books to basket...");
        ArrayList<String> booksToAddToBasket = new ArrayList<>(Arrays.asList(selectionDetails.get(2)
                                                                , selectionDetails.get(0)
                                                                , selectionDetails.get(1)));
        String preparedStatement = SELECTS_UPDATE;
        updateItem(booksToAddToBasket, preparedStatement, new int[]{1,0,0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static void addToBasket(ArrayList<String> selectionDetails)
    {
        if (!DBQuery.selectionExists(selectionDetails))
        {
            System.out.println("Adding book to basket...");
            DBCreate.selectBook(selectionDetails);
        }
        else
        {
            System.out.println("Adding more books to basket...");
            updateQuantityOfSelectedBooks(selectionDetails);
        }
    }
}