// Alexander Wolf
// Employee Database Using Binary Search Tree
// 03/21/2022

package awemployeedatabase; // part of awemployeedatabase package

import java.util.Scanner; // import Scanner class

// begin EmployeeDatabaseMain class
public class EmployeeDatabaseMain {
    public static void main(String[] args) {
        EmployeeDatabaseManager databaseManager =
                new EmployeeDatabaseManager();
        // pass EmployeeDatabaseManager object into
        // EmployeeDatabaseInterface
        EmployeeDatabaseInterface databaseInterface =
                new EmployeeDatabaseInterface(databaseManager);

        // add list of employees to database for testing purposes
        // comment out to begin with empty database
        addTestEmployees(databaseManager);

        databaseInterface.giveMenu();
    } // end main

    // adds a list of employees to database for testing purposes
    public static void addTestEmployees
        (EmployeeDatabaseManager databaseManager) {

        databaseManager.add(new Employee(8336515, "James Smith",
                "1234 Main St", "(012) 345-6789"));
        databaseManager.add(new Employee(7350293, "Mary Williams",
                "1234 Front St", "(012) 345-6789"));
        databaseManager.add(new Employee(9178355, "George Davis",
                "1234 First St", "(012) 345-6789"));
        databaseManager.add(new Employee(2468191, "Anne Clark",
                "1234 Second St", "(012) 345-6789"));
        databaseManager.add(new Employee(4329852, "Sam Miller",
                "1234 Pine St", "(012) 345-6789"));
        databaseManager.add(new Employee(1478383, "Sarah Wilson",
                "1234 Third St", "(012) 345-6789"));
        databaseManager.add(new Employee(5827531, "Henry Davis",
                "1234 Scenic Ave", "(012) 345-6789"));
        databaseManager.add(new Employee(3347321, "Charles Jones",
                "1234 Mountain Pl", "(012) 345-6789"));
    }
} // end EmployeeDatabaseMain

// begin EmployeeDatabaseInterface class; user makes selections to
// view or modify database
class EmployeeDatabaseInterface {
    private EmployeeDatabaseManager databaseManager;

    // post: EmployeeDatabaseManager field has been initialized
    public EmployeeDatabaseInterface
            (EmployeeDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    } // end constructor

    // post: user has made selections from menu to view or
    // modify database
    public void giveMenu() {
        // create Scanner object to handle user input
        Scanner console = new Scanner(System.in);
        String selection; // initialize user selection String

        giveIntroduction();
        do {
            // interface menu
            System.out.println("1 to add an entry\n"
                    + "2 to view all entries\n"
                    + "3 to search for an entry\n"
                    + "4 to edit an entry\n"
                    + "5 to delete an entry\n"
                    + "6 to clear all current entries\n"
                    + "7 to quit");
            // user menu selection
            selection = console.nextLine();
            System.out.println();

            // select action from user input
            switch (selection) {
                case "1": // add an employee
                    addEmployee(console);
                    System.out.println();
                    break;

                case "2": // view all entries
                    databaseManager.printInorder();
                    break;

                case "3": // search for employee
                    // search for entry and
                    // print if found
                    findEntry(console);
                    break;

                case "4": // edit employee information
                    editEntry(console);
                    break;

                case "5": // delete an entry
                    deleteEntry(console);
                    break;

                case "6": // clear database
                    clearDatabase(console);
                    break;

                case "7": // quit program
                    break;

                default: // any other user input
                    System.out.println("Please enter a valid command.\n");
                    break;
            } // end outer switch
        } while (!selection.equals("7")); // end do while
    } // end giveMenu

    // post: introduction to database management interface has been
    // printed to console
    public static void giveIntroduction() {
        System.out.println("Welcome to the employee database management " +
                "program.\nSelect from the options to add to the database, " +
                "modify\nentries to the database, or view all entries in the\n" +
                "database.");
    } // end giveIntroduction

    // post: employee entry has been added via menu
    public void addEmployee(Scanner console) {
        System.out.print("Employee identification number: ");
        String employeeID = console.nextLine();
        System.out.print("Full name: ");
        String name = console.nextLine();
        System.out.print("Home address: ");
        String homeAddress = console.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = console.nextLine();

        databaseManager.add(new Employee(Integer.parseInt(employeeID),
                name, homeAddress, phoneNumber));
    } // end addEmployee

