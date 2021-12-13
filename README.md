# COMP3005Project
COMP3005 Project F2021

Kieran McGregor
101098640

REQUIREMENTS
-PostgreSQL server 14
-Java 8
-PostgreSQL 4.3.1 JDBC driver (must be in JRE to work)

PROVIDED
-The provided DDL.sql file will create the LookInnaBook database, all tables, add the warehous and shipping service and establish the threshold trigger
-All .java files required to run the program

INSTRUCTIONS
-Change the attributes in the following lines in LookInnaBook.java

    static public final String DB_URL = "jdbc:postgresql://localhost:{PORT}/lookinnabook";
    static public final String USER = "{DB USERNAME}";
    static public final String PW = "{DB PASSWORD}";

-Goto the file where Java files are being stored and in the CLI enter
    
    "javac *.java" to compile the code
    "java LookInnaBook" to run the code

-Register as a user or owner to test user and owner functionality