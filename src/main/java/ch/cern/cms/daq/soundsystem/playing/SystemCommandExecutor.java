package ch.cern.cms.daq.soundsystem.playing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class SystemCommandExecutor {

    Logger logger = LoggerFactory.getLogger(SoundService.class);

    public boolean execute(String command) {

        logger.info("Executing command " + command);
        Runtime r = Runtime.getRuntime();
        try {
            Process p = r.exec(command);
            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                //System.out.println(line);
            }

            b.close();
            logger.info("Done executing " + command);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
