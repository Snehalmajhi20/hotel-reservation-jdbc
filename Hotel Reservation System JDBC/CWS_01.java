import java.sql.*;

public class CWS_01 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/emp";
        String username = "";
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Class loaded to driver successfully....");

            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection establish successfully into DataBase");

        }
        catch (ClassNotFoundException e){
            System.out.println(""+e);
        }
        catch (SQLException e){
            System.out.println(e);
        }

    }
}
