import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OwnerViewFrame extends JFrame implements ActionListener
{
    Container container = getContentPane();
    JTabbedPane tabbedPane = new JTabbedPane();
    JLabel pageTitleLabel = new JLabel("LookInnaBook: Owner");
    JPanel addBook = new JPanel();
    JPanel deleteBook = new JPanel();
    JPanel editBook = new JPanel();
    JPanel reports = new JPanel();
    JLabel titleLabel = new JLabel("Title:");
    JLabel genreLabel = new JLabel("Genre:");
    JTextField titleTextField = new JTextField();
    JTextField genreTextField = new JTextField();
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

    public void setLayoutManager()
    {
        container.setLayout(null);
    }

    public void setLocationAndSize()
    {
        // Set location and size of each component
        pageTitleLabel.setBounds(225, 25, 500, 60);

        tabbedPane.setBounds(25, 100, 735, 440);

        titleLabel.setBounds(250, 225, 100, 30);
        genreLabel.setBounds(250, 270, 100, 30);
        titleTextField.setBounds(350, 225, 150, 30);
        genreTextField.setBounds(350, 270, 150, 30);
        addBookButton.setBounds(225, 350, 100, 30);
        clearButton.setBounds(475, 350, 100, 30);
    }

    public void setUniqueAttributes()
    {
        // Set title font and size
        pageTitleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
    }

    public void addComponentsToContainer()
    {
        // Add components to view
        container.add(pageTitleLabel);

        // Add to addBook tabe
        addBook.add(titleLabel);
        addBook.add(genreLabel);
        addBook.add(titleTextField);
        addBook.add(genreTextField);
        addBook.add(addBookButton);
        addBook.add(clearButton);

        // Add tabs to tabbed pane
        tabbedPane.add("Add Book", addBook);
        tabbedPane.add("Delete Book", deleteBook);
        tabbedPane.add("Edit Book", editBook);
        tabbedPane.add("Reports", reports);

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