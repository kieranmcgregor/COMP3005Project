/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import java.sql.*;

import java.util.*;
import java.math.*;

public class CallDB
{
    static private final String DB_URL = "jdbc:postgresql://localhost:5433/lookinnabook";
    static private final String USER = "postgres";
    static private final String PW = "S##+d57750!9";
    static private final String BOOK_QUERY = "SELECT * FROM books";
    static private final String BOOK_CREATE = "INSERT INTO books(isbn, title, genre, page_count, price, publisher_percentage, quantity, threshold, publisher_id, warehouse_id) VALUES(?,?,?,?,?,?,?,?,?,1)";
    static private final String AUTHORS_QUERY = "SELECT * FROM authors";
    static private final String AUTHORS_CREATE = "INSERT INTO authors(id, isbn) VALUES(?,?)";
    static private final String AUTHOR_QUERY = "SELECT * FROM author";
    static private final String AUTHOR_CREATE = "INSERT INTO author(first_name, middle_name, last_name, bio) VALUES(?,?,?,?)";
    static private final String AUTHOR_UPDATE = "UPDATE author SET";
    static private final String PUBLISHER_QUERY = "SELECT * FROM publisher";
    static private final String PUBLISHER_CREATE = "INSERT INTO publisher(name, email, phone_number, number, street, postal_code, country) VALUES(?,?,?,?,?,?,?)";
    static private final String PROVINCE_QUERY = "SELECT * FROM provincial_area";
    static private final String PROVINCE_CREATE = "INSERT INTO provincial_area(postal_code, city, province) VALUES(?,?,?)";
    static private final String STREET_QUERY = "SELECT * FROM street_area";
    static private final String STREET_CREATE = "INSERT INTO street_area(number, street, postal_code, country) VALUES(?,?,?,?)";
    static private final String BANK_ACCOUNT_QUERY = "SELECT * FROM bank_account";
    static private final String BANK_ACCOUNT_CREATE = "INSERT INTO bank_account(institution_number, transit_number, account_number, balance, id) VALUES(?,?,?,0.00,?)";


    protected static Integer getLastEntryID(String query)
    {
        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PW);
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

    protected static ArrayList<String> getSpecificEntry(String prepared_query, ArrayList<String> attributeValues, int[] stringIntFlag)
    {
        ArrayList<String> queryReturnValues = new ArrayList<String>();

        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PW);
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

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            for (int i = 0; i < colCount; ++i)
            {
                queryReturnValues.add(rs.getString(i));
            }

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

    protected static Boolean checkIfExists(ArrayList<String> entityDetails
                                        , String prepared_statement
                                        , int[] stringIntFlag)
    {
        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PW);
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

    protected static Boolean addItem(ArrayList<String> entityDetails
                                        , String preparedStatement
                                        , int[] stringIntFlag)
    {
        return addItem(entityDetails, preparedStatement, stringIntFlag, 0);
    }

    protected static Boolean addItem(ArrayList<String> entityDetails
                                        , String prepared_statement
                                        , int[] stringIntFlag
                                        , int offset)
    {
        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PW);
            PreparedStatement prepStmt = conn.prepareStatement(prepared_statement);