    // post: entry has been searched for via menu, found and printed, or
    // user has been notified that entry does not exist
    public void findEntry(Scanner console) {
        int employeeID;
        do {
            System.out.print("Identification number to search for: ");
            employeeID = getIntFromInput(console);
        } while (employeeID == -1);


        // get node from tree with matching ID number; null if no match
        // found
        EmployeeDatabaseNode databaseNode = databaseManager.getNode(employeeID);

        // print Employee data from node if node is not null, otherwise
        // inform user that node does not exist
        if (databaseNode != null) {
            System.out.println(databaseNode.getEmployee());
        } else {
            System.out.println("The database does not contain an\n" +
                    "entry for identification number " + employeeID +
                    ".\n");
        } // end if/else
    } // end findEntry

    // post: an information field for a specific employee in database
    // has been modified
    public void editEntry(Scanner console) {
        int employeeID;
        do {
            System.out.print("Identification number of employee whose " +
                    "information\nyou would like to edit: ");
            employeeID = getIntFromInput(console);
        } while (employeeID == -1);

        // check if database contains matching employee ID
        if (databaseManager.contains(employeeID)) {
            int editSelection = 0;
            do {
                System.out.print("Which information field would you " +
                        "like to edit:\n" +
                        "name(1), address(2), phone number(3),\n" +
                        "or exit information editor(4)? ");

                // catch Exception if user enters char instead of int
                editSelection = getIntFromInput(console);

                // pass constant from Field enum into edit method to
                // determine which field to edit
                switch (editSelection) {
                    case 1: // edit name
                        editField(employeeID, Field.NAME, console);
                        break;
                    case 2: // edit address
                        editField(employeeID, Field.HOMEADDRESS, console);
                        break;
                    case 3: // edit phone number
                        editField(employeeID, Field.PHONENUMBER, console);
                        break;
                    default:
                        // do nothing
                        break;
                } // end switch
            } while (editSelection < 1 || editSelection > 3);
        } else { // if no employee with that ID in database
            System.out.println("The database does not contain an\n" +
                    "entry for identification number " + employeeID +
                    ".");
        } // end if/else
        System.out.println();
    } // end editEntry

    // post: specified field has been modified with new information
    public void editField(int employeeID, Field field, Scanner console) {
        System.out.print("What is the new " + field +
                " for this employee? ");
        String editField = console.nextLine();

        // Field parameter determines which set method to call
        switch (field) {
            case NAME:
                databaseManager.getNode(employeeID).getEmployee().
                        setName(editField);
                break;
            case HOMEADDRESS:
                databaseManager.getNode(employeeID).getEmployee().
                        setHomeAddress(editField);
                break;
            case PHONENUMBER:
                databaseManager.getNode(employeeID).getEmployee().
                        setPhoneNumber(editField);
                break;
        } // end switch

        System.out.print("\nEmployee " + field + " is now " +
                editField + ".\n");
    } // end editField

    // post: employee entry matching employee ID number has been
    // removed from search tree
    public void deleteEntry(Scanner console) {
        int employeeID;
        do {
            System.out.print("ID number of employee whose entry you would\n" +
                    "like to delete: ");
            employeeID = getIntFromInput(console);
        } while (employeeID == -1);

        if (databaseManager.contains(employeeID)) {
            databaseManager.deleteNode(employeeID);
            System.out.println("Entry for employee number " + employeeID +
                    " has been removed.");
        } else {
            System.out.println("The database does not contain an\n" +
                    "entry for identification number " + employeeID +
                    ".\n");
        } // end outer if/else
        System.out.println();
    } // end deleteEntry

    // post: user has selected whether to clear database of entries
    public void clearDatabase(Scanner console) {
        String response = "";
        do {
            System.out.println("Are you sure you want to delete " +
                    "all entries\nfrom the database(y/n)?");
            response = console.nextLine();
        } while (!response.equals("y") && !response.equals("n"));

        if (response.equals("y")) {
            // set overall root to null, making database empty
            databaseManager.setOverallRoot(null);
            System.out.println("Database has been cleared of entries.\n");
        } else {
            System.out.println("Database has not been cleared of entries.\n");
        } // end if/else
    } // emd clearDatabase

