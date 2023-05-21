import java.sql.*;

public class DatabaseConnection {
    public static ResultSet DatabaseConn() {
        ResultSet rs = null;
        Connection conn;
        Statement statement;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Failed to load database driver.");
            ex.printStackTrace();
            return null;
        }

        String url = System.getenv("S_URL");
        String username = System.getenv("USERNAME");
        String password = System.getenv("PASS_ds");

        try {
            conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
                System.out.println("Verbinding gelukt");

                 statement = conn.createStatement();

                rs = statement.executeQuery("SELECT * FROM stad;");
            } else {
                System.out.println("Geen verbinding gelukt");
            }
        } catch (
                SQLException e) {
            System.err.println(e.getMessage() + "fout 2");
        }
        if (rs == null){
            System.out.println("rs is null");
        }
        return rs;
    }
    public static String getStadNaam(int stadId) {
        String stadNaam = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Failed to load database driver.");
            ex.printStackTrace();
            return null;
        }

        String url = "jdbc:mysql://localhost/NerdyGadgets_java";
        String username = "root";
        String password = "";

        try {
            conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
                String query = "SELECT stad_naam FROM stad WHERE stad_id = ?";
                statement = conn.prepareStatement(query);
                statement.setInt(1, stadId);
                rs = statement.executeQuery();

                if (rs.next()) {
                    stadNaam = rs.getString("stad_naam");
                }
                System.out.println("Naam gevonden: " + stadNaam);
            } else {
                System.out.println("Naam niet gevonden");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "fout 2");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return stadNaam;
    }
}