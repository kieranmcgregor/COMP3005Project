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

import java.util.Calendar;
import java.util.Date;

public class OwnerViewFrame extends JFrame implements ActionListener
{
    ArrayList<Component> reportResults = new ArrayList<Component>();

    Container container = getContentPane();
    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel pageTitleLabel = new JLabel("LookInnaBook: Owner");

    // Create CUD tab components
    JPanel workPane = new JPanel();
    JScrollPane workScrollPane = new JScrollPane(workPane);

    // Create report tab
    JPanel reportsPane = new JPanel();
    JScrollPane reportsScrollPane = new JScrollPane(reportsPane);

    // Create work tab labels
    // Create Book labels
    JLabel isbnLabel = new JLabel("ISBN:");
    JLabel titleLabel = new JLabel("Title:");
    JLabel genreLabel = new JLabel("Genre:");
    JLabel pageCountLabel = new JLabel("Page count:");
    JLabel priceLabel = new JLabel("Price:");
    JLabel publisherPercentageLabel = new JLabel("Publisher percentage:");
    JLabel quantityLabel = new JLabel("Quantity:");
    JLabel thresholdLabel = new JLabel("Threshold:");

    // Create Author labels
    JLabel authorIDsLabel = new JLabel("Author IDs:");
    JLabel authorFirstNameLabel = new JLabel("Author First Name:");
    JLabel authorMiddleNameLabel = new JLabel("Author Middle Name:");
    JLabel authorLastNameLabel = new JLabel("Author Last Name:");
    JLabel authorBioLabel = new JLabel("Author Bio:");

    // Create publisher labels
    JLabel publisherIDLabel = new JLabel("Publisher ID:");
    JLabel publisherNameLabel = new JLabel("Publisher name:");
    JLabel publisherEmailLabel = new JLabel("Publisher email:");
    JLabel publisherPhoneNumberLabel = new JLabel("Publisher phone number:");
    JLabel publisherStreetNumberLabel = new JLabel("Publisher street number:");
    JLabel publisherStreetLabel = new JLabel("Publisher street:");
    JLabel publisherCityLabel = new JLabel("Publisher city:");
    JLabel publisherProvinceLabel = new JLabel("Publisher province:");
    JLabel publisherPostalCodeLabel = new JLabel("Publisher postal code:");
    JLabel publisherCountryLabel = new JLabel("Publisher country:");

    // Create Bank Account labels
    JLabel publisherInstitutionNumberLabel = new JLabel("Publisher institution number:");
    JLabel publisherTransitNumberLabel = new JLabel("Publisher transit number:");
    JLabel publisherAccountNumberLabel = new JLabel("Publisher acount number:");

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

    NumberFormat percentageFormat = NumberFormat.getPercentInstance();
    JFormattedTextField publisherPercentageTextField = new JFormattedTextField(percentageFormat);
    JFormattedTextField quantityTextField = new JFormattedTextField(amountFormat);
    JFormattedTextField thresholdTextField = new JFormattedTextField(amountFormat);

    // Create author text fields
    JFormattedTextField authorIDsTextField = new JFormattedTextField(amountFormat);
    JTextField authorFirstNameTextField = new JTextField();
    JTextField authorMiddleNameTextField = new JTextField();
    JTextField authorLastNameTextField = new JTextField();
    JTextArea authorBioTextArea = new JTextArea();


    // Create publisher text fields
    JFormattedTextField publisherIDTextField = new JFormattedTextField(amountFormat);
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

    // Create bank account text fields
    MaskFormatter insitutionNumberMask = createFormatter("###", '_');
    JFormattedTextField publisherInstitutionNumberTextField = new JFormattedTextField(insitutionNumberMask);

    MaskFormatter transitNumberMask = createFormatter("#####", '_');
    JFormattedTextField publisherTransitNumberTextField = new JFormattedTextField(transitNumberMask);

    MaskFormatter accountNumberMask = createFormatter("############", '_');
    JFormattedTextField publisherAccountNumberTextField = new JFormattedTextField(accountNumberMask);