            for (int i = 0; i < stringIntFlag.length; ++i)
            {
                System.out.println(entityDetails.get(i));
                if (stringIntFlag[i] == 0)
                {
                    prepStmt.setString(i+1, entityDetails.get(i+offset));
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

    protected static Boolean updateItem(ArrayList<String> entityDetails
                                        , String prepared_statement
                                        , int[] stringIntFlag)
    {
        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PW);
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

            int i = prepStmt.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

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

    protected static Boolean addProvinceDetails(ArrayList<String> provinceDetails)
    {
        System.out.println("Adding province details...");
        return addItem(provinceDetails, PROVINCE_CREATE, new int[]{0,0,0});
    }

    public static Boolean checkAndAddProvinceDetails(ArrayList<String> provinceDetails)
    {
        Boolean exists = provinceDetailsExist(provinceDetails);
        if (!exists)
        {
            return addProvinceDetails(provinceDetails);
        }

        return exists;
    }

    protected static Boolean editProvinceDetails(ArrayList<String> provinceDetails)
    {
        System.out.println("Editing province details...");

        return true;
    }

    public static Boolean checkAndEditProvinceDetails(ArrayList<String> provinceDetails)
    {
        if (!provinceDetailsExist(provinceDetails))
        {
            return addProvinceDetails(provinceDetails);
        }
        else
        {
            return editProvinceDetails(provinceDetails);
        }
    }

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

    protected static Boolean addStreetDetails(ArrayList<String> streetDetails)
    {
        System.out.println("Adding street details...");
        return addItem(streetDetails, STREET_CREATE, new int[]{0,0,0,0});
    }

    public static Boolean checkAndAddStreetDetails(ArrayList<String> streetDetails)
    {
        Boolean exists = streetDetailsExist(streetDetails);
        if (!exists)
        {
            return addStreetDetails(streetDetails);
        }

        return exists;
    }

    protected static Boolean editStreetDetails(ArrayList<String> streetDetails)
    {
        System.out.println("Editing street details...");

        return true;
    }

    public static Boolean checkAndEditStreetDetails(ArrayList<String> streetDetails)
    {
        if (!streetDetailsExist(streetDetails))
        {
            return addStreetDetails(streetDetails);
        }
        else
        {
            return editStreetDetails(streetDetails);
        }
    }

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

    protected static Integer addPublisher(ArrayList<String> publisherDetails, ArrayList<String> streetDetails)
    {
        System.out.println("Adding publisher...");
        publisherDetails.addAll(streetDetails);
        if (addItem(publisherDetails, PUBLISHER_CREATE, new int[]{0,0,0,0,0,0,0}, 1))
        {
            return getLastEntryID(PUBLISHER_QUERY);
        }

        return -1;
    }

    public static Integer checkAndAddPublisher(ArrayList<String> publisherDetails, ArrayList<String> streetDetails)
    {
        if (!publisherExists(publisherDetails)
            && checkAndAddStreetDetails(streetDetails))
        {
            return addPublisher(publisherDetails, streetDetails);
        }

        return -1;
    }

    protected static Boolean editPublisher(ArrayList<String> publisherDetails)
    {
        System.out.println("Editing publisher...");

        return true;
    }

    public static void checkAndEditPublisher(ArrayList<String> publisherDetails)
    {
        if (publisherExists(publisherDetails))
        {
            editPublisher(publisherDetails);
        }
    }

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

    protected static Boolean addBankAccount(ArrayList<String> bankAccountDetails)
    {
        System.out.println("Adding bank account...");
        return addItem(bankAccountDetails, BANK_ACCOUNT_CREATE, new int[]{1,1,1,1});
    }

    public static Boolean checkAndAddBankAccount(ArrayList<String> bankAccountDetails)
    {
        ArrayList<String> publisherID = new ArrayList<>(Arrays.asList(bankAccountDetails.get(3)));
        Boolean exists = bankAccountExists(bankAccountDetails);
        if (!exists && publisherExists(publisherID))
        {
            return addBankAccount(bankAccountDetails);
        }

        return exists;
    }

    protected static Boolean editBankAccount(ArrayList<String> bankAccountDetails)
    {
        System.out.println("Editing bank account...");
        return true;
    }

    public static void checkAndEditBankAccount(ArrayList<String> bankAccountDetails)
    {
        if (!bankAccountExists(bankAccountDetails))
        {
            addBankAccount(bankAccountDetails);
        }
        else
        {
            editBankAccount(bankAccountDetails);
        }
    }

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

    protected static Integer addAuthor(ArrayList<String> authorDetails)
    {
        System.out.println("Adding author...");
        if (addItem(authorDetails, AUTHOR_CREATE, new int[]{0,0,0,0}, 1))
        {
            return getLastEntryID(AUTHOR_QUERY);
        }

        return -1;
    }

    public static Integer checkAndAddAuthor(ArrayList<String> authorDetails)
    {
        if (!authorExists(authorDetails))
        {
            return addAuthor(authorDetails);
        }

        return -1;
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
            updateItem(attributeValues, preparedStatement, changeMask);
            return true;
        }
        return false;
    }

    public static void checkAndEditAuthor(ArrayList<String> authorDetails)
    {
        if (!authorExists(authorDetails))
        {
            addAuthor(authorDetails);
        }
        else
        {
            editAuthor(authorDetails);
        }
    }

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

    public static Boolean addBook(ArrayList<String> bookDetails)
    {
        System.out.println("Adding book...");
        return addItem(bookDetails, BOOK_CREATE, new int[]{0,0,0,1,2,2,1,1,1});
    }

    public static Boolean checkAndAddBook(ArrayList<String> bookDetails)
    {
        Boolean exists = bookExists(bookDetails);
        if (!exists)
        {
            return addBook(bookDetails);
        }

        return exists;
    }

    public static Boolean editBook(ArrayList<String> bookDetails)
    {
        System.out.println("Editing book...");

        return true;
    }

    public static void checkAndEditBook(ArrayList<String> bookDetails)
    {
        if (!bookExists(bookDetails))
        {
            addBook(bookDetails);
        }
        else
        {
            editBook(bookDetails);
        }
    }

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

    protected static Boolean addAuthorLink(ArrayList<String> authorDetails, ArrayList<String> bookDetails)
    {
        System.out.println("Adding author authors book...");
        ArrayList<String> authorBookLink = new ArrayList<>(Arrays.asList(authorDetails.get(0), bookDetails.get(0)));
        return addItem(authorBookLink, AUTHORS_CREATE, new int[]{1,0});
    }


    /*
    Function:   addBook
    Purpose:    to add a book and any other dependent entities to the DB
    in:         bookDetails (a list of details described by the book)
    return:     Boolean (true if addition is successful)
    */
    public static void checkAndAddAllBookDetails(ArrayList<String> provinceDetails
                                                    , ArrayList<String> streetDetails
                                                    , ArrayList<String> publisherDetails
                                                    , ArrayList<String> bankAccountDetails
                                                    , ArrayList<String> authorDetails
                                                    , ArrayList<String> bookDetails)
    {
        System.out.println("Adding all book details to DB...");
        if (!publisherExists(publisherDetails))
        {
            checkAndAddProvinceDetails(provinceDetails);
            checkAndAddPublisher(publisherDetails, streetDetails);
            checkAndAddBankAccount(bankAccountDetails);
        }

        if (!authorExists(authorDetails))
        {
            addAuthor(authorDetails);
        }

        if (!bookExists(bookDetails))
        {
            addBook(bookDetails);
        }

        if (!authorLinkExists(authorDetails, bookDetails))
        {
            addAuthorLink(authorDetails, bookDetails);
        }
    }

    public static Boolean deleteBook(ArrayList<String> bookDetails
                                    , ArrayList<String> publisherDetails
                                    , ArrayList<String> bankAccountDetails)
    {
        System.out.println("Editing book in DB...");
        return true;
    }

    /*
    Function:   getAllBooks
    Purpose:    query the DB for all books
    in:         runningList (running list of books found)
    return:     runningList (with any newly found books added)
    */
    public static ArrayList<String> getAllBooks(ArrayList<String> runningList)
    {
        String prepared_query = BOOK_QUERY;

        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PW);
            PreparedStatement prepStmt = conn.prepareStatement(prepared_query);
            ResultSet rs = prepStmt.executeQuery();

            Class.forName("org.postgresql.Driver");

            while(rs.next())
            {
                runningList = addElementToListNoDup(rs.getString("Name"), runningList);
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return runningList;
    }

    /*
    Function:   addElementToListNoDup
    Purpose:    add elements to the running list if they haven't previously been added
    in:         element (course to be compared against and added if not in list)
    in:         runningList (running list of prereq courses found)
    return:     runningList (updated accordingly)
    */
    public static ArrayList<String> addElementToListNoDup(String element, ArrayList<String> runningList)
    {   
        if (!runningList.contains(element))
        {
            runningList.add(element);
        }

        return runningList;
    }
}
