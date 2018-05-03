package Report;

import java.io.IOException;

import static DataTypes.ReportType.REPORT_DEBT_NPF_SUD;

public class ReportDebtNpfSud {

    public static ReportData generate() throws IOException {
        ReportData generator = new ReportData(REPORT_DEBT_NPF_SUD);
        generator.generateDeletes()
                .generateInserts();
        return generator;
    }
}
