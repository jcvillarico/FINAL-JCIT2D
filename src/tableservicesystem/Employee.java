package tableservicesystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Employee {

    public void eTransaction() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("");
            System.out.println("[******WELCOME TO EMPLOYEE******]");
            System.out.println("");
            System.out.println("1. --ADD EMPLOYEE--");
            System.out.println("2. --VIEW EMPLOYEE--");
            System.out.println("3. --UPDATE EMPLOYEE--");
            System.out.println("4. --DELETE EMPLOYEE--");
            System.out.println("5. --EXIT EMPLOYEE--");

            int action = -1;

            while (true) {
                System.out.print("Enter Action: ");
                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    if (action >= 1 && action <= 5) {
                        break;
                    } else {
                        System.out.println("Invalid input! Please enter a number between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number between 1 and 5.");
                    sc.next(); 
                }
            }

            Employee em = new Employee();

            switch (action) {
                case 1:
                    em.addEmployee();
                    break;
                case 2:
                    em.viewEmployee();
                    break;
                case 3:
                    em.viewEmployee();
                    em.updateEmployee();
                    em.viewEmployee();
                    break;
                case 4:
                    em.viewEmployee();
                    em.deleteEmployee();
                    em.viewEmployee();
                    break;
                case 5:
                    System.out.println("Exiting Employee Management...");
                    return;
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-5).");
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank You, See you soon!");
    }

    public void addEmployee() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Employee Name: ");
        String ename = sc.nextLine().trim();
        while (ename.isEmpty()) {
            System.out.print("Employee Name cannot be empty. Please enter a valid Employee Name: ");
            ename = sc.nextLine().trim();
        }

        System.out.print("Role: ");
        String role = sc.nextLine().trim();
        while (role.isEmpty()) {
            System.out.print("Role cannot be empty. Please enter a valid Role: ");
            role = sc.nextLine().trim();
        }

        System.out.print("Shift: ");
        String shift = sc.nextLine().trim();
        while (shift.isEmpty()) {
            System.out.print("Shift cannot be empty. Please enter a valid Shift: ");
            shift = sc.nextLine().trim();
        }

        System.out.print("Contact Number (10 digits): ");
        String contact = sc.nextLine().trim();
        while (!contact.matches("\\d{10}")) {
            System.out.print("Invalid contact number! Please enter a 10-digit contact number: ");
            contact = sc.nextLine().trim();
        }

        String sql = "INSERT INTO tbl_employee (e_name, e_role, e_shift, e_contact) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, ename, role, shift, contact);
    }

    public void viewEmployee() {
        config conf = new config();
        String query = "SELECT * FROM tbl_employee";
        String[] headers = {"Employee_ID", "Employee Name", "Role", "Shift", "Contact Number"};
        String[] columns = {"e_id", "e_name", "e_role", "e_shift", "e_contact"};

        conf.viewRecords(query, headers, columns);
    }

    private void updateEmployee() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        
        while (true) {
            System.out.print("Enter the Employee ID to update: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Employee ID: ");
                sc.next();
            }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT e_id FROM tbl_employee WHERE e_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid ID! Please enter a valid ID.");
                sc.nextLine(); 
            }
        }

        sc.nextLine(); 

        System.out.print("New Employee Name: ");
        String nename = sc.nextLine().trim();
        while (nename.isEmpty()) {
            System.out.print("Employee Name cannot be empty. Please enter a valid Employee Name: ");
            nename = sc.nextLine().trim();
        }

        System.out.print("New Role: ");
        String nrole = sc.nextLine().trim();
        while (nrole.isEmpty()) {
            System.out.print("Role cannot be empty. Please enter a valid Role: ");
            nrole = sc.nextLine().trim();
        }

        System.out.print("New Shift: ");
        String nshift = sc.nextLine().trim();
        while (nshift.isEmpty()) {
            System.out.print("Shift cannot be empty. Please enter a valid Shift: ");
            nshift = sc.nextLine().trim();
        }

        System.out.print("New Contact Number (10 digits): ");
        String ncontact = sc.nextLine().trim();
        while (!ncontact.matches("\\d{10}")) {
            System.out.print("Invalid contact number! Please enter a 10-digit contact number: ");
            ncontact = sc.nextLine().trim();
        }

        String qry = "UPDATE tbl_employee SET e_name = ?, e_role = ?, e_shift = ?, e_contact = ? WHERE e_id = ?";
        conf.updateRecord(qry, nename, nrole, nshift, ncontact, id);
    }

    private void deleteEmployee() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        
        while (true) {
            System.out.print("Enter the Employee ID to delete: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Employee ID: ");
                sc.next();
            }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT e_id FROM tbl_employee WHERE e_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid ID! Please enter a valid ID.");
                sc.nextLine(); 
            }
        }

        String qry = "DELETE FROM tbl_employee WHERE e_id = ?";
        conf.deleteRecord(qry, id);
    }
}



