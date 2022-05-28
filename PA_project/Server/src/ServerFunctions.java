import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class ServerFunctions {

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {

        String email = "cristea3000@yahoo.com";
        String password = "dare";
        if(!ServerFunctions.alreadyExistingUser(email))
        {ServerFunctions.addToDatabase(email, password);
            System.out.println("We have a new player");
        }
        else{
            System.out.println("We already have this guy");
            //GoingDown.updateFirstName("admin", "admin");
            //GoingDown.updateLastName("admin", "admin");
            //changePassword("admin", "admin");
        }
        String email2 = "c1";
        String password2 = "admin";
        if(!ServerFunctions.alreadyExistingUser(email2))
        {ServerFunctions.addToDatabase(email2, password2);
            System.out.println("We have a new player");
        }
        String email3 = "c2";
        String password3 = "admin";
        if(!ServerFunctions.alreadyExistingUser(email3))
        {ServerFunctions.addToDatabase(email3, password3);
            System.out.println("We have a new player");
        }
        String email4 = "c3";
        String password4 = "admin";
        if(!ServerFunctions.alreadyExistingUser(email4))
        {ServerFunctions.addToDatabase(email4, password4);
            System.out.println("We have a new player");
        }

    }

    protected static byte[] hashing(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        return encodedhash;
    }
    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean verifyCredentials(String email, String password) throws SQLException {
        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id from users where email='" + email+"' and password='"+password+"'");
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean alreadyExistingUser(String email) throws SQLException {
        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id from users where email='" + email+"'");
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addToDatabase(String email, String rawPassword)throws SQLException, NoSuchAlgorithmException {
        Connection con = Database.getConnection();
        try{
            PreparedStatement pstmt = con.prepareStatement("insert into users (email, password)" + "values(?, ?)");
            String password = bytesToHex(ServerFunctions.hashing(rawPassword));
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            Database.getConnection().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateFirstName(String email, String firstName) throws SQLException {
        Connection con = Database.getConnection();
        try{PreparedStatement pstmt = con.prepareStatement("update users set firstName = ? where email = ?");
            pstmt.setString(1, firstName);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            Database.getConnection().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateLastName(String email, String lastName) throws SQLException {
        Connection con = Database.getConnection();
        try{PreparedStatement pstmt = con.prepareStatement("update users set lastName = ? where email = ?");
            pstmt.setString(1, lastName);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            Database.getConnection().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void changePassword(String email, String newRawPassword) throws NoSuchAlgorithmException, SQLException {
        String newPassword = bytesToHex(ServerFunctions.hashing(newRawPassword));
        Connection con = Database.getConnection();
        try{PreparedStatement pstmt = con.prepareStatement("update users set password = ? where email = ?");
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            Database.getConnection().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
