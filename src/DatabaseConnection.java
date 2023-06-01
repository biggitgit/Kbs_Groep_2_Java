import java.sql.*;

public class DatabaseConnection {
    // Existing code for fetching data from the "cities" table

    public static ResultSet executeQuery(String query) {
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
                statement = conn.createStatement();
                rs = statement.executeQuery(query);
            } else {
                System.out.println("Connection fout");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " Error 2");
        }
        if (rs == null) {
            System.out.println("ResultSet is null");
        }
        return rs;
    }

    public static ResultSet getCities() {
        String query = "SELECT * FROM cities;";
        return executeQuery(query);
    }

    public static ResultSet getOrders() {
        String query = "SELECT * FROM orders;";
        return executeQuery(query);
    }

    public static ResultSet getStockItemsHolding() {
        String query = "SELECT * FROM stockitemsholding;";
        return executeQuery(query);
    }
    public static ResultSet getOrdersLines() {
        String query = "SELECT * FROM orderslines;";
        return executeQuery(query);
    }
    public static void updateStockItemsHolding(int StockID) {
        String query = "UPDATE stockitemsholding SET QuantityOnHand = QuantityOnHand + 1 WHERE StockItemID = " + StockID + ";";
         executeUpdate(query);
    }
    public static void executeUpdate(String query) {
        Connection conn;
        Statement statement;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Failed to load database driver.");
            ex.printStackTrace();
            return;
        }

        String url = System.getenv("S_URL");
        String username = System.getenv("USERNAME");
        String password = System.getenv("PASS_ds");

        try {
            conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
                statement = conn.createStatement();
                statement.executeUpdate(query);
            } else {
                System.out.println("Connection error");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}