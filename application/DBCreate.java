/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import java.sql.*;

import java.util.*;
import java.math.*;

public class DBCreate
{
    static private final String BOOK_CREATE = "INSERT INTO books(isbn, title, genre, page_count, price, publisher_percentage, quantity, threshold, publisher_id, warehouse_id) VALUES(?,?,?,?,?,?,?,?,?,1)";
    static private final String AUTHORS_CREATE = "INSERT INTO authors(id, isbn) VALUES(?,?)";
    static private final String ADDS_CREATE = "INSERT INTO adds(username, isbn) VALUES(?,?)";
    static private final String AUTHOR_CREATE = "INSERT INTO author(first_name, middle_name, last_name, bio) VALUES(?,?,?,?)";
    static private final String PUBLISHER_CREATE = "INSERT INTO publisher(name, email, phone_number, number, street, postal_code, country) VALUES(?,?,?,?,?,?,?)";
    static private final String PROVINCE_CREATE = "INSERT INTO provincial_area(postal_code, city, province) VALUES(?,?,?)";
    static private final String STREET_CREATE = "INSERT INTO street_area(number, street, postal_code, country) VALUES(?,?,?,?)";
    static private final String BANK_ACCOUNT_CREATE = "INSERT INTO bank_account(institution_number, transit_number, account_number, balance, id) VALUES(?,?,?,0.00,?)";
    static private final String OWNER_CREATE = "INSERT INTO owner(username, password) VALUES(?,?)";
    static private final String USER_CREATE = "INSERT INTO users(username, password) VALUES(?,?)";

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean addItem(ArrayList<String> entityDetails
                                        , String preparedStatement
                                        , int[] stringIntFlag)
    {
        return addItem(entityDetails, preparedStatement, stringIntFlag, 0);
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean addItem(ArrayList<String> entityDetails
                                        , String prepared_statement
                                        , int[] stringIntFlag
                                        , int offset)
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

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean addProvinceDetails(ArrayList<String> provinceDetails)
    {
        System.out.println("Adding province details...");
        return addItem(provinceDetails, PROVINCE_CREATE, new int[]{0,0,0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Boolean checkAndAddProvinceDetails(ArrayList<String> provinceDetails)
    {
        Boolean exists = DBQuery.provinceDetailsExist(provinceDetails);
        if (!exists)
        {
            return addProvinceDetails(provinceDetails);
        }

        return exists;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean editProvinceDetails(ArrayList<String> provinceDetails)
    {
        System.out.println("Editing province details...");

        return true;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Boolean checkAndEditProvinceDetails(ArrayList<String> provinceDetails)
    {
        if (!DBQuery.provinceDetailsExist(provinceDetails))
        {
            return addProvinceDetails(provinceDetails);
        }
        else
        {
            return editProvinceDetails(provinceDetails);
        }
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean addStreetDetails(ArrayList<String> streetDetails)
    {
        System.out.println("Adding street details...");
        return addItem(streetDetails, STREET_CREATE, new int[]{0,0,0,0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Boolean checkAndAddStreetDetails(ArrayList<String> streetDetails)
    {
        Boolean exists = DBQuery.streetDetailsExist(streetDetails);
        if (!exists)
        {
            return addStreetDetails(streetDetails);
        }

        return exists;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean editStreetDetails(ArrayList<String> streetDetails)
    {
        System.out.println("Editing street details...");

        return true;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Boolean checkAndEditStreetDetails(ArrayList<String> streetDetails)
    {
        if (!DBQuery.streetDetailsExist(streetDetails))
        {
            return addStreetDetails(streetDetails);
        }
        else
        {
            return editStreetDetails(streetDetails);
        }
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Integer addPublisher(ArrayList<String> publisherDetails, ArrayList<String> streetDetails)
    {
        System.out.println("Adding publisher...");
        publisherDetails.addAll(streetDetails);
        if (addItem(publisherDetails, PUBLISHER_CREATE, new int[]{0,0,0,0,0,0,0}, 1))
        {
            return DBQuery.getLastEntryID(DBQuery.PUBLISHER_QUERY);
        }

        return -1;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Integer checkAndAddPublisher(ArrayList<String> publisherDetails, ArrayList<String> streetDetails)
    {
        if (!DBQuery.publisherExists(publisherDetails)
            && checkAndAddStreetDetails(streetDetails))
        {
            return addPublisher(publisherDetails, streetDetails);
        }

        return -1;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean editPublisher(ArrayList<String> publisherDetails)
    {
        System.out.println("Editing publisher...");

        return true;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static void checkAndEditPublisher(ArrayList<String> publisherDetails)
    {
        if (DBQuery.publisherExists(publisherDetails))
        {
            editPublisher(publisherDetails);
        }
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean addBankAccount(ArrayList<String> bankAccountDetails)
    {
        System.out.println("Adding bank account...");
        return addItem(bankAccountDetails, BANK_ACCOUNT_CREATE, new int[]{1,1,1,1});
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Boolean checkAndAddBankAccount(ArrayList<String> bankAccountDetails)
    {
        ArrayList<String> publisherID = new ArrayList<>(Arrays.asList(bankAccountDetails.get(3)));
        Boolean exists = DBQuery.bankAccountExists(bankAccountDetails);
        if (!exists && DBQuery.publisherExists(publisherID))
        {
            return addBankAccount(bankAccountDetails);
        }

        return exists;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean editBankAccount(ArrayList<String> bankAccountDetails)
    {
        System.out.println("Editing bank account...");
        return true;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static void checkAndEditBankAccount(ArrayList<String> bankAccountDetails)
    {
        if (!DBQuery.bankAccountExists(bankAccountDetails))
        {
            addBankAccount(bankAccountDetails);
        }
        else
        {
            editBankAccount(bankAccountDetails);
        }
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Integer addAuthor(ArrayList<String> authorDetails)
    {
        System.out.println("Adding author...");
        if (addItem(authorDetails, AUTHOR_CREATE, new int[]{0,0,0,0}, 1))
        {
            return DBQuery.getLastEntryID(DBQuery.AUTHOR_QUERY);
        }

        return -1;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Integer checkAndAddAuthor(ArrayList<String> authorDetails)
    {
        if (!DBQuery.authorExists(authorDetails))
        {
            return addAuthor(authorDetails);
        }

        return -1;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Boolean addBook(ArrayList<String> bookDetails)
    {
        System.out.println("Adding book...");
        return addItem(bookDetails, BOOK_CREATE, new int[]{0,0,0,1,2,2,1,1,1});
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Boolean checkAndAddBook(ArrayList<String> bookDetails)
    {
        Boolean exists = DBQuery.bookExists(bookDetails);
        if (!exists)
        {
            return addBook(bookDetails);
        }

        return exists;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static Boolean editBook(ArrayList<String> bookDetails)
    {
        System.out.println("Editing book...");

        return true;
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    public static void checkAndEditBook(ArrayList<String> bookDetails)
    {
        if (!DBQuery.bookExists(bookDetails))
        {
            addBook(bookDetails);
        }
        else
        {
            editBook(bookDetails);
        }
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean addAuthorLink(ArrayList<String> authorDetails, ArrayList<String> bookDetails)
    {
        System.out.println("Adding author authors book...");
        ArrayList<String> authorBookLink = new ArrayList<>(Arrays.asList(authorDetails.get(0), bookDetails.get(0)));
        return addItem(authorBookLink, AUTHORS_CREATE, new int[]{1,0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean addOwnerLink(ArrayList<String> bookDetails)
    {
        System.out.println("Adding owner adds book...");
        ArrayList<String> ownerBookLink = new ArrayList<>(Arrays.asList(LookInnaBook.getUsername(), bookDetails.get(0)));
        return addItem(ownerBookLink, ADDS_CREATE, new int[]{0,0});
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
        if (!DBQuery.publisherExists(publisherDetails))
        {
            checkAndAddProvinceDetails(provinceDetails);
            checkAndAddPublisher(publisherDetails, streetDetails);
            checkAndAddBankAccount(bankAccountDetails);
        }

        if (!DBQuery.authorExists(authorDetails))
        {
            addAuthor(authorDetails);
        }

        if (!DBQuery.bookExists(bookDetails))
        {
            addBook(bookDetails);
        }

        if (!DBQuery.authorLinkExists(authorDetails, bookDetails))
        {
            addAuthorLink(authorDetails, bookDetails);
        }

        if (!DBQuery.ownerLinkExists(bookDetails))
        {
            addOwnerLink(bookDetails);
        }
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean addOwner(ArrayList<String> ownerDetails)
    {
        System.out.println("Adding owner...");
        return addItem(ownerDetails, OWNER_CREATE, new int[]{0,0});
    }

    /*
    Function:   
    Purpose:    
    in:         
    return:     
    */
    protected static Boolean addUser(ArrayList<String> userDetails)
    {
        System.out.println("Adding user...");
        return addItem(userDetails, USER_CREATE, new int[]{0,0});
    }
}
