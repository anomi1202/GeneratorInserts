package GenerateData;

import DataTypes.ReportType;
import DataTypes.TableName;

import java.io.*;
import java.nio.file.Paths;

import static DataTypes.ShortTableName.*;

public class DeletesRows {
    private static String deletes;
    private static File templateFile;

    private static void readDeletesFromFile(){
        StringBuilder builder = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new FileReader(templateFile))) {
            while (reader.ready()) {
                String lineText = reader.readLine();
                if (lineText.contains("delete from")) {
                    builder.append(lineText)
                            .append("\r\n");
                }
            }

            builder.append("commit;")
                    .append("\n\n\n\n");
            deletes = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String forReport(ReportType reportType) {
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

        readDeletesFromFile();
        return deletes;
    }
}
