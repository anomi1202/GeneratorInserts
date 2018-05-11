package Report;

import Common.ReportType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import static Common.ReportType.*;

public class GeneratorReportsDataFactory {
    private static Logger logger = LoggerFactory.getLogger(GeneratorReportsDataFactory.class);

    public static void generateReport(ReportType report, Path templatePath) {
        logger.info("Select report " + report);
        logger.info("Select template file " + templatePath);
        ReportData generator;
        switch (report){
            case REPORT_DEBT_NPF_SUD:
                generator = new ReportData(REPORT_DEBT_NPF_SUD)
                        .withTemplateFileGeneration(templatePath)
                        .generateDeletes()
                        .generateInserts();
                break;
            case REPORT_DEBT_NPF_MSK:
                generator = new ReportData(REPORT_DEBT_NPF_MSK)
                        .withTemplateFileGeneration(templatePath)
                        .generateDeletes()
                        .generateInserts();
                break;
            case CONTROL_REPORT:
                generator = new ReportData(CONTROL_REPORT)
                        .withTemplateFileGeneration(templatePath)
                        .generateDeletes()
                        .generateInserts();
                break;
            default:
                generator = new ReportData(TEMPLATE)
                        .withTemplateFileGeneration(templatePath)
                        .generateDeletes()
                        .generateInserts();

        }

        logger.info("Finish generate report " + report);
        System.out.println(generator);
    }
}
