package GenerateData;

import DataTypes.ReportType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class InsertsRows {
    public static Logger logger = LoggerFactory.getLogger(InsertsRows.class);
    private static HashMap<String, ArrayList<String>> insertsMap;
    private static ArrayList<String> insertTable;
    private static File templateFile;

    private static void readInsertFromFile(){
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
            e.printStackTrace();
        }

    }

    public static HashMap<String, ArrayList<String>> forReport(ReportType reportType){
        logger.info("Select report " + reportType);
        switch (reportType){
            case REPORT_DEBT_NPF_MSK:
                templateFile = Paths.get("templates/report_MSK.tmp").toFile();
                break;
            case REPORT_DEBT_NPF_SUD:
                templateFile = Paths.get("templates/report_SUD.tmp").toFile();
                break;
            default:
                templateFile = Paths.get("templates/report.tmp").toFile();
                break;
        }

        readInsertFromFile();


        return insertsMap;
    }
}
