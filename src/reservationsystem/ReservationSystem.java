package reservationsystem;

import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ReservationSystem {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/reservation";
        System.out.print("Enter you user name : ");
        String userName = sc.next();
        sc.nextLine();
        System.out.print("Enter your password : ");
        String password = sc.next();
        sc.nextLine();

        // loading driver
        Class.forName("com.mysql.jdbc.Driver");
        try {
            Connection con = DriverManager.getConnection(url, userName, password);
            Statement st = con.createStatement();

            while (true) {
                System.out.println("            GUEST HOUSE RESERVATION            ");
                System.out.println("===============================================");
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("6. Exit");
                System.out.print("Choose an option : ");
                int choice = sc.nextInt();
                sc.nextLine();

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
