/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import java.sql.*;

import java.util.ArrayList;

public class CallDB
{
    static private final String DB_URL = "jdbc:postgresql://localhost:5433/lookinnabook";
    static private final String USER = "postgres";
    static private final String PW = "S##+d57750!9";
    static private final String BOOK_QUERY = "SELECT * FROM books";
    static private final String AUTHOR_QUERY = "SELECT * FROM author";
    static private final String AUTHOR_CREATE = "INSERT INTO author(first_name, middle_name, last_name, bio) VALUES(?,?,?,?)";
    static private final String PUBLISHER_QUERY = "SELECT * FROM publisher";
    static private final String PROVINCE_QUERY = "SELECT * FROM provincial_area";
    static private final String STREET_QUERY = "SELECT * FROM street_area";
    static private final String BANK_ACCOUNT_QUERY = "SELECT * FROM bank_account";

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
                    prepStmt.setString(i+1, entityDetails.get(i+1));
                }
                else
                {
                    prepStmt.setLong(i+1, Long.parseUnsignedLong(entityDetails.get(i+1)));
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

        return true;
    }

    public static void checkAndAddProvinceDetails(ArrayList<String> provinceDetails)
    {
        if (!provinceDetailsExist(provinceDetails))
        {
            addProvinceDetails(provinceDetails);
        }
    }

    protected static Boolean editProvinceDetails(ArrayList<String> provinceDetails)
    {
        System.out.println("Editing province details...");

        return true;
    }

    public static void checkAndEditProvinceDetails(ArrayList<String> provinceDetails)
    {
        if (!provinceDetailsExist(provinceDetails))
        {
            addProvinceDetails(provinceDetails);
        }
        else
        {
            editProvinceDetails(provinceDetails);
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
            return checkIfExists(streetDetails, prepared_street_query, new int[]{1,0,0,0});
        }

        return false;
    }

    protected static Boolean addStreetDetails(ArrayList<String> streetDetails)
    {
        System.out.println("Adding street details...");

        return true;
    }

    public static void checkAndAddStreetDetails(ArrayList<String> streetDetails)
    {
        if (!streetDetailsExist(streetDetails))
        {
            addStreetDetails(streetDetails);
        }
    }

    protected static Boolean editStreetDetails(ArrayList<String> streetDetails)
    {
        System.out.println("Editing street details...");

        return true;
    }

    public static void checkAndEditStreetDetails(ArrayList<String> streetDetails)
    {
        if (!streetDetailsExist(streetDetails))
        {
            addStreetDetails(streetDetails);
        }
        else
        {
            editStreetDetails(streetDetails);
        }
    }

    public static Boolean publisherExists(ArrayList<String> publisherDetails)
    {
        System.out.println("Checking for publisher...");
        String prepared_publisher_query = PUBLISHER_QUERY + " WHERE ID=?";

        if (!publisherDetails.get(0).isEmpty())
        {
            return checkIfExists(publisherDetails, prepared_publisher_query, new int[]{1});
        }

        return false;
    }

    protected static Boolean addPublisher(ArrayList<String> publisherDetails)
    {
        System.out.println("Adding publisher...");

        return true;
    }

    public static void checkAndAddPublisher(ArrayList<String> publisherDetails)
    {
        if (!publisherExists(publisherDetails))
        {
            addPublisher(publisherDetails);
        }
    }

    protected static Boolean editPublisher(ArrayList<String> publisherDetails)
    {
        System.out.println("Editing publisher...");

        return true;
    }

    public static void checkAndEditPublisher(ArrayList<String> publisherDetails)
    {
        if (!publisherExists(publisherDetails))
        {
            addPublisher(publisherDetails);
        }
        else
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

        return true;
    }

    public static void checkAndAddBankAccount(ArrayList<String> bankAccountDetails)
    {
        if (!bankAccountExists(bankAccountDetails))
        {
            addBankAccount(bankAccountDetails);
        }
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
            return checkIfExists(authorDetails, prepared_author_query, new int[]{0});
        }

        return false;
    }

    protected static Boolean addAuthor(ArrayList<String> authorDetails)
    {
        System.out.println("Adding author...");
        addItem(authorDetails, AUTHOR_CREATE, new int[]{0,0,0,0});

        return true;
    }

    public static void checkAndAddAuthor(ArrayList<String> authorDetails)
    {
        if (!authorExists(authorDetails))
        {
            addAuthor(authorDetails);
        }
    }

    protected static Boolean editAuthor(ArrayList<String> authorDetails)
    {
        System.out.println("Editing author...");

        return true;
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
        String prepared_book_query = BOOK_QUERY + " WHERE ISBN=?";
        
        if (bookDetails.get(0).charAt(0) != '_')
        {
            return checkIfExists(bookDetails, prepared_book_query, new int[]{0});
        }

        return false;
    }

    public static Boolean addBook(ArrayList<String> bookDetails)
    {
        System.out.println("Adding book...");

        return true;
    }

    public static void checkAndAddBook(ArrayList<String> bookDetails)
    {
        if (!bookExists(bookDetails))
        {
            addBook(bookDetails);
        }
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
        checkAndAddProvinceDetails(provinceDetails);
        checkAndAddStreetDetails(streetDetails);
        checkAndAddPublisher(publisherDetails);
        checkAndAddBankAccount(bankAccountDetails);
        checkAndAddAuthor(authorDetails);
        checkAndAddBook(bookDetails);
    }

    public static Boolean editBook(ArrayList<String> bookDetails
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
