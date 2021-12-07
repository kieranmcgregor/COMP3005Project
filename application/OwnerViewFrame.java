import java.text.*;
import java.util.Locale;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OwnerViewFrame extends JFrame implements ActionListener
{
    Container container = getContentPane();
    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel pageTitleLabel = new JLabel("LookInnaBook: Owner");

    JPanel addBook = new JPanel();
    JScrollPane addBookScrollPane = new JScrollPane(addBook);

    JPanel deleteBook = new JPanel();
    JPanel editBook = new JPanel();
    JPanel reports = new JPanel();

    JLabel isbnLabel = new JLabel("ISBN:");
    JLabel titleLabel = new JLabel("Title:");
    JLabel genreLabel = new JLabel("Genre:");
    JLabel pageCountLabel = new JLabel("Page count:");
    JLabel priceLabel = new JLabel("Price:");
    JLabel publisherPercentageLabel = new JLabel("Publisher percentage:");
    JLabel publisherIDLabel = new JLabel("Publisher ID:");
    JLabel publisherNameLabel = new JLabel("Publisher name:");
    JLabel publisherEmailLabel = new JLabel("Publisher email:");
    JLabel publisherCountryCodeLabel = new JLabel("Publisher country code:");
    JLabel publisherAreaCodeLabel = new JLabel("Publisher area code:");
    JLabel publisherPhoneNumberLabel = new JLabel("Publisher phone number:");
    JLabel publisherStreetNumberLabel = new JLabel("Publisher street number:");
    JLabel publisherStreetLabel = new JLabel("Publisher street:");
    JLabel publisherCityLabel = new JLabel("Publisher city:");
    JLabel publisherProvinceLabel = new JLabel("Publisher province:");
    JLabel publisherPostalCodeLabel = new JLabel("Publisher postal code:");
    JLabel publisherCountryLabel = new JLabel("Publisher country:");
    JLabel publisherInstitutionNumber = new JLabel("Publisher institution number:");
    JLabel publisherBranchNumber = new JLabel("Publisher branch number:");
    JLabel publisherTransitNumber = new JLabel("Publisher transit number:");

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

    JFormattedTextField publisherIDTextField = new JFormattedTextField(amountFormat);
    JTextField publisherNameTextField = new JTextField();
    JTextField publisherEmailTextField = new JTextField();


    JButton addBookButton = new JButton("Add");
    JButton clearButton = new JButton("Clear");

    // Constructor
    OwnerViewFrame()
    {
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

        addBook.setLayout(null);
        //addBookScrollPane.setLayout(null);

        deleteBook.setLayout(null);
        editBook.setLayout(null);
        reports.setLayout(null);
    }

    public void setLocationAndSize()
    {
        // Set location and size of each component
        pageTitleLabel.setBounds(225, 25, 500, 60);

        tabbedPane.setBounds(25, 100, 735, 440);

        isbnLabel.setBounds(175, 25, 150, 30);
        titleLabel.setBounds(175, 60, 150, 30);
        genreLabel.setBounds(175, 95, 150, 30);
        pageCountLabel.setBounds(175, 130, 150, 30);
        priceLabel.setBounds(175, 165, 150, 30);
        publisherPercentageLabel.setBounds(175, 200, 150, 30);
        publisherIDLabel.setBounds(175, 235, 150, 30);
        publisherNameLabel.setBounds(175, 270, 150, 30);
        publisherEmailLabel.setBounds(175, 305, 150, 30);

        isbnTextField.setBounds(350, 25, 150, 30);
        titleTextField.setBounds(350, 60, 150, 30);
        genreTextField.setBounds(350, 95, 150, 30);
        pageCountTextField.setBounds(350, 130, 150, 30);
        priceTextField.setBounds(350, 165, 150, 30);
        publisherPercentageTextField.setBounds(350, 200, 150, 30);
        publisherIDTextField.setBounds(350, 235, 150, 30);
        publisherNameTextField.setBounds(350, 270, 150, 30);
        publisherEmailTextField.setBounds(350, 305, 150, 30);

        addBookButton.setBounds(225, 340, 100, 30);
        clearButton.setBounds(475, 340, 100, 30);
    }

    public void setUniqueAttributes()
    {
        // Set title font and size
        pageTitleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));

        // Flood label alignments right
        isbnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        genreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        pageCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherPercentageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        publisherEmailLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Set addBook vertical scroll
        //addBookScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        addBookScrollPane.setPreferredSize(new Dimension(400, 440));
        addBook.setPreferredSize(new Dimension(400, 1000));
    }

    public void addComponentsToContainer()
    {
        // Add components to view
        container.add(pageTitleLabel);

        // Add labels to addBook
        addBook.add(isbnLabel);
        addBook.add(titleLabel);
        addBook.add(genreLabel);
        addBook.add(pageCountLabel);
        addBook.add(priceLabel);
        addBook.add(publisherPercentageLabel);
        addBook.add(publisherIDLabel);
        addBook.add(publisherNameLabel);
        addBook.add(publisherEmailLabel);

        // Add fields to addBook
        addBook.add(isbnTextField);
        addBook.add(titleTextField);
        addBook.add(genreTextField);
        addBook.add(pageCountTextField);
        addBook.add(priceTextField);
        addBook.add(publisherPercentageTextField);
        addBook.add(publisherIDTextField);
        addBook.add(publisherNameTextField);
        addBook.add(publisherEmailTextField);

        addBook.add(addBookButton);
        addBook.add(clearButton);

        // Add tabs to tabbed pane
        tabbedPane.addTab("Add Book", addBookScrollPane);
        tabbedPane.addTab("Delete Book", deleteBook);
        tabbedPane.addTab("Edit Book", editBook);
        tabbedPane.addTab("Reports", reports);

        // Add tabbed pane to container
        container.add(tabbedPane);
    }

    public void addActionEvent()
    {
        addBookButton.addActionListener(this);
        clearButton.addActionListener(this);
    }

    // Override action performed
    @Override
    public void actionPerformed(ActionEvent event)
    {
    }
}