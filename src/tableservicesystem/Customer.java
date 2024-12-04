package tableservicesystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer {

    public void customerTransaction() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("");
            System.out.println("[******WELCOME CUSTOMER******]");
            System.out.println("");
            System.out.println("1. --ADD CUSTOMER--");
            System.out.println("2. --VIEW CUSTOMER--");
            System.out.println("3. --UPDATE CUSTOMER--");
            System.out.println("4. --DELETE CUSTOMER--");
            System.out.println("5. --EXIT CUSTOMER--");

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
                    sc.next(); // clear invalid input
                }
            }

            Customer cr = new Customer();

            switch (action) {
                case 1:
                    cr.addCustomers();
                    break;
                case 2:
                    cr.viewCustomers();
                    break;
                case 3:
                    cr.viewCustomers();
                    cr.updateCustomers();
                    cr.viewCustomers();
                    break;
                case 4:
                    cr.viewCustomers();
                    cr.deleteCustomers();
                    cr.viewCustomers();
                    break;
                case 5:
                    System.out.println("Exiting Customer Management...");
                    return;
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-5).");
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank You, See you soon!");
    }

    public void addCustomers() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Customer Name: ");
        String name = sc.nextLine().trim();

        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter a valid Customer Name: ");
            name = sc.nextLine().trim();
        }

        System.out.print("Contact Number: ");
        String cont = sc.nextLine().trim();

        while (!cont.matches("\\d{10}")) {  // Validate contact number format (10 digits)
            System.out.print("Invalid contact number! Please enter a 10-digit contact number: ");
            cont = sc.nextLine().trim();
        }

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        while (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) { // Email format validation
            System.out.print("Invalid email format! Please enter a valid email: ");
            email = sc.nextLine().trim();
        }

        System.out.print("Reservation Status: ");
        String stat = sc.nextLine().trim();

        while (stat.isEmpty()) {
            System.out.print("Reservation Status cannot be empty. Please enter a valid Reservation Status: ");
            stat = sc.nextLine().trim();
        }

        String sql = "INSERT INTO tbl_customer (c_name, c_contact, c_email, c_status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, name, cont, email, stat);
    }

    public void viewCustomers() {
        config conf = new config();
        String Query = "SELECT * FROM tbl_customer";
        String[] Headers = {"Customers_ID", "Customer Name", "Contact Number", "Email", "Status"};
        String[] Columns = {"c_id", "c_name", "c_contact", "c_email", "c_status"};

        conf.viewRecords(Query, Headers, Columns);
    }

    private void updateCustomers() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the ID to update: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Customer ID: ");
                sc.next();
            }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", id) != 0) {
                    break; // Customer exists
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid ID! Please enter a valid ID.");
                sc.nextLine(); // clear buffer
            }
        }

        sc.nextLine(); // Clear the buffer before reading string inputs

        System.out.println("New Customer Name: ");
        String nname = sc.nextLine().trim();
        while (nname.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter a valid Customer Name: ");
            nname = sc.nextLine().trim();
        }

        System.out.println("New Contact Number: ");
        String ncont = sc.nextLine().trim();
        while (!ncont.matches("\\d{10}")) {  // Validate contact number format (10 digits)
            System.out.print("Invalid contact number! Please enter a 10-digit contact number: ");
            ncont = sc.nextLine().trim();
        }

        System.out.println("New Email: ");
        String nemail = sc.nextLine().trim();
        while (!nemail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) { // Email format validation
            System.out.print("Invalid email format! Please enter a valid email: ");
            nemail = sc.nextLine().trim();
        }

        System.out.println("New Reservation Status: ");
        String nstat = sc.nextLine().trim();
        while (nstat.isEmpty()) {
            System.out.print("Reservation Status cannot be empty. Please enter a valid Reservation Status: ");
            nstat = sc.nextLine().trim();
        }

        String qry = "UPDATE tbl_customer SET c_name = ?, c_contact = ?, c_email = ?, c_status = ? WHERE c_id = ?";
        conf.updateRecord(qry, nname, ncont, nemail, nstat, id);
    }

    private void deleteCustomers() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the ID to delete: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Customer ID: ");
                sc.next();
            }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", id) != 0) {
                    break; // Customer exists
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid ID! Please enter a valid ID.");
                sc.nextLine(); // clear buffer
            }
        }

        String qry = "DELETE FROM tbl_customer WHERE c_id = ?";
        conf.deleteRecord(qry, id);
    }
}

