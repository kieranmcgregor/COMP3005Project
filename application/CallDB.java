/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import java.sql.*;

import java.util.ArrayList;

public class CallDB
{
    static private final String DB_URL = "jdbc:postgresql://localhost:5432/lookinnabook";
    static private final String USER = "postgres";
    static private final String PW = "S##+d57750!9";
    static private final String BOOK_QUERY = "SELECT * FROM books";
    static private final String PUBLISHER_QUERY = "SELECT * FROM publisher";
    static private final String BANK_ACCOUNT_QUERY = "SELECT * FROM bank_account";

    public static Boolean checkIfExists(ArrayList<String> entityDetails, String prepared_statement, int numberOfVariables)
    {
        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PW);
            PreparedStatement prepStmt = conn.prepareStatement(prepared_statement);

            for (int i = 0; i < numberOfVariables; ++i)
            {
                prepStmt.setString(i+1, entityDetails.get(i));
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

    public static void addPublisher(ArrayList<String> publisherDetails)
    {
        System.out.println("Adding publisher...");
    }

    public static void addBankAccount(ArrayList<String> bankAccountDetails)
    {
        System.out.println("Adding bank account...");
    }

    public static void addBook(ArrayList<String> bookDetails)
    {
        System.out.println("Adding book...");
    }

    /*
    Function:   addBook
    Purpose:    to add a book and any other dependent entities to the DB
    in:         bookDetails (a list of details described by the book)
    return:     Boolean (true if addition is successful)
    */
    public static Boolean addBook(ArrayList<String> bookDetails
                                    , ArrayList<String> publisherDetails
                                    , ArrayList<String> bankAccountDetails)
    {
        System.out.println("Adding book to DB...");
        Boolean publisherExists = true;
        String prepared_publisher_query = PUBLISHER_QUERY + " WHERE ID=?";

        Boolean bankAccountExists = true;
        String prepared_bank_account_query = BANK_ACCOUNT_QUERY + " WHERE insitution_number=?, transit_number=?, account_number=?";

        Boolean bookExists = true;
        String prepared_book_query = BOOK_QUERY + " WHERE ISBN=?";

        publisherExists = checkIfExists(publisherDetails, prepared_publisher_query, 1);
        bankAccountExists = checkIfExists(bankAccountDetails, prepared_bank_account_query, 3);
        bookExists = checkIfExists(bookDetails, prepared_book_query, 1);

        if (!publisherExists)
        {
            addPublisher(publisherDetails);
        }

        if (!bankAccountExists)
        {
            addBankAccount(bankAccountDetails);
        }

        if (!bookExists)
        {
            addBook(bookDetails);
        }

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
