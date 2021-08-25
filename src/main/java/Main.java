import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[] { "-h" };
        }

        File sourceDir = null;
        File destination = null;

        boolean isVerbose = false;

        int i = 0;
        for (String arg : args) {
            switch (arg) {
                case "--help":
                    System.out.println("");
                    System.out.println("Usage: pdfmerger [OPTIONS] SOURCE DESTINATION");
                    System.out.println("Merges pdf files from SOURCE directory to DESTINATION.");
                    System.out.println("DESTINATION may be a file or a directory.");
                    System.out.println("If DESTINATION is a directory, output file name sets by default to out.pdf.");
                    System.out.println(" -v, --verbose\t\texplain what is being done");
                    System.out.println(" --help\t\t\tdisplay this help and exit");
                    System.out.println(" --version\t\toutput version information and exit");
                    return;
                case "--version":
                    try {
                        System.out.println(Utils.getVersion());
                    } catch (Exception e) {
                        System.err.println("pdfmerger: " + e.getMessage());
                    }
                    return;
                case "-v":
                case "--verbose":
                    if (args.length > 1) {
                        isVerbose = true;
                    } else {
                        System.err.println("pdfmerger: wrong number of arguments specified");
                        System.err.println("Try 'pdfmerger --help' for more information.");
                        System.exit(1);
                    }
                    break;
                default:
                    if (args.length == 1 || i == 1) {
                        sourceDir = new File(arg);
                        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
                            System.err.println("pdfmerger: source directory is not exist");
                            System.exit(1);
                        }
                    } else if (i == 2) {
                        destination = new File(arg);
                        if (!destination.exists()) {
                            System.err.println("pdfmerger: destination is not exist");
                            System.exit(1);
                        }
                    }

                    try {
                        Merger merger = new Merger(sourceDir, destination);
                        merger.setVerbose(isVerbose);
                        merger.merge();
                    } catch (IOException e) {
                        System.err.println("pdfmerger: merge failed");
                        System.err.println(e.getMessage());
                    }
            }
            i++;
        }
    }
}
