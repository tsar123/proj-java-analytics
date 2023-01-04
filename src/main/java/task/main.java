package task;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class main {
    public static void main(String args[]) throws IOException {
        boolean fullDatabase = false //если таблица заполнена данными, то fullDatabase = false
        try {
            var taskRun = new taskRun();
            var dataLoad = databaseLoad.getInstance();
            if(fullDatabase == true){
                ArrayList<dataCountryIndicators> data = parser.getParse();
                dataLoad.database(data);
            }
            taskRun.createBarChart(dataLoad);
            taskRun.printHighEconomy(dataLoad);
            taskRun.printIndicators(dataLoad);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
