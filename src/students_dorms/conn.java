package students_dorms;
import java.sql.*;
public class conn {
    public static Connection getCon(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_dorm","root","");
            return con;
        }
        catch(Exception e){
            return null;
        }
    }
}
