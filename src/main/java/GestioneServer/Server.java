package GestioneServer;
import javassist.bytecode.stackmap.BasicBlock;

import java.sql.*;

/**
 *
 * @author sqlitetutorial.net
 */
public class Server {
    /**
     * Connect to a sample database
     */
    /*
        La funzione IsPresent serve per vedere se un determinato utente è presente sul database.
     */

    // db data
    private static final String url = "jdbc:sqlite:db.db";

    public boolean isPresent(Utente utente) {
        Connection conn = null;
        String sql = "SELECT * FROM Users WHERE username =? AND password = ? AND ROLE =?";
        int rows = 0;

        // create a connection to the database
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Connection to SQLite has been established.");
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM Users WHERE username =? AND password = ? AND role =?");
            pstmt.setString(1, utente.getUsername());
            pstmt.setString(2, utente.getPassword());
            pstmt.setString(3, utente.getRuolo());

            ResultSet result = pstmt.executeQuery();
            result.next();
            rows = result.getRow();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(rows);
        if (rows > 0) return true;
        else return false;
    }
   /*
      La funzione insertUser aggiunge un utente al sistema. Verrà utilizzata all'interno dell'area Admin.
    */
    public void insertUser(String username, String password, String ruolo) {
        Connection conn = null;
        String sql = "SELECT * FROM Users WHERE username =? AND password = ? AND ROLE =?";
        int rows = 0;

        // create a connection to the database
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Connection to SQLite has been established.");
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO Users('Username','Password','Role') VALUES(?,?,?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, ruolo);
            pstmt.execute();
            conn.close();
            System.out.println("Utente Inserito");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void registerPatient(String cf, String nome, String cognome,
                                String trattamento, String diagnosi, String email){
        String updateStr = "INSERT INTO Patients(fiscalCode, name, surname, email, treatment, diagnosis)" +
                "VALUES (?,?,?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(url)){
            PreparedStatement pst = conn.prepareStatement(updateStr);
            pst.setString(1, cf);
            pst.setString(2, nome);
            pst.setString(3, cognome);
            pst.setString(4, email);
            pst.setString(5, trattamento);
            pst.setString(6, diagnosi);
            int result = pst.executeUpdate();
            System.out.println("Numero utenti inseriti: "+ result);
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }
    }
}

