/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import javax.swing.*;

public class OwnerView
{
    /*
    Function:   showOwnerView
    Purpose:    Set up owner window     
    */
    public static void showOwnerView()
    {
        System.out.println("Owner View Screen...");

        OwnerViewFrame frame = new OwnerViewFrame();

        frame.setTitle("LookInnaBook Owner");
        frame.setVisible(true);
        frame.setBounds(10, 10, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}