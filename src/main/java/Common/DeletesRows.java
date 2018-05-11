package Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DeletesRows {
    public static Logger logger = LoggerFactory.getLogger(DeletesRows.class);
    private static String deletes;
    private static File templateFile;

    private static void readDeletesFromFile() {
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
            logger.error("FAILED! ", e);
        }
    }

    public static String withTemplate(File template) {
        logger.info("Generate deletes with template file " + template);
        templateFile = template;
        readDeletesFromFile();
        return deletes;
    }
}
