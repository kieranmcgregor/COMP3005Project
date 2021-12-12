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
    static private final String SELECTS_ADD_BOOKS_UPDATE = "UPDATE selects SET quantity = quantity + ? WHERE username=? AND isbn = ?";
    static private final String SELECTS_UPDATE = "UPDATE selects SET quantity=? WHERE username=? AND isbn =?";
    static private final String BANK_BALANCE_UPDATE = "UPDATE bank_account SET balance = balance + ? WHERE id=?";

    /*
    Function:   updateItem
    Purpose:    update tuple in a table
    in:         entityDetails (Attribute values to use for updating)
    in:         prepared_statement (sql statement used for updating table)
    in:         stringIntFlag (mask to identify attribute types)
    return:     true is updated, false otherwise
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
    Function:   editAuthor
    Purpose:    update author details in author table
    in:         authorDetails (Attribute values to use for updating)
    return:     true is updated, false otherwise
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
    Function:   checkAndEditAuthor
    Purpose:    check if author exist if not create author else edit author
    in:         authorDetails (Attribute values to use for updating)
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
    Function:   buyBooks
    Purpose:    add books to the bookstore (i.e. increase book quantity in books)
    in:         bookDetails (Attribute values to use for updating)
    */
    public static void buyBooks(ArrayList<String> bookDetails)
    {
        System.out.println("Buying books...");
        ArrayList<String> booksToOrder = new ArrayList<>(Arrays.asList(bookDetails.get(6), bookDetails.get(0)));
        String preparedStatement = BOOK_QUANTITY_UPDATE;
        updateItem(booksToOrder, preparedStatement, new int[]{1,0});
    }

    /*
    Function:   checkAndBuyBooks
    Purpose:    check if book exists if so edit book quantity
    in:         bookDetails (Attribute values to use for updating)
    */
    public static void checkAndBuyBooks(ArrayList<String> bookDetails)
    {
        if (DBQuery.bookExists(bookDetails))
        {
            buyBooks(bookDetails);
        }
    }

    /*
    Function:   sellBooks
    Purpose:    subtract books to the bookstore (i.e. reduce book quantity in books)
    in:         bookDetails (Attribute values to use for updating)
    */
    public static void sellBooks(ArrayList<String> bookDetails)
    {
        System.out.println("Selling books...");
        String preparedStatement = SELL_BOOKS_UPDATE;
        updateItem(bookDetails, preparedStatement, new int[]{1,0});
    }

    /*
    Function:   checkAndSellBooks
    Purpose:    check if book exists if so edit book quantity
    in:         bookDetails (Attribute values to use for updating)
    */
    public static void checkAndSellBooks(ArrayList<String> bookDetails)
    {
        if (DBQuery.bookExists(bookDetails))
        {
            sellBooks(bookDetails);
        }
    }

    /*
    Function:   payPublisher
    Purpose:    add money to publisher bank account (i.e. increase balance in publisher bank account)
    in:         bookDetails (Attribute values to use for updating)
    */
    public static void payPublisher(Double paymentAmount, String publisherID)
    {
        System.out.println("Paying publisher...");
        String preparedStatement = BANK_BALANCE_UPDATE;
        updateItem(new ArrayList<>(Arrays.asList(paymentAmount.toString(), publisherID))
                    , preparedStatement
                    , new int[]{2,1});
    }

    /*
    Function:   increaseSelectedBooksQuantity
    Purpose:    add books to the basket (i.e. increase book quantity in selects)
    in:         selectionDetails (Attribute values to use for updating)
    */
    public static void increaseSelectedBooksQuantity(ArrayList<String> selectionDetails)
    {
        System.out.println("Adding books to basket...");
        ArrayList<String> booksToAddToBasket = new ArrayList<>(Arrays.asList(selectionDetails.get(0)
                                                                , selectionDetails.get(1)
                                                                , selectionDetails.get(2)));
        String preparedStatement = SELECTS_ADD_BOOKS_UPDATE;
        updateItem(booksToAddToBasket, preparedStatement, new int[]{1,0,0});
    }

    /*
    Function:   increaseBooksInBasket
    Purpose:    if book not in basket add it to basket else increase quantity of books in basket
    in:         selectionDetails (Attribute values to use for updating)
    */
    public static void increaseBooksInBasket(ArrayList<String> selectionDetails)
    {
        if (!DBQuery.selectionExists(selectionDetails))
        {
            System.out.println("Adding book to basket...");
            DBCreate.addBooksToBasket(selectionDetails);
        }
        else
        {
            System.out.println("Adding more books to basket...");
            increaseSelectedBooksQuantity(selectionDetails);
        }
    }

    /*
    Function:   updateSelectedBookQuantity
    Purpose:    change selected book quantity to given value
    in:         selectionDetails (Attribute values to use for updating)
    */
    public static void updateSelectedBookQuantity(ArrayList<String> selectionDetails)
    {
        System.out.println("Updating books in basket...");
        ArrayList<String> booksInBasket = new ArrayList<>(Arrays.asList(selectionDetails.get(0)
                                                                , selectionDetails.get(1)
                                                                , selectionDetails.get(2)));
        String preparedStatement = SELECTS_UPDATE;
        System.out.println(preparedStatement);
        updateItem(booksInBasket, preparedStatement, new int[]{1,0,0});
    }
}