import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBook
{
    public static void addBook()
    {
        System.out.println("Add Book Screen...");

        AddBookFrame frame = new AddBookFrame();

        frame.setTitle("LookInnaBook Add Book");
        frame.setVisible(true);
        frame.setBounds(10, 10, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}