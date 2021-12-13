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
    static private final String BOOK_CREATE = "INSERT INTO books(isbn, title, genre, page_count, price,"+
                                                "publisher_percentage, quantity, threshold, publisher_id,"+
                                                "warehouse_id) VALUES(?,?,?,?,?,?,?,?,?,1)";
    static private final String AUTHORS_CREATE = "INSERT INTO authors(id, isbn) VALUES(?,?)";
    static private final String ADDS_CREATE = "INSERT INTO adds(username, isbn) VALUES(?,?)";
    static private final String AUTHOR_CREATE = "INSERT INTO author(first_name, middle_name, last_name, bio)"+
                                                    " VALUES(?,?,?,?)";
    static private final String PUBLISHER_CREATE = "INSERT INTO publisher(name,email,phone_number,number,street,"+
                                                    "postal_code, country)"+
                                                    " VALUES(?,?,?,?,?,?,?)";
    static private final String PROVINCE_CREATE = "INSERT INTO provincial_area(postal_code,city,province)"+
                                                    " VALUES(?,?,?)";
    static private final String STREET_CREATE = "INSERT INTO street_area(number,street,postal_code,country)"+
                                                    " VALUES(?,?,?,?)";
    static private final String BANK_ACCOUNT_CREATE = "INSERT INTO bank_account(institution_number,transit_number,"+
                                                        "account_number,balance,id) VALUES(?,?,?,0.00,?)";
    static private final String OWNER_CREATE = "INSERT INTO owner(username, password) VALUES(?,?)";
    static private final String USER_CREATE = "INSERT INTO users(username, password) VALUES(?,?)";
    static private final String SELECTS_CREATE = "INSERT INTO selects(quantity, username, isbn) VALUES(?,?,?)";
    static private final String BOOK_ORDER_CREATE = "INSERT INTO book_order(shipping_state,username,warehouse_id,"+
                                                "shipping_service_id) VALUES(?,?,1,1)";
    static private final String ORDERS_CREATE = "INSERT INTO orders(order_number,isbn,quantity) VALUES(?,?,?)";
    static private final String ORDER_ADDRESSES_CREATE = "INSERT INTO order_addresses(order_number,shipping_number"+
                                                            ",shipping_street,shipping_postal_code,shipping_country"+
                                                            ",billing_number,billing_street,billing_postal_code"+
                                                            ",billing_country,current_number,current_street"+
                                                            ",current_postal_code,current_country)"+
                                                            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /*
    Function:   addItem
    Purpose:    helper method to add an new tuple to a table
    in:         entityDetails (tuple attribute details)
    in:         prepared_statement (statement to be used to create new tuple)
    in:         stringIntFlag (mask to identify attribute type)
    return:     true if created false otherwise
    */
    protected static Boolean addItem(ArrayList<String> entityDetails
                                        , String preparedStatement
                                        , int[] stringIntFlag)
    {
        return addItem(entityDetails, preparedStatement, stringIntFlag, 0);
    }

    /*
    Function:   addItem
    Purpose:    add an new tuple to a table
    in:         entityDetails (tuple attribute details)
    in:         prepared_statement (statement to be used to create new tuple)
    in:         stringIntFlag (mask to identify attribute type)
    in:         offset (used if first entry is not used in creation)
    return:     true if created false otherwise
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
                    prepStmt.setLong(i+1, Long.parseUnsignedLong(entityDetails.get(i+offset)));
                }
                else
                {
                    prepStmt.setBigDecimal(i+1, new BigDecimal(entityDetails.get(i+offset)));
                }
            }

            System.out.println(prepStmt);
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
    Function:   addProvinceDetails
    Purpose:    add province to province_area table
    in:         provinceDetails (attributes to add new province)
    return:     true if created
    */
    protected static Boolean addProvinceDetails(ArrayList<String> provinceDetails)
    {
        System.out.println("Adding province details...");
        return addItem(provinceDetails, PROVINCE_CREATE, new int[]{0,0,0});
    }

    /*
    Function:   checkAndAddProvinceDetails
    Purpose:    check if province exists and if not add province to province_area table
    in:         provinceDetails (attributes to add new province)
    return:     true if created
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
    Function:   checkAndEditProvinceDetails
    Purpose:    check if province exists and if not add province to province_area table else edit province
    in:         provinceDetails (attributes to add new province)
    return:     true if created
    */
    public static Boolean checkAndEditProvinceDetails(ArrayList<String> provinceDetails)
    {
        if (!DBQuery.provinceDetailsExist(provinceDetails))
        {
            return addProvinceDetails(provinceDetails);
        }
        else
        {
            System.out.println("Editing province details...");
            return true;
        }
    }

    /*
    Function:   addStreetDetails
    Purpose:    add street to street_area table
    in:         streetDetails (attributes to add new street)
    return:     true if created
    */
    protected static Boolean addStreetDetails(ArrayList<String> streetDetails)
    {
        System.out.println("Adding street details...");
        return addItem(streetDetails, STREET_CREATE, new int[]{0,0,0,0});
    }

    /*
    Function:   checkAndAddStreetDetails
    Purpose:    check if street exists and if not add street to street_area table
    in:         streetDetails (attributes to add new street)
    return:     true if created
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
    Function:   checkAndEditStreetDetails
    Purpose:    check if street exists and if not add street to street_area table else edit street
    in:         streetDetails (attributes to add new street)
    return:     true if created
    */
    public static Boolean checkAndEditStreetDetails(ArrayList<String> streetDetails)
    {
        if (!DBQuery.streetDetailsExist(streetDetails))
        {
            return addStreetDetails(streetDetails);
        }
        else
        {
            System.out.println("Editing street details...");
            return true;
        }
    }

    /*
    Function:   addPublisher
    Purpose:    add publisher to publisher table
    in:         publisherDetails (attributes to add new publisher)
    in:         streetDetails (attributes to add new street)
    return:     true if created
    */
    protected static Integer addPublisher(ArrayList<String> publisherDetails, ArrayList<String> streetDetails)
    {
        System.out.println("Adding publisher...");
        publisherDetails.addAll(streetDetails);
        if (addItem(publisherDetails, PUBLISHER_CREATE, new int[]{0,0,0,0,0,0,0}, 1))
        {
            return DBQuery.getLastEntryID(DBQuery.PUBLISHER_QUERY + " ORDER BY id");
        }

        return -1;
    }

    /*
    Function:   checkAndAddPublisher
    Purpose:    check if publihser exists and if not add publisher to publisher table
    in:         publisherDetails (attributes to add new publisher)
    in:         streetDetails (attributes to add new street)
    return:     true if created
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
    Function:   checkAndEditPublisher
    Purpose:    check if publisher exists and if not add publisher to publisher table else edit publisher
    in:         publisherDetails (attributes to add new publisher)
    return:     true if created
    */
    public static void checkAndEditPublisher(ArrayList<String> publisherDetails)
    {
        if (DBQuery.publisherExists(publisherDetails))
        {
            System.out.println("Editing publisher...");
        }
    }

    /*
    Function:   addBankAccount
    Purpose:    add bank account to bank_account table
    in:         bankAccountDetails (attributes to add new bank account)
    return:     true if created
    */
    protected static Boolean addBankAccount(ArrayList<String> bankAccountDetails)
    {
        System.out.println("Adding bank account...");
        return addItem(bankAccountDetails, BANK_ACCOUNT_CREATE, new int[]{1,1,1,1});
    }

    /*
    Function:   checkAndAddBankAccount
    Purpose:    check if bank account exists and if not add bank account to bank account table
    in:         bankAccountDetails (attributes to add new bank account)
    return:     true if created
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
    Function:   checkAndEditBankAccount
    Purpose:    check if bank account exists and if not add bank account to bank account table else edit bank account
    in:         bankAccountDetails (attributes to add new bank account)
    return:     true if created
    */
    public static void checkAndEditBankAccount(ArrayList<String> bankAccountDetails)
    {
        if (!DBQuery.bankAccountExists(bankAccountDetails))
        {
            addBankAccount(bankAccountDetails);
        }
        else
        {
            System.out.println("Editing bank account...");
        }
    }

    /*
    Function:   addAuthor
    Purpose:    add author to author table
    in:         authorDetails (attributes to add new author)
    return:     true if created
    */
    protected static Integer addAuthor(ArrayList<String> authorDetails)
    {
        System.out.println("Adding author...");
        if (addItem(authorDetails, AUTHOR_CREATE, new int[]{0,0,0,0}, 1))
        {
            return DBQuery.getLastEntryID(DBQuery.AUTHOR_QUERY + " ORDER BY id");
        }

        return -1;
    }

    /*
    Function:   checkAndAddAuthor
    Purpose:    check if author exists and if not add author to author table
    in:         authorDetails (attributes to add new author)
    return:     true if created
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
    Function:   addBook
    Purpose:    add book to books table
    in:         bookDetails (attributes to add new book)
    return:     true if created
    */
    public static Boolean addBook(ArrayList<String> bookDetails)
    {
        System.out.println("Adding book...");
        return addItem(bookDetails, BOOK_CREATE, new int[]{0,0,0,1,2,2,1,1,1});
    }

    /*
    Function:   checkAndAddBook
    Purpose:    check if book exists and if not add book to book table
    in:         bookDetails (attributes to add new book)
    return:     true if created
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
    Function:   checkAndEditBook
    Purpose:    check if book exists, if not add book, else update book
    in:         bookDetails (attributes to add/update book)  
    */
    public static void checkAndEditBook(ArrayList<String> bookDetails)
    {
        if (!DBQuery.bookExists(bookDetails))
        {
            addBook(bookDetails);
        }
        else
        {
            System.out.println("Editing book...");
        }
    }

    /*
    Function:   addAuthorLink
    Purpose:    add author authors book to authors table
    in:         authorDetails (attributes to add new author)
    in:         bookDetails (attributes to add new book)
    return:     true if created
    */
    protected static Boolean addAuthorLink(ArrayList<String> authorDetails, ArrayList<String> bookDetails)
    {
        System.out.println("Adding author authors book...");
        ArrayList<String> authorBookLink = new ArrayList<>(Arrays.asList(authorDetails.get(0), bookDetails.get(0)));
        return addItem(authorBookLink, AUTHORS_CREATE, new int[]{1,0});
    }

    /*
    Function:   addOwnerLink
    Purpose:    add order addresses to order_addresses table
    in:         bookDetails (attributes to add new book to bookorder)
    return:     true if created
    */
    protected static Boolean addOwnerLink(ArrayList<String> bookDetails)
    {
        System.out.println("Adding owner adds book...");
        ArrayList<String> ownerBookLink = new ArrayList<>(Arrays.asList(LookInnaBook.getUsername(), bookDetails.get(0)));
        return addItem(ownerBookLink, ADDS_CREATE, new int[]{0,0});
    }


    /*
    Function:   checkAndAddAllBookDetails
    Purpose:    to add a book and any other dependent entities to the DB
    in:         provinceDetails (a list of details described by the province)
    in:         streetDetails (a list of details described by the street)
    in:         publisherDetails (a list of details described by the publisher)
    in:         bankAccountDetails (a list of details described by the bank account)
    in:         authorDetails (a list of details described by the author)
    in:         bookDetails (a list of details described by the book)
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
    Function:   addOwner
    Purpose:    add owner to owner table
    in:         ownerDetails (attributes to add new owner)
    return:     true if created
    */
    protected static Boolean addOwner(ArrayList<String> ownerDetails)
    {
        System.out.println("Adding owner...");
        return addItem(ownerDetails, OWNER_CREATE, new int[]{0,0});
    }

    /*
    Function:   addUser
    Purpose:    add user to user table
    in:         userDetails (attributes to add new user)
    return:     true if created
    */
    protected static Boolean addUser(ArrayList<String> userDetails)
    {
        System.out.println("Adding user...");
        return addItem(userDetails, USER_CREATE, new int[]{0,0});
    }

    /*
    Function:   addBooksToBasket
    Purpose:    add books to selects table
    in:         selectionDetails (attributes to add new books to basket)
    return:     true if created
    */
    protected static Boolean addBooksToBasket(ArrayList<String> selectionDetails)
    {
        System.out.println("Adding book to basket...");
        return addItem(selectionDetails, SELECTS_CREATE, new int[]{1,0,0});
    }

    /*
    Function:   orderBooks
    Purpose:    add books to book_order table
    in:         orderDetails (attributes to add new book to bookorder)
    return:     order_number
    */
    protected static Integer orderBooks(ArrayList<String> orderDetails)
    {
        Random r = new Random();
        System.out.println("Ordering books...");
        orderDetails.add(0, LookInnaBook.orderStates.get(r.nextInt(3)));

        if (addItem(orderDetails, BOOK_ORDER_CREATE, new int[]{0,0}))
        {
            return DBQuery.getLastEntryOrderNumber(DBQuery.BOOK_ORDER_QUERY + " ORDER BY order_number");
        }

        return -1;
    }

    /*
    Function:   addBooksToOrder
    Purpose:    add username orders books to orders table and update books quantity, publisher balance and empty basket
    in:         orderNumber (attributes to add new username orders books to orders table)
    */
    protected static void addBooksToOrder(Integer orderNumber)
    {
        System.out.println("Adding books to order...");
        ArrayList<ArrayList<String>> booksInBasket = DBQuery.getAllSelectedUsernameBooks(new ArrayList<>(Arrays.asList(LookInnaBook.getUsername())));

        for (ArrayList<String> bookInBasket : booksInBasket)
        {
            ArrayList<String> orderedBookDetails = new ArrayList<>(Arrays.asList(orderNumber.toString()));
            orderedBookDetails.add(bookInBasket.get(1));
            orderedBookDetails.add(bookInBasket.get(2));
            addItem(orderedBookDetails, ORDERS_CREATE, new int[]{1,0,1});

            // Sell books
            DBUpdate.sellBooks(new ArrayList<String>(Arrays.asList(bookInBasket.get(2), bookInBasket.get(1))));

            // Add Percentage to publisher account
            ArrayList<String> priceAndPercentage = DBQuery.getBookPriceAndPercentage(bookInBasket.get(1));
            Double paymentAmount = Double.parseDouble(priceAndPercentage.get(0)) * Double.parseDouble(priceAndPercentage.get(1)) * Double.parseDouble(bookInBasket.get(2));
            DBUpdate.payPublisher(paymentAmount, DBQuery.getBookPublisher(bookInBasket.get(1)).get(0));
            
            // Delete book from basket
            DBDelete.deleteBookFromSelects(new ArrayList<>(Arrays.asList(bookInBasket.get(0)
                                                                            ,bookInBasket.get(1))));
        }
    }

    /*
    Function:   addAddressesToOrder
    Purpose:    add order addresses to order_addresses table
    in:         orderDetails (attributes to add new book to bookorder)
    */
    protected static void addAddressesToOrder(Integer orderNumber
                                                , ArrayList<String> shippingStreetDetails
                                                , ArrayList<String> billingStreetDetails)
    {
        System.out.println("Adding addressses to order...");
        ArrayList<String> currentStreetDetails = new ArrayList<String>(shippingStreetDetails);
        ArrayList<String> order = DBQuery.getOrder(orderNumber);
        
        if (order.get(1).equals("picking"))
        {
            System.out.println("Order is being picked...");
            currentStreetDetails = DBQuery.getWarehouseStreetAddress(order.get(4));
        }
        else if (order.get(1).equals("shipping"))
        {
            System.out.println("Order is being shipped...");
            currentStreetDetails = DBQuery.getShippingServiceStreetAddress(order.get(5));
        }

        billingStreetDetails.addAll(currentStreetDetails);
        shippingStreetDetails.addAll(billingStreetDetails);
        shippingStreetDetails.add(0, orderNumber.toString());

        addItem(shippingStreetDetails, ORDER_ADDRESSES_CREATE, new int[]{1,0,0,0,0,0,0,0,0,0,0,0,0});
    }
}
