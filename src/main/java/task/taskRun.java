package task;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class taskRun {
    public void printHighEconomy(databaseLoad dataL) {
        String sql = "SELECT country FROM country WHERE economy = (SELECT MAX(economy) FROM country WHERE region = 'Latin America and Caribbean' OR region = 'Eastern Asia')";
        try (Statement stmt = dataL.getConnection().createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("Страна с самым высоким показателем экономики среди \"Latin America and Caribbean\" и \"Eastern Asia\": " + resultSet.getString("country"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createViewsForTask(databaseLoad dataLoad) {
        String sqlFirst = "CREATE VIEW IF NOT EXISTS res\n" +
                "AS  \n" +
                "SELECT country, abs((SELECT AVG(happyScore) FROM country)-happyScore)/(SELECT AVG(happyScore) FROM country) as '1',\n" +
                "abs((SELECT AVG(standartError) FROM country)-standartError)/(SELECT AVG(standartError) FROM country) as '2',\n" +
                "abs((SELECT AVG(economy) FROM country)-economy)/(SELECT AVG(economy) FROM country) as '3',\n" +
                "abs((SELECT AVG(family) FROM country)-family)/(SELECT AVG(family) FROM country) as '4',\n" +
                "abs((SELECT AVG(health) FROM country)-health)/(SELECT AVG(health) FROM country) as '5',\n" +
                "abs((SELECT AVG(freedom) FROM country)-freedom)/(SELECT AVG(freedom) FROM country) as '6',\n" +
                "abs((SELECT AVG(trust) FROM country)-trust)/(SELECT AVG(trust) FROM country) as '7',\n" +
                "abs((SELECT AVG(generosity) FROM country)-generosity)/(SELECT AVG(generosity) FROM country) as '8',\n" +
                "abs((SELECT AVG(dystopiaResidual) FROM country)-dystopiaResidual)/(SELECT AVG(dystopiaResidual) FROM country) as '9'\n" +
                "FROM country\n" +
                "WHERE country IN (SELECT country FROM country \n" +
                "WHERE region = 'Western Europe' OR region = 'North America')\n";

        String sqlDouble = "CREATE VIEW IF NOT EXISTS resultTable AS SELECT country,res.[1]+res.[2]+res.[3]+res.[4]+res.[5]+res.[6]+res.[7]+res.[8]+res.[9] AS 'sum' FROM res\n";

        try (Statement stat = dataLoad.getConnection().createStatement()) {
            stat.execute(sqlFirst);
            stat.execute(sqlDouble);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createBarChart(databaseLoad dataL) {
        var dataset = new DefaultCategoryDataset();
        var set = dataL.getAllCountry().stream()
                .sorted(Comparator.comparing(dataCountryIndicators::getEconomy))
                .collect(Collectors.toCollection(ArrayList::new));

        set.forEach(country -> dataset.addValue(country.economy, country.country, country.country));
        JFreeChart chart = ChartFactory.createBarChart("Экономические показатели стран","Страна","Экономический показатель",
                dataset, PlotOrientation.HORIZONTAL,
                false,false,false);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer br = (BarRenderer) plot.getRenderer();
        br.setItemMargin(0);

        JFrame frame = new JFrame("Задание 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel panel = new ChartPanel(chart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1500, 800);
            }
        };
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void printIndicators(databaseLoad dataLoad) throws SQLException {
        createViewsForTask(dataLoad);
        String sql = "SELECT country FROM resultTable ORDER BY sum LIMIT 1 OFFSET 11";
        try (Statement stat = dataLoad.getConnection().createStatement()) {
            ResultSet result = stat.executeQuery(sql);
            System.out.println("Страна с самыми средними показателями экономики среди \"Western Europe\" и \"North America\": " + result.getString("country"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
