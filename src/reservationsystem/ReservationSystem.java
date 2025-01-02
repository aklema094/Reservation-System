package reservationsystem;

import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class ReservationSystem {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/reservation";
        String userName = "root";
        String password = "29344";
        

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
                System.out.println("");
                System.out.print("Choose an option : ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        reserveRoom(st, sc);
                        System.out.println("");
                        break;
                    case 2:
                        viewReservation(st);
                        System.out.println("");
                        break;
                    case 3:
                        System.out.print("Enter Id : ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name : ");
                        String name = sc.nextLine();
                        GetRoom(st, sc, id, name);
                        System.out.println("");
                        break;
                    case 4 :
                        updateReservation(st,sc);
                        System.out.println("");
                        break;
                    case 5:
                        System.out.print("Enter Id : ");
                        int pId = sc.nextInt();
                        deleteReservation(st, pId);
                        System.out.println("");
                        break;
                    case 6:
                        exitSystem();
                        return;
                    default:
                        System.out.println("Invalid Choice!!!, Try again.");
                        break;
                }
            }
           
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      sc.close();
      System.exit(0);
      
    }

    // room reserve
    public static void reserveRoom(Statement st, Scanner sc) throws SQLException {

        System.out.print("Enter your name : ");
        String name = sc.nextLine();
        System.out.print("Enter your room number : ");
        int room = sc.nextInt();
        if (availabelRoom(room, st)) {
            System.out.print("Enter your Contact Number : ");
            int contact = sc.nextInt();
            String query = "INSERT INTO reservationn(name,roomNo,contactNo) VALUES('" + name + "','" + room + "','" + contact + "');";
            int affectedRows = st.executeUpdate(query);
            if (affectedRows > 0) {
                System.out.println("Data added successfully");
            } else {
                System.out.println("Failed to add data");
            }
        } else {
            System.out.println("Room is not Available!! Try with new room");
        }

    }

    public static boolean availabelRoom(int room, Statement st) throws SQLException {

        ResultSet rs = st.executeQuery("SELECT * FROM reservationn WHERE roomNo = '" + room + "';");
        if (rs.next()) {
            return false;
        }
        return true;
    }

    // view reservations
    public static void viewReservation(Statement st) {

        try {
            ResultSet rs = st.executeQuery("select * from reservationn;");
            System.out.println("+-----+---------------------+----------+----------------+-------------------------+");
            System.out.println("|  Id |        Name         |  Room No |    Contact No  |          Date           |");
            System.out.println("+-----+---------------------+----------+----------------+-------------------------+");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int room = rs.getInt("roomNo");
                int contact = rs.getInt("contactNo");
                String date = rs.getTimestamp("date").toString();
                System.out.printf("| %-4s| %-20s| %-9s| %-15s| %-24s|\n", id, name, room, contact, date);
            }
            System.out.println("+-----+---------------------+----------+----------------+-------------------------+");
        } catch (SQLException e) {

        }

    }

    // get room number 
    public static void GetRoom(Statement st, Scanner sc, int id, String name) {

        try {
            ResultSet rs = st.executeQuery("SELECT * FROM reservationn WHERE id = '" + id + "' AND name = '" + name + "' ;");
            if (rs.next()) {
                int room = rs.getInt("roomNo");
                System.out.println("Room number is : " + room);
            } else {
                System.out.println("");
                System.out.println("Invalid information!!!, Try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // update reservation
    public static void updateReservation(Statement st, Scanner sc) throws SQLException {
        System.out.print("Enter Id : ");
        int id = sc.nextInt();
        sc.nextLine();
        if(isValidId(st,id)){
            System.out.print("Enter new Name : ");
            String name = sc.nextLine();
            System.out.print("Enter new room no : ");
            int room = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new Contact : ");
            int con = sc.nextInt();
            int r = st.executeUpdate("UPDATE reservationn SET name = '"+name+"',roomNo = '"+room+"', contactNo = '"+con+"' WHERE id ='"+id+"';");
            if(r>0){
                System.out.println("Updated Successfully");
            }else{
                System.out.println("Failed to update data!!! Please Try again.");
            }
        }else{
            System.out.println("Enter Valid ID.");
        }
    }
    
    public static boolean isValidId(Statement st, int id) throws SQLException{
        ResultSet rs = st.executeQuery("SELECT * FROM reservationn WHERE id = '"+id+"';");
        if(rs.next()){
            return true;
        }
        return false;
    }

    // delete reservation 
    public static void deleteReservation(Statement st, int id) throws SQLException {

        int r = st.executeUpdate("DELETE FROM reservationn WHERE id = '" + id + "';");

        if (r > 0) {
            System.out.println("Record deleted successfully");
        } else {
            System.out.println("Failed to delete data");
        }

    }
    // exit from system
    public static void exitSystem() throws InterruptedException{
        System.out.print("Existing System");
        for (int i = 0; i < 7; i++) {
            System.out.print(". ");
            Thread.sleep(300);   
        }
        System.out.println("\nTHANK YOU FOR USING GUEST HOUSE");
    }

}
