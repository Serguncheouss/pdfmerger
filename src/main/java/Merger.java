import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.IOException;

public class Merger {
    private final static String DEFAULT_OUT_FILE_NAME = "out.pdf";

    private final File source;
    private final File destination;

    private boolean isVerbose;

    public Merger(File sourceDir, File destination) {
        this.source = sourceDir;
        if (destination == null) {
            this.destination = new File(DEFAULT_OUT_FILE_NAME);
        } else if (destination.isDirectory()) {
            this.destination = new File(
                    destination.getAbsolutePath() +
                            File.separatorChar +
                            DEFAULT_OUT_FILE_NAME
            );
        } else {
            this.destination = destination;
        }

    }

    public void merge() throws IOException {
        File[] sourceListing = source.listFiles();

        if (sourceListing == null) {
            throw new IOException("Unable to read source directory.");
        }

        PDFMergerUtility merger = new PDFMergerUtility();

        int i = 0;
        if (isVerbose) {
            System.out.println("Preparing files...");
        }
        for (File file: sourceListing) {
            if (file.isDirectory()) {
                continue;
            }

            merger.addSource(file);

            if (isVerbose) {
                System.out.println(++i + " " + file.getAbsolutePath());
            }
        }

        merger.setDestinationFileName(destination.getAbsolutePath());
        if (isVerbose) {
            System.out.println("Merging files...");
        }
        merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        if (isVerbose) {
            System.out.println("Total " + i + " file(s).");
            System.out.println("Output file: " + destination.getAbsolutePath());
        }
    }

    public void setVerbose(boolean verbose) {
        isVerbose = verbose;
    }
}
