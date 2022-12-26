import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class main {
    public static void main(String args[]) throws IOException {
        boolean fullDatabase = false;
        try {
            var dataL = databaseLoad.getInstance();

            if(fullDatabase){
                ArrayList<dataCountryIndicators> data = parser.getParse();
                dataL.database(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
