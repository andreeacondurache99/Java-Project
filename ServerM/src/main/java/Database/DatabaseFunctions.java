package Database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DatabaseFunctions {
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {

        String email = "admin@admin.com";
        String password = "admin";
        String firstName = "admin";
        String lastName = "admin";

        DatabaseFunctions.randomPriorityQueueGenerator();

//        Connection con = Database.Database.getConnection();
//        try{PreparedStatement pstmt = con.prepareStatement("update students set chosen = ? where email = ?");
//            pstmt.setString(1, "");
//            pstmt.setString(2, email);
//            pstmt.executeUpdate();
//            Database.Database.getConnection().commit();
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//
//        if(!Database.DatabaseFunctions.alreadyExistingUser(email))
//        {Database.DatabaseFunctions.addToDatabase(email, password, firstName, lastName);
//            System.out.println("We have a new player");
//        }
//        else {
//            System.out.println("We already have this guy");
//        }

    }

    public static void randomPriorityQueueGenerator() throws SQLException{
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select id from students where id!=1");

        while(rs.next()){
            int id = rs.getInt(1);
            System.out.println("Id-ul userului: " + id);
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("select tobechosen from students where id='" + id+"'");
            if(rs2.next()){
                String tobechosen = rs2.getString(1);
                if(!tobechosen.isEmpty()){

                    List<String> notChosen = new ArrayList<>();
                    String[] allIds= tobechosen.split(" ");

                    for(int i=0;i<allIds.length;i++){
                        notChosen.add(allIds[i]);
                    }

                    Random r1 = new Random();
                    for (int i = notChosen.size() - 1; i >= 1; i--) {
                        Collections.swap(notChosen, i, r1.nextInt(i + 1));
                    }

                    Statement stmt3 = con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("select chosen from students where id='" + id+"'");
                    String chosen ="";
                    if(rs3.next())
                    chosen = rs3.getString(1);

                    System.out.println(notChosen.toString());
                    StringBuilder remainingElements = new StringBuilder();

                    if(!chosen.isEmpty()) {
                    remainingElements.append(chosen);
                    remainingElements.append(" ");
                    }


                    for(int i=0;i<notChosen.size();i++){
                        remainingElements.append(notChosen.get(i));
                        remainingElements.append(" ");
                    }
                    remainingElements.deleteCharAt(remainingElements.length() - 1);

                    Statement stmt4 = con.createStatement();
                    ResultSet rs4 = stmt4.executeQuery("select email from students where id='" + id+"'");
                    String email = "";
                    if(rs4.next())
                    email = rs4.getString(1);

                    DatabaseFunctions.changeToBeChosen("", email);
                    DatabaseFunctions.changeChosen(remainingElements.toString(), email);
                }
            }
        }
    }

    protected static byte[] hashing(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        return encodedhash;
    }
    protected static String bytesToHex(byte[] hash) {
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

    public static boolean alreadyExistingUser(String email) throws SQLException {
        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id from students where email='" + email+"'");
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String giveUsername(int id) throws SQLException {

        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select firstname, lastname from students where id='" + id+"'");
            if(rs.next()){
                String name1 = rs.getString(1);
                String name2 = rs.getString(2);
                StringBuilder sb = new StringBuilder();
                sb.append(name1);
                sb.append(" ");
                sb.append(name2);
                return sb.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean verifyCredentials(String email, String rawPassword) throws NoSuchAlgorithmException, SQLException {
        String password = DatabaseFunctions.bytesToHex(DatabaseFunctions.hashing(rawPassword));
        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select password from students where email='" + email+"'");
            if(rs.next()){
                String dataBasePass = rs.getString(1);
                return dataBasePass.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addToDatabase(String email, String rawPassword, String firstName, String lastName)throws SQLException, NoSuchAlgorithmException {
        Connection con = Database.getConnection();
        try{
            PreparedStatement pstmt = con.prepareStatement("insert into students (email, password, firstname, lastname, tobechosen, chosen, roommate)" + "values(?, ?, ?, ?, ?, ?, ?)");
            String password = bytesToHex(DatabaseFunctions.hashing(rawPassword));
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, "");
            pstmt.setString(6, "");
            pstmt.setInt(7, 0);
            pstmt.executeUpdate();
            Database.getConnection().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void addToDatabase(String email, String rawPassword)throws SQLException, NoSuchAlgorithmException {
        Connection con = Database.getConnection();
        try{
            PreparedStatement pstmt = con.prepareStatement("insert into students (email, password)" + "values(?, ?)");
            String password = bytesToHex(DatabaseFunctions.hashing(rawPassword));
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
        try{PreparedStatement pstmt = con.prepareStatement("update students set firstName = ? where email = ?");
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
        try{PreparedStatement pstmt = con.prepareStatement("update students set lastName = ? where email = ?");
            pstmt.setString(1, lastName);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            Database.getConnection().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void changePassword(String email, String newRawPassword) throws NoSuchAlgorithmException, SQLException {
        String newPassword = bytesToHex(DatabaseFunctions.hashing(newRawPassword));
        Connection con = Database.getConnection();
        try{PreparedStatement pstmt = con.prepareStatement("update students set password = ? where email = ?");
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            Database.getConnection().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String getFirstName(String email) throws SQLException {
        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select firstname from students where email='" + email+"'");
            if(rs.next()){
                String firstName = rs.getString(1);
                return firstName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getId(String email) throws SQLException {
        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id from students where email='" + email+"'");
            if(rs.next()){
                int id = rs.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getLastName(String email) throws SQLException {
        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select lastname from students where email='" + email+"'");
            if(rs.next()){
                String lastName = rs.getString(1);
                return lastName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void constructUnchosenLists() throws SQLException {
        Connection con = Database.getConnection();
        try{int count = 0;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select count(id) from students");
            List<String> ids = new ArrayList<>();
            if(rs.next()){
                count = rs.getInt(1);
                System.out.println("Count is: " +count);
                ResultSet rs2 = stmt.executeQuery("select id from students");
                while(rs2.next()){
                    int id = rs2.getInt(1);
                    if(id!=1){

                    ids.add(String.valueOf(id));
                    }
                }
            }
            if(!ids.isEmpty()){
                addToAllStudents(ids, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private static void addToAllStudents(List<String> ids, int count) throws SQLException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select id from students");

        while(rs.next()){
            int id = rs.getInt(1);
            String myIds = DatabaseFunctions.makeListToString(ids, id);
            if(id!=1){
                PreparedStatement pstmt = con.prepareStatement("update students set tobechosen = ? where id = ?");
                pstmt.setString(1, myIds);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                Database.getConnection().commit();

                PreparedStatement pstmt2 = con.prepareStatement("update students set chosen = ? where id = ?");
                pstmt2.setString(1, "");
                pstmt2.setInt(2, id);
                pstmt2.executeUpdate();
                Database.getConnection().commit();

                PreparedStatement pstmt3 = con.prepareStatement("update students set roommate = ? where id = ?");
                pstmt3.setInt(1, 0);
                pstmt3.setInt(2, id);
                pstmt3.executeUpdate();
                Database.getConnection().commit();
            }
        }
    }

    private static String makeListToString(List<String> allIds, int currentId){
        String currId=String.valueOf(currentId);
        List<String> ids = new ArrayList<>(allIds);
        ids.remove(currId);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if(i==ids.size()-1)
            sb.append(ids.get(i));
            else
            {
                sb.append(ids.get(i));
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String giveToChose(String email) throws SQLException {
        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select tobechosen from students where email='" + email+"'");
            if(rs.next()){
                String tobechosen = rs.getString(1);
                return tobechosen;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String giveChosen(String email) throws SQLException {
        Connection con = Database.getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select chosen from students where email='" + email+"'");
            if(rs.next()){
                String chosen = rs.getString(1);
                return chosen;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean giveMeToChose(String email) throws SQLException {
        Connection con = Database.getConnection();
        boolean succes = false;
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select chosen, tobechosen from students where email='" + email+"'");
            if(rs.next()){
                String chosen = rs.getString(1);
                String tobechosen = rs.getString(2);
                if(!(chosen.isEmpty()&&tobechosen.isEmpty())){
                    succes = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return succes;
    }


    public static void changeToBeChosen(String list, String email) throws SQLException {
        Connection con = Database.getConnection();
        try{PreparedStatement pstmt = con.prepareStatement("update students set tobechosen = ? where email = ?");
            pstmt.setString(1, list);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            Database.getConnection().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void changeChosen(String list, String email) throws SQLException {
        Connection con = Database.getConnection();
        try{PreparedStatement pstmt = con.prepareStatement("update students set chosen = ? where email = ?");
            pstmt.setString(1, list);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            Database.getConnection().commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
