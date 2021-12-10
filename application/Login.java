/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import javax.swing.*;

public class Login
{
    /*
    Function:   
    Purpose:    
    in:         
    in:         
    return:     
    */
    public static void login()
    {
        System.out.println("Login Screen...");

        LoginFrame frame = new LoginFrame();

        frame.setTitle("LookInnaBook Login");
        frame.setVisible(true);
        frame.setBounds(10, 10, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}