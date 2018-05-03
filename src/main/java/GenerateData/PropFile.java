package GenerateData;


import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropFile {
    private Properties propFile = new Properties();

    public String getParam(String key) throws IOException {
        propFile.load(new FileReader("param.ini"));
        if (propFile.containsKey(key)) {
            return propFile.getProperty(key);
        } else {
            return null;
        }
    }
}
