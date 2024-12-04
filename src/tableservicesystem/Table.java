package tableservicesystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Table {

    
    public void tableTransaction() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("");
            System.out.println("[******WELCOME TO TABLES******]");
            System.out.println("");
            System.out.println("1. --ADD TABLE--");
            System.out.println("2. --VIEW TABLE--");
            System.out.println("3. --UPDATE TABLE--");
            System.out.println("4. --DELETE TABLE--");
            System.out.println("5. --EXIT TABLE--");

            int action = -1;

            
            while (true) {
                System.out.print("Enter Action (1-5): ");
                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    if (action >= 1 && action <= 5) {
                        break; 
                    } else {
                        System.out.println("Invalid choice! Please select a valid option (1-5).");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number between 1 and 5.");
                    sc.next(); 
                }
            }

            Table tb = new Table();

            switch (action) {
                case 1:
                    tb.addTables();
                    tb.viewTables();
                    break;
                case 2:
                    tb.viewTables();
                    break;
                case 3:
                    tb.viewTables();
                    tb.updateTables();
                    tb.viewTables();
                    break;
                case 4:
                    tb.viewTables();
                    tb.deleteTables();
                    tb.viewTables();
                    break;
                case 5:
                    System.out.println("Exiting Table Management...");
                    return; 
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-5).");
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank You, See you soon!");
    }

    
    public void addTables() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        Employee em = new Employee();
        em.viewEmployee();

        int eid;
        
        while (true) {
            System.out.print("Enter the Employee ID to assign the table: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Employee ID: ");
                sc.next(); 
            }
            try {
                eid = sc.nextInt();
                if (conf.getSingleValue("SELECT e_id FROM tbl_employee WHERE e_id = ?", eid) != 0) {
                    break; 
                }
                System.out.println("Selected Employee ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid ID! Please enter a valid Employee ID.");
                sc.nextLine(); 
            }
        }

        
        System.out.print("Table Capacity: ");
        String cap = sc.next();
        System.out.print("Location: ");
        String loc = sc.next();
        System.out.print("Table Status: ");
        String stat = sc.next();

        
        String qry = "INSERT INTO tbl_tables (e_id, t_capacity, t_location, t_status)"
                + "VALUES (?, ?, ?, ?)";
        conf.addRecord(qry, eid, cap, loc, stat);
    }

    
    public void viewTables() {
        String qry = "SELECT tbl_tables.t_id, tbl_tables.e_id, tbl_tables.t_capacity, tbl_tables.t_location, tbl_tables.t_status "
                + "FROM tbl_tables "
                + "LEFT JOIN tbl_employee ON tbl_employee.e_id = tbl_tables.e_id";

        String[] hrds = {"T_ID", "E_ID", "Table Capacity", "Location", "Status"};
        String[] clms = {"t_id", "e_id", "t_capacity", "t_location", "t_status"};

        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }

    
    private void updateTables() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;
        
        
        while (true) {
            System.out.print("Enter the Table ID to update: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Table ID: ");
                sc.next(); 
            }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT t_id FROM tbl_tables WHERE t_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected Table ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid ID! Please enter a valid Table ID.");
                sc.nextLine(); 
            }
        }

        
        System.out.println("New Table Capacity: ");
        String ncap = sc.next();
        System.out.println("New Location: ");
        String nloc = sc.next();
        System.out.println("New Status: ");
        String nstat = sc.next();

        
        String qry = "UPDATE tbl_tables SET t_capacity = ?, t_location = ?, t_status = ? WHERE t_id = ?";
        conf.updateRecord(qry, ncap, nloc, nstat, id);
    }

    
    private void deleteTables() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        
        while (true) {
            System.out.print("Enter the Table ID to delete: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Table ID: ");
                sc.next(); 
            }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT t_id FROM tbl_tables WHERE t_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected Table ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid ID! Please enter a valid Table ID.");
                sc.nextLine(); 
            }
        }

       
        String qry = "DELETE FROM tbl_tables WHERE t_id = ?";
        conf.deleteRecord(qry, id);
    }
}
