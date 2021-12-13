/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

import java.util.*;

public class LookInnaBook
{
    static public final String DB_URL = "jdbc:postgresql://localhost:5433/lookinnabook";
    static public final String USER = "postgres";
    static public final String PW = "S##+d57750!9";
    static public final ArrayList<String> orderStates = new ArrayList<>(Arrays.asList("picking", "shipping", "delivered"));
    
    private static String username = new String();
    private static Boolean isUser = true;

    public static String getUsername()
    {
        return username;
    }

    public static Boolean isUser()
    {
        return isUser;
    }

    public static void setUsername(String newName)
    {
        username = newName;
    }

    public static void setIsUser(Boolean newIsUser)
    {
        isUser = newIsUser;
    }

    public static void main(String[] args)
    {
        System.out.println("Welcome to look inna book!");

        Login.login();
    }
}