    JButton addAddressButton = new JButton("Add Address");
    JButton editAddressButton = new JButton("Edit Address");
    JButton clearAddressButton = new JButton("Clear Address");
    JButton addPublisherButton = new JButton("Add Publisher");
    JButton editPublisherButton = new JButton("Edit Publisher");
    JButton clearPublisherButton = new JButton("Clear Publisher");
    JButton addBankAccountButton = new JButton("Add Bank Account");
    JButton editBankAccountButton = new JButton("Edit Bank Account");
    JButton clearBankAccountButton = new JButton("Clear Bank Account");
    JButton addAuthorButton = new JButton("Add Author");
    JButton editAuthorButton = new JButton("Edit Author");
    JButton clearAuthorButton = new JButton("Clear Author");
    JButton addBookButton = new JButton("Add Book");
    JButton orderBooksButton = new JButton("Order Books");
    JButton editBookButton = new JButton("Edit Book");
    JButton deleteBookButton = new JButton("Delete Book");
    JButton clearButton = new JButton("Clear All Fields");

    // Create reports tab dropdown and date pickers
    JLabel reportTypeLabel = new JLabel("Search By:");
    JLabel startDateLabel = new JLabel("Start Date:");
    JLabel endDateLabel = new JLabel("End Date:");

    String reportTypes[] = { "Total Sales", "Genre", "Author"};
    JComboBox reportTypeSelection = new JComboBox(reportTypes);

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    JFormattedTextField startDateField = new JFormattedTextField(dateFormat);
    JFormattedTextField endDateField = new JFormattedTextField(dateFormat);

    JButton getReportButton = new JButton("Get Report");

    // Constructor
    OwnerViewFrame()
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

    /*
    Function:   setLayoutManager
    Purpose:    set up container template    
    */
    public void setLayoutManager()
    {
        container.setLayout(null);

        workPane.setLayout(null);
        reportsPane.setLayout(null);
    }

