package Report;

import Common.DeletesRows;
import Common.InsertsRows;
import Common.PropFile;
import Common.ReportType;
import com.beust.jcommander.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportData {
    private Logger logger = LoggerFactory.getLogger(ReportData.class);
    private PropFile propFile = new PropFile();
    private Path outFile;
    private boolean fileIsCleaned = false;
    private int accIdBeg;
    private int accIdEnd;
    private ReportType report;
    private File templateReport;

    public ReportData(ReportType report) {
        this.report= report;
        initAccId();
        createOutFileForReportData();
    }

    private void createOutFileForReportData() {
        try {
            switch (report){
                case REPORT_DEBT_NPF_SUD:
                    outFile = Paths.get(propFile.getParam("outFile_debt_npf_sud"));
                    break;
                case REPORT_DEBT_NPF_MSK:
                    outFile = Paths.get(propFile.getParam("outFile_debt_npf_msk"));
                    break;
                case CONTROL_REPORT:
                    outFile = Paths.get(propFile.getParam("outFile_control_report"));
                    break;
                default:
                    outFile = Paths.get("reports_template.sql");
            }
            if (!outFile.toFile().exists()){
                Files.createFile(outFile);
            } else if (!fileIsCleaned){
                outFile.toFile().delete();
                Files.createFile(outFile);
                fileIsCleaned = true;
            }
        } catch (IOException | NullPointerException e) {
            logger.error("FAILED! ", e);
        }
    }

    public ReportData generateDeletes() {
        String deletes = DeletesRows.withTemplate(templateReport);
        deletes = deletes.replace("{ACC_ID_START}", String.valueOf(accIdBeg));
        deletes = deletes.replace("{ACC_ID_END}", String.valueOf(accIdEnd));
        writeData(deletes);

        return this;
    }

    public ReportData generateInserts() {
        HashMap<String, ArrayList<String>> insersMap = InsertsRows.withTemplate(templateReport);

        int countAccRows = 0;
        for (Map.Entry<String, ArrayList<String>> pair : insersMap.entrySet()) {
            StringBuilder builder = new StringBuilder();
            builder.append("// insert for ").append(pair.getKey()).append("\r\n");

            ArrayList<String> insertsText = pair.getValue();
            for (int accId = accIdBeg; accId <= accIdEnd; accId++) {
                countAccRows++;
                String l_lineText;

                for (int k = 0; k < insertsText.size(); k++) {
                    String lineText = insertsText.get(k);
                    String ID = String.format("-%d%06d", accId, k);

                    l_lineText = lineText.replace("{ID}", ID)
                            .replace("{ACC_ID}", String.valueOf(accId))
                            .replace("{OP_UNI}", ID);

                    builder.append(l_lineText);
                }

                if (countAccRows % 500 == 0){
                    builder.append("commit;\r\n");
                    writeData(builder.toString());
                    builder = new StringBuilder();
                }
            }

            if (countAccRows % 500 != 0) {
                builder.append("commit;")
                        .append("\n\n\n");
                writeData(builder.toString());
            }
        }


        return this;
    }

    public ReportData withTemplateFileGeneration(Path templatePath) throws ParameterException {
        if (templatePath == null) {
            if (!initTemplate()) {
                throw new ParameterException("");
            }
        } else {
            this.templateReport = templatePath.toFile();
        }

        return this;
    }

    private void initAccId() {
        String accBegParamKey = "";
        String accEndParamKey = "";
        String accCountParamKey = "acc_count";

        switch (report){
            case REPORT_DEBT_NPF_SUD:
                accBegParamKey = "acc_id_beg_sud";
                accEndParamKey = "acc_id_end_sud";
                break;
            case REPORT_DEBT_NPF_MSK:
                accBegParamKey = "acc_id_beg_msk";
                accEndParamKey = "acc_id_end_msk";
            case CONTROL_REPORT:
                accBegParamKey = "acc_id_beg_ctrl";
                accEndParamKey = "acc_id_end_ctrl";
                break;
        }


        try {
            accIdBeg = Integer.parseInt(propFile.getParam(accBegParamKey).trim());
        } catch (ParameterException e){
            throw new ParameterException("");
        }

        try {
            accIdEnd = Integer.parseInt(propFile.getParam(accEndParamKey).trim());
        } catch (NullPointerException e) {
            try {
                int accCount = Integer.parseInt(propFile.getParam(accCountParamKey).trim());
                if (accCount > 9999 || accCount < 1) {
                    throw new NullPointerException();
                } else {
                    accIdEnd = accIdBeg + Integer.parseInt(propFile.getParam(accCountParamKey).trim()) - 1;
                    logger.warn(String.format("Used default key '%s' = %d!", accCountParamKey, accCount ));
                }
            } catch (NullPointerException e1){
                logger.warn(String.format("Used default '%s' = 5!", accCountParamKey));
                accIdEnd = accIdBeg + 4;
            }
        }
    }

    private boolean initTemplate() {
        boolean resultBool = false;
        try {
            switch (report) {
                case REPORT_DEBT_NPF_MSK:
                    templateReport = Paths.get("templates/report_MSK.tmp").toFile();
                    break;
                case REPORT_DEBT_NPF_SUD:
                    templateReport = Paths.get("templates/report_SUD.tmp").toFile();
                    break;
                case CONTROL_REPORT:
                    templateReport = Paths.get("templates/report_CONTROL.tmp").toFile();
                    break;
                default:
                    templateReport = Paths.get("templates/report.tmp").toFile();
                    break;
            }

            resultBool = true;
        } catch (NullPointerException e) {
            logger.error("FAILED", e);
        }

        return resultBool;
    }

    private void writeData(String data){
        try (RandomAccessFile writer = new RandomAccessFile(outFile.toFile(), "rw")){
            writer.seek(outFile.toFile().length());
            writer.write(data.getBytes("Windows-1251"));
        }
        catch (IOException | NullPointerException e){
            logger.error("FAILED! ", e);
        }
    }

    @Override
    public String toString() {
        return  "Generate report: " + report + "\r\n" +
                "File: " + outFile.toFile().getAbsolutePath() +":\n" +
                "ACC_ID between " + accIdBeg + " and " + accIdEnd + "\n\n"
                ;
    }

}
