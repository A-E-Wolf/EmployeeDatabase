# EmployeeDatabase
This program constructs and utilizes a binary search tree to
create and manage a database of employees, sorted by employee number.
The program provides an interface for the user to view, search, and modify
database entries. The Employee class stores data related to an employee
including employee identification number, name, home address, and phone
number. The EmployeeDatabase node contains an Employee field, and
references to the two branch nodes of the node. The EmployeeDatabaseManager
consists of the methods which constitute the core functionality of the
program, including methods which use recursion to add nodes to the tree,
verify if a node exists in the tree, print the contents of the tree in an
inorder traversal, return a reference to a node from the tree, and
delete a single node from the tree. The EmployeeDatabaseInterface class
provides an interface through which a user may view and modify the
contents of a database search tree, and the EmployeeDatabaseMain class
contains the main method as well as an addTestEmployees method for
program testing purposes.