    public void setLocationAndSize()
    {
        // Set location and size of each component
        pageTitleLabel.setBounds(225, 25, 500, 60);

        tabbedPane.setBounds(25, 100, 735, 440);

        // Position the work tab components
        // Position book labels
        isbnLabel.setBounds(175, 25, 150, 30);
        titleLabel.setBounds(175, 60, 150, 30);
        genreLabel.setBounds(175, 95, 150, 30);
        pageCountLabel.setBounds(175, 130, 150, 30);
        priceLabel.setBounds(175, 165, 150, 30);
        publisherPercentageLabel.setBounds(175, 200, 150, 30);
        quantityLabel.setBounds(175, 235, 150, 30);
        thresholdLabel.setBounds(175, 270, 150, 30);

        // Position author labels
        authorIDsLabel.setBounds(175, 305, 150, 30);
        authorFirstNameLabel.setBounds(175, 340, 150, 30);
        authorMiddleNameLabel.setBounds(175, 375, 150, 30);
        authorLastNameLabel.setBounds(175, 410, 150, 30);
        authorBioLabel.setBounds(175, 445, 150, 30);

        // Position add author button
        addAuthorButton.setBounds(105, 480, 150, 30);
        editAuthorButton.setBounds(280, 480, 150, 30);
        clearAuthorButton.setBounds(455, 480, 150, 30);

        // Position Publisher Address Labels
        publisherStreetNumberLabel.setBounds(175, 515, 150, 30);
        publisherStreetLabel.setBounds(175, 550, 150, 30);
        publisherCityLabel.setBounds(175, 585, 150, 30);
        publisherProvinceLabel.setBounds(175, 620, 150, 30);
        publisherPostalCodeLabel.setBounds(175, 655, 150, 30);
        publisherCountryLabel.setBounds(175, 690, 150, 30);

        // Position Add Address Button
        addAddressButton.setBounds(105, 725, 150, 30);
        editAddressButton.setBounds(280, 725, 150, 30);
        clearAddressButton.setBounds(455, 725, 150, 30);

        // Position publisher labels
        publisherIDLabel.setBounds(175, 760, 150, 30);
        publisherNameLabel.setBounds(175, 795, 150, 30);
        publisherEmailLabel.setBounds(175, 830, 150, 30);
        publisherPhoneNumberLabel.setBounds(175, 865, 150, 30);

        // Position Add Publisher Button
        addPublisherButton.setBounds(105, 900, 150, 30);
        editPublisherButton.setBounds(280, 900, 150, 30);
        clearPublisherButton.setBounds(455, 900, 150, 30);

        //Position Bank Account Labels
        publisherInstitutionNumberLabel.setBounds(175, 935, 150, 30);
        publisherTransitNumberLabel.setBounds(175, 970, 150, 30);
        publisherAccountNumberLabel.setBounds(175, 1005, 150, 30);

        addBankAccountButton.setBounds(105, 1040, 150, 30);
        editBankAccountButton.setBounds(280, 1040, 150, 30);
        clearBankAccountButton.setBounds(455, 1040, 150, 30);

        // Position book fields
        isbnTextField.setBounds(350, 25, 150, 30);
        titleTextField.setBounds(350, 60, 150, 30);
        genreTextField.setBounds(350, 95, 150, 30);
        pageCountTextField.setBounds(350, 130, 150, 30);
        priceTextField.setBounds(350, 165, 150, 30);
        publisherPercentageTextField.setBounds(350, 200, 150, 30);
        quantityTextField.setBounds(350, 235, 150, 30);
        thresholdTextField.setBounds(350, 270, 150, 30);

        // Position author fields
        authorIDsTextField.setBounds(350, 305, 150, 30);
        authorFirstNameTextField.setBounds(350, 340, 150, 30);
        authorMiddleNameTextField.setBounds(350, 375, 150, 30);
        authorLastNameTextField.setBounds(350, 410, 150, 30);
        authorBioTextArea.setBounds(350, 445, 150, 30);

        // Position publisher address fields
        publisherStreetNumberTextField.setBounds(350, 515, 150, 30);
        publisherStreetTextField.setBounds(350, 550, 150, 30);
        publisherCityTextField.setBounds(350, 585, 150, 30);
        publisherProvinceTextField.setBounds(350, 620, 150, 30);
        publisherPostalCodeTextField.setBounds(350, 655, 150, 30);
        publisherCountryTextField.setBounds(350, 690, 150, 30);

        // Poaition publisher fields
        publisherIDTextField.setBounds(350, 760, 150, 30);
        publisherNameTextField.setBounds(350, 795, 150, 30);
        publisherEmailTextField.setBounds(350, 830, 150, 30);
        publisherPhoneNumberTextField.setBounds(350, 865, 150, 30);

        // Position bank account fields
        publisherInstitutionNumberTextField.setBounds(350, 935, 150, 30);
        publisherTransitNumberTextField.setBounds(350, 970, 150, 30);
        publisherAccountNumberTextField.setBounds(350, 1005, 150, 30);

        orderBooksButton.setBounds(20, 1075, 125, 30);
        addBookButton.setBounds(170, 1075, 100, 30);
        clearButton.setBounds(295, 1075, 125, 30);
        editBookButton.setBounds(445, 1075, 100, 30);
        deleteBookButton.setBounds(570, 1075, 125, 30);

        // Position dropdown and date pickers
        reportTypeLabel.setBounds(0, 25, 75, 30);
        reportTypeSelection.setBounds(80, 25, 100, 30);

        startDateLabel.setBounds(190, 25, 75, 30);
        startDateField.setBounds(270, 25, 100, 30);
        endDateLabel.setBounds(380, 25, 75, 30);
        endDateField.setBounds(460, 25, 100, 30);

        getReportButton.setBounds(590, 25, 100, 30);
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
        publisherPercentageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        thresholdLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Flood author labels right
        authorIDsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        authorFirstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        authorMiddleNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        authorLastNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        authorBioLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Flood publisher labels right
        publisherIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherPhoneNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherStreetNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherStreetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherCityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherProvinceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherPostalCodeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherCountryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Flood Bank labels right
        publisherInstitutionNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherTransitNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherAccountNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Flood report search labels right
        reportTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        startDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        endDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Set addBook vertical scroll
        workScrollPane.setPreferredSize(new Dimension(400, 400));
        workPane.setPreferredSize(new Dimension(400, 1130));

        // Set reports veritcal scroll
        reportsScrollPane.setPreferredSize(new Dimension(400, 400));
        reportsPane.setPreferredSize(new Dimension(400, 410));
    }

