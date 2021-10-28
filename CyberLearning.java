import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Class Object for Cyber Learning attendance system
 * @author seanh
 */
public class CyberLearning {
    
    private String url = "jdbc:sqlite:sqlite/CyberLearning.db";
    
    /**
     * Main method for running the system
     * @param args 
     */
    public static void main(String[] args) {
        CyberLearning me = new CyberLearning();
        me.mainMenu();
    }
    
    /**
     * Method for the main menu that pops up when system is run
     */
    public void mainMenu() {
        System.out.println("Welcome to the Cyber Learning Portal");
        System.out.println("Please state the number for the task you wish to complete:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("1-Search Student");
        System.out.println("2-Add new Student");
        System.out.println("3-Delete Student");
        System.out.println("4-Update Student");
        System.out.println("5-Add Attendance to new Event");
        System.out.println("6-Add new Badge, Test, Topic or Part");
        System.out.println("7-Award Student Badge, Test, Topic or Part");
        System.out.println("8-View all students");
        System.out.println("9-View all badges earned");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                searchStudent();
                break;
            case "2":
                addStudent();
                break;
            case "3":
                deleteStudent();
                break;
            case "4":
                updateStudent();
                break;
            case "5":
                attendance();
                break;
            case "6":
                addCourseWork();
                break;
            case "7":
                awardCoursework();
                break;
            case "8":
                query("SELECT * FROM Students;");
                mainMenu();
                break;
            case  "9":
                query("SELECT * FROM BadgesEarned;");
                mainMenu();
                break;
            default:
                System.out.println("Invalid Input, try again");
                mainMenu();
        }
    }
    
    /**
     * Method for searching for a student
     */
    public void searchStudent() {
        Scanner scanner = new Scanner(System.in);
        String IDInput, firstInput, lastInput = "";
        Integer paramCount = 0;
        boolean ID = false;
        boolean first = false;
        boolean last = false;
        System.out.println("Select Student parameters to search with");
        System.out.println("More than one parameter can be chosen by placing a comma between two numbers");
        System.out.println("1-Student ID");
        System.out.println("2-First name");
        System.out.println("3-Last name");
        String input = scanner.nextLine();
        String sql = "SELECT * FROM Students WHERE " ;
        if (input.contains("1")) {
            ID = true;
            System.out.println("Enter ID to search:");
            IDInput = "studentID = \"" + scanner.nextLine() + "\"";
            paramCount++;
            sql = sql + IDInput;
        }
        if (input.contains("2")) {
            first = true;
            if (ID) {
                sql = sql + " AND ";
            }
            System.out.println("Enter first name to search:");
            firstInput = "firstName = \"" + scanner.nextLine() + "\"";
            sql = sql + firstInput;
            paramCount++;
        }
        if (input.contains("3")) {
            last = true;
            if (ID || first) {
                sql = sql + " AND ";
            }
            System.out.println("Enter last name to search:");
            lastInput = "last Name = \"" + scanner.nextLine() + "\"";
            sql = sql + lastInput;
            paramCount++;
        }
        //sql = sql + ";";
        query(sql);
        System.out.println("");
        mainMenu();
    }
    
    /**
     * Method for adding a new student
     */
    public void addStudent(){
        Scanner scanner = new Scanner(System.in);
        String IDInput, firstInput, lastInput, DOBInput, emailInput, phoneInput = "";
        System.out.println("Enter ID of new Student:");
        IDInput = scanner.nextLine();
        System.out.println("Enter first name of new Student:");
        firstInput = scanner.nextLine();
        System.out.println("Enter last name of new Student:");
        lastInput = scanner.nextLine();
        System.out.println("Enter DOB(dd/mm/yyyy) of new Student:");
        DOBInput = scanner.nextLine();
        System.out.println("Enter email of new Student:");
        emailInput = scanner.nextLine();
        System.out.println("Enter phone number of new Student:");
        phoneInput = scanner.nextLine();
        String sql = "INSERT INTO Students VALUES('" + IDInput + "','" + firstInput + "','" + lastInput + "','" + DOBInput + "','" + emailInput + "','" + phoneInput + "');";
        System.out.println(sql);
        insert(sql);
        System.out.println("");
        mainMenu();
    }
    
