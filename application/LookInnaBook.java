/*  
 *  COMP3005 F21 Project
 *  Kieran McGregor
 *  101098640
 */

public class LookInnaBook
{
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