    public void addComponentsToWorkTab()
    {
        // Add book labels to work tab
        workPane.add(isbnLabel);
        workPane.add(titleLabel);
        workPane.add(genreLabel);
        workPane.add(pageCountLabel);
        workPane.add(priceLabel);
        workPane.add(publisherPercentageLabel);
        workPane.add(quantityLabel);
        workPane.add(thresholdLabel);

        // Add author labels to work tab
        workPane.add(authorIDsLabel);
        workPane.add(authorFirstNameLabel);
        workPane.add(authorMiddleNameLabel);
        workPane.add(authorLastNameLabel);
        workPane.add(authorBioLabel);

        workPane.add(addAuthorButton);
        workPane.add(editAuthorButton);
        workPane.add(clearAuthorButton);

        // Add publisher labels to work tab
        workPane.add(publisherIDLabel);
        workPane.add(publisherNameLabel);
        workPane.add(publisherEmailLabel);
        workPane.add(publisherPhoneNumberLabel);
        workPane.add(publisherStreetNumberLabel);
        workPane.add(publisherStreetLabel);
        workPane.add(publisherCityLabel);
        workPane.add(publisherProvinceLabel);
        workPane.add(publisherPostalCodeLabel);
        workPane.add(publisherCountryLabel);

        workPane.add(addAddressButton);
        workPane.add(editAddressButton);
        workPane.add(clearAddressButton);
        workPane.add(addPublisherButton);
        workPane.add(editPublisherButton);
        workPane.add(clearPublisherButton);

        // Add bank account labels to work tab
        workPane.add(publisherInstitutionNumberLabel);
        workPane.add(publisherTransitNumberLabel);
        workPane.add(publisherAccountNumberLabel);

        workPane.add(addBankAccountButton);
        workPane.add(editBankAccountButton);
        workPane.add(clearBankAccountButton);

        // Add fields to work tab
        // Add publisher fields
        workPane.add(isbnTextField);
        workPane.add(titleTextField);
        workPane.add(genreTextField);
        workPane.add(pageCountTextField);
        workPane.add(priceTextField);
        workPane.add(publisherPercentageTextField);
        workPane.add(quantityTextField);
        workPane.add(thresholdTextField);

        // Add author fields
        workPane.add(authorIDsTextField);
        workPane.add(authorFirstNameTextField);
        workPane.add(authorMiddleNameTextField);
        workPane.add(authorLastNameTextField);
        workPane.add(authorBioTextArea);

        // Add publisher fields
        workPane.add(publisherIDTextField);
        workPane.add(publisherNameTextField);
        workPane.add(publisherEmailTextField);
        workPane.add(publisherPhoneNumberTextField);
        workPane.add(publisherStreetNumberTextField);
        workPane.add(publisherStreetTextField);
        workPane.add(publisherCityTextField);
        workPane.add(publisherProvinceTextField);
        workPane.add(publisherPostalCodeTextField);
        workPane.add(publisherCountryTextField);
        workPane.add(publisherInstitutionNumberTextField);
        workPane.add(publisherTransitNumberTextField);
        workPane.add(publisherAccountNumberTextField);

        workPane.add(addBookButton);
        workPane.add(editBookButton);
        workPane.add(orderBooksButton);
        workPane.add(deleteBookButton);
        workPane.add(clearButton);

        // Add labels to report pane
        reportsPane.add(reportTypeLabel);
        reportsPane.add(startDateLabel);
        reportsPane.add(endDateLabel);
        reportsPane.add(reportTypeSelection);
        reportsPane.add(startDateField);
        reportsPane.add(endDateField);
        reportsPane.add(getReportButton);
    }
    public void addComponentsToContainer()
    {
        // Add components to view
        container.add(pageTitleLabel);

        addComponentsToWorkTab();

        // Add tabs to tabbed pane
        tabbedPane.addTab("Work", workScrollPane);
        tabbedPane.addTab("Reports", reportsScrollPane);

        // Add tabbed pane to container
        container.add(tabbedPane);
    }

     /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    protected void displayReportResults(ArrayList<ArrayList<String>> reportSearchResults)
    {
        System.out.println("Displaying report...");
        int yPos = 60;

        // Remove previous search results
        for (Component component : reportResults)
        {
            reportsPane.remove(component);
        }

        for (int i = 0; i < reportSearchResults.size() ; ++i)
        {
            System.out.println("Adding data to report...");

            yPos = (60 + i*35);
            String reportDetails = new String();
            ArrayList<String> reportAttributes = reportSearchResults.get(i);

            // Build other book details
            for (int j = 0; j < reportAttributes.size(); ++j)
            {
                if (j > 0)
                {
                    reportDetails += ", ";
                }
                reportDetails += reportAttributes.get(j);
            }
            JLabel roDetails = new JLabel(reportDetails);
            roDetails.setName(reportAttributes.get(0));
            roDetails.setBounds(25, yPos, 700, 30);
            reportsPane.add(roDetails);
            reportResults.add(roDetails);
        }

        if (yPos < 410)
        {
            yPos = 410;
        }

        reportsPane.setPreferredSize(new Dimension(400, yPos + 55));
    }

