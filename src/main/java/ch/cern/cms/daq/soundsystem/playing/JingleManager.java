package ch.cern.cms.daq.soundsystem.playing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

/**
 *
 */
@Service
public class JingleManager {

    /**
     * Path to directory with jingles. It will be scanned to find jingles.
     */
    @Value("${jingle.path}")
    private String path;

    private List<String> avialableExtensions = Arrays.asList(".wav", ".mp3");


    /**
     * Available jingles since last scan. Maps name of the jingle to its absolute path. Key is with filename extension.
     */
    private Map<String, String> availableJingles = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(JingleManager.class);

    @PostConstruct
    public void initIt() throws Exception {
        scanDirectory();
    }

    public Set<String> getAvailableJingles() {
        return availableJingles.keySet();
    }

    public String getAbsolutePathToJingleByFilename(String filename) {

        if (availableJingles == null || availableJingles.size() == 0) {
            logger.info("There is no available jingles at the moment. Rescanning directory: " + path);
            scanDirectory();
        }

        String absolutePath = getAbsolutePathToJingleByFilenameWithExtension(filename);
        if (absolutePath == null) {
            absolutePath = getAbsolutePathToJingleByFilenameWithoutExtension(filename);
        }
        if (absolutePath == null) {
            throw new RuntimeException("Jingle not available. Could not find by name: " + filename);
        }

        logger.info("On request " + filename + " pointing to jingle: " + absolutePath);
        return absolutePath;

    }

    private String getAbsolutePathToJingleByFilenameWithoutExtension(String filename) {


        logger.debug("Getting jingle by filename without extension: " + filename);

        for (String extension : avialableExtensions) {
            String result = getAbsolutePathToJingleByFilenameWithExtension(filename + extension);

            if (result != null) {
                return result;
            }
        }

        logger.warn("Could not find a file " + filename + " with any of available extensions: " + avialableExtensions);
        return null;

    }

    private String getAbsolutePathToJingleByFilenameWithExtension(String filename) {


        logger.debug("Getting jingle by filename with extension: " + filename);

        if (availableJingles.containsKey(filename)) {
            return availableJingles.get(filename);
        } else {
            return null;
        }

    }

    private boolean allowedExtension(String filename) {

        for (String extension : avialableExtensions) {
            if (filename.endsWith(extension)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Access the given directory and get all files that can be played
     */
    public void scanDirectory() {

        File actual = new File(path);
        logger.info("Available jingles in directory " + path);
        for (File f : actual.listFiles()) {
            logger.info(" * " + f.getName());

            if (allowedExtension(f.getName())) {
                availableJingles.put(f.getName(), f.getAbsolutePath());
            }

        }

    }

}
