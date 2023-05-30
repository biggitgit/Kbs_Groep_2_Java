import java.sql.ResultSet;

public class Stad {
    ResultSet rs = DatabaseConnection.DatabaseConn();
    private String naam;
    private double longitude,latitude;

    public Stad(String naam, double latitude, double longitude) {
        this.naam = naam;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getNaam() {
        return naam;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
