package Report;

import java.io.IOException;

import static DataTypes.ReportType.REPORT_DEBT_NPF_MSK;

public class ReportDebtNpfMsk {

    public static ReportData generate() throws IOException {
        ReportData generator = new ReportData(REPORT_DEBT_NPF_MSK);
        generator.generateDeletes()
                .generateInserts();
        return generator;
    }
}
