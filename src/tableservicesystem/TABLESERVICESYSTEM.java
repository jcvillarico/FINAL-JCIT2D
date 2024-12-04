package tableservicesystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TABLESERVICESYSTEM {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);    
        boolean exit = true;

        do {
            System.out.println("");
            System.out.println("-----------------------------------------");
            System.out.println("|*** WELCOME TO TABLE SERVICE SYSTEM ***|");
            System.out.println("-----------------------------------------");
            System.out.println("");
            System.out.println("1. CUSTOMER");
            System.out.println("2. TABLE");
            System.out.println("3. EMPLOYEE");
            System.out.println("4. ORDER");
            System.out.println("5. REPORT MENU");
            System.out.println("6. EXIT");

            int act = -1; 

            while (true) {
                System.out.print("Enter Action: ");
                try {
                act = sc.nextInt();
                break; 
                } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 6.");
                sc.next(); 
              }
            }
            switch (act) {
                case 1:
                    Customer cr = new Customer();
                    cr.customerTransaction();
                    break;
                case 2:
                    Table tb = new Table();
                    tb.tableTransaction();
                    break;
                case 3:
                    Employee em = new Employee();
                    em.eTransaction();
                    break;
                case 4:
                    Order or = new Order();
                    or.orderTransaction();
                    break;
                case 5:
                    Report rp = new Report();
                    rp.reportMenu();
                case 6:
                    System.out.print("Are you sure? (yes/no): ");
                    String resp = sc.next();
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = false;
                    }
                    break;
                    
                default:
                System.out.println("Invalid choice! Please select a valid option (1-5).");
            }
        } while (exit);
        System.out.println("Thank you for using our system");
    }
}
