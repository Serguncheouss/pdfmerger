import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static String getVersion() throws IOException, XmlPullParserException {
        File pomFile = new File("pom.xml");
        if (!pomFile.exists() || !pomFile.isFile()) {
            throw new FileNotFoundException("Unable to read file pom.xml.");
        }

        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(pomFile));

        return model.getVersion();
    }
}
