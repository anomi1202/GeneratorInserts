package Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InsertsRows {
    private static Logger logger = LoggerFactory.getLogger(InsertsRows.class);
    private static HashMap<String, ArrayList<String>> insertsMap;
    private static ArrayList<String> insertTable;
    private static File templateFile;

    private static void readInsertFromFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(templateFile))) {
            insertsMap = new HashMap<>();
            logger.debug("Read template " + templateFile.getName());
            while (reader.ready()) {
                String lineText = reader.readLine();

                if (lineText.toLowerCase().contains("insert into")) {
                    String table = lineText.replace("// insert into ", "");
                    insertTable = new ArrayList<>();

                    lineText = reader.readLine();
                    while (reader.ready()
                            && !lineText.contains("commit;")) {
                        insertTable.add(lineText + "\r\n");
                        logger.debug("Read row from template \"" + lineText + "\"");
                        lineText = reader.readLine();
                    }

                    insertsMap.put(table, insertTable);
                }
            }
        } catch (IOException e) {
            logger.error("FAILED! ", e);
        }

    }

    public static HashMap<String, ArrayList<String>> withTemplate(File template) {
        logger.info("Generate insert with template file " + template);
        templateFile = template;
        readInsertFromFile();
        return insertsMap;
    }
}
