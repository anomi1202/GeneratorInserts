import Common.ReportType;
import Report.GeneratorReportsDataFactory;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.nio.file.Path;

public class Main {

    @Parameter (names = {"-report",  "-r"}, description = "Report Name")
    public static ReportType report;

    @Parameter (names = {"-templatePath",  "-template", "-tp", "-t"}, description = "Report path and name")
    public static Path templatePath;

    public static void main(String[] args) {
        JCommander jCommander = new JCommander(new Main());
        try {
            if (args.length == 0){
                throw new ParameterException("");
            } else {
                jCommander.parse(args);
                GeneratorReportsDataFactory.generateReport(report, templatePath);
            }
        } catch (ParameterException e) {
            jCommander.usage();
        }

    }
}
