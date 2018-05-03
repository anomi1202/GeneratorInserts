package Report;

import DataTypes.ReportType;

import static DataTypes.ReportType.*;

public class GeneratorReportsDataFactory {

    public static void generateReport(ReportType report) throws Exception {
        ReportData generator = null;
        switch (report){
            case REPORT_DEBT_NPF_SUD:
                generator = ReportDebtNpfSud.generate();
                break;
            case REPORT_DEBT_NPF_MSK:
                generator = ReportDebtNpfMsk.generate();
                break;
            default:
                generator = new ReportData(CONTROL_REPORT)
                        .generateDeletes()
                        .generateInserts();

        }

        System.out.println(generator);
    }
}
