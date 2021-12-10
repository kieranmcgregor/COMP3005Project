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
    Container container = getContentPane();
    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel pageTitleLabel = new JLabel("LookInnaBook: User");

    // Create CUD tab components
    JPanel searchPane = new JPanel();
    JScrollPane searchScrollPane = new JScrollPane(searchPane);

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
    }

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

    public void setLayoutManager()
    {
        container.setLayout(null);

        searchPane.setLayout(null);
    }

    public void setLocationAndSize()
    {
        // Set location and size of each component
        pageTitleLabel.setBounds(225, 25, 500, 60);

        searchScrollPane.setBounds(25, 100, 735, 440);

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

        searchButton.setBounds(210, 355, 150, 30);
        clearButton.setBounds(410, 355, 150, 30);
    }

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

        // Set addBook vertical scroll
        searchPane.setPreferredSize(new Dimension(400, 1100));
        searchScrollPane.setPreferredSize(new Dimension(400, 440));
    }

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
    }
    public void addComponentsToContainer()
    {
        // Add components to view
        container.add(pageTitleLabel);

        addComponentsToTopPane();

        // Add search and results pane to container
        // container.add(searchPane);
        container.add(searchScrollPane);
    }

    public void addActionEvent()
    {
        searchButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    public void clearAuthor()
    {
        authorFirstNameTextField.setText("");
        authorMiddleNameTextField.setText("");
        authorLastNameTextField.setText("");
    }

    public void clearAddress()
    {
        publisherStreetNumberTextField.setText("");
        publisherStreetTextField.setText("");
        publisherCityTextField.setText("");
        publisherProvinceTextField.setText("");
        publisherPostalCodeTextField.setText("");
        publisherCountryTextField.setText("");
    }

    public void clearPublisher()
    {
        publisherNameTextField.setText("");
        publisherEmailTextField.setText("");
        publisherPhoneNumberTextField.setText("");
    }

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

    public String convertFieldValue(JFormattedTextField fieldForConversion)
    {
        if (!fieldForConversion.getText().trim().isEmpty())
        {
            return fieldForConversion.getValue().toString();
        }

        return fieldForConversion.getText().trim();
    }

    protected void displayBooks(ArrayList<ArrayList<String>> listOfBooks)
    {
        int yPos = 410;

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
                bookDetails += bookAttributes.get(j);

                if (j < bookAttributes.size()-1)
                {
                    bookDetails += ", ";
                }
            }
            JLabel roDetails = new JLabel(bookDetails + "THE END");

            // Set component bounds
            button.setBounds(10, yPos, 150, 30);
            quantityField.setBounds(165, yPos, 50, 30);
            roDetails.setBounds(220, yPos, 400, 30);

            // Add components to search pane
            searchPane.add(button);
            searchPane.add(quantityField);
            searchPane.add(roDetails);
        }

        // searchPane.setPreferredSize(new Dimension(400, yPos));
    }

    // Override action performed
    @Override
    public void actionPerformed(ActionEvent event)
    {
        ArrayList<String> bookDetails = new ArrayList<String>();

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

        // Handle add author button event
        if (event.getSource() == searchButton)
        {
           System.out.println("Searching books...");
           ArrayList<ArrayList<String>> listOfBooks = DBQuery.getAllBooksOfCriteria(bookDetails);

           displayBooks(listOfBooks);
        }
        // Handle clear button event
        else if (event.getSource() == clearButton)
        {
            clearBook();
        }
        else
        {
            String selectedISBN = event.getActionCommand();
            String quantity = "0"; 

            for(Component component : searchPane.getComponents())
            {
                //System.out.println("Component:" + component);
                if (component instanceof JFormattedTextField
                    && component.getName() != null
                    && component.getName().equals(selectedISBN))
                { 
                    quantity = ((JFormattedTextField)component).getText().trim();
                }
            }

            ArrayList<String> selectedBook = new ArrayList<>(Arrays.asList(LookInnaBook.getUsername()
                                                                            , selectedISBN
                                                                            , quantity));
            DBUpdate.addToBasket(selectedBook);
        }
    }
}