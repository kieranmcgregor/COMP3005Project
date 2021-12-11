/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserViewFrame extends JFrame implements ActionListener
{
    ArrayList<Component> bookResults = new ArrayList<Component>();
    ArrayList<Component> selectedResults = new ArrayList<Component>();
    ArrayList<Component> historyResults = new ArrayList<Component>();
    Container container = getContentPane();
    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel pageTitleLabel = new JLabel("LookInnaBook: User");

    // Create CUD tab components
    JPanel searchPane = new JPanel();
    JScrollPane searchScrollPane = new JScrollPane(searchPane);
    JPanel orderPane = new JPanel();
    JScrollPane orderScrollPane = new JScrollPane(orderPane);
    JPanel historyPane = new JPanel();
    JScrollPane historyScrollPane = new JScrollPane(historyPane);

    // Create work tab labels
    // Create Book labels
    JLabel isbnLabel = new JLabel("ISBN:");
    JLabel titleLabel = new JLabel("Title:");
    JLabel genreLabel = new JLabel("Genre:");
    JLabel pageCountLabel = new JLabel("Page count:");
    JLabel priceLabel = new JLabel("Price:");

    // Create Author labels
    JLabel authorFirstNameLabel = new JLabel("Author First Name:");
    JLabel authorMiddleNameLabel = new JLabel("Author Middle Name:");
    JLabel authorLastNameLabel = new JLabel("Author Last Name:");

    // Create publisher labels
    JLabel publisherNameLabel = new JLabel("Publisher name:");
    JLabel publisherEmailLabel = new JLabel("Publisher email:");
    JLabel publisherPhoneNumberLabel = new JLabel("Publisher phone number:");
    JLabel publisherStreetNumberLabel = new JLabel("Publisher street number:");
    JLabel publisherStreetLabel = new JLabel("Publisher street:");
    JLabel publisherCityLabel = new JLabel("Publisher city:");
    JLabel publisherProvinceLabel = new JLabel("Publisher province:");
    JLabel publisherPostalCodeLabel = new JLabel("Publisher postal code:");
    JLabel publisherCountryLabel = new JLabel("Publisher country:");

    // Create work tab text fields
    // Create book text fields
    MaskFormatter isbnMask = createFormatter("###-#-##-######-#", '_');
    JFormattedTextField isbnTextField = new JFormattedTextField(isbnMask);

    JTextField titleTextField = new JTextField();
    JTextField genreTextField = new JTextField();

    NumberFormat amountFormat = NumberFormat.getNumberInstance();
    JFormattedTextField pageCountTextField = new JFormattedTextField(amountFormat);

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
    JFormattedTextField priceTextField = new JFormattedTextField(currencyFormat);

    // Create author text fields
    JTextField authorFirstNameTextField = new JTextField();
    JTextField authorMiddleNameTextField = new JTextField();
    JTextField authorLastNameTextField = new JTextField();
    JTextArea authorBioTextArea = new JTextArea();


    // Create publisher text fields
    JTextField publisherNameTextField = new JTextField();
    JTextField publisherEmailTextField = new JTextField();
    
    MaskFormatter phoneNumberMask = createFormatter("##-###-###-####", '_');
    JFormattedTextField publisherPhoneNumberTextField = new JFormattedTextField(phoneNumberMask);
    JTextField publisherStreetNumberTextField = new JTextField();
    JTextField publisherStreetTextField = new JTextField();
    JTextField publisherCityTextField = new JTextField();
    JTextField publisherProvinceTextField = new JTextField();

    MaskFormatter postalCodeMask = createFormatter("?#?#?#", '_');
    JFormattedTextField publisherPostalCodeTextField = new JFormattedTextField(postalCodeMask);
    JTextField publisherCountryTextField = new JTextField();

    JButton searchButton = new JButton("Search");
    JButton clearButton = new JButton("Clear Criteria");

    // Order Pane components
    // Create shipping labels
    JLabel shippingStreetNumberLabel = new JLabel("Shipping street number:");
    JLabel shippingStreetLabel = new JLabel("Shipping street:");
    JLabel shippingCityLabel = new JLabel("Shipping city:");
    JLabel shippingProvinceLabel = new JLabel("Shipping province:");
    JLabel shippingPostalCodeLabel = new JLabel("Shipping postal code:");
    JLabel shippingCountryLabel = new JLabel("Shipping country:");

    // Create shipping Fields
    JTextField shippingStreetNumberTextField = new JTextField();
    JTextField shippingStreetTextField = new JTextField();
    JTextField shippingCityTextField = new JTextField();
    JTextField shippingProvinceTextField = new JTextField();
    JFormattedTextField shippingPostalCodeTextField = new JFormattedTextField(postalCodeMask);
    JTextField shippingCountryTextField = new JTextField();

    // Create billing labels
    JLabel billingStreetNumberLabel = new JLabel("Billing street number:");
    JLabel billingStreetLabel = new JLabel("Billing street:");
    JLabel billingCityLabel = new JLabel("Billing city:");
    JLabel billingProvinceLabel = new JLabel("Billing province:");
    JLabel billingPostalCodeLabel = new JLabel("Billing postal code:");
    JLabel billingCountryLabel = new JLabel("Billing country:");

    // Create billing Fields
    JTextField billingStreetNumberTextField = new JTextField();
    JTextField billingStreetTextField = new JTextField();
    JTextField billingCityTextField = new JTextField();
    JTextField billingProvinceTextField = new JTextField();
    JFormattedTextField billingPostalCodeTextField = new JFormattedTextField(postalCodeMask);
    JTextField billingCountryTextField = new JTextField();

    JCheckBox billingIsShipping = new JCheckBox("Billing address is same as shipping address");

    JButton checkoutButton = new JButton("Checkout");
    JButton clearOrderButton = new JButton("Clear Order");

    // Constructor
    UserViewFrame()
    {
        System.out.println(LookInnaBook.getUsername());
        System.out.println(LookInnaBook.isUser());

        setLayoutManager();
        setLocationAndSize();
        setUniqueAttributes();
        addComponentsToContainer();
        addActionEvent();

        displaySelectedBooks();
        displayOrderHistory();
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected static MaskFormatter createFormatter(final String mask, char replacementChar)
    {
        try
        {
            MaskFormatter newMask = new MaskFormatter ( mask );
            newMask.setPlaceholderCharacter(replacementChar);
            return newMask;
        }
        catch ( final ParseException e )
        {
            throw new RuntimeException ( "Unable to parse formatter mask: " + mask, e );
        }
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void setLayoutManager()
    {
        container.setLayout(null);

        searchPane.setLayout(null);
        orderPane.setLayout(null);
        historyPane.setLayout(null);
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void setLocationAndSize()
    {
        // Set location and size of each component
        pageTitleLabel.setBounds(225, 25, 500, 60);

        tabbedPane.setBounds(25, 100, 735, 440);

        // Position the work tab components
        // Position book labels
        isbnLabel.setBounds(10, 25, 150, 30);
        titleLabel.setBounds(10, 60, 150, 30);
        genreLabel.setBounds(10, 95, 150, 30);
        pageCountLabel.setBounds(10, 130, 150, 30);
        priceLabel.setBounds(10, 165, 150, 30);

        // Position author labels
        authorFirstNameLabel.setBounds(10, 220, 150, 30);
        authorMiddleNameLabel.setBounds(10, 255, 150, 30);
        authorLastNameLabel.setBounds(10, 290, 150, 30);

        // Position publisher labels
        publisherNameLabel.setBounds(360, 25, 150, 30);
        publisherEmailLabel.setBounds(360, 60, 150, 30);
        publisherPhoneNumberLabel.setBounds(360, 95, 150, 30);

        // Position Publisher Address Labels
        publisherStreetNumberLabel.setBounds(360, 130, 150, 30);
        publisherStreetLabel.setBounds(360, 165, 150, 30);
        publisherCityLabel.setBounds(360, 200, 150, 30);
        publisherProvinceLabel.setBounds(360, 235, 150, 30);
        publisherPostalCodeLabel.setBounds(360, 270, 150, 30);
        publisherCountryLabel.setBounds(360, 305, 150, 30);

        // Position book fields
        isbnTextField.setBounds(185, 25, 150, 30);
        titleTextField.setBounds(185, 60, 150, 30);
        genreTextField.setBounds(185, 95, 150, 30);
        pageCountTextField.setBounds(185, 130, 150, 30);
        priceTextField.setBounds(185, 165, 150, 30);

        // Position author fields
        authorFirstNameTextField.setBounds(185, 220, 150, 30);
        authorMiddleNameTextField.setBounds(185, 255, 150, 30);
        authorLastNameTextField.setBounds(185, 290, 150, 30);

        // Poaition publisher fields
        publisherNameTextField.setBounds(535, 25, 150, 30);
        publisherEmailTextField.setBounds(535, 60, 150, 30);
        publisherPhoneNumberTextField.setBounds(535, 95, 150, 30);

        // Position publisher address fields
        publisherStreetNumberTextField.setBounds(535, 130, 150, 30);
        publisherStreetTextField.setBounds(535, 165, 150, 30);
        publisherCityTextField.setBounds(535, 200, 150, 30);
        publisherProvinceTextField.setBounds(535, 235, 150, 30);
        publisherPostalCodeTextField.setBounds(535, 270, 150, 30);
        publisherCountryTextField.setBounds(535, 305, 150, 30);

        searchButton.setBounds(200, 355, 150, 30);
        clearButton.setBounds(400, 355, 150, 30);

        // Order Tab
        // Position shipping address labels
        shippingStreetNumberLabel.setBounds(10, 25, 150, 30);
        shippingStreetLabel.setBounds(10, 60, 150, 30);
        shippingCityLabel.setBounds(10, 95, 150, 30);
        shippingProvinceLabel.setBounds(10, 130, 150, 30);
        shippingPostalCodeLabel.setBounds(10, 165, 150, 30);
        shippingCountryLabel.setBounds(10, 200, 150, 30);

        // Position shipping address fields
        shippingStreetNumberTextField.setBounds(185, 25, 150, 30);
        shippingStreetTextField.setBounds(185, 60, 150, 30);
        shippingCityTextField.setBounds(185, 95, 150, 30);
        shippingProvinceTextField.setBounds(185, 130, 150, 30);
        shippingPostalCodeTextField.setBounds(185, 165, 150, 30);
        shippingCountryTextField.setBounds(185, 200, 150, 30);

        // position shipping = billing checkbox
        billingIsShipping.setBounds(25, 235, 300, 30);

        // Position billing address labels
        billingStreetNumberLabel.setBounds(360, 25, 150, 30);
        billingStreetLabel.setBounds(360, 60, 150, 30);
        billingCityLabel.setBounds(360, 95, 150, 30);
        billingProvinceLabel.setBounds(360, 130, 150, 30);
        billingPostalCodeLabel.setBounds(360, 165, 150, 30);
        billingCountryLabel.setBounds(360, 200, 150, 30);

        // Position billing address fields
        billingStreetNumberTextField.setBounds(535, 25, 150, 30);
        billingStreetTextField.setBounds(535, 60, 150, 30);
        billingCityTextField.setBounds(535, 95, 150, 30);
        billingProvinceTextField.setBounds(535, 130, 150, 30);
        billingPostalCodeTextField.setBounds(535, 165, 150, 30);
        billingCountryTextField.setBounds(535, 200, 150, 30);

        checkoutButton.setBounds(200, 285, 150, 30);
        clearOrderButton.setBounds(400, 285, 150, 30);
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void setUniqueAttributes()
    {
        // Set title font and size
        pageTitleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));

        // Flood book labels right
        isbnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        genreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pageCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Flood author labels right
        authorFirstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        authorMiddleNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        authorLastNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Flood publisher labels right
        publisherNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherPhoneNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherStreetNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherStreetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherCityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherProvinceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherPostalCodeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherCountryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Flood shipping labels right
        shippingStreetNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        shippingStreetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        shippingCityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        shippingProvinceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        shippingPostalCodeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        shippingCountryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Flood billing labels right
        billingStreetNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        billingStreetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        billingCityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        billingProvinceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        billingPostalCodeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        billingCountryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Set search pane vertical scroll
        searchPane.setPreferredSize(new Dimension(700, 410));
        searchScrollPane.setPreferredSize(new Dimension(700, 400));

        // Set order pane vertical scroll
        orderPane.setPreferredSize(new Dimension(700, 410));
        orderScrollPane.setPreferredSize(new Dimension(700, 400));

        // Set history pane vertical scroll
        historyPane.setPreferredSize(new Dimension(700, 410));
        historyScrollPane.setPreferredSize(new Dimension(700, 400));
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void addComponentsToTopPane()
    {
        // Add book labels to work tab
        searchPane.add(isbnLabel);
        searchPane.add(titleLabel);
        searchPane.add(genreLabel);
        searchPane.add(pageCountLabel);
        searchPane.add(priceLabel);

        // Add author labels to work tab
        searchPane.add(authorFirstNameLabel);
        searchPane.add(authorMiddleNameLabel);
        searchPane.add(authorLastNameLabel);

        // Add publisher labels to work tab
        searchPane.add(publisherNameLabel);
        searchPane.add(publisherEmailLabel);
        searchPane.add(publisherPhoneNumberLabel);
        searchPane.add(publisherStreetNumberLabel);
        searchPane.add(publisherStreetLabel);
        searchPane.add(publisherCityLabel);
        searchPane.add(publisherProvinceLabel);
        searchPane.add(publisherPostalCodeLabel);
        searchPane.add(publisherCountryLabel);

        // Add fields to work tab
        // Add publisher fields
        searchPane.add(isbnTextField);
        searchPane.add(titleTextField);
        searchPane.add(genreTextField);
        searchPane.add(pageCountTextField);
        searchPane.add(priceTextField);

        // Add author fields
        searchPane.add(authorFirstNameTextField);
        searchPane.add(authorMiddleNameTextField);
        searchPane.add(authorLastNameTextField);

        // Add publisher fields
        searchPane.add(publisherNameTextField);
        searchPane.add(publisherEmailTextField);
        searchPane.add(publisherPhoneNumberTextField);
        searchPane.add(publisherStreetNumberTextField);
        searchPane.add(publisherStreetTextField);
        searchPane.add(publisherCityTextField);
        searchPane.add(publisherProvinceTextField);
        searchPane.add(publisherPostalCodeTextField);
        searchPane.add(publisherCountryTextField);

        searchPane.add(searchButton);
        searchPane.add(clearButton);

        // Add shipping address fields
        orderPane.add(shippingStreetNumberLabel);
        orderPane.add(shippingStreetLabel);
        orderPane.add(shippingCityLabel);
        orderPane.add(shippingProvinceLabel);
        orderPane.add(shippingPostalCodeLabel);
        orderPane.add(shippingCountryLabel);

        // Add shipping address fields
        orderPane.add(shippingStreetNumberTextField);
        orderPane.add(shippingStreetTextField);
        orderPane.add(shippingCityTextField);
        orderPane.add(shippingProvinceTextField);
        orderPane.add(shippingPostalCodeTextField);
        orderPane.add(shippingCountryTextField);

        // Add shipping = billing address checkbox
        orderPane.add(billingIsShipping);

        // Add billing address fields
        orderPane.add(billingStreetNumberLabel);
        orderPane.add(billingStreetLabel);
        orderPane.add(billingCityLabel);
        orderPane.add(billingProvinceLabel);
        orderPane.add(billingPostalCodeLabel);
        orderPane.add(billingCountryLabel);

        // Add billing address fields
        orderPane.add(billingStreetNumberTextField);
        orderPane.add(billingStreetTextField);
        orderPane.add(billingCityTextField);
        orderPane.add(billingProvinceTextField);
        orderPane.add(billingPostalCodeTextField);
        orderPane.add(billingCountryTextField);

        orderPane.add(checkoutButton);
        orderPane.add(clearOrderButton);
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void addComponentsToContainer()
    {
        // Add components to view
        container.add(pageTitleLabel);

        addComponentsToTopPane();

        // Add tabs to tabbed pane
        tabbedPane.addTab("Search", searchScrollPane);
        tabbedPane.addTab("Order", orderScrollPane);
        tabbedPane.addTab("History", historyScrollPane);

        // Add search and results pane to container
        // container.add(searchPane);
        container.add(tabbedPane);
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void addActionEvent()
    {
        searchButton.addActionListener(this);
        clearButton.addActionListener(this);

        checkoutButton.addActionListener(this);
        clearOrderButton.addActionListener(this);
        billingIsShipping.addActionListener(this);
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void clearAuthor()
    {
        authorFirstNameTextField.setText("");
        authorMiddleNameTextField.setText("");
        authorLastNameTextField.setText("");
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void clearAddress()
    {
        publisherStreetNumberTextField.setText("");
        publisherStreetTextField.setText("");
        publisherCityTextField.setText("");
        publisherProvinceTextField.setText("");
        publisherPostalCodeTextField.setText("");
        publisherCountryTextField.setText("");
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void clearPublisher()
    {
        publisherNameTextField.setText("");
        publisherEmailTextField.setText("");
        publisherPhoneNumberTextField.setText("");
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void clearBook()
    {
        isbnTextField.setText("");
        titleTextField.setText("");
        genreTextField.setText("");
        pageCountTextField.setText("");
        priceTextField.setText("");

        clearAuthor();
        clearAddress();
        clearPublisher();
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public void clearOrder()
    {
        shippingStreetNumberTextField.setText("");
        shippingStreetTextField.setText("");
        shippingCityTextField.setText("");
        shippingProvinceTextField.setText("");
        shippingPostalCodeTextField.setText("");
        shippingCountryTextField.setText("");

        billingStreetNumberTextField.setText("");
        billingStreetTextField.setText("");
        billingCityTextField.setText("");
        billingProvinceTextField.setText("");
        billingPostalCodeTextField.setText("");
        billingCountryTextField.setText("");
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public String convertFieldValue(JFormattedTextField fieldForConversion)
    {
        if (!fieldForConversion.getText().trim().isEmpty())
        {
            return fieldForConversion.getValue().toString();
        }

        return fieldForConversion.getText().trim();
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected void displayBooks(ArrayList<ArrayList<String>> listOfBooks)
    {
        int yPos = 410;

        // Remove previous search results
        for (Component component : bookResults)
        {
            searchPane.remove(component);
        }

        for (int i = 0; i < listOfBooks.size(); ++i)
        {
            System.out.println("Adding New Book...");

            yPos = (410 + i*35);
            String bookDetails = new String();
            ArrayList<String> bookAttributes = listOfBooks.get(i);

            // Build buttons for book selection
            JButton button = new JButton(bookAttributes.get(0));
            button.addActionListener(this);

            // Build quantity fields
            JFormattedTextField quantityField = new JFormattedTextField(amountFormat);
            quantityField.setName(bookAttributes.get(0));

            // Build other book details
            for (int j = 1; j < bookAttributes.size(); ++j)
            {
                if (j == 4)
                {
                    bookDetails += "$";
                }

                bookDetails += bookAttributes.get(j);

                if (j == 3)
                {
                    bookDetails += " pages";
                }

                if (j < bookAttributes.size()-1)
                {
                    bookDetails += ", ";
                }
            }
            JLabel roDetails = new JLabel(bookDetails);
            roDetails.setName(bookAttributes.get(0));

            // Set component bounds
            button.setBounds(25, yPos, 150, 30);
            quantityField.setBounds(180, yPos, 50, 30);
            roDetails.setBounds(235, yPos, 400, 30);

            // Add components to search pane
            searchPane.add(button);
            searchPane.add(quantityField);
            searchPane.add(roDetails);

            // Add components to array list
            bookResults.add(button);
            bookResults.add(quantityField);
            bookResults.add(roDetails);
        }

        searchPane.setPreferredSize(new Dimension(400, yPos + 55));
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected void displaySelectedBooks()
    {
        ArrayList<ArrayList<String>> listOfBooks = DBQuery.getAllSelectedBooksOfCriteria(new ArrayList<>(Arrays.asList(LookInnaBook.getUsername())));
        int yPos = 410;

        // Remove previous search results
        for (Component component : selectedResults)
        {
            orderPane.remove(component);
        }

        for (int i = 0; i < listOfBooks.size(); ++i)
        {
            System.out.println("Adding New Selected Book...");

            yPos = (410 + i*35);
            String bookDetails = new String();
            ArrayList<String> bookAttributes = listOfBooks.get(i);

            // Build buttons for book selection
            JButton deleteButton = new JButton("Delete: " + bookAttributes.get(0));
            deleteButton.setName(bookAttributes.get(0));
            deleteButton.addActionListener(this);

            // Build buttons for book selection
            JButton editButton = new JButton("Edit: " + bookAttributes.get(0));
            editButton.setName(bookAttributes.get(0));
            editButton.addActionListener(this);

            // Build quantity fields
            JFormattedTextField quantityField = new JFormattedTextField(amountFormat);
            quantityField.setValue(Integer.parseInt(bookAttributes.get(1)));
            quantityField.setName(bookAttributes.get(0));

            // Build other book details
            for (int j = 2; j < bookAttributes.size(); ++j)
            {
                if (j == 4)
                {
                    bookDetails += "$";
                }

                bookDetails += bookAttributes.get(j);

                if (j == 3)
                {
                    bookDetails += " pages";
                }

                if (j < bookAttributes.size()-1)
                {
                    bookDetails += ", ";
                }
            }
            JLabel roDetails = new JLabel(bookDetails);
            roDetails.setName(bookAttributes.get(0));

            // Set component bounds
            deleteButton.setBounds(25, yPos, 95, 30);
            editButton.setBounds(125, yPos, 95, 30);
            quantityField.setBounds(230, yPos, 50, 30);
            roDetails.setBounds(285, yPos, 400, 30);

            // Add components to order pane
            orderPane.add(deleteButton);
            orderPane.add(editButton);
            orderPane.add(quantityField);
            orderPane.add(roDetails);

            // Add components to array list
            selectedResults.add(deleteButton);
            selectedResults.add(editButton);
            selectedResults.add(quantityField);
            selectedResults.add(roDetails);
        }

        orderPane.setPreferredSize(new Dimension(400, yPos + 55));
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected void displayOrderHistory()
    {
        ArrayList<ArrayList<String>> listOfOrders = DBQuery.getAllOrdersByUsername(LookInnaBook.getUsername());
        int yPos = 25;

        // Remove previous search results
        for (Component component : historyResults)
        {
            historyPane.remove(component);
        }

        for (int i = listOfOrders.size()-1; i >= 0 ; --i)
        {
            System.out.println("Adding order to history...");

            yPos = (25 + (listOfOrders.size() - (i + 1))*35);
            String orderDetails = new String();
            ArrayList<String> orderAttributes = listOfOrders.get(i);

            // Build other book details
            for (int j = 0; j < orderAttributes.size(); ++j)
            {
                if (j > 0)
                {
                    if (j != 3)
                    {
                        orderDetails += ", ";
                    }
                    else
                    {
                        orderDetails += " ";
                    }
                }
                orderDetails += orderAttributes.get(j);
            }
            JLabel roDetails = new JLabel(orderDetails);
            roDetails.setName(orderAttributes.get(0));
            roDetails.setBounds(25, yPos, 700, 30);
            historyPane.add(roDetails);
            historyResults.add(roDetails);
        }

        if (yPos < 410)
        {
            yPos = 410;
        }

        historyPane.setPreferredSize(new Dimension(400, yPos + 55));
    }

    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    // Override action performed
    @Override
    public void actionPerformed(ActionEvent event)
    {
        ArrayList<String> bookDetails = new ArrayList<String>();
        ArrayList<String> shippingStreetDetails = new ArrayList<String>();
        ArrayList<String> shippingProvinceDetails = new ArrayList<String>();
        ArrayList<String> billingStreetDetails = new ArrayList<String>();
        ArrayList<String> billingProvinceDetails = new ArrayList<String>();

        bookDetails.add(isbnTextField.getText().trim());
        bookDetails.add(titleTextField.getText().trim());
        bookDetails.add(genreTextField.getText().trim());
        bookDetails.add(convertFieldValue(pageCountTextField));
        bookDetails.add(convertFieldValue(priceTextField));

        bookDetails.add(authorFirstNameTextField.getText().trim());
        bookDetails.add(authorMiddleNameTextField.getText().trim());
        bookDetails.add(authorLastNameTextField.getText().trim());

        bookDetails.add(publisherNameTextField.getText().trim());
        bookDetails.add(publisherEmailTextField.getText().trim());
        bookDetails.add(publisherPhoneNumberTextField.getText().trim());

        bookDetails.add(publisherStreetNumberTextField.getText().trim());
        bookDetails.add(publisherStreetTextField.getText().trim());
        bookDetails.add(publisherCityTextField.getText().trim());
        bookDetails.add(publisherProvinceTextField.getText().trim());
        bookDetails.add(publisherPostalCodeTextField.getText().trim());
        bookDetails.add(publisherCountryTextField.getText().trim());

        shippingStreetDetails.add(shippingStreetNumberTextField.getText().trim());
        shippingStreetDetails.add(shippingStreetTextField.getText().trim());
        shippingStreetDetails.add(shippingPostalCodeTextField.getText().trim());
        shippingStreetDetails.add(shippingCountryTextField.getText().trim());

        shippingProvinceDetails.add(shippingPostalCodeTextField.getText().trim());
        shippingProvinceDetails.add(shippingCityTextField.getText().trim());
        shippingProvinceDetails.add(shippingProvinceTextField.getText().trim());

        billingStreetDetails.add(billingStreetNumberTextField.getText().trim());
        billingStreetDetails.add(billingStreetTextField.getText().trim());
        billingStreetDetails.add(billingPostalCodeTextField.getText().trim());
        billingStreetDetails.add(billingCountryTextField.getText().trim());

        billingProvinceDetails.add(billingPostalCodeTextField.getText().trim());
        billingProvinceDetails.add(billingCityTextField.getText().trim());
        billingProvinceDetails.add(billingProvinceTextField.getText().trim());

        // Handle add author button event
        if (event.getSource() == searchButton)
        {
           System.out.println("Searching books...");
           ArrayList<ArrayList<String>> listOfBooks = DBQuery.getAllBooksOfCriteria(bookDetails);

           displayBooks(listOfBooks);
        }

        // Handle clear button event
        if (event.getSource() == clearButton)
        {
            System.out.println("Clearing search...");
            clearBook();
        }
        // Handle search list selection
        else if (event.getActionCommand() != null
            && event.getActionCommand().charAt(3) == '-')
        {
            System.out.println("Adding book to basket...");
            String selectedISBN = event.getActionCommand();
            String quantity = "0"; 

            for(Component component : searchPane.getComponents())
            {
                //System.out.println("Component:" + component);
                if (component instanceof JFormattedTextField
                    && component.getName() != null
                    && component.getName().equals(selectedISBN))
                { 
                    quantity = ((JFormattedTextField)component).getValue().toString();
                }
            }

            ArrayList<String> selectedBook = new ArrayList<>(Arrays.asList(quantity
                                                                            , LookInnaBook.getUsername()
                                                                            , selectedISBN));
            DBUpdate.increaseBooksInBasket(selectedBook);

            displaySelectedBooks();
        }

        // Handle clear button event
        if (event.getSource() == checkoutButton)
        {
            System.out.println("Checking out...");
            Integer orderNumber = -1;

            // Check and add addresses
            DBCreate.checkAndAddProvinceDetails(shippingProvinceDetails);
            DBCreate.checkAndAddStreetDetails(shippingStreetDetails);
            DBCreate.checkAndAddProvinceDetails(billingProvinceDetails);
            DBCreate.checkAndAddStreetDetails(billingStreetDetails);

            // Add the order
            orderNumber = DBCreate.orderBooks(new ArrayList<String>(Arrays.asList(LookInnaBook.getUsername())));

            if (orderNumber >= 0)
            {
                // Add the orders & Delete the selects
                DBCreate.addBooksToOrder(orderNumber);

                // Add the order addresses
                DBCreate.addAddressesToOrder(orderNumber, shippingStreetDetails, billingStreetDetails);
            }

            displayOrderHistory();
        }

        // Handle clear button event
        if (event.getSource() == clearOrderButton)
        {
            clearOrder();
        }

        // Handle search list deletion
        if (event.getActionCommand().length() >= 7
                    && event.getActionCommand().substring(0,7).equals("Delete:"))
        {
            String selectedISBN = event.getActionCommand().substring(8);

            System.out.println("Delete book " + selectedISBN + " from selects...");

            DBDelete.deleteBookFromSelects(new ArrayList<>(Arrays.asList(LookInnaBook.getUsername()
                                                                            , selectedISBN)));

            displaySelectedBooks();
        }

        // Handle search list editing
        if (event.getActionCommand().length() >= 5
                && event.getActionCommand().substring(0,5).equals("Edit:"))
        {
            String selectedISBN = event.getActionCommand().substring(6);
            System.out.println("Edit book " + selectedISBN + " from selects...");
            String quantity = "0"; 

            for(Component component : orderPane.getComponents())
            {
                //System.out.println("Component:" + component);
                if (component instanceof JFormattedTextField
                    && component.getName() != null
                    && component.getName().equals(selectedISBN))
                { 
                    quantity = ((JFormattedTextField)component).getValue().toString();
                }
            }

            DBUpdate.updateSelectedBookQuantity(new ArrayList<>(Arrays.asList(quantity
                                                                                , LookInnaBook.getUsername()
                                                                                , selectedISBN)));
        }

        // Handle billingIsShipping checkbox event
        if (event.getSource() == billingIsShipping)
        {
            if (billingIsShipping.isSelected())
            {
                System.out.println("Shipping-->Billing");
                billingStreetNumberTextField.setText(shippingStreetNumberTextField.getText());
                billingStreetTextField.setText(shippingStreetTextField.getText());
                billingCityTextField.setText(shippingCityTextField.getText());
                billingProvinceTextField.setText(shippingProvinceTextField.getText());
                billingPostalCodeTextField.setText(shippingPostalCodeTextField.getText());
                billingCountryTextField.setText(shippingCountryTextField.getText());
            }
        }
    }
}