    // post: integer from input has been returned if successfully
    // parsed, otherwise -1 has been returned
    public int getIntFromInput(Scanner console) {
        // catch Exception if user enters char instead of int
        try {
            return Integer.parseInt(console.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\nPlease enter an integer.");
        } // end try/catch
        return -1;
    } // end getIntFromInput
} // end EmployeeDatabaseInterface

// begin EmployeeDatabase class; contains field for overall root
// and primary methods related to search tree functions
class EmployeeDatabaseManager {
    private EmployeeDatabaseNode overallRoot; // root of search tree

    // post: constructs an empty tree
    public EmployeeDatabaseManager() {
        overallRoot = null;
    } // end constructor

    // post: value is added to overall tree, preserving binary
    // search tree property
    public void add(Employee employee) {
        overallRoot = add(overallRoot, employee);
    } // end add

    // post: value is added to overall tree, preserving binary
    // search tree property
    private EmployeeDatabaseNode add(EmployeeDatabaseNode root,
                                     Employee employee) {
        if (root == null) {
            root = new EmployeeDatabaseNode(employee);
        } else if (employee.compareTo(root.getEmployee()) < 0) {
            root.left = add(root.left, employee);
        } else {
            root.right = add(root.right, employee);
        }
        return root;
    } // end add

    // post: returns true if overall tree contains value
    public boolean contains(int employeeIDNum) {
        return contains(overallRoot, employeeIDNum);
    } // end contains

    // post: returns true if overall tree contains value
    private boolean contains(EmployeeDatabaseNode root, int employeeIDNum) {
        if (root == null) {
            return false;
        } else if (employeeIDNum == root.getEmployee().getEmployeeIDNum()) {
            return true;
        } else if (employeeIDNum < root.getEmployee().getEmployeeIDNum()) {
            return contains(root.left, employeeIDNum);
        } else { // employee ID value > root employee ID value
            return contains(root.right, employeeIDNum);
        }
    } // end contains

    // post: prints the tree contents using an inorder traversal
    public void printInorder() {
        printInorder(overallRoot);
    } // end print

    // post: prints contents of tree with given root using inorder traversal
    private void printInorder(EmployeeDatabaseNode root) {
        if (root != null) {
            printInorder(root.left);
            System.out.println(root.getEmployee());
            printInorder(root.right);
        } // end if
    } // end printInorder

    // post: prints single node from search tree
    public EmployeeDatabaseNode getNode(int employeeIDNum) {
        return getNode(overallRoot, employeeIDNum);
    } // end printLeaf

    // post: returns single node from search tree, returns null if
    // node does not exist
    private EmployeeDatabaseNode getNode(EmployeeDatabaseNode root,
                                         int employeeIDNum) {
        if (root == null) {
            return null;
        } else if (employeeIDNum == root.getEmployee().getEmployeeIDNum()) {
            return root;
        } else if (employeeIDNum < root.getEmployee().getEmployeeIDNum()) {
            return getNode(root.left, employeeIDNum);
        } else { // value > root.data
            return getNode(root.right, employeeIDNum);
        } // end if/else
    } // end printLeaf

    // post: node containing Employee with matching employee ID number
    // has been removed, preserving binary search tree property
    public void deleteNode(int employeeIDNum) {
        overallRoot = deleteNode(overallRoot, employeeIDNum);
    } // end deleteNode

    // post: node containing Employee with matching employee ID number
    // has been removed, preserving binary search tree property
    private EmployeeDatabaseNode deleteNode(EmployeeDatabaseNode root,
                                           int employeeIDNum) {
        if (root == null) {
            return null; // node with matching employee ID does not exist
        } else if (employeeIDNum < root.getEmployee().getEmployeeIDNum()) {
            // recursively traverse tree looking for matching node
            root.left = deleteNode(root.left, employeeIDNum);
        } else if (employeeIDNum > root.getEmployee().getEmployeeIDNum()) {
            root.right = deleteNode(root.right, employeeIDNum);
        } else { // employeeIDNum values match, this is the node to be deleted
            // if parent node has only one child, return it as replacement
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } // end inner if/else

            // replace Employee in root node with Employee from inorder
            // traversal of ID values, which is min ID from right subtree
            root.setEmployee(getMin(root.right));

            // delete node containing Employee just used for replacement
            root.right = deleteNode(root.right, root.getEmployee().getEmployeeIDNum());
        } // end outer if/else
        return root; // return root with new Employee value
    } // end deleteNode

