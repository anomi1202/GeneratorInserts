package Report;

import DataTypes.ReportType;
import GenerateData.DeletesRows;
import GenerateData.InsertsRows;
import GenerateData.PropFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportData {
    private PropFile propFile = new PropFile();
    private Path outFile;
    private boolean fileIsCleaned = false;
    private int accIdBeg;
    private int accIdEnd;
    private ReportType report;

    public ReportData(ReportType report) throws IOException {
        this.report= report;
        initAccId();
        createOutFileForReportData();
    }

    private void createOutFileForReportData() throws IOException {
        switch (report){
            case REPORT_DEBT_NPF_SUD:
                outFile = Paths.get(propFile.getParam("outFile_debt_npf_sud"));
                break;
            case REPORT_DEBT_NPF_MSK:
                outFile = Paths.get(propFile.getParam("outFile_debt_npf_msk"));
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
    }

    public ReportData generateDeletes(){
        String deletes = DeletesRows.forReport(report);
        deletes = deletes.replace("{ACC_ID_START}", String.valueOf(accIdBeg));
        deletes = deletes.replace("{ACC_ID_END}", String.valueOf(accIdEnd));
        writeData(deletes);

        return this;
    }

    public ReportData generateInserts(){
        HashMap<String, ArrayList<String>> insersMap = InsertsRows.forReport(report);

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

    private void initAccId() throws IOException {
        switch (report){
            case REPORT_DEBT_NPF_SUD:
                if (propFile.getParam("acc_id_beg_sud") != null) {
                    accIdBeg = Integer.parseInt(propFile.getParam("acc_id_beg_sud").trim());
                }

                if (propFile.getParam("acc_id_end_sud") != null){
                    accIdEnd = Integer.parseInt(propFile.getParam("acc_id_end_sud").trim());
                } else if (propFile.getParam("acc_count") != null
                        && Integer.parseInt(propFile.getParam("acc_count").trim()) <= 9999){
                    accIdEnd = accIdBeg + Integer.parseInt(propFile.getParam("acc_count").trim()) - 1;
                } else {
                    accIdEnd = accIdBeg + 4;
                }
                break;
            case REPORT_DEBT_NPF_MSK:
                if (propFile.getParam("acc_id_beg_msk") != null) {
                    accIdBeg = Integer.parseInt(propFile.getParam("acc_id_beg_msk"));
                }

                if (propFile.getParam("acc_id_end_msk") != null){
                    accIdEnd = Integer.parseInt(propFile.getParam("acc_id_end_msk"));
                } else if (propFile.getParam("acc_count") != null
                        && Integer.parseInt(propFile.getParam("acc_count").trim()) <= 9999){
                    accIdEnd = accIdBeg + Integer.parseInt(propFile.getParam("acc_count").trim()) - 1;
                } else {
                    accIdEnd = accIdBeg + 4;
                }
                break;
        }
    }

    private void writeData(String data){
        try (RandomAccessFile writer = new RandomAccessFile(outFile.toFile(), "rw")){
            writer.seek(outFile.toFile().length());
            writer.write(data.getBytes("Windows-1251"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return  "Generate dataFile: " + report + "\r\n" +
                "File: " + outFile.toFile().getAbsolutePath() +":\n" +
                "ACC_ID between " + accIdBeg + " and " + accIdEnd + "\n\n"
                ;
    }

}
