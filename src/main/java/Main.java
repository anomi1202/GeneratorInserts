import GenerateData.InsertsRows;
import Report.GeneratorReportsDataFactory;

import static DataTypes.ReportType.CONTROL_REPORT;
import static DataTypes.ReportType.REPORT_DEBT_NPF_MSK;
import static DataTypes.ReportType.REPORT_DEBT_NPF_SUD;

public class Main {

    public static void main(String[] args) throws Exception {
//        GeneratorReportsDataFactory.generateReport(REPORT_DEBT_NPF_SUD);
//        GeneratorReportsDataFactory.generateReport(REPORT_DEBT_NPF_MSK);
        GeneratorReportsDataFactory.generateReport(CONTROL_REPORT);
    }
}
