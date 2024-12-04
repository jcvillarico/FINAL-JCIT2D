package tableservicesystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Order {

    public void orderTransaction() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("[******WELCOME TO ORDERS******]");    
            System.out.println("1. --ADD ORDERS--");
            System.out.println("2. --VIEW ORDERS--");
            System.out.println("3. --UPDATE ORDERS--");
            System.out.println("4. --DELETE ORDERS--");
            System.out.println("5. --EXIT ORDERS-- ");
            
            int action = -1;
            while (action < 1 || action > 5) {
                System.out.print("Enter Action (1-5): ");
                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    if (action < 1 || action > 5) {
                        System.out.println("Invalid choice! Please select a number between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number between 1 and 5.");
                    sc.next(); 
                }
            }

            Order or = new Order();
            switch (action) {
                case 1:
                    or.addOrders();
                    or.viewOrders();
                    break;
                case 2:       
                    or.viewOrders();
                    break;
                case 3:
                    or.viewOrders();
                    or.updateOrders();
                    or.viewOrders();
                    break;
                case 4:
                    or.viewOrders();
                    or.deleteOrders();
                    or.viewOrders();
                    break;
                case 5:
                    System.out.println("Exiting Order Management...");
                    return; 
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
        
        System.out.println("Thank You, See you soon!");
    }

    public void addOrders() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        Customer cr = new Customer();
        cr.viewCustomers(); 
        
        int cid = -1;
        while (cid < 1) {
            System.out.print("Enter the ID of the Customer: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Customer ID: ");
                sc.next(); 
            }
            cid = sc.nextInt();
            String csql = "SELECT c_id FROM tbl_customer WHERE c_id = ?";
            if (conf.getSingleValue(csql, cid) == 0) {
                System.out.println("Customer does not exist, try again.");
                cid = -1; 
            }
        }
        
        Table tb = new Table();
        tb.viewTables(); 
        
        int tid = -1;
        while (tid < 1) {
            System.out.print("Enter the ID of the Table: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Table ID: ");
                sc.next(); 
            }
            tid = sc.nextInt();
            String tsql = "SELECT t_id FROM tbl_tables WHERE t_id = ?";
            if (conf.getSingleValue(tsql, tid) == 0) {
                System.out.println("Table does not exist, try again.");
                tid = -1; 
            }
        }

        double total = -1;
        while (total < 0) {
            System.out.print("Total Amount: ");
            while (!sc.hasNextDouble()) {
                System.out.print("Invalid amount! Please enter a valid Total Amount: ");
                sc.next(); 
            }
            total = sc.nextDouble();
        }

        System.out.print("Order Status: ");
        String stat = sc.next().trim();
        while (stat.isEmpty()) {
            System.out.print("Order Status cannot be empty! Please enter a valid Order Status: ");
            stat = sc.next().trim();
        }

        String qry = "INSERT INTO tbl_order (c_id, t_id, o_totalamount, o_status) VALUES (?, ?, ?, ?)";
        conf.addRecord(qry, cid, tid, total, stat);
    }

    public void viewOrders() {
        String qry = "SELECT o_id, c_name, c_status, tbl_tables.e_id, tbl_employee.e_name, t_capacity, t_location, t_status, o_totalamount, o_status "
                   + "FROM tbl_order "
                   + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_order.c_id "
                   + "LEFT JOIN tbl_tables ON tbl_tables.t_id = tbl_order.t_id "
                   + "LEFT JOIN tbl_employee ON tbl_employee.e_id = tbl_tables.e_id";

        String[] hrds = {"O_ID", "Customer", "Reservation Status", "Assigned Employee ID", "Employee Name", "Table Capacity", "Location", "Table Status", "Total Amount", "Order Status"};
        String[] clms = {"o_id", "c_name", "c_status", "e_id", "e_name", "t_capacity", "t_location", "t_status", "o_totalamount", "o_status"};
        
        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }

    private void updateOrders() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id = -1;

        while (id < 1) {
            System.out.print("Enter the Order ID to update: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Order ID: ");
                sc.next(); 
            }
            id = sc.nextInt();
            if (conf.getSingleValue("SELECT o_id FROM tbl_order WHERE o_id = ?", id) == 0) {
                System.out.println("Selected Order ID doesn't exist! Try again.");
                id = -1; 
            }
        }

        sc.nextLine(); 
        System.out.print("New Order Status: ");
        String status = sc.nextLine().trim();
        while (status.isEmpty()) {
            System.out.print("Order Status cannot be empty! Please enter a valid Order Status: ");
            status = sc.nextLine().trim();
        }

        String qry = "UPDATE tbl_order SET o_status = ? WHERE o_id = ?";
        conf.updateRecord(qry, status, id);
    }

    private void deleteOrders() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id = -1;

        while (id < 1) {
            System.out.print("Enter the Order ID to delete: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Order ID: ");
                sc.next(); 
            }
            id = sc.nextInt();
            if (conf.getSingleValue("SELECT o_id FROM tbl_order WHERE o_id = ?", id) == 0) {
                System.out.println("Selected Order ID doesn't exist! Try again.");
                id = -1; 
            }
        }

        String qry = "DELETE FROM tbl_order WHERE o_id = ?";
        conf.deleteRecord(qry, id);
    }
}
