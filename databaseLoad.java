import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class databaseLoad {
    private static final String CON_STR = "proj:country.s3db";
    private static databaseLoad instance = null;
    private Connection connection;

    public static synchronized databaseLoad getInstance() throws SQLException {
        if (instance == null)
            instance = new databaseLoad();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private databaseLoad() throws SQLException {
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public List<dataCountryIndicators> getAllCountry() {
        try (Statement stat = this.connection.createStatement()) {
            List<dataCountryIndicators> products = new ArrayList<>();
            ResultSet result = stat.executeQuery("SELECT * FROM CountryIndicators");
            while (result.next()) {
                products.add(new dataCountryIndicators(result.getString("country"), result.getString("region"), result.getInt("happyRank"), result.getFloat("happyScore"), result.getFloat("standardError"), result.getFloat("economy"), result.getFloat("family"), result.getFloat("health"), result.getFloat("freedom"), result.getFloat("trust"), result.getFloat("generosity"), result.getFloat("dystopiaResidual")));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void createTableCountry() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS CountryIndicators (\n"
                + "	countryName NVARCHAR(30) PRIMARY KEY,\n"
                + "	region NVARCHAR(30) NOT NULL,\n"
                + "	happinessRank INT NOT NULL,\n"
                + "	happinessScore FLOAT NOT NULL,\n"
                + "	standardError FLOAT NOT NULL,\n"
                + "	economy FLOAT NOT NULL,\n"
                + "	family FLOAT NOT NULL,\n"
                + "	health FLOAT NOT NULL,\n"
                + "	freedom FLOAT NOT NULL,\n"
                + "	trust FLOAT NOT NULL,\n"
                + "	generosity FLOAT NOT NULL,\n"
                + "	dystopiaResidual FLOAT NOT NULL\n);";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addCountry(dataCountryIndicators countryInd) {

        try (PreparedStatement stat = this.connection.prepareStatement("INSERT INTO country(`country`, `region`, `happyRank`, `happyScore`, `standartError`, `economy`, `family`, `health`, `freedom`,`trust`, `generosity`, `dystopiaResidual`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stat.setObject(1, countryInd.country);
            stat.setObject(2, countryInd.region);
            stat.setObject(3, countryInd.happyRank);
            stat.setObject(4, countryInd.happyScore);
            stat.setObject(5, countryInd.standartError);
            stat.setObject(6, countryInd.economy);
            stat.setObject(7, countryInd.family);
            stat.setObject(8, countryInd.health);
            stat.setObject(9, countryInd.freedom);
            stat.setObject(10, countryInd.trust);
            stat.setObject(11, countryInd.generosity);
            stat.setObject(12, countryInd.dystopiaResidual);
            stat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void database(List<dataCountryIndicators> countryIndicatorsList) {
        try {
            databaseLoad dataL = databaseLoad.getInstance();
            countryIndicatorsList.stream().forEach(count -> dataL.addCountry(count));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
