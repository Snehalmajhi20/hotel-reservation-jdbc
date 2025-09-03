import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Hotel_Reservation_System {
    private static String url = "jdbc:mysql://localhost:3306/hotel_reservation_db";   // as table and port u can change.
    private static String username = "";  // enter username as in mysql or database!
    private static String password = "";  // enter your password which you given on MYSQL server

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class loaded successfully into Driver.....");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{
            Connection con = DriverManager.getConnection(url,username,password);
            System.out.println("Connection successfully into the DataBase....");

            while(true){
                System.out.println("*-------------------------*");
                System.out.println("| HOTEL MANAGEMENT SYSTEM |");
                System.out.println("*-------------------------*");

                Scanner sc = new Scanner(System.in);
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Upadte Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. exit");

                System.out.println("---------------------------");
                System.out.println("Enter Your Option............... ");

                int choice = sc.nextInt();
                switch (choice){
                    case 1:
                        reserveRoom(con, sc);
                        break;
                    case 2:
                        veiwReservation(con);
                        break;
                    case 3:
                        getRoomNumber(con, sc);
                        break;
                    case 4:
                        updateReservation(con, sc);
                        break;
                    case 5:
                        deleteReservation(con, sc);
                        break;
                    case 0 :
                        exit(con, sc);
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid Choice, please try again.....");
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void reserveRoom(Connection con, Scanner sc) {

        try {
            sc.nextLine();

            System.out.print("Enter the Guest Name: ");
            String guestName = sc.nextLine();

            System.out.print("Enter Your Contact Number: ");
            String contactNumber = sc.next();

            System.out.print("Enter the Room Number: ");
            int roomNumber = sc.nextInt();

            String sql_Query = "INSERT INTO reservation (guest_name, contact_number, room_number) " +
                    "VALUES ('" + guestName + "', '" + contactNumber + "', '" + roomNumber + "')";

            Statement st = con.createStatement();
            int affectedRows = st.executeUpdate(sql_Query);

            if (affectedRows > 0) {
                System.out.println("Reservation Successfully Added!");
            } else {
                System.out.println("Reservation Failed, Please try again....");
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void veiwReservation(Connection con){

        try{
            String sql_query = "SELECT * FROM reservation";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql_query);

            System.out.println("\n---- Reservations ----");

            while(rs.next()){
                int id = rs.getInt("guest_id");
                String Guest_name = rs.getString("guest_name");
                String contact_details = rs.getString("contact_number");
                int roomnumber = rs.getInt("room_number");

                System.out.println("Id: "+id+ " Guest Name: "+ Guest_name+ " Contact Number: "+ contact_details+ " Room Number: "+roomnumber);
            }
        }
        catch (SQLException e){
            System.out.println("SQl Error "+ e.getMessage());
        }
    }

    private static void getRoomNumber(Connection con, Scanner sc){
        try{
            System.out.println("Enter the Guest ID : ");
            int guest_ID = sc.nextInt();

            String sql_query = "SELECT guest_name, room_number from reservation WHERE guest_id ="+guest_ID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql_query);

            if(rs.next()){
                int roomnumber = rs.getInt("room_number");
                System.out.print("Room Number for reservation ID is: "+roomnumber);
            }
            else {
                System.out.print("Reservation not found for given Guest ID"+guest_ID);
            }

        }
        catch (SQLException e){
            System.out.println("SQl Error! "+e.getMessage());
        }
    }
    private static void updateReservation(Connection con, Scanner sc){
        try{
            System.out.print("Enter the Reservation ID: ");
            int Reservation_ID = sc.nextInt();

            String checkQuery = "SELECT * FROM reservation WHERE guest_id=" + Reservation_ID;
            Statement checkSt = con.createStatement();
            ResultSet rs = checkSt.executeQuery(checkQuery);
            if (!rs.next()) {
                System.out.println("Reservation ID " + Reservation_ID + " not found. Update cancelled!");
                return;
            } else {
                System.out.println("Reservation ID found! Proceeding with update...");
            }

            sc.nextLine();
            System.out.print("Enter the Guest Name to Update: ");
            String Guest_name = sc.nextLine();
            System.out.print("Enter the Contact Number to Update: ");
            String Contact_number = sc.nextLine();
            System.out.print("Enter the Room number to Update: ");
            int Room_number = sc.nextInt();

            String sql_query = "UPDATE reservation SET " +
                    "guest_name='" + Guest_name + "', " +
                    "contact_number='" + Contact_number + "', " +
                    "room_number=" + Room_number + " " +
                    "WHERE guest_id=" + Reservation_ID;
            Statement st = con.createStatement();
            int rowsaffected = st.executeUpdate(sql_query);
            if (rowsaffected>0){
                System.out.println("Reservation Updated successfully!");
            }
            else {
                System.out.println("Reservation updated failed..... please try again!");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private static void deleteReservation(Connection con, Scanner sc){
        try{
            sc.nextLine();
            System.out.print("Enter Guest ID to delete: ");
            int Guest_id = sc.nextInt();

            String sql_query ="DELETE FROM reservation WHERE guest_id= "+Guest_id;

            Statement st = con.createStatement();
            int rowsaffected = st.executeUpdate(sql_query);

            if(rowsaffected>0){
                System.out.println("Reservation Delete Successfully!");
            }
            else {
                System.out.println("Reservation Not Found!");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private static void exit(Connection con, Scanner sc){
        try{
            sc.close();
            con.close();
            System.out.println("Exiting System....");
            int i = 5;
            while(i!=0){
                System.out.print(".");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            System.out.println("Thank you for using Hotel Management System");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
