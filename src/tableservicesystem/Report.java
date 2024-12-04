package tableservicesystem;

import java.util.Scanner;

public class Report {
    
    
    public void reportMenu() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("");
            System.out.println("[******WELCOME TO REPORTS******]");
            System.out.println("");
            System.out.println("1. --VIEW ALL GENERAL RECORDS--");
            System.out.println("2. --VIEW CUSTOMER DETAILS--");
            System.out.println("3. --EXIT REPORTS--");

            int action = -1;
            while (true) {
                System.out.print("Enter Action (1-3): ");
                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    if (action >= 1 && action <= 3) {
                        break; 
                    } else {
                        System.out.println("Invalid choice! Please select a valid option (1-3).");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number between 1 and 3.");
                    sc.next(); 
                }
            }

            Report rp = new Report();
            switch (action) {
                case 1:
                    rp.viewGeneralRecords();
                    break;
                case 2:
                    rp.viewCustomerDetails();
                    break;
                case 3:
                    System.out.println("Exiting Reports...");
                    return; 
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-3).");
            }

            
            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank you, See you soon!");
    }

    
    public void viewGeneralRecords() {
        String qry = "SELECT o_id, tbl_customer.c_id, c_name, c_status, tbl_tables.e_id, tbl_employee.e_name, "
                   + "t_capacity, t_location, t_status, o_totalamount, o_status "
                   + "FROM tbl_order "
                   + "LEFT JOIN tbl_customer ON tbl_customer.c_id = tbl_order.c_id "
                   + "LEFT JOIN tbl_tables ON tbl_tables.t_id = tbl_order.t_id "
                   + "LEFT JOIN tbl_employee ON tbl_employee.e_id = tbl_tables.e_id";

        String[] hrds = {"O_ID", "C_ID", "Customer", "Reservation Status", "Assigned Employee ID", 
                         "Employee Name", "Table Capacity", "Location", "Table Status", "Total Amount", "Order Status"};
        String[] clms = {"o_id", "c_id", "c_name", "c_status", "e_id", "e_name", "t_capacity", "t_location", 
                         "t_status", "o_totalamount", "o_status"};

        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }

    
    private void viewCustomerDetails() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        
        int cid = -1;
        while (cid < 1) {
            System.out.print("Enter the Customer ID: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Customer ID: ");
                sc.next(); 
            }
            cid = sc.nextInt();
            String checkCustomerSQL = "SELECT c_id FROM tbl_customer WHERE c_id = ?";
            if (conf.getSingleValue(checkCustomerSQL, cid) == 0) {
                System.out.println("Customer does not exist! Try again.");
                cid = -1; 
            }
        }

        String qry = "SELECT tbl_customer.c_name AS Customer, tbl_employee.e_name AS Employee, "
                   + "tbl_tables.t_capacity AS TableCapacity, tbl_tables.t_location AS Location, "
                   + "tbl_order.o_totalamount AS TotalAmount, tbl_order.o_status AS OrderStatus "
                   + "FROM tbl_order "
                   + "JOIN tbl_customer ON tbl_order.c_id = tbl_customer.c_id "
                   + "JOIN tbl_tables ON tbl_order.t_id = tbl_tables.t_id "
                   + "JOIN tbl_employee ON tbl_tables.e_id = tbl_employee.e_id "
                   + "WHERE tbl_customer.c_id = ?";

        String[] headers = {"Customer Name", "Assigned Employee", "Table Capacity", "Table Location", "Total Amount", "Order Status"};
        String[] columns = {"Customer", "Employee", "TableCapacity", "Location", "TotalAmount", "OrderStatus"};
        
        
        conf.viewRecords(qry, headers, columns, cid);
    }
}