    /**
     * Method for deleting student
     */
    public void deleteStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Student ID of Student to be deleted: ");
        String IDInput = scanner.nextLine();
        System.out.println("Deleting " + IDInput + ", are you sure? (type 'yes' to confirm, just press enter to cancel)");
        String confirm = scanner.nextLine();
        if (confirm.equals("yes")) {
            String sql = "DELETE FROM Students WHERE studentID = '" + IDInput + "';";
            insert(sql);
        }
        System.out.println("");
        mainMenu();
    }
    
    /**
     * Method for updating a student
     */
    public void updateStudent() {
        Scanner scanner = new Scanner(System.in);
        String IDInput, sql = "";
        System.out.println("Student ID of Student to be updated: ");
        IDInput = scanner.nextLine();
        System.out.println("Field to update: ");
        System.out.println("1-First name");
        System.out.println("2-Last name");
        System.out.println("3-DOB(dd/mm/yyyy)");
        System.out.println("4-email");
        System.out.println("5-phone");
        String input = scanner.nextLine();
        switch(input) {
            case "1":
                System.out.println("Enter updated first name: ");
                sql = "UPDATE Students SET firstName = '" + scanner.nextLine() + "' WHERE studentID = '" + IDInput + "'";
                insert(sql);
                break;
            case "2":
                System.out.println("Enter updated last name: ");
                sql = "UPDATE Students SET lastName = '" + scanner.nextLine() + "' WHERE studentID = '" + IDInput + "'";
                insert(sql);
                break;
            case "3":
                System.out.println("Enter updated DOB(dd/mm/yyyy): ");
                sql = "UPDATE Students SET DOB = '" + scanner.nextLine() + "' WHERE studentID = '" + IDInput + "'";
                insert(sql);
                break;
            case "4":
                System.out.println("Enter updated email: ");
                sql = "UPDATE Students SET email = '" + scanner.nextLine() + "' WHERE studentID = '" + IDInput + "'";
                insert(sql);
                break;
            case "5":
                System.out.println("Enter updated phone: ");
                sql = "UPDATE Students SET phone = '" + scanner.nextLine() + "' WHERE studentID = '" + IDInput + "'";
                insert(sql);
                break;
            default:
                System.out.println("Invalid Input, returning to main menu");
        }
        System.out.println("");
        mainMenu();
    }
    
    /**
     * Method for marking attendance
     */
    public void attendance(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert ID for event:");
        String EventID = scanner.nextLine();
        System.out.println("Insert date (dd/mm/yyyy) for event:");
        String date = scanner.nextLine();
        insert("INSERT INTO Attendables VALUES('" + EventID + "','" + date + "')");
        System.out.println("Enter studentID of attending student, one per line");
        System.out.println("Enter 'Done' once all students have been marked for attendance");
        String input = scanner.nextLine();
        while (!input.equals("Done")) {
            insert ("INSERT INTO ATTENDANCE VALUES('" + EventID + "','" + input + "')");
            input = scanner.nextLine();
        }
        System.out.println("Attendance marking completed");
        System.out.println("");
        mainMenu();
    }
    
    /**
     * method for adding coursework
     */
    public void addCourseWork(){
        Scanner scanner = new Scanner(System.in);
        String sql, badgeID, testID, topicID, partID;
        System.out.println("Select Coursework type:");
        System.out.println("1-Badge");
        System.out.println("2-Test");
        System.out.println("3-Topic");
        System.out.println("4-Part");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                System.out.println("Enter Badge ID:");
                badgeID = scanner.nextLine();
                System.out.println("Enter Badge Type (A, S or C:");
                String badgeType = scanner.nextLine();
                System.out.println("Enter Badge Level (Lithium, Platinum or Diamond:");
                String badgeLevel = scanner.nextLine();
                sql = "INSERT INTO Badges VALUES('" + badgeID + "','" + badgeType + "','" + badgeLevel + "');";
                insert(sql);
                break;
            case "2":
                System.out.println("Enter Test ID:");
                testID = scanner.nextLine();
                System.out.println("Enter Badge ID:");
                badgeID = scanner.nextLine();
                System.out.println("Enter test number for badge:");
                String testNumber = scanner.nextLine();
                System.out.println("Enter whether optional test for badge (0-no, 1-yes):");
                String optional = scanner.nextLine();   
                System.out.println("Enter number of required topics to pass:");
                String topicsNum = scanner.nextLine();  
                sql = "INSERT INTO Tests VALUES('" + testID + "','" + badgeID + "','" + testNumber + "','" + optional + "','" + topicsNum + "');";
                insert(sql);
                break;
            case "3":
                System.out.println("Enter topic ID:");
                topicID = scanner.nextLine();
                System.out.println("Enter test ID:");
                testID = scanner.nextLine(); 
                System.out.println("Enter number of parts in topic:");
                String topicNum = scanner.nextLine();  
                sql = "INSERT INTO Topics VALUES('" + topicID + "','" + testID + "','" + topicNum + "');";
                insert(sql);
                break;
            case "4":
                System.out.println("Enter part ID:");
                partID = scanner.nextLine();
                System.out.println("Enter topic ID:");
                topicID = scanner.nextLine();
                sql = "INSERT INTO Parts VALUES('" + partID + "','"  + topicID + "');";
                insert(sql);
                break;
            default:
                System.out.println("Invalid input, returning to main menu");
        }
        mainMenu();
    }
    
    /**
     * method for awarding student coursework
     */
    public void awardCoursework(){
        Scanner scanner = new Scanner(System.in);
        String sql, badgeID, testID, topicID, partID, studentID, date;
        System.out.println("Select Coursework type to award:");
        System.out.println("1-Badge");
        System.out.println("2-Test");
        System.out.println("3-Topic");
        System.out.println("4-Part");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                System.out.println("Enter Badge ID:");
                badgeID = scanner.nextLine();
                System.out.println("Enter Student ID:");
                studentID = scanner.nextLine();
                System.out.println("Enter date(dd/mm/yyyy):");
                date = scanner.nextLine();
                sql = "INSERT INTO BadgesEarned VALUES('" + badgeID + "','" + studentID + "','" + date + "');";
                insert(sql);
                break;
            case "2":
                System.out.println("Enter Test ID:");
                testID = scanner.nextLine();
                System.out.println("Enter Student ID:");
                studentID = scanner.nextLine();
                System.out.println("Enter date(dd/mm/yyyy):");
                date = scanner.nextLine();
                sql = "INSERT INTO TestsCompleted VALUES('" + testID + "','" + studentID + "','" + date + "');";
                insert(sql);
                break;
            case "3":
                System.out.println("Enter Topic ID:");
                topicID = scanner.nextLine();
                System.out.println("Enter Student ID:");
                studentID = scanner.nextLine();
                System.out.println("Enter date(dd/mm/yyyy):");
                date = scanner.nextLine();
                sql = "INSERT INTO TopicsCompleted VALUES('" + topicID + "','" + studentID + "','" + date + "');";
                insert(sql);
                break;
            case "4":
                System.out.println("Enter Part ID:");
                partID = scanner.nextLine();
                System.out.println("Enter Student ID:");
                studentID = scanner.nextLine();
                System.out.println("Enter date(dd/mm/yyyy):");
                date = scanner.nextLine();
                sql = "INSERT INTO PartsDone VALUES('" + partID + "','" + studentID + "','" + date + "');";
                insert(sql);
                break;
            default:
                System.out.println("Invalid input, returning to main menu");
        }
        mainMenu();
    }
    
    /**
     * Method for connecting to database
     * @return 
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection successfully established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    /**
     * method to query database
     * @param sql 
     */
    public void query(String sql) {
        try {
            Connection conn = this.connect();
        
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            System.out.println("***Start of results***");
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        System.out.print("\t");
                    }
                    String columnValue = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println("");
            }
            System.out.println("***End of results***");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * method for inserting into database
     * @param sql 
     */
    public void insert(String sql) {
        try {
            Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Successfully executed to database");
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
