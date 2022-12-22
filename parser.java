import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


//Распарсив данные в файле CSV, нужно по ним создать набор объектов, заполнив все необходимые поля.
public class parser {
    public static ArrayList<dataCountryIndicators> getParse() throws IOException{
        ArrayList dataArr = new ArrayList<dataCountryIndicators>();

        try {
            List<String> fileName = Files.readAllLines(Paths.get("dataset.csv"));
            fileName.remove(0);
            fileName.forEach(dataString -> dataArr.add(new dataCountryIndicators(dataString.split(","))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataArr;
    }
}