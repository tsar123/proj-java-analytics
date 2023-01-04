package task;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class databaseLoad {
    private static databaseLoad instance = null;
    private final Connection connection;

    public static synchronized databaseLoad getInstance() throws SQLException {
        if (instance == null)
            instance = new databaseLoad();
        return instance;
    }

    public Connection getConnection() { return connection; }

    private databaseLoad() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:D:/Users/tsarg/CountryIndicators/country.s3db");
    }

    public List<dataCountryIndicators> getAllCountry() {
        try (Statement stat = this.connection.createStatement()) {
            List<dataCountryIndicators> products = new ArrayList<>();
            ResultSet result = stat.executeQuery("SELECT * FROM country");
            while (result.next()) {
                products.add(new dataCountryIndicators(result.getString("country"), result.getString("region"), result.getInt("happyRank"), result.getFloat("happyScore"), result.getFloat("standartError"), result.getFloat("economy"), result.getFloat("family"), result.getFloat("health"), result.getFloat("freedom"), result.getFloat("trust"), result.getFloat("generosity"), result.getFloat("dystopiaResidual")));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void addCountry(dataCountryIndicators countryId) {

        try (
                PreparedStatement stat = this.connection.prepareStatement("INSERT INTO 'country' (`country`, `region`, `happyRank`, `happyScore`, `standartError`, `economy`, `family`, `health`, `freedom`,`trust`, `generosity`, `dystopiaResidual`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stat.setObject(1, countryId.country);
            stat.setObject(2, countryId.region);
            stat.setObject(3, countryId.happyRank);
            stat.setObject(4, countryId.happyScore);
            stat.setObject(5, countryId.standartError);
            stat.setObject(6, countryId.economy);
            stat.setObject(7, countryId.family);
            stat.setObject(8, countryId.health);
            stat.setObject(9, countryId.freedom);
            stat.setObject(10, countryId.trust);
            stat.setObject(11, countryId.generosity);
            stat.setObject(12, countryId.dystopiaResidual);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void database(List<dataCountryIndicators> data) {
        try {
            databaseLoad dataL = databaseLoad.getInstance();
            data.forEach(dataL::addCountry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