    public void addActionEvent()
    {
        addAuthorButton.addActionListener(this);
        editAuthorButton.addActionListener(this);
        clearAuthorButton.addActionListener(this);

        addAddressButton.addActionListener(this);
        editAddressButton.addActionListener(this);
        clearAddressButton.addActionListener(this);

        addPublisherButton.addActionListener(this);
        editPublisherButton.addActionListener(this);
        clearPublisherButton.addActionListener(this);

        addBankAccountButton.addActionListener(this);
        editBankAccountButton.addActionListener(this);
        clearBankAccountButton.addActionListener(this);

        addBookButton.addActionListener(this);
        editBookButton.addActionListener(this);
        orderBooksButton.addActionListener(this);
        deleteBookButton.addActionListener(this);
        clearButton.addActionListener(this);

        getReportButton.addActionListener(this);
    }

    public void clearAuthor()
    {
        authorIDsTextField.setText("");
        authorFirstNameTextField.setText("");
        authorMiddleNameTextField.setText("");
        authorLastNameTextField.setText("");
        authorBioTextArea.setText("");
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
        publisherIDTextField.setText("");
        publisherNameTextField.setText("");
        publisherEmailTextField.setText("");
        publisherPhoneNumberTextField.setText("");
    }

    public void clearBankAccount()
    {
        publisherInstitutionNumberTextField.setText("");
        publisherTransitNumberTextField.setText("");
        publisherAccountNumberTextField.setText("");
    }

    public void clearBook()
    {
        isbnTextField.setText("");
        titleTextField.setText("");
        genreTextField.setText("");
        pageCountTextField.setText("");
        priceTextField.setText("");
        publisherPercentageTextField.setText("");
        quantityTextField.setText("");
        thresholdTextField.setText("");

        clearAuthor();
        clearAddress();
        clearPublisher();
        clearBankAccount();
    }

    public String convertFieldValue(JFormattedTextField fieldForConversion)
    {
        if (!fieldForConversion.getText().trim().isEmpty())
        {
            return fieldForConversion.getValue().toString();
        }

        return fieldForConversion.getText().trim();
    }

