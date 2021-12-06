import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener
{
    Container container = getContentPane();
    JLabel titleLabel = new JLabel("Welcome to LookInnaBook!");
    JLabel userLabel = new JLabel("Username:");
    JLabel passwordLabel = new JLabel("Password:");
    JRadioButton userType = new JRadioButton("User", true);
    JRadioButton ownerType = new JRadioButton("Owner");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");
    JButton clearButton = new JButton("Clear");
    JCheckBox showPassword = new JCheckBox("Show Password");


    // Constructor
    LoginFrame()
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
        titleLabel.setBounds(200, 100, 500, 60);

        userType.setBounds(300, 175, 100, 30);
        ownerType.setBounds(400, 175, 100, 30);

        userLabel.setBounds(250, 225, 100, 30);
        passwordLabel.setBounds(250, 270, 100, 30);
        userTextField.setBounds(350, 225, 150, 30);
        passwordField.setBounds(350, 270, 150, 30);
        showPassword.setBounds(350, 300, 150, 30);
        loginButton.setBounds(225, 350, 100, 30);
        registerButton.setBounds(350, 350, 100, 30);
        clearButton.setBounds(475, 350, 100, 30);
    }

    public void setUniqueAttributes()
    {
        // Set title font and size
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));

        // Set login type button group
        ButtonGroup loginType = new ButtonGroup();
        loginType.add(userType);
        loginType.add(ownerType);
    }

    public void addComponentsToContainer()
    {
        // Add components to view
        container.add(titleLabel);

        container.add(userType);
        container.add(ownerType);

        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(registerButton);
        container.add(clearButton);
    }

    public void addActionEvent()
    {
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        clearButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    // Override action performed
    @Override
    public void actionPerformed(ActionEvent event)
    {
        // Handle login button event
        if (event.getSource() == loginButton)
        {
            Boolean isUserType;
            String userText;
            String passwordText;

            isUserType = userType.isSelected();
            userText = userTextField.getText();
            passwordText = passwordField.getText();


            System.out.println("Type: " + isUserType + ", Username: " + userText + ", Password " + passwordText + ", Logged In");
        }

        // Handle register button event
        if (event.getSource() == registerButton)
        {
            Boolean isUserType;
            String userText;
            String passwordText;

            isUserType = userType.isSelected();
            userText = userTextField.getText();
            passwordText = passwordField.getText();

            System.out.println("Type: " + isUserType + ", Username: " + userText + ", Password " + passwordText + ", Registered and Logged In");
        }

        // Handle Clear button event
        if (event.getSource() == clearButton)
        {
            userTextField.setText("");
            passwordField.setText("");
        }
        
        // Handle Clear button event
        if (event.getSource() == showPassword)
        {
            if (showPassword.isSelected())
            {
                passwordField.setEchoChar((char) 0);
            }
            else
            {
                passwordField.setEchoChar('*');
            }
        }
    }
}