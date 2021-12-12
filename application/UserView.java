/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import javax.swing.*;

public class UserView
{
    /*
    Function:   showUserView
    Purpose:    Set up user window     
    */
    public static void showUserView()
    {
        System.out.println("User View Screen...");

        UserViewFrame frame = new UserViewFrame();

        frame.setTitle("LookInnaBook User");
        frame.setVisible(true);
        frame.setBounds(10, 10, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}