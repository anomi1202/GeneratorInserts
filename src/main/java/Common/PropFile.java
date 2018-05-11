package Common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropFile {
    private static Logger logger = LoggerFactory.getLogger(PropFile.class);
    private Properties propFile = new Properties();

    public String getParam(String key){
        String res = null;
        try (FileReader propFileReader = new FileReader("param.ini")) {
            propFile.load(propFileReader);
            res = propFile.getProperty(key);
            if (res == null){
                logger.error("Parametr key '" + key + "' is not exist");
            }
        } catch (IOException e){
            logger.error("FAILED! ", e);
        }

        return res;
    }
}