    // post: tree has been traversed beginning at specified root in
    // leftward path to find and return node containing Employee with
    // least ID value
    public Employee getMin(EmployeeDatabaseNode root) {
        Employee min = root.getEmployee();
        // move left while left nodes exist
        while (root.left != null) {
            min = root.left.getEmployee();
            root = root.left;
        }
        return min; // int from node with smalled value
    } // end getMin

    // mutator method
    public void setOverallRoot(EmployeeDatabaseNode overallRoot) {
        this.overallRoot = overallRoot;
    } // end setOverallRoot

    // accessor method
    public EmployeeDatabaseNode getOverallRoot() {
        return overallRoot;
    } // end getOverallRoot
} // end EmployeeDatabase

// begin EmployeeDatabaseNode; contains references to child nodes
// as well as Employee field
class EmployeeDatabaseNode {
    public EmployeeDatabaseNode left;
    public EmployeeDatabaseNode right;
    private Employee employee; // stores data about an employee

    // post: constructs leaf node with given data
    public EmployeeDatabaseNode(Employee employee) {
        this(employee, null, null);
    } // end constructor

    // post: constructs node with given data and links
    public EmployeeDatabaseNode(Employee employee, EmployeeDatabaseNode left,
                                EmployeeDatabaseNode right) {
        this.employee = employee;
        this.left = left;
        this.right = right;
    } // end constructor

    // mutator method
    public void setEmployee(Employee employee) {
        this.employee = employee;
    } // end setEmployee

    // accessor method
    public Employee getEmployee() {
        return employee;
    } // end getEmployee
} // end EmployeeDatabaseNode

// begin Employee class; store information related to a single employee,
// such as ID number, name, home address, and phone number
// contains compareTo method to allow comparison of two objects based on
// employee ID number
class Employee{
    int employeeIDNum;
    String name;
    String homeAddress;
    String phoneNum;

    // begin constructor
    public Employee(int employeeIDNum, String name, String homeAddress,
                    String phoneNum) {
        this.employeeIDNum = employeeIDNum;
        this.name = name;
        this.homeAddress = homeAddress;
        this.phoneNum = phoneNum;
    } // end constructor

    // begin mutator methods
    public void setEmployeeIDNum(int employeeIDNum) {
        this.employeeIDNum = employeeIDNum;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
    public void setPhoneNumber(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    // begin accessor methods
    public int getEmployeeIDNum() {
        return employeeIDNum;
    }
    public String getName() {
        return name;
    }
    public String getHomeAddress() {
        return homeAddress;
    }
    public String getPhoneNum() {
        return phoneNum;
    }

    // post: String representation of employee information
    // has been returned
    public String toString() {
        return this.employeeIDNum + "\n"
                + this.name + "\n"
                + this.homeAddress + "\n"
                + this.phoneNum + "\n";
    } // end toString

    // post: two employee ID number values have been compared
    // negative number returned if this Employee ID is less than parameter
    // ID, positive number returned if parameter ID is less than this
    // Employee ID, 0 returned if Employee ID numbers are equal
    public int compareTo(Employee employee) {
        return this.employeeIDNum - employee.getEmployeeIDNum();
    } // end compareTo
} // end Employee class

// begin Field enum; define constants related to employee
// information fields
enum Field {
    NAME("name"), HOMEADDRESS("home address"),
    PHONENUMBER("phone number");

    // String representation of field
    private String fieldType;

    // begin constructor
    Field(String fieldType) {
        this.fieldType = fieldType;
    } // end constructor

    // post: String representation of field
    // has been returned
    public String toString() {
        return fieldType;
    } // end toString
} // end Field enum