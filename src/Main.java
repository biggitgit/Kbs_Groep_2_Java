import java.sql.*;


public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        String url = "jdbc:mysql://localhost/NerdyGadgets";

        try (Connection conn = DriverManager.getConnection(url,"root", "")) {
            if (conn != null) {
                System.out.println("Verbinding gelukt");

                Statement statement = conn.createStatement();

                ResultSet rs = statement.executeQuery("SELECT * FROM stad;");
            } else {
                System.out.println("Geen verbinding gelukt");
            }
        }   catch (SQLException e){
            System.err.println(e.getMessage() + "fout 2");
        }

        rbAP rbAP1 = new rbAP();
}
}