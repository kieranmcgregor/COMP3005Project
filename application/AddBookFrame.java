import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookFrame extends JFrame implements ActionListener
{
    Container container = getContentPane();
    JLabel pageTitleLabel = new JLabel("Let's Grow the Inventory of LookInnaBook!");
    JLabel titleLabel = new JLabel("Title:");
    JLabel genreLabel = new JLabel("Genre:");
    JTextField titleTextField = new JTextField();
    JTextField genreTextField = new JTextField();
    JButton addBookButton = new JButton("Add");
    JButton clearButton = new JButton("Clear");


    // Constructor
    AddBookFrame()
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
        pageTitleLabel.setBounds(200, 100, 500, 60);

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

        container.add(titleLabel);
        container.add(genreLabel);
        container.add(titleTextField);
        container.add(genreTextField);
        container.add(addBookButton);
        container.add(clearButton);
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