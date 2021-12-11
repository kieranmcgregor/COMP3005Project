/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */
import java.sql.*;

import java.util.*;
import java.math.*;

public class DBQuery
{
    static public final String SUPER_BOOK_QUERY = "SELECT books.isbn,title,genre,page_count,price,first_name"+
                                                            ",middle_name,last_name,name"+
                                                        " FROM (books INNER JOIN (publisher NATURAL JOIN provincial_area)"+
                                                                " ON books.publisher_id = publisher.id)"+
                                                            " INNER JOIN (authors NATURAL JOIN author)"+
                                                                " ON books.isbn = authors.isbn";
    static public final String BOOK_QUERY = "SELECT * FROM books";
    static public final String BOOK_PUBLISHER_QUERY = "SELECT publisher_id FROM books";
    static public final String BOOK_PRICE_PERCENTAGE_QUERY = "SELECT price,publisher_percentage FROM books";
    static public final String AUTHORS_QUERY = "SELECT * FROM authors";
    static public final String ADDS_QUERY = "SELECT * FROM adds";
    static public final String AUTHOR_QUERY = "SELECT * FROM author";
    static public final String PUBLISHER_QUERY = "SELECT * FROM publisher";
    static public final String PROVINCE_QUERY = "SELECT * FROM provincial_area";
    static public final String STREET_QUERY = "SELECT * FROM street_area";
    static public final String BANK_ACCOUNT_QUERY = "SELECT * FROM bank_account";
    static public final String OWNER_QUERY = "SELECT * FROM owner";
    static public final String USER_QUERY = "SELECT * FROM users";
    static public final String SUPER_SELECTS_QUERY = "SELECT books.isbn,selects.quantity,title,genre,page_count,price"+
                                                        ",first_name,middle_name,last_name,name"+
                                                        " FROM ((books INNER JOIN selects ON books.isbn=selects.isbn)"+
                                                                " INNER JOIN (publisher NATURAL JOIN provincial_area)"+
                                                                    " ON books.publisher_id = publisher.id)"+
                                                            " INNER JOIN (authors NATURAL JOIN author)"+
                                                                " ON books.isbn = authors.isbn"+
                                                        " WHERE selects.username=?";
    static public final String SELECTS_QUERY = "SELECT * FROM selects";
    static public final String BOOK_ORDER_QUERY = "SELECT * FROM book_order";
    static public final String WAREHOUSE_ADDRESS_QUERY = "SELECT number,street,postal_code,country FROM warehouse";
    static public final String SHIPPING_SERVICE_ADDRESS_QUERY = "SELECT number,street,postal_code,country FROM shipping_service";
    static public final String CURRENT_ORDER_ADDRESS_QUERY = "SELECT order_number,shipping_state,current_number,current_street"+
                                                        ",provincial_area.city,provincial_area.province"+
                                                        ",current_postal_code,current_country"+
                                                            " FROM ((book_order NATURAL JOIN orders)"+
                                                                    " NATURAL JOIN order_addresses)"+
                                                                        " INNER_JOIN provincial_area"+
                                                                            "ON provincial_area.postal_code = current_postal_code";

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected static Integer getLastEntryID(String query)
    {
        try
        {
            Connection conn = DriverManager.getConnection(LookInnaBook.DB_URL, LookInnaBook.USER, LookInnaBook.PW);
            Statement stmt = conn.createStatement ( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
            ResultSet rs = stmt.executeQuery (query);

            Class.forName("org.postgresql.Driver");

            rs.last();

            return rs.getInt("ID");
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
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected static Integer getLastEntryOrderNumber(String query)
    {
        try
        {
            Connection conn = DriverManager.getConnection(LookInnaBook.DB_URL, LookInnaBook.USER, LookInnaBook.PW);
            Statement stmt = conn.createStatement ( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
            ResultSet rs = stmt.executeQuery (query);

            Class.forName("org.postgresql.Driver");

            rs.last();

            return rs.getInt("order_number");
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
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected static ArrayList<String> getSpecificEntry(ArrayList<String> attributeValues, String prepared_query, int[] stringIntFlag)
    {
        ArrayList<String> queryReturnValues = new ArrayList<String>();

        try
        {
            Connection conn = DriverManager.getConnection(LookInnaBook.DB_URL, LookInnaBook.USER, LookInnaBook.PW);
            PreparedStatement prepStmt = conn.prepareStatement(prepared_query);

            for (int i = 0; i < stringIntFlag.length; ++i)
            {
                System.out.println(attributeValues.get(i));
                if (stringIntFlag[i] == 0)
                {
                    prepStmt.setString(i+1, attributeValues.get(i));
                }
                else
                {
                    prepStmt.setLong(i+1, Long.parseUnsignedLong(attributeValues.get(i)));
                }
            }

            ResultSet rs = prepStmt.executeQuery();
            Class.forName("org.postgresql.Driver");

            if (rs.next())
            {
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();

                for (int i = 1; i < colCount; ++i)
                {
                    queryReturnValues.add(rs.getString(i));
                }
            }

            System.out.println("Returning");
            return queryReturnValues;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return queryReturnValues;
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected static ArrayList<ArrayList<String>> getAllEntriesOfCriteria(ArrayList<String> attributeValues
                                                                , String prepared_query
                                                                , int[] stringIntFlag)
    {
        ArrayList<ArrayList<String>> queryReturnValues = new ArrayList<ArrayList<String>>();

        try
        {
            Connection conn = DriverManager.getConnection(LookInnaBook.DB_URL, LookInnaBook.USER, LookInnaBook.PW);
            PreparedStatement prepStmt = conn.prepareStatement(prepared_query);

            for (int i = 0; i < attributeValues.size(); ++i)
            {
                System.out.println(attributeValues.get(i));
                if (stringIntFlag[i] == 0)
                {
                    prepStmt.setString(i+1, attributeValues.get(i));
                }
                else
                {
                    prepStmt.setLong(i+1, Long.parseUnsignedLong(attributeValues.get(i)));
                }
            }

            ResultSet rs = prepStmt.executeQuery();
            Class.forName("org.postgresql.Driver");

            while (rs.next())
            {
                ArrayList<String> tupleValue = new ArrayList<String>();
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();

                for (int i = 1; i <= colCount; ++i)
                {
                    System.out.println(rs.getString(i));
                    tupleValue.add(rs.getString(i));
                }

                queryReturnValues.add(tupleValue);
            }

            System.out.println("Returning");
            return queryReturnValues;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return queryReturnValues;
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected static Boolean checkIfExists(ArrayList<String> entityDetails
                                        , String prepared_statement
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
                else
                {
                    prepStmt.setLong(i+1, Long.parseUnsignedLong(entityDetails.get(i)));
                }
            }
            ResultSet rs = prepStmt.executeQuery();

            Class.forName("org.postgresql.Driver");

            return rs.next();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
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
    in:         
    return:     
    */
    public static Boolean provinceDetailsExist(ArrayList<String> provinceDetails)
    {
        System.out.println("Checking province details...");
        String prepared_province_query = PROVINCE_QUERY + " WHERE postal_code=?";

        if (provinceDetails.get(0).charAt(0) != '_')
        {
            return checkIfExists(provinceDetails, prepared_province_query, new int[]{0});
        }

        return false;
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean streetDetailsExist(ArrayList<String> streetDetails)
    {
        System.out.println("Checking street details...");
        String prepared_street_query = STREET_QUERY + " WHERE number=? AND street=? AND postal_code=? AND country=?";

        if (streetDetails.get(0) != null
            && streetDetails.get(1) != null
            && streetDetails.get(2).charAt(0) != '_'
            && streetDetails.get(3) != null)
        {
            return checkIfExists(streetDetails, prepared_street_query, new int[]{0,0,0,0});
        }

        return false;
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean publisherExists(ArrayList<String> publisherDetails)
    {
        System.out.println("Checking for publisher...");
        if (!publisherDetails.get(0).isEmpty())
        {
            String prepared_publisher_query = PUBLISHER_QUERY + " WHERE ID=?";
            return checkIfExists(publisherDetails, prepared_publisher_query, new int[]{1});
        }

        return false;
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean bankAccountExists(ArrayList<String> bankAccountDetails)
    {
        System.out.println("Checking for bank account...");
        String prepared_bank_account_query = BANK_ACCOUNT_QUERY + " WHERE institution_number=? AND transit_number=? AND account_number=?";

        if (bankAccountDetails.get(0).charAt(0) != '_'
            && bankAccountDetails.get(1).charAt(0) != '_'
            && bankAccountDetails.get(2).charAt(0) != '_')
        {
            return checkIfExists(bankAccountDetails, prepared_bank_account_query, new int[]{1,1,1});
        }

        return false;
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean authorExists(ArrayList<String> authorDetails)
    {
        System.out.println("Checking for author...");
        String prepared_author_query = AUTHOR_QUERY + " WHERE ID=?";
        
        if (!authorDetails.get(0).isEmpty())
        {
            return checkIfExists(authorDetails, prepared_author_query, new int[]{1});
        }

        return false;
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean bookExists(ArrayList<String> bookDetails)
    {
        System.out.println("Checking for book...");
        if (bookDetails.get(0).charAt(0) != '_')
        {
            String prepared_book_query = BOOK_QUERY + " WHERE ISBN=?";
            return checkIfExists(bookDetails, prepared_book_query, new int[]{0});
        }

        return false;
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean authorLinkExists(ArrayList<String> authorDetails, ArrayList<String> bookDetails)
    {
        System.out.println("Checking for author authors book...");
        
        if (!authorDetails.get(0).isEmpty()
            && bookDetails.get(0).charAt(0) != '_')
        {
            ArrayList<String> authorBookLink = new ArrayList<>(Arrays.asList(authorDetails.get(0), bookDetails.get(0)));
            String prepared_author_query = AUTHORS_QUERY + " WHERE ID=? AND isbn=?";
            return checkIfExists(authorBookLink, prepared_author_query, new int[]{1,0});
        }

        return false;
    }

     /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean ownerLinkExists(ArrayList<String> bookDetails)
    {
        System.out.println("Checking for owner adds book...");
        
        if (bookDetails.get(0).charAt(0) != '_')
        {
            ArrayList<String> ownerBookLink = new ArrayList<>(Arrays.asList(LookInnaBook.getUsername(), bookDetails.get(0)));
            String prepared_adds_query = ADDS_QUERY + " WHERE username=? AND isbn=?";
            return checkIfExists(ownerBookLink, prepared_adds_query, new int[]{0,0});
        }

        return false;
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean ownerExists(ArrayList<String> ownerDetails)
    {
        System.out.println("Checking owner...");
        String prepared_owner_query = OWNER_QUERY + " WHERE username=?";
        return checkIfExists(ownerDetails, prepared_owner_query, new int[]{0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean ownerPasswordIsValid(ArrayList<String> ownerDetails)
    {
        System.out.println("Checking owner password...");
        String prepared_owner_query = OWNER_QUERY + " WHERE username=?";
        ArrayList<String> userValues = getSpecificEntry(ownerDetails, prepared_owner_query, new int[]{0});

        return ownerDetails.get(1).equals(userValues.get(1));
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean userExists(ArrayList<String> userDetails)
    {
        System.out.println("Checking user...");
        String prepared_user_query = USER_QUERY + " WHERE username=?";
        return checkIfExists(userDetails, prepared_user_query, new int[]{0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean selectionExists(ArrayList<String> selectionDetails)
    {
        System.out.println("Checking user selected book...");
        String prepared_selects_query = SELECTS_QUERY + " WHERE username=? AND isbn=?";
        return checkIfExists(selectionDetails, prepared_selects_query, new int[]{0,0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static Boolean userPasswordIsValid(ArrayList<String> userDetails)
    {
        System.out.println("Checking user password...");
        String prepared_user_query = USER_QUERY + " WHERE username=?";
        ArrayList<String> userValues = getSpecificEntry(userDetails, prepared_user_query, new int[]{0});

        return userDetails.get(1).equals(userValues.get(1));
    }

    /*
    Function:   getAllSelectedBooksOfCriteria
    Purpose:    query the DB for all books given a certain search criteria
    in:         runningList (running list of books found)
    return:     runningList (with any newly found books added)
    */
    public static ArrayList<ArrayList<String>> getAllSelectedBooksOfCriteria(ArrayList<String> selectionDetails)
    {
        String prepared_selects_query = SUPER_SELECTS_QUERY;
        return getAllEntriesOfCriteria(selectionDetails, prepared_selects_query, new int[]{0});
    }

    /*
    Function:   getAllSelectedUsernameBooks
    Purpose:    query the DB for all books given a certain search criteria
    in:         runningList (running list of books found)
    return:     runningList (with any newly found books added)
    */
    public static ArrayList<ArrayList<String>> getAllSelectedUsernameBooks(ArrayList<String> selectionDetails)
    {
        String prepared_selects_query = SELECTS_QUERY + " WHERE username=?";
        return getAllEntriesOfCriteria(selectionDetails, prepared_selects_query, new int[]{0});
    }

    /*
    Function:   getAllBooksOfCriteria
    Purpose:    query the DB for all books given a certain search criteria
    in:         runningList (running list of books found)
    return:     runningList (with any newly found books added)
    */
    public static ArrayList<ArrayList<String>> getAllBooksOfCriteria(ArrayList<String> bookDetails)
    {
        System.out.println("Serching books by criteria...");
        String preparedStatement = SUPER_BOOK_QUERY;
        ArrayList<String> attributeValues = new ArrayList<String>();
        String selectAttributes = new String();
        ArrayList<String> attributeNames = new ArrayList<>(Arrays.asList("books.isbn"
                                                                        , "title"
                                                                        , "genre"
                                                                        , "page_count"
                                                                        , "price"
                                                                        , "first_name"
                                                                        , "middle_name"
                                                                        , "last_name"
                                                                        , "name"
                                                                        , "email"
                                                                        , "phone_number"
                                                                        , "number"
                                                                        , "street"
                                                                        , "city"
                                                                        , "province"
                                                                        , "postal_code"
                                                                        , "country"));
        int[] fullMask = new int[]{0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] changeMask = new int[fullMask.length];

        for (int i = 0; i < bookDetails.size(); ++i)
        {
            if (!bookDetails.get(i).isEmpty()
                && bookDetails.get(i).charAt(0) != '_')
            {
                if (attributeValues.size() > 1)
                {
                    preparedStatement += " AND";
                }
                else
                {
                    preparedStatement += " WHERE";
                }
                changeMask[attributeValues.size()] = fullMask[i];
                attributeValues.add(bookDetails.get(i));
                preparedStatement += " " + attributeNames.get(i) +"=?";
            }
        }

        System.out.println(preparedStatement);
        return getAllEntriesOfCriteria(attributeValues, preparedStatement, changeMask);
    }

    /*
    Function:   getOrder
    Purpose:    query the DB for order state
    in:         runningList (running list of books found)
    return:     runningList (with any newly found books added)
    */
    public static ArrayList<String> getOrder(Integer orderNumber)
    {
        System.out.println("Getting order " + orderNumber + "...");
        String prepared_selects_query = BOOK_ORDER_QUERY + " WHERE order_number=?";
        return getAllEntriesOfCriteria(new ArrayList<>(Arrays.asList(orderNumber.toString()))
                                        , prepared_selects_query
                                        , new int[]{1}).get(0);
    }

    /*
    Function:   getWarehouseStreetAddress
    Purpose:    query the DB for warehouse street address
    in:         runningList (running list of books found)
    return:     runningList (with any newly found books added)
    */
    public static ArrayList<String> getWarehouseStreetAddress(String warehouseID)
    {
        String prepared_selects_query = WAREHOUSE_ADDRESS_QUERY + " WHERE id=?";
        return getAllEntriesOfCriteria(new ArrayList<>(Arrays.asList(warehouseID))
                                        , prepared_selects_query
                                        , new int[]{1}).get(0);
    }

    /*
    Function:   getShippingServiceStreetAddress
    Purpose:    query the DB for shipping service street address
    in:         runningList (running list of books found)
    return:     runningList (with any newly found books added)
    */
    public static ArrayList<String> getShippingServiceStreetAddress(String shippingServiceID)
    {
        String prepared_selects_query = SHIPPING_SERVICE_ADDRESS_QUERY + " WHERE id=?";
        return getAllEntriesOfCriteria(new ArrayList<>(Arrays.asList(shippingServiceID))
                                        , prepared_selects_query
                                        , new int[]{1}).get(0);
    }

    /*
    Function:   getBookPriceAndPercentage
    Purpose:    query the DB for shipping service street address
    in:         runningList (running list of books found)
    return:     runningList (with any newly found books added)
    */
    public static ArrayList<String> getBookPriceAndPercentage(String isbn)
    {
        String prepared_selects_query = BOOK_PRICE_PERCENTAGE_QUERY + " WHERE isbn=?";
        return getAllEntriesOfCriteria(new ArrayList<>(Arrays.asList(isbn))
                                        , prepared_selects_query
                                        , new int[]{0}).get(0);
    }

    /*
    Function:   getBookPublisher
    Purpose:    query the DB for shipping service street address
    in:         runningList (running list of books found)
    return:     runningList (with any newly found books added)
    */
    public static ArrayList<String> getBookPublisher(String isbn)
    {
        String prepared_selects_query = BOOK_PUBLISHER_QUERY + " WHERE isbn=?";
        return getAllEntriesOfCriteria(new ArrayList<>(Arrays.asList(isbn))
                                        , prepared_selects_query
                                        , new int[]{0}).get(0);
    }
}