    // Override action performed
    @Override
    public void actionPerformed(ActionEvent event)
    {
        ArrayList<String> bookDetails = new ArrayList<String>();
        ArrayList<String> authorDetails = new ArrayList<String>();
        ArrayList<String> publisherDetails = new ArrayList<String>();
        ArrayList<String> provinceDetails = new ArrayList<String>();
        ArrayList<String> streetDetails = new ArrayList<String>();
        ArrayList<String> bankAccountDetails = new ArrayList<String>();
        ArrayList<String> reportSearchDetails = new ArrayList<String>();

        bookDetails.add(isbnTextField.getText().trim());
        bookDetails.add(titleTextField.getText().trim());
        bookDetails.add(genreTextField.getText().trim());
        bookDetails.add(convertFieldValue(pageCountTextField));
        bookDetails.add(convertFieldValue(priceTextField));
        bookDetails.add(convertFieldValue(publisherPercentageTextField));
        bookDetails.add(convertFieldValue(quantityTextField));
        bookDetails.add(convertFieldValue(thresholdTextField));
        bookDetails.add(convertFieldValue(publisherIDTextField));

        authorDetails.add(convertFieldValue(authorIDsTextField));
        authorDetails.add(authorFirstNameTextField.getText().trim());
        authorDetails.add(authorMiddleNameTextField.getText().trim());
        authorDetails.add(authorLastNameTextField.getText().trim());
        authorDetails.add(authorBioTextArea.getText().trim());

        publisherDetails.add(publisherIDTextField.getText().trim());
        publisherDetails.add(publisherNameTextField.getText().trim());
        publisherDetails.add(publisherEmailTextField.getText().trim());
        publisherDetails.add(publisherPhoneNumberTextField.getText().trim());

        streetDetails.add(publisherStreetNumberTextField.getText().trim());
        streetDetails.add(publisherStreetTextField.getText().trim());
        streetDetails.add(publisherPostalCodeTextField.getText().trim());
        streetDetails.add(publisherCountryTextField.getText().trim());

        provinceDetails.add(publisherPostalCodeTextField.getText().trim());
        provinceDetails.add(publisherCityTextField.getText().trim());
        provinceDetails.add(publisherProvinceTextField.getText().trim());

        bankAccountDetails.add(publisherInstitutionNumberTextField.getText().trim());
        bankAccountDetails.add(publisherTransitNumberTextField.getText().trim());
        bankAccountDetails.add(publisherAccountNumberTextField.getText().trim());
        bankAccountDetails.add(convertFieldValue(publisherIDTextField));

        reportSearchDetails.add(startDateField.getText().trim());
        reportSearchDetails.add(endDateField.getText().trim());

        // Handle add author button event
        if (event.getSource() == addAuthorButton)
        {
            Integer authorId = DBCreate.checkAndAddAuthor(authorDetails);

            if (authorId > 0)
            {
                authorIDsTextField.setValue(authorId);
            }
        }

        // Handle edit author button event
        if (event.getSource() == editAuthorButton)
        {
            DBUpdate.checkAndEditAuthor(authorDetails);
        }

        // Handle clear author button event
        if (event.getSource() == clearAuthorButton)
        {
            clearAuthor();
        }

        // Handle add address button event
        if (event.getSource() == addAddressButton)
        {
            if (DBCreate.checkAndAddProvinceDetails(provinceDetails)
                && DBCreate.checkAndAddStreetDetails(streetDetails))
            {
                System.out.println("Address added!");
            }
            else
            {
                System.out.println("ERROR: Address not added!");
            }
        }

        // Handle edit address button event
        if (event.getSource() == editAddressButton)
        {
            if (DBCreate.checkAndEditProvinceDetails(provinceDetails)
                && DBCreate.checkAndEditStreetDetails(streetDetails))
            {
                System.out.println("Address edited!");
            }
            else
            {
                System.out.println("ERROR: Address not edited!");
            }
        }

        // Handle clear address button event
        if (event.getSource() == clearAddressButton)
        {
            clearAddress();
        }

        // Handle add publisher button event
        if (event.getSource() == addPublisherButton)
        {
            Integer publisherId = DBCreate.checkAndAddPublisher(publisherDetails, streetDetails);

            if (publisherId > 0)
            {
                publisherIDTextField.setValue(publisherId);
            }
        }

        // Handle edit publisher button event
        if (event.getSource() == editPublisherButton)
        {
            DBCreate.checkAndEditPublisher(publisherDetails);
        }

        // Handle clear publisher button event
        if (event.getSource() == clearPublisherButton)
        {
            clearPublisher();
        }

        // Handle add bank account button event
        if (event.getSource() == addBankAccountButton)
        {
            DBCreate.checkAndAddBankAccount(bankAccountDetails);
        }

        // Handle edit bank account button event
        if (event.getSource() == editBankAccountButton)
        {
            DBCreate.checkAndEditBankAccount(bankAccountDetails);
        }

        // Handle clear bank account button event
        if (event.getSource() == clearBankAccountButton)
        {
            clearBankAccount();
        }

        // Handle add button event
        if (event.getSource() == addBookButton)
        {
            DBCreate.checkAndAddAllBookDetails(provinceDetails
                                                , streetDetails
                                                , publisherDetails
                                                , bankAccountDetails
                                                , authorDetails
                                                , bookDetails);
        }

        // Handle edit button event
        if (event.getSource() == orderBooksButton)
        {
            System.out.println("Ordering books...");
            DBUpdate.checkAndBuyBooks(bookDetails);
        }

         // Handle edit button event
        if (event.getSource() == editBookButton)
        {
            System.out.println("Editing book...");
        }

        // Handle delete button event
        if (event.getSource() == deleteBookButton)
        {
            System.out.println("Deleting book...");
            DBDelete.deleteBook(bookDetails.get(0));
        }

        // Handle clear button event
        if (event.getSource() == clearButton)
        {
            clearBook();
        }

        // Handle clear button event
        if (event.getSource() == getReportButton)
        {
            System.out.println("Getting report...");
            ArrayList<ArrayList<String>> reportSearchResults = new ArrayList<ArrayList<String>>();
            String reportType = reportTypeSelection.getSelectedItem().toString();

            if (reportType.equals("Total Sales"))
            {
                reportSearchResults = DBQuery.getTotalSales(reportSearchDetails);
            }
            else if (reportType.equals("Genre"))
            {
                reportSearchResults = DBQuery.getGenreSales(reportSearchDetails);
            }
            else
            {
                reportSearchResults = DBQuery.getAuthorSales(reportSearchDetails);
            }

            displayReportResults(reportSearchResults);
        }
